package com.Store.www.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseActivity;

import com.Store.www.entity.AdvertisingResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.NetWorkUtil;
import com.Store.www.utils.UserPrefs;
import com.Store.www.utils.UserPrefsFirst;

import butterknife.BindView;

/**
 * 欢迎界面的闪屏
 */
public class WelcomeTwoActivity extends BaseActivity
        implements View.OnClickListener {
    @BindView(R.id.tv_goto_main)
    TextView mTvGotoMain;

    private int mCode;  //获取当前APP的版本号
    public static final String ADVERTISING_BROADCAST = "closeWelcome";  //注册的广播名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_welcome_two;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mCode = ActivityUtils.getVersionCode(WelcomeTwoActivity.this);
        LogUtils.d("当前APP版本号==" + mCode);
        LogUtils.d("存在本地的版本号=" + UserPrefsFirst.getInstance().getFirst());
        mTvGotoMain.setOnClickListener(this);
        setStatusBarFullTransparent(); //设置为沉浸式状态栏
        setButtonCartoon();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ADVERTISING_BROADCAST);
        registerReceiver(new AdvertisingBroadcast(), filter);
    }

    class AdvertisingBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), ADVERTISING_BROADCAST)) {
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
    }

    //给按钮设置一个淡入淡出的动画
    private void setButtonCartoon() {
        //2.1创建一个淡入淡出的效果动画
        //1）构建一个淡入淡出的动画对象
        AlphaAnimation a = new AlphaAnimation(0, 0); // 动画对象传两个参数 第一个为开始透明度  第二个为结束透明度
        //2)设置动画持续时长
        a.setDuration(8000);//毫秒
        mTvGotoMain.startAnimation(a);
        //动画结束后点击事件生效
        mTvGotoMain.setEnabled(true);
        LogUtils.d("本地版本号值==" + UserPrefsFirst.getInstance().getFirst());
//        if (UserPrefsFirst.getInstance().getFirst() == 0) {  //如果本地的值为空 ，说明是第一次首次安装
//            gotoIntroduction();  //跳到引导页
//        } else {  //否则就不是首次安装
//            LogUtils.d("当前版本号=" + mCode);
//            LogUtils.d("存在本地的版本号=" + UserPrefsFirst.getInstance().getFirst());
//            if (mCode != UserPrefsFirst.getInstance().getFirst()) {  //不是首次安装 就判断本地的值 和网络获取的值是否不同  如果不同 就是刚更新的
//                gotoIntroduction(); //更新了就跳出引导页
//            } else {  //否则两个值相等 说明不是刚更新的
//                if (!NetWorkUtil.isNetworkAvailable(WelcomeTwoActivity.this)) {  //判断当前设备是否有网络  如果没有 直接跳转进入首页
//                    /*Intent dialogIntent = new Intent(WelcomeTwoActivity.this,AdvertisingDialogActivity.class);
//                    dialogIntent.putExtra("picture",UserPrefs.getInstance().getAdvertisingPicture());
//                    startActivity(dialogIntent);*/
//                    gotoMain(); // 直接跳到主页
//                } else {
//                    getHomePageAdvertising();  //获取首页广告
//                }
//            }
//        }
        gotoMain(); // 直接跳到主页
    }

    //获取首页广告
    private void getHomePageAdvertising() {
        RetrofitClient.getInstances().getAdvertising().enqueue(new UICallBack<AdvertisingResponse>() {
            @Override
            public void OnRequestFail(String msg) {  //如果接口异常或者报错 直接进入主页
                /*Intent dialogIntent = new Intent(WelcomeTwoActivity.this,AdvertisingDialogActivity.class);
                dialogIntent.putExtra("picture",UserPrefs.getInstance().getAdvertisingPicture());
                startActivity(dialogIntent);*/
                if (isTop) mActivityUtils.startActivity(MainActivity.class);
            }

            @Override
            public void OnRequestSuccess(AdvertisingResponse bean) {
                LogUtils.d("广告返回值==" + bean.getReturnValue());
                switch (bean.getReturnValue()) {
                    case 1:
                        UserPrefs.getInstance().setAdvertisingPicture(bean.getData().getPicture());  //把广告图片存在本地
                        if (bean.getData().getStatus() == 1) {  //1播放广告
                            Intent dialogIntent = new Intent(WelcomeTwoActivity.this, AdvertisingDialogActivity.class);
                            dialogIntent.putExtra("advertising_url", bean.getData().getUrl());
                            dialogIntent.putExtra("picture", bean.getData().getPicture());
                            startActivity(dialogIntent);
                        } else {  //不播放广告
                            gotoMain(); //已经更新了就 直接跳到主页
                        }
                        break;
                    case 8:  //无数据
                        gotoMain(); //直接跳到主页
                        break;
                    default:
                        Intent dialogIntent = new Intent(WelcomeTwoActivity.this, AdvertisingDialogActivity.class);
                        dialogIntent.putExtra("picture", UserPrefs.getInstance().getAdvertisingPicture());
                        startActivity(dialogIntent);
                        break;
                }
            }
        });
    }

    //进入主页
    private void gotoMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(WelcomeTwoActivity.this, MainActivity.class);
                startActivity(intent);
                UserPrefsFirst.getInstance().setFirst(mCode);  //将本地版本号替换为网络获取版本号
                finish();
                setContentView(R.layout.view_null); //设置一个空布局 以免报异常

            }
        }, 100); //利用handler  延迟0.1秒 做跳转
    }


    //进入引导页
    private void gotoIntroduction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(WelcomeTwoActivity.this, IntroductionActivity.class);
                startActivity(intent);
                UserPrefsFirst.getInstance().setFirst(mCode);  //将本地版本号替换为网络获取版本号
                finish();
                setContentView(R.layout.view_null); //设置一个空布局 以免报异常

            }
        }, 1500); //利用handler  延迟1.5秒 做跳转
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(WelcomeTwoActivity.this, IntroductionActivity.class);
        startActivity(intent);
        WelcomeTwoActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
