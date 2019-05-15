package com.Store.www.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.SettingPasswordRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置 / 修改支付密码
 */
public class SettingPayPasswordActivity extends BaseToolbarActivity implements TextWatcher {
    @BindView(R.id.et_first_password)
    EditText mEtFirstPassword;  //第一个密码输入框
    @BindView(R.id.et_two_password)
    EditText mEtTwoPassword;  //第二个密码输入框

    private boolean yes = false;
    private boolean Alter = true;
    private String mType;

    private String mFirstPassword;   //第一次输入的密码
    private String twoPassword;  //第二次输入的密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_setting_pay_password;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mType = getIntent().getStringExtra("type");
        if (mType.equals("setting")) {  //设置
            initToolbar(this, true, R.string.setting_pay_password);
        } else if (mType.equals("alter")) {  //修改 找回
            initToolbar(this, true, R.string.alter_pay_password);
        }
        mEtFirstPassword.addTextChangedListener(this);
        mEtTwoPassword.addTextChangedListener(this);

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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入中文本框的监听
    @Override
    public void afterTextChanged(Editable s) {
        mFirstPassword = mEtFirstPassword.getText().toString();
        twoPassword = mEtTwoPassword.getText().toString();
    }

    @OnClick({R.id.btn_password_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_password_ok:  //设置密码
                yes = false;
                Alter = true;
                if (mFirstPassword.length()<6){
                    showToast("密码长度不足");
                }else if (!compareValue(mFirstPassword)){
                    showToast("不能设置六位相同的数字");
                }else if (compareValues(mFirstPassword)){
                    showToast("不能设置六位连续的数字");
                }else  if (!mFirstPassword.equals(twoPassword)){
                    showToast("两次输入的密码不一致");
                }else {
                    requestSettingPassword(mUserId,ActivityUtils.Md5Password(mFirstPassword));
                }
                break;
        }
    }


    //设置支付密码
    private void requestSettingPassword(int agentId,String password){
        SettingPasswordRequest request = new SettingPasswordRequest(agentId,password);
        RetrofitClient.getInstances().requestSettingPassword(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            showToast(R.string.setting_ok);
                            finish();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //第一种判断密码方法  判断是否是6位相同的密码
    private boolean compareValue(String password){
        for (int i=0; i<password.length()-1;i++){
            LogUtils.d("第"+i+"次比较的值="+mFirstPassword.charAt(i));
            if (mFirstPassword.charAt(i)==mFirstPassword.charAt(i+1)){
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
                    if (password.charAt(i)-password.charAt(i+1)!=1){
                        //修改密码
                        LogUtils.d("是连续的");
                        Alter = false;
                    }
                }
            }
            if (password.charAt(1)-password.charAt(0)==1){
                for (int i=0;i<password.length()-1;i++){
                    if (password.charAt(i+1)-password.charAt(i)!=1){
                        //修改密码
                        LogUtils.d("是连续的");
                        Alter = false;
                    }
                }
            }
        }else {
            Alter = false;
        }
        return Alter;
    }
}
