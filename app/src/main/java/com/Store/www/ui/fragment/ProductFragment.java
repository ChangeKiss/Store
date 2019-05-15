package com.Store.www.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.Store.www.R;
import com.Store.www.base.BaseFragment;
import com.Store.www.entity.BannerResponse;
import com.Store.www.entity.CartWhetherNullResponse;
import com.Store.www.entity.CommodityManagerResponse;
import com.Store.www.entity.ProductBannerResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.activity.CommodityActivity;
import com.Store.www.ui.activity.IntroduceActivity;
import com.Store.www.ui.activity.NewsWebActivity;
import com.Store.www.ui.activity.ShoppingCartActivity;
import com.Store.www.ui.adapter.CommodityAdapter;
import com.Store.www.ui.adapter.TeamResultsAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: haifeng
 * @description: 产品界面
 */
public class ProductFragment extends BaseFragment implements OnLoadMoreListener,
        OnRefreshListener, CommodityAdapter.OnOrderButtonClickListener{
    @BindView(R.id.nodata)
    RelativeLayout mNoData;  //无数据提示
    @BindView(R.id.lr_product)
    LRecyclerView mLrProduct;  //数据列表
    @BindView(R.id.cart_floating_button)
    FloatingActionButton mCartFloatingButton;

    private CommonHeader mCommHeader;  //头布局
    XBanner mXBanner;  //轮播图
    List<String> BannerUrl = new ArrayList<>();
    CommodityAdapter mAdapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private boolean isCart = false;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container,false);
        unbinder = ButterKnife.bind(this,view);
        initHead();
        return view;
    }

    //初始化头布局与适配器
    private void initHead(){
        mCommHeader = new CommonHeader(mContext,R.layout.product_fragment_head);
        mXBanner = mCommHeader.findViewById(R.id.xb_product);
        mAdapter = new CommodityAdapter(getActivity());
        mAdapter.setListener(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager( getActivity(),2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mLRecyclerViewAdapter.addHeaderView(mCommHeader);  //添加头布局
        mLrProduct.setLayoutManager(layoutManager);
        mLrProduct.setAdapter(mLRecyclerViewAdapter);
        mLrProduct.setOnRefreshListener(this);
        mLrProduct.setOnLoadMoreListener(this);
        mLrProduct.setHeaderViewColor(R.color.redColorBackground, R.color.textColorBlack, R.color.colorLucency);
        mLrProduct.setFooterViewColor(R.color.redColorBackground, R.color.textColorBlack, R.color.colorLucency);
        mLrProduct.setFooterViewHint("正在加载", "我是有底线的", "网络没了..");
        mNoData.setVisibility(View.VISIBLE);
        mCartFloatingButton.setVisibility(View.VISIBLE);
        getBannerData();
        getCommodity(mPageIndex);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCartWhetherIsNull();
        mIsTopShow = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsTopShow = false;
    }

    /**
     * 网络获取商品信息
     */
    private void getCommodity(int pageIndex) {
        LogUtils.d("pageIndex=="+pageIndex);
        if (pageIndex==1){
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
            DialogLoading.shows(mContext,R.string.hint_loading);
        }
        RetrofitClient.getInstances().getCommodity(mUserId, mCountPerPage, pageIndex,token).enqueue(new UICallBack<CommodityManagerResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow){
                    checkNet();
                }
            }
            @Override
            public void OnRequestSuccess(CommodityManagerResponse bean) {
                if (mIsTopShow){  //如果当前活动是最上层(可见)  就加载数据
                    switch (bean.getReturnValue()) {
                        case 1:
                            mNoData.setVisibility(View.GONE);
                            if (bean.getData().size() == 0) {
                                mLrProduct.setNoMore(true);
                            } else {
                                mAdapter.addAll(bean.getData());
                                mLrProduct.refreshComplete(mCountPerPage);//设置一页加载数量为10
                                mAdapter.notifyDataSetChanged();
                            }
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //获取购物车中是否有商品
    private void getCartWhetherIsNull(){
        RetrofitClient.getInstancesTest().getCartWhetherIsNull(mUserId).enqueue(new UICallBack<CartWhetherNullResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(CartWhetherNullResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (mIsTopShow){
                            if (bean.getData().isIsCartHaveCommodity()){
                                isCart = false;
                                mCartFloatingButton.setImageResource(R.mipmap.cart_no_null);
                            }else {
                                isCart = true;
                                mCartFloatingButton.setImageResource(R.mipmap.cart_no);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //获取产品轮播
    private void getBannerData() {
        RetrofitClient.getInstances().getProductBanner().enqueue(new UICallBack<ProductBannerResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow)checkNet();
            }

            @Override
            public void OnRequestSuccess(ProductBannerResponse bean) {
                if (mIsTopShow){
                    switch (bean.getReturnValue()){
                        case 1:
                            initBanner(bean.getData());
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }


    private void initBanner(final List<ProductBannerResponse.DataBean> dataBeen) {
        for (int i = 0; i < dataBeen.size(); i++) {
            BannerUrl.add(dataBeen.get(i).getUrl());
        }
        ViewGroup.LayoutParams params;
        params = mXBanner.getLayoutParams();
        params.height = (int) (UserPrefs.getInstance().getWidth() / 1080.0 * 580);
        mXBanner.setLayoutParams(params);
        mXBanner.setData(BannerUrl, null);
        mXBanner.loadImage((banner, model, view, position) -> {  //添加图片加载框架
            Glide.with(mContext).load((String) model).into((ImageView) view);
        });

        mXBanner.setOnItemClickListener((banner, model, view, position) -> {  //轮播图的点击事件 打开产品集
            mActivityUtils.startActivity(CommodityActivity.class,"id",dataBeen.get(position).getInfoId());
        });

    }

    //商品item的点击事件
    @Override
    public void setItemButtonClickListener(int position, int productId) {
        Intent intent = new Intent(getActivity(), IntroduceActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex ++;
        getCommodity(mPageIndex);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        getCommodity(mPageIndex);
    }

    @OnClick({R.id.cart_floating_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cart_floating_button:
                if (isCart){  //购物车中没有商品
                    showToast("购物车空空如也");
                }else {
                    Intent intentCart = new Intent(getActivity(), ShoppingCartActivity.class);
                    intentCart.putExtra("toFragment", "cart");
                    startActivity(intentCart);
                }
                break;
        }
    }
}
