package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.PayAffirmRequest;
import com.Store.www.entity.SellManageResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.SellManageAdapter;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;

    /**
     * 销售管理
     *
     */
public class SellManageActivity extends BaseToolbarActivity implements TabLayout.OnTabSelectedListener,
            OnLoadMoreListener,OnRefreshListener,SellManageAdapter.onClickListener,DialogHint.OnDialogTwoButtonClickListener {
        @BindView(R.id.tab_sell)
        TabLayout mTabSell;
        @BindView(R.id.nodata)
        RelativeLayout mNoData;  //无数据的布局
        @BindView(R.id.lr_sell)
        LRecyclerView mRySell;
        SellManageAdapter mAdapter;
        LRecyclerViewAdapter mLrAdapter;
        private int mTabPosition;
        private String mType;
        private int mStatus= 101; //初始化第一个tab的状态为全部 //用来分页显示数据的状态
        private String SellOrderNumber;
        private int mIsIShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_sell_manage;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.market_manage);
        mAdapter = new SellManageAdapter(this,this);
        mLrAdapter = new LRecyclerViewAdapter(mAdapter);
        mRySell.setLayoutManager(new LinearLayoutManager(this));
        mRySell.setAdapter(mLrAdapter);
        mRySell.setOnLoadMoreListener(this);  //上拉加载
        mRySell.setOnRefreshListener(this);     //下拉刷新
        iniTab();
    }

        private void iniTab(){ //初始化TAB
            mTabSell.addTab(mTabSell.newTab().setText(R.string.all_order)); //全部
            mTabSell.addTab(mTabSell.newTab().setText(R.string.stay_ok));  //待确认
            mTabSell.addTab(mTabSell.newTab().setText(R.string.stay_shipments)); //待发货
            mTabSell.addTab(mTabSell.newTab().setText(R.string.already_shipments));  //已发货
            mTabSell.addOnTabSelectedListener(this);
        }

        @Override
        protected void onResume() {
            super.onResume();
            isTop = true;
            mAdapter.getDataList().clear();
            mPageIndex=1;
            getSell(mPageIndex);
        }

        @Override
        protected void onPause() {
            super.onPause();
            isTop = false;
        }

        //获取销售管理数据
        private void getSell(int pageIndex){
            if (pageIndex==1){
                mAdapter.getDataList().clear();
                mAdapter.notifyDataSetChanged();
                DialogLoading.shows(mContext,"加载中...");
            }
            RetrofitClient.getInstances().getSellManage(userId,mCountPerPage,pageIndex,mStatus).enqueue(new UICallBack<SellManageResponse>() {
                @Override
                public void OnRequestFail(String msg) {
                    if (isTop)checkNet();
                }

                @Override
                public void OnRequestSuccess(SellManageResponse bean) {
                    if (isTop){
                        switch (bean.getReturnValue()){
                            case 1:
                                if (bean.getData().size()!=0){  //如果有数据 隐藏无数据界面 并添加数据
                                    mNoData.setVisibility(View.GONE);
                                    mRySell.setNoMore(false);
                                    mAdapter.addAll(bean.getData());
                                    mRySell.refreshComplete(mCountPerPage);
                                    mAdapter.notifyDataSetChanged();
                                }else {  //否则无数据 显示无数据界面
                                    mNoData.setVisibility(View.VISIBLE);
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

        //获取上拉加载的销售数据
        private void getLoadMore(int pageIndex){
            RetrofitClient.getInstances().getSellManage(userId,mCountPerPage,pageIndex,mStatus).enqueue(new UICallBack<SellManageResponse>() {
                @Override
                public void OnRequestFail(String msg) {
                    if (isTop)checkNet();
                }
                @Override
                public void OnRequestSuccess(SellManageResponse bean) {
                    if (isTop){
                        switch (bean.getReturnValue()){
                            case 1:
                                if (bean.getData().size()<1){  //如果没有更多数据了 提示加载完
                                    mRySell.setNoMore(true);
                                    mPageIndex = 1;
                                }else {  //否则有数据 加载更多
                                    mAdapter.addAll(bean.getData());
                                    mRySell.refreshComplete(mCountPerPage);
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

        //顶部tab的监听
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mTabPosition = tab.getPosition();
            mAdapter.setTabPosition(mTabPosition);
            switch (mTabPosition){
                case 0:  //全部
                    mStatus = 101;
                    mPageIndex =1;
                    mAdapter.getDataList().clear();
                    getSell(mPageIndex);
                    break;
                case 1:  //待确认
                    mStatus = 4;
                    mPageIndex = 1;
                    mAdapter.getDataList().clear();
                    getSell(mPageIndex);
                    break;
                case 2:  //待发货
                    mStatus = 0;
                    mPageIndex = 1;
                    mAdapter.getDataList().clear();
                    getSell(mPageIndex);
                    break;
                case 3:  //已发货
                    mStatus = 1;
                    mPageIndex = 1;
                    mAdapter.getDataList().clear();
                    getSell(mPageIndex);
                    break;
            }

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

        //上拉加载
        @Override
        public void onLoadMore() {
            mPageIndex++;
            getLoadMore(mPageIndex);
        }

        //下拉刷新
        @Override
        public void onRefresh() {
            mPageIndex=1;
            getSell(mPageIndex);
        }

        //Item的点击事件 查看订单详情
        @Override
        public void onItemClickListener(int position, String orderNumber, int status,String currency,int isIShow) {
            mType = "ordinary";
            Intent intent = new Intent(this,SellDetailsActivity.class);
            LogUtils.d("类型="+mType);
            intent.putExtra("type",mType);
            intent.putExtra("orderNumber",orderNumber);
            intent.putExtra("status",status);
            intent.putExtra("currency",currency);
            intent.putExtra("isIShow",isIShow);
            startActivity(intent);
        }

        //支付确认的点击事件
        @Override
        public void onPayClickListener(int position, String orderNumber,int isIShow) {
            SellOrderNumber = orderNumber;
            mIsIShow = isIShow;
            if (isIShow==1){
                DialogHint.showDialogWithTwoButton(this,R.string.sellOrder_iShow_affirm_hint,this);
            }else {
                requestPay(orderNumber,isIShow);
            }
        }

        //支付确认
        private void requestPay(String orderNo, final int orderType){
            PayAffirmRequest request = new PayAffirmRequest(orderNo,orderType);
            RetrofitClient.getInstances().requestPayAffirm(request).enqueue(new UICallBack<BaseBenTwo>() {
                @Override
                public void OnRequestFail(String msg) {
                    if (isTop)checkNet();
                }

                @Override
                public void OnRequestSuccess(BaseBenTwo bean) {
                    if (isTop){
                        switch (bean.getReturnValue()){
                            case 1:
                                if (orderType==1){
                                    showToast("接单成功!");
                                }else {
                                    showToast(R.string.affirm_ok);
                                }
                                getSell(mPageIndex);
                                break;
                            default:
                                showToast(bean.getErrMsg());
                                break;
                        }
                    }
                }
            });
        }


        //物流发货的点击事件
        @Override
        public void onShipmentsClickListener(int position, String orderNumber) {
            Intent intent = new Intent(this,AffirmShipmentsActivity.class);
            intent.putExtra("orderNumber",orderNumber);
            startActivity(intent);
        }

        //查看物流的点击事件
        @Override
        public void onAddressClickListener(int position, String orderNumber) {
            //CommonWebActivity.startWebActivity(this,"物流详情","http://121.43.59.111:9005/suiwap/mobile/Kdniao/"+orderNumber);
            mActivityUtils.startActivity(LogisticsLookOver.class,"orderNumber",orderNumber);
        }

        //弹窗确认的点击事件
        @Override
        public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
            requestPay(SellOrderNumber,mIsIShow);
            dialog.dismiss();
        }

        //弹窗取消的点击事件
        @Override
        public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
            dialog.dismiss();
        }
    }
