package com.Store.www.ui.activity;

import android.os.Bundle;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.utils.ActivityCollector;

/**
 * 评论列表界面
 */

public class CommentListActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_comment_list;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.comment);
    }
}
