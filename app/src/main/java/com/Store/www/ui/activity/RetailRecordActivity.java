package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.RetailRecordResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.RetailRecordAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 零售记录界面
 */

public class RetailRecordActivity extends BaseToolbarActivity implements OnRefreshListener,OnLoadMoreListener{
    @BindView(R.id.lt_retail_record)
    LRecyclerView mLR;
    @BindView(R.id.btn_add_retail)
    Button mBtnAddRetail;  //添加出库单按钮

    RetailRecordAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_retail_record;
    }

    @Override
    public void initView() {
        initToolbar(this, true, R.string.retail_record);
        ActivityCollector.addActivity(this);
        initAdapter();
    }

    //初始化适配
    private void initAdapter(){
        mAdapter = new RetailRecordAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLR.setLayoutManager(new LinearLayoutManager(this));
        mLR.setAdapter(viewAdapter);
        mLR.setOnLoadMoreListener(this);
        mLR.setOnRefreshListener(this);
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
        mPageIndex =1;
        getRetailRecord(mPageIndex,mCountPerPage);
    }

    //获取零售记录
    private void getRetailRecord(int pageIndex,int size){
        if (pageIndex==1){
            DialogLoading.shows(mContext,"加载中...");
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        RetrofitClient.getInstances().getRetailRecord(mUserId,pageIndex,size).enqueue(new UICallBack<RetailRecordResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(RetailRecordResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mAdapter.addAll(bean.getList());
                            mAdapter.notifyDataSetChanged();
                            mLR.refreshComplete(mCountPerPage);
                            mLR.setNoMore(true);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @OnClick({R.id.iv_toolbar_right, R.id.btn_add_retail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right:
                break;
            case R.id.btn_add_retail:
                mActivityUtils.startActivity(AddRetailRecordActivity.class);
                break;
        }
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        getRetailRecord(mPageIndex,mCountPerPage);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        getRetailRecord(mPageIndex,mCountPerPage);
    }
}
