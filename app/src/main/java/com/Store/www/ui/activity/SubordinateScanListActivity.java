package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.SubordinateScanResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.SubordinateScanAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 下级扫码列表
 */

public class SubordinateScanListActivity extends BaseToolbarActivity implements OnRefreshListener,OnLoadMoreListener {
    @BindView(R.id.scan_lrv)
    LRecyclerView mScanLrv;  //扫描列表
    @BindView(R.id.layout_no_data)
    RelativeLayout mLayoutNoData;   //无数据布局

    SubordinateScanAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_subordinate_scan_list;
    }

    @Override
    public void initView() {
        initToolbar(this,true,"下级扫码列表");
        ActivityCollector.addActivity(this);
        initAdapter();
    }

    private void initAdapter(){
        mAdapter = new SubordinateScanAdapter(mContext);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mScanLrv.setLayoutManager(new LinearLayoutManager(this));
        mScanLrv.setAdapter(viewAdapter);
        mScanLrv.setOnRefreshListener(this);
        mScanLrv.setOnLoadMoreListener(this);
        getSubordinateScanList(mUserId,mPageIndex,mCountPerPage);
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

    //获取下级扫码列表
    private void getSubordinateScanList(int userId,int pageIndex,int countPerPage){
        if (pageIndex ==1){
            DialogLoading.shows(mContext,"加载中...");
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        RetrofitClient.getInstances().getSubordinateScan(userId,pageIndex,countPerPage).enqueue(new UICallBack<SubordinateScanResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(SubordinateScanResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mLayoutNoData.setVisibility(View.GONE);
                            mScanLrv.setVisibility(View.VISIBLE);
                            mScanLrv.refreshComplete(mCountPerPage);
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            break;
                        case 2:
                            mScanLrv.setNoMore(true);
                            showToast(bean.getErrMsg());
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex ++;
        getSubordinateScanList(mUserId,mPageIndex,mCountPerPage);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        getSubordinateScanList(mUserId,mPageIndex,mCountPerPage);
    }
}
