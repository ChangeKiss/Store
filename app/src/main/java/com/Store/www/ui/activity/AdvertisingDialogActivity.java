package com.Store.www.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 广告页的dialogActivity
 */
public class AdvertisingDialogActivity extends Activity {
    @BindView(R.id.iv_advertising)
    ImageView mIvAdvertising;  //显示广告的图片
    @BindView(R.id.tv_advertising_close)
    TextView mTvAdvertisingClose;  //关闭广告 相当于finish 调当前activity

    public static final String ADVERTISING_TYPE = "advertising";  //intent类型
    private final static int COUNT = 1;  //handler 消息值
    private String mUrl,mPicture;   //广告链接  广告图片URL
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising_dialog);
        ButterKnife.bind(this);
        setTransparentToolbar();
        ActivityCollector.addActivity(this);
        initView();
        /*TextViewCountDownTimerUtil timerUtil = new TextViewCountDownTimerUtil(mTvAdvertisingClose,5000,1000);
        timerUtil.start();*/
    }

    //利用handler对TextView进行文本更新
    private Handler handler = new Handler(){
        int time = 4;
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case COUNT:
                    mTvAdvertisingClose.setText(String.valueOf(time)+"s后关闭");
                    time --;
                    if (time==0){
                        time = 0;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //设置状态栏为沉浸式
    public void setTransparentToolbar() {
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    private void initView() {
        mUrl = getIntent().getStringExtra("advertising_url");
        mPicture =  getIntent().getStringExtra("picture");
        if (!TextUtils.isEmpty(mPicture)) {  //如果图片不为空  加载图片
            Glide.with(AdvertisingDialogActivity.this)
                    .load(mPicture)
                    .crossFade()
                    .into(mIvAdvertising);
        }
        gotoMain();
    }



    //进入主页
    private void gotoMain(){
        mTimer = new Timer();
        //schedule的第二个参数是第一次启动延时的时间，第三个是每隔多长时间执行一次
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(COUNT);
            }
        },0,1000);
        //schedule的参数是延时多长时间后执行
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(AdvertisingDialogActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                mTimer.cancel();
            }
        },4000);  //用timer 延迟4秒做跳转

    }

    //重写系统返回键
    @Override
    public void onBackPressed() {
        return;
    }

    @OnClick({R.id.iv_advertising, R.id.tv_advertising_close, R.id.ll_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_advertising:
                //CommonWebActivity.startWebActivity(AdvertisingDialogActivity.this,mUrl);
                // 打开对应的webView
                if (TextUtils.isEmpty(mUrl)){  //如果链接是空的
                    return;
                }
                Intent advertisingIntent = new Intent(AdvertisingDialogActivity.this,CommonWebActivity.class);
                advertisingIntent.putExtra("url",mUrl);
                advertisingIntent.putExtra("type",ADVERTISING_TYPE);
                startActivity(advertisingIntent);
                mTimer.cancel();
                finish();
                break;
            case R.id.tv_advertising_close:
                finish();
                Intent intentClose = new Intent();
                intentClose.setAction(WelcomeTwoActivity.ADVERTISING_BROADCAST);
                sendBroadcast(intentClose);
                Intent intent = new Intent(AdvertisingDialogActivity.this,MainActivity.class);
                startActivity(intent);
                mTimer.cancel();  //手动点击时取消timer
                LogUtils.d("点击了广告页的关闭按钮");
                break;
            case R.id.ll_root:

                break;
        }
    }
}
