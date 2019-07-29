package com.Store.www.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BalanceResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的余额界面
 */
public class MyBalanceActivity extends BaseToolbarActivity{
    @BindView(R.id.iv_toolbar_right)
    TextView mTvToolbarRight;  //顶部导航栏 右侧的提现
    @BindView(R.id.tv_balance_money)
    TextView mTvBalanceMoney;  //余额的金额
    @BindView(R.id.tv_usable_balance)
    TextView mTvUsableBalance;  //  可用余额
    @BindView(R.id.tv_no_usable_balance)
    TextView mTvNoUsableBalance; //不可用余额
    @BindView(R.id.tv_withdraw_deposit_balance)
    TextView mTvWithdrawDepositBalance; //可提现余额
    @BindView(R.id.tv_freeze_balance)
    TextView mTvFreezeBalance;  //冻结金额

    LinearLayout.LayoutParams params;
    private int BalanceMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_balance;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);  //将次界面添加至界面管理器中
        initToolbar(this,true,R.string.balance);  //初始化顶部标题栏
        mTvToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        setViewSize();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    //设置控件的大小
    private void setViewSize() {
        params = (LinearLayout.LayoutParams) mTvUsableBalance.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvNoUsableBalance.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvWithdrawDepositBalance.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvFreezeBalance.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth() / 2;
        mTvUsableBalance.setLayoutParams(params);
        mTvNoUsableBalance.setLayoutParams(params);
        mTvWithdrawDepositBalance.setLayoutParams(params);
        mTvFreezeBalance.setLayoutParams(params);
        getMyBalance();
    }


    //获取我的余额
    private void getMyBalance() {
        RetrofitClient.getInstances().getMyBalance(mUserId).enqueue(new UICallBack<BalanceResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }
            @Override
            public void OnRequestSuccess(BalanceResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()) {
                        case 1:
                            BalanceMoney = bean.getData().getUsableBalance();  //可用金额 用于提现
                            mTvBalanceMoney.setText(ActivityUtils.changeMoneys(bean.getData().getCurrentBalance()) + "");
                            mTvUsableBalance.setText("可用奖金(元) ¥" + ActivityUtils.changeMoneys(bean.getData().getUsableBalance()));
                            mTvNoUsableBalance.setText("不可用奖金(元) ¥" + ActivityUtils.changeMoneys(bean.getData().getDisableBalance()));
                            mTvWithdrawDepositBalance.setText("可提现奖金(元) ¥" + ActivityUtils.changeMoneys(bean.getData().getWithdrawBalance()));
                            mTvFreezeBalance.setText("冻结奖金(元) ¥" + ActivityUtils.changeMoneys(bean.getData().getFreezeBalance()));
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }

    //查看明细的点击事件
    @OnClick({R.id.tv_income, R.id.tv_expend,R.id.tv_deposit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_income:  //查看收入
                mActivityUtils.startActivity(BalanceDetailActivity.class,"type",1);
                break;
            case R.id.tv_expend:  //查看支出
                mActivityUtils.startActivity(BalanceDetailActivity.class,"type",0);
                break;
            case R.id.tv_deposit:
                mActivityUtils.startActivity(WithdrawMoneyInputActivity.class,"balance",BalanceMoney);
                break;
        }
    }


}
