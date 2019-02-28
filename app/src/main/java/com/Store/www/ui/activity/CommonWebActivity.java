package com.Store.www.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;

/**
 * 正常的webActivity用来加载url
 */
public class CommonWebActivity extends BaseToolbarActivity implements View.OnClickListener{
    @BindView(R.id.iv_toolbar_left)
    ImageView mTvToolbarLeft;
    @BindView(R.id.pb_webs)
    ProgressBar mPbWeb;
    @BindView(R.id.wv_common_web)
    WebView mWv;

    private String mUrl;
    private String mTitle;
    private String mType = "default";
    private int title;
    private NetWork netWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_commom_web;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mType = intent.getStringExtra("type");
        LogUtils.d("链接==" + mUrl);
        mTitle = intent.getStringExtra("title");
        title = intent.getIntExtra("title", 0);
        initToolbar(this, true, mTitle);
        initToolbar(this, true, title);
        if (mType!=null&&mType.equals("HTML")){
            loadHtmlWebView(mUrl);
        }else {
            setWebView(mUrl);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("ccb");
        netWork = new NetWork();
        registerReceiver(netWork, filter);

    }


    //接收广播并处理相关事务
    class NetWork extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                finish();
            }
        }
    }

    //加载系统弹出框的新闻链接
    public static void startWebActivity(Context context, String url) {
        Intent intent = new Intent(context, CommonWebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void startWebActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, CommonWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    public static void startWebActivity(Context context, int title, String url) {
        Intent intent = new Intent(context, CommonWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    /**
     * 加载普通的url
     * @param url
     */
    private void setWebView(String url) {
        LogUtils.d("加载中的url" + url);
        mWv.getSettings().setDefaultTextEncodingName("gbk");
        mWv.getSettings().setJavaScriptEnabled(true);
        mWv.setWebViewClient(new WebViewClient());
        mWv.loadUrl(url);
        mWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //这里将textView换成你的progress来设置进度
                if (newProgress == 0) {
                    mPbWeb.setVisibility(View.VISIBLE);
                }
                if (mPbWeb != null) {
                    mPbWeb.setProgress(newProgress);
                    mPbWeb.postInvalidate();
                    if (newProgress == 100) {
                        mPbWeb.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 加载富文本url
     * @param url
     */
    private void loadHtmlWebView(String url){
        mWv.getSettings().setDefaultTextEncodingName("gbk");
        mWv.getSettings().setJavaScriptEnabled(true);
        mWv.setWebViewClient(new WebViewClient());
        mWv.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);  //加载富文本
        //mWv.loadUrl(url);
        mWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //这里将textView换成你的progress来设置进度
                if (newProgress == 0) {
                    mPbWeb.setVisibility(View.VISIBLE);
                }
                if (mPbWeb != null) {
                    mPbWeb.setProgress(newProgress);
                    mPbWeb.postInvalidate();
                    if (newProgress == 100) {
                        mPbWeb.setVisibility(View.GONE);
                    }
                }
            }
        });
    }


    //重写系统的返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mType.equals(AdvertisingDialogActivity.ADVERTISING_TYPE)) {  //如果是广告页过来的
            Intent intentClose = new Intent();
            intentClose.setAction(WelcomeTwoActivity.ADVERTISING_BROADCAST);
            sendBroadcast(intentClose);
            mActivityUtils.startActivity(MainActivity.class);
            finish();
        } else {
            finish();
        }
    }

    //左上角的返回按钮
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_toolbar_left:
                if (mType.equals(AdvertisingDialogActivity.ADVERTISING_TYPE)){  //如果是广告页过来的
                    Intent intentClose = new Intent();
                    intentClose.setAction(WelcomeTwoActivity.ADVERTISING_BROADCAST);
                    sendBroadcast(intentClose);
                    mActivityUtils.startActivity(MainActivity.class);
                    finish();
                }else {
                    finish();
                }
                break;
        }
    }

    /*@OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        if (mType.equals(AdvertisingDialogActivity.ADVERTISING_TYPE)){  //如果是广告页过来的
            Intent intentClose = new Intent();
            intentClose.setAction(WelcomeTwoActivity.ADVERTISING_BROADCAST);
            sendBroadcast(intentClose);
            mActivityUtils.startActivity(MainActivity.class);
            finish();
        }else {
            finish();
        }
    }*/
}
