package com.Store.www.base;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.Store.www.R;
import com.Store.www.utils.LogUtils;


/**
 * Created by junYong on 2017/8/29.
 * <p>
 * 注意：在MyBaseActivity类中增加了属性 protected Activity activity;
 * <p>
 * 所有抽象方法 有就返回值 没有就返回默认值
 */

public abstract class BaseToolbarActivity extends BaseActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    private OnToolBarRightClickListener mListener;
    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private TextView mTitleTextView;
    private TextView mTvRightTextView;
    private ImageView mRightImageViewShare;
    private ImageView mRightImageViewClose;
    private Toolbar mToolbar;

    public void initToolbarLayout(Activity activity, int title) {
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        mLeftImageView = (ImageView) mToolbar.findViewById(R.id.iv_toolbar_left);
        mTitleTextView = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        mRightImageView = (ImageView) mToolbar.findViewById(R.id.tv_toolbar_right);
        mTvRightTextView = (TextView) mToolbar.findViewById(R.id.iv_toolbar_right);
        mRightImageViewShare = (ImageView) mToolbar.findViewById(R.id.iv_toolbar_right_share);
        mRightImageViewClose = (ImageView) mToolbar.findViewById(R.id.iv_toolbar_right_close);
        mToolbar.setTitle("");
        if (title != 0) {
            mTitleTextView.setText(title);
        }
        setSupportActionBar(toolbar);
    }

   /* public void intoToolbraLayout(Activity activity,int title){
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        mLeftImageView = (ImageView) mToolbar.findViewById(R.id.iv_toolbar_left);
        mTitleTextView = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        mRightImageView = (TextView) mToolbar.findViewById(R.id.tv_toolbar_right);
        mToolbar.setTitle("");
        if (title != 0) {
            mRightImageView.setText(title);
        }
        setSupportActionBar(toolbar);
    }

    public void intoToolbraLayout(Activity activity,String title){
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        mLeftImageView = (ImageView) mToolbar.findViewById(R.id.iv_toolbar_left);
        mTitleTextView = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        mRightImageView = (TextView) mToolbar.findViewById(R.id.tv_toolbar_right);
        mToolbar.setTitle("");
        if (null != title && title.length() != 0) {
            mRightImageView.setText(title);
        }
        setSupportActionBar(toolbar);
    }*/


    public void initToolbarLayout(Activity activity, String title) {
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        mLeftImageView = (ImageView) mToolbar.findViewById(R.id.iv_toolbar_left);
        mTitleTextView = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        mRightImageView = (ImageView) mToolbar.findViewById(R.id.tv_toolbar_right);
        mTvRightTextView = (TextView) mToolbar.findViewById(R.id.iv_toolbar_right);
        mRightImageViewShare = (ImageView) mToolbar.findViewById(R.id.iv_toolbar_right_share);
        mRightImageViewClose = (ImageView) mToolbar.findViewById(R.id.iv_toolbar_right_close);
        mToolbar.setTitle("");
        if (null != title && title.length() != 0) {
            mTitleTextView.setText(title);
        }
        setSupportActionBar(toolbar);
    }

    public void initToolbar(Activity activity, int title) {
        initToolbarLayout(activity, title);
    }

    public void initToolbar(Activity activity, String title) {
        initToolbarLayout(activity, title);
    }



    public void initToolbar(Activity activity, Boolean leftShow, int title) {
        initToolbarLayout(activity, title);
        if (leftShow) {
            mLeftImageView.setVisibility(View.VISIBLE);
            mLeftImageView.setOnClickListener(this);
            mRightImageViewClose.setOnClickListener(this);
            mTvRightTextView.setOnClickListener(this);
        }
    }

    public void initToolbar(Activity activity, Boolean leftShow, int title,OnToolBarRightClickListener listener) {
        initToolbarLayout(activity, title);
        mListener = listener;
        if (leftShow) {
            mLeftImageView.setVisibility(View.VISIBLE);
            mLeftImageView.setOnClickListener(this);
            mRightImageViewClose.setOnClickListener(this);
            mTvRightTextView.setOnClickListener(this);
        }
    }

    public void initToolbar(Activity activity, Boolean leftShow, String title,OnToolBarRightClickListener listener) {
        initToolbarLayout(activity, title);
        mListener = listener;
        if (leftShow) {
            mLeftImageView.setVisibility(View.VISIBLE);
            mLeftImageView.setOnClickListener(this);
            mRightImageViewClose.setOnClickListener(this);
            mTvRightTextView.setOnClickListener(this);
        }
    }

    public void initToolbar(Activity activity, Boolean leftShow, String title) {
        initToolbarLayout(activity, title);
        if (leftShow) {
            mLeftImageView.setVisibility(View.VISIBLE);
            mLeftImageView.setOnClickListener(this);
            mRightImageViewClose.setOnClickListener(this);
            mTvRightTextView.setOnClickListener(this);
        }
    }

    public void initToolbar(Activity activity, Boolean leftShow, int title, int right, OnToolBarRightClickListener listener) {
        initToolbarLayout(activity, title);
        mListener = listener;
        if (leftShow) {
            mLeftImageView.setVisibility(View.VISIBLE);
            mLeftImageView.setOnClickListener(this);
        }
        mRightImageView.setVisibility(View.VISIBLE);
        mRightImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_toolbar_right:
                mListener.setOnToolBarRightClickListener();
                break;
            case R.id.iv_toolbar_right_close:
                finish();
                break;
            case R.id.iv_toolbar_right:
                LogUtils.d("点击了右上角按钮");
                mListener.setOnToolBarRightClickListener();
                break;
        }
    }

    public interface OnToolBarRightClickListener {
        void setOnToolBarRightClickListener();  //点击事件
    }
}
