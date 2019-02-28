package com.Store.www.ui.activity;

import android.content.Intent;
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
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.SubmitJDPayRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 京东支付确认界面
 */

public class AffirmJDPayActivity extends BaseToolbarActivity implements TextWatcher {
    @BindView(R.id.tv_hint_affirm_pay_money)
    TextView mTvHintAffirmPayMoney;  //提示金额
    @BindView(R.id.et_verify_code_jd)
    EditText mEtVerifyCodeJd;  //京东支付验证码
    @BindView(R.id.btn_affirm_pay)
    Button mBtnAffirmPay;  //确认支付按钮

    private String JDNumber, JDCode;  //京东订单编号  京东验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_affirm_jdpay;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.pay_jd);
        JDNumber = getIntent().getStringExtra("jdNumber");
        mTvHintAffirmPayMoney.setText(ActivityUtils.changeMoneys(getIntent().getIntExtra("orderMoney", 0)) + "元");
        mEtVerifyCodeJd.addTextChangedListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        JDCode = mEtVerifyCodeJd.getText().toString();
    }

    //提交京东支付
    private void submitJDPay(String jdNumber,String jdCode){
        SubmitJDPayRequest request = new SubmitJDPayRequest(jdNumber,jdCode);
        RetrofitClient.getInstances().requestSubmitJDPay(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                mBtnAffirmPay.setEnabled(true);  //开启提交按钮点击事件
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.pay_OK);
                        Intent intent = new Intent();
                        intent.setAction("payOver");
                        sendBroadcast(intent);
                        finish();
                        break;
                    default:
                        mBtnAffirmPay.setEnabled(true);  //开启提交按钮点击事件
                        showToast(bean.getErrMsg());
                        break;
                }

            }
        });
    }

    //点击事件
    @OnClick({R.id.btn_affirm_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_affirm_pay:  //确认支付
                mBtnAffirmPay.setEnabled(false);  //关闭提交按钮点击事件
                if (TextUtils.isEmpty(JDCode)){  //如果验证码为空
                    showToast(R.string.please_verify_code);
                    mBtnAffirmPay.setEnabled(true);  //开启提交按钮点击事件
                    return;
                }
                submitJDPay(JDNumber,JDCode);
                break;
        }
    }
}
