package com.Store.www.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Store.www.MyApplication;
import com.Store.www.R;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.NetWorkUtil;
import com.Store.www.utils.UserPrefs;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/12/8 0008.
 * activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity{
    public String userId;
    public int mUserId;
    public String mPhone;
    public int year;
    public String mMonth;
    public String agentCode;
    public ActivityUtils mActivityUtils;
    public Activity mActivity;
    private Unbinder mUnbinder;
    public int mCountPerPage = 10; //全局变量 分页用展示数据
    public int mPageIndex = 1; //全局变量分页用
    public Fragment mCurrentFragment;
    public boolean isTop =false;  //判断当前界面是否处于活动最上层(可见)
    public boolean mIsPasswordShow = false;//true有显示
    public boolean mCanLoadMore = true;
    public Context mContext;
    public int token = 2;
    public String netWork = " ";  //网络连接类型
    public boolean isCart = false;  //购物车是否有商品

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUtils = new ActivityUtils(this);
        mActivity = this;
        mContext = this;
        mUserId = UserPrefs.getInstance().getId(); //userId Int型
        //agentCode = "18857738393"; //测试用代理人ID 先用着
        agentCode = UserPrefs.getInstance().getAgentCode();  //代理人ID
        year = UserPrefs.getInstance().getYear(); //网络获取当前年份
        mMonth = UserPrefs.getInstance().getMonth(); //网络获取当前月份
        userId = UserPrefs.getInstance().getUserId();  //userId String型
        mPhone = UserPrefs.getInstance().getPhone();
        setContentView(initLayout()); //这里传布局/继承此类之后不用原生的引入布局了
        mUnbinder = ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//禁止一进入界面就因为et的原因弹出软键盘
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横竖屏切换
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);  //设置软键盘弹出模式覆盖Activity*****
        MyApplication.getInstance().addActivity(this);  //初始化网易七鱼云客服
        initView();
        setStatusBarFullTransparent();

    }

    //获取设备网络连接类型
    private void getPhoneNetWork(){
        //没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
        switch (ActivityUtils.getPhoneNetWork(mContext)){
            case 1:
                netWork = "WiFi";
                break;
            case 2:
                netWork = "2G";
                break;
            case 3:
                netWork = "3G";
                break;
            case 4:
                netWork = "4G";
                break;
            default:
                netWork = "0";
                break;
        }
    }


    public abstract int initLayout();

    public abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityUtils.hideSoftKeyboard();
        if (mActivityUtils != null) {
            mActivityUtils = null;
        }
        mUnbinder.unbind();
        mUnbinder = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void switchFragmentByShow(Fragment targetFragment) {
        if (mCurrentFragment == targetFragment) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        if (targetFragment.isAdded()) {
            // 如果已经添加到FragmentManager里面，就展示
            transaction.show(targetFragment);
        } else {
            // 添加Fragment
            transaction.add(R.id.frame, targetFragment);
        }
        transaction.commitAllowingStateLoss();
        mCurrentFragment = targetFragment;
    }


    /** * 全透状态栏 */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(255,202,147));  //设置系统状态栏 颜色
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4//
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明 //
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }




    public void showToast(String msg) {
        mActivityUtils.showToast(msg);
    }

    public void showToast(int msgId) {
        mActivityUtils.showToast(msgId);
    }

    public void checkNet() {
        boolean networkAvailable = NetWorkUtil.isNetworkAvailable(mActivity);
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

    //点击空白处隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
