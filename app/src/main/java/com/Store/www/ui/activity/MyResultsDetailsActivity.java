package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.MyResultsDetailsResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.GradeDetailsAdapter;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 我的业绩明细界面
 */
public class MyResultsDetailsActivity extends BaseToolbarActivity {
    @BindView(R.id.rv_my_results_details)
    RecyclerView mRvMyResultsDetails;  //我的业绩明细列表
    RelativeLayout mNoData;

    GradeDetailsAdapter mAdapter;
    private String mTitle;
    private int mYear;
    private String month;
    private String mType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_results_details;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mNoData = (RelativeLayout) findViewById(R.id.nodata);
        mTitle = getIntent().getStringExtra("title")+"业绩明细";
        month = getIntent().getStringExtra("month");
        mYear = getIntent().getIntExtra("year",0);
        initToolbar(this,true,mTitle);
        mAdapter = new GradeDetailsAdapter(this);
        mRvMyResultsDetails.setLayoutManager(new LinearLayoutManager(this));
        mRvMyResultsDetails.setAdapter(mAdapter);
        mType = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(mType)&&mType.equals("bar")){
            getMyResultsDetails(mUserId,month,mYear);
        }else if (!TextUtils.isEmpty(mType)&&mType.equals("shapeWear")){
            getMyShapeWearResultsDetail(mUserId,month,mYear);
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

    //获取内衣业绩明细
    private void getMyResultsDetails(int userId, final String month,int year){
        RetrofitClient.getInstances().getMyResultsDetail(userId,month,year).enqueue(new UICallBack<MyResultsDetailsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(MyResultsDetailsResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            if (bean.getData().size()<1){
                                mNoData.setVisibility(View.VISIBLE);
                            }else {
                                mNoData.setVisibility(View.GONE);
                                mAdapter.addAll(bean.getData());
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //获取塑身衣业绩明细
    private void getMyShapeWearResultsDetail(int agentId,String month,int year){
        RetrofitClient.getInstances().getMyShapeWearResults(agentId,month,year).enqueue(new UICallBack<MyResultsDetailsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(MyResultsDetailsResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData().size()<1){
                                mNoData.setVisibility(View.VISIBLE);
                            }else {
                                mNoData.setVisibility(View.GONE);
                                mAdapter.addAll(bean.getData());
                                mAdapter.notifyDataSetChanged();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
}
