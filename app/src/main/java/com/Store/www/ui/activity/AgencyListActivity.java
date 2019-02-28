package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AgencyQueryResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.AgencyListAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 代理查询界面
 */
public class AgencyListActivity extends BaseToolbarActivity implements AgencyListAdapter.OnClickListener,
        TextWatcher, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.et_toolbar_search)
    EditText mEtToolbarSearch;   //搜索输入框
    @BindView(R.id.tv_toolbar_search)
    TextView mTvToolbarSearch;  //搜索
    @BindView(R.id.iv_toolbar_delete)
    ImageView mIvToolbarDelete;  //删除
    @BindView(R.id.rl_agency_list)
    LRecyclerView mRy;

    AgencyListAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    private final int TYPE = 1;
    private final int TYPE_TWO = 3;
    private String mSeek,isId,mCode,superiorUserId;
    private int mIndex = 1;
    private String queryType; //查询类型
    private String mAgentNumber; // 代理编号
    private boolean isTop = false;  //用来判断当前界面是否处于最上层（可见）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_agency_list;
    }

    @Override
    public void initView() {
        initToolbar(this, true, R.string.agency_query);
        ActivityCollector.addActivity(this);
        queryType = getIntent().getStringExtra("type");
        mAgentNumber = getIntent().getStringExtra("agentNumber");
        mEtToolbarSearch.addTextChangedListener(this);
        mAdapter = new AgencyListAdapter(this, this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRy.setLayoutManager(new LinearLayoutManager(this));
        mRy.setAdapter(viewAdapter);
        mRy.setOnRefreshListener(this);
        mRy.setOnLoadMoreListener(this);
        mRy.setHeaderViewColor(R.color.redColorBackground, R.color.textColorBlack, R.color.colorLucency);
        mRy.setFooterViewColor(R.color.redColorBackground, R.color.textColorBlack, R.color.colorLucency);
        mRy.setFooterViewHint("正在加载", "别扯了.到底了", "网络没了..");
        mRy.setPullRefreshEnabled(false);
        if (queryType!=null&&queryType.equals("results")){
            getSearchAgency(mAgentNumber,userId,6,mPageIndex);
            mEtToolbarSearch.setText(mAgentNumber);
        }else {
            getAgency(userId, TYPE,mPageIndex);
            mEtToolbarSearch.setText("");
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

    //获取上级代理信息
    private void getAgencyList(String parentCode,String userId, final int type,int pageIndex) {
        if (pageIndex==1){
            DialogLoading.shows(mContext,"加载中...");
        }
        RetrofitClient.getInstances().getAgencyList(parentCode, pageIndex, mCountPerPage,userId).enqueue(new UICallBack<AgencyQueryResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(AgencyQueryResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        mAdapter.setFlag(type);
                        if (mAdapter.getFlag()!=0){//刷新
                            mAdapter.getDataList().clear();
                            mAdapter.addAll(bean.getData());
                            mRy.refreshComplete(mCountPerPage);
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setType(bean.getType());
                            if (bean.getType()==1){
                                superiorUserId = String.valueOf(bean.getData().get(0).getId());
                                mAdapter.setFlag(5);
                            }
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }



    //获取下级代理
    private void getAgency(final String userId, final int type,int pageIndex){
        if (pageIndex==1){
            DialogLoading.shows(mContext,"加载中...");
        }
        RetrofitClient.getInstances().getLowerAgency(userId,pageIndex,mCountPerPage).enqueue(new UICallBack<AgencyQueryResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(AgencyQueryResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        LogUtils.d("当前活动是否还存在"+isTop);
                        if (isTop){ //如果当前活动还存在就加载数据
                            mRy.setPullRefreshEnabled(true);
                            isId = userId;
                            mAdapter.setFlag(type);
                            mIndex = mPageIndex;
                            if (mPageIndex==1){
                                mAdapter.getDataList().clear();
                            }
                            mAdapter.addAll(bean.getData());
                            mRy.refreshComplete(mCountPerPage);
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setType(bean.getType());
                        }
                        break;
                    case 2:
                        if (isTop){
                            if (mPageIndex==1){
                                showToast(R.string.noData);
                                mRy.setLoadMoreEnabled(true);
                                return;
                            }
                            showToast(R.string.noData);
                            mRy.setNoMore(true);
                        }
                        break;
                }
            }
        });
    }



    //查自己
    private void getMeAgency(String userId,final int type){
        RetrofitClient.getInstances().getMeAgency(userId,mPageIndex,mCountPerPage).enqueue(new UICallBack<AgencyQueryResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(AgencyQueryResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            mAdapter.setFlag(type);
                            mAdapter.setType(bean.getType());
                            if (mPageIndex==1){
                                mAdapter.getDataList().clear();
                            }
                            mAdapter.addAll(bean.getData());
                            mRy.refreshComplete(mCountPerPage);
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 2:
                        showToast(R.string.noData);
                        mRy.setNoMore(true);
                        break;
                }
            }
        });
    }


    //搜索代理
    private void getSearchAgency(String seek, String userId, final int type,int pageIndex){
        if (pageIndex==1){
            DialogLoading.shows(mContext,"加载中...");
        }
        RetrofitClient.getInstances().getSearchAgency(seek,pageIndex,mCountPerPage,userId).enqueue(new UICallBack<AgencyQueryResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(AgencyQueryResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            mAdapter.setFlag(type);
                            mIndex = mPageIndex;
                            mAdapter.setType(bean.getType());
                            if (mPageIndex==1){
                                mAdapter.getDataList().clear();
                            }
                            mAdapter.addAll(bean.getData());
                            mRy.refreshComplete(mCountPerPage);
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setType(bean.getType());
                        }
                        break;
                    case 2:
                        if (isTop){
                            if (mPageIndex==1){
                                showToast(R.string.noData);
                                mRy.setLoadMoreEnabled(true);
                                return;
                            }
                            showToast(R.string.noData);
                            mRy.setNoMore(true);
                        }
                        break;
                }

            }
        });
    }

    //查看上级的点击事件
    @Override
    public void OverClickListener(int position, String parentCode,int id) {
        mPageIndex = 1;
        mCode = parentCode;
        if (mAdapter.getType()==1){ //查到最顶级时
            showToast(R.string.you_have_no_right);
            return;
        }
        mAdapter.getDataList().clear();
        getAgencyList(parentCode,userId,4,mPageIndex);
    }

    //查看下级的点击事件
    @Override
    public void LowerClickListener(int position, int id) {
        mPageIndex=1;
        LogUtils.d("superiorUserId=="+superiorUserId);
        if (mAdapter.getType()==1){  //最顶级时只能查看自己
            if (superiorUserId.equals(userId)){ //如果最顶级的id和用户id一致，就直接查下级
                getAgency(String.valueOf(id),3,mPageIndex);
            }else { //否则查自己
                getMeAgency(userId,2);
            }
            return;
        }
        getAgency(String.valueOf(id),3,mPageIndex);
    }

    //拨打电话
    @Override
    public void CallClickListener(int position, final String phone) {
        AlertDialog callDialog = new AlertDialog.Builder(this).setTitle("拨号").setMessage("是否拨出" + phone + "")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", null)
                .create();
        callDialog.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //搜索框输入监听
    @Override
    public void afterTextChanged(Editable s) {
        mSeek = mEtToolbarSearch.getText().toString();
        boolean isViewEnable = !TextUtils.isEmpty(mSeek);
        mTvToolbarSearch.setEnabled(isViewEnable);
        mIvToolbarDelete.setEnabled(isViewEnable);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex= mIndex+1;
        LogUtils.d("加载flag="+mAdapter.getFlag());
        switch (mAdapter.getFlag()){
            case 1:
                getAgency(userId, 1,mPageIndex);
                break;
            case 2:
                getMeAgency(userId,2);
                break;
            case 3:
                getAgency(isId,3,mPageIndex);
                break;
            case 4:
                getAgencyList(mCode,userId,4,mPageIndex);
                break;
            case 6:
                getSearchAgency(mSeek,userId,6,mPageIndex);
                break;
        }
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex=1;
        LogUtils.d("刷新flag="+mAdapter.getFlag());
        switch (mAdapter.getFlag()){
            case 1:
                getAgency(userId, TYPE,mPageIndex);
                break;
            case 2:
                getMeAgency(userId,2);
                break;
            case 3:
                getAgency(isId,TYPE_TWO,mPageIndex);
                break;
            case 4:
                getAgencyList(mCode,userId,4,mPageIndex);
                break;
            case 5:
                getAgencyList(mCode,userId,5,mPageIndex);
                break;
            case 6:
                getSearchAgency(mSeek,userId,6,mPageIndex);
                break;
        }

    }

    //点击事件
    @OnClick({R.id.tv_toolbar_search, R.id.iv_toolbar_delete,R.id.iv_toolbar_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_search: //搜索
                mPageIndex =1;
                getSearchAgency(mSeek,userId,6,mPageIndex);
                break;
            case R.id.iv_toolbar_delete:
                if (mAdapter.getFlag()==6){
                    mEtToolbarSearch.setText("");
                    return;
                }
                mEtToolbarSearch.setText("");
                mAdapter.getDataList().clear();
                getAgency(userId, TYPE,mPageIndex);
                break;
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        LogUtils.d("返回键flag="+mAdapter.getFlag());
        switch (mAdapter.getFlag()){
            case 5:
                mEtToolbarSearch.setText("");
                mAdapter.getDataList().clear();
                getAgency(userId, TYPE,mPageIndex);
                break;
            default:
                finish();
                break;
        }
    }
}
