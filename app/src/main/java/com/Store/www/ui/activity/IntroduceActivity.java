package com.Store.www.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonFooter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.CartWhetherNullResponse;
import com.Store.www.entity.IntroduceResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.CommodityDetailAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情
 */
public class IntroduceActivity extends BaseToolbarActivity {
    @BindView(R.id.tv_toolbar_right)
    ImageView mIvToolbarRight;  //购物车按钮
    @BindView(R.id.iv_toolbar_left)
    ImageView mIvToolbarLeft;
    @BindView(R.id.lr_commodity_detail)
    LRecyclerView mLrCommodityDetail;  //商品详情图片列表

    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;  //加载动画

    RelativeLayout.LayoutParams params;
    private CommonHeader commonHeader;  //头布局
    private CommonFooter commonFooter;  //尾布局
    CommodityDetailAdapter mAdapter;  //适配器
    LRecyclerViewAdapter viewAdapter;
    private ImageView mCommodityImage;  //商品图片
    private TextView mClassify;  //分类
    private TextView mStyle;  //款式
    private TextView mPrice;  //价格
    private TextView mTvIntroduceContent; //商品介绍
    private LinearLayout mDetail;  //图文详情的字的布局
    private String titlePicture;
    List<IntroduceResponse.DataBean.ImagesBean> imagesBean = new ArrayList<>();
    IntroduceResponse.DataBean.ImagesBean mBean;
    private LinearLayout mPlaceAnOrder;  //下单按钮


    private int productId;//商品ID
    private String toolbarTitle;
    private boolean loadOk = false; //判断是否加载完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横竖屏切换
    }

    @Override
    public int initLayout() {
        return R.layout.activity_introduce;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.commodity_introduce);
        productId = getIntent().getIntExtra("productId", 0);
        LogUtils.d("商品ID=03=" + productId);
        aviShow();  //加载动画
        initHeadView(); //初始化头布局
    }

    //显示加载动画
    private void aviShow(){
        mAvi.setVisibility(View.VISIBLE);
        mAvi.show();
    }

    //关闭加载动画
    private void aviHide(){
        mAvi.setVisibility(View.GONE);
        mAvi.hide();
    }

    //初始化头布局/尾布局
    private void initHeadView(){
        commonHeader = new CommonHeader(mContext,R.layout.layout_commodity_detail_head);  //头布局
        commonFooter = new CommonFooter(mContext,R.layout.layout_commodity_detail_footer);  //尾布局
        mCommodityImage = (ImageView) commonHeader.findViewById(R.id.iv_introduce_image);  //商品图片
        mClassify = (TextView) commonHeader.findViewById(R.id.tv_classify);  //分类
        mStyle = (TextView) commonHeader.findViewById(R.id.tv_style);  //款式
        mPrice = (TextView) commonHeader.findViewById(R.id.tv_introduce_price);  //价格
        mDetail = (LinearLayout) commonHeader.findViewById(R.id.layout_commodity_detail);  //图文详情的布局
        mPlaceAnOrder = (LinearLayout) commonHeader.findViewById(R.id.layout_place_an_order);  //下单按钮
        params = (RelativeLayout.LayoutParams) mPlaceAnOrder.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/2-30;
        mPlaceAnOrder.setLayoutParams(params);
        mPlaceAnOrder.setOnClickListener(this);  //注册点击事件
        mTvIntroduceContent = (TextView) commonFooter.findViewById(R.id.tv_introduce_content);  //商品介绍
        initAdapter();  //初始化适配器
    }

    //初始化适配器
    private void initAdapter(){
        mAdapter = new CommodityDetailAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(commonHeader);  //添加头布局
        viewAdapter.addFooterView(commonFooter);    //添加尾布局
        mLrCommodityDetail.setLayoutManager(new LinearLayoutManager(this)); //设置布局管理器
        mLrCommodityDetail.setAdapter(viewAdapter);
        mLrCommodityDetail.setPullRefreshEnabled(false);  //关闭下拉刷新
        getIntroduce(); //获取商品详情
    }

    //获取商品介绍详情
    private void getIntroduce() {
        RetrofitClient.getInstances().getIntroduce(mUserId, productId).enqueue(new UICallBack<IntroduceResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }
            @Override
            public void OnRequestSuccess(IntroduceResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        LogUtils.d("是否获取商品详情="+isTop);
                        if (isTop){   //如果当前活动是最上层(可见)  就加载数据
                            aviHide();
                            mLrCommodityDetail.setVisibility(View.VISIBLE);  //数据请求成功把详情列表展示出来
                            toolbarTitle = bean.getData().getName();  //下单时传到下一个界面的商品名称
                            mClassify.setText("分类：" + bean.getData().getTypeName());
                            mStyle.setText("款式：" + bean.getData().getName());
                            mPrice.setText("价格:"+bean.getData().getCurrency() + ActivityUtils.changeMoneys(bean.getData().getPrice()));
                            mTvIntroduceContent.setText(bean.getData().getContent());
                            if (bean.getData().getImages().size()!= 0){
                                titlePicture = bean.getData().getImages().get(0).getUrl();
                                for (int i=1;i<bean.getData().getImages().size();i++){
                                    mBean = new IntroduceResponse.DataBean.ImagesBean();
                                    mBean.setUrl(bean.getData().getImages().get(i).getUrl());
                                    imagesBean.add(mBean);
                                }
                            }
                            LogUtils.d("图片集合长度="+bean.getData().getImages().size()+"");
                            Glide.with(IntroduceActivity.this)
                                    .load(titlePicture)
                                    .priority(Priority.HIGH)  //设置加载优先级 高
                                    .crossFade()  //设置加载动画
                                    .thumbnail(0.5f)
                                    .error(R.mipmap.jzz_img)
                                    .into(mCommodityImage);
                            mAdapter.addAll(imagesBean);  //添加数据
                            mAdapter.notifyDataSetChanged();  //通知刷新数据
                            mIvToolbarRight.setVisibility(View.VISIBLE);
                            mPlaceAnOrder.setVisibility(View.VISIBLE);
                            mDetail.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        showToast(bean.getErrMsg());
                        mDetail.setVisibility(View.INVISIBLE);
                        mPlaceAnOrder.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //获取购物车中是否有商品
    private void getCartWhetherIsNull(){
        RetrofitClient.getInstancesTest().getCartWhetherIsNull(mUserId).enqueue(new UICallBack<CartWhetherNullResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(CartWhetherNullResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            if (bean.getData().isIsCartHaveCommodity()){
                                mIvToolbarRight.setImageResource(R.mipmap.cart_no_null);
                            }else {
                                mIvToolbarRight.setImageResource(R.mipmap.cart_no);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop =true;
        getCartWhetherIsNull();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop =false;
    }

    //详情界面里的 购物车按钮点击事件
    @OnClick({R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_right:  //右上角跳转到购物车
                Intent intentCart = new Intent(this, MainActivity.class);
                intentCart.putExtra("toFragment", "cart");
                startActivity(intentCart);
                finish();
                break;
        }
    }

    //头布局的点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_place_an_order:  //下单
                Intent intent = new Intent(this,OrdersActivity.class);
                intent.putExtra("toolbarTitle",toolbarTitle);
                intent.putExtra("productId",productId);
                startActivity(intent);
                break;
            case R.id.iv_toolbar_left:  //左上角的返回点击事件失效 这里重写一下
                finish();
                break;
        }
    }
}
