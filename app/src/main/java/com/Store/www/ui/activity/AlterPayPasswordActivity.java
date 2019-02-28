package com.Store.www.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AlterPayPasswordRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.CountDownTimerUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改支付密码界面
 */
public class AlterPayPasswordActivity extends BaseToolbarActivity implements TextWatcher{
    @BindView(R.id.et_alter_new_password)
    EditText mEtAlterNewPassword;  //新密码
    @BindView(R.id.et_again_input_password)
    EditText mEtAgainInputPassword;  //再次输入密码
    @BindView(R.id.et_alter_phone)
    EditText mEtAlterPhone;  //手机号码
    @BindView(R.id.et_alter_verify_code)
    EditText mEtAlterVerifyCode;  //验证码
    @BindView(R.id.btn_pay_verify_code)
    Button mBtnPayVerifyCode;  //获取验证码按钮

    //两个布尔值用来判断两种密码验证方式
    private boolean yes = false;
    private boolean Alter = true;
    private String mNewPassword,mAgainPassword,mPhone,mVerifyCode;  //新密码  重复密码  手机号码  验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_alter_pay_password;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.alters_pay_password);
        mEtAlterNewPassword.addTextChangedListener(this);
        mEtAgainInputPassword.addTextChangedListener(this);
        mEtAlterPhone.addTextChangedListener(this);
        mEtAlterVerifyCode.addTextChangedListener(this);
    }

    //点击事件
    @OnClick({R.id.btn_pay_verify_code, R.id.btn_confirm_alter_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pay_verify_code:  //获取验证码
                if (TextUtils.isEmpty(mPhone)){
                    showToast(R.string.phone_number_null);
                    return;
                }
                if (mPhone.length()<11){
                    showToast(R.string.phone_number_no);
                    return;
                }
                getPayVerifyCode(mPhone);
                break;
            case R.id.btn_confirm_alter_password:  //修改密码
                yes = false;
                Alter = true;
                if (mNewPassword.length()<6){
                    showToast("密码长度不足");
                }else if (!compareValue(mNewPassword)){
                    showToast("不能设置六位相同的数字");
                }else if (compareValues(mNewPassword)){
                    showToast("不能设置六位连续的数字");
                }else if (!mNewPassword.equals(mAgainPassword)){
                    showToast("两次输入的密码不一致");
                }else {
                    requestAlterPayPassword(mPhone, ActivityUtils.Md5Password(mNewPassword),mVerifyCode);  //将密码加密
                }
                break;
        }
    }


    //获取验证码
    private void getPayVerifyCode(String phone){
        RetrofitClient.getInstances().getVerifyCode(phone).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.verify_code_ok);
                        CountDownTimerUtils timerUtils = new CountDownTimerUtils(mBtnPayVerifyCode,60000,1000);
                        timerUtils.start();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //修改支付密码
    private void requestAlterPayPassword(String mobilephone,String password,String code){
        AlterPayPasswordRequest request = new AlterPayPasswordRequest(mobilephone,password,code);
        RetrofitClient.getInstances().requestAlterPayPassword(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.change_ok);
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //第一种判断密码方法  判断是否是6位相同的密码
    private boolean compareValue(String password){
        for (int i=0; i<password.length()-1;i++){
            LogUtils.d("第"+i+"次比较的值="+password.charAt(i));
            if (password.charAt(i)==password.charAt(i+1)){
                yes = false;
                LogUtils.d("第一种判断失败");
            }else {
                LogUtils.d("第一种判断成功");
                yes = true;  //第一种比较成功
            }
        }
        return yes;
    }

    //第二种判断密码方法  判断是否是连续的六位密码
    private boolean compareValues(String password){
        if (password.charAt(0)-password.charAt(1)==1||password.charAt(1)-password.charAt(0)==1){
            if (password.charAt(0)-password.charAt(1)==1){
                for (int i=0;i<password.length()-1;i++){
                    LogUtils.d("第二种判断方法升序第"+i+"次比较的值="+password.charAt(i));
                    if (password.charAt(i)-password.charAt(i+1)!=1){
                        //修改密码
                        LogUtils.d("第二种升序判断成功");
                        Alter = false;
                    }
                }
            }
            if (password.charAt(1)-password.charAt(0)==1){
                for (int i=0;i<password.length()-1;i++){
                    LogUtils.d("第二种判断方法降序第"+i+"次比较的值="+password.charAt(i));
                    if (password.charAt(i+1)-password.charAt(i)!=1){
                        //修改密码
                        LogUtils.d("第二种降序判断成功");
                        Alter = false;
                    }
                }
            }
        }else {
            Alter = false;
        }
        return Alter;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入框监听
    @Override
    public void afterTextChanged(Editable s) {
        mNewPassword = mEtAlterNewPassword.getText().toString();
        mAgainPassword = mEtAgainInputPassword.getText().toString();
        mPhone = mEtAlterPhone.getText().toString();
        mVerifyCode = mEtAlterVerifyCode.getText().toString();
    }
}
