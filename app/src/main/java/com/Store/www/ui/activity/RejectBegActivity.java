package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.RejectUpRequest;
import com.Store.www.entity.UpDataBegResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 拒绝申请界面
 */
public class RejectBegActivity extends BaseToolbarActivity implements TextWatcher {
    @BindView(R.id.cv_reject_agency_head_portrait)
    CircleImageView mCvRejectAgencyHeadPortrait;   //拒绝代理的头像
    @BindView(R.id.tv_reject_agency_name)
    TextView mTvRejectAgencyName;    //拒绝代理的姓名
    @BindView(R.id.tv_reject_agency_number)
    TextView mTvRejectAgencyNumber;   //拒绝代理的编号
    @BindView(R.id.tv_beg_reject_up_time)
    TextView mTvBegRejectUpTime;   //拒绝代理发起申请的时间
    @BindView(R.id.tv_reject_present_lv)
    TextView mTvRejectPresentLv;   //拒绝代理当前等级
    @BindView(R.id.tv_reject_beg_up_to)
    TextView mTvRejectBegUpTo;    //拒绝代理升级等级
    @BindView(R.id.et_reject_text)
    EditText mEtRejectText;
    @BindView(R.id.btn_reject_reason)
    Button mBtnRejectSeason;

    private String mRejectReason;
    private boolean mSubmitButton = false;
    UpDataBegResponse.DataBean dataBean;   //代理信息对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_reject_beg;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.reject_up_beg);
        mEtRejectText.addTextChangedListener(this);  //添加监听
        Intent intent = this.getIntent();
        dataBean = (UpDataBegResponse.DataBean) intent.getSerializableExtra("UpDataBean");   //接收传过来的对象
        LogUtils.d("当前代理姓名=="+dataBean.getName());
        setAgencyData();
    }

    //设置代理数据
    private void setAgencyData(){
        Glide.with(mContext).load(dataBean.getHeadPicture()).into(mCvRejectAgencyHeadPortrait);
        mTvRejectAgencyName.setText(dataBean.getName());
        mTvRejectAgencyNumber.setText(dataBean.getCode());
        mTvBegRejectUpTime.setText(ActivityUtils.time(dataBean.getTime()));
        mTvRejectPresentLv.setText(dataBean.getCurrentLevelName());
        mTvRejectBegUpTo.setText(dataBean.getApplyLevelName());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入框的文本监听
    @Override
    public void afterTextChanged(Editable s) {
        mRejectReason = mEtRejectText.getText().toString().trim();
        mSubmitButton = !TextUtils.isEmpty(mRejectReason);
        setButtonEnable(mSubmitButton);
    }

    //设置按钮的可点击
    private void setButtonEnable(boolean enable){
        mBtnRejectSeason.setEnabled(enable);
    }

    @OnClick({R.id.iv_toolbar_left, R.id.btn_reject_reason})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                break;
            case R.id.btn_reject_reason:  //提交拒绝原因的点击按钮
                if (!TextUtils.isEmpty(mRejectReason)){
                    requestRejectUp(dataBean.getId(),mRejectReason);
                }
                break;
        }
    }

    //发起拒绝升级请求
    private void requestRejectUp(int id,String Reason){
        RejectUpRequest request = new RejectUpRequest(id,Reason);
        RetrofitClient.getInstances().requestRejectUp(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                }
            }
        });
    }
}
