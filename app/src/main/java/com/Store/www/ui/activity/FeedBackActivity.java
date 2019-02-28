package com.Store.www.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.FeedBackRequest;
import com.Store.www.entity.ReportRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈和举报界面
 */

public class FeedBackActivity extends BaseToolbarActivity implements TextWatcher {
    @BindView(R.id.et_feed_back)
    EditText mEtFeedBack;  //输入文本框
    @BindView(R.id.btn_feed_back)
    Button mBtnFeedBack; //提交按钮
    @BindView(R.id.tv_hint_back_report)
    TextView mTvHintBackReport;  //提示的标题

    boolean contentNoNull;
    private String content; //内容
    private String mType; //根据类型来判断是意见反馈还是举报
    private int reportType=0; //举报类型写死0
    private int CircleId; //圈子ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mType = getIntent().getStringExtra("type");
        CircleId = getIntent().getIntExtra("id",0);
        if (mType.equals("feedback")){  //意见反馈
            initToolbar(this, true, R.string.feedback);
            mTvHintBackReport.setText(R.string.issue_and_feedback);
            mEtFeedBack.setHint(R.string.feed_back_hint);
        }else if (mType.equals("report")){
            initToolbar(this,true,R.string.report);
            mTvHintBackReport.setText(R.string.report_content);
            mEtFeedBack.setHint(R.string.report_hint);
        }
        mEtFeedBack.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(mEtFeedBack.getText().toString())){
            contentNoNull = false;
        }else {
            contentNoNull = true;
        }
        content = mEtFeedBack.getText().toString();
        LogUtils.d("举报或反馈内容="+content);
        mBtnFeedBack.setEnabled(contentNoNull);
    }

    @OnClick(R.id.btn_feed_back) //提交的点击事件
    public void onViewClicked() {
        if (mType.equals("feedback")){
            showToast("意见反馈");
            requestFeedBack();
        }else if (mType.equals("report")){
            requestReport(mUserId,CircleId,content,reportType);
        }

    }

    //提交意见反馈
    private void requestFeedBack(){
        FeedBackRequest request = new FeedBackRequest(mUserId,content);
        RetrofitClient.getInstances().requestFeedBack(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.submit_ok);
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //发起举报
    private void requestReport(int userId,int circleId,String content,int type){
        ReportRequest request = new ReportRequest(userId,circleId,content,type);
        RetrofitClient.getInstances().requestReportRequest(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.report_ok);
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
