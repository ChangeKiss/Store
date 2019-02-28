package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AddNewLocationRequest;
import com.Store.www.entity.AlterLocationRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.RegexUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加新地址/修改地址界面
 */

public class AddLocationActivity extends BaseToolbarActivity implements TextWatcher{
    @BindView(R.id.et_user_name)
    EditText mEtUserName;  //收件人
    @BindView(R.id.et_user_phone)
    EditText mEtUserPhone;  //收件人电话
    @BindView(R.id.tv_user_area)
    TextView mEtUserArea;  //区域
    @BindView(R.id.et_user_in_detail)
    EditText mEtUserInDetail; //详细地址
    @BindView(R.id.iv_default_kg)
    ImageView mIvDefaultKg;
    @BindView(R.id.btn_save_address)
    TextView mBtnSaveAddress;  //保存地址的按钮


    private final int ADDRESS_PROVINCE = 0;
    private final int CITY_FLAG = 1;
    private final int RETURN_AREA = 2;
    private String area;
    private String type,form;
    private String mReceiveName,mPhone,mAddress,mProvince,mCountry,mCity,mStreet;
    private int mAddressId;
    private int mIsUsed = 1; //新建地址时设置为默认


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_add_location;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        type = getIntent().getStringExtra("type");
        if (type.equals("alter")){ //修改
            alterAddress();
            initToolbar(this,true,R.string.alter_location);
        }else if (type.equals("add")){  //添加
            initToolbar(this, true, R.string.new_location);
        }
        mEtUserName.addTextChangedListener(this);
        mEtUserPhone.addTextChangedListener(this);
        mEtUserArea.addTextChangedListener(this);
        mEtUserInDetail.addTextChangedListener(this);
    }

    //编辑修改地址
    private void alterAddress(){
        mReceiveName = getIntent().getStringExtra("name");
        mPhone = getIntent().getStringExtra("phoneNumber");
        mAddressId = getIntent().getIntExtra("addressId",0);
        mAddress = getIntent().getStringExtra("address");
        mCountry = getIntent().getStringExtra("country");
        mProvince = getIntent().getStringExtra("province");
        mCity = getIntent().getStringExtra("city");
        mStreet = getIntent().getStringExtra("street");
        mIsUsed = getIntent().getIntExtra("isused",0);
        mEtUserName.setText(mReceiveName);
        mEtUserPhone.setText(mPhone);
        mEtUserArea.setText(mProvince+mCity+mStreet);
        mEtUserInDetail.setText(mAddress);
        if (mIsUsed!=1){
            mIvDefaultKg.setImageResource(R.mipmap.off_kg_icon);
        }else {
            mIvDefaultKg.setImageResource(R.mipmap.on_kg_icon);
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==ADDRESS_PROVINCE){ //省级返回的值
            if (requestCode ==6){
                mCountry = data.getStringExtra("country");
                mProvince = data.getStringExtra("province");
                mCity = "";
                mStreet = "";
                if (!TextUtils.isEmpty(mCountry))mEtUserArea.setText(mCountry);
                if (!TextUtils.isEmpty(mReceiveName))mEtUserName.setText(mReceiveName);
                if (TextUtils.isEmpty(mPhone))mEtUserPhone.setText(mPhone);
                if (TextUtils.isEmpty(mAddress))mEtUserInDetail.setText(mAddress);
            }
        }else if (resultCode ==CITY_FLAG){ //市级返回的值
            if (requestCode ==6){
                mCountry = data.getStringExtra("country");
                mProvince = data.getStringExtra("province");
                mCity = data.getStringExtra("city");
                mStreet = "";
                mEtUserArea.setText(mProvince+mCity);
                mEtUserName.setText(mReceiveName);
                mEtUserPhone.setText(mPhone);
                mEtUserInDetail.setText(mAddress);
            }
        }else if (resultCode ==RETURN_AREA){  //区域返回的值
            if (requestCode ==6){
                mCountry = data.getStringExtra("country");
                mProvince = data.getStringExtra("province");
                mCity = data.getStringExtra("city");
                mStreet = data.getStringExtra("area");
                mEtUserArea.setText(mProvince+mCity+mStreet);
                mEtUserName.setText(mReceiveName);
                mEtUserPhone.setText(mPhone);
                mEtUserInDetail.setText(mAddress);
            }
        }

    }

    @OnClick({R.id.iv_default_kg, R.id.btn_save_address,R.id.layout_add_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_default_kg: //设为默认的点击事件
                if (mIsUsed == 1) {
                    mIsUsed = 0;
                    LogUtils.d("默认地址值=="+mIsUsed);
                    mIvDefaultKg.setImageResource(R.mipmap.off_kg_icon);
                }else {
                    LogUtils.d("默认地址值=="+mIsUsed);
                    mIsUsed = 1;
                    mIvDefaultKg.setImageResource(R.mipmap.on_kg_icon);
                }
                break;
            case R.id.btn_save_address:// 保存/修改地址的点击事件
                mBtnSaveAddress.setEnabled(false);
                if (type.equals("alter")){  //修改地址
                    //修改地址前先判断信息是否完整
                    if (TextUtils.isEmpty(mEtUserName.getText().toString())||TextUtils.isEmpty(mEtUserPhone.getText().toString())
                            ||TextUtils.isEmpty(mEtUserInDetail.getText().toString())||TextUtils.isEmpty(mEtUserArea.getText().toString())){
                        showToast(R.string.please_perfect_message);
                        mBtnSaveAddress.setEnabled(true);
                        return;
                    }
                    //发起修改地址请求
                    requestAlterLocation();
                }else {//新增地址
                    if (TextUtils.isEmpty(mEtUserName.getText().toString())||TextUtils.isEmpty(mEtUserPhone.getText().toString())
                            ||TextUtils.isEmpty(mEtUserInDetail.getText().toString())||TextUtils.isEmpty(mEtUserArea.getText().toString())){
                        showToast(R.string.please_perfect_message);
                        mBtnSaveAddress.setEnabled(true);
                        return;
                    }
                    if (RegexUtils.verifyPhoneNumber(mPhone)!=RegexUtils.VERIFY_SUCCESS){
                        showToast(R.string.phone_number_no);
                        mBtnSaveAddress.setEnabled(true);
                        return;
                    }
                    requestAddNew();
                }
                break;
            case R.id.layout_add_area:  //选择区域
                Intent intent = new Intent(this,AlterCityAddressActivity.class);
                startActivityForResult(intent,6);
                break;
        }
    }

    //修改地址
    private void requestAlterLocation(){
        AlterLocationRequest request = new AlterLocationRequest(mReceiveName,mPhone,mAddressId,mAddress,
                userId,mProvince,mCountry,mCity,mStreet,mIsUsed);
        RetrofitClient.getInstances().requestAlterLocation(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            showToast(bean.getErrMsg());
                            mBtnSaveAddress.setEnabled(true);
                            finish();
                        }
                        break;
                    default:
                        if (isTop){
                            showToast(bean.getErrMsg());
                            mBtnSaveAddress.setEnabled(true);
                        }
                        break;
                }
            }
        });
    }

    //添加新地址
    private void requestAddNew(){
        AddNewLocationRequest request = new AddNewLocationRequest(mReceiveName,mPhone,mAddress,userId,mProvince,mCountry,mCity,mStreet,mIsUsed);
        RetrofitClient.getInstances().requestAddNew(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            mBtnSaveAddress.setEnabled(true);
                            showToast(R.string.add_ok);
                            finish();
                        }
                        break;
                    default:
                        if (isTop){
                            showToast(bean.getErrMsg());
                            mBtnSaveAddress.setEnabled(true);
                        }
                        break;
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

    //输入文本框的监听
    @Override
    public void afterTextChanged(Editable s) {
        LogUtils.d("用户名=="+mReceiveName);
        mReceiveName =mEtUserName.getText().toString();
        mPhone = mEtUserPhone.getText().toString();
        mAddress = mEtUserInDetail.getText().toString();
        area = mEtUserArea.getText().toString();
    }
}
