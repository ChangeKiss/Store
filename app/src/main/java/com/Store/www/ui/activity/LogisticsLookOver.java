package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.LookLogisticsRequest;
import com.Store.www.entity.LookLogisticsResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.LogisticsAdapter;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 物流查询界面
 */

public class LogisticsLookOver extends BaseToolbarActivity {
    @BindView(R.id.lr_look)
    LRecyclerView mLrLook;
    @BindView(R.id.nodata)
    RelativeLayout mNodata;

    LRecyclerViewAdapter viewAdapter;
    LogisticsAdapter mAdapter;
    private String mOrderNumber;
    private String lookType;  //查看类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_logistics_look_over;
    }

    @Override
    public void initView() {
        initToolbar(this,true,R.string.look_adders);
        ActivityCollector.addActivity(this);
        lookType = getIntent().getStringExtra("type");
        mOrderNumber = getIntent().getStringExtra("orderNumber");
        initAdapter();
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

    //初始化适配器
    private void initAdapter(){
        mAdapter = new LogisticsAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLrLook.setLayoutManager(new LinearLayoutManager(this));  //设置布局管理器
        mLrLook.setAdapter(viewAdapter);  //绑定适配器
        mLrLook.setPullRefreshEnabled(false);  //关闭下拉刷新
        if (!TextUtils.isEmpty(lookType) && lookType.equals("Th")){
            getPickUpLogistics(mOrderNumber);
        }else {
            getLogisticsMessage(mOrderNumber);
        }
    }

    //获取提货物流信息
    private void getPickUpLogistics(String orderNumber){
        LookLogisticsRequest request = new LookLogisticsRequest(orderNumber);
        RetrofitClient.getInstances().requestPickUpLookLogistics(request).enqueue(new UICallBack<LookLogisticsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(LookLogisticsResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mNodata.setVisibility(View.GONE);
                            mAdapter.setDataList(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            break;
                        default:
                            mNodata.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });

    }

    //获取物流信息
    private void getLogisticsMessage(String orderNumber){
        LookLogisticsRequest request = new LookLogisticsRequest(orderNumber);
        RetrofitClient.getInstances().requestLookLogistics(request).enqueue(new UICallBack<LookLogisticsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(LookLogisticsResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mNodata.setVisibility(View.GONE);
                            mAdapter.setDataList(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            break;
                        default:
                            mNodata.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });

    }
}
