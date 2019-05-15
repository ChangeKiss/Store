package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.MyApplication;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.LoginRequest;
import com.Store.www.entity.LoginResponse;
import com.Store.www.entity.RegisterRequest;
import com.Store.www.entity.VerifyPeopleResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.CountDownTimerUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.RegexUtils;
import com.Store.www.utils.UserPrefs;
import com.Store.www.utils.UserPrefsFirst;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录界面
 */
public class LoginActivity extends BaseToolbarActivity implements TextWatcher ,DialogHint.OnDialogOneButtonClickListener
                                                        ,TabLayout.OnTabSelectedListener{
    @BindView(R.id.iv_toolbar_left)
    ImageView mIvToolbarLeft;
    @BindView(R.id.iv_toolbar_right_close)
    ImageView mIvToolbarRightClose;  //右边的关闭按钮
    @BindView(R.id.et_account_number)
    EditText mEtAccountNumber;
    @BindView(R.id.et_import_password)
    EditText mEtImportPassword;
    @BindView(R.id.cb_login_policy)
    CheckBox mCbLoginPolicy;
    @BindView(R.id.tv_find)
    TextView mTvFind;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.layout_register)
    LinearLayout mLayoutRegister;  //注册布局
    @BindView(R.id.layout_login)
    LinearLayout mLayoutLogin; //登录布局
    @BindView(R.id.tab_login)
    TabLayout mTabLogin;
    @BindView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @BindView(R.id.et_user_password)
    EditText mEtUserPassword;
    @BindView(R.id.et_user_password_two)
    EditText mEtUserPasswordTwo;
    @BindView(R.id.et_user_referrer)
    EditText mEtUserReferrer;
    @BindView(R.id.btn_code)
    Button mBtnCode;  //获取验证码验证按钮
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.agency_verify_name)
    TextView mTvAgencyVerifyName;
    @BindView(R.id.cb_remember_password)
    CheckBox mCbRememberPassword;  //记住密码复选框


    private String loginNumber, loginPassword,mLoginPassword;
    private boolean mCbRegisterPolicyChecked = true;
    private boolean mRegisterEnable;
    private String mLogin;
    private int screenWidth;  //屏幕的宽
    private int screenHeight;   //屏幕的高
    private String user,mobile,code,password,passwordTwo,mPassword,mPasswords;
    boolean buttonEnable;
    private String mAlias;
    protected static final int SELECT_AREA = 2;
    private String areaCode= "+86",mAreaCode = "0";  //地区编号 默认86  提交注册时上传的地区编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mLogin = getIntent().getStringExtra("login");
        initToolbar(this, true, "");
        initTab();
        if (!TextUtils.isEmpty(mLogin)){
            showToast("请先登录");
        }
        UserPrefsFirst.getInstance().setCodeNma(ActivityUtils.getVersionName(mContext));  //将版本编号存入本地仓库
        mLayoutLogin.setVisibility(View.VISIBLE);
        mIvToolbarLeft.setVisibility(View.GONE);
        mIvToolbarRightClose.setVisibility(View.VISIBLE);
        mEtAccountNumber.addTextChangedListener(this);
        mEtImportPassword.addTextChangedListener(this);
        mEtUserPhone.addTextChangedListener(this);
        mEtUserPassword.addTextChangedListener(this);
        mEtUserPasswordTwo.addTextChangedListener(this);
        mEtUserReferrer.addTextChangedListener(this);
        getShielding();
        initOut();  //取出数据
        IntentFilter filter = new IntentFilter();
        filter.addAction("codeLogin");
        filter.addAction("weChartLogin");
        registerReceiver(new bordCast(),filter);
    }

    class bordCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo!=null && networkInfo.isAvailable()){
                if (intent.getAction().equals("codeLogin")){
                    finish();
                }else if (intent.getAction().equals("weChartLogin")){
                    showToast("登录成功");
                    finish();
                }
            }
        }
    }

    //初始化tab布局
    private void initTab(){
        mTabLogin.addTab(mTabLogin.newTab().setText(R.string.login));
        mTabLogin.addTab(mTabLogin.newTab().setText(R.string.register_name));
        mTabLogin.addOnTabSelectedListener(this);
    }

    /**
     * 设置登录按钮的可用
     */
    private void setButtonEnable() {
        mRegisterEnable =  mCbRegisterPolicyChecked;
        //同意用户协议，登录按钮可用
        mBtnLogin.setEnabled(mRegisterEnable);
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
        if (!TextUtils.isEmpty(mEtUserPassword.getText().toString().trim()) && !TextUtils.isEmpty(mEtUserPasswordTwo.getText().toString())){
            mBtnRegister.setText("注册");
        }else {
            mBtnRegister.setText("免密码注册");
        }
        loginNumber = mEtAccountNumber.getText().toString().trim();
        loginPassword = mActivityUtils.Md5Password(mEtImportPassword.getText().toString());
        mLoginPassword = mEtImportPassword.getText().toString();
        //LogUtils.d("加盐后密码==="+loginPassword);
        mobile = mEtUserPhone.getText().toString().trim();
        code = mEtUserReferrer.getText().toString().trim();  //验证码
        mPassword = mEtUserPassword.getText().toString().trim();
        mPasswords = mEtUserPasswordTwo.getText().toString().trim();
        password = ActivityUtils.Md5Password(mEtUserPassword.getText().toString().trim());
        passwordTwo = ActivityUtils.Md5Password(mEtUserPasswordTwo.getText().toString().trim());
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    //按钮的点击事件
    @OnClick({R.id.btn_login, R.id.cb_login_policy,R.id.tv_login_protocol, R.id.tv_find,R.id.iv_toolbar_left,R.id.btn_code, R.id.btn_register,
    R.id.tv_we_chart_login,R.id.tv_code_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login: //登录
                if (TextUtils.isEmpty(loginNumber)){
                    showToast(R.string.user_no_null);
                    return;
                }
               /* if (RegexUtils.verifyPassword(mLoginPassword)!= RegexUtils.VERIFY_SUCCESS){
                    DialogHint.showDialogWithOneButton(this,R.string.dialog_password);
                    return;
                }*/
                mBtnLogin.setEnabled(false);
                requestLogin(loginNumber,loginPassword);   //发起登录请求
                break;
            case R.id.tv_we_chart_login:  //微信登录
                weChartLogin();
                break;
            case R.id.tv_code_login: //验证码登录
                mActivityUtils.startActivity(RetrievePasswordActivity.class,"type","code_login");
                break;
            case R.id.cb_login_policy: //用户协议复选框
                mCbRegisterPolicyChecked = mCbLoginPolicy.isChecked();
                if (!mCbRegisterPolicyChecked){
                    mActivityUtils.showToast(R.string.no_protocol);
                    setButtonEnable();
                }else {
                    setButtonEnable();
                }
                break;
            case R.id.tv_login_protocol: //用户注册协议
                String mUrl = "http://47.96.152.157:9061/042710501793.html";
                //CommonWebActivity.startWebActivity(this,"金薇协议",mUrl);
                Intent intent = new Intent(LoginActivity.this,CommonWebActivity.class);
                intent.putExtra("url",mUrl);
                intent.putExtra("title",R.string.kivie_protocol_titile);
                intent.putExtra("type","common");
                startActivity(intent);
                break;
            case R.id.tv_find: //找回密码
                mActivityUtils.startActivity(RetrievePasswordActivity.class,"type","retrieve_password");
                break;
            case R.id.iv_toolbar_left:
                if (mLogin.equals("login")){
                    mActivityUtils.startActivity(MainActivity.class);
                }
                break;
            case R.id.iv_toolbar_right_close:
                finish();
                break;
            case R.id.btn_code:  //获取验证码
                if (TextUtils.isEmpty(mobile)){
                    showToast(R.string.verify_null);
                    return;
                }
                getVerifyCode(mobile);
                break;
            case R.id.btn_register:  //  注册
                if (RegexUtils.verifyPhoneNumber(mobile)!= RegexUtils.VERIFY_SUCCESS){
                    showToast(R.string.phone_number_no);
                    return;
                }
                if (!password.equals(passwordTwo)){
                    showToast(R.string.two_password);
                    return;
                }
                /*if (RegexUtils.verifyPassword(mPassword)!= RegexUtils.VERIFY_SUCCESS && RegexUtils.verifyPassword(mPasswords)!=RegexUtils.VERIFY_SUCCESS){
                    DialogHint.showDialogWithOneButton(this,R.string.dialog_password);
                    return;
                }*/
                if (TextUtils.isEmpty(code)){
                    showToast("请输入验证码");
                    return;
                }
                mBtnRegister.setEnabled(false);  //发起注册后设置注册按钮不可点击
                requestRegister();
                break;
            /*case R.id.layout_nation_area:  //选择国家或地区
                Intent areaIntent = new Intent(this,SelectCountryAreaActivity.class);
                startActivityForResult(areaIntent,SELECT_AREA);
                break;*/
        }
    }

    @Override
    public void onBackPressed() { //重写系统返回键
        super.onBackPressed();
        if (!TextUtils.isEmpty(mLogin) && mLogin.equals("login")){
            Intent intent = new Intent();
            intent.setAction("toHome");
            sendBroadcast(intent);
            mActivityUtils.startActivity(MainActivity.class);
            finish();
        }else {
            finish();
        }
    }

    //获取验证码
    private void getVerifyCode(String phoneNumber){
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
                            showToast(R.string.verify_code_ok);
                            CountDownTimerUtils timerUtils = new CountDownTimerUtils(mBtnCode,60000,1000);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SELECT_AREA:
                if (resultCode==6){
                    areaCode = data.getStringExtra("areaCode");
                    //mTvThisAreaNumber.setText(areaCode);
                }
                break;
            default:
                break;
        }
    }

    //发起登录
    private void requestLogin(String login,String password){
        LoginRequest request = new LoginRequest(login,password);
        RetrofitClient.getInstances().requestLogin(request).enqueue(new UICallBack<LoginResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
                mBtnLogin.setEnabled(true);
            }

            @Override
            public void OnRequestSuccess(LoginResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1: //登录成功跳转到主页
                        LogUtils.d("loginToken="+bean.getData().getLoginToken());
                        //JPushInterface.setAlias(mContext,1,"ID"+bean.getData().getLoginToken());  //设置极光推送用户标签
                        setAlias("ID"+bean.getData().getLoginToken());
                        UserPrefs.getInstance().setLoginToken(bean.getData().getLoginToken());
                        UserPrefs.getInstance().setLoginInfo(bean);
                        initPut();  //存储数据
                        mActivityUtils.startActivity(MainActivity.class);
                        showToast(R.string.login_ok);
                        CrashReport.setUserId(userId); //设置BugLy的用户ID方便查看
                        finish();
                        break;
                    default:
                        mBtnLogin.setEnabled(true);
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //微信登录
    private void weChartLogin(){
        if (!MyApplication.mWxApi.isWXAppInstalled()){
            showToast("请先安装微信");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "fuatee_wx_login";
        //req.state = "pedometer_binding";
        MyApplication.mWxApi.sendReq(req);
        finish();
    }

    //存储数据
    private void initPut(){
        SharedPreferences.Editor edit= getSharedPreferences("myPsd", MODE_PRIVATE).edit();
        if (mCbRememberPassword.isChecked()){  //如果记住密码是选中的
            edit.putString("name", mEtAccountNumber.getText().toString());  //存储用户名
            edit.putString("psd", mEtImportPassword.getText().toString());  //存储密码
            edit.putBoolean("isChecked", true);
        }else {  //如果记住密码是没选中的
            edit.putString("name", mEtAccountNumber.getText().toString());//只存用户名
            edit.putString("psd", "");
            edit.putBoolean("isChecked", false);
        }
        edit.commit();  //提交保存
    }

    //取出数据
    private void initOut(){
        SharedPreferences shared = getSharedPreferences("myPsd", MODE_PRIVATE);
        String name = shared.getString("name", "");//同上，若没找到就让它为空""
        String psd = shared.getString("psd", "");
        boolean isChecked = shared.getBoolean("isChecked", false);  //获取登录时 是否选择了记住密码
        if (isChecked){   //如果选择了 就把账号密码填写进来
            mEtAccountNumber.setText(name);
            mEtImportPassword.setText(psd);
        }else {   //如果是没选中   就只把账号填写进来
            mEtAccountNumber.setText(name);
            mEtImportPassword.setText("");
        }
        mCbRememberPassword.setChecked(isChecked);
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



        //单个确认按钮的弹窗  标题传int
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        dialog.dismiss();
    }

    //单个确认按钮的弹窗  标题传String
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId) {

    }

    //选中的tab 的监听
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int mTab = tab.getPosition();
        switch (mTab){
            case 0:
                mLayoutLogin.setVisibility(View.VISIBLE);
                mLayoutRegister.setVisibility(View.GONE);
                break;
            case 1:
                mLayoutRegister.setVisibility(View.VISIBLE);
                mLayoutLogin.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



    //发起注册请求
    private void requestRegister(){
        RegisterRequest request = new RegisterRequest(user,mobile,code,password);
        RetrofitClient.getInstances().requestRegister(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
                mBtnRegister.setEnabled(true);  //请求失败之后设置注册按钮可点击
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1: //注册成功后直接发起登录请求
                        //登录
                        requestLogin(mobile,password);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        mBtnRegister.setEnabled(true);  //请求失败之后设置注册按钮可点击
                        break;
                }
            }
        });
    }

    //获取当前手机屏幕宽高
    private void getShielding(){
        screenWidth = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        UserPrefs.getInstance().setWidth(screenWidth);
        UserPrefs.getInstance().setHeight(screenHeight);
        LogUtils.d("屏幕宽==="+screenWidth);
        LogUtils.d("屏幕高==="+screenHeight);
    }

}
