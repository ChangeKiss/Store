package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Store.www.MyApplication;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AppUpDataResponse;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.LoginOutRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.DataCleanManager;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.Store.www.utils.UserPrefsFirst;
import com.qiyukf.unicorn.api.Unicorn;

import java.io.File;


import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Downloading;
import zlc.season.rxdownload3.core.Failed;
import zlc.season.rxdownload3.core.Normal;
import zlc.season.rxdownload3.core.Status;
import zlc.season.rxdownload3.core.Succeed;
import zlc.season.rxdownload3.core.Suspend;
import zlc.season.rxdownload3.core.Waiting;
import zlc.season.rxdownload3.extension.ApkInstallExtension;


//右上角的设置界面-->关于金薇
public class RegardActivity extends BaseToolbarActivity {
    @BindView(R.id.layout_clear)
    LinearLayout mLayoutClear;
    @BindView(R.id.tv_apply_Cache)
    TextView mTvApplyCache;   //显示App缓存控件
    @BindView(R.id.btn_exit)
    Button mBtnExit;

    private int code;
    private String CacheSize;  //缓存大小
    protected ProgressBar mProgressBar;  //下载进度条
    protected TextView mTvProgressBar,mTvAppUpDataHint;  //下载进度文字  app升级内容提示
    protected Button mBtnGoOn;    //暂停、继续的按钮
    protected AlertDialog mDialog;  //弹窗
    private Disposable disposable;
    private String mDownLoadUrl,mUpDataHint;  //下载链接    升级提示
    private int mNewCode;  //最新的Code版本
    private Status currentStatus = new Status();
    private static final int MSG_COUNT = 10;
    private String mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_regard;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.seting);
        mKey = getIntent().getStringExtra("key");
        code = ActivityUtils.getVersionCode(RegardActivity.this);
        getNewApp();  //获取版本信息
        if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
            mBtnExit.setVisibility(View.GONE);
        }else {
            mBtnExit.setVisibility(View.VISIBLE);
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
        try {
            CacheSize = DataCleanManager.getTotalCacheSize(this);
            mTvApplyCache.setText(CacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //点击事件
    @OnClick({R.id.layout_clear, R.id.btn_exit,R.id.layout_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_clear:  //清理缓存
                DataCleanManager.cleanInternalCache(this);
                showToast(R.string.clear_ok);
                onResume();
                break;
            case R.id.layout_update:  //版本更新
                //checkUpdate(0, null);
                if (code<mNewCode){  //如果当前版本号小于最新的版本号  有更新
                    showUpData(mDownLoadUrl,mUpDataHint);
                }else {  //没有更新
                    showToast("当前已是最新版");
                }
                break;
            case R.id.btn_exit:  //退出登录
                requestOut(userId);
                break;
        }
    }


    //获取是否有最新版APP
    private void getNewApp(){
        RetrofitClient.getInstances().getAppUpData().enqueue(new UICallBack<AppUpDataResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(AppUpDataResponse bean) {
                if (isTop){
                    LogUtils.d("当前版本号=="+code);
                    LogUtils.d("最新版本号=="+bean.getVersionCode());
                    mDownLoadUrl = bean.getUrl();
                    mUpDataHint = bean.getContent();
                    mNewCode = bean.getVersionCode();
                    File file = new File(MyApplication.getmDirPath());
                    //LogUtils.d("当前APK 路径=="+file.getPath());
                    if (mKey!=null && mKey.equals("StartNow")){  //如果更新的key 是首页过来的立即更新  就直接开始下载
                        showUpData(mDownLoadUrl,mUpDataHint);
                        onCreateRxDownload();  //初始化
                        mProgressBar.setVisibility(View.VISIBLE);
                        dispatchClick(mDownLoadUrl);
                    }
                }
            }
        });
    }



    //弹出更新升级对话框
    private void showUpData(final String url,String upDataHint){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_app_updata,null);
        mTvAppUpDataHint =  view.findViewById(R.id.tv_app_upData_hint);
        mProgressBar =  view.findViewById(R.id.pb_upData_progress);  //下载进度条
        mTvProgressBar =  view.findViewById(R.id.tv_progress);  //下载进度文字
        mBtnGoOn =  view.findViewById(R.id.btn_go_on);  //暂停、继续的按钮
        mDialog = new AlertDialog.Builder(mContext).setView(view).create();
        setProgressBarIndeterminateVisibility(true);
        mDialog.setCancelable(true);  //设置点击外部不消失
        mDialog.show();
        mTvAppUpDataHint.setText(upDataHint);
        mDialog.getWindow().setLayout(UserPrefs.getInstance().getWidth()-100, WindowManager.LayoutParams.WRAP_CONTENT);  //设置弹窗的宽高
        mBtnGoOn.setOnClickListener(v -> {
            onCreateRxDownload();  //初始化
            mProgressBar.setVisibility(View.VISIBLE);
            dispatchClick(url);
        });

    }

    //初始化RxDownload
    private void onCreateRxDownload(){
        disposable = RxDownload.INSTANCE.create(mDownLoadUrl, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Status>() {
                    @Override
                    public void accept(Status status) throws Exception {
                        currentStatus = status;
                        setProgress(status);
                        setActionText(status);
                    }
                });
    }

    //点击事件
    private void dispatchClick(String url) {
        if (currentStatus instanceof Normal) {
            start(url);
        } else if (currentStatus instanceof Suspend) {
            start(url);
        } else if (currentStatus instanceof Failed) {
            start(url);
        } else if (currentStatus instanceof Downloading) {
            stop(url);
        } else if (currentStatus instanceof Succeed) {
            install(url);
            mDialog.dismiss();
        } else if (currentStatus instanceof ApkInstallExtension.Installed) {
            //open();
        }

    }

    //按钮字体
    private void setActionText(Status status) {
        String text = "";
        if (status instanceof Normal) {
            text = "开始";
        } else if (status instanceof Suspend) {
            text = "已暂停";
        } else if (status instanceof Waiting) {
            text = "等待中";
        } else if (status instanceof Downloading) {
            text = "暂停";
        } else if (status instanceof Failed) {
            text = "失败";
        } else if (status instanceof Succeed) {
            text = "安装";
        } else if (status instanceof ApkInstallExtension.Installing) {
            text = "安装中";
        } else if (status instanceof ApkInstallExtension.Installed) {
            text = "打开";
        }
        mBtnGoOn.setText(text);
    }

    //设置进度条
    private void setProgress(Status status) {
        mProgressBar.setMax((int) status.getTotalSize());
        mProgressBar.setProgress((int) status.getDownloadSize());
        /*contentMainBinding.percent.setText(status.percent());*/
        mTvProgressBar.setText(status.formatString());
    }

    //开始下载
    private void start(String url) {
        RxDownload.INSTANCE.start(url).subscribe();
    }


    //停止下载
    private void stop(String url) {
        RxDownload.INSTANCE.stop(url).subscribe();
    }

    //安装APK
    private void install(String url) {

        RxDownload.INSTANCE.extension(url, ApkInstallExtension.class).subscribe();

    }

    /**
     * 安装对应apk
     *
     * @param file 安装文件
     */
    protected void installApk(File file) {
        //系统应用界面,源码,安装apk入口
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //7.0以上安装
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.jinwei.kivie.fileprovider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");

        }else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }


    //退出登录
    private void requestOut(String userId){
        LoginOutRequest  request = new LoginOutRequest(userId);
        RetrofitClient.getInstances().requestLoginOut(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            UserPrefs.getInstance().clearUser(); //退出时清空本地仓库 并跳转到登录界面
                            Unicorn.logout();//清空客服端资料
                            UserPrefs.getInstance().setLoginToken("0");
                            UserPrefsFirst.getInstance().setNetWork(ActivityUtils.getNetWorkStatus(mContext));  //退出登录时把网络连接方式也存一下 否则会报空指针
                            UserPrefsFirst.getInstance().setCodeNma(ActivityUtils.getVersionName(mContext));  //退出登录时把版本名称再存一次 否则会报空指针
                            UserPrefsFirst.getInstance().setFirst(ActivityUtils.getVersionCode(RegardActivity.this));  //退出登录的时候把当前版本号再存一次，以免被清掉 否则会报空指针
                            mActivityUtils.startActivity(LoginActivity.class,"login","login");
                            Intent intent = new Intent();
                            intent.setAction("homePage");
                            sendBroadcast(intent);
                            finish();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

}
