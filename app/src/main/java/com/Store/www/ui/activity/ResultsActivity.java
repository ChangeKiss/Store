package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.SumResultsResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.TeamResultsAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 业绩查询界面
 */
public class ResultsActivity extends BaseToolbarActivity implements TabLayout.OnTabSelectedListener, ResultsAdapter.OnclickListener {
    @BindView(R.id.tab_results)
    TabLayout mTabResults;
    @BindView(R.id.ry_results)
    RecyclerView mRy;
    @BindView(R.id.nodata)  //无数据界面
    RelativeLayout mNodata;
    @BindView(R.id.tv_toolbar_right)
    ImageView mIvToolbarRight;   //右上角的图标
    @BindView(R.id.vi_line)
    View mViLine;

    ResultsAdapter mAdapter;
    TeamResultsAdapter mAdapterTwo;
    private int mTabNumber;
    private String mMonth;
    private String mType = "bar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_results;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.results_detail);
        mIvToolbarRight.setVisibility(View.VISIBLE);  //右上角我的团队功能
        mIvToolbarRight.setImageResource(R.mipmap.report_forms_icon);
        initTba();
        mAdapter = new ResultsAdapter(this, this);
        mAdapterTwo = new TeamResultsAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        switch (mTabNumber) {
            case 0:
                mRy.setAdapter(mAdapter);
                break;
            case 1:
                mRy.setAdapter(mAdapterTwo);
                break;
        }
    }

    //初始化tab
    private void initTba() {
        mTabResults.addTab(mTabResults.newTab().setText(R.string.team_results));  //内衣业绩
        mTabResults.addTab(mTabResults.newTab().setText(R.string.shape_wear_results));  //塑身衣业绩
        mTabResults.addOnTabSelectedListener(this);
        if (mTabNumber == 0) {
            mRy.setBackgroundResource(R.drawable.shape_null_background);
            getMyResults();
        } else if (mTabNumber == 1) {
            mRy.setBackgroundResource(R.drawable.shape_text_frame);
            getTeamResults();
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

    //获取内衣业绩
    private void getMyResults() {
        DialogLoading.shows(mContext, "加载中...");
        RetrofitClient.getInstances().getSumResults(mUserId).enqueue(new UICallBack<SumResultsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(SumResultsResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()) {
                        case 1:

                            mRy.setVisibility(View.VISIBLE);
                            mViLine.setVisibility(View.VISIBLE);
                            mTabResults.setVisibility(View.VISIBLE);
                            mNodata.setVisibility(View.GONE);
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();

                            break;
                        case 5:

                            mNodata.setVisibility(View.VISIBLE);
                            mTabResults.setVisibility(View.INVISIBLE);
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

    //获取塑身衣业绩
    private void getTeamResults() {
        DialogLoading.shows(mContext, "加载中...");
        RetrofitClient.getInstances().getShapeWearResults(mUserId).enqueue(new UICallBack<SumResultsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(SumResultsResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        if (isTop) {
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:

                        break;
                }
            }
        });
    }


    //顶部Tab的监听
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mTabNumber = tab.getPosition();
        switch (mTabNumber) {
            case 0:
                mType = "bar";
                mAdapter.getDataList().clear();
                mRy.setBackgroundResource(R.drawable.shape_null_background);
                mRy.setAdapter(mAdapter);
                getMyResults();
                break;
            case 1:
                mType = "shapeWear";
                mAdapter.getDataList().clear();
                mAdapter.notifyDataSetChanged();
                mRy.setBackgroundResource(R.drawable.shape_null_background);
                mRy.setAdapter(mAdapter);
                getTeamResults();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //item的点击事件
    @Override
    public void OnItemClickListener(int position, String month, String TimeTitle,int year) {
        mMonth = month;
        Intent intent = new Intent(this, MyResultsDetailsActivity.class);
        intent.putExtra("year",year);
        intent.putExtra("month", mMonth);
        intent.putExtra("type",mType);
        intent.putExtra("title", TimeTitle);
        startActivity(intent);
    }

    @OnClick({R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_right:
                mActivityUtils.startActivity(MyTeamActivity.class);
                break;
        }
    }
}
