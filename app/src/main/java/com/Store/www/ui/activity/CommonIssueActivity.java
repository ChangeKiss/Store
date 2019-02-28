package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.CommonIssueResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.CommonAdapter;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 常见问题界面
 */
public class CommonIssueActivity extends BaseToolbarActivity implements CommonAdapter.OnItemClickListener{
    @BindView(R.id.ry_common)
    RecyclerView mRy;

    CommonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_common_issue;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.common_issue);
        mAdapter = new CommonAdapter(this,this);
        mRy.setLayoutManager(new LinearLayoutManager(this));
        mRy.setAdapter(mAdapter);
        getCommonIssue();
    }

    //获取帮助中心内容
    private void getCommonIssue(){
        RetrofitClient.getInstances().getCommonIssue().enqueue(new UICallBack<CommonIssueResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(CommonIssueResponse bean) {
                switch (bean.getResult()){
                    case 200:
                        mAdapter.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }


    //item的点击事件
    @Override
    public void ItemClickListener(int position, String content) {
        Intent intent = new Intent(this,IssueContentActivity.class);
        intent.putExtra("content",content);
        startActivity(intent);
    }
}
