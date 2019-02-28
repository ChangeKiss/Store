package com.Store.www.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.Store.www.R;
import com.Store.www.base.BaseActivity;

import butterknife.BindView;

//第一个欢迎页面
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_welcome)
    ImageView mIvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // gotoMain();
    }

    @Override
    public int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
    }
/*
    private void gotoMain(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(WelcomeActivity.this, WelcomeTwoActivity.class);
                startActivity(intent);
                //将当前的界面的生命周期给结束
                WelcomeActivity.this.finish();
                //知识点，包括intent的信息传递，activity的生命周期
            }
        },100);
    }*/

}
