package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.CommodityStocksResponse;
import com.Store.www.entity.EntityWarehouseStocksResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.CommodityStocksAdapter;
import com.Store.www.ui.adapter.EntityWarehouseStockAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 商品库存界面
 */
public class CommodityStocksActivity extends BaseToolbarActivity implements CommodityStocksAdapter.OnItemClickListener,
             EntityWarehouseStockAdapter.ClickListener,OnLoadMoreListener{

    @BindView(R.id.ry_stocks)
    LRecyclerView mRy;
    LRecyclerViewAdapter viewAdapter;

    private int repositoryId,type,repId;
    private String mType;
    private int countPage = 20;  //实体仓库一页加载的商品数量
    CommodityStocksAdapter mAdapter;
    EntityWarehouseStockAdapter mAdapterTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_commodity_stocks;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        repositoryId = getIntent().getIntExtra("repId",0);
        mType = getIntent().getStringExtra("type");
        type = getIntent().getIntExtra("type",0);
        repId = getIntent().getIntExtra("id",0);
        initToolbar(this,true,R.string.commodity_stocks);
        mRy.setPullRefreshEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop =false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        if (mType!=null && mType.equals("entity")){  //实体仓库库存
            mAdapterTwo = new EntityWarehouseStockAdapter(this,this);
            viewAdapter = new LRecyclerViewAdapter(mAdapterTwo);
            mRy.setLayoutManager(new LinearLayoutManager(this));
            mRy.setAdapter(viewAdapter);
            mRy.setNoMore(false);
            mRy.setOnLoadMoreListener(this);
            mPageIndex =1;
            getEntityStocks(mPageIndex,countPage);
        }else {  //云仓库商品库存
            mAdapter = new CommodityStocksAdapter(this,this);
            viewAdapter = new LRecyclerViewAdapter(mAdapter);
            mRy.setLayoutManager(new LinearLayoutManager(this));
            mRy.setAdapter(viewAdapter);
            mRy.setLoadMoreEnabled(false); //关闭上拉加载
            getStocks();  //获取云仓库库存
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    //获取云仓库商品库存
    private void getStocks(){
        RetrofitClient.getInstances().getStocks(repositoryId,type).enqueue(new UICallBack<CommodityStocksResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
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
                        showToast(bean.getErrMsg());
                        break;
                }

            }
        });
    }

    //获取实体仓库商品库存
    private void getEntityStocks(int page, final int countPage){
        if (page==1){
            DialogLoading.shows(mContext,"加载中...");
            mAdapterTwo.getDataList().clear();
            mAdapterTwo.notifyDataSetChanged();
        }
        RetrofitClient.getInstances().getEntityStocks(repId,page,countPage).enqueue(new UICallBack<EntityWarehouseStocksResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(EntityWarehouseStocksResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getMap().size()<countPage){
                                mRy.setNoMore(true);
                                mAdapterTwo.addAll(bean.getMap());
                                mAdapterTwo.notifyDataSetChanged();
                            }else if (bean.getMap()!=null){
                                mAdapterTwo.addAll(bean.getMap());
                                mAdapterTwo.notifyDataSetChanged();
                                mRy.refreshComplete(countPage);  //加载完成数量20
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    //查看单件商品库存
    @Override
    public void setItemClickListener(int position, String productNo,String title) {
        /*Intent intent = new Intent(this,OnePieceStocksActivity.class);
        LogUtils.d("title"+title);
        intent.putExtra("repositoryId",repositoryId);
        intent.putExtra("type",type);
        intent.putExtra("title",title);
        intent.putExtra("productNo",productNo);
        startActivity(intent);*/
    }

    //实体仓库item的点击事件
    @Override
    public void onItemClickListener(int position, int productId,String name) {
        Intent intent = new Intent(this,PickUpRepertoryActivity.class);
        intent.putExtra("type","entityStock");
        intent.putExtra("title",name);
        intent.putExtra("id",repId);
        intent.putExtra("productId",productId);
        startActivity(intent);
    }

    //实体仓库库存的上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        getEntityStocks(mPageIndex,countPage);
    }
}
