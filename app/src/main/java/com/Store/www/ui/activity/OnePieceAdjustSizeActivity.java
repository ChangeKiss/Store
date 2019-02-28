package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AdjustSizeRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.OnePieceStocksResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.OnePieceAdjustAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 单件商品尺码调整库存界面
 */
public class OnePieceAdjustSizeActivity extends BaseToolbarActivity implements OnePieceAdjustAdapter.OnAdjustItemClickListener{
    @BindView(R.id.rl_one_piece_adjust_size)
    RecyclerView mRy;
    @BindView(R.id.tv_adjust_size_count)
    TextView mTvAdjustSizeCount;  //换货数量
    @BindView(R.id.btn_repertory_adjust_cart)
    TextView mBtnRepertoryAdjustCart;
    @BindView(R.id.tv_size_location)
    TextView mTvSizeLocation; //尺寸的位置
    @BindView(R.id.tv_stocks_location)
    TextView mTvStocksLocation;  //剩余库存控件的位置
    @BindView(R.id.tv_number_location)
    TextView mTvNumberLocation;

    OnePieceAdjustAdapter mAdapter;
    LinearLayout.LayoutParams params;

    public int[] RepertorySum = new int[]{};
    public int[] countSum = new int[]{};
    private String toolbarTitle, productNo;
    private int repositoryId, type,mProductId;
    private int adjustSum;
    private String adjustType;
    int s;
    List<AdjustSizeRequest.SKUdataBean> dataBeen = new ArrayList<>();
    AdjustSizeRequest.SKUdataBean Data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_one_piece_adjust_size;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        toolbarTitle = getIntent().getStringExtra("title");
        repositoryId = getIntent().getIntExtra("repositoryId", 0);
        type = getIntent().getIntExtra("type", type);
        productNo = getIntent().getStringExtra("productNo");
        mProductId = getIntent().getIntExtra("productId",0);
        initToolbar(this, true, toolbarTitle);
        mAdapter = new OnePieceAdjustAdapter(this,this);
        mRy.setLayoutManager(new LinearLayoutManager(this));
        mRy.setAdapter(mAdapter);
        setViewLocation();  //设置控件的大小
        getOnePiece();
    }

    //动态设置控件的大小
    private void setViewLocation(){
        params=(LinearLayout.LayoutParams) mTvSizeLocation.getLayoutParams();
        params=(LinearLayout.LayoutParams) mTvStocksLocation.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvNumberLocation.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3;
        mTvSizeLocation.setLayoutParams(params);
        mTvStocksLocation.setLayoutParams(params);
        mTvNumberLocation.setLayoutParams(params);
    }

    //获取单款商品库存
    private void getOnePiece(){
        RetrofitClient.getInstances().getOnePiece(repositoryId,type,productNo).enqueue(new UICallBack<OnePieceStocksResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
                mBtnRepertoryAdjustCart.setEnabled(false);
            }

            @Override
            public void OnRequestSuccess(OnePieceStocksResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        RepertorySum = new int[bean.getData().size()];
                        countSum = new int[bean.getData().size()];
                        for (int i=0;i<RepertorySum.length;i++){
                            countSum[i] = Integer.parseInt(bean.getData().get(i).getRepositoryCount());
//                            LogUtils.d("第"+i+"款原始商品库存"+countSum[i]);
                            RepertorySum[i] = 0;
                            Data = new AdjustSizeRequest.SKUdataBean();
                            Data.setSku(bean.getData().get(i).getSku());
                            Data.setName(bean.getData().get(i).getColor());
                            Data.setRepositoryCount(bean.getData().get(i).getRepositoryCount());
                            Data.setSize(bean.getData().get(i).getSize());
                            dataBeen.add(Data);
                        }
                        mAdapter.setmCountSum(countSum);
                        mAdapter.setmRepertorySum(RepertorySum);
                        mAdapter.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //加入换货车的点击事件
    @OnClick(R.id.btn_repertory_adjust_cart)
    public void onViewClicked() {
        if (adjustSum!=0){
            showToast(R.string.synthetical_adjust_count);
            return;
        }else {
            for (int i=0;i<mAdapter.getmRepertorySum().length;i++){
                if (mAdapter.getmRepertorySum()[i]!=0){
                    adjustType = "ok";
                    break;
                }else {
                    adjustType = "no";
                }
            }
            LogUtils.d("adjustType="+adjustType);
            if (adjustType.equals("ok")){
                mBtnRepertoryAdjustCart.setEnabled(false);
                requestAdjustSize();
            }else if (adjustType.equals("no")){
                showToast(R.string.pay_content_null);
                return;
            }

        }

    }

    //提交换货请求
    private void requestAdjustSize(){
        for (int i =0;i<mAdapter.getmRepertorySum().length;i++){
            dataBeen.get(i).setCount(mAdapter.getmRepertorySum()[i]);
        }
        AdjustSizeRequest request = new AdjustSizeRequest(repositoryId,userId,mProductId,adjustSum,dataBeen);
        RetrofitClient.getInstances().requestAdjustSize(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.submit_ok);
                        mBtnRepertoryAdjustCart.setEnabled(true);
                        finish();
                        break;
                    default:
                        mBtnRepertoryAdjustCart.setEnabled(true);
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //减号的点击数量的监听
    @Override
    public void setMinusClickListener(int position, int orderSum) {
        adjustSum = 0;
        adjustSum = orderSum;
        mTvAdjustSizeCount.setText("数量："+orderSum);
    }

    //加号的点击数量的监听
    @Override
    public void setPlusClickListener(int position, int orderSum) {
        adjustSum = 0;
        adjustSum = orderSum;
        mTvAdjustSizeCount.setText("数量："+orderSum);
    }
}
