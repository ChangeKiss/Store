package com.Store.www.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AccountBalanceDetailResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;

/**
 * 账户余额的收入支出明细详情
 */
public class AccountBalanceDetailActivity extends BaseToolbarActivity {
    @BindView(R.id.tv_detail_money_title)
    TextView mTvDetailMoneyTitle;  //明细金额标题
    @BindView(R.id.tv_detail_money)
    TextView mTvDetailMoney;  //明细金额
    @BindView(R.id.tv_detail_time_title)
    TextView mTvDetailTimeTitle;  //明细时间标题
    @BindView(R.id.tv_detail_time)
    TextView mTvDetailTime;  //明细时间
    @BindView(R.id.tv_detail_number_title)
    TextView mTvDetailNumberTitle;  //明细编号/内容标题
    @BindView(R.id.tv_detail_number_content)
    TextView mTvDetailNumberContent;  //明细  内容
    @BindView(R.id.layout_detail_content)
    LinearLayout mLayoutDetailContent;  //明细状态布局  提现时用
    @BindView(R.id.vw_account_line)
    View mVwAccountLine;  //提现时底部的线条
    @BindView(R.id.tv_detail_status_title)
    TextView mTvDetailStatusTitle;  //明细状态标题  提现时用
    @BindView(R.id.tv_detail_status_content)
    TextView mTvDetailStatusContent;  //明细状态内容  提现时用

    private int mId,mType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_account_balance_detail;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.detail);
        mId = getIntent().getIntExtra("id",0);
        mType = getIntent().getIntExtra("type",0);
        if (mType==0){  //支出
            LogUtils.d("支出");
            getAccountDetail(mId,mType);
        }else if (mType==1){  //收入
            LogUtils.d("收入");
            getIncomeDetail(mId,mType);
        }

    }

    //获取支出账单明细
    private void getAccountDetail(int id,int type){
        RetrofitClient.getInstances().getAccountBalance(id,type).enqueue(new UICallBack<AccountBalanceDetailResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(AccountBalanceDetailResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (bean.getData().getOrderNo().equals("0")){  //是提现详情
                            mTvDetailMoneyTitle.setText("提现金额");  //金额标题
                            mTvDetailMoney.setText("-"+ActivityUtils.changeMoneys(bean.getData().getMoney()));  //金额
                            mTvDetailTimeTitle.setText("提现时间");  //时间标题
                            mTvDetailTime.setText(bean.getData().getCreateTime());  //提现时间
                            mTvDetailNumberTitle.setText("提现对象");  //内容标题
                            mTvDetailNumberContent.setText(bean.getData().getCardNumber());  //提现内容  显示账号
                            mLayoutDetailContent.setVisibility(View.VISIBLE);  //是提现 就要将提现的状态展示出来
                            mVwAccountLine.setVisibility(View.VISIBLE);
                            mTvDetailStatusTitle.setText("提现状态");  //状态标题
                            if (bean.getData().getStatus()==0){  //审核中
                                mTvDetailStatusContent.setText("审核中");
                            }else if (bean.getData().getStatus()==1){
                                mTvDetailStatusContent.setText("审核通过");
                            }else if (bean.getData().getStatus()==2){
                                mTvDetailStatusContent.setText("审核失败");
                            }

                        }else {  //是消费订单详情
                            mTvDetailMoneyTitle.setText("消费金额");  //金额标题
                            mTvDetailMoney.setText("-"+ActivityUtils.changeMoneys(bean.getData().getMoney()));  //金额
                            mTvDetailTimeTitle.setText("消费时间");  //时间标题
                            mTvDetailTime.setText(bean.getData().getCreateTime());  //消费时间
                            mTvDetailNumberTitle.setText("订单编号");  //内容标题
                            mTvDetailNumberContent.setText(bean.getData().getOrderNo());  //消费   显示订单编号
                            mLayoutDetailContent.setVisibility(View.GONE);  //是消费订单 就要将提现的状态隐藏起来
                            mVwAccountLine.setVisibility(View.GONE);  //是消费订单  就将底部的线隐藏起来
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //获取收入明细
    private void getIncomeDetail(int id,int type){
        RetrofitClient.getInstances().getAccountBalance(id,type).enqueue(new UICallBack<AccountBalanceDetailResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(AccountBalanceDetailResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (TextUtils.isEmpty(bean.getData().getFailReason())){  //如果失败原因为空  就不是提现退回来的  把失败原因那一栏隐藏起来
                            mLayoutDetailContent.setVisibility(View.GONE);  //失败布局隐藏起来
                            mVwAccountLine.setVisibility(View.GONE);  //布局的线 隐藏起来
                            mTvDetailMoneyTitle.setText("收入金额");  //金额标题
                            mTvDetailMoney.setText("+"+ActivityUtils.changeMoneys(bean.getData().getMoney()));  //金额
                            mTvDetailTimeTitle.setText("收入时间");  //时间标题
                            mTvDetailTime.setText(bean.getData().getCreateTime());  //提现时间
                            mTvDetailNumberTitle.setText("收入来源");  //内容标题
                            mTvDetailNumberContent.setText(bean.getData().getDetail());  //提现内容  显示账号
                        }else {  //否则就是提现时退回来的钱  把失败原因那一栏显示出来
                            mTvDetailMoneyTitle.setText("收入金额");  //金额标题
                            mTvDetailMoney.setText("+"+ActivityUtils.changeMoneys(bean.getData().getMoney()));  //金额
                            mTvDetailTimeTitle.setText("收入时间");  //时间标题
                            mTvDetailTime.setText(bean.getData().getCreateTime());  //提现时间
                            mTvDetailNumberTitle.setText("收入来源");  //内容标题
                            mTvDetailNumberContent.setText(bean.getData().getDetail());  //提现内容  显示账号
                            mLayoutDetailContent.setVisibility(View.VISIBLE);  //是提现退回的 就要将提现的状态展示出来
                            mVwAccountLine.setVisibility(View.VISIBLE);  //布局的线 也要显示出来
                            mVwAccountLine.setVisibility(View.VISIBLE);
                            mTvDetailStatusTitle.setText("失败原因");  //状态标题
                            mTvDetailStatusContent.setText(bean.getData().getFailReason());  //失败原因
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

}
