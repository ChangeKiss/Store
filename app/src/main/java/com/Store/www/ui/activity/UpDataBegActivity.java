package com.Store.www.ui.activity;

import android.content.Intent;
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
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.ConsentUpRequest;
import com.Store.www.entity.UpDataBegResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.UpDataBegAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 升级请求界面
 */
public class UpDataBegActivity extends BaseToolbarActivity implements UpDataBegAdapter.onButtonOnclickListener,
        OnRefreshListener,OnLoadMoreListener{
    @BindView(R.id.nodata)
    RelativeLayout mNoData;  //无数据布局
    @BindView(R.id.lv_upData_beg)
    LRecyclerView mLvUpDataBeg;  //升级请求列表

    LRecyclerViewAdapter viewAdapter;
    UpDataBegAdapter mAdapter;

    private List<UpDataBegResponse.DataBean> been = new ArrayList<>();
    UpDataBegResponse.DataBean dataBean;
    private int mThisIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_up_data_beg;
    }

    @Override
    public void initView() {
        initToolbar(this,true,R.string.apply_for_beg);
        ActivityCollector.addActivity(this);
        initAdapter();
    }

    //初始化适配器
    private void initAdapter(){
        mAdapter = new UpDataBegAdapter(this,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLvUpDataBeg.setLayoutManager(new LinearLayoutManager(this));
        mLvUpDataBeg.setAdapter(viewAdapter);
        mLvUpDataBeg.setOnRefreshListener(this);
        mLvUpDataBeg.setOnLoadMoreListener(this);
        mLvUpDataBeg.setFooterViewHint("正在加载","我是有底线的","网络没了..");
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
        getBegUpInformation(mCountPerPage,mPageIndex);
    }



    //获取请求升级的代理信息
    private void getBegUpInformation(int perPage,int index){
        if (index==1){
            DialogLoading.shows(mContext,"加载中...");
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        RetrofitClient.getInstances().getUpBeg(mUserId,perPage,index).enqueue(new UICallBack<UpDataBegResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }
            @Override
            public void OnRequestSuccess(UpDataBegResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mNoData.setVisibility(View.GONE);
                            mAdapter.setDataList(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            mLvUpDataBeg.refreshComplete(mCountPerPage);  //一页加载的数量
                            break;
                        case 8:
                            //LogUtils.d("无数据时Data的长度=="+mAdapter.getDataList().size());
                            if (mAdapter.getDataList().size()!=0){
                                mLvUpDataBeg.setNoMore(true);
                            }else {
                                mNoData.setVisibility(View.VISIBLE);
                            }
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //拒绝的点击事件
    @Override
    public void onRejectClickListener(int position, int id) {
        List<UpDataBegResponse.DataBean> beanList = mAdapter.getDataList();
        dataBean = new UpDataBegResponse.DataBean();
        for (int i=0;i<beanList.size();i++){
            dataBean.setApplyLevelName(beanList.get(position).getApplyLevelName());
            dataBean.setCode(beanList.get(position).getCode());
            dataBean.setCurrentLevelName(beanList.get(position).getCurrentLevelName());
            dataBean.setHeadPicture(beanList.get(position).getHeadPicture());
            dataBean.setId(beanList.get(position).getId());
            dataBean.setName(beanList.get(position).getName());
            dataBean.setStatus(beanList.get(position).getStatus());
            dataBean.setTime(beanList.get(position).getTime());
        }
        LogUtils.d("当前拒绝代理姓名=="+dataBean.getName());
        Intent intent = new Intent(this,RejectBegActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("UpDataBean",dataBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //同意的点击事件
    @Override
    public void onConsentClickListener(int position, int id) {
        requestConsentUp(id);
    }

    //同意升级
    private void requestConsentUp(int id){
        ConsentUpRequest request = new ConsentUpRequest(id);
        RetrofitClient.getInstances().requestConsentUp(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        getBegUpInformation(mCountPerPage,mThisIndex);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        mThisIndex = mPageIndex;
        getBegUpInformation(mCountPerPage,mPageIndex);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        getBegUpInformation(mCountPerPage,mPageIndex);
    }
}
