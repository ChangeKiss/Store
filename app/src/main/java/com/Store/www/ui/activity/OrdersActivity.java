package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AddShoppingrequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.IntroduceResponse;

import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.OrdersAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品介绍点进去的下单界面
 */

public class OrdersActivity extends BaseToolbarActivity implements OrdersAdapter.OnOrderItemClickListener{

    @BindView(R.id.lv_orders)
    RecyclerView mLvOrders;
    @BindView(R.id.tv_order_money)
    TextView mTvOrderMoney;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_order_all_money)
    TextView mTvOrderAllMoney;
    @BindView(R.id.tv_into_cart)
    TextView mTvIntoCart;
    @BindView(R.id.tv_size_location)
    TextView mTvSizeLocation; //显示尺寸
    @BindView(R.id.tv_stocks_location)
    TextView mTvStocksLocation;  //剩余库存控件
    @BindView(R.id.tv_number_location)
    TextView mTvNumberLocation; //显示数量的控件
    @BindView(R.id.layout_stocks)
    LinearLayout mLayoutStocks;  //上面三个控件的父布局
    @BindView(R.id.vi_line)
    View mViLine;
    @BindView(R.id.layout_cart)
    LinearLayout mLayoutCart;  //加入购物车布局
    @BindView(R.id.avi_view)
    AVLoadingIndicatorView mAviView;

    OrdersAdapter mAdapter;
    private int productId, mMoney;
    private String toolbarTitle;
    public int[] goodsSum = new int[]{};
    private int[] orderNumber;
    private int id=0; //加入购物车时用ID 不知道做什么用的 写死0
    private int totalPrice; //商品总价
    private int mSum;
    private int countValue;
    List<AddShoppingrequest.SKUdataBean> been = new ArrayList<>();
    AddShoppingrequest.SKUdataBean mBean;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_oders;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        toolbarTitle = getIntent().getStringExtra("toolbarTitle");
        initToolbar(this, true, toolbarTitle);
        productId = getIntent().getIntExtra("productId", 0);
        mTvIntoCart.setEnabled(true);
        mAdapter = new OrdersAdapter(this, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mLvOrders.setLayoutManager(manager);
        mLvOrders.setAdapter(mAdapter);
        setViewLocation();  //设置布局的位置
        aviShow();  //显示加载动画
        getIntroduce();
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

    //动态设置控件的位置
    private void setViewLocation(){
        params=(LinearLayout.LayoutParams) mTvSizeLocation.getLayoutParams();
        params=(LinearLayout.LayoutParams) mTvStocksLocation.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvNumberLocation.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3;
        mTvSizeLocation.setLayoutParams(params);
        mTvStocksLocation.setLayoutParams(params);
        mTvNumberLocation.setLayoutParams(params);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
    }

    //获取商品详情
    private void getIntroduce() {
        RetrofitClient.getInstances().getIntroduce(mUserId, productId).enqueue(new UICallBack<IntroduceResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(IntroduceResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        if (isTop){
                            aviHide(); //隐藏加载动画
                            mViLine.setVisibility(View.VISIBLE); //把布局显示出来
                            mLayoutStocks.setVisibility(View.VISIBLE);  //把布局显示出来
                            mLvOrders.setVisibility(View.VISIBLE);
                            mLayoutCart.setVisibility(View.VISIBLE);
                            goodsSum = new int[bean.getData().getSkuData().size()];
                            mMoney = bean.getData().getPrice();
                            mTvOrderMoney.setText("单价:" + ActivityUtils.changeMoneys(mMoney));
                            for (int i = 0; i < goodsSum.length; i++) {
                                goodsSum[i] = 0;
                                mBean=new AddShoppingrequest.SKUdataBean();
                                mBean.setName(bean.getData().getSkuData().get(i).getColorName()+bean.getData().getSkuData().get(i).getSizeName());
                                mBean.setColorName(bean.getData().getSkuData().get(i).getColorName());
                                mBean.setFontcolor(0);
                                mBean.setSku(bean.getData().getSkuData().get(i).getSku());
                                been.add(mBean);
                            }
                            mAdapter.setMordersNumber(goodsSum);
                            mAdapter.addAll(bean.getData().getSkuData());
                            LogUtils.d("数组长度===" + goodsSum.length);
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        mLayoutCart.setVisibility(View.GONE);
                        showToast(bean.getErrMsg());
                        break;

                }
            }
        });
    }

    //减号的点击事件
    @Override
    public void setMinusClickListener(int position, int sum) {
        mTvOrderAllMoney.setText(""+ActivityUtils.changeMoneys(sum * mMoney));  //总价
        mTvOrderNumber.setText("数量:"+sum);
        mSum = sum;
        totalPrice = sum *mMoney;
    }
    //加号的点击事件
    @Override
    public void setPlusClickListener(int position, int sum) {
        mTvOrderAllMoney.setText(""+ActivityUtils.changeMoneys(sum * mMoney)); //总价
        mTvOrderNumber.setText("数量:"+sum);
        mSum = sum;
        totalPrice = sum *mMoney;
    }

    //编辑时更新数量
    @Override
    public void setOrdersNumber(int position,int sum) {
        mTvOrderAllMoney.setText(""+ActivityUtils.changeMoneys(sum * mMoney)); //总价
        mTvOrderNumber.setText("数量:"+sum);
        mSum = sum;
        totalPrice = sum *mMoney;
    }



    //点击加入购物车
    @OnClick(R.id.tv_into_cart)
    public void onViewClicked() {
        mTvIntoCart.setEnabled(false);
        requestAddShoppingCart();
    }

    //加入购物车
    private void requestAddShoppingCart(){
        for (int i=0;i<mAdapter.getMordersNumber().length;i++){
            been.get(i).setCount(mAdapter.getMordersNumber()[i]);
            LogUtils.d("数量=01="+mAdapter.getMordersNumber()[i] );
        }
        AddShoppingrequest skUdataBean = new AddShoppingrequest(id,productId,mUserId,mSum,totalPrice,mMoney,been);
        RetrofitClient.getInstancesTest().requestAddShoppingCart(skUdataBean).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    mTvIntoCart.setEnabled(true);
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (isTop){
                                showToast("加入购物车成功");
                                finish();
                            }
                            break;
                        default:
                            if (isTop){
                                showToast(bean.getErrMsg());
                                mTvIntoCart.setEnabled(true);
                            }
                            break;
                    }
                }
            }
        });

    }

}
