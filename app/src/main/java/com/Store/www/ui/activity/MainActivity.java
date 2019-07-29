package com.Store.www.ui.activity;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.MyApplication;
import com.Store.www.MyReceive;
import com.Store.www.R;
import com.Store.www.entity.AppUpDataResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.permisson.PermissionListener;
import com.Store.www.permisson.RequestPermissionActivity;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.ui.fragment.CircleFragment;
import com.Store.www.ui.fragment.HomeFragment;
import com.Store.www.ui.fragment.MessageFragment;
import com.Store.www.ui.fragment.MyKivieFragment;
import com.Store.www.ui.fragment.NewsFragment;
import com.Store.www.ui.fragment.ProductFragment;
import com.Store.www.ui.fragment.ShoppingTrolleyFragment;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;

import com.Store.www.utils.FileUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.Store.www.utils.UserPrefsFirst;
import com.qiyukf.nimlib.sdk.NimIntent;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends RequestPermissionActivity implements PermissionListener, DialogHint.OnDialogOneButtonClickListener {
    @BindView(R.id.layout_bottombar)
    LinearLayout mLayoutBottombar;
    @BindView(R.id.tv_tab_one)
    TextView mTvTabOne;
    @BindView(R.id.tv_tab_two)
    TextView mTvTabTwo;
    @BindView(R.id.tv_tab_three)
    TextView mTvTabThree;
    @BindView(R.id.tv_tab_four)
    TextView mTvTabFour;
    @BindView(R.id.tv_tab_five)
    TextView mTvTabFive; //圈子按钮


    private TextView mCurrentTab;
    private long mExitTime;  //退出程序时间
    private String mToFragment; //判断是否需要切换fragment
    HomeFragment mHomeFragment;
    NewsFragment mNewsFragment;
    MessageFragment mMessageFragment;
    ProductFragment mProductFragment;  //产品碎片
    ShoppingTrolleyFragment mShoppingTrolleyFragment;
    MyKivieFragment mMyKivieFragment;
    CircleFragment mCircleFragment;
    private Unbinder unbinder;
    public static int hasNotification;
    private static final int OVERDRAW_PERMISSION_REQUEST = 1000;
    private AlertDialog mPermissionDialog;  //权限弹窗
    private AlertDialog mAppUpDataDialog;  //APP升级弹窗
    public static final String SHOW_ACTION = "messageDialog";  //注册的广播名
    private messageWork mMessage;
    private int code, mNewCode, mCoerceUpData;  //当前版本号 ,最新的版本号,  强制更新 0正常更新 1强制更新
    private String mApkName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        //JPushInterface.setAlias(mContext,1,"ID"+UserPrefs.getInstance().getUserId());  //设置极光推送用户标签
        if (UserPrefs.getInstance().getLoginToken() == null) { //如果一开始loginToken 为空
            UserPrefs.getInstance().setLoginToken("0");  //先默认设置一个loginToken为0  避免报错
        }
        UserPrefsFirst.getInstance().setNetWork(ActivityUtils.getNetWorkStatus(mContext));
        LogUtils.d("当前网络类型=" + UserPrefsFirst.getInstance().getNetWork());
        setAlias("ID" + UserPrefs.getInstance().getLoginToken());  //设置极光推送用户标签
        getDataTime();
        requestPermission();
        code = ActivityUtils.getVersionCode(mContext);
        service(); //客服
        //getNewApp();  //获取APP更新信息
        if (!TextUtils.isEmpty(userId)) CrashReport.setUserId(userId);
        IntentFilter filter = new IntentFilter();
        filter.addAction(SHOW_ACTION);
        filter.addAction("toHome");
        mMessage = new messageWork();
        registerReceiver(mMessage, filter);
    }

    //设置极光别名
    private void setAlias(String alias) {
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));

    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    LogUtils.d("设置成功");
                    LogUtils.d("成功的别名=" + alias);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    LogUtils.d("设置失败");
                    // 延迟 2 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 2);
                    break;
                default:

                    break;
            }
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        service(); //客服
    }

    private void service() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            LogUtils.d("打开客服窗口");
            // 打开客服窗口
            Unicorn.openServiceActivity(mContext, "金薇客服", new ConsultSource("", "App", "custom information string"));
            // 最好将intent清掉，以免从堆栈恢复时又打开客服窗口
            setIntent(new Intent());
        }
    }


    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        UserPrefsFirst.getInstance().setCodeNma(ActivityUtils.getVersionName(mContext));  //将版本编号存入本地仓库
        setTabState(mTvTabOne, R.mipmap.home_ok, R.color.buttombarTextYellow);
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        switchFragmentByShow(mHomeFragment);
        mCurrentTab = mTvTabOne;
        mToFragment = getIntent().getStringExtra("toFragment");
        if (mToFragment != null) {
            dumpFragment();  //跳转fragment
        }
        //permissionHandle();  //获取系统悬浮窗权限


    }


    //接收广播
    class messageWork extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                if (TextUtils.equals(intent.getAction(), SHOW_ACTION)) {
                    LogUtils.d("接收到广播弹出提示框");
                    //弹出系统悬浮窗
                    LogUtils.d("弹窗图片？==" + MyReceive.ad_img);
                    Intent dialogIntent = new Intent(MainActivity.this, DialogActivity.class);
                    dialogIntent.putExtra("img_url", MyReceive.ad_img);
                    startActivity(dialogIntent);
                } else if (intent.getAction().equals("toHome")) {
                    LogUtils.d("toHome");
                    mToFragment = "home";
                    dumpFragment();
                }
            }
        }
    }

    //弹出APP更新弹窗
    private void showAppUpDataDialog() {
        if (mCoerceUpData == 0) {
            mAppUpDataDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("APP更新")
                    .setMessage("有新的版本,请立即更新")
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mActivityUtils.startActivity(RegardActivity.class, "key", "StartNow");
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(false)  //设置dialog点击外部消失
                    .create();
        } else if (mCoerceUpData == 1) {
            mAppUpDataDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("APP更新")
                    .setMessage("此版本为重要版本,请立即更新")
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mActivityUtils.startActivity(RegardActivity.class, "key", "StartNow");
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(false)  //设置dialog点击外部不消失
                    .create();
        }
        mAppUpDataDialog.show();
    }

    /**
     * 获取是否有最新版APP  以及检查APK文件是否存在
     */
    private void getNewApp() {
        RetrofitClient.getInstances().getAppUpData().enqueue(new UICallBack<AppUpDataResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(AppUpDataResponse bean) {
                LogUtils.d("当前版本号==" + code);
                LogUtils.d("最新版本号==" + bean.getVersionCode());
                mNewCode = bean.getVersionCode();
                mApkName = bean.getApkName();
                mCoerceUpData = bean.getForceUpdate();
                LogUtils.d("是否强制更新APP 0正常 1强制==" + mCoerceUpData);
                if (code < mNewCode) {  //如果当前版本号小于最新的版本号  有更新
                    showAppUpDataDialog(); //弹出对话框
                } else {  //没有更新检查是否有下载过的APK文件
                    File apkFile = new File(MyApplication.getmDirPath(), mApkName);
                    if (apkFile.exists()) {  //如果文件存在就删除文件
                        try {
                            FileUtils.deleteFile(apkFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return;
                    }
                }
            }
        });
    }


    //申请打开应用悬浮窗权限
    private void permissionHandle() {
        if (!hasOverdrawPermission(MainActivity.this)) {
            mPermissionDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("权限申请")
                    .setMessage("需要悬浮窗权限，请同意")
                    .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), OVERDRAW_PERMISSION_REQUEST);
                            mPermissionDialog.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .create();
            mPermissionDialog.show();
        }
    }

    //判断是否打开悬浮窗权限
    private boolean hasOverdrawPermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }

    /**
     * 申请相机权限
     */
    private void requestCameraPermission() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERDRAW_PERMISSION_REQUEST) {
            if (Build.VERSION.SDK_INT != 26) {
                permissionHandle();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        permissionHandle();
                    }
                }, 1200);
            }
        }
    }

    // 恢复因为系统重启造成的Fragmentmanager里面恢复的Fragment
    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        mHomeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mNewsFragment = (NewsFragment) manager.findFragmentByTag(NewsFragment.class.getName());
        mShoppingTrolleyFragment = (ShoppingTrolleyFragment) manager.findFragmentByTag(ShoppingTrolleyFragment.class.getName());
        mMyKivieFragment = (MyKivieFragment) manager.findFragmentByTag(MyKivieFragment.class.getName());
        //mCircleFragment = (CircleFragment) manager.findFragmentByTag(CircleFragment.class.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();  //接收推送通知跳转页面
        if (hasNotification == 1) {
            hasNotification = 0;
            if (MyReceive.from != null) {
                if (MyReceive.from.equals("1")) {  //1 APPg更新升级
                    mActivityUtils.startActivity(RegardActivity.class);
                } else if (MyReceive.from.equals("2")) {  //2 问卷调查或者是其他的链接类型的推送
                    Intent intent = new Intent();   //调用系统自带的 浏览器打开链接
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(MyReceive.url);
                    intent.setData(content_url);
                    startActivity(intent);
                }
            } else if (MyReceive.messageCodeId == 1001) {  //接收到自定义消息  收到自定义消息说明此账号已在其他设备登录  将这台设备的账号做登出处理
                UserPrefs.getInstance().setLoginToken("0");
                UserPrefsFirst.getInstance().setNetWork(ActivityUtils.getNetWorkStatus(mContext));
                UserPrefsFirst.getInstance().setCodeNma(ActivityUtils.getVersionName(mContext));
                LogUtils.d("清空资料后存入的版本编号==" + UserPrefsFirst.getInstance().getCodeName());
                UserPrefsFirst.getInstance().setFirst(ActivityUtils.getVersionCode(MainActivity.this));  //退出登录的时候把当前版本号再存一次，以免被清掉
                DialogHint.showDialogWithOneButton(mContext, MyReceive.customMessage, this);  //弹出对提示话框
            } else if (!MyApplication.ismAppIsTop() && MyReceive.messageCode != 0) {   //如果当前APP处于前台运行并且有收到过广播消息   ***作废了
                MyReceive.messageCode = 0;
            } else if (MyReceive.messageCodeId == 1008) {
                mToFragment = "mykivie";
                dumpFragment();
            }
        }
    }

    private void dumpFragment() {  //切换Fragment
        switch (mToFragment) {
            case "cart":
                setTabState(mTvTabThree, R.mipmap.shopping_cart_ok, 0xfff67f96);
                setTabState(mTvTabOne, R.mipmap.home_no, 0xff979797);
                setTabState(mTvTabTwo, R.mipmap.news_no, 0xff979797);
                setTabState(mTvTabFour, R.mipmap.my_kivie_no, 0xff979797);
                setTabState(mTvTabFive, R.mipmap.circle_no, 0xff979797);
                if (mShoppingTrolleyFragment == null) {
                    mShoppingTrolleyFragment = new ShoppingTrolleyFragment();
                }
                mCurrentTab = mTvTabThree;
                switchFragmentByShow(mShoppingTrolleyFragment);
                break;
            case "mykivie":
                setTabState(mTvTabFour, R.mipmap.my_kivie_ok, 0xfff67f96);
                setTabState(mTvTabOne, R.mipmap.home_no, 0xff979797);
                setTabState(mTvTabTwo, R.mipmap.news_no, 0xff979797);
                setTabState(mTvTabThree, R.mipmap.shopping_cart_no, 0xff979797);
                setTabState(mTvTabFive, R.mipmap.circle_no, 0xff979797);
                if (mMyKivieFragment == null) {
                    mMyKivieFragment = new MyKivieFragment();
                }
                switchFragmentByShow(mMyKivieFragment);
                mCurrentTab = mTvTabFour;
                break;
            case "home":
                LogUtils.d("跳转到讯息");
                setTabState(mTvTabTwo, R.mipmap.news_ok, R.color.buttombarTextYellow);
                setTabState(mTvTabOne, R.mipmap.home_no, R.color.buttombarTextGary);
                setTabState(mTvTabThree, R.mipmap.product_no, R.color.buttombarTextGary);
                setTabState(mTvTabFour, R.mipmap.user_no, R.color.buttombarTextGary);
                setTabState(mTvTabFive, R.mipmap.circle_no, R.color.buttombarTextGary);
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                }
                switchFragmentByShow(mMessageFragment);
                mCurrentTab = mTvTabTwo;
                break;
        }
    }

    //每次进入首页都获取一次获取当前时间
    private void getDataTime() {
        Calendar calendar = Calendar.getInstance();
        UserPrefs.getInstance().setYear(calendar.get(Calendar.YEAR));
        LogUtils.d("当前年=" + calendar.get(Calendar.YEAR));
        UserPrefs.getInstance().setMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1) + "");
        LogUtils.d("当前月=" + UserPrefs.getInstance().getMonth());
        LogUtils.d("当前时=" + calendar.get(Calendar.HOUR_OF_DAY));
        LogUtils.d("当前分=" + calendar.get(Calendar.MINUTE));
        LogUtils.d("当前秒=" + calendar.get(Calendar.SECOND));
        LogUtils.d("agentCode=" + UserPrefs.getInstance().getAgentCode());
    }


    /*//显示windowManager弹窗   改方案了此方法弃用
    private void showWindowManager(){
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //创建悬浮窗View
        final View mFloatView = layoutInflater.inflate(R.layout.message_popu_layout,null);
        final WindowManager mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //创建一个新的布局
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //设置窗口属性
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;  //设置显示类型
        //params.type = WindowManager.LayoutParams.TYPE_TOAST;   //这种类型无法接受事件处理
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;  //当窗口可以获得焦点（没有设置FLAG_NOT_FOCUSALBE选项）时，仍然将窗口范围之外的点设备事件（鼠标、触摸屏）发送给后面的窗口处理
        params.format = PixelFormat.TRANSLUCENT; // 支持透明
        params.alpha = 1f;  //透明度
        params.gravity = Gravity.CENTER;  //重力效果 居中
        params.width = UserPrefs.getInstance().getWidth()-100;
        params.height = UserPrefs.getInstance().getHeight()/2;
        //添加到屏幕  如果有就更新 如果没有就添加
        try {
            mWindowManager.updateViewLayout(mFloatView,params);
        }catch (Exception e){
            mWindowManager.addView(mFloatView,params);
        }
        ImageView messageImageView = (ImageView) mFloatView.findViewById(R.id.iv_message_popu);
        Glide.with(this).load(MyReceive.ad_img).into(messageImageView);
        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CommonWebActivity.startWebActivity(mContext," ",MyReceive.ad_Url);  //暂时作废
                mWindowManager.removeView(mFloatView);
            }
        });
    }*/

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        unbinder = null;
        if (mHomeFragment != null) {
            mHomeFragment = null;
        }
        if (mMessageFragment != null) {
            mMessageFragment = null;
        }

        if (mProductFragment != null) {
            mProductFragment = null;
        }

        if (mShoppingTrolleyFragment != null) {
            mShoppingTrolleyFragment = null;
        }
        if (mMyKivieFragment != null) {
            mMyKivieFragment = null;
        }
        if (mCircleFragment != null) {
            mCircleFragment = null;
        }
        super.onDestroy();
    }

    //处理返回键
    @Override
    public void onBackPressed() {
        if (mCurrentFragment != mHomeFragment) {
            // 如果不是在首页，就切换首页上
            if (mLayoutBottombar.getVisibility() == View.GONE) {
                mLayoutBottombar.setVisibility(View.VISIBLE);
            }
            resetTabState();
            //setTabState(mTvTabOne, R.mipmap.home_ok, R.color.buttombarTextYellow);
            mTvTabOne.setSelected(true);
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
            }
            switchFragmentByShow(mHomeFragment);
            mCurrentTab = mTvTabOne;
            return;
        } else {
            // 是首页。，退到后台运行
//        moveTaskToBack(true);
            if (System.currentTimeMillis() - mExitTime > 2000) {
                //2.保存当前时间
                mExitTime = System.currentTimeMillis();
                //3.提示
                showToast(R.string.again_exit);
            } else {
                //4.点击的时间差小于2000，调用父类onBackPressed方法执行退出。
                ActivityCollector.finishAll();//退出程序是finish掉所有的界面
                super.onBackPressed();
                System.exit(0);  //让系统彻底关闭程序
            }
        }

    }


    /**
     * 重置所有tab的颜色
     */
    private void resetTabState() {
        setTabState(mTvTabOne, R.mipmap.home_ok, R.color.buttombarTextYellow);
        setTabState(mTvTabTwo, R.mipmap.news_no, R.color.buttombarTextGary);
        setTabState(mTvTabThree, R.mipmap.product_no, R.color.buttombarTextGary);
        setTabState(mTvTabFour, R.mipmap.user_no, R.color.buttombarTextGary);
        //setTabState(mTvTabFive,R.mipmap.circle_no,0xff666666);
    }


    //对tab变色
    private void setTabState(TextView textView, int image, int color) {
        LogUtils.d("setTabState");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, image, 0, 0);//Call requires API level 17
        } else {
            Drawable nav_up = getResources().getDrawable(image);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            textView.setCompoundDrawables(null, null, nav_up, null);
        }
        textView.setTextColor(getResources().getColor(color));
    }

    //6.0以上的系统要获取权限
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.SYSTEM_ALERT_WINDOW",
                    "android.permission.READ_PHONE_STATE",
                    "android.permission.CAMERA",
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS",}, this);
        }
    }


    @Override
    public void onGranted() {

    }

    @Override
    public void onDenied(List<String> deniedPermissions) {
        StringBuilder builder = new StringBuilder();
        int deniedCount = deniedPermissions.size();
        for (int i = 0; i < deniedCount; i++) {
            String[] strArray = deniedPermissions.get(i).split("\\.");
            builder.append(strArray[strArray.length - 1]);
            if (i == (deniedCount - 1)) {
                builder.append(".");
            } else {
                builder.append(",");
            }
        }
    }

    //导航栏底部按钮的点击事件
    @OnClick({R.id.tv_tab_one, R.id.tv_tab_two, R.id.tv_tab_three, R.id.tv_tab_four, R.id.tv_tab_five})
    public void onViewClicked(View view) {
        if (view == mCurrentTab) {
            return;
        }
        LogUtils.d("mCurrentTab=" + mCurrentTab);
        switch (view.getId()) {
            case R.id.tv_tab_one:  //首页
                setTabState(mTvTabOne, R.mipmap.home_ok, R.color.buttombarTextYellow);
                setTabState(mTvTabTwo, R.mipmap.news_no, R.color.buttombarTextGary);
                setTabState(mTvTabThree, R.mipmap.product_no, R.color.buttombarTextGary);
                setTabState(mTvTabFour, R.mipmap.user_no, R.color.buttombarTextGary);
                setTabState(mTvTabFive, R.mipmap.circle_no, R.color.buttombarTextGary);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                switchFragmentByShow(mHomeFragment);
                mCurrentTab = mTvTabOne;
                break;
            case R.id.tv_tab_two:  //新闻资讯
                setTabState(mTvTabTwo, R.mipmap.news_ok, R.color.buttombarTextYellow);
                setTabState(mTvTabOne, R.mipmap.home_no, R.color.buttombarTextGary);
                setTabState(mTvTabThree, R.mipmap.product_no, R.color.buttombarTextGary);
                setTabState(mTvTabFour, R.mipmap.user_no, R.color.buttombarTextGary);
                setTabState(mTvTabFive, R.mipmap.circle_no, R.color.buttombarTextGary);
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                }
                switchFragmentByShow(mMessageFragment);
                mCurrentTab = mTvTabTwo;
                break;
            case R.id.tv_tab_three:  //产品
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    setTabState(mTvTabThree, R.mipmap.product_ok, R.color.buttombarTextYellow);
                    setTabState(mTvTabOne, R.mipmap.home_no, R.color.buttombarTextGary);
                    setTabState(mTvTabTwo, R.mipmap.news_no, R.color.buttombarTextGary);
                    setTabState(mTvTabFour, R.mipmap.user_no, R.color.buttombarTextGary);
                    if (mProductFragment == null) {
                        mProductFragment = new ProductFragment();
                    }
                    switchFragmentByShow(mProductFragment);
                    mCurrentTab = mTvTabThree;
                }

                break;
            case R.id.tv_tab_four:  //个人中心
                setTabState(mTvTabFour, R.mipmap.user_ok, R.color.buttombarTextYellow);
                setTabState(mTvTabOne, R.mipmap.home_no, R.color.buttombarTextGary);
                setTabState(mTvTabTwo, R.mipmap.news_no, R.color.buttombarTextGary);
                setTabState(mTvTabThree, R.mipmap.product_no, R.color.buttombarTextGary);
                if (mMyKivieFragment == null) {
                    mMyKivieFragment = new MyKivieFragment();
                }
                switchFragmentByShow(mMyKivieFragment);
                mCurrentTab = mTvTabFour;
                break;
            case R.id.tv_tab_five:  //圈子
                setTabState(mTvTabFive, R.mipmap.circle_ok, R.color.buttombarTextYellow);
                setTabState(mTvTabFour, R.mipmap.my_kivie_no, R.color.buttombarTextGary);
                setTabState(mTvTabOne, R.mipmap.home_no, R.color.buttombarTextGary);
                setTabState(mTvTabTwo, R.mipmap.news_no, R.color.buttombarTextGary);
                setTabState(mTvTabThree, R.mipmap.shopping_cart_no, R.color.buttombarTextGary);
                if (mCircleFragment == null) {
                    mCircleFragment = new CircleFragment();
                }
                switchFragmentByShow(mCircleFragment);
                mCurrentTab = mTvTabFive;
                break;
        }
    }


    //一个按钮的弹窗确认的点击事件  提示内容传int
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {

    }

    //弹窗确认的点击事件  提示内容传String
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId) {
        mActivityUtils.startActivity(LoginActivity.class, "login", "login");
        dialog.dismiss();
    }
}
