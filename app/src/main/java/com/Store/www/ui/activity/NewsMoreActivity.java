package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.NewsMoreRequest;
import com.Store.www.entity.NewsMoreResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.NewsMoreAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 新闻资讯更多界面
 */
public class NewsMoreActivity extends BaseToolbarActivity implements NewsMoreAdapter.OnContentClickListener,OnRefreshListener,OnLoadMoreListener{
    @BindView(R.id.rv_more_news)
    LRecyclerView mRvMoreNews;

    NewsMoreAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    private String titles;
    private int typeNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_news_more;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        titles = getIntent().getStringExtra("title");
        typeNumber = getIntent().getIntExtra("typeNumber",0);
        initToolbar(this,true,titles);
        mAdapter = new NewsMoreAdapter(this,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRvMoreNews.setLayoutManager(new LinearLayoutManager(this));
        mRvMoreNews.setOnRefreshListener(this);
        mRvMoreNews.setOnLoadMoreListener(this);
        mRvMoreNews.setAdapter(viewAdapter);
        requestMoreNews(mPageIndex,mCountPerPage);
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

    //获取更多新闻资讯
    private void requestMoreNews(int index,int page){
        if (index==1){
            DialogLoading.shows(mContext,"加载中...");
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        NewsMoreRequest request = new NewsMoreRequest(typeNumber,index,page);
        RetrofitClient.getInstances().requestNewsMore(request).enqueue(new UICallBack<NewsMoreResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(NewsMoreResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            mRvMoreNews.refreshComplete(mCountPerPage);
                            if (bean.getData().size()<10){
                                mRvMoreNews.setNoMore(true);
                            }
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //item的点击事件
    @Override
    public void ContentClickListener(int position, int infoId, String title,String author) {
        Intent intent = new Intent(this, NewsWebActivity.class);
        intent.putExtra("infoid",infoId);
        intent.putExtra("title",title);
        intent.putExtra("author",author);
        intent.putExtra("bigtitle",titles);
        startActivity(intent);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        requestMoreNews(mPageIndex,mCountPerPage);
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        requestMoreNews(mPageIndex,mCountPerPage);
    }
}
