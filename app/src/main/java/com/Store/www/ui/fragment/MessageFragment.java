package com.Store.www.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseFragment;
import com.Store.www.ui.activity.MyCircleActivity;
import com.Store.www.ui.activity.PublishCircleActivity;
import com.Store.www.ui.adapter.MessageFragmentAdapter;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: haifeng
 * @description: 讯息界面
 */
public class MessageFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.message_my)
    TextView mTvMessageMy;  //我的圈子
    @BindView(R.id.message_title)
    TextView mTvMessageTitle; //中间的标题
    @BindView(R.id.iv_publish_circle)
    ImageView mIvPublishCircle;  //右边的发布圈子
    @BindView(R.id.message_tab)
    TabLayout mMessageTab;  //tab
    @BindView(R.id.message_vp)
    ViewPager mMessageVp;  //ViewPager

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViewPager();
        return view;
    }

    private void initViewPager() {
        List<String> title = new ArrayList<>();
        List<Fragment> messageFragment = new ArrayList<>();
        title.add("新闻");
        title.add("圈子");
        title.add("公司相册");
        messageFragment.add(new NewsFragment());
        messageFragment.add(new CircleFragment());
        messageFragment.add(new CompanyPhotoAlbumFragment());
        for (String t : title) {
            mMessageTab.addTab(mMessageTab.newTab().setText(t));
        }
        MessageFragmentAdapter adapter = new MessageFragmentAdapter(getActivity().getSupportFragmentManager(), messageFragment, title);
        mMessageVp.setOffscreenPageLimit(2);  //设置缓存view 的个数（实际有3个，缓存2个+正在显示的1个）
        //给ViewPager绑定适配器
        mMessageVp.setAdapter(adapter);
        mMessageVp.addOnPageChangeListener(this); //给ViewPager添加监听事件
        //将TabLayout与ViewPager关联
        mMessageTab.setupWithViewPager(mMessageVp);
        mMessageTab.setTabsFromPagerAdapter(adapter);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {  //根据当前选中的是那一页 显示不同的布局
        if (position == 1) {
            mTvMessageMy.setVisibility(View.VISIBLE);
            mIvPublishCircle.setVisibility(View.VISIBLE);
            mTvMessageTitle.setVisibility(View.GONE);
        } else {
            mTvMessageMy.setVisibility(View.GONE);
            mIvPublishCircle.setVisibility(View.GONE);
            mTvMessageTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder = null;
    }

    @OnClick({R.id.message_my, R.id.iv_publish_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_my:
                mActivityUtils.startActivity(MyCircleActivity.class);
                break;
            case R.id.iv_publish_circle:
                mActivityUtils.startActivity(PublishCircleActivity.class);
                break;
        }
    }
}
