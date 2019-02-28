package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.CMBPayUrlResponse;
import com.Store.www.entity.CancelOrderRequest;
import com.Store.www.entity.PayExpressRequest;
import com.Store.www.entity.PickUpGoodsResponse;
import com.Store.www.entity.QueryOutSubordinateResponse;
import com.Store.www.net.ExceptionHandle;
import com.Store.www.net.LongConnectionCallBack;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.PickUpGoodsAdapter;
import com.Store.www.ui.commom.DialogConnectTimeOut;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 提货单界面
 */
public class PickUpGoodsActivity extends BaseToolbarActivity implements TabLayout.OnTabSelectedListener,
        PickUpGoodsAdapter.OnButtonClickListener,DialogHint.OnDialogTwoButtonClickListener,OnRefreshListener,OnLoadMoreListener,
        DialogConnectTimeOut.TimeOutDialogButtonClickListener {
    @BindView(R.id.tab_pick_goods)
    TabLayout mTabPickGoods;
    @BindView(R.id.ry_pick_goods)
    LRecyclerView mRy;  //提货单列表
    @BindView(R.id.layout_add_pick_up)
    LinearLayout mLayoutAddPickUp;  //新增提货单布局
    @BindView(R.id.layout_no_data)
    LinearLayout mRlNoData;

    LinearLayout.LayoutParams params;
    PickUpGoodsAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    private String mOrderNumber,UserId;
    private int mTabListener; //顶部tab的下标
    private int mStatus= 101; //初始化第一个tab的状态为全部
    private NetWorks netWorks;
    private int mPayType =0;  //运费支付类型 0是H5支付 1 调用招行APP 支付
    private AlertDialog mDialog;  //提货选择弹窗
    private String mInputAgentNumber;  //输入的代理编号
    private CircleImageView mCivSelectAgentHead;  //头像布局
    private TextView mTvNext;  //下一步
    private TextView mTvSelectAgentName;  //代理姓名
    private TextView mTvSelectAgentNumber; //代理编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_pick_up_goods;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, "我的提货单");
        mAdapter = new PickUpGoodsAdapter(this,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(viewAdapter);
        mRy.setOnRefreshListener(this);
        mRy.setOnLoadMoreListener(this);
        mRy.setHeaderViewColor(R.color.redColorBackground, R.color.textColorBlack, R.color.colorLucency);
        mRy.setFooterViewColor(R.color.redColorBackground, R.color.textColorBlack, R.color.colorLucency);
        mRy.setFooterViewHint("正在加载", "别扯了.到底了", "网络没了..");
        initTab();
        setView();
        IntentFilter filter = new IntentFilter();
        filter.addAction("pickup");
        filter.addAction("payExpressOver");
        netWorks = new NetWorks();
        registerReceiver(netWorks,filter);  //注册广播
    }

    //连接超时取消点击事件
    @Override
    public void OnCancelClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }

    //连接超时再次请求点击事件
    @Override
    public void OnDismissClickListener(AlertDialog dialog) {
        dialog.dismiss();
        getPickUpGoods(mPageIndex);
    }

    //注册广播接收器并处理相关事务
    class NetWorks extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            /*if (networkInfo!=null && networkInfo.isAvailable()){
                onRefresh();
            }*/
        }
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
        onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        netWorks = null;
    }

    //设置无数据布局的高
    private void setView(){
        params = (LinearLayout.LayoutParams) mRlNoData.getLayoutParams();
        if (Build.VERSION.SDK_INT>=25){  //如果系统版本是7.0 及以上
            params.height = UserPrefs.getInstance().getHeight()-552;
        }else {
            params.height = UserPrefs.getInstance().getHeight()-492;
        }
        LogUtils.d("设置的高=="+params.height);
        mRlNoData.setLayoutParams(params);
    }

    //初始化tab
    private void initTab() {
        mTabPickGoods.addTab(mTabPickGoods.newTab().setText(R.string.all_order));
        mTabPickGoods.addTab(mTabPickGoods.newTab().setText(R.string.stay_shipments));
        mTabPickGoods.addTab(mTabPickGoods.newTab().setText(R.string.shipments_loading));
        mTabPickGoods.addTab(mTabPickGoods.newTab().setText(R.string.order_over));
        mTabPickGoods.addOnTabSelectedListener(this);
    }

    //获取提货单数据
    private void getPickUpGoods(int pageIndex){
        if (pageIndex==1){
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
            DialogLoading.shows(mContext,"加载中...");
        }
        RetrofitClient.getApiLongConnection().getLading(mUserId,mCountPerPage,pageIndex,mStatus).enqueue(new LongConnectionCallBack<PickUpGoodsResponse>() {
            @Override
            public void OnRequestFail(int code) {
                if (isTop)checkNet();
            }

            @Override
            public void OnError(ExceptionHandle.ResponeThrowable throwable) {
                if (isTop){
                    if (throwable.code == ExceptionHandle.ERROR.TIMEOUT_ERROR){
                            DialogConnectTimeOut.ShowTimeOutDialog(PickUpGoodsActivity.this,PickUpGoodsActivity.this);
                    }
                }
            }

            @Override
            public void OnRequestSuccess(PickUpGoodsResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            //LogUtils.d("提货单数据为"+bean.getData().size());
                            if (bean.getData().size()>0){  //请求下来有数据
                                mRlNoData.setVisibility(View.GONE);  //无数据的提示界面隐藏起来
                                mLayoutAddPickUp.setVisibility(View.VISIBLE);
                                if (bean.getData().size()==0){
                                    mRy.setNoMore(true);
                                }else {
                                    LogUtils.d("数据长度="+bean.getData().size());
                                    mAdapter.addAll(bean.getData());
                                    mRy.refreshComplete(mCountPerPage);//加载完成时
                                    mAdapter.notifyDataSetChanged();
                                }
                            }else {
                                mRlNoData.setVisibility(View.VISIBLE);
                                mLayoutAddPickUp.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    default:
                        if (isTop)showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //上拉加载更多的时候调这个方法
    private void loadMorePickUp(int pageIndex){
        RetrofitClient.getInstances().getLading(mUserId,mCountPerPage,pageIndex,mStatus).enqueue(new UICallBack<PickUpGoodsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(PickUpGoodsResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            LogUtils.d("提货单数据为"+bean.getData().size());
                            mRlNoData.setVisibility(View.GONE);  //无数据的提示界面隐藏起来
                            mLayoutAddPickUp.setVisibility(View.VISIBLE);
                            if (bean.getData().size()==0){
                                mRy.setNoMore(true);
                            }else {
                                LogUtils.d("数据长度="+bean.getData().size());
                                mAdapter.addAll(bean.getData());
                                mRy.refreshComplete(mCountPerPage);//加载完成时
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    default:
                        if (isTop)showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }


    //顶部TAB的监听
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mTabListener = tab.getPosition();
        mAdapter.setmTabNumber(mTabListener);
        switch (mTabListener){
            case 0:
                mStatus=101;
                getPickUpGoods(mPageIndex);
                break;
            case 1:
                mStatus = 0;
                mPageIndex =1;
                getPickUpGoods(mPageIndex);
                break;
            case 2:
                mStatus = 1;
                mPageIndex =1;
                getPickUpGoods(mPageIndex);
                break;
            case 3:
                mStatus = 5;
                mPageIndex =1;
                getPickUpGoods(mPageIndex);
                break;
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //增加提货单的点击事件
    @OnClick(R.id.layout_add_pick_up)
    public void onViewClicked() {
        mActivityUtils.startActivity(NewLadingActivity.class,"pickUpType","OneSelf");
        //showDialog();
    }

    //弹出弹窗
    private void showDialog(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_puckup_mode,null);
        LinearLayout mLayoutSelectPickUpMode = view.findViewById(R.id.layout_select_pickup_mode);  //选择提货方式布局
        TextView mTvPickUpOneSelf = view.findViewById(R.id.tv_pickup_oneself);  //直接给自己提货
        TextView mTvCloudShift = view.findViewById(R.id.tv_cloud_shift);   //云库转移
        LinearLayout mLayoutSelectAgentContent = view.findViewById(R.id.layout_select_agent_content);  //选择下级代理的信息布局
        EditText mEtInputAgentNumber = view.findViewById(R.id.et_input_agent_number);  //代理编号输入框
        TextView mTvQuerySelectAgent = view.findViewById(R.id.tv_query_select_agent);  //查询按钮
        mCivSelectAgentHead = view.findViewById(R.id.civ_select_agent_head);  //头像
        mTvSelectAgentName = view.findViewById(R.id.tv_select_agent_name);  //代理姓名
        mTvSelectAgentNumber = view.findViewById(R.id.tv_select_agent_number);  //代理编号
        mTvNext = view.findViewById(R.id.tv_next);  //下一步
        TextView mTvCancelSelect = view.findViewById(R.id.tv_cancel_select);  //取消
        mDialog = new AlertDialog.Builder(mContext).setView(view).create();
        mDialog.setCancelable(false);
        mDialog.show();
        mEtInputAgentNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mInputAgentNumber = mEtInputAgentNumber.getText().toString().trim();
            }
        });
        mTvCloudShift.setOnClickListener(v -> {  //云库转移按钮
            mLayoutSelectPickUpMode.setVisibility(View.GONE);
            mLayoutSelectAgentContent.setVisibility(View.VISIBLE);
        });
        mTvPickUpOneSelf.setOnClickListener(v ->  { //给自己提货
            mActivityUtils.startActivity(NewLadingActivity.class,"pickUpType","OneSelf");
            mDialog.dismiss();
        });
        mTvQuerySelectAgent.setOnClickListener(v -> {  //查询点击事件
            if (TextUtils.isEmpty(mInputAgentNumber)){
                showToast("请输入完整的代理编号");
                return;
            }
            getQueryAgent(mInputAgentNumber);
        });
        mTvNext.setOnClickListener(v ->{ //云库转移下一步点击事件
            Intent intent = new Intent(this,NewLadingActivity.class);
            intent.putExtra("pickUpType","Subordinate");
            intent.putExtra("agentNumber",mInputAgentNumber);
            startActivity(intent);
            mDialog.dismiss();
        });
        mTvCancelSelect.setOnClickListener(v -> {  //取消点击事件
            mDialog.dismiss();
        });
    }


    //查询出库下级代理信息
    private void getQueryAgent(String agentCode){
        RetrofitClient.getInstances().getOutSubordinate(agentCode).enqueue(new UICallBack<QueryOutSubordinateResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(QueryOutSubordinateResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){  //查询成功
                        case 1:
                            if (!TextUtils.isEmpty(bean.getData().getHeadPicture()))Glide.with(mContext).
                                    load(bean.getData().
                                            getHeadPicture()).
                                    error(R.mipmap.default_head).
                                    into(mCivSelectAgentHead);
                            mTvSelectAgentName.setText("代理姓名: "+bean.getData().getName());
                            mTvSelectAgentNumber.setText("代理编号: "+bean.getData().getAgentCode());
                            mInputAgentNumber = bean.getData().getAgentCode();  //代理编号
                            mTvNext.setEnabled(true);
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //查看物流的点击事件
    @Override
    public void OnButtonClickListener(int position, String orderNumber) {
        //CommonWebActivity.startWebActivity(this,"物流详情","http://121.43.59.111:9005/suiwap/mobile/Kdniao/"+orderNumber);
        mActivityUtils.startActivity(LogisticsLookOver.class,"orderNumber",orderNumber);  //查看物流
    }

    //取消订单的点击事件
    @Override
    public void OnCancelOrderClickListener(int position, String orderNumber,int userIdInt) {
        mOrderNumber = orderNumber;
        UserId = String.valueOf(userIdInt);
        DialogHint.showDialogWithTwoButton(this,R.string.yes_order_no,this);
    }

    //Item的点击事件
    @Override
    public void OnItemClickListener(int position, String orderNumber) {
        mActivityUtils.startActivity(PickUpDetailsActivity.class,"orderNumber",orderNumber);
    }

    //支付运费点击事件
    @Override
    public void OnPayExpressClickListener(int position, String orderNumber) {
        if (ActivityUtils.isCMBAppInstalled(mContext)){  //判断是否安装APP
            mPayType = 1;  //装了
            requestPayExpress(orderNumber,mPayType);
        }else {
            mPayType = 0;  //没装
            requestPayExpress(orderNumber,mPayType);
        }
    }


    //发起支付运费请求
    private void requestPayExpress(String orderNo,int payType){
        PayExpressRequest request = new PayExpressRequest(orderNo,payType);
        RetrofitClient.getInstances().requestPayExpress(request).enqueue(new UICallBack<CMBPayUrlResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }
            @Override
            public void OnRequestSuccess(CMBPayUrlResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData()!=null){
                                if (payType==0){
                                    Intent intent = new Intent(PickUpGoodsActivity.this,CommonWebActivity.class);
                                    intent.putExtra("url",bean.getData());
                                    intent.putExtra("type","HTML");
                                    intent.putExtra("title","一网通支付");
                                    startActivity(intent);
                                }else if (payType ==1){
                                    final String payUrl = "cmbmobilebank://CMBLS/FunctionJump?action=gofuncid&funcid=200007&serverid=CMBEUserPay&requesttype=post&cmb_app_trans_parms_start=here&charset=utf-8&jsonRequestData="+
                                            bean.getData();
                                    LogUtils.d("支付链接=="+payUrl);
                                    Intent intent = new Intent();
                                    Uri uri = Uri.parse(payUrl);
                                    intent.setData(uri);
                                    intent.setAction("android.intent.action.VIEW");
                                    startActivity(intent);
                                }
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

    //取消订单
    private void requestCancelOrder(String orderNumber,String userId){
        CancelOrderRequest request = new CancelOrderRequest(orderNumber,userId);
        RetrofitClient.getInstances().requestCancelOrder(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop)getPickUpGoods(mPageIndex);
                        break;
                    default:
                        if (isTop)showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }



    //弹窗确认的监听
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        requestCancelOrder(mOrderNumber,UserId);
        dialog.dismiss();
        LogUtils.d("订单编号=="+mOrderNumber);
        LogUtils.d("用户id==="+String.valueOf(UserId));
    }

    //弹窗取消的监听
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex =1;
        mAdapter.getDataList().clear();
        getPickUpGoods(mPageIndex);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        loadMorePickUp(mPageIndex);
    }
}
