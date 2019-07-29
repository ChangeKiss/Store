package com.Store.www;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.Store.www.ui.activity.MainActivity;
import com.Store.www.ui.commom.GlideLoader;
import com.Store.www.utils.FileUtils;
import com.Store.www.utils.UserPrefs;
import com.Store.www.utils.UserPrefsFirst;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import zlc.season.rxdownload3.core.DownloadConfig;
import zlc.season.rxdownload3.extension.ApkInstallExtension;
import zlc.season.rxdownload3.extension.ApkOpenExtension;

/**
 * 初始化
 */

public class MyApplication extends Application {
    private static Context mContext;
    private static boolean mAppIsTop = false;
    private List<Activity> mActivityList = new ArrayList<>();
    private static MyApplication instance;
    //微信开发平台的AppId
    public final static String APP_ID = "wx9b0e1d8112423f5e";
    //IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI mWxApi;

    private static String mDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Fuatee";

    public static Context getmContext() {
        return mContext;
    }

    public static boolean ismAppIsTop() {
        return mAppIsTop;
    }

    public static String getmDirPath() {
        return mDirPath;
    }

    public MyApplication() {
    }

    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UserPrefs.init(getApplicationContext());  //初始化用户信息仓库
        UserPrefsFirst.init(getApplicationContext());  //初始化 存储是否第一次打开APP的数据仓库
        registerWx();
        Unicorn.init(this, "8636c1c78a1a64d370a970a03dfa1766", options(), new GlideLoader(getApplicationContext()));
        mContext = getApplicationContext();
        //LeakCanary.install(this);  //检测内存泄露
        registerActivityLifecycleCallbacks(new activityLifecycleListener());
        FileUtils.createDir(mDirPath);  //先创建文件夹，
        //初始化rxdownload3
        DownloadConfig.Builder builder = DownloadConfig.Builder.Companion.create(this)
                .enableDb(true)
                .setDebug(true)
                .setDefaultPath(mDirPath)
                .enableNotification(true)
                .setMaxRange(5)
                .addExtension(ApkInstallExtension.class)
                .addExtension(ApkOpenExtension.class);
        DownloadConfig.INSTANCE.init(builder);
        CrashReport.initCrashReport(getApplicationContext(), "774422dd40", true);  //初始化BugLy
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    //注册微信登录
    private void registerWx() {
        mWxApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        mWxApi.registerApp(APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                mWxApi.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }


    //监听APP Activity的生命周期  用于检测PP是否在前台运行
    class activityLifecycleListener implements Application.ActivityLifecycleCallbacks {

        private int refCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (refCount == 0) {
                mAppIsTop = true;
                //LogUtils.d("01是否在前台=="+mAppIsTop);
            } else {
                mAppIsTop = false;
                //LogUtils.d("02是否在前台=="+mAppIsTop);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
            refCount++;
            //LogUtils.d("Started + refCount==  0是后台 其他是前台"+refCount);
            if (refCount == 0) {
                mAppIsTop = true;
                //LogUtils.d("01是否在前台=="+mAppIsTop);
            } else {
                mAppIsTop = false;
                //LogUtils.d("02是否在前台=="+mAppIsTop);
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (refCount == 0) {
                mAppIsTop = true;
                //LogUtils.d("01是否在前台=="+mAppIsTop);
            } else {
                mAppIsTop = false;
                //LogUtils.d("02是否在前台=="+mAppIsTop);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            refCount--;
            //LogUtils.d("Stopped + refCount==  0是后台 其他是前台"+refCount);
            if (refCount == 0) {
                mAppIsTop = true;
                //LogUtils.d("03是否在前台=="+mAppIsTop);
            } else {
                mAppIsTop = false;
                //LogUtils.d("04是否在前台=="+mAppIsTop);
            }

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

    // add Activity
    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.statusBarNotificationConfig.notificationEntrance = MainActivity.class;
        options.savePowerConfig = new SavePowerConfig();
        return options;
    }
}
