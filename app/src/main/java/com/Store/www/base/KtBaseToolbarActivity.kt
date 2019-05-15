package com.Store.www.base

import android.app.Activity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.Store.www.R
import com.Store.www.utils.LogUtils

/**
 *@author: haifeng
 *@description:使用Kotlin写的界面要继承这个基类
 */
abstract class KtBaseToolbarActivity :KtBaseActivity(),View.OnClickListener {
    protected var toolbar: Toolbar? = null
    private var mListener: OnToolBarRightClickListener? = null
    private var mLeftImageView: ImageView? = null
    private var mRightImageView: ImageView? = null
    private var mTitleTextView: TextView? = null
    private var mTvRightTextView: TextView? = null
    private var mRightImageViewShare: ImageView? = null
    private var mRightImageViewClose: ImageView? = null
    private var mToolbar: Toolbar? = null

    fun initToolbarLayout(activity: Activity, title: Int) {
        mToolbar = activity.findViewById(R.id.toolbar) as Toolbar
        mLeftImageView = mToolbar!!.findViewById(R.id.iv_toolbar_left) as ImageView
        mTitleTextView = mToolbar!!.findViewById(R.id.tv_toolbar_title) as TextView
        mRightImageView = mToolbar!!.findViewById(R.id.tv_toolbar_right) as ImageView
        mTvRightTextView = mToolbar!!.findViewById(R.id.iv_toolbar_right) as TextView
        mRightImageViewShare = mToolbar!!.findViewById(R.id.iv_toolbar_right_share) as ImageView
        mRightImageViewClose = mToolbar!!.findViewById(R.id.iv_toolbar_right_close) as ImageView
        mToolbar!!.title = ""
        if (title != 0) {
            mTitleTextView!!.setText(title)
        }
        setSupportActionBar(toolbar)
    }


    private fun initToolbarLayout(activity: Activity, title: String?) {
        mToolbar = activity.findViewById(R.id.toolbar) as Toolbar
        mLeftImageView = mToolbar!!.findViewById(R.id.iv_toolbar_left) as ImageView
        mTitleTextView = mToolbar!!.findViewById(R.id.tv_toolbar_title) as TextView
        mRightImageView = mToolbar!!.findViewById(R.id.tv_toolbar_right) as ImageView
        mTvRightTextView = mToolbar!!.findViewById(R.id.iv_toolbar_right) as TextView
        mRightImageViewShare = mToolbar!!.findViewById(R.id.iv_toolbar_right_share) as ImageView
        mRightImageViewClose = mToolbar!!.findViewById(R.id.iv_toolbar_right_close) as ImageView
        mToolbar!!.title = ""
        if (null != title && title.length != 0) {
            mTitleTextView!!.text = title
        }
        setSupportActionBar(toolbar)
    }

    fun initToolbar(activity: Activity, title: Int) {
        initToolbarLayout(activity, title)
    }

    fun initToolbar(activity: Activity, title: String?) {
        initToolbarLayout(activity, title)
    }


    fun initToolbar(activity: Activity, leftShow: Boolean?, title: Int) {
        initToolbarLayout(activity, title)
        if (leftShow!!) {
            mLeftImageView!!.visibility = View.VISIBLE
            mLeftImageView!!.setOnClickListener(this)
            mRightImageViewClose!!.setOnClickListener(this)
            mTvRightTextView!!.setOnClickListener(this)
        }
    }

    fun initToolbar(activity: Activity, leftShow: Boolean?, title: Int, listener: OnToolBarRightClickListener) {
        initToolbarLayout(activity, title)
        mListener = listener
        if (leftShow!!) {
            mLeftImageView!!.visibility = View.VISIBLE
            mLeftImageView!!.setOnClickListener(this)
            mRightImageViewClose!!.setOnClickListener(this)
            mTvRightTextView!!.setOnClickListener(this)
        }
    }

    fun initToolbar(activity: Activity, leftShow: Boolean?, title: String?, listener: OnToolBarRightClickListener) {
        initToolbarLayout(activity, title)
        mListener = listener
        if (leftShow!!) {
            mLeftImageView!!.visibility = View.VISIBLE
            mLeftImageView!!.setOnClickListener(this)
            mRightImageViewClose!!.setOnClickListener(this)
            mTvRightTextView!!.setOnClickListener(this)
        }
    }

    fun initToolbar(activity: Activity, leftShow: Boolean?, title: String?) {
        initToolbarLayout(activity, title)
        if (leftShow!!) {
            mLeftImageView!!.visibility = View.VISIBLE
            mLeftImageView!!.setOnClickListener(this)
            mRightImageViewClose!!.setOnClickListener(this)
            mTvRightTextView!!.setOnClickListener(this)
        }
    }

    fun initToolbar(activity: Activity, leftShow: Boolean?, title: Int, right: Int, listener: OnToolBarRightClickListener) {
        initToolbarLayout(activity, title)
        mListener = listener
        if (leftShow!!) {
            mLeftImageView!!.visibility = View.VISIBLE
            mLeftImageView!!.setOnClickListener(this)
        }
        mRightImageView!!.visibility = View.VISIBLE
        mRightImageView!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_toolbar_left -> finish()
            R.id.tv_toolbar_right -> mListener!!.setOnToolBarRightClickListener()
            R.id.iv_toolbar_right_close -> finish()
            R.id.iv_toolbar_right -> {
                LogUtils.d("点击了右上角按钮")
                mListener!!.setOnToolBarRightClickListener()
            }
        }
    }

    interface OnToolBarRightClickListener {
        fun setOnToolBarRightClickListener()   //点击事件
    }
}