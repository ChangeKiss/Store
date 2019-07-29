package com.Store.www.ui.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Store.www.base.BaseLazyLoadFragment;
import com.Store.www.ui.commom.DialogLoading;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseFragment;
import com.Store.www.entity.NewsResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.activity.NewsMoreActivity;
import com.Store.www.ui.adapter.NewsTitleAdapter;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 资讯新闻碎片
 */

//
public class NewsFragment extends BaseLazyLoadFragment implements NewsTitleAdapter.OnItemClickListener, OnRefreshListener {

    @BindView(R.id.lv_news)
    LRecyclerView mLvNews;
    @BindView(R.id.nodata)
    RelativeLayout mNodata;
    Unbinder unbinder;

    NewsTitleAdapter mAdapterTitle;
    LRecyclerViewAdapter viewAdapter;
    private int titleId;
    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    public void onLazyLoad() {
        initAdapter();
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        manager = getFragmentManager();
        return view;
    }

    @Override
    public void initEvent() {

    }

    //初始化适配器
    private void initAdapter() {
        mAdapterTitle = new NewsTitleAdapter(mContext, this);
        viewAdapter = new LRecyclerViewAdapter(mAdapterTitle);
        mLvNews.setLayoutManager(new LinearLayoutManager(mContext));
        mLvNews.setAdapter(viewAdapter);
        mLvNews.setOnRefreshListener(this);
        mLvNews.setLoadMoreEnabled(false);//关闭上拉加载
        getNews();
    }

    //网络请求资讯新闻
    private void getNews() {
        DialogLoading.shows(mContext, R.string.hint_loading);
        RetrofitClient.getInstances().getNewsList().enqueue(new UICallBack<NewsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow) {
                    mLvNews.refreshComplete(mCountPerPage);  //数据获取失败调次方法来刷新完成
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(NewsResponse bean) {
                if (mIsTopShow) {
                    switch (bean.getReturnValue()) {
                        case 1:
                            mAdapterTitle.getDataList().clear();
                            mAdapterTitle.addAll(bean.getData());
                            mAdapterTitle.notifyDataSetChanged();
                            mLvNews.refreshComplete(bean.getData().size());
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d("onPause");
        mIsTopShow = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsTopShow = true;
        LogUtils.d("onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unbinder = null;
    }


    //Item更多的点击事件
    @Override
    public void ItemClickListener(int position, int typeNumber, String title) {
        Intent intent = new Intent(getActivity(), NewsMoreActivity.class);
        intent.putExtra("typeNumber", typeNumber);
        intent.putExtra("title", title);
        LogUtils.d("标题==" + title);
        startActivity(intent);

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        getNews();
    }

    /*@OnClick({R.id.tv_title_toolbar, R.id.tv_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title_toolbar:
                break;
            case R.id.tv_circle:  //圈子
                break;
        }
    }*/
}
