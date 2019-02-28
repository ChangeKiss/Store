package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.CommodityStocksResponse;
import com.Store.www.entity.MyWarehouseResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.AdjustRepertoryAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;

/**
 * 调整尺码的商品库存界面
 */
public class AdjustSizeRepertoryActivity extends BaseToolbarActivity implements AdjustRepertoryAdapter.OnItemClickListener{
    @BindView(R.id.rl_adjust_repertory)
    RecyclerView mRy;
    @BindView(R.id.nodata)
    RelativeLayout mNodata;

    AdjustRepertoryAdapter mAdapter;
    private int repositoryId,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_adjust_size_repertory;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.commodity_stocks);
        mAdapter = new AdjustRepertoryAdapter(this,this);
        mRy.setLayoutManager(new LinearLayoutManager(this));
        mRy.setAdapter(mAdapter);
        getWarehouse();

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

    //获取仓库信息
    private void getWarehouse(){
        RetrofitClient.getInstances().getWarehouse(agentCode).enqueue(new UICallBack<MyWarehouseResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(MyWarehouseResponse bean) {
                LogUtils.d("返回值=="+bean.getReturnValue());
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            if (bean.getData().size()<1){
                                mNodata.setVisibility(View.VISIBLE);
                            }else {
                                mNodata.setVisibility(View.GONE);
                                for (int i=0;i<bean.getData().size();i++){
                                    repositoryId = bean.getData().get(0).getRepId();
                                    type = bean.getData().get(0).getType();
                                    LogUtils.d("仓库ID=="+repositoryId);
                                }
                                getStocks();
                            }
                        }
                        break;
                    default:
                        if (isTop)showToast(bean.getErrMsg());
                        break;
                }
            }
        });

    }

    //获取商品库存
    private void getStocks(){
        RetrofitClient.getInstances().getStocks(repositoryId,type).enqueue(new UICallBack<CommodityStocksResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(CommodityStocksResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        if (isTop)showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }



    //Item的点击事件

    @Override
    public void setItemClickListener(int position,String productNo,String title,int productId) {
        Intent intent = new Intent(this,OnePieceAdjustSizeActivity.class);
        intent.putExtra("repositoryId",repositoryId);
        intent.putExtra("type",type);
        intent.putExtra("title",title);
        intent.putExtra("productNo",productNo);
        intent.putExtra("productId",productId);
        startActivity(intent);
    }
}
