package com.Store.www.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.EntityWarehouseResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.AddRetailStockAdapter;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 添加零售记录仓库界面
 * */

public class AddRetailRecordActivity extends BaseToolbarActivity implements AddRetailStockAdapter.ClickListener{
    @BindView(R.id.rv_add_retail_stock)
    LRecyclerView mRvAddRetailStock;  //添加零售仓库列表

    AddRetailStockAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    private CommonHeader mCommonHeader;  //头布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_add_retail_record;
    }

    @Override
    public void initView() {
        initToolbar(this,true,R.string.add_retail_record);
        ActivityCollector.addActivity(this);
        initHead();
        IntentFilter filter = new IntentFilter();
        filter.addAction("addOver");
        registerReceiver(new BroadCast(),filter);
    }


    //注册广播并接收
    class BroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo.isAvailable() && networkInfo!=null){
                if (intent.getAction().equals("addOver")){
                    finish();
                }
            }
        }
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

    //初始化头布局
    private void initHead(){
        mCommonHeader = new CommonHeader(mContext,R.layout.item_add_retail_head);
        mAdapter = new AddRetailStockAdapter(this,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(mCommonHeader);
        mRvAddRetailStock.setLayoutManager(new LinearLayoutManager(this));
        mRvAddRetailStock.setPullRefreshEnabled(false);
        mRvAddRetailStock.setAdapter(viewAdapter);
        getEntityWarehouseStock();
    }

    //获取实体仓库列表
    private void getEntityWarehouseStock(){
        RetrofitClient.getInstances().getEntityWarehouse(mUserId).enqueue(new UICallBack<EntityWarehouseResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(EntityWarehouseResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }

    //实体仓库Item的点击事件
    @Override
    public void setOnItemClickListener(int position, int repId, String name, String province, String city, String area, String address) {
        Intent intent = new Intent(this,EntityComeStockActivity.class);
        intent.putExtra("title",name);
        intent.putExtra("id",repId);
        startActivity(intent);
    }
}
