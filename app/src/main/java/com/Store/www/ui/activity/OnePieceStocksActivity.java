package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.WarehouseStocksResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.OnePieceStocksAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;

/**
 * 单件商品库存
 */
public class OnePieceStocksActivity extends BaseToolbarActivity {
    @BindView(R.id.ry_one_piece)
    RecyclerView mRy;
    @BindView(R.id.tv_size_location)
    TextView mTvSizeLocation;
    @BindView(R.id.tv_stocks_number)
    TextView mTvStocksNumber;

    private String toolbarTitle,productNo;
    private int repositoryId,type;
    OnePieceStocksAdapter mAdapter;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_one_piece_stocks;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        toolbarTitle = getIntent().getStringExtra("title");
        repositoryId = getIntent().getIntExtra("repositoryId",0);
        type = getIntent().getIntExtra("type",type);
        productNo = getIntent().getStringExtra("productNo");
        initToolbar(this,true,toolbarTitle);
        mAdapter = new OnePieceStocksAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(mAdapter);
        setViewLocation();
        getOnePiece();
    }

    //动态设置控件的位置
    private void setViewLocation(){
        params=(LinearLayout.LayoutParams) mTvSizeLocation.getLayoutParams();
        params=(LinearLayout.LayoutParams) mTvStocksNumber.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3;
        mTvSizeLocation.setLayoutParams(params);
        mTvStocksNumber.setLayoutParams(params);

    }

    //获取单件商品的库存
    private void getOnePiece(){
        RetrofitClient.getInstances().getWarehouse(repositoryId,type,productNo).enqueue(new UICallBack<WarehouseStocksResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(WarehouseStocksResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        mAdapter.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }
}
