package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.CodeLoginRequest;
import com.Store.www.entity.FindAlterPasswordRequest;
import com.Store.www.entity.LoginRequest;
import com.Store.www.entity.LoginResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.CountDownTimerUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.RegexUtils;
import com.Store.www.utils.UserPrefs;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 找回密码界面
 */
public class RetrievePasswordActivity extends BaseToolbarActivity implements TextWatcher,DialogHint.OnDialogOneButtonClickListener{
    @BindView(R.id.et_user_phone_two)
    EditText mEtUserPhoneTwo;
    @BindView(R.id.et_verify_code)
    EditText mEeVerityCode;
    @BindView(R.id.btn_verify_code)
    Button mBtnVerifyCode;
    @BindView(R.id.et_password_find)
    EditText mEtPasswordFin;
    @BindView(R.id.et_password_find_two)
    EditText mEtPasswordFindTwo;
    @BindView(R.id.btn_affirm)
    Button mBtnAffirm;
    @BindView(R.id.layout_password_one)
    LinearLayout mLayoutPasswordOne;  //新密码输入布局
    @BindView(R.id.line_one)
    View mLineOne;
    @BindView(R.id.layout_password_two)
    LinearLayout mLayoutPasswordTwo;  //重复密码输入布局
    @BindView(R.id.line_two)
    View mLineTwo;

    private String passwordOne,nwePassword,phoneNumber,code,mPassword;
    private boolean buttonEnable;
    private String mType = "";
    private String mDialogType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_retrieve_password;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mType = getIntent().getStringExtra("type");
        if (mType.equals("code_login")){
            initToolbar(this, true, "验证码登录");
            mBtnAffirm.setText("登录");
            mLayoutPasswordOne.setVisibility(View.GONE);
            mLineOne.setVisibility(View.GONE);
            mLayoutPasswordTwo.setVisibility(View.GONE);
            mLineTwo.setVisibility(View.GONE);
        }else if (mType.equals("retrieve_password")){
            initToolbar(this, true, R.string.find_password);
        }
        mEtUserPhoneTwo.addTextChangedListener(this);
        mEeVerityCode.addTextChangedListener(this);
        mEtPasswordFin.addTextChangedListener(this);
        mEtPasswordFindTwo.addTextChangedListener(this);

    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    //文本输入框的监听
    @Override
    public void afterTextChanged(Editable s) {
        phoneNumber =mEtUserPhoneTwo.getText().toString().trim(); //手机号
        code = mEeVerityCode.getText().toString().trim();           //验证码
        passwordOne = mActivityUtils.Md5Password(mEtPasswordFin.getText().toString().trim());      //加盐的新密码
        nwePassword = mActivityUtils.Md5Password(mEtPasswordFindTwo.getText().toString().trim());   //加盐的(新)重复密码
        mPassword = mEtPasswordFin.getText().toString();
    }

    //按钮的点击事件
    @OnClick({R.id.btn_verify_code, R.id.btn_affirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_verify_code: //获取验证码
                if (TextUtils.isEmpty(mEtUserPhoneTwo.getText().toString().trim())){
                    showToast(R.string.phone_number_null);
                    return;
                }
                //检查手机号
                if (RegexUtils.verifyPhoneNumber(mEtUserPhoneTwo.getText().toString().trim())!=RegexUtils.VERIFY_SUCCESS){
                    showToast(R.string.phone_number_no);
                    return;
                }
                //获取验证码
                getVerifyCode();

                break;
            case R.id.btn_affirm: //确认
                if (mType.equals("code_login")){  //验证码登录
                    if (TextUtils.isEmpty(code)){
                        showToast("请输入验证码");
                        return;
                    }
                    requestCodeLogin(phoneNumber,code);
                }else if (mType.equals("retrieve_password")){
                    if (RegexUtils.verifyPassword(mPassword)!= RegexUtils.VERIFY_SUCCESS){
                        DialogHint.showDialogWithOneButton(this,R.string.dialog_password);
                        mDialogType = "password_error";
                        return;
                    }
                    if (!passwordOne.equals(nwePassword)){
                        showToast(R.string.two_password);
                        break;
                    }
                    requestFindAlter();
                }
                break;
        }
    }


    /**
     * 发起验证码登录
     */
    private void requestCodeLogin(String phone,String code){
        CodeLoginRequest request = new CodeLoginRequest(phone,code);
        RetrofitClient.getInstances().requestCodeLogin(request).enqueue(new UICallBack<LoginResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(LoginResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            showToast(R.string.login_ok);
                            String model=android.os.Build.MODEL; // 手机型号
                            String release=android.os.Build.VERSION.RELEASE; // android系统版本号
                            //JPushInterface.setAlias(mContext,1,"ID"+bean.getData().getAgentId());  //设置极光推送用户标签  ***作废
                            setAlias("ID"+bean.getData().getLoginToken());  //设置极光推送用户标签
                            UserPrefs.getInstance().setLoginToken(bean.getData().getLoginToken());
                            UserPrefs.getInstance().setLoginInfo(bean);
                            Intent intent = new Intent();
                            intent.setAction("codeLogin");
                            sendBroadcast(intent);
                            DialogHint.showDialogWithOneButton(mContext,"默认密码为: "+bean.getData().getPassword()+",请及时修改",RetrievePasswordActivity.this);
                            mDialogType = "password_error";
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            mBtnAffirm.setEnabled(true);
                            break;
                    }
                }
            }
        });
    }


    //获取验证码
    private void getVerifyCode(){
        RetrofitClient.getInstances().getVerifyCode(phoneNumber).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            //设置验证码获取成功，修改密码的确认按钮可用
                            setEnable(true);
                            showToast(R.string.verify_code_ok);
                            CountDownTimerUtils timerUtils = new CountDownTimerUtils(mBtnVerifyCode,60000,1000);
                            timerUtils.start();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    private void setEnable(boolean button){
        buttonEnable = button;
        mBtnAffirm.setEnabled(buttonEnable);
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

    //发起修改密码请求
    private void requestFindAlter(){
        FindAlterPasswordRequest request = new FindAlterPasswordRequest(phoneNumber,nwePassword,code);
        RetrofitClient.getInstances().requestModifyPassword(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        /*showToast(R.string.change_ok);
                        mActivityUtils.startActivity(LoginActivity.class);
                        finish();*/
                        requestLogin(phoneNumber,nwePassword);
                        break;
                    default:
                        if (isTop)showToast(bean.getErrMsg());
                        break;
                }
            }
        });

    }

    //发起登录
    private void requestLogin(String number,String password){
        LoginRequest request = new LoginRequest(number,password);
        RetrofitClient.getInstances().requestLogin(request).enqueue(new UICallBack<LoginResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
                mBtnAffirm.setEnabled(true);  //登录按钮可点击
            }

            @Override
            public void OnRequestSuccess(LoginResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1: //登录成功跳转到主页
                        if (isTop){
                            showToast(R.string.login_ok);
                            String model=android.os.Build.MODEL; // 手机型号
                            String release=android.os.Build.VERSION.RELEASE; // android系统版本号
                            LogUtils.d("手机型号=="+model);
                            LogUtils.d("系统版本号=="+release);
                            //JPushInterface.setAlias(mContext,1,"ID"+bean.getData().getAgentId());  //设置极光推送用户标签  ***作废
                            setAlias("ID"+bean.getData().getLoginToken());  //设置极光推送用户标签
                            UserPrefs.getInstance().setLoginToken(bean.getData().getLoginToken());
                            UserPrefs.getInstance().setLoginInfo(bean);
                            mActivityUtils.startActivity(MainActivity.class);
                            finish();
                        }
                        break;
                    default:
                        if (isTop)showToast(bean.getErrMsg());
                        mBtnAffirm.setEnabled(true);  //登录按钮可点击
                        break;
                }
            }
        });
    }

    //设置极光别名
    private void setAlias(String alias){
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));

    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    LogUtils.d("设置成功");
                    LogUtils.d("成功的别名="+alias);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    LogUtils.d("设置失败");
                    // 延迟 2 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 2);
                    break;
                default:

                    break;
            }
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:

                    break;
            }
        }
    };

    //弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        dialog.dismiss();
    }

    //单个确认按钮的弹窗  标题传String
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId) {
        mActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
