package com.Store.www.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.WhetherPasswordRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择修改密码界面
 */
public class SelectAlterPasswordActivity extends BaseToolbarActivity {
    @BindView(R.id.tv_alter_pay)
    TextView mTvAlterPay;  //修改支付密码框
    @BindView(R.id.vw_pay_line)
    View mVwPayLine;   //修改支付密码框下面的线条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_select_alter_password;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.change_password);
        getWhetherPassword();   //判断用户是否设置过支付密码
    }


    //判断用户是否有设置过支付密码
    private void getWhetherPassword(){
        WhetherPasswordRequest request = new WhetherPasswordRequest(mUserId);
        RetrofitClient.getInstances().requestPassword(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();

            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:  //有密码 //显示修改支付密码框
                        mTvAlterPay.setVisibility(View.VISIBLE);
                        mVwPayLine.setVisibility(View.VISIBLE);
                        break;
                    case 8:  //没支付密码//隐藏修改支付密码框
                        mTvAlterPay.setVisibility(View.GONE);
                        mVwPayLine.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_alter_login, R.id.tv_alter_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_alter_login:  //修改登录密码
                mActivityUtils.startActivity(AlterPasswordActivity.class);
                break;
            case R.id.tv_alter_pay:  //修改支付密码
                mActivityUtils.startActivity(AlterPayPasswordActivity.class);
                break;
        }
    }
}
