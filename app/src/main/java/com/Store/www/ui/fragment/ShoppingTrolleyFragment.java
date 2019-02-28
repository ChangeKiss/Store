package com.Store.www.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Store.www.R;
import com.Store.www.base.BaseFragment;
import com.Store.www.entity.AllCartRequest;
import com.Store.www.entity.AllDeleteCartRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.CloseAccountRequest;
import com.Store.www.entity.CloseAccountResponse;
import com.Store.www.entity.ShoppingCartResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.activity.AlterOrderDetailsActivity;
import com.Store.www.ui.activity.SubmitOrderActivity;
import com.Store.www.ui.adapter.ShoppingTrolleyAdapter;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 购物车碎片
 */


public class ShoppingTrolleyFragment extends BaseFragment implements ShoppingTrolleyAdapter.OnOrderItemClickListener {
    @BindView(R.id.ry_cart)
    RecyclerView mLvCart;
    @BindView(R.id.tv_cart_money_total)
    TextView mTvCartMoneyTotal;
    @BindView(R.id.btn_clearing)
    TextView mBtnClearing;
    @BindView(R.id.cb_trolley)
    CheckBox mCbCartAll;
    @BindView(R.id.tv_hint_all)
    TextView mTvHintAll;
    @BindView(R.id.tv_delete)
    TextView mIvDelete;
    @BindView(R.id.nodata)
    RelativeLayout mNodata;
    @BindView(R.id.iv_nodata)
    ImageView mIvNoData;  //购物车无数据时显示的图片
    @BindView(R.id.tv_nodata)
    TextView mTvNoData;
    @BindView(R.id.layout_check)
    LinearLayout mLayoutCheck;  //复选框布局

    Unbinder mUnbinder;
    ShoppingTrolleyAdapter mAdapter;
    private int isAndroidNewVersion = 1;
    List<ShoppingCartResponse.DataBean> orderList = new ArrayList<>(); //单选时存放被选中的商品信息



    List<CloseAccountRequest.ProductsBean> mProducts = new ArrayList<>(); //结算时提交的商品信息
    CloseAccountRequest.ProductsBean productsBean;

    private int mCartId=0;
    private  int totalPrice;  //总价
    private int totalCount;    //总数
    private int count;
    private boolean mIsCheck;
    private int mPosition;
    private CheckBox mCheckBox;
    private String type = "sing";
    List<AllDeleteCartRequest.CartIdsBean> dataBean = new ArrayList<>();  //单选时购物车ID
    List<AllDeleteCartRequest.CartIdsBean> SingleBean = new ArrayList<>();  //全选时购物车ID
    List<Integer> CartIdS = new ArrayList<>(); //全选购物车商品ID
    List<Integer> CartId = new ArrayList<>(); ;  //单选购物车商品ID

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_trolley, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initAdapter();
        return view;
    }



    private void initAdapter() {
        mCbCartAll.setChecked(false);
        mAdapter = new ShoppingTrolleyAdapter(mContext, this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mLvCart.setLayoutManager(manager);
        mLvCart.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsTopShow = true;
        mCbCartAll.setChecked(false);
        CartId.clear();
        CartIdS.clear();
        SingleBean.clear();
        orderList.clear();
        mProducts.clear();
        mBtnClearing.setEnabled(false);
        getShoppingCart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsTopShow = false;
    }

    //获取购物车商品
    private void getShoppingCart() {
        RetrofitClient.getInstancesTest().getCart(mUserId).enqueue(new UICallBack<ShoppingCartResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow)checkNet();
            }

            @Override
            public void OnRequestSuccess(ShoppingCartResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        if (mIsTopShow){
                            mIvDelete.setVisibility(View.VISIBLE);
                            mLayoutCheck.setVisibility(View.VISIBLE);
                            mNodata.setVisibility(View.GONE);
                            mAdapter.getDataList().clear();
                            mAdapter.notifyDataSetChanged();
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            getTotalPrice();
                        }
                        break;
                    case 5:
                        if (mIsTopShow){
                            mIvDelete.setVisibility(View.INVISIBLE);
                            mLayoutCheck.setVisibility(View.INVISIBLE);
                            mNodata.setVisibility(View.VISIBLE);
                            mIvNoData.setImageResource(R.mipmap.null_cart);
                            mTvNoData.setText(R.string.cart_null);
                        }
                        break;
                    default:
                        getTotalPrice();
                        break;

                }
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.cb_trolley, R.id.tv_delete, R.id.btn_clearing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_trolley:  //全选的点击事件
                for (int i=0;i<mAdapter.getDataList().size();i++){
                    CartIdS.add(mAdapter.getDataList().get(i).getProductId());
                }
                requestJudge(userId,CartIdS);
                break;
            case R.id.tv_delete:  //删除商品
                List<ShoppingCartResponse.DataBean> been = mAdapter.getDataList();
                for (int i=0;i<mAdapter.getDataList().size();i++){
                    if (been.get(i).isCheck()){
                        AllDeleteCartRequest.CartIdsBean cartIdsBean = new AllDeleteCartRequest.CartIdsBean();
                        cartIdsBean.setCartId(been.get(i).getId());
                        dataBean.add(cartIdsBean);
                    }
                }
                LogUtils.d("删除的购物车数量="+dataBean.size());
                LogUtils.d("类型=="+type);
                LogUtils.d("选中购物车ID="+dataBean.size());
                if (dataBean.size()==0){
                    return;
                }
                allDeleteCart(userId,dataBean);
                break;
            case R.id.btn_clearing:
                LogUtils.d("提交时类型=="+type);
                mBtnClearing.setEnabled(false);
                List<ShoppingCartResponse.DataBean> dataBeen = mAdapter.getDataList();
                for (int i=0;i<mAdapter.getDataList().size();i++){
                    if (dataBeen.get(i).isCheck()){
                        ShoppingCartResponse.DataBean mDataBean = new ShoppingCartResponse.DataBean();
                        mDataBean.setId(dataBeen.get(i).getId()); //购物车ID
                        mDataBean.setProductImg(dataBeen.get(i).getProductImg()); //商品图片
                        mDataBean.setProductName(dataBeen.get(i).getProductName()); //商品名称
                        mDataBean.setProductCode(dataBeen.get(i).getProductCode()); //商品编号
                        mDataBean.setProductId(dataBeen.get(i).getProductId()); //商品ID
                        mDataBean.setPrice(dataBeen.get(i).getPrice()); //商品单价
                        mDataBean.setCount(dataBeen.get(i).getCount()); //商品总数
                        orderList.add(mDataBean);
                        productsBean = new CloseAccountRequest.ProductsBean();
                        productsBean.setProductId(dataBeen.get(i).getProductId());
                        productsBean.setCount(dataBeen.get(i).getCount());
                        mProducts.add(productsBean);
                    }
                }
                requestCloseAccount(userId,isAndroidNewVersion,mProducts); //判断商品类型  提交结算
                break;
        }
    }

  /*  @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
           orderList.clear();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }*/



    //获取商品被勾选时的总价/以及要传递的参数
    private void getTotalPrice(){
        totalPrice = 0;
        totalCount = 0;
        List<ShoppingCartResponse.DataBean> dataBeen = mAdapter.getDataList();
        LogUtils.d("ataBeen.size=="+mAdapter.getDataList().size());
        for (int i=0;i<dataBeen.size();i++){
            if (dataBeen.get(i).isCheck()){  //如果当前商品是被选中的
                totalPrice = dataBeen.get(i).getPrice()*dataBeen.get(i).getCount()+totalPrice;
                totalCount = dataBeen.get(i).getCount()+totalCount;
            }
        }
        mTvCartMoneyTotal.setText(ActivityUtils.changeMoneys(totalPrice));
        mBtnClearing.setText("结算("+totalCount+")");
        if (totalCount==0){
            mBtnClearing.setEnabled(false);
        }else {
            mBtnClearing.setEnabled(true);
        }
        LogUtils.d("商品总数==="+totalCount);
        LogUtils.d("商品总价==="+totalPrice);
    }

    //单个商品选中事件
    @Override
    public void setOnCheckboxChangeListener(boolean isChecked,int position,CheckBox checkBox) {
        mIsCheck = isChecked;
        mPosition = position;
        mCheckBox = checkBox;
        if (isChecked){
            List<ShoppingCartResponse.DataBean> dataBeen = mAdapter.getDataList();
            for (int i=0;i<mAdapter.getDataList().size();i++){
                if (dataBeen.get(i).isCheck()){
                    CartId.add(dataBeen.get(i).getProductId());
                    requestSingle(userId,CartId);
                }
            }
        }else {
            mCbCartAll.setChecked(false);
            //orderList.clear();
            getTotalPrice();
            //dataBean.clear();
            CartId.clear();
            //mProducts.clear();
        }
    }

    //点击勾选框获取相应的ID
    @Override
    public void getCartIdClickListener(int position, int productId,int number) {
        mCartId = productId;
        LogUtils.d("选中的商品ID = "+productId);
        //CartId.add(productId);
    }


    //Item的点击事件 查看并编辑商品
    @Override
    public void setOnItemClickListener(int position, int cartId, int product,String title) {
        Intent intent = new Intent(mContext, AlterOrderDetailsActivity.class);
        intent.putExtra("cartid",cartId);
        intent.putExtra("product",product);
        intent.putExtra("title",title);
        startActivity(intent);
    }



    //全选删除购物车商品
    private void allDeleteCart(String userId, List<AllDeleteCartRequest.CartIdsBean> been){
        AllDeleteCartRequest request = new AllDeleteCartRequest(userId,been);
        RetrofitClient.getInstancesTest().requestAllDeleteCart(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (mIsTopShow){
                            dataBean.clear();  //删除成功将原有存放购物车ID的集合清空
                            SingleBean.clear();
                            CartIdS.clear();
                            CartId.clear();
                            showToast(R.string.delete_ok);
                            getTotalPrice();
                            getShoppingCart();
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //全选商品时判断是否有不同币种
    private void requestJudge(String userId,List<Integer> commodityId){
        AllCartRequest request = new AllCartRequest(userId,commodityId);
        RetrofitClient.getInstancesTest().requestAllCart(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (mIsTopShow){
                            type = "all";
                            //LogUtils.d("选中全选按钮时类型=="+type);
                            //LogUtils.d("全选时 被选中的长度"+mAdapter.getDataList().size());
                            for (int i=0;i<mAdapter.getDataList().size();i++){
                                mAdapter.getDataList().get(i).setCheck(mCbCartAll.isChecked());
                            }
                            //LogUtils.d("全选时商品集合长度="+SingleBean.size());
                            getTotalPrice();
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        mCbCartAll.setChecked(false);
                        CartId.clear();
                        CartIdS.clear();
                        SingleBean.clear();
                        orderList.clear();
                        getTotalPrice();
                        mAdapter.notifyDataSetChanged();
                        Toast toast = Toast.makeText(getActivity(),bean.getErrMsg(),Toast.LENGTH_LONG);
                        ActivityUtils.showMyToast(toast,4*1000);
                        break;
                }
            }
        });
    }

    //单选商品时判断是否有不同币种商品
    private void requestSingle(String userId,List<Integer> commodityId){
        AllCartRequest request = new AllCartRequest(userId,commodityId);
        RetrofitClient.getInstancesTest().requestAllCart(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (!mIsCheck) {  //没有选中时
                           /* mCbCartAll.setChecked(false);  //此段代码作废
                            orderList.clear();
                            getTotalPrice();
                            dataBean.clear();
                            mOrderList.clear();
                            CartId.clear();
                            mProducts.clear();
                            mBtnClearing.setEnabled(false);
                            LogUtils.d("取消选中购物车ID="+dataBean.size());*/
                        }else { //选中时，把当前商品的信息保存起来
                            type = "sing";
                        }
                        //判断所有的item是否都选中
                        count = 0;
                        for (int i = 0; i < mAdapter.getDataList().size(); i++) {
                            if (mAdapter.getDataList().get(i).isCheck()) {
                                count++;
                            }
                        }
                        //没有项目选中时，结算按钮不可用
                        LogUtils.d("被选中数量=="+count);
                        if (count == 0) {
                            totalCount = 0;
                            totalPrice = 0;
                            mTvCartMoneyTotal.setText(ActivityUtils.changeMoneys(totalPrice));
                            mBtnClearing.setText("结算("+totalCount+")");
                            mBtnClearing.setEnabled(false);
                            mIvDelete.setEnabled(false);
                            orderList.clear();
                            SingleBean.clear();
                            dataBean.clear();
                        } else {
                            mBtnClearing.setEnabled(true);
                            mIvDelete.setEnabled(true);
                        }
                        getTotalPrice();
                        break;
                    default:
                        mCheckBox.setChecked(false);
                        Toast toast = Toast.makeText(getActivity(),bean.getErrMsg(),Toast.LENGTH_LONG);
                        ActivityUtils.showMyToast(toast,4*1000);
                        break;
                }
            }
        });
    }

    //结算时判断是否有不同币种商品
    private void requestCloseAccount(String userId,int isAndroid,List<CloseAccountRequest.ProductsBean> products){
        CloseAccountRequest request = new CloseAccountRequest(userId,isAndroid,products);
        RetrofitClient.getInstancesTest().requestCloseAccount(request).enqueue(new UICallBack<CloseAccountResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
                mBtnClearing.setEnabled(true);
            }

            @Override
            public void OnRequestSuccess(CloseAccountResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        Intent intent = new Intent(getActivity(), SubmitOrderActivity.class);
                        intent.putExtra("DataBean", (Serializable) orderList);
                        intent.putExtra("currency",mAdapter.getmCurrency());
                        intent.putExtra("totalMoney",totalPrice);
                        intent.putExtra("isShow",bean.getData().getIsKVE20D());
                        startActivity(intent);
                        break;
                    case 3:
                        showToast(bean.getErrMsg());
                        mProducts.clear();
                        orderList.clear();
                        getTotalPrice();
                        mBtnClearing.setEnabled(true);
                        break;
                    default:
                        mProducts.clear();
                        orderList.clear();
                        getTotalPrice();
                        mBtnClearing.setEnabled(true);
                        Toast toast = Toast.makeText(getActivity(),bean.getErrMsg(),Toast.LENGTH_LONG);
                        ActivityUtils.showMyToast(toast,4*1000);
                        break;
                }
            }
        });
    }


}
