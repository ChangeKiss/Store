package com.Store.www.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.SizeAdjustResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.SizeAdjustAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 尺码调整的界面
 */
public class SizeAdjustActivity extends BaseToolbarActivity implements OnRefreshListener,OnLoadMoreListener{
    @BindView(R.id.rl_size_adjust)
    LRecyclerView mRy;
    @BindView(R.id.layout_size_adjust)
    LinearLayout mLayoutSizeAdjust;
    @BindView(R.id.layout_no_data)
    LinearLayout mLayoutNoData;  //无数据时的布局

    LinearLayout.LayoutParams params;
    LRecyclerViewAdapter viewAdapter;
    SizeAdjustAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_size_adjust;

    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.size_adjust_bill);
        mAdapter = new SizeAdjustAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(viewAdapter);
        mRy.setOnRefreshListener(this); //下拉刷新
        setView();
    }

    //设置无数据布局的高
    private void setView(){
        params = (LinearLayout.LayoutParams) mLayoutNoData.getLayoutParams();
        if (Build.VERSION.SDK_INT>=25){  //如果系统版本是7.0 及以上
            params.height = UserPrefs.getInstance().getHeight()-552;
        }else {
            params.height = UserPrefs.getInstance().getHeight()-339;
        }
        LogUtils.d("设置的高=="+params.height);
        mLayoutNoData.setLayoutParams(params);
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
        getSizeAdjust(mPageIndex);

    }

    //获取尺码调整单
    private void getSizeAdjust(int pageIndex){
        if (pageIndex==1){
            DialogLoading.shows(mContext,"加载中...");
        }
        RetrofitClient.getInstances().getSizeBill(mUserId,mCountPerPage,pageIndex).enqueue(new UICallBack<SizeAdjustResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    mLayoutSizeAdjust.setEnabled(false);
                }
            }

            @Override
            public void OnRequestSuccess(SizeAdjustResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            mRy.setPullRefreshEnabled(true);
                            mLayoutSizeAdjust.setEnabled(true);
                            LogUtils.d("尺码调整单数据"+bean.getData().size());
                            if (bean.getData().size()>0){ //有数据，加载数据
                                mLayoutNoData.setVisibility(View.GONE);
                                mAdapter.getDataList().clear();
                                mAdapter.addAll(bean.getData());
                                mRy.refreshComplete(mCountPerPage);//加载完成时
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    case 2:
                        if (isTop){
                            showToast(bean.getErrMsg());
                            mLayoutNoData.setVisibility(View.VISIBLE);
                            mRy.setPullRefreshEnabled(false);
                        }
                        break;
                    default:
                        if (isTop)showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }


    //新增尺码调整单的点击事件
    @OnClick(R.id.layout_size_adjust)
    public void onViewClicked() {
        mActivityUtils.startActivity(AdjustSizeRepertoryActivity.class);
    }


    //上拉加载
    @Override
    public void onLoadMore() {

    }
    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        getSizeAdjust(mPageIndex);
    }
}
