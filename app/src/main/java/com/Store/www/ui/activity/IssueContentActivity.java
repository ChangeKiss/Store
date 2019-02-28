package com.Store.www.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 问题内容详情界面
 */
public class IssueContentActivity extends BaseToolbarActivity {
    @BindView(R.id.tv_issue_content)
    TextView mTvIssueContent;


    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_issue_content;

    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.common_issue);
        content = getIntent().getStringExtra("content");
        mTvIssueContent.setText(Html.fromHtml(content.toString()));
    }
}
