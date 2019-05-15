package com.Store.www.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Store.www.R;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.NetWorkUtil;
import com.Store.www.utils.UserPrefs;

/**
 * @author: haifeng
 * @description: fragment懒加载基类
 */
public abstract class BaseLazyLoadFragment extends Fragment {
    public ActivityUtils mActivityUtils;
    public Context mContext;
    public int  mUserId ; //
    public String userId;
    public int mPageIndex = 1;
    public int mCountPerPage = 10;
    public Fragment mCurrentFragment;
    public int token = 2;
    public boolean mIsGetData = false; //是否重新请求数据
    public boolean mIsTopShow = false; //true在顶部显示
    public boolean mCanLoadMore = true;
    public AppCompatActivity activity;
    private boolean isFirstLoad = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        activity = (AppCompatActivity) getActivity();
        mActivityUtils = new ActivityUtils(this);
        mUserId = UserPrefs.getInstance().getId();
        userId = UserPrefs.getInstance().getUserId();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横竖屏切换
        View view = initView(inflater, container);//让子类实现初始化视图
        initEvent();//初始化事件
        isFirstLoad = true;//视图创建完成，将变量置为true
        if (getUserVisibleHint()) {//如果Fragment可见进行数据加载
            onLazyLoad();
            isFirstLoad = false;
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {//如果Fragment可见进行数据加载
            onLazyLoad();
            isFirstLoad = false;
        }
    }


    public void showToast(String msg) {
        mActivityUtils.showToast(msg);
    }

    public void showToast(int msgId) {
        mActivityUtils.showToast(msgId);
    }

    public void checkNet() {
        boolean networkAvailable = NetWorkUtil.isNetworkAvailable(mContext);
        if (!networkAvailable) {
            showToast(R.string.hint_net_fail);
        } else {
            showToast(R.string.hint_service);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = false;//视图销毁将变量置为false
        if (mActivityUtils != null) {
            mActivityUtils = null;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser) {//视图变为可见并且是第一次加载
            onLazyLoad();
            isFirstLoad = false;
        }

    }
    //数据加载接口，留给子类实现
    public abstract void onLazyLoad();

    //初始化视图接口，子类必须实现
    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);

    //初始化事件接口，留给子类实现
    public abstract void initEvent();

}
