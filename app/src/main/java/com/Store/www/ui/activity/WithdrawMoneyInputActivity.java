package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.JudgeTaiWanAgentResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 资金申请输入申请金额界面
 */
public class WithdrawMoneyInputActivity extends BaseToolbarActivity implements TextWatcher ,DialogHint.OnDialogTwoButtonClickListener{
    @BindView(R.id.et_withdraw_money)
    EditText mEtWithdrawMoney;  //提现金额输入框
    @BindView(R.id.tv_ok_withdraw_money)
    TextView mTvOkWithdrawMoney;  //显示可提现金额
    @BindView(R.id.btn_withdraw)
    Button mBtnWithdraw;   //提现按钮
    @BindView(R.id.tv_tax_hint)
    TextView mTvTaxHint;  //扣税的提示

    private String money;
    private String mType;
    private int mBalanceMoney;
    private int mMoney;
    private double mTaxMoney;  //税额
    DecimalFormat mFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_withdraw_money_input;
    }

    @Override
    public void initView() {
        initToolbar(this, true, "奖励提取");
        ActivityCollector.addActivity(this);
        mBalanceMoney = getIntent().getIntExtra("balance",0);
        mType = "default";
        mTvOkWithdrawMoney.setText("可提取金额"+ActivityUtils.changeMoneys(mBalanceMoney)+"元");
        mEtWithdrawMoney.addTextChangedListener(this);  //添加输入监听
        ActivityUtils.setPoint(mEtWithdrawMoney); //设置可输入提现金额只能包含两位小数
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("applyOver");
        //mBroadCast = new SelectFundModeActivity.BroadCast();
        registerReceiver(new BroadCast(),intentFilter);
    }

    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {

    }

    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {

    }


    //注册广播并接收
    class BroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo.isAvailable() && networkInfo!=null){
                if (intent.getAction().equals("applyOver")){
                    finish();
                }
            }
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
        mBtnWithdraw.setEnabled(true);
    }

    @OnClick({R.id.tv_all_withdraw, R.id.btn_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all_withdraw:
                mType = "all";
                mEtWithdrawMoney.setText(ActivityUtils.changeMoney(mBalanceMoney));
                break;
            case R.id.btn_withdraw:
                if (!TextUtils.isEmpty(mType)){
                    mBtnWithdraw.setEnabled(false);
                    if (!TextUtils.isEmpty(money)){

                        if (mType.equals("edit")){
                            int moneys = Math.round(Float.parseFloat(money)*100);
                            LogUtils.d("提现金额=="+moneys);
                            if (moneys <= 350){
                                mTaxMoney = 10;
                            }else {
                                mTaxMoney =  ActivityUtils.formatDouble(((moneys/100)*0.03)*100); //税额
                            }
                            //LogUtils.d("手续费=="+moneys/100*0.03);
                            mMoney = (int) moneys;
                            //LogUtils.d("扣除手续费后金额=="+(mMoney-mTaxMoney));
                        }
                        LogUtils.d("输入的金额转换为分="+mMoney);
                        if (mMoney>mBalanceMoney){
                            showToast("金额超限");
                            mBtnWithdraw.setEnabled(true);
                            return;
                        }
                        if (mMoney==0){
                            showToast("请输入有效的金额");
                            mBtnWithdraw.setEnabled(true);
                            return;
                        }
                        //LogUtils.d("money=="+mMoney);
                       //getTaiWanAgent(mUserId);
                        //LogUtils.d("不扣手续费金额=="+mMoney);
                        LogUtils.d("手续费==分"+mTaxMoney);
                        LogUtils.d("计算前金额=="+(mMoney-mTaxMoney));
                        int a = (int) Math.round( mMoney-mTaxMoney);
                        mActivityUtils.startActivity(WithdrawDepositActivity.class,"applyMoney",a); //
                        LogUtils.d("计算后金额=="+a);
                    }else {
                        mBtnWithdraw.setEnabled(true);
                        showToast("请输入有效的金额");
                    }
                }
                break;
        }
    }

    //判断是否是台湾代理
    private void getTaiWanAgent(int agentId){
        RetrofitClient.getInstances().getTaiWanAgent(agentId).enqueue(new UICallBack<JudgeTaiWanAgentResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(JudgeTaiWanAgentResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getOpen()==1){  //正常代理
                                mActivityUtils.startActivity(SelectFundModeActivity.class,"applyMoney",mMoney);
                            }else {  //台湾代理
                                mActivityUtils.startActivity(WithdrawDepositActivity.class,"applyMoney",mMoney);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mType = "edit";
        money = mEtWithdrawMoney.getText().toString();
        if (!TextUtils.isEmpty(money)){  //
            String str = money.substring(money.length()-1);money.length();  //截取出字符串最后一位
                if (!money.contains(".") && !str.equals(".")){  //如果输入的是整数 并且最后一位数不是小数点
                    int m = Integer.parseInt(money);
                    if (m <=3.5){
                        mTvTaxHint.setText("代扣税额: "+0.1+"元");
                    }else {
                        mTvTaxHint.setText("代扣税额: "+mFormat.format((m*0.03))+"元");
                    }
                }else if (money.contains(".")&&!str.equals(".")){  //如果是小数 并且  最后一位不是小数点
                    float m = Float.parseFloat(money);
                    if (m <=3.5){
                        mTvTaxHint.setText("代扣税额: "+0.1+"元");
                    }else {
                        mTvTaxHint.setText("代扣税额: "+mFormat.format((m*0.03))+"元");
                    }
                }else if (str.equals(".")){  //如果最后一位是小数点
                    int m = Integer.parseInt(money.substring(0,money.length()-1));
                    if (m <=3.5){
                        mTvTaxHint.setText("代扣税额: "+0.1+"元");
                    }else {
                        mTvTaxHint.setText("代扣税额: "+mFormat.format((m*0.03))+"元");
                    }
                }

        }else {
            mTvTaxHint.setText("代扣税额: 0.00元");
        }
        LogUtils.d("输入的金额=="+money);
    }
}
