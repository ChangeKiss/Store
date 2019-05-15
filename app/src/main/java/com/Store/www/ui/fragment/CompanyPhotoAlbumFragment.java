package com.Store.www.ui.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.Store.www.R;
import com.Store.www.base.BaseFragment;
import com.Store.www.base.BaseLazyLoadFragment;
import com.Store.www.entity.CompanyPhotoResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.activity.PhotoAlbumPictureActivity;
import com.Store.www.ui.adapter.CompanyPhotoAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

/**
 * @author: haifeng
 * @description:  公司相册界面
 */
public class CompanyPhotoAlbumFragment extends BaseLazyLoadFragment implements
        CompanyPhotoAdapter.ClickListener, OnRefreshListener, OnLoadMoreListener {

    RelativeLayout mNoData;
    LRecyclerView mLv;
    private CompanyPhotoAdapter mAdapter;
    private LRecyclerViewAdapter viewAdapter;
    @Override
    public void onLazyLoad() {
        initAdapter();
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_company_photo,container,false);
        mLv = view.findViewById(R.id.lv_company_photo);
        mNoData = view.findViewById(R.id.nodata);
        return view;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mIsTopShow = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsTopShow = false;
    }

    private void initAdapter(){
        mAdapter = new CompanyPhotoAdapter(mContext,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLv.setLayoutManager(new LinearLayoutManager(mContext));
        mLv.setAdapter(viewAdapter);
        mLv.setOnRefreshListener(this);
        mLv.setOnLoadMoreListener(this);
        getPhotoAlbum(mPageIndex,mCountPerPage,false);
    }

    private void getPhotoAlbum(int pageIndex,int countPerPage,boolean lodMore){
        if (pageIndex==1){
            DialogLoading.shows(mContext,"加载中...");
            mLv.setNoMore(false);
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        RetrofitClient.getInstances().getPhotoAlbum(pageIndex,countPerPage).enqueue(new UICallBack<CompanyPhotoResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow)checkNet();
            }

            @Override
            public void OnRequestSuccess(CompanyPhotoResponse bean) {
                if (mIsTopShow){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (lodMore){ //如果是上拉加载
                                if (bean.getData().size()<mCountPerPage){
                                    mAdapter.addAll(bean.getData());
                                    mAdapter.notifyDataSetChanged();
                                    mLv.setNoMore(true);
                                    mLv.refreshComplete(bean.getData().size());
                                    mPageIndex = 1;
                                }
                            }else {
                                if (bean.getData().size()>0){
                                    mNoData.setVisibility(View.GONE);
                                    mAdapter.addAll(bean.getData());
                                    mAdapter.notifyDataSetChanged();
                                    mLv.refreshComplete(mCountPerPage);
                                }else {
                                    mNoData.setVisibility(View.VISIBLE);
                                }
                            }
                            break;
                        default:

                            break;
                    }

                }
            }
        });
    }

    //item的点击事件
    @Override
    public void itemClickListener(int position, String title,int id) {
        mActivityUtils.startActivity(PhotoAlbumPictureActivity.class,"title",title,"id",id);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        getPhotoAlbum(mPageIndex,mCountPerPage,true);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        getPhotoAlbum(mPageIndex,mCountPerPage,false);
    }
}
