package com.Store.www.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 制度详情界面
 */
public class SystemDetailsActivity extends BaseToolbarActivity {
    @BindView(R.id.pb_web)
    ProgressBar mPbWeb;
    @BindView(R.id.wv_company_system)
    WebView mWvCompanySystem;


    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_system_details;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.company_system);
        content = getIntent().getStringExtra("content");
        mWvCompanySystem.getSettings().setJavaScriptEnabled(true);
        mWvCompanySystem.setWebViewClient(new WebViewClient());
        mWvCompanySystem.loadDataWithBaseURL(null,content, "text/html", "UTF-8", null);
        mWvCompanySystem.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 0) {
                    mPbWeb.setVisibility(View.VISIBLE);
                }
                if (mPbWeb != null){
                    mPbWeb.setProgress(newProgress);
                    mPbWeb.postInvalidate();
                    if (newProgress==100) {
                        mPbWeb.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}
