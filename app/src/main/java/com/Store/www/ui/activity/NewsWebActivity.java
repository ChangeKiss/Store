package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.DeleteCommentRequest;
import com.Store.www.entity.NewsCommentResponse;
import com.Store.www.entity.NewsDetailsRequest;
import com.Store.www.entity.NewsDetailsResponse;
import com.Store.www.entity.PublishCommentRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.NewsCommentAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新闻资讯的WEB界面
 */
public class NewsWebActivity extends BaseToolbarActivity implements TextWatcher, NewsCommentAdapter.OnClickListener, OnLoadMoreListener {
    @BindView(R.id.news_pb_web)
    ProgressBar mPbWeb;
    @BindView(R.id.wv_news)
    WebView mWvNews;
    @BindView(R.id.tv_news_web_title)
    TextView mTvNewsWebTitle;  //小标题
    @BindView(R.id.layout_time_author)
    RelativeLayout mLayoutTimeAuthor;  //时间和作者
    @BindView(R.id.tv_news_time)
    TextView mTvNewsTime;   //时间
    @BindView(R.id.tv_news_web_author)
    TextView mTvNewsWebAuthor;  //作者
    @BindView(R.id.et_comment)
    EditText mEtComment; //用户输入框
    @BindView(R.id.tv_comment_send)
    TextView mTvCommentSend;  //发送按钮
    @BindView(R.id.comment_Rv)
    LRecyclerView mCommentRv;  //评论列表
    @BindView(R.id.layout_news_comment)
    LinearLayout mLayoutNewsComment;  //发表评论布局


    private String bigTitle, title, author, infoId;//大标题  小标题 作者  新闻ID
    private String mTextContent;  //评论内容
    private int mTextId;  //新闻ID 获取评论列表用
    InputMethodManager inputMethodManager;
    NewsCommentAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_news_web;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        bigTitle = getIntent().getStringExtra("bigtitle");
        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        infoId = String.valueOf(getIntent().getIntExtra("infoid", 0));
        mTextId = getIntent().getIntExtra("infoid", 0);
        initToolbar(this, true, bigTitle);
        mTvNewsWebTitle.setText(title);
        mTvNewsWebAuthor.setText(""+author);
        mEtComment.addTextChangedListener(this);
        initUtil();
        initAdapter();
        requestNews();
    }

    //初始化适配器
    private void initAdapter() {
        mAdapter = new NewsCommentAdapter(this, this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mCommentRv.setLayoutManager(new LinearLayoutManager(this));
        mCommentRv.setAdapter(viewAdapter);
        mCommentRv.setOnLoadMoreListener(this);
        mCommentRv.setPullRefreshEnabled(false);
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

    //新闻资讯详情内容
    private void requestNews() {
        NewsDetailsRequest request = new NewsDetailsRequest(infoId);
        RetrofitClient.getInstances().requestNews(request).enqueue(new UICallBack<NewsDetailsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(NewsDetailsResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()) {
                        case 1:
                            mWvNews.getSettings().setJavaScriptEnabled(true);
                            mWvNews.getSettings().setBuiltInZoomControls(true);
                            mWvNews.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
                            mWvNews.getSettings().setSupportZoom(true);
                            mWvNews.setWebViewClient(new WebViewClient());
                            //mWvNews.loadDataWithBaseURL(null, bean.getData().getUrl(), "text/html", "UTF-8", null);  //加载富文本
                            mWvNews.loadUrl(bean.getData().getUrl());  //加载链接
                            mTvNewsTime.setText(ActivityUtils.YMDTime(bean.getData().getCreatTime()));  //时间
                            mWvNews.setWebChromeClient(new WebChromeClient() {
                                @Override
                                public void onProgressChanged(WebView view, int newProgress) {
                                    super.onProgressChanged(view, newProgress);
                                    if (newProgress == 0) {
                                        mPbWeb.setVisibility(View.VISIBLE);
                                    }
                                    if (mPbWeb != null) {
                                        mPbWeb.setProgress(newProgress);
                                        mPbWeb.postInvalidate();
                                        if (newProgress == 100) {
                                            mPbWeb.setVisibility(View.GONE);
                                            mTvNewsWebTitle.setVisibility(View.VISIBLE);
                                            mLayoutTimeAuthor.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            });
                       /* if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())){ //没有登录
                            getNewsCommentList(0,mTextId, mPageIndex);
                            mLayoutNewsComment.setVisibility(View.GONE);
                        }else {
                            getNewsCommentList(mUserId, mTextId, mPageIndex);
                            mLayoutNewsComment.setVisibility(View.VISIBLE);
                        }*/
                            break;
                    }
                }
            }
        });
    }

    //获取评论列表
    private void getNewsCommentList(int userId, int textId, int pageIndex) {
        RetrofitClient.getInstances().getNewsComment(userId, textId, mCountPerPage, pageIndex).enqueue(new UICallBack<NewsCommentResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(NewsCommentResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        mCommentRv.setNoMore(false);
                        mAdapter.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        mCommentRv.refreshComplete(mCountPerPage);//设置一页加载10条
                        break;
                    case 8:
                        mCommentRv.setNoMore(true);  //加载完成
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //监听键盘缩回


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    //输入文本框的监听
    @Override
    public void afterTextChanged(Editable s) {
        mTextContent = mEtComment.getText().toString();
        if (!TextUtils.isEmpty(mTextContent)) {
            mTvCommentSend.setEnabled(true);
        } else {
            mTvCommentSend.setEnabled(false);
        }
    }

    //删除按钮的点击事件
    @Override
    public void OnDeleteClickListener(int position, int commentId) {
        requestDeleteComment(mUserId,commentId);
    }

    //删除评论
    private void requestDeleteComment(int userId,int commentId){
        DeleteCommentRequest request = new DeleteCommentRequest(userId,commentId);
        RetrofitClient.getInstances().requestDeleteComment(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        mPageIndex=1;
                        mAdapter.getDataList().clear();
                        getNewsCommentList(mUserId,mTextId,mPageIndex);
                        showToast(R.string.delete_ok);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        LogUtils.d("pageIndex=" + mPageIndex);
        getNewsCommentList(mUserId, mTextId, mPageIndex);
    }

    /**
     * 初始化必须工具
     */
    private void initUtil() {
        //初始化输入法
        inputMethodManager = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
    }


    //点击事件
    @OnClick({R.id.tv_comment_send,R.id.et_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_comment_send:  //发表评论
                mEtComment.setFocusable(false); //设置输入框不可聚焦，即失去焦点
                requestPublishComment(mUserId,mTextContent,mTextId);
                if (inputMethodManager.isActive()){  //如果输入法是活动的
                    inputMethodManager.hideSoftInputFromWindow(mEtComment.getWindowToken(),0); //就隐藏输入法
                }
                break;
            case R.id.et_comment:  //***点击输入框
                mEtComment.setFocusable(true);//设置可获取焦点
                mEtComment.setFocusableInTouchMode(true); //设置触摸聚焦
                mEtComment.requestFocus();  //请求焦点
                mEtComment.findFocus();     //获取焦点
                inputMethodManager.showSoftInput(mEtComment,InputMethodManager.SHOW_FORCED); //显示输入法
                break;
        }
    }

    //发表评论
    private void requestPublishComment(final int userId, String content, final int textId){
        PublishCommentRequest request = new PublishCommentRequest(userId,content,textId);
        RetrofitClient.getInstances().requestPublishComment(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.publish_ok);
                        mPageIndex=1;
                        mAdapter.getDataList().clear();
                        getNewsCommentList(mUserId,mTextId,mPageIndex);
                        mEtComment.setText("");
                        mEtComment.setHint(R.string.comment_write_hint);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }
}
