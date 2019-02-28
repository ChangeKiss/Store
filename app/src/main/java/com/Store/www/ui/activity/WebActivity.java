package com.Store.www.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.CarouselRequest;
import com.Store.www.entity.CarouselResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 用来加载富文本的webActivity
 */
    //首页轮播图界面
public class WebActivity extends BaseToolbarActivity {
    @BindView(R.id.pb_web)
    ProgressBar mPbWeb;
    @BindView(R.id.wv_home_web)
    WebView mWvHomeWeb;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;

    private String mUrl;
    private String mTitle,mContent,mAuthor;
    private int inforId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCarousel();
    }

    @Override
    public int initLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        inforId = intent.getIntExtra("inforId",0);
        mContent = intent.getStringExtra("content");
        mAuthor = intent.getStringExtra("author");
        initToolbar(this,true,mTitle);
        mTvTitle.setText(mContent);
        mTvAuthor.setText("作者:"+mAuthor);
    }

    public static void startWebActivity(Context context,String title,String url){
        Intent intent = new Intent(context,WebActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }



    //首页轮播图请求页面内容
    private void requestCarousel(){
        CarouselRequest request = new CarouselRequest(inforId);
        RetrofitClient.getInstances().requestCarousel(request).enqueue(new UICallBack<CarouselResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(CarouselResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        mUrl = bean.getData().getContext();
                        mWvHomeWeb.getSettings().setJavaScriptEnabled(true);
                        mWvHomeWeb.setWebViewClient(new WebViewClient());
                        mWvHomeWeb.loadDataWithBaseURL(null,bean.getData().getContext(), "text/html", "UTF-8", null);
                        mWvHomeWeb.setWebChromeClient(new WebChromeClient(){
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

                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }
}
