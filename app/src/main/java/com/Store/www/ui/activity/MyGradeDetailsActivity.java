package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 我的业绩明细界面
 */
public class MyGradeDetailsActivity extends BaseToolbarActivity {
    @BindView(R.id.rv_grade_details)
    RecyclerView mRvGradeDetails;

    private String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_grade_details;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mTitle = getIntent().getStringExtra("title");
        initToolbar(this,true,mTitle);
    }
}
