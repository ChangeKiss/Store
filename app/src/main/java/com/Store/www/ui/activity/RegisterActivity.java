package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.RegisterRequest;
import com.Store.www.entity.VerifyPeopleResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.RegexUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册界面  已经作废
 */
public class RegisterActivity extends BaseToolbarActivity implements TextWatcher,DialogHint.OnDialogOneButtonClickListener {
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @BindView(R.id.et_user_password)
    EditText mEtUserPassword;
    @BindView(R.id.et_user_password_two)
    EditText mEtUserPasswordTwo;
    @BindView(R.id.et_user_referrer)
    EditText mEtUserReferrer;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    private String user,mobile,introducer,password,passwordTwo,mPassword,mPasswords;
    boolean buttonEnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.register_name);
        mEtUserName.addTextChangedListener(this);
        mEtUserPhone.addTextChangedListener(this);
        mEtUserPassword.addTextChangedListener(this);
        mEtUserPasswordTwo.addTextChangedListener(this);
        mEtUserReferrer.addTextChangedListener(this);
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入文本框的监听
    @Override
    public void afterTextChanged(Editable s) {
        user = mEtUserName.getText().toString().trim();
        mobile = mEtUserPhone.getText().toString().trim();
        introducer = mEtUserReferrer.getText().toString().trim();
        mPassword = mEtUserPassword.getText().toString().trim();
        mPasswords = mEtUserPasswordTwo.getText().toString().trim();
        password = ActivityUtils.Md5Password(mEtUserPassword.getText().toString().trim());
        passwordTwo = ActivityUtils.Md5Password(mEtUserPasswordTwo.getText().toString().trim());
    }
    //按钮的点击事件
    @OnClick({R.id.btn_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code: //验证推荐人
                if (TextUtils.isEmpty(introducer)){
                    showToast(R.string.verify_null);
                    return;
                }
                getReferrerPeople();
                break;
            case R.id.btn_register: //注册
                if (RegexUtils.verifyPhoneNumber(mobile)!= RegexUtils.VERIFY_SUCCESS){
                    showToast(R.string.phone_number_no);
                    return;
                }
                if (!buttonEnable){
                    showToast(R.string.please_verify);
                    return;
                }
                if (!password.equals(passwordTwo)){
                    showToast(R.string.two_password);
                    return;
                }
                if (RegexUtils.verifyPassword(mPassword)!= RegexUtils.VERIFY_SUCCESS && RegexUtils.verifyPassword(mPasswords)!=RegexUtils.VERIFY_SUCCESS){
                    DialogHint.showDialogWithOneButton(this,R.string.dialog_password);
                    return;
                }
                requestRegister();
                break;
        }
    }


    //验证推荐人信息
    private void getReferrerPeople(){
        RetrofitClient.getInstances().getReferrer(introducer).enqueue(new UICallBack<VerifyPeopleResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(VerifyPeopleResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.verify_ok);
                        buttonEnable = introducer.equals(bean.getJsrAgent().getCode());
                        mBtnRegister.setEnabled(buttonEnable);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //发起注册请求
    private void requestRegister(){
        RegisterRequest request = new RegisterRequest(user,mobile,introducer,password);
        RetrofitClient.getInstances().requestRegister(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1: //注册成功跳转到登录界面
                        showToast(R.string.register_ok);
                        mActivityUtils.startActivity(LoginActivity.class);
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }


    //弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        dialog.dismiss();
    }

    //单个确认按钮的弹窗  标题传String
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId) {

    }
}
