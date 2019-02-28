package com.Store.www.ui.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.ui.adapter.IntroductionAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.DensityUtil;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 引导页 界面
 */
public class IntroductionActivity extends BaseToolbarActivity implements ViewPager.OnPageChangeListener,IntroductionAdapter.OnClickListener{

    @BindView(R.id.vp_splash)
    ViewPager mVpSplash;
    @BindView(R.id.iv_splash_red)
    ImageView mIvSplashRed;
    @BindView(R.id.btn_splash_skip)
    Button mBtnSplashSkip;
    float pixelWidth;
    private List<Integer> mImageList;
    IntroductionAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_introduction;
    }

    @Override
    public void initView() {
        //38位点间距离+点的宽度 单位dp  38是三个点的间距30+点的直径8 的出来的值 38
        ActivityCollector.addActivity(this);
        setStatusBarFullTransparent(); //设置沉浸式状态栏
        pixelWidth = 38 * DensityUtil.getDensity(this);//获取屏幕密度 以及点移动的距离
        mImageList = new ArrayList<>();
        mImageList.add(R.mipmap.guide_scan_icon); //在线客服 引导页
        mImageList.add(R.mipmap.guide_entity_icon);  //支付方式  引导页
        mImageList.add(R.mipmap.guide_retail_icon);  //余额 引导页
        pagerAdapter = new IntroductionAdapter(this, mImageList,this);
        mVpSplash.setAdapter(pagerAdapter);
        mBtnSplashSkip.setOnClickListener(this);
        mVpSplash.addOnPageChangeListener(this);
    }

    //向后翻页,操作当前页面,百分比[0.0,1.0)
    //向前翻页,操作前一个页面,百分比(1.0,0.0]
    //position是指第几页,第一页为0,positionOffset为偏移量
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mIvSplashRed.setPadding((int) (position * pixelWidth + positionOffset * pixelWidth), 0, 0, 0);  //设置点的移动
    }

    //页面被选中时
    @Override
    public void onPageSelected(int position) {
        LogUtils.d("当前页=="+position);

         /*当前功能是最后一页时点击整个屏幕任意位置都可进行跳转 所以按钮点击暂时作废*/
        /*if (mImageList.size()-1 == position){  //如果是最后一页
            mBtnSplashSkip.setVisibility(View.VISIBLE);  //将进入按钮展示出来
        }else {  //否则
            mBtnSplashSkip.setVisibility(View.INVISIBLE);  //隐藏
        }*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        LogUtils.d("当前状态=="+state);
    }

    //跳转的点击事件
    @Override
    public void onClick(View view) {
        //UserPrefsFirst.getInstance().setFirst(false); //设置是否第一次进入APP为假
        //finish();
    }

    //最后一页时的点击事件
    @Override
    public void OnItemClickListener(int position) {
        mActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
