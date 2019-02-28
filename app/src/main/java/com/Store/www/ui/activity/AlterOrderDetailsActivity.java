package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AlterOrderDetailsResponse;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.SaveAlterShoppingRequest;
import com.Store.www.entity.ShoppingCartRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.AlterOrderAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.UserPrefs;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改商品信息的界面
 */
public class AlterOrderDetailsActivity extends BaseToolbarActivity implements AlterOrderAdapter.OnOrderItemClickListener,TextWatcher{
    @BindView(R.id.rl_order_details)
    RecyclerView mRy;  //列表布局
    @BindView(R.id.tv_one_piece_money)
    TextView mTvOnePieceMoney; //单价
    @BindView(R.id.tv_all_piece_money)
    TextView mTvAllPieceMoney;  //总价
    @BindView(R.id.tv_order_alter_number)
    TextView mTvOrderAlterNumber; //修改后的总数
    @BindView(R.id.tv_alter_size)
    TextView mTvAlterSize;  //尺寸
    @BindView(R.id.tv_stocks_number)
    TextView mTvStocksNumber;  //库存数量
    @BindView(R.id.tv_alter_number)
    TextView mTvAlterNumber; //修改数量
    @BindView(R.id.layout_alter_content)
    LinearLayout mLayoutAlterContent;  //尺码库存标题的布局
    @BindView(R.id.vi_line)
    View mViLine;  //分割线布局
    @BindView(R.id.layout_save_alter)
    LinearLayout mLayoutSaveAlter;  //底部的修改商品数量布局
    @BindView(R.id.btn_save_order)
    TextView mBtnSaveOrder;
    @BindView(R.id.avi_view)
    AVLoadingIndicatorView mAviView;  //加载动画

    AlterOrderAdapter mAdapter;
    private String title;
    private int cartId,productId,money,alterNumber,totalMoney,totalNumber;
    public int[] goodSum = new int[]{};
    LinearLayout.LayoutParams params;

    List<SaveAlterShoppingRequest.SKUdataBean> been = new ArrayList<>();
    SaveAlterShoppingRequest.SKUdataBean dataBean ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_alter_order_details;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        cartId = getIntent().getIntExtra("cartid",0);
        productId = getIntent().getIntExtra("product",0);
        title = getIntent().getStringExtra("title");
        initToolbar(this, true, title);
        mAdapter = new AlterOrderAdapter(this,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(mAdapter);
        setViewLocation();
        aviShow();  //先显示一个加载动画 提升用户体验
        requestShoppingCart();
    }


    //显示加载动画
    private void aviShow(){
        mAviView.setVisibility(View.VISIBLE);
        mAviView.show();
    }

    //关闭加载动画
    private void aviHide(){
        mAviView.setVisibility(View.GONE);
        mAviView.hide();
    }

    //动态设置布局的大小
    private void setViewLocation(){
        params = (LinearLayout.LayoutParams) mTvAlterSize.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3;
        mTvAlterNumber.setLayoutParams(params);
        mTvAlterSize.setLayoutParams(params);
        mTvStocksNumber.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;  //
    }

    //获取商品详情
    private void requestShoppingCart(){
        ShoppingCartRequest request = new ShoppingCartRequest(userId,cartId,productId);
        RetrofitClient.getInstancesTest().requestShoppingCart(request).enqueue(new UICallBack<AlterOrderDetailsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(AlterOrderDetailsResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){  //活动是否在最上层  是就进行操作
                            aviHide(); //将加载动画隐藏起来
                            mLayoutAlterContent.setVisibility(View.VISIBLE);
                            mViLine.setVisibility(View.VISIBLE);
                            mLayoutSaveAlter.setVisibility(View.VISIBLE);
                            alterNumber = 0;
                            goodSum = new int[bean.getSKUdata().size()];
                            for (int i=0;i<bean.getSKUdata().size();i++){
                                goodSum[i]=bean.getSKUdata().get(i).getCount();
                                alterNumber+=goodSum[i];
                                dataBean = new SaveAlterShoppingRequest.SKUdataBean();
                                dataBean.setId(bean.getSKUdata().get(i).getId());
                                dataBean.setProductId(bean.getSKUdata().get(i).getProductId());
                                dataBean.setSku(bean.getSKUdata().get(i).getSku());
                                dataBean.setSizeName(bean.getSKUdata().get(i).getSizeName());
                                dataBean.setProducName(bean.getSKUdata().get(i).getProducName());
                                dataBean.setColorName(bean.getSKUdata().get(i).getColorName());
                                dataBean.setShoppingId(bean.getSKUdata().get(i).getShoppingId());
                                been.add(dataBean);
                            }
                            money = bean.getUnitPrice();
                            totalMoney = 0;
                            totalMoney = alterNumber*money;
                            mTvOnePieceMoney.setText("单价: "+ActivityUtils.changeMoneys(money));
                            mTvAllPieceMoney.setText("总价: "+(ActivityUtils.changeMoneys(alterNumber*money)));
                            totalNumber = alterNumber;
                            mTvOrderAlterNumber.setText("数量: "+alterNumber);
                            mAdapter.getDataList().clear();
                            mAdapter.setmOrderNumber(goodSum);
                            mAdapter.addAll(bean.getSKUdata());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //保存修改的点击事件
    @OnClick(R.id.btn_save_order)
    public void onViewClicked() {
        mBtnSaveOrder.setEnabled(false);
        requestSaveAlter();
    }

    //保存修改后购物车商品信息
    private void requestSaveAlter(){
        for (int i=0;i<mAdapter.getmOrderNumber().length;i++){
            been.get(i).setCount(mAdapter.getmOrderNumber()[i]);
        }
        SaveAlterShoppingRequest request = new SaveAlterShoppingRequest(cartId,productId,userId,totalNumber,totalMoney,money,been);
        RetrofitClient.getInstancesTest().requestSaveAlter(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            showToast(R.string.change_ok);
                            mBtnSaveOrder.setEnabled(true);
                            finish();
                            break;
                        default:
                            mBtnSaveOrder.setEnabled(true);
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //减号的点击事件
    @Override
    public void setOnMinusClickListener(int position, int sum) {
        totalNumber = 0;
        totalMoney = 0;
        totalMoney = sum*money;
        mTvAllPieceMoney.setText("总价: "+(ActivityUtils.changeMoneys(sum*money)));
        mTvOrderAlterNumber.setText("数量: "+sum);
        totalNumber = sum;
    }


    //加号的点击事件
    @Override
    public void setOnPlusClickListener(int position, int sum) {
        totalNumber = 0;
        totalMoney = 0;
        totalMoney = sum*money;
        mTvAllPieceMoney.setText("总价: "+(ActivityUtils.changeMoneys(sum*money)));
        mTvOrderAlterNumber.setText("数量: "+sum);
        totalNumber = sum;
    }

    //编辑时更新数量
    @Override
    public void setOrdersNumber(int position, int sum) {
        totalNumber = 0;
        totalMoney = 0;
        totalMoney = sum*money;
        mTvAllPieceMoney.setText("总价: "+(ActivityUtils.changeMoneys(sum*money)));
        mTvOrderAlterNumber.setText("数量: "+sum);
        totalNumber = sum;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    //文本框
    @Override
    public void afterTextChanged(Editable s) {

    }
}
