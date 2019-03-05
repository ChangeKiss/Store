package com.Store.www.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseFragment;
import com.Store.www.entity.BannerResponse;
import com.Store.www.entity.HomeNewArrivalResponse;
import com.Store.www.entity.QRCodeResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.activity.AgencyListActivity;
import com.Store.www.ui.activity.BonusQueryActivity;
import com.Store.www.ui.activity.CommodityActivity;
import com.Store.www.ui.activity.IntroduceActivity;
import com.Store.www.ui.activity.LoginActivity;
import com.Store.www.ui.activity.MyOrderActivity;
import com.Store.www.ui.activity.MyQrCodeActivity;
import com.Store.www.ui.activity.NewsWebActivity;
import com.Store.www.ui.activity.PickUpGoodsActivity;
import com.Store.www.ui.activity.ResultsActivity;
import com.Store.www.ui.activity.SellManageActivity;
import com.Store.www.ui.adapter.HomeNewArrivalAdapter;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.Store.www.utils.UserPrefsFirst;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页碎片
 */

public class HomeFragment extends BaseFragment implements OnRefreshListener,
        OnLoadMoreListener, HomeNewArrivalAdapter.OnNewArrivalClickListener, View.OnClickListener {

    @BindView(R.id.ry_home)
    LRecyclerView mLry;
    @BindView(R.id.iv_ke_fu)
    ImageView mIvKeFu;  //客服
    @BindView(R.id.tv_message_count)
    TextView mTvMessageCount;  //未读消息数
    LinearLayout mLayoutOne;
    LinearLayout mLayoutTwo;
    LinearLayout mLayoutThree;
    LinearLayout mLayoutFour;
    LinearLayout mLayoutFive;
    LinearLayout mLayoutSix;
    LinearLayout mLayoutSeven;
    LinearLayout mLayoutEight;

    LinearLayout.LayoutParams params;
    HomeNewArrivalAdapter mAdapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    Unbinder unbinder;
    List<String> mBannerUrl = new ArrayList<>();
    private ActivityUtils mActivityUtils;
    private CommonHeader mHeaderView;
    private XBanner mBanner;
    private int nweNumber; //新品数量
    private TextView mTvTodayNew; //中间文本框
    private LinearLayout mLayoutHomeEventHint;
    private View mVwHomeLinesOne;
    private View mVwHomeLinesTwo;
    private int token = 2;
    //集合用于给中间的控件添加点击事件/图片、文字  暂时是写死的，后期会网络获取*****
    List<LinearLayout> mHomeButtonLayout = new ArrayList<>();
    public int screenWidth, screenHeight; //屏幕宽，屏幕高

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        mActivityUtils = new ActivityUtils(this);
        initHeader();
        getShielding();
        IntentFilter filter = new IntentFilter();
        filter.addAction("homePage");
        getActivity().registerReceiver(new HomeNetWork(), filter);
        Unicorn.addUnreadCountChangeListener(listener, true); //添加是否监听未读消息 true为监听
        return view;
    }

    @OnClick({R.id.iv_ke_fu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_ke_fu:
                initQiYuService();  //七鱼客服
                break;
        }
    }


    class HomeNetWork extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("homePage")) {
                onRefresh();
            }
        }
    }

    //初始化七鱼云客服
    private void initQiYuService() {
        String title = "金薇客服";
        /**
         * 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入。
         * 三个参数分别为：来源页面的url，来源页面标题，来源页面额外信息（保留字段，暂时无用）。
         * 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
         */
        YSFOptions options = new YSFOptions(); //初始化七鱼云客服
        UICustomization uiCustomization = new UICustomization();
        uiCustomization.titleBackgroundResId = R.color.placeholder_top;  //设置顶部标题栏背景颜色
        uiCustomization.topTipBarTextColor = R.color.windowBackground;  //设置顶部标题栏字体颜色
        if (UserPrefs.getInstance().getIcon() == null) {
            uiCustomization.rightAvatar = "http://jwbucket.oss-cn-shanghai.aliyuncs.com/mrtx.png";
        } else {
            uiCustomization.rightAvatar = UserPrefs.getInstance().getIcon();
        }
        uiCustomization.leftAvatar = "http://jwbucket.oss-cn-shanghai.aliyuncs.com/kf.png";
        uiCustomization.titleCenter = true;
        options.uiCustomization = uiCustomization;
        //options.statusBarNotificationConfig = new StatusBarNotificationConfig();  //已经初始化过此处不能加否则会出现重复启动APP的BUG
        options.savePowerConfig = new SavePowerConfig(); //***保存聊天记录
        ConsultSource source = new ConsultSource("", "App", "custom information string");
        YSFUserInfo userInfo = new YSFUserInfo();
        userInfo.userId = UserPrefs.getInstance().getUserId();
        //把用户信息传给 七鱼 后台展示用户信息
        userInfo.data = "[{\"key\":\"real_name\", \"value\":\"" + UserPrefs.getInstance().getNickName() + "\"}," +
                "{\"key\":\"mobile_phone\", \"value\":\"" + UserPrefs.getInstance().getPhone() + "\"}," +
                "{\"key\":\"email\", \"value\":\"" + UserPrefs.getInstance().getEmail() + "\"}," +
                "{\"index\":0,\"key\":\"account\", \"label\":\"代理编号\", \"value\":\"" + UserPrefs.getInstance().getCode() + "\"}," +
                "{\"index\":1, \"key\":\"sex\", \"label\":\"微信号\", \"value\":\"" + UserPrefs.getInstance().getWxi() + "\"}," +
                "{\"index\":5, \"key\":\"reg_date\", \"label\":\"代理等级\", \"value\":\"" + UserPrefs.getInstance().getLevel() + "\"}," +
                "{\"key\":\"avatar\", \"value\":\"" + UserPrefs.getInstance().getIcon() + "\"}," +
                "{\"index\":3,\"key\":\"agent\",\"label\":\"版本编号\",\"value\":\"" + UserPrefsFirst.getInstance().getCodeName() + "\"}]";

        Unicorn.setUserInfo(userInfo);  //设置用户信息
        Unicorn.updateOptions(options);  //设置自定义UI
        Unicorn.addUnreadCountChangeListener(listener, true); //添加是否监听未读消息 true为监听
        Unicorn.toggleNotification(true);  //退到后台时监听未读消息
        /**
         * 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable()，
         * 如果返回为false，该接口不会有任何动作
         *
         * @param context 上下文
         * @param title   聊天窗口的标题
         * @param source  咨询的发起来源，包括发起咨询的url，title，描述信息等
         */
        Unicorn.openServiceActivity(mContext, title, source);
    }

    private UnreadCountChangeListener listener = new UnreadCountChangeListener() { // 声明一个成员变量
        @Override
        public void onUnreadCountChange(int count) {
            // 在此更新界面, count 为当前未读数，
            // 也可以用 Unicorn.getUnreadCount() 获取总的未读数
            //LogUtils.d("未读数="+count);
            if (count == 0) {
                if (mTvMessageCount != null) mTvMessageCount.setVisibility(View.INVISIBLE);
            } else if (count > 0 || count < 99) {
                if (mTvMessageCount != null) mTvMessageCount.setVisibility(View.VISIBLE);
                mTvMessageCount.setText(count + " ");
            } else if (count > 99) {
                if (mTvMessageCount != null) mTvMessageCount.setVisibility(View.VISIBLE);
                mTvMessageCount.setText(99 + "+");
            }
        }
    };


    //动态设置中间八个按钮的控件的宽
    private void setViewWidth() {
        params = (LinearLayout.LayoutParams) mLayoutOne.getLayoutParams();
        params = (LinearLayout.LayoutParams) mLayoutTwo.getLayoutParams();
        params = (LinearLayout.LayoutParams) mLayoutThree.getLayoutParams();
        params = (LinearLayout.LayoutParams) mLayoutFour.getLayoutParams();
        params = (LinearLayout.LayoutParams) mLayoutFive.getLayoutParams();
        params = (LinearLayout.LayoutParams) mLayoutSix.getLayoutParams();
        params = (LinearLayout.LayoutParams) mLayoutSeven.getLayoutParams();
        params = (LinearLayout.LayoutParams) mLayoutEight.getLayoutParams();
        params.width = screenWidth / 4;  //控件的宽 等于屏幕的宽除以4
        mLayoutOne.setLayoutParams(params);
        mLayoutTwo.setLayoutParams(params);
        mLayoutThree.setLayoutParams(params);
        mLayoutFour.setLayoutParams(params);
        mLayoutFive.setLayoutParams(params);
        mLayoutSix.setLayoutParams(params);
        mLayoutSeven.setLayoutParams(params);
        mLayoutEight.setLayoutParams(params);
    }

    //初始化头布局
    private void initHeader() {
        mHeaderView = new CommonHeader(mContext, R.layout.layout_home_head);
        mBanner = (XBanner) mHeaderView.findViewById(R.id.banner);
        mVwHomeLinesTwo = mHeaderView.findViewById(R.id.vw_home_lines_two);
        initAdapter();
    }

    //初始化适配器
    private void initAdapter() {
        mAdapter = new HomeNewArrivalAdapter(mContext, this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerViewAdapter.addHeaderView(mHeaderView);//添加header头布局
        /*GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);*/
        mLry.setLayoutManager(new LinearLayoutManager(mContext));
        mLry.setAdapter(mLRecyclerViewAdapter);
        mLry.setOnRefreshListener(this);//下拉刷新
        mLry.setLoadMoreEnabled(false);  //关闭上拉加载
        getNewArrival();
        mBannerUrl.clear();
        getBannerData();
    }


    //请求新闻活动圈子数据
    private void getNewArrival() {
        RetrofitClient.getInstances().getNewArrival(userId, token).enqueue(new UICallBack<HomeNewArrivalResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                mLry.refreshComplete(4);
            }

            @Override
            public void OnRequestSuccess(HomeNewArrivalResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        nweNumber = bean.getData().size();
                        //mTvTodayNew.setText("今日上新"+nweNumber+"款促销");
                        if (bean.getData().size() != 0) {
                            mAdapter.getDataList().clear();
                            mAdapter.addAll(bean.getData());
                            mLry.refreshComplete(mCountPerPage); //加载完成
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        mLayoutHomeEventHint.setVisibility(View.GONE);
                        mVwHomeLinesOne.setVisibility(View.GONE);
                        mVwHomeLinesTwo.setVisibility(View.GONE);
                        showToast(bean.getErrMsg());
                        break;

                }
            }
        });
    }


    //上拉加载
    @Override
    public void onLoadMore() {
        mLry.setNoMore(true);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mAdapter.getDataList().clear();
        mBannerUrl.clear();
        getBannerData();
        getNewArrival();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d("销毁视图");
        unbinder = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsTopShow = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsTopShow = true;
        mBanner.startAutoPlay();
        LogUtils.d("onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    //获取轮播图
    private void getBannerData() {
        RetrofitClient.getInstances().getShopBanner().enqueue(new UICallBack<BannerResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow) checkNet();
                mLry.refreshComplete(4);
            }

            @Override
            public void OnRequestSuccess(BannerResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        LogUtils.d("加载轮播图");
                        initBanner(bean.getData());
                        mLry.refreshComplete(4);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }


    private void initBanner(final List<BannerResponse.DataBean> dataBeen) {
        for (int i = 0; i < dataBeen.size(); i++) {
            mBannerUrl.add(dataBeen.get(i).getPictueUrl());
        }
        ViewGroup.LayoutParams params;
        params = mBanner.getLayoutParams();
        params.height = (int) (screenWidth / 1080.0 * 642);
        mBanner.setLayoutParams(params);
        mBanner.setData(mBannerUrl, null);
        mBanner.loadImage(new XBanner.XBannerAdapter() {  //添加图片加载框架
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(mContext).load((String) model).into((ImageView) view);
            }
        });
        mBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsWebActivity.class);
                intent.putExtra("bigtitle", "公司新闻"); //大标题
                intent.putExtra("title", dataBeen.get(position).getTitle()); //标题
                intent.putExtra("infoid", dataBeen.get(position).getInfoid());
                intent.putExtra("author", dataBeen.get(position).getAutor());  //作者
                LogUtils.d("infoid" + dataBeen.get(position).getInfoid());
                startActivity(intent);
            }
        });
        /*mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        LogUtils.d("轮播图的高==" + mBanner.getHeight());
        mBanner.setImages(mBannerUrl);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.start();
        //轮播图的点击件，点击后打开新闻
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                LogUtils.d("点击了轮播图");
                Intent intent = new Intent(getActivity(), NewsWebActivity.class);
                intent.putExtra("bigtitle", "公司新闻"); //大标题
                intent.putExtra("title", dataBeen.get(position).getTitle()); //标题
                intent.putExtra("infoid", dataBeen.get(position).getInfoid());
                intent.putExtra("author", dataBeen.get(position).getAutor());  //作者
                LogUtils.d("infoid" + dataBeen.get(position).getInfoid());
                startActivity(intent);
            }
        });*/
    }


    //Item的点击事件
    @Override
    public void OnItemClickListener(int position, int productId) {
        if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
            mActivityUtils.startActivity(LoginActivity.class, "login", "login");
        } else {
            LogUtils.d("商品ID=02=" + productId);
            Intent intent = new Intent(getActivity(), IntroduceActivity.class);
            intent.putExtra("productId", productId);
            startActivity(intent);
        }

    }

    //首页中间按钮的点击事件
    @Override
    public void onClick(View v) {

             //商品管理
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    mActivityUtils.startActivity(CommodityActivity.class);
                }

             //我的订单
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    mActivityUtils.startActivity(MyOrderActivity.class);
                }

             //分红查询
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    mActivityUtils.startActivity(BonusQueryActivity.class);
                }

             //提货管理
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    mActivityUtils.startActivity(PickUpGoodsActivity.class);
                }

             //销售管理
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    mActivityUtils.startActivity(SellManageActivity.class);
                }

             //团队业绩
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    mActivityUtils.startActivity(ResultsActivity.class);
                }

             //代理查询
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    mActivityUtils.startActivity(AgencyListActivity.class);
                }

             //我的二维码
                if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())) {
                    mActivityUtils.startActivity(LoginActivity.class, "login", "login");
                } else {
                    //mActivityUtils.startActivity(SellManageActivity.class);
                    getQRCode(mUserId);
                }


    }

    //获取当前手机屏幕宽高
    private void getShielding() {
        screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        UserPrefs.getInstance().setWidth(screenWidth);
        UserPrefs.getInstance().setHeight(screenHeight);
        LogUtils.d("屏幕宽===" + screenWidth);
        LogUtils.d("屏幕高===" + screenHeight);
    }


    //判断用户是否有二维码
    private void getQRCode(int userId) {
        RetrofitClient.getInstances().getQRCode(userId).enqueue(new UICallBack<QRCodeResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow) checkNet();
            }

            @Override
            public void OnRequestSuccess(QRCodeResponse bean) {
                if (mIsTopShow) {
                    switch (bean.getReturnValue()) {
                        case 1:
                            Intent intent = new Intent(getActivity(), MyQrCodeActivity.class);
                            intent.putExtra("codeUrl", bean.getCode());
                            intent.putExtra("shareUrl", bean.getShareUrl());
                            intent.putExtra("title", bean.getTitle());
                            intent.putExtra("context", bean.getContext());
                            startActivity(intent);
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

}
