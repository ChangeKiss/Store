package com.Store.www.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AlterWeCharRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.SetMyPayContentRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改资料的界面
 */
public class AlterMaterialActivity extends BaseToolbarActivity implements TextWatcher {
    @BindView(R.id.et_alter_pay)  //输入框
    EditText mEtAlterPay;
    @BindView(R.id.tv_save_pay)  //保存按钮
    TextView mTvSavePay;

    private String mPayContent,payContent;  //输入的收款内容
    private String mType; //修改资料的类型
    private String Wxi;  //微信号
    String chinese = "\"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$\"";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_alter_material;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mType = getIntent().getStringExtra("type");
        if (mType.equals("alterPay")){ //修改收款资料
            initToolbar(this, true, R.string.pay_data);
            payContent = getIntent().getStringExtra("hintContent");
            mEtAlterPay.setText(payContent);
        }else if (mType.equals("alterWxi")){  //修改微信号
            initToolbar(this, true, R.string.alter_wxi);
            Wxi = getIntent().getStringExtra("Wxi");
            mEtAlterPay.setHint(R.string.please_input_wxi);
            mEtAlterPay.setText(Wxi);
        }

        mEtAlterPay.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入中的文本框的监听
    @Override
    public void afterTextChanged(Editable s) {
        mPayContent = mEtAlterPay.getText().toString();
       /* if (TextUtils.isEmpty(mPayContent)){
            mTvSavePay.setEnabled(false);
        }else {
            mTvSavePay.setEnabled(true);
        }*/
        LogUtils.d("输入内容==" + mPayContent);
    }


    //请求修改收款资料
    private void setMyPayContent(int userId, String content) {
        SetMyPayContentRequest request = new SetMyPayContentRequest(userId, content);
        RetrofitClient.getInstancesTest().setPayContent(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        showToast(R.string.plus_ok);
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //请求修改微信号码
    private void requestAlterWeChar(int userId, final String content){
        AlterWeCharRequest request = new AlterWeCharRequest(userId,content);
        RetrofitClient.getInstances().requestAlterWeChar(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.hint_revise_success);
                        UserPrefs.getInstance().setWxi(content); //修改成功将本地仓库的微信号改掉
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_save_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save_pay:  //保存按钮的点击事件
                if (mType.equals("alterPay")){  //修改收款资料
                    if (TextUtils.isEmpty(mPayContent)){
                        mPayContent = "";
                    }
                    setMyPayContent(mUserId,mPayContent);
                }else if (mType.equals("alterWxi")){ //修改微信号
                    if (TextUtils.isEmpty(mPayContent)){
                        showToast(R.string.pay_content_null);
                        return;
                    }
                    if (mPayContent.toString().length()>20){
                        showToast(R.string.alter_wxi_number_overrun);
                        return;
                    }
                    /*Pattern pattern = Pattern.compile(chinese);
                    if (pattern.matcher(mPayContent).matches()){
                        showToast("不能包含中文");
                        return;
                    }*/
                    requestAlterWeChar(mUserId,mPayContent);
                }

                break;
        }
    }
}
