package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AddBillCartRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.EntitySingletonStockResponse;
import com.Store.www.entity.PickUpRepertoryResponse;
import com.Store.www.entity.SaveSingletonStockRequest;
import com.Store.www.net.ExceptionHandle;
import com.Store.www.net.LongConnectionCallBack;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.EntityComeStockAdapter;
import com.Store.www.ui.adapter.EntitySingletonStockAdapter;
import com.Store.www.ui.adapter.PickUpRepertoryAdapter;
import com.Store.www.ui.commom.DialogConnectTimeOut;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 单件商品的提货库存界面  多界面复用
 */
public class PickUpRepertoryActivity extends BaseToolbarActivity implements PickUpRepertoryAdapter.OnRepertoryItemClickListener,
            EntitySingletonStockAdapter.OnEntityRepertoryItemClickListener,EntityComeStockAdapter.OnComeStockItemClickListener,
            DialogConnectTimeOut.TimeOutDialogButtonClickListener{

    @BindView(R.id.rl_pick_up_repertory)
    RecyclerView mRy;
    @BindView(R.id.tv_repertory_sum)
    TextView mTvRepertorySum;
    @BindView(R.id.btn_repertory_cart)
    TextView mBtnRepertoryCart;
    @BindView(R.id.tv_size_location)
    TextView mTvSizeLocation; //尺寸的位置
    @BindView(R.id.tv_stocks_location)
    TextView mTvStocksLocation;  //剩余库存控件的位置
    @BindView(R.id.tv_my_stock_number)
    TextView mTvStockNumber;  //我的库存数量
    @BindView(R.id.tv_number_location)
    TextView mTvNumberLocation;

    final List<AddBillCartRequest.SKUdataBean> been = new ArrayList<>();
    AddBillCartRequest.SKUdataBean mBean ;  //云商品库存提货调整数据
    List<SaveSingletonStockRequest.SkuListBean> listBeen = new ArrayList<>();
    SaveSingletonStockRequest.SkuListBean mListBean;  //实体商品库存调整数据

    List<SaveSingletonStockRequest.SkuListBean> listBeens = new ArrayList<>();
    SaveSingletonStockRequest.SkuListBean mListBeans;

    PickUpRepertoryAdapter mAdapter;  //云仓库商品库存
    EntitySingletonStockAdapter mAdapterTwo;  //实体商品的单件库存适配器
    EntityComeStockAdapter mAdapterThree;  //实体商品出库的适配器
    private String mTitle, productNo;
    private int mRepositoryId, mType,productId,id,sumCount,comeSum;
    public int[] RepertorySum = new int[]{};
    private int[] EntityStockSum = new int[]{};  //实体仓库商品调整总数
    private int[] ComeStockSum = new int[]{};   //实体仓库商品出库总数
    private int mCount=0;
    LinearLayout.LayoutParams params;
    private String mTypes;  //判断是实体仓库 还是云仓 的单件商品库存
    private int mWarehouseId,mProductId;  //仓库ID  商品ID
    private int mStockNumber;  //我的库存数量
    private String DialogType;  //连接超时类型  用来判断再次请求时 是请求哪一个接口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_pick_up_repertory;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mTitle = getIntent().getStringExtra("title");
        productNo = getIntent().getStringExtra("productNo");
        mRepositoryId = getIntent().getIntExtra("repositoryId", 0);
        mType = getIntent().getIntExtra("type", 0);
        mTypes = getIntent().getStringExtra("type");
        mWarehouseId = getIntent().getIntExtra("id",0);
        mProductId = getIntent().getIntExtra("productId",0);
        productId = getIntent().getIntExtra("productId",0);
        id = getIntent().getIntExtra("id",0); //加入提货车用的
        initToolbar(this, true, mTitle);
        if (mTypes!=null && mTypes.equals("entityStock")){  //如果是查看实体仓库商品库存 就加载实体商品数据
            mTvNumberLocation.setText("库存数量");
            mTvRepertorySum.setText("数量：");
            mTvStocksLocation.setVisibility(View.INVISIBLE);
            mAdapterTwo = new EntitySingletonStockAdapter(this,this);
            mRy.setLayoutManager(new LinearLayoutManager(this));
            mRy.setAdapter(mAdapterTwo);
            getEntityStock(mProductId,mWarehouseId);
        }else if (mTypes!=null && mTypes.equals("comeStock")){   //出库商品库存
            mTvNumberLocation.setText("出库数量");
            mTvRepertorySum.setText("数量：");
            mAdapterThree = new EntityComeStockAdapter(this,this);
            mRy.setLayoutManager(new LinearLayoutManager(this));
            mRy.setAdapter(mAdapterThree);
            getEntityStock(mProductId,mWarehouseId);
        }else {  //否则加载云商品数据
            mAdapter = new PickUpRepertoryAdapter(this, this);
            mRy.setLayoutManager(new LinearLayoutManager(this));
            mRy.setAdapter(mAdapter);
            getRepertory(mRepositoryId, mType, productNo);
        }
    }

    //动态设置控件的位置
    private void setViewLocation(){
        params=(LinearLayout.LayoutParams) mTvSizeLocation.getLayoutParams();
        params=(LinearLayout.LayoutParams) mTvStocksLocation.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvNumberLocation.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3;
        mTvSizeLocation.setLayoutParams(params);
        mTvStocksLocation.setLayoutParams(params);
        mTvNumberLocation.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    //获取商品库存信息
    private void getRepertory(int repositoryId, int type, String productNo) {
        DialogLoading.shows(mContext,"加载中...");
        RetrofitClient.getInstances().getRepertory(repositoryId, type, productNo).enqueue(new UICallBack<PickUpRepertoryResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(PickUpRepertoryResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()) {
                        case 1:
                            mStockNumber = bean.getData().getTotalCount();
                            RepertorySum = new int[bean.getData().getList().size()];
                            for (int i = 0; i < RepertorySum.length; i++) {
                                RepertorySum[i] = bean.getData().getList().get(i).getCurrentCount();
                                mBean = new AddBillCartRequest.SKUdataBean();
                                mBean.setName(bean.getData().getList().get(i).getColor()); //名字就是颜色
                                mBean.setSize(bean.getData().getList().get(i).getSize()); //编号
                                mBean.setSku(bean.getData().getList().get(i).getSku()); //大小尺码
                                mBean.setRepositoryCount(bean.getData().getList().get(i).getRepositoryCount());
                                been.add(mBean);
                                sumCount +=RepertorySum[i];
                            }
                            mTvRepertorySum.setText("提货数量："+sumCount);
                            if (mStockNumber!=0){
                                mTvStockNumber.setText("剩余数量: "+(mStockNumber-sumCount));
                            }else {
                                mTvStockNumber.setText("剩余数量: "+0);
                            }
                            mBtnRepertoryCart.setEnabled(true);
                            mAdapter.setTotalCount(bean.getData().getTotalCount());
                            mAdapter.setmRepertorySum(RepertorySum);
                            mAdapter.getDataList().clear();
                            mAdapter.addAll(bean.getData().getList());
                            mAdapter.notifyDataSetChanged();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //减号的点击事件监听
    @Override
    public void setMinusClickListener(int position, int orderSum,int count) {
        mTvRepertorySum.setText("提货数量："+orderSum);
        mTvStockNumber.setText("剩余数量: "+(mStockNumber-orderSum));
        mCount = count;
        LogUtils.d("减号总数=="+orderSum);
        LogUtils.d("单价商品的数量=="+mCount);
        sumCount = orderSum;

    }

    //加号的点击事件的监听
    @Override
    public void setPlusClickListener(int position, int orderSum,int count) {
        mTvRepertorySum.setText("提货数量："+orderSum);
        mTvStockNumber.setText("剩余数量: "+(mStockNumber-orderSum));
        mCount = count;
        LogUtils.d("加号总数=="+orderSum);
        LogUtils.d("单价商品的数量=="+mCount);
        sumCount = orderSum;

    }

    //加入提货车的点击事件
    @OnClick(R.id.btn_repertory_cart)
    public void onViewClicked() {
        mBtnRepertoryCart.setEnabled(false);
        if (mTypes!=null && mTypes.equals("entityStock")){ //保存实体仓库出库记录
            requestSaveEntityStock(mWarehouseId,listBeen);
        }else if (mTypes!=null && mTypes.equals("comeStock")){ //保存出库记录
            requestSaveComeStock(mWarehouseId,listBeens);
        }else {  //保存提货单记录
            if (mAdapter.getmRepertorySum().length==0){
                showToast("商品数量异常,请重新选择");
            }else {
                addBillCart();
            }
        }
    }

    //加入提货篮
    private void addBillCart(){
        for (int i=0;i<mAdapter.getmRepertorySum().length;i++){
            been.get(i).setCount(mAdapter.getmRepertorySum()[i]);
        }
        AddBillCartRequest request = new AddBillCartRequest(mRepositoryId,userId,id,productId,sumCount,been);
        RetrofitClient.getApiLongConnection().requestAddBill(request).enqueue(new LongConnectionCallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(int code) {
                if (isTop)checkNet();
            }

            @Override
            public void OnError(ExceptionHandle.ResponeThrowable throwable) {
                if (isTop){
                    if (throwable.code == ExceptionHandle.ERROR.TIMEOUT_ERROR){
                        DialogConnectTimeOut.ShowTimeOutDialog(PickUpRepertoryActivity.this,PickUpRepertoryActivity.this);
                        DialogType = "ADD_BILL";
                    }
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            showToast(R.string.plus_ok);
                            Intent intent = new Intent();
                            intent.putExtra("isNew",0);
                            intent.putExtra("oneSum",mCount);
                            setResult(RESULT_OK,intent);
                            finish();
                            break;
                        default:
                            mBtnRepertoryCart.setEnabled(true);
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //获取实体仓库单件商品库存
    private void getEntityStock(int productId,int warehouseId){
        DialogLoading.shows(mContext,"加载中...");
        RetrofitClient.getInstances().getEntitySingletonStock(productId,warehouseId).enqueue(new UICallBack<EntitySingletonStockResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(EntitySingletonStockResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            EntityStockSum = new int[bean.getList().size()];
                            ComeStockSum = new int[bean.getList().size()];
                            for (int i=0;i<EntityStockSum.length;i++){
                                mListBean = new SaveSingletonStockRequest.SkuListBean();
                                mListBeans = new SaveSingletonStockRequest.SkuListBean();
                                mListBean.setProColSizeId(bean.getList().get(i).getId());
                                mListBean.setSum(bean.getList().get(i).getRepositoryCount());
                                mListBeans.setProColSizeId(bean.getList().get(i).getId());
                                mListBeans.setSum(bean.getList().get(i).getOutSum());
                                listBeen.add(mListBean);
                                listBeens.add(mListBeans);
                                EntityStockSum[i] = bean.getList().get(i).getRepositoryCount();
                                ComeStockSum[i] = bean.getList().get(i).getOutSum();
                                sumCount +=EntityStockSum[i];
                                comeSum +=ComeStockSum[i];
                            }
                            mBtnRepertoryCart.setEnabled(true);
                            if (mTypes.equals("entityStock")){
                                mTvRepertorySum.setText("数量："+sumCount);
                                mAdapterTwo.setRepertorySum(EntityStockSum);
                                mAdapterTwo.addAll(bean.getList());
                                mAdapterTwo.notifyDataSetChanged();
                            }else if (mTypes.equals("comeStock")){
                                mTvRepertorySum.setText("数量："+comeSum);
                                mAdapterThree.setmComeStockSum(ComeStockSum);
                                mAdapterThree.addAll(bean.getList());
                                mAdapterThree.notifyDataSetChanged();
                            }
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }

    //减号的点击事件
    @Override
    public void setEntityMinusClickListener(int position, int orderSum, int count) {
        for (int i=position;i<EntityStockSum.length;i++){
            listBeen.get(position).setSum(count);
        }
        mTvRepertorySum.setText("数量："+orderSum);
        mCount = count;
        LogUtils.d("减号总数=="+orderSum);
        LogUtils.d("单件商品的数量=="+mCount);
    }

    //加号的点击事件
    @Override
    public void setEntityPlusClickListener(int position, int orderSum, int count) {
        for (int i=position;i<EntityStockSum.length;i++){
            listBeen.get(position).setSum(count);
        }
        mTvRepertorySum.setText("数量："+orderSum);
        mCount = count;
        LogUtils.d("加号总数=="+orderSum);
        LogUtils.d("单件商品的数量=="+mCount);
    }

    //保存实体仓库调整的数量
    private void requestSaveEntityStock(int warehouseId,List<SaveSingletonStockRequest.SkuListBean> listBeen){
        SaveSingletonStockRequest request = new SaveSingletonStockRequest(warehouseId,listBeen);
        RetrofitClient.getInstances().requestSaveEntitySingletonStock(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            finish();
                            break;
                        default:
                            mBtnRepertoryCart.setEnabled(true);
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //减号点击事件
    @Override
    public void setComeStockMinusClickListener(int position, int orderSum, int count) {
        for (int i=position;i<ComeStockSum.length;i++){
            listBeens.get(position).setSum(count);
        }
        mTvRepertorySum.setText("数量："+orderSum);
        mCount = count;
        LogUtils.d("减号总数=="+orderSum);
        LogUtils.d("单件商品的数量=="+mCount);
    }

    //加号点击事件
    @Override
    public void setComeStockPlusClickListener(int position, int orderSum, int count) {
        for (int i=position;i<ComeStockSum.length;i++){
            listBeens.get(position).setSum(count);
        }
        mTvRepertorySum.setText("数量："+orderSum);
        mCount = count;
        LogUtils.d("加号总数=="+orderSum);
        LogUtils.d("单件商品的数量=="+mCount);
    }


    //保存出库单
    private void requestSaveComeStock(int warehouseId,List<SaveSingletonStockRequest.SkuListBean> listBeen){
        SaveSingletonStockRequest request = new SaveSingletonStockRequest(warehouseId,listBeen);
        RetrofitClient.getInstances().requestSaveComeStock(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            finish();
                            break;
                        default:
                            mBtnRepertoryCart.setEnabled(true);
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //连接超时取消的点击事件
    @Override
    public void OnCancelClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }

    //连接超时再次请求的点击事件
    @Override
    public void OnDismissClickListener(AlertDialog dialog) {
        dialog.dismiss();
        addBillCart();
    }
}
