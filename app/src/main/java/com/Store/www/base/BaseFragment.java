package com.Store.www.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Store.www.R;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.NetWorkUtil;
import com.Store.www.utils.UserPrefs;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public abstract class BaseFragment extends Fragment{
    public ActivityUtils mActivityUtils;
    public Context mContext;
    public int  mUserId ; //
    public String userId;
    private Toast toast;
    public int mPageIndex = 1;
    public int mCountPerPage = 10;
    public Fragment mCurrentFragment;
    public int token = 2;
    public boolean mIsGetData = false; //是否重新请求数据
    public boolean mIsTopShow = false; //true在顶部显示
    public boolean mCanLoadMore = true;
    public AppCompatActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
        activity = (AppCompatActivity) getActivity();
        mActivityUtils = new ActivityUtils(this);
        mUserId = UserPrefs.getInstance().getId();
        userId = UserPrefs.getInstance().getUserId();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横竖屏切换
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mActivityUtils != null) {
            mActivityUtils = null;
        }
        super.onDestroy();
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

    public void resetNodata(RelativeLayout nodata, int hintId) {
        ImageView ivNodata = (ImageView) nodata.findViewById(R.id.iv_nodata);
        TextView tvNodata = (TextView) nodata.findViewById(R.id.tv_nodata);
        ivNodata.setImageResource(R.mipmap.no_data);
        if (hintId != 0) {
            tvNodata.setText(hintId);
        }
        nodata.setVisibility(View.GONE);
    }
}
