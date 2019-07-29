package com.Store.www.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AlterVerifyResponse;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.VerifyOldCodeRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.CountDownTimerUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.Store.www.utils.UserPrefsFirst;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.YSFUserInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更换手机号界面
 */
public class ChangePhoneActivity extends BaseToolbarActivity implements TextWatcher {
    @BindView(R.id.layout_verify_phone)
    LinearLayout mLayoutVerifyPhone;  //验证旧手机的布局
    @BindView(R.id.tv_this_phone_number)
    TextView mTvThisPhoneNumber;  //当前电话号码
    @BindView(R.id.et_verify_code)
    EditText mEtVerifyCode;  //输入验证码文本框
    @BindView(R.id.btn_change_phone_code)
    Button mBtnChangePhoneCode;  //获取验证码按钮
    @BindView(R.id.btn_verify)
    Button mBtnVerify;  //提交验证
    @BindView(R.id.layout_contact_service)
    LinearLayout mLayoutContactService;  //联系客服按钮

    @BindView(R.id.layout_affirm_alter)
    LinearLayout mLayoutAffirmAlter;  //修改新的手机号码的布局
    @BindView(R.id.et_input_new_phone)
    EditText mEtInputNewPhone;  //新的手机号码输入框
    @BindView(R.id.et_alter_phone_verify_code)
    EditText mEtAlterPhoneVerifyCode;  //修改手机的验证码
    @BindView(R.id.btn_change_new_phone_code)
    Button mBtnChangeNewPhoneCode;   //获取新手机的验证码
    @BindView(R.id.btn_affirm_alter_phone)
    Button mBtnAffirmAlterPhone;   //确认修改的按钮

    private String mVerifyCode;     //旧的手机验证码
    private String mNewPhone;       //新的手机号码
    private String mNewVerifyCode;  //新的手机验证码
    private String mVerifyType;  //验证类型
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.change_phone);
        mTvThisPhoneNumber.setText(UserPrefs.getInstance().getPhone());  //当前手机号码
        mEtVerifyCode.addTextChangedListener(this);  //添加输入框文本监听
        mEtInputNewPhone.addTextChangedListener(this);
        mEtAlterPhoneVerifyCode.addTextChangedListener(this);
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
        mVerifyCode = mEtVerifyCode.getText().toString();
        mNewPhone = mEtInputNewPhone.getText().toString();
        mNewVerifyCode = mEtAlterPhoneVerifyCode.getText().toString();
    }

    /**
     * 获取旧的手机验证码
     */
    private void getOldVerifyCode(String mobilePhone) {
        RetrofitClient.getInstances().getAlterPhoneVerifyCode(mobilePhone).enqueue(new UICallBack<AlterVerifyResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
                mBtnChangePhoneCode.setEnabled(true);
                mBtnChangeNewPhoneCode.setEnabled(true);
            }

            @Override
            public void OnRequestSuccess(AlterVerifyResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        LogUtils.d("验证类型=="+mVerifyType);
                        if (mVerifyType.equals("oldCode")){  //获取旧的验证码
                            showToast(R.string.verify_code_ok);
                            CountDownTimerUtils timerUtils = new CountDownTimerUtils(mBtnChangePhoneCode,60000,1000);
                            timerUtils.start();
                        }else if (mVerifyType.equals("newCode")){  //获取新的验证码
                            showToast(R.string.verify_code_ok);
                            CountDownTimerUtils timerUtils = new CountDownTimerUtils(mBtnChangeNewPhoneCode,60000,1000);
                            timerUtils.start();
                        }
                        break;
                    default:
                        mBtnChangePhoneCode.setEnabled(true);
                        mBtnChangeNewPhoneCode.setEnabled(true);
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });

    }

    /**
     * 验证旧的手机
     * @param phone
     * @param code
     */

    private void requestOldVerifyCode(String phone,String code){
        VerifyOldCodeRequest request = new VerifyOldCodeRequest(phone,code);
        RetrofitClient.getInstances().requestOldVerifyCode(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        mLayoutVerifyPhone.setVisibility(View.GONE);
                        mLayoutAffirmAlter.setVisibility(View.VISIBLE);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    /**
     * 修改新手机号码
     */
    private void requestAlterPhone(final String newPhone, String newCode){
        VerifyOldCodeRequest request = new VerifyOldCodeRequest(newPhone,newCode);
        RetrofitClient.getInstances().requestAlterNewPhone(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.change_ok);
                        SharedPreferences shared = getSharedPreferences("myPsd", MODE_PRIVATE);
                        if (shared.getString("name","").equals(UserPrefs.getInstance().getPhone())){  //如果存的登录时输入的数据跟这边的数据一样 都是手机号码
                            initPut();  //就把修改的手机号码存进去
                        }
                        UserPrefs.getInstance().setPhone(newPhone);  //修改成功 把本地手机号码更新一下
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //存储数据
    private void initPut(){
        SharedPreferences.Editor edit= getSharedPreferences("myPsd", MODE_PRIVATE).edit();
        edit.putString("name", mEtInputNewPhone.getText().toString());  //存储手机号码
        edit.commit();  //提交保存
    }

    //初始化七鱼云客服
    private void initQiYuService(){
        String title = "金薇客服";
        /**
         * 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入。
         * 三个参数分别为：来源页面的url，来源页面标题，来源页面额外信息（保留字段，暂时无用）。
         * 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
         */
        YSFOptions options = new YSFOptions(); //初始化七鱼云客服
        UICustomization uiCustomization = new UICustomization();
        uiCustomization.titleBackgroundResId = R.color.placeholder_top;  //设置顶部标题栏背景颜色
        uiCustomization.topTipBarTextColor = R.color.windowBackground;  //设置顶部标题栏字体颜色
        if (UserPrefs.getInstance().getIcon()==null){
            uiCustomization.rightAvatar = "http://fuatee.oss-cn-hangzhou.aliyuncs.com/mrtx.png";
        }else {
            uiCustomization.rightAvatar = UserPrefs.getInstance().getIcon();
        }
        uiCustomization.leftAvatar = "http://fuatee.oss-cn-hangzhou.aliyuncs.com/kf.png";
        uiCustomization.titleCenter = true;
        options.uiCustomization = uiCustomization;
        //options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig=new SavePowerConfig(); //***保存聊天记录
        ConsultSource source = new ConsultSource("", "首页", "custom information string");
        YSFUserInfo userInfo = new YSFUserInfo();
        userInfo.userId = UserPrefs.getInstance().getUserId();
        //把用户信息传给 七鱼 后台展示用户信息
        userInfo.data = "[{\"key\":\"real_name\", \"value\":\""+UserPrefs.getInstance().getNickName()+"\"}," +
                "{\"key\":\"mobile_phone\", \"value\":\""+UserPrefs.getInstance().getPhone()+"\"}," +
                "{\"key\":\"email\", \"value\":\""+UserPrefs.getInstance().getEmail()+"\"}," +
                "{\"index\":0,\"key\":\"account\", \"label\":\"代理编号\", \"value\":\""+UserPrefs.getInstance().getCode()+"\"}," +
                "{\"index\":1, \"key\":\"sex\", \"label\":\"微信号\", \"value\":\""+UserPrefs.getInstance().getWxi()+"\"}," +
                "{\"index\":5, \"key\":\"reg_date\", \"label\":\"代理等级\", \"value\":\""+UserPrefs.getInstance().getLevel()+"\"},"+
                "{\"key\":\"avatar\", \"value\":\""+UserPrefs.getInstance().getIcon()+"\"}," +
                "{\"index\":3,\"key\":\"agent\",\"label\":\"版本编号\",\"value\":\""+ UserPrefsFirst.getInstance().getCodeName()+"\"}]";

        Unicorn.setUserInfo(userInfo);  //设置用户信息
        Unicorn.updateOptions(options);  //设置自定义UI
        Unicorn.toggleNotification(true);  //退到后台时监听未读消息
        /**
         * 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable()，
         * 如果返回为false，该接口不会有任何动作
         *
         * @param context 上下文
         * @param title   聊天窗口的标题
         * @param source  咨询的发起来源，包括发起咨询的url，title，描述信息等
         */
        Unicorn.openServiceActivity(mContext, title, source);
    }

    @OnClick({R.id.btn_change_phone_code, R.id.btn_verify, R.id.layout_contact_service, R.id.btn_change_new_phone_code, R.id.btn_affirm_alter_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change_phone_code:  //获取旧的验证码
                mVerifyType = "oldCode";
                getOldVerifyCode(UserPrefs.getInstance().getPhone());
                break;
            case R.id.btn_verify:  //验证
                if (TextUtils.isEmpty(mVerifyCode)){  //如果验证码是空的
                    showToast(R.string.please_verify_code);
                    return;
                }
                requestOldVerifyCode(UserPrefs.getInstance().getPhone(),mVerifyCode);
                break;
            case R.id.layout_contact_service:  //联系客服
                //initQiYuService();   //打开客服
                break;
            case R.id.btn_change_new_phone_code:  //获取新的验证码
                if (TextUtils.isEmpty(mNewPhone)){
                    showToast("请输入手机号");
                    return;
                }
                mVerifyType = "newCode";
                getOldVerifyCode(mNewPhone);
                break;
            case R.id.btn_affirm_alter_phone:  //确认修改手机
                if (TextUtils.isEmpty(mNewPhone)){
                    showToast(R.string.phone_number_null);
                    return;
                }
                if (TextUtils.isEmpty(mNewVerifyCode)){
                    showToast(R.string.please_verify_code);
                    return;
                }
                requestAlterPhone(mNewPhone,mNewVerifyCode);
                break;
        }
    }
}
