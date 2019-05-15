package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.CartWhetherNullResponse;
import com.Store.www.entity.CommodityManagerResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.CommodityAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

//活动商品/集 管理界面
public class CommodityActivity extends BaseToolbarActivity implements
        OnRefreshListener, CommodityAdapter.OnOrderButtonClickListener {
    @BindView(R.id.ry_commodity)
    LRecyclerView mRyCommodity;
    @BindView(R.id.tv_toolbar_right)
    ImageView mIvToolbarRight;
    CommodityAdapter mAdapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mChartId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_commodity;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, "活动商品");
        mChartId = getIntent().getIntExtra("id",0);
        mIvToolbarRight.setVisibility(View.VISIBLE);
        mAdapter = new CommodityAdapter(this);
        mAdapter.setListener(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRyCommodity.setLayoutManager(layoutManager);
        mRyCommodity.setAdapter(mLRecyclerViewAdapter);
        mRyCommodity.setOnRefreshListener(this);
        mRyCommodity.setLoadMoreEnabled(false);
        mRyCommodity.setHeaderViewColor(R.color.redColorBackground, R.color.textColorBlack, R.color.colorLucency);
        mRyCommodity.setFooterViewColor(R.color.redColorBackground, R.color.textColorBlack, R.color.colorLucency);
        mRyCommodity.setFooterViewHint("正在加载", "我是有底线的", "网络没了..");
        getHuoDongCommodity(mChartId);
        //getCommodity(mPageIndex);
    }

    //获取购物车中是否有商品
    private void getCartWhetherIsNull(){
        RetrofitClient.getInstancesTest().getCartWhetherIsNull(mUserId).enqueue(new UICallBack<CartWhetherNullResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(CartWhetherNullResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            if (bean.getData().isIsCartHaveCommodity()){
                                isCart = false;
                                mIvToolbarRight.setImageResource(R.mipmap.cart_no_null);
                            }else {
                                isCart = true;
                                mIvToolbarRight.setImageResource(R.mipmap.cart_no);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * 网络获取商品信息
     */
    private void getCommodity(int pageIndex) {
        if (pageIndex==1){
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
            DialogLoading.shows(mContext,R.string.hint_loading);
        }
        RetrofitClient.getInstances().getCommodity(mUserId, mCountPerPage, pageIndex,token).enqueue(new UICallBack<CommodityManagerResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }
            @Override
            public void OnRequestSuccess(CommodityManagerResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        LogUtils.d("是否获取商品详情="+isTop);
                        if (isTop){  //如果当前活动是最上层(可见)  就加载数据
                            if (bean.getData().size() == 0) {
                                mRyCommodity.setNoMore(true);
                            } else {
                                mAdapter.addAll(bean.getData());
                                mRyCommodity.refreshComplete(mCountPerPage);//设置一页加载数量为10
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    /**
     * 获取活动商品集商品
     */
    private void getHuoDongCommodity(int chartId){
        DialogLoading.shows(mContext,R.string.hint_loading);
        RetrofitClient.getInstances().getProductList(chartId).enqueue(new UICallBack<CommodityManagerResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(CommodityManagerResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData().size() == 0) {
                                mRyCommodity.setNoMore(true);
                            } else {
                                mAdapter.addAll(bean.getData());
                                mRyCommodity.refreshComplete(mCountPerPage);//设置一页加载数量为10
                                mAdapter.notifyDataSetChanged();
                            }
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop =true;
        getCartWhetherIsNull();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }



    @Override
    public void onRefresh() {
        mAdapter.getDataList().clear();
        mPageIndex = 1;
        //getCommodity(mPageIndex);
        getHuoDongCommodity(mChartId);
        getCartWhetherIsNull();
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    //item的点击事件
    @Override
    public void setItemButtonClickListener(int position, int productId) {
        LogUtils.d("商品ID=02=" + productId);
        Intent intent = new Intent(this, IntroduceActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }

    //右上角购物车的点击事件
    @OnClick({R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_toolbar_right:
                if (isCart){  //没有商品
                    showToast("购物车空空如也");
                }else {
                    mActivityUtils.startActivity(ShoppingCartActivity.class);
                    finish();
                }
                break;

        }

    }
}
