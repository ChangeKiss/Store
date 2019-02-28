package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.OrderFundRollBackResponse;
import com.Store.www.entity.WhetherPasswordRequest;
import com.Store.www.entity.WithdrawOrderBackRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.FundOrderRollBackAdapter;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.ui.costomPassword.PopEnterPassword;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 资金订单回退界面
 */
public class FundOrderRollBackActivity extends BaseToolbarActivity implements FundOrderRollBackAdapter.OnClickListener,
        DialogHint.OnDialogTwoButtonClickListener{
    @BindView(R.id.rv_fund_order)
    LRecyclerView mRvFundOrder;  //订单回退列表
    @BindView(R.id.layout_noData)
    RelativeLayout mLayoutNoData;  //无数据提示
    @BindView(R.id.tv_apply_money)
    TextView mTvApplyMoney;     //申请金额
    @BindView(R.id.tv_select_money)
    TextView mTvSelectMoney;   //已选金额
    @BindView(R.id.btn_apply_money)
    Button mBtnApplyMoney;   //申请资金按钮

    FundOrderRollBackAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    CommonHeader mCommHeader;  //头布局
    TextView mTvOrderHint;
    private int mApplyMoney;  //申请金额
    private String mPassword;  //用户密码
    private List<Integer> orderIdList = new ArrayList<>();  //提现订单列表
    private PopEnterPassword popEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_fund_order_roll_back;
    }

    @Override
    public void initView() {
        initToolbar(this, true, R.string.order_rollback);
        ActivityCollector.addActivity(this);
        mApplyMoney = getIntent().getIntExtra("applyMoney",0);
        mTvApplyMoney.setText("申请金额: "+ActivityUtils.changeMoneys(mApplyMoney));
        initHead();
        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("withdraw");  //注册密码输入完成时用的广播
        intentFilter.addAction("alterPassword");  //注册找回密码时用的广播
        intentFilter.addAction("close");  //关闭密码输入框的广播

    }


    //注册广播并接收
    class NetWorks extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){  //如果广播返回的信息不为空  并且广播是有效的
                 if (intent.getAction().equals("withdraw")){  //如果返回的Action是 密码输入框那边来的
                    mPassword = intent.getStringExtra("password");  //获取密码
                    //发起提现请求
                    //LogUtils.d("密码=="+mPassword);
                    //将密码加盐  发起提现请求
                    requestWithdrawOrder(userId,ActivityUtils.Md5Password(mPassword), (int) mApplyMoney,orderIdList);
                }else if (intent.getAction().equals("alterPassword")&&intent.getStringExtra("password").equals("alter")){
                    LogUtils.d("找回密码");
                    mActivityUtils.startActivity(AlterPayPasswordActivity.class);
                }else if (intent.getAction().equals("close")){
                    mBtnApplyMoney.setEnabled(true);
                }
            }
        }
    }


    //初始化头布局
    private void initHead() {
        mCommHeader = new CommonHeader(mContext, R.layout.fund_order_rollback_head);
        mTvOrderHint =  mCommHeader.findViewById(R.id.fund_order_hint);
        initAdapter();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popEnterPassword = null;
    }

    //初始化适配器
    private void initAdapter() {
        mAdapter = new FundOrderRollBackAdapter(this,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(mCommHeader);
        mRvFundOrder.setLayoutManager(new LinearLayoutManager(this));
        mRvFundOrder.setAdapter(viewAdapter);
        mRvFundOrder.setPullRefreshEnabled(false);
        getFundOrder(mUserId, mCountPerPage, mPageIndex);
    }

    //获取资金回退订单
    private void getFundOrder(int agentId, int countPerPage, int pageIndex) {
        RetrofitClient.getInstances().getFundOrderRollBack(agentId, countPerPage, pageIndex).enqueue(new UICallBack<OrderFundRollBackResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(OrderFundRollBackResponse bean) {
                if (isTop) {
                    switch (bean.getReturnValue()) {
                        case 1:
                            mTvOrderHint.setText("请选择您要退款的订单，如果退款的订单金额大于申请金额,余下订单可以在下次申请时使用。");  //顶部轮播滚动文字
                            LogUtils.d("size==" + bean.getData().getList().size());
                            if (bean.getData().getList().size() > 0) {
                                mLayoutNoData.setVisibility(View.GONE);
                                mRvFundOrder.setVisibility(View.VISIBLE);
                                mAdapter.setApplyMoney((int) mApplyMoney);
                                mAdapter.setSelectMoney(0);
                                mAdapter.addAll(bean.getData().getList());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mLayoutNoData.setVisibility(View.VISIBLE);
                                mRvFundOrder.setVisibility(View.INVISIBLE);
                            }
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            mLayoutNoData.setVisibility(View.VISIBLE);
                            mRvFundOrder.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }
        });
    }

    @OnClick({R.id.btn_apply_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_money:
                for (int i=0;i<mAdapter.getDataList().size();i++){
                    if (mAdapter.getDataList().get(i).isCheck()){
                        orderIdList.add(mAdapter.getDataList().get(i).getId());
                    }
                }
                if (orderIdList.size()==0){
                    showToast("已选金额必须大于等于申请金额");
                    return;
                }
                //requestWithdrawOrder(userId);
                getWhetherPassword();
                break;
        }
    }

    //判断用户是否有设置过支付密码
    private void getWhetherPassword(){
        WhetherPasswordRequest request = new WhetherPasswordRequest(mUserId);
        RetrofitClient.getInstances().requestPassword(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:  //有密码 弹出密码输入框
                        popEnterPassword = new PopEnterPassword(FundOrderRollBackActivity.this);
                        // 显示窗口
                        popEnterPassword.showAtLocation(FundOrderRollBackActivity.this.findViewById(R.id.layout_fund_order),
                                Gravity.BOTTOM | Gravity.BOTTOM, 0, 0); // 设置layout在PopupWindow中显示的位置
                        //delayButtonEnabled();  //延时设置按钮可点击
                        break;
                    case 8:  //没支付密码 ， 弹窗询问是否设置
                        //delayButtonEnabled();  //延时设置按钮可点击
                        DialogHint.showDialogWithTwoButton(mContext,R.string.no_pay_password,FundOrderRollBackActivity.this);
                        break;
                }
            }
        });
    }


    //提现订单回退
    private void requestWithdrawOrder(String agentId,String password,int money,List<Integer> orderList){
        WithdrawOrderBackRequest request = new WithdrawOrderBackRequest(agentId,password,money,orderList);
        RetrofitClient.getInstances().requestWithdrawOrderBack(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    mBtnApplyMoney.setEnabled(true);
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            showToast(R.string.agent_loding);
                            finish();
                            Intent intent = new Intent();
                            intent.setAction("applyOver");
                            sendBroadcast(intent);  //发送一个提现完成的广播 关闭前面的界面
                            break;
                        default:
                            mBtnApplyMoney.setEnabled(true);
                            delayShowPassword();
                            break;
                    }
                }
            }
        });
    }


    //延迟弹出支付密码框
    private void delayShowPassword(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PopEnterPassword popEnterPassword = new PopEnterPassword(FundOrderRollBackActivity.this);
                // 显示窗口
                popEnterPassword.showAtLocation(FundOrderRollBackActivity.this.findViewById(R.id.layoutContent),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
            }
        },200);
    }

    //选中时
    @Override
    public void CheckClickListener(int position, boolean isCheck, boolean select) {
        int money = 0;
        for (int i=0;i<mAdapter.getDataList().size();i++){
            if (mAdapter.getDataList().get(i).isCheck()){
                money+=mAdapter.getDataList().get(i).getSurplusMoney();
            }
        }
        if (!select){
            showToast("已选金额已足够");
        }
        mAdapter.setSelectMoney(money);
        mTvSelectMoney.setText("已选金额: "+ActivityUtils.changeMoneys(money));

    }

    //弹窗确定的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        dialog.dismiss();
        mActivityUtils.startActivity(SettingPayPasswordActivity.class,"type","setting");
    }
    //弹窗取消的点击事件
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }
}
