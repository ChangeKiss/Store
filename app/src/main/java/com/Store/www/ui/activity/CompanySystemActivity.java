package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.CompanySystemResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.SystemAdapter;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 公司制度界面
 */
public class CompanySystemActivity extends BaseToolbarActivity implements SystemAdapter.OnItemClickListener{
    @BindView(R.id.rl_system)
    RecyclerView mRy;

    SystemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_company_system;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.company_system);
        mAdapter = new SystemAdapter(this,this);
        mRy.setLayoutManager(new LinearLayoutManager(this));
        mRy.setAdapter(mAdapter);
        getSystem();
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

    //获取公司制度
    private void getSystem(){
        RetrofitClient.getInstances().getSystem().enqueue(new UICallBack<CompanySystemResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(CompanySystemResponse bean) {
                switch (bean.getResult()){
                    case 200:
                        if (isTop){
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });
    }

    //item点击事件
    @Override
    public void ItemClickListener(int position, String content) {
        Intent intent = new Intent(this,SystemDetailsActivity.class);
        intent.putExtra("content",content);
        startActivity(intent);
    }
}
