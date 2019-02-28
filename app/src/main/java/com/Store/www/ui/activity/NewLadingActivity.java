package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.CMBPayUrlResponse;
import com.Store.www.entity.CommodityStocksResponse;
import com.Store.www.entity.LocationResponse;
import com.Store.www.entity.MyWarehouseResponse;
import com.Store.www.entity.PayExpressRequest;
import com.Store.www.entity.PickUpAndCommodityResponse;
import com.Store.www.entity.PickUpRemindResponse;
import com.Store.www.entity.SubmitBillRequest;
import com.Store.www.entity.SubmitPickUpGoodsResponse;
import com.Store.www.entity.SubmitSubordinateBillRequest;
import com.Store.www.net.ExceptionHandle;
import com.Store.www.net.LongConnectionCallBack;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.NewLadingAdapter;
import com.Store.www.ui.commom.DialogConnectTimeOut;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增提货单界面
 */

public class NewLadingActivity extends BaseToolbarActivity implements NewLadingAdapter.OnItemClickListener,DialogConnectTimeOut.TimeOutDialogButtonClickListener{
    @BindView(R.id.rl_pick_up_goods)
    RecyclerView mRy;
    @BindView(R.id.tv_take_people)
    TextView mTvTakePeople; //收货人
    @BindView(R.id.tv_pick_phone_number)
    TextView mTvPickPhoneNumber;  //电话
    @BindView(R.id.tv_pick_adders)
    TextView mTvPickAdders;  //地址
    @BindView(R.id.tv_null_address_hint)
    TextView mTvNullAddressHint;  //没有地址的提示
    @BindView(R.id.tv_sum_pick)
    TextView mTvSumPick; //总提货
    @BindView(R.id.tv_sum_repertory)
    TextView mTvSumRepertory; //总库存
    @BindView(R.id.tv_sum_surplus)
    TextView mTvSumSurplus;  //总剩余
    @BindView(R.id.btn_submit)
    TextView mTvBtnSubmit;   //提交按钮
    @BindView(R.id.layout_Subordinate)
    LinearLayout mLayoutSubordinate;  //输入下级代理编号布局
    @BindView(R.id.tv_Subordinate_agent)
    TextView mTvSubordinateAgent;  //下架代理编号输入框
    @BindView(R.id.Rl_pick_head)
    RelativeLayout mRlPickHead;  //提货地址
    @BindView(R.id.nodata)
    RelativeLayout mNodata;


    private String mSelectName;//选择地址返回的地址信息select
    private String mSelectPhoneNumber;
    private String mSelectCity;
    private String mSelectAddress;
    private String mSelectProvince;
    private String mSelectArea;
    private int mSelectAddressId = 0;

    private AlertDialog mDialog;  //支付运费弹窗
    private AlertDialog mDialogInputPassword;  //验证密码输入弹窗
    private int present;  //点击的item下标用来给item设置提货数
    private int mProductId; //商品Id
    NewLadingAdapter mAdapter;
    private int mAllSum; //总库存
    private int mRepositoryId, mType;
    private int mSume;  //提交数量
    private int typeId = 1, orderType = 1; //提交提货单使用的ID 先写死1
    static final int ORDER_SELECT_ADDRESS = 11;
    private int mPayType =0;  //运费支付类型 0是H5支付 1 调用招行APP 支付
    List<CommodityStocksResponse.DataBean> mData = new ArrayList();
    CommodityStocksResponse.DataBean mDataBean;
    List<Integer> cartId = new ArrayList<>();
    private String mPickUpType;  //提货类型
    private String TimeOutDialogType;//连接超时类型  用来判断再次请求时 是请求哪一个接口
    private String mSubordinateAgent,mPickUpRemind;  //下级代理编号   提货提示

    private String name, phone, address, city, area, province, receiveName,mInputPassword;
    private int[] mSumNumber = new int[]{};
    private int[] oneSum;
    private Network  network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_new_lading;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.new_lading); //给头布局设置标题
        mSubordinateAgent = getIntent().getStringExtra("agentNumber"); //代理编号
        mPickUpType = getIntent().getStringExtra("pickUpType");  //提货类型
        mAdapter = new NewLadingAdapter(this, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(mAdapter);
        //getBill(mUserId, mIsNew);
        if (mPickUpType.equals("Subordinate")){ //给下级代理提货
            mTvSubordinateAgent.setText(mSubordinateAgent);
            mLayoutSubordinate.setVisibility(View.VISIBLE);
            getPickUpRemind();
        }else if (mPickUpType.equals("OneSelf")){  //给自己提货
            mRlPickHead.setVisibility(View.VISIBLE);
            getLocation();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("lock");  //删除地址广播
        intentFilter.addAction("payExpressOver");  //支付运费完成
        network = new Network();
        registerReceiver(network,intentFilter);
    }

    /**
     * 获取提货提示
     */
    private void getPickUpRemind(){
        RetrofitClient.getInstances().getPickUpRemind().enqueue(new UICallBack<PickUpRemindResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(PickUpRemindResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mPickUpRemind = bean.getRemind();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //获取提货篮以及商品库存
    private void getPickUpCommodity(){
        DialogLoading.shows(mContext,"加载中...");
        RetrofitClient.getInstances().getPickUpCommodity(userId,mRepositoryId).enqueue(new UICallBack<PickUpAndCommodityResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(PickUpAndCommodityResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mAdapter.getDataList().clear();
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            mSumNumber = new int[bean.getData().size()];
                            oneSum = new int[bean.getData().size()];
                            for (int i = 0; i < bean.getData().size(); i++) {
                                mSumNumber[i] = bean.getData().get(i).getRepositoryCount();
                                mAllSum += mSumNumber[i];
                                LogUtils.d("第" + i + "款总数==" + mSumNumber[i]);
                                LogUtils.d("总数==" + mAllSum);
                            }
                            mSume = 0;
                            for (int i = 0; i < mSumNumber.length; i++) {
                                oneSum[i] = bean.getData().get(i).getCount();
                                LogUtils.d("第" + i + "款提货数==" + oneSum[i]);
                                mSume += oneSum[i];
                            }
                            mTvSumRepertory.setText("总库存：" + mAllSum + "件");
                            mTvSumSurplus.setText("总剩余：" + (mAllSum - mSume) + "件");
                            break;
                        default:
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
        if (TimeOutDialogType!=null && TimeOutDialogType.equals("SUBMIT_ONESELF")){  //提货给自己
            requestSubmitBill();
        }else if (TimeOutDialogType!=null && TimeOutDialogType.equals("SUBMIT_XIA_JI")){  //提货给下级
            requestSubmitSubordinateBill(mUserId,cartId,mSubordinateAgent,3,ActivityUtils.Md5Password(mInputPassword));
        }else if (TimeOutDialogType!=null && TimeOutDialogType.equals("GET_WARE_HOUSE")){ //获取仓库及库存数据
            if (TextUtils.isEmpty(name)){
                getLocation();
                getWareHouse();
            }else {
                getWareHouse();
            }
        }
    }

    //注册广播并接收
    class Network extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                if (intent.getAction().equals("payExpressOver")){ //运费支付完成 关闭界面
                    finish();
                }else if (intent.getAction().equals("lock")){   //删除了地址重新获取一次
                    getLocation();
                }
            }
            else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //从提货界面提交成功返回时再调一次提货篮接口
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ORDER_SELECT_ADDRESS:
                if (resultCode == 11) {
                    mSelectName = data.getStringExtra("name");
                    mSelectPhoneNumber = data.getStringExtra("phoneNumber");
                    mSelectCity = data.getStringExtra("city");
                    mSelectAddress = data.getStringExtra("address");
                    mSelectAddressId = data.getIntExtra("addressId",0);
                    mSelectProvince = data.getStringExtra("province");
                    mSelectArea = data.getStringExtra("area");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DialogConnectTimeOut.HintDialog!=null && DialogConnectTimeOut.HintDialog.isShowing()){
            DialogConnectTimeOut.HintDialog.dismiss();
        }
        cartId.clear();
        isTop = true;
        mAllSum = 0;
        getWareHouse();
        mTvBtnSubmit.setEnabled(true);
        if (mSelectAddressId != 0) {
            receiveName = mSelectName;
            phone = mSelectPhoneNumber;
            city = mSelectCity;
            address = mSelectAddress;
            province = mSelectProvince;
            area = mSelectArea;
            mTvTakePeople.setVisibility(View.VISIBLE);
            mTvPickPhoneNumber.setVisibility(View.VISIBLE);
            LogUtils.e("wzq:addressId0=" + mSelectAddress);
            mTvTakePeople.setText("收件人："+receiveName);
            mTvPickPhoneNumber.setText(phone);
            mTvPickAdders.setText("地址："+mSelectProvince+mSelectCity+mSelectArea+address);
            mTvNullAddressHint.setVisibility(View.GONE);
            return;
        } else if (mSelectAddressId==0){
            LogUtils.e("wzq:addressId1=" + mSelectAddress);
            getLocation();
        }
        LogUtils.d("data长度===02" + mData.size());
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        network = null;
    }

    //获取地址信息
    private void getLocation() {
        RetrofitClient.getInstances().getLocation(mUserId).enqueue(new UICallBack<LocationResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }
            @Override
            public void OnRequestSuccess(LocationResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()) {
                        case 1:
                            for (int i = 0; i < bean.getData().size(); i++) {
                                if (bean.getData().get(i).getIsUsed() == 1) {
                                    name = bean.getData().get(i).getReceiveName();
                                    phone = bean.getData().get(i).getPhone();
                                    address = bean.getData().get(i).getAddress();
                                    city = bean.getData().get(i).getCity();
                                    area = bean.getData().get(i).getStreet();
                                    province = bean.getData().get(i).getProvince();
                                    receiveName = bean.getData().get(i).getReceiveName();
                                /*LogUtils.d("姓名" + name);
                                LogUtils.d("电话" + phone);
                                LogUtils.d("地址" + address);
                                LogUtils.d("城市" + city);
                                LogUtils.d("区域" + area);
                                LogUtils.d("省/直辖市" + province);
                                LogUtils.d("收件人" + receiveName);*/
                                    mTvNullAddressHint.setVisibility(View.GONE);
                                    mTvTakePeople.setVisibility(View.VISIBLE);
                                    mTvPickPhoneNumber.setVisibility(View.VISIBLE);
                                    mTvTakePeople.setText("收件人：" + name);
                                    mTvPickPhoneNumber.setText(phone);
                                    mTvPickAdders.setText("地址：" + province+city+area+address);
                                    return;
                                }else {
                                    mTvNullAddressHint.setVisibility(View.VISIBLE);
                                    mTvNullAddressHint.setText(R.string.no_default);
                                    mTvTakePeople.setVisibility(View.INVISIBLE);
                                    mTvPickPhoneNumber.setVisibility(View.INVISIBLE);
                                }
                            }
                            if (bean.getData().size()<1){
                                mTvNullAddressHint.setVisibility(View.VISIBLE);
                                mTvNullAddressHint.setText(R.string.no_address);
                                mTvTakePeople.setVisibility(View.INVISIBLE);
                                mTvPickPhoneNumber.setVisibility(View.INVISIBLE);
                            }
                            break;
                    }
                }
            }
        });
    }

    //获取仓库信息
    private void getWareHouse() {
        DialogLoading.shows(mContext,"加载中...");
        RetrofitClient.getApiLongConnection().getWarehouse(agentCode).enqueue(new LongConnectionCallBack<MyWarehouseResponse>() {
            @Override
            public void OnRequestFail(int code) {
                if (isTop)checkNet();
            }

            @Override
            public void OnError(ExceptionHandle.ResponeThrowable throwable) {
                DialogConnectTimeOut.ShowTimeOutDialog(NewLadingActivity.this,NewLadingActivity.this);
                TimeOutDialogType = "GET_WARE_HOUSE";
            }

            @Override
            public void OnRequestSuccess(MyWarehouseResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()) {
                        case 1:
                            if (bean.getData().size()<1){
                                mNodata.setVisibility(View.VISIBLE);
                            }else {
                                mNodata.setVisibility(View.GONE);
                                mRepositoryId = bean.getData().get(0).getRepId();
                                mType = bean.getData().get(0).getType();
                                LogUtils.d("仓库ID=01=" + bean.getData().get(0).getRepId());
                                LogUtils.d("仓库类型=02=" + bean.getData().get(0).getType());
                                //getCommodityStocks(mRepositoryId, mType);
                                getPickUpCommodity();
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

   /* //获取商品库存
    private void getCommodityStocks(int repositoryId, int type) {
        RetrofitClient.getInstances().getStocks(repositoryId, type).enqueue(new UICallBack<CommodityStocksResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(CommodityStocksResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        mAdapter.getDataList().clear();
                        LogUtils.d("长度==" + bean.getData().size());
                        for (int i = 0; i < bean.getData().size(); i++) {
                            mDataBean = new CommodityStocksResponse.DataBean();
                            mDataBean.setProductId(bean.getData().get(i).getProductId());
                            LogUtils.d("商品ID=" + bean.getData().get(i).getProductId());
                            mDataBean.setmCount(0);
                            mData.add(mDataBean);
                        }

                        mSumNumber = new int[bean.getData().size()];
                        for (int i = 0; i < bean.getData().size(); i++) {
                            mSumNumber[i] = bean.getData().get(i).getSum();
                            mAllSum += mSumNumber[i];
                            LogUtils.d("第" + i + "款总数==" + mSumNumber[i]);
                            LogUtils.d("总数==" + mAllSum);
                            LogUtils.d("商品ID==02=" + mData.get(i).getProductId());
                        }
                        mTvSumRepertory.setText("总库存：" + mAllSum + "件");
                        mTvSumSurplus.setText("总剩余：" + (mAllSum - mSume) + "件");
                        mAdapter.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }*/


    //Item的点击事件 进入单件商品库存界面
    @Override
    public void OnItemClickListener(int position, String productNo, String title, int productId,int id) {
        present = position;
        mProductId = productId;
        LogUtils.d("当前点击===" + present);
        Intent intent = new Intent(this, PickUpRepertoryActivity.class);
        intent.putExtra("repositoryId", mRepositoryId);
        intent.putExtra("type", mType);
        intent.putExtra("productNo", productNo);
        intent.putExtra("title", title);
        intent.putExtra("productId", productId);
        intent.putExtra("id",id);
        startActivityForResult(intent, 1);
    }

    @Override
    public void OnCheckBokClickListener(int position, boolean isCheck, CheckBox checkBox) {
        if (isCheck){
            checkBox.setChecked(true);
            mSume = 0;
            for (int i = 0; i < mSumNumber.length; i++) {
                if (mAdapter.getDataList().get(i).isCheck()){
                    oneSum[i] = mAdapter.getDataList().get(i).getCount();
                    //LogUtils.d("第" + i + "款提货数==" + oneSum[i]);
                    mSume += oneSum[i];
                }
            }
            mTvBtnSubmit.setText("提交(" + mSume + ")");
        }else {
            checkBox.setChecked(false);
            mSume = 0;
            for (int i = 0; i < mSumNumber.length; i++) {
                if (mAdapter.getDataList().get(i).isCheck()){
                    oneSum[i] = mAdapter.getDataList().get(i).getCount();
                    //LogUtils.d("第" + i + "款提货数==" + oneSum[i]);
                    mSume += oneSum[i];
                }
            }
            mTvBtnSubmit.setText("提交(" + mSume + ")");
        }
    }


    /**
     * 提交给自己的提货请求
     */
    private void requestSubmitBill() {
        SubmitBillRequest request = new SubmitBillRequest(userId, typeId, orderType, city, area, address, phone, province, receiveName, cartId);
        RetrofitClient.getApiLongConnection().requestSubmitBill(request).enqueue(new LongConnectionCallBack<SubmitPickUpGoodsResponse>() {
            @Override
            public void OnRequestFail(int code) {
                if (isTop){
                    cartId.clear();
                    checkNet();
                }
            }

            @Override
            public void OnError(ExceptionHandle.ResponeThrowable throwable) {
                if (isTop){
                    if (throwable.code == ExceptionHandle.ERROR.TIMEOUT_ERROR){
                        DialogConnectTimeOut.ShowTimeOutDialog(NewLadingActivity.this,NewLadingActivity.this);
                        TimeOutDialogType = "SUBMIT_ONESELF";
                    }
                }
            }

            @Override
            public void OnRequestSuccess(SubmitPickUpGoodsResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()) {
                        case 1:
                            if (bean.getData().getIsPayExpress()==1){
                                payDialogShow(mContext,bean.getData().getPayExpressTotal(),bean.getData().getOrderNumber());
                            }else {
                                showToast(R.string.submit_ok);
                                Intent intent = new Intent();  //发送一个广播到提货单界面
                                intent.setAction("pickUp");
                                sendBroadcast(intent);
                                finish();
                            }
                            break;
                        default:
                            mTvBtnSubmit.setEnabled(true);
                            cartId.clear();
                            Toast toast = Toast.makeText(NewLadingActivity.this,bean.getErrMsg(),Toast.LENGTH_LONG);
                            ActivityUtils.showMyToast(toast,10*500);
                            break;
                    }
                }
            }
        });
    }

    /**
     * 提交给下级提货的请求
     * @param userId
     * @param cartIds
     * @param code
     * @param type
     */
    private void requestSubmitSubordinateBill(int userId,List<Integer> cartIds,String code,int type,String password){
        SubmitSubordinateBillRequest request = new SubmitSubordinateBillRequest(userId,code,type,password,cartIds);
        RetrofitClient.getApiLongConnection().requestSubmitSubordinateBill(request).enqueue(new LongConnectionCallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(int code) {
                if (isTop)checkNet();
            }

            @Override
            public void OnError(ExceptionHandle.ResponeThrowable throwable) {
                if (isTop){
                    if (throwable.code == ExceptionHandle.ERROR.TIMEOUT_ERROR){
                        mDialogInputPassword.dismiss();
                        DialogConnectTimeOut.ShowTimeOutDialog(NewLadingActivity.this,NewLadingActivity.this);
                        TimeOutDialogType = "SUBMIT_XIA_JI";
                    }
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mDialogInputPassword.dismiss();
                            showToast(R.string.submit_ok);
                            Intent intent = new Intent();  //发送一个广播到提货单界面
                            intent.setAction("pickUp");
                            sendBroadcast(intent);
                            finish();
                            break;
                        default:
                            mDialogInputPassword.dismiss();
                            cartId.clear();
                            mTvBtnSubmit.setEnabled(true);
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //弹出运费支付弹窗
    private void payDialogShow(Context context,int money,String orderNo){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_apply_for_ok_two_btn,null);
        TextView mTvTitle = view.findViewById(R.id.tv_apply_for_dialog_title);
        TextView mTvContext = view.findViewById(R.id.tv_apply_for_context);
        Button mBtnOk = view.findViewById(R.id.btn_apply_for_dialog_ok);
        Button mBtnCancel = view.findViewById(R.id.btn_apply_for_dialog_cancel);
        mTvTitle.setText("支付运费");
        mTvContext.setText("该提货单需要支付"+ActivityUtils.changeMoneys(money)+"元的运费,\n是否前往支付?");
        mDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();
        mDialog.show();
        mBtnOk.setOnClickListener(v -> {  //确认支付按钮
            if (ActivityUtils.isCMBAppInstalled(mContext)){  //判断是否安装APP
                mPayType = 1;  //装了
                requestPayExpress(orderNo,mPayType);
            }else {
                mPayType = 0;  //没装
                requestPayExpress(orderNo,mPayType);
            }
        });
        mBtnCancel.setOnClickListener(v -> {  //取消按钮
            mDialog.dismiss();
            finish();
        });
    }

    //发起支付运费请求
    private void requestPayExpress(String orderNo,int payType){
        PayExpressRequest request = new PayExpressRequest(orderNo,payType);
        RetrofitClient.getInstances().requestPayExpress(request).enqueue(new UICallBack<CMBPayUrlResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }
            @Override
            public void OnRequestSuccess(CMBPayUrlResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData()!=null){
                                if (payType==0){
                                    Intent intent = new Intent(NewLadingActivity.this,CommonWebActivity.class);
                                    intent.putExtra("url",bean.getData());
                                    intent.putExtra("type","HTML");
                                    intent.putExtra("title","一网通支付");
                                    startActivity(intent);
                                }else if (payType ==1){
                                    final String payUrl = "cmbmobilebank://CMBLS/FunctionJump?action=gofuncid&funcid=200007&serverid=CMBEUserPay&requesttype=post&cmb_app_trans_parms_start=here&charset=utf-8&jsonRequestData="+
                                            bean.getData();
                                    LogUtils.d("支付链接=="+payUrl);
                                    Intent intent = new Intent();
                                    Uri uri = Uri.parse(payUrl);
                                    intent.setData(uri);
                                    intent.setAction("android.intent.action.VIEW");
                                    startActivity(intent);
                                }
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

    @OnClick({R.id.Rl_pick_head, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Rl_pick_head:  //点击去选择地址
                //点击可以去地址页面选个地址
                Intent intent = new Intent(this, LocationManagerActivity.class);
                intent.putExtra("selectAddress", "order");
                startActivityForResult(intent, ORDER_SELECT_ADDRESS);
                break;
            case R.id.btn_submit:  //提交提货单
                mTvBtnSubmit.setEnabled(false);
                List<PickUpAndCommodityResponse.DataBean> been = mAdapter.getDataList();
                for (int i=0;i<mAdapter.getDataList().size();i++){
                    if (been.get(i).isCheck()){
                        cartId.add(been.get(i).getId());
                    }
                }
                if (cartId.size()==0){
                    showToast("请选择提货商品");
                    mTvBtnSubmit.setEnabled(true);
                    return;
                }
                if (mPickUpType.equals("Subordinate")){  //给下级提货
                    showInputPassword();
                }else if (mPickUpType.equals("OneSelf")){  //给自己提货
                    if (address == null){  //如果没有默认地址
                        showToast(R.string.no_default);
                        mTvBtnSubmit.setEnabled(true);
                        cartId.clear();
                        return;
                    }
                    requestSubmitBill();
                }
                break;
        }
    }

    /**
     * 弹出密码验证输入框
     */
    private void showInputPassword(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_input_password,null);
        TextView mTvPasswordTitle = view.findViewById(R.id.tv_input_password_title);
        EditText mEtPasswordContent = view.findViewById(R.id.et_input_password_content);
        TextView mTvInputPasswordHint = view.findViewById(R.id.tv_input_password_hint);  //提示内容
        TextView mTvPasswordCancel = view.findViewById(R.id.tv_cancel_input);
        TextView mTvPasswordAffirm = view.findViewById(R.id.tv_affirm_input);
        mEtPasswordContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mInputPassword = mEtPasswordContent.getText().toString();
                //LogUtils.d("输入密码=="+mInputPassword);
            }
        });
        mTvInputPasswordHint.setText(mPickUpRemind);
        mDialogInputPassword = new AlertDialog.Builder(mContext).setView(view).create();
        mDialogInputPassword.setCancelable(false);
        mDialogInputPassword.show();
        mTvPasswordCancel.setOnClickListener(v ->{
            mDialogInputPassword.dismiss();
            mTvBtnSubmit.setEnabled(true);
        });
        mTvPasswordAffirm.setOnClickListener(v -> {
            requestSubmitSubordinateBill(mUserId,cartId,mSubordinateAgent,3,ActivityUtils.Md5Password(mInputPassword));
        });
    }

}
