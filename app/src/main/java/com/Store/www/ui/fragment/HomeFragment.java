package com.Store.www.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
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
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import butterknife.internal.Utils;
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

import static net.bither.util.FileUtil.runOnUiThread;

/**
 * 首页碎片
 */

public class HomeFragment extends BaseFragment implements RefreshListener, HomeNewArrivalAdapter.OnNewArrivalClickListener,
        View.OnClickListener, OnBannerListener {


    @BindView(R.id.iv_ke_fu)
    ImageView mIvKeFu;  //客服
    @BindView(R.id.tv_message_count)
    TextView mTvMessageCount;  //未读消息数
    RefreshScrollView mScrollView;
    Banner mBanner;
    TextView mTvHeadHint;
    RefreshHeadbgView mHeadbgViwe;
    RelativeLayout mHeadView;

    Unbinder unbinder;
    List<String> mBannerUrl = new ArrayList<>();
    private ActivityUtils mActivityUtils;
    List<HomeCircleResponse.DataBean> photosBeans = new ArrayList<>();
    List<BannerResponse.DataBean> photosBeansBanner = new ArrayList<>();
    private int token = 2;
    public int screenWidth, screenHeight; //屏幕宽，屏幕高
    private boolean isLoad = false;  //全部数据是否加载完
    private Banner banner_recent;
    List imageList = new ArrayList();
    List titleList = new ArrayList();
    List imageList2 = new ArrayList();
    List titleList2 = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        mActivityUtils = new ActivityUtils(this);
        mScrollView = view.findViewById(R.id.refresh_sv);
        banner_recent = view.findViewById(R.id.banner_recent);
        mBanner = view.findViewById(R.id.banner);
        mHeadView = view.findViewById(R.id.head_view);
        mTvHeadHint = view.findViewById(R.id.head_view_tv);
        mHeadbgViwe = view.findViewById(R.id.head_bg);
        mScrollView.setListsner(this);
        mScrollView.setHeadView(mHeadView);
        getBannerData();
        getCircleData();
        getShielding();
        // 设置加载器
        banner_recent.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context)
                        .load(path)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                        .into(new BitmapImageViewTarget(imageView) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                super.setResource(resource);
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                                circularBitmapDrawable.setCornerRadius(13); //设置圆角弧度
                                imageView.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
        });
        // 设置加载器
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context)
                        .load(path)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                        .into(new BitmapImageViewTarget(imageView) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                super.setResource(resource);
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                                circularBitmapDrawable.setCornerRadius(13); //设置圆角弧度
                                imageView.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
        });
        banner_recent.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), MyCircleActivity.class);
                intent.putExtra("type", "HotCircle");
                intent.putExtra("id", photosBeans.get(position).getId());
                startActivity(intent);
            }
        });// 让主活动实现OnBannerListener接口
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), NewsWebActivity.class);
                intent.putExtra("bigtitle", "公司新闻"); //大标题
                intent.putExtra("title", photosBeansBanner.get(position).getTitle()); //标题
                intent.putExtra("infoid", photosBeansBanner.get(position).getInfoid());
                intent.putExtra("author", photosBeansBanner.get(position).getAutor());  //作者
                LogUtils.d("infoid" + photosBeansBanner.get(position).getInfoid());
                startActivity(intent);
            }
        });// 让主活动实现OnBannerListener接口
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
            uiCustomization.rightAvatar = "http://fuatee.oss-cn-hangzhou.aliyuncs.com/mrtx.png";
        } else {
            uiCustomization.rightAvatar = UserPrefs.getInstance().getIcon();
        }
        uiCustomization.leftAvatar = "http://fuatee.oss-cn-hangzhou.aliyuncs.com/kf.png";
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
        mScrollView.stopRefresh();
        LogUtils.d("onResume");
        LogUtils.d("isLoad==" + isLoad);
        if (isLoad = false) {
            getBannerData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner_recent.startAutoPlay();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
        banner_recent.stopAutoPlay();
    }


    //获取首页顶部轮播图
    private void getBannerData() {
        RetrofitClient.getInstances().getShopBanner().enqueue(new UICallBack<BannerResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow) {
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(BannerResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
//                        initBanner(bean.getData());
                        photosBeansBanner = bean.getData();
                        imageList2.clear();
                        titleList2.clear();
                        for (int i = 0; i < bean.getData().size(); i++) {
                            imageList2.add(TextUtils.isEmpty(bean.getData().get(i).getPictueUrl()) ? "" : bean.getData().get(i).getPictueUrl());
                            titleList2.add(TextUtils.isEmpty(bean.getData().get(i).getTitle()) ? "" : bean.getData().get(i).getTitle());
                        }
                        // 设置相关属性
                        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);//设置页码与标题
                        mBanner.setDelayTime(3000);//设置轮播时间
                        mBanner.setImages(imageList2);//设置图片源
                        mBanner.setBannerTitles(titleList2);
                        mBanner.start();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    /**
     * 获取首页圈子轮播图
     */
    private void getCircleData() {
        RetrofitClient.getInstances().getHomeCircle().enqueue(new UICallBack<HomeCircleResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow) {
                    checkNet();
                    mScrollView.stopRefresh();
                }
            }

            @Override
            public void OnRequestSuccess(HomeCircleResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        if (bean.getData().size() != 0) {
                            photosBeans = bean.getData();
                            imageList.clear();
                            titleList.clear();
                            for (int i = 0; i < bean.getData().size(); i++) {
                                for (int a = 0; a < bean.getData().get(i).getPhotos().size(); a++) {
                                    imageList.add(TextUtils.isEmpty(bean.getData().get(i).getPhotos().get(a).getPhoto()) ? "" : bean.getData().get(i).getPhotos().get(a).getPhoto());
                                    titleList.add(TextUtils.isEmpty(bean.getData().get(i).getTitle()) ? "" : bean.getData().get(i).getTitle());
                                }
                            }
                            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) banner_recent.getLayoutParams();
                            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                            int width = wm.getDefaultDisplay().getWidth();    //获取屏幕的宽度
                            int height = wm.getDefaultDisplay().getHeight();    //获取屏幕的宽度
                            linearParams.width = width - 40;
                            linearParams.height = height - 280;
                            banner_recent.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                            // 设置相关属性
                            banner_recent.setBannerStyle(BannerConfig.NOT_INDICATOR);//设置页码与标题
                            banner_recent.setDelayTime(3000);//设置轮播时间
                            banner_recent.setImages(imageList);//设置图片源
                            banner_recent.setBannerTitles(titleList);
                            banner_recent.start();
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        if (imageList.size() != 0) {
//                                            int[] imgWH = getImgWH(imageList.get(0).toString());
//                                            runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
////                                                    //此时已在主线程中，更新UI
//                                                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) banner_recent.getLayoutParams();
//                                                    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//                                                    int width = wm.getDefaultDisplay().getWidth();    //获取屏幕的宽度
//                                                    int height = wm.getDefaultDisplay().getHeight();    //获取屏幕的宽度
//                                                    linearParams.width = width - 40;
//                                                    linearParams.height = height - 300;
//                                                    banner_recent.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
//                                                    // 设置相关属性
//                                                    banner_recent.setBannerStyle(BannerConfig.NOT_INDICATOR);//设置页码与标题
//                                                    banner_recent.setDelayTime(3000);//设置轮播时间
//                                                    banner_recent.setImages(imageList);//设置图片源
//                                                    banner_recent.setBannerTitles(titleList);
//                                                    banner_recent.start();
//                                                }
//                                            });
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }).start();
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

    @Override
    public void OnBannerClick(int position) {

    }

    /**
     * 获取服务器上的图片尺寸
     */
    public static int[] getImgWH(String urls) throws Exception {

        URL url = new URL(urls);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        InputStream is = conn.getInputStream();
        Bitmap image = BitmapFactory.decodeStream(is);

        int srcWidth = image.getWidth();      // 源图宽度
        int srcHeight = image.getHeight();    // 源图高度
        int[] imgSize = new int[2];
        imgSize[0] = srcWidth;
        imgSize[1] = srcHeight;
        //释放资源
        image.recycle();
        is.close();
        conn.disconnect();
        return imgSize;
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
