package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AlterPasswordRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.RegexUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改登录密码界面
 */
public class AlterPasswordActivity extends BaseToolbarActivity implements DialogHint.OnDialogOneButtonClickListener,TextWatcher{
    @BindView(R.id.et_former_password)
    EditText mEtFormerPassword;
    @BindView(R.id.et_news_password)
    EditText mEtNewsPassword;
    @BindView(R.id.et_repetition_password)
    EditText mEtRepetitionPassword;
    @BindView(R.id.btn_change)
    Button mBtnChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_alter_password;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.alter_login_password);
    }

    //修改密码点击事件
    @OnClick(R.id.btn_change)
    public void onClick() {
        String oldPassword = mActivityUtils.Md5Password(mEtFormerPassword.getText().toString().trim());
        String newPassword = mActivityUtils.Md5Password(mEtNewsPassword.getText().toString().trim());
        String repetitionPassword = mActivityUtils.Md5Password(mEtRepetitionPassword.getText().toString().trim());
        String mPassword = mEtNewsPassword.getText().toString().trim();
        String mPasswords = mEtRepetitionPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mEtNewsPassword.getText().toString().trim())||TextUtils.isEmpty(mEtFormerPassword.getText().toString().trim())){
            showToast(R.string.please_correct_password);
            return;
        }
        if (newPassword.equals(oldPassword)){
            showToast(R.string.new_former_password);
            return;
        }
        if (!repetitionPassword.equals(newPassword)){
            showToast(R.string.two_new_password);
            return;
        }

       /* LogUtils.d("密码=="+mPassword);
        String regex = "^[\u4e00-\u9fa5]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mPassword);
        if (matcher.matches()){
            LogUtils.d("是中文");
            DialogHint.showDialogWithOneButton(this,R.string.dialog_password);
            return;
        }*/

        if (RegexUtils.verifyPassword(mPassword)!=RegexUtils.VERIFY_SUCCESS && RegexUtils.verifyPassword(mPasswords)!=RegexUtils.VERIFY_SUCCESS){
            DialogHint.showDialogWithOneButton(this,R.string.dialog_password);
            return;
        }
        requestAlterPassword(userId,repetitionPassword,oldPassword);

    }

    //发起修改密码请求

    private void requestAlterPassword(String userid,String repetitionPassword,String oldPassword){
        AlterPasswordRequest request = new AlterPasswordRequest(userid,repetitionPassword,oldPassword);
        RetrofitClient.getInstances().requestAlterPassword(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.change_ok);
                        initPut();
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
        edit.putString("psd", mEtRepetitionPassword.getText().toString());  //存储密码
        edit.commit();  //提交保存
    }

    //单个确认按钮的弹窗  标题传int
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        dialog.dismiss();
    }

    //单个确认按钮的弹窗  标题传String
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
