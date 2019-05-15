package com.Store.www.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Store.www.MyApplication;
import com.Store.www.base.CustomScrollView.RefreshHeadbgView;
import com.Store.www.base.CustomScrollView.RefreshListener;
import com.Store.www.base.CustomScrollView.RefreshScrollView;
import com.Store.www.entity.HomeActivityResponse;
import com.Store.www.entity.HomeCircleResponse;
import com.Store.www.entity.HomeNewsResponse;
import com.Store.www.net.Api;
import com.Store.www.ui.activity.MyCircleActivity;
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
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.stx.xhb.xbanner.XBanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.functions.Function2;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 首页碎片
 */

public class HomeFragment extends BaseFragment implements RefreshListener ,HomeNewArrivalAdapter.OnNewArrivalClickListener,
        View.OnClickListener {


    @BindView(R.id.iv_ke_fu)
    ImageView mIvKeFu;  //客服
    @BindView(R.id.tv_message_count)
    TextView mTvMessageCount;  //未读消息数
    RefreshScrollView mScrollView;
    XBanner mBanner,mBannerNews,mBannerActivity,mBannerCircle;
    TextView mTvHintNews,mTvHintActivity,mTvHintCircle,mTvHeadHint;
    RefreshHeadbgView mHeadbgViwe;
    RelativeLayout mHeadView;

    Unbinder unbinder;
    List<String> mBannerUrl = new ArrayList<>();
    List<String> mNewsBannerUrl = new ArrayList<>();
    List<String> mActivityBannerUrl = new ArrayList<>();
    List<String> mCircleBannerUrl = new ArrayList<>();
    private ActivityUtils mActivityUtils;
    List<HomeCircleResponse.DataBean.PhotosBean> photosBeans = new ArrayList<>();
    HomeCircleResponse.DataBean.PhotosBean photosBean = new HomeCircleResponse.DataBean.PhotosBean();
    List<HomeActivityResponse.DataBean.PhotosBean> activityBeans = new ArrayList<>();
    HomeActivityResponse.DataBean.PhotosBean activityBean = new HomeActivityResponse.DataBean.PhotosBean();
    private int token = 2;
    public int screenWidth, screenHeight; //屏幕宽，屏幕高
    private boolean isLoad= false;  //全部数据是否加载完

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        mActivityUtils = new ActivityUtils(this);
        mScrollView = view.findViewById(R.id.refresh_sv);
        mBanner =  view.findViewById(R.id.banner);
        mBannerNews = view.findViewById(R.id.home_banners_news);
        mBannerActivity = view.findViewById(R.id.home_activity);
        mBannerCircle = view.findViewById(R.id.home_circle);
        mTvHintNews = view.findViewById(R.id.tv_home_hint_news);
        mTvHintActivity = view.findViewById(R.id.tv_home_hint_activity);
        mTvHintCircle = view.findViewById(R.id.tv_home_hint_circle);
        mHeadView = view.findViewById(R.id.head_view);
        mTvHeadHint = view.findViewById(R.id.head_view_tv);
        mHeadbgViwe = view.findViewById(R.id.head_bg);
        mScrollView.setListsner(this);
        mScrollView.setHeadView(mHeadView);
        getBannerData();
        getShielding();
        IntentFilter filter = new IntentFilter();
        filter.addAction("homePage");
        getActivity().registerReceiver(new HomeNetWork(), filter);
        //Unicorn.addUnreadCountChangeListener(listener, true); //添加是否监听未读消息 true为监听  此功能目前已弃用
        return view;
    }


    @OnClick({R.id.iv_ke_fu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_ke_fu:
                //initQiYuService();  //七鱼客服
                break;
        }
    }


    @Override
    public void startRefresh() {
        LogUtils.d("刷新数据");
        getBannerData();
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void hintChange(String hint) {
        mTvHeadHint.setText(hint);
    }

    @Override
    public void setWidthX(int x) {
        mHeadbgViwe.setWidthX(x);
    }


    class HomeNetWork extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("homePage")) {  //刷新数据
                mBannerUrl.clear();
            }
        }
    }

    //初始化七鱼云客服
    private void initQiYuService() {
        String title = "客服";
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
        //Unicorn.addUnreadCountChangeListener(listener, true); //添加是否监听未读消息 true为监听  目前无需客服功能 暂时不监听 以免报错
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
        mBannerNews.startAutoPlay();
        mBannerActivity.startAutoPlay();
        mBannerCircle.startAutoPlay();
        mScrollView.stopRefresh();
        LogUtils.d("onResume");
        LogUtils.d("isLoad=="+isLoad);
        if (isLoad = false){
            getBannerData();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
        mBannerNews.stopAutoPlay();
        mBannerActivity.stopAutoPlay();
        mBannerCircle.stopAutoPlay();
    }


    //获取首页顶部轮播图
    private void getBannerData() {
        RetrofitClient.getInstances().getShopBanner().enqueue(new UICallBack<BannerResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow){
                    checkNet();
                    getNewsData();
                }
            }

            @Override
            public void OnRequestSuccess(BannerResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        initBanner(bean.getData());
                        getNewsData();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        getNewsData();
                        break;
                }
            }
        });
    }

    /**
     * 加载首页顶部轮播图
     * @param dataBeen
     */
    private void initBanner(final List<BannerResponse.DataBean> dataBeen) {
        mBannerUrl.clear();
        for (int i = 0; i < dataBeen.size(); i++) {
            mBannerUrl.add(dataBeen.get(i).getPictueUrl());
        }
        ViewGroup.LayoutParams params;
        params = mBanner.getLayoutParams();
        params.height = (int) (screenWidth / 1080.0 * 642);
        mBanner.setLayoutParams(params);
        mBanner.setData(mBannerUrl, null);
        mBanner.loadImage((banner, model, view, position) ->{
            Glide.with(mContext).load((String) model).into((ImageView) view);
        });
        //添加图片加载框架
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
    }

    /**
     * 获取新闻轮播
     */
    private void getNewsData(){
        RetrofitClient.getInstances().getHomeNews(mPageIndex,mCountPerPage).enqueue(new UICallBack<HomeNewsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow) {
                    checkNet();
                    getActivityData();
                }
            }

            @Override
            public void OnRequestSuccess(HomeNewsResponse bean) {
                if (mIsTopShow){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData().size()!=0){
                                initNewsBanner(bean.getData());
                                mTvHintNews.setText("新闻公告");
                            }
                            getActivityData();
                            break;
                        default:
                            getActivityData();
                            break;
                    }
                }
            }
        });
    }

    /**
     * 加载首页新闻轮播图
     */
    private void initNewsBanner(List<HomeNewsResponse.DataBean> beans){
        mNewsBannerUrl.clear();
        for (int i=0;i<beans.size();i++){
            mNewsBannerUrl.add(beans.get(i).getPhotos().get(i).getPhoto());
        }
        ViewGroup.LayoutParams params = mBannerNews.getLayoutParams();
        params.height = UserPrefs.getInstance().getWidth()*184/414;
        mBannerNews.setLayoutParams(params);
        mBannerNews.setData(mNewsBannerUrl,null);
        mBannerNews.loadImage((banner, model, view, position) -> {
            Glide.with(mContext).load(model).into((ImageView) view);
        });
        mBannerNews.setOnItemClickListener((banner, model, view, position) -> {
            Intent intent = new Intent(getActivity(),NewsWebActivity.class);
            intent.putExtra("infoid",beans.get(position).getPhotos().get(position).getId());
            intent.putExtra("title",beans.get(position).getTitle());
            intent.putExtra("author","Fuatee");
            intent.putExtra("bigtitle","公司新闻"); //大标题
            startActivity(intent);
        });
    }

    /**
     * 获取活动商品轮播图
     */
    private void getActivityData(){
        RetrofitClient.getInstances().getHomeNotice(mPageIndex,mCountPerPage).enqueue(new UICallBack<HomeActivityResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow){
                    checkNet();
                    getCircleData();
                }
            }

            @Override
            public void OnRequestSuccess(HomeActivityResponse bean) {
                if (mIsTopShow){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData().size()!=0){
                                initActivityBanner(bean.getData());
                                mTvHintActivity.setText("活动商品");
                            }
                            getCircleData();

                            break;
                        default:
                            getCircleData();
                            break;
                    }
                }
            }
        });
    }

    /**
     *加载首页活动商品轮播图
     */
    private void initActivityBanner(List<HomeActivityResponse.DataBean> bean){
        mActivityBannerUrl.clear();
        for (int i=0;i<bean.size();i++){
            for (int k=0;k<bean.get(i).getPhotos().size();k++){
                mActivityBannerUrl.add(bean.get(i).getPhotos().get(k).getPhoto());
                activityBean.setId(bean.get(i).getPhotos().get(k).getId());
                activityBeans.add(activityBean);
            }
        }
        ViewGroup.LayoutParams params = mBannerActivity.getLayoutParams();
        params.height = UserPrefs.getInstance().getWidth()*184/414;
        mBannerActivity.setLayoutParams(params);
        mBannerActivity.setData(mActivityBannerUrl,null);
        mBannerActivity.loadImage((banner, model, view, position) -> {
            Glide.with(mContext).load(model).into((ImageView) view);
        });
        mBannerActivity.setOnItemClickListener((banner, model, view, position) -> {
            mActivityUtils.startActivity(IntroduceActivity.class,"productId",activityBeans.get(position).getId());
        });
    }


    /**
     *获取首页圈子轮播图
     */
    private void getCircleData(){
        RetrofitClient.getInstances().getHomeCircle().enqueue(new UICallBack<HomeCircleResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow){
                    checkNet();
                    mScrollView.stopRefresh();
                }
            }
            @Override
            public void OnRequestSuccess(HomeCircleResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (bean.getData().size()!=0){
                            initCircleBanner(bean.getData());
                            mTvHintCircle.setText("热门圈子");
                        }
                        mScrollView.stopRefresh();
                        isLoad = true;
                        break;
                    default:
                        isLoad = true;
                        mScrollView.stopRefresh();
                        break;
                }
            }
        });
    }

    /**
     *加载首页圈子轮播图
     */
    private void initCircleBanner(List<HomeCircleResponse.DataBean> bean){
        mCircleBannerUrl.clear();
        for (int i=0; i<bean.size(); i++){
            for (int k=0;k<bean.get(i).getPhotos().size();k++){
                mCircleBannerUrl.add(bean.get(i).getPhotos().get(k).getPhoto());
                photosBean.setId(bean.get(i).getPhotos().get(k).getId());
                photosBeans.add(photosBean);
            }
        }
        ViewGroup.LayoutParams params = mBannerCircle.getLayoutParams();
        params.height = UserPrefs.getInstance().getWidth()*184/414;
        mBannerCircle.setLayoutParams(params);
        mBannerCircle.setData(mCircleBannerUrl,null);
        mBannerCircle.loadImage((banner, model, view, position) -> {
            Glide.with(mContext).load(model).into((ImageView) view);
        });
        mBannerCircle.setOnItemClickListener((banner, model, view, position) -> {
            Intent intent = new Intent(getActivity(), MyCircleActivity.class);
            intent.putExtra("type","HotCircle");
            intent.putExtra("id",photosBeans.get(position).getId());
            startActivity(intent);
        });
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
//        LogUtils.d("屏幕宽===" + screenWidth);
//        LogUtils.d("屏幕高===" + screenHeight);
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
