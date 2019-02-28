package com.Store.www.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.LogisticsShipmentsRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 确认发货的界面
 */
public class AffirmShipmentsActivity extends BaseToolbarActivity implements TextWatcher {
    @BindView(R.id.et_express_name)
    EditText mEtExpressName;
    @BindView(R.id.et_express_number)
    EditText mEtExpressNumber;

    private String mOrderNo;//订单编号
    private String mCompany;  //快递公司
    private String mNumber;   //快递单号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_affirm_shipments;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.affirm_shipments);
        mOrderNo = getIntent().getStringExtra("orderNumber");
        mEtExpressName.addTextChangedListener(this); //给控件添加监听
        mEtExpressNumber.addTextChangedListener(this); //给控件添加监听
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
        mCompany = mEtExpressName.getText().toString();
        mNumber = mEtExpressNumber.getText().toString();
        LogUtils.d("快递公司="+mCompany);
        LogUtils.d("快递单号=="+mNumber);
    }

    @OnClick({R.id.tv_save_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save_button:  //保存单号的点击事件
                if (TextUtils.isEmpty(mCompany)&&TextUtils.isEmpty(mNumber)){
                    showToast(R.string.intact_content);
                    return;
                }
                //提交物流信息
                requestShipments(mOrderNo,userId,mCompany,mNumber);
                break;
        }
    }

    //向服务器提交物流信息
    private void requestShipments(String orderNo,String userId,String company,String number){
        LogisticsShipmentsRequest request = new LogisticsShipmentsRequest(orderNo,userId,company,number);
        RetrofitClient.getInstances().requestShipments(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.affirm_ok);
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });

    }

}
