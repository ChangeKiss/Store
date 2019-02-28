package com.Store.www.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.CustomTextView.ImageViewSpan;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择资金申请方式界面
 */

public class SelectFundModeActivity extends BaseToolbarActivity {
    @BindView(R.id.tv_order_backspacing_hint)
    TextView mTvOrderBackSpacingHint;  //订单回退提示
    @BindView(R.id.tv_direct_apply_for_hint)
    TextView mTvDirectApplyForHint;  //直接申请提示

    SpannableStringBuilder orderBackSpacingBuilder;  //订单回退文本
    ForegroundColorSpan orderBackSpacingColor;  //订单回退字体颜色

    SpannableStringBuilder directApplyForBuilder;  //直接申请文本
    ForegroundColorSpan directApplyForColor;   //直接申请字体颜色
    private BroadCast mBroadCast;
    private int mApplyMoney;  //申请金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_select_fund_mode;
    }

    @Override
    public void initView() {
        initToolbar(this, true, "请选择资金申请方式");
        ActivityCollector.addActivity(this);
        mApplyMoney = getIntent().getIntExtra("applyMoney",0);
        orderBackSpacingBuilder = new SpannableStringBuilder(" " + " 订单回退: 您的资金申请将会以订单回退的形式返回，根据订单支付原路返回。");
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.caution_hint_icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        orderBackSpacingColor = new ForegroundColorSpan(Color.parseColor("#404040"));
        orderBackSpacingBuilder.setSpan(orderBackSpacingColor, 2, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        orderBackSpacingBuilder.setSpan(new ImageViewSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvOrderBackSpacingHint.setText(orderBackSpacingBuilder);
        directApplyForBuilder = new SpannableStringBuilder("直接申请: 您的资金申请将会在审核通过后发放到您所提交的银行卡中");
        directApplyForColor = new ForegroundColorSpan(Color.parseColor("#404040"));
        directApplyForBuilder.setSpan(directApplyForColor, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvDirectApplyForHint.setText(directApplyForBuilder);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("applyOver");
        mBroadCast = new BroadCast();
        registerReceiver(new BroadCast(),intentFilter);

    }

    //注册广播并接收
    class BroadCast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo.isAvailable() && networkInfo!=null){
                if (intent.getAction().equals("applyOver")){
                    finish();
                }
            }
        }
    }

    @OnClick({R.id.layout_order_Rollback, R.id.layout_directApply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_order_Rollback:
                mActivityUtils.startActivity(FundOrderRollBackActivity.class,"applyMoney",mApplyMoney);
                break;
            case R.id.layout_directApply:
                mActivityUtils.startActivity(WithdrawDepositActivity.class,"applyMoney",mApplyMoney);
                break;
        }
    }
}
