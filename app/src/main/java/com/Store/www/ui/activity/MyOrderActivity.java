package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
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
import com.Store.www.entity.CancelOrderRequest;
import com.Store.www.entity.ConfirmOrderRequest;
import com.Store.www.entity.DeleteOrderRequest;
import com.Store.www.entity.MyOrderResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.MyOrderAdapter;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;

/**
 * 我的订单界面
 */
public class MyOrderActivity extends BaseToolbarActivity implements TabLayout.OnTabSelectedListener,
        MyOrderAdapter.OnOrderButtonClickListener,DialogHint.OnDialogTwoButtonClickListener,OnRefreshListener,OnLoadMoreListener{
    @BindView(R.id.tab_order)
    TabLayout mTabOrder;
    @BindView(R.id.ry_order)
    LRecyclerView mRyOrder;
    @BindView(R.id.nodata)
    RelativeLayout mNodata;

    LRecyclerViewAdapter mLrAdapter;
    MyOrderAdapter mAdapter;
    private int mTabPosition;
    private int mStatus= 101; //初始化第一个tab的状态为全部
    private String mOrderNumber,OrderNumber,mType;
    private String mStyle;
    private int mPosition;  //当前位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.order_management);
        mStyle = getIntent().getStringExtra("style");
        mAdapter = new MyOrderAdapter(this,this);
        mLrAdapter = new LRecyclerViewAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRyOrder.setLayoutManager(manager);
        mRyOrder.setAdapter(mLrAdapter);
        mRyOrder.setOnRefreshListener(this); //下拉刷新
        mRyOrder.setOnLoadMoreListener(this);  //上拉加载
        mRyOrder.setHeaderViewColor(R.color.redColorBackground,R.color.textColorBlack,R.color.colorLucency);
        mRyOrder.setFooterViewColor(R.color.redColorBackground,R.color.textColorBlack,R.color.colorLucency);
        mRyOrder.setFooterViewHint("正在加载","别扯了.到底了","网络没了..");
        initTab();
    }

    private void initTab(){
        mTabOrder.addTab(mTabOrder.newTab().setText(R.string.all_order));
        mTabOrder.addTab(mTabOrder.newTab().setText(R.string.stay_pay));
        mTabOrder.addTab(mTabOrder.newTab().setText(R.string.stay_shipments));
        mTabOrder.addTab(mTabOrder.newTab().setText(R.string.shipments_loading));
        mTabOrder.addTab(mTabOrder.newTab().setText(R.string.order_over));
        mTabOrder.addOnTabSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        LogUtils.d("onResume");
        if (mStyle!=null){
            if (mStyle.equals("stayPay")){ //待付款
                mTabOrder.getTabAt(1).select();
            }else if (mStyle.equals("stayShipments")){ //待发货
                mTabOrder.getTabAt(2).select();
            }else if (mStyle.equals("stayLoading")){ //发货中
                mTabOrder.getTabAt(3).select();
            }else if (mStyle.equals("stayOver")){  //已完成
                mTabOrder.getTabAt(4).select();
            }
        }else {
            getOrderList(mPageIndex);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    //获取我的订单
    private void getOrderList(int pageIndex){
        if (pageIndex==1){
            DialogLoading.shows(mContext,"加载中...");
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        RetrofitClient.getInstancesTest().getOrderList(mUserId,mStatus,mCountPerPage,pageIndex).enqueue(new UICallBack<MyOrderResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }
            @Override
            public void OnRequestSuccess(MyOrderResponse bean) {
                if (isTop) {
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData().size()<1){
                                mNodata.setVisibility(View.VISIBLE);
                            }else {
                           /* LogUtils.d("获取订单时长度==="+bean.getData().size());
                            LogUtils.d("当前位置=="+mPosition);*/
                                mNodata.setVisibility(View.GONE);
                                mRyOrder.setNoMore(false);  //获取订单后设置全部加载完为否，否则会有BUG
                                mAdapter.addAll(bean.getData());
                                mAdapter.notifyDataSetChanged();
                                mRyOrder.refreshComplete(mCountPerPage);
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

    //获取更多订单、因为没有最后一页的参数
    private void getMoreOrderList(){
        RetrofitClient.getInstancesTest().getOrderList(mUserId,mStatus,mCountPerPage,mPageIndex).enqueue(new UICallBack<MyOrderResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(MyOrderResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData().size()<1){
                                LogUtils.d("加载更多时长度==="+bean.getData().size());
                                mRyOrder.setNoMore(true);  //加载到最后一条的时候设置加载完为 是
                                mPageIndex = 1;
                            }else {
                                mAdapter.addAll(bean.getData());
                                mRyOrder.refreshComplete(mCountPerPage);
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
        LogUtils.d("被选中的TAB=="+mTabPosition);
        mAdapter.setTabPosition(mTabPosition);
        switch (mTabPosition){
            case 0:
                mStatus = 101;
                mPageIndex = 1;
                mRyOrder.setLoadMoreEnabled(true);
                LogUtils.d("index="+mPageIndex);
                LogUtils.d("mStatus0"+mStatus);
                getOrderList(mPageIndex);
                break;
            case 1:
                mStatus = 3;
                mPageIndex = 1;
                LogUtils.d("mStatus1"+mStatus);
                getOrderList(mPageIndex);
                break;
            case 2:
                mStatus = 4;
                mPageIndex = 1;
                LogUtils.d("mStatus2"+mStatus);
                getOrderList(mPageIndex);
                break;
            case 3:
                mStatus =1;
                LogUtils.d("mStatus3"+mStatus);
                getOrderList(mPageIndex);
                break;
            case 4:
                mStatus = 2;
                mPageIndex = 1;
                LogUtils.d("mStatus4"+mStatus);
                getOrderList(mPageIndex);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //Item的点击事件 查看订单详情
    @Override
    public void getItemIdClickListener(int position, String orderNo,String currency,int status) {
        Intent intent = new Intent(this,OrderDetailsActivity.class);
        intent.putExtra("currency",currency);
        intent.putExtra("orderNo",orderNo);
        intent.putExtra("status",status);
        startActivity(intent);
    }

    //查看物流的点击事件
    @Override
    public void getExpressNumberClickListener(int position, String orderNo) {
        /*CommonWebActivity.startWebActivity(this,"物流详情","http://121.43.59.111:9005/suiwap/mobile/Kdniao/"+orderNo);
        LogUtils.d("物流链接==="+"http://121.43.59.111:9005/suiwap/mobile/Kdniao/"+orderNo);*/
        mActivityUtils.startActivity(LogisticsLookOver.class,"orderNumber",orderNo);
    }

    //取消订单的点击事件
    @Override
    public void setCancelOrderClickListener(int position, String orderNumber) {
        mOrderNumber = orderNumber;
        mType = "Cancel";
        DialogHint.showDialogWithTwoButton(this,R.string.yes_order_no,this);
    }


    //取消订单
    private void requestCancelOrder(String orderNumber,String userId){
        CancelOrderRequest request = new CancelOrderRequest(orderNumber,userId);
        RetrofitClient.getInstancesTest().requestCancelMyOrder(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        mPageIndex = 1;
                        getOrderList(mPageIndex);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //支付订单的点击事件
    @Override
    public void setPayOrderClickListener(int position,String name,String phone,String address,String number,String typeName,
                                         int count,int money,String currency,long createTime,int isUseBalance,int useBalance) {
        Intent intent = new Intent(this,PayActivity.class);
        intent.putExtra("orderName",name);  //收件人
        intent.putExtra("orderPhone",phone);  //电话
        intent.putExtra("orderAddress",address);       //地址
        intent.putExtra("oderNumber",number);      //订单编号
        intent.putExtra("oderType",typeName);       //订单类型
        intent.putExtra("orderCount",count);        //订单数量
        intent.putExtra("orderMoney",money);        //订单金额
        intent.putExtra("currency",currency);       //货币符号
        intent.putExtra("orderTime",createTime);  //下单时间
        intent.putExtra("isUseBalance",isUseBalance);  //是否使用过余额
        intent.putExtra("useBalance",useBalance);  //用过的余额
        startActivity(intent);
    }

    //删除订单的点击事件
    @Override
    public void setDeleteClickListener(int position, String orderNo) {
        OrderNumber = orderNo;
        mType = "delete";
        LogUtils.d("当前位置=="+position);
        DialogHint.showDialogWithTwoButton(this,R.string.yes_delete_order,this);
    }

    //确认收货的点击事件
    @Override
    public void setConfirmOrderClickListener(int position, String orderNo) {
        OrderNumber = orderNo;
        mType = "confirm";
        DialogHint.showDialogWithTwoButton(this,R.string.yes_confirm_order,this);
    }

    //删除订单
    private void requestDeleteOrder(String userId,String OrderNumber){
        DeleteOrderRequest request = new DeleteOrderRequest(userId,OrderNumber);
        RetrofitClient.getInstancesTest().requestDeleteOrder(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.delete_ok);
                        onRefresh(); //刷新界面
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //确认收货
    private void requestConfirm(int userId,String orderNumber){
        ConfirmOrderRequest request = new ConfirmOrderRequest(userId,orderNumber);
        RetrofitClient.getInstancesTest().requestConfirm(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.affirm_ok);
                        onRefresh();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //弹窗确定的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        if (mType.equals("Cancel")){  //取消订单
            requestCancelOrder(mOrderNumber,userId);
        }else if (mType.equals("delete")){  //删除订单
            requestDeleteOrder(userId,OrderNumber);
        }else if (mType.equals("confirm")){  //确认收货
            requestConfirm(mUserId,OrderNumber);
        }
        dialog.dismiss();
    }

    //弹窗取消的点击事件
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        LogUtils.d("上拉加载");
        LogUtils.d("index=="+mPageIndex);
        mPageIndex++;
        getMoreOrderList();
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        getOrderList(mPageIndex);
    }
}
