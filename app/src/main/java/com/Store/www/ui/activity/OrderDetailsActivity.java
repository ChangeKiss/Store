package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonFooter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.CancelOrderRequest;
import com.Store.www.entity.ConfirmOrderRequest;
import com.Store.www.entity.DeleteOrderRequest;
import com.Store.www.entity.OrderDetailsResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.OrderDetailsAdapter;
import com.Store.www.ui.adapter.OrderDetailsImageAdapter;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.liji.imagezoom.util.ImageZoom;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详情界面
 */
public class OrderDetailsActivity extends BaseToolbarActivity implements OrderDetailsImageAdapter.OnClickListener,
        DialogHint.OnDialogTwoButtonClickListener {

    //头布局显示的数据
    TextView mTvDetailsName;  //收件人
    TextView mTvDetailsPhone;  //电话
    TextView mTvDetailsAddress;  //地址
    TextView mTvOrderDetailsNumber;  //订单编号
    TextView mTvOrderDetailsType;  //订单类型
    TextView mTvOrderDetailsState;  //订单状态
    TextView mTvOrderDetailsCount;  //订单数量
    TextView mTvOrderDetailsMoney;   //订单金额
    TextView mTvOrderDetailsTime;  //下单时间
    TextView mTvAgentName;  //代理姓名
    TextView mTvAgentNumber;  //代理编号
    TextView mTvAgentPhone;  //代理电话
    LinearLayout mLayoutBalanceDeduction;  //余额抵扣布局
    TextView mTvBalanceDeduction;  //余额抵扣

    @BindView(R.id.ry_order_details)
    LRecyclerView mRy;  //订单详情列表

    //尾布局显示的数据
    RecyclerView mOrderDetailsRv;    //尾布局订单详情图片列表
    LinearLayout mLayoutOrderImage;   //尾布局的商品详情图片布局

    @BindView(R.id.btn_order_details_pay)
    TextView mBtnOrderDetailsPay;//支付按钮
    @BindView(R.id.btn_order_cancel_pay)
    TextView mBtnOrderCancelPay;     //取消支付按钮

    @BindView(R.id.tv_order_details_total_money)
    TextView mTvOrderDetailsTotalMoney;  //合计金额

    LRecyclerViewAdapter viewAdapter;
    OrderDetailsImageAdapter mAdapterTwo;  //订单详情图片适配器
    OrderDetailsAdapter mAdapter;  //订单详情适配器
    private CommonHeader mCommonHeader;  //头布局
    private CommonFooter mCommonFooter;  //尾布局
    private String orderNo,name,phone,address,typeName; //订单ID
    private String mCurrency;  //货币类型
    private List<String> imageUrl = new ArrayList<>(); //浏览的图片集合
    private int mStatus,count,money; //订单状态 数量 金额
    private int isUseBalance,useBalance;  //是否使用余额   使用的余额
    private long createTime;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        orderNo = getIntent().getStringExtra("orderNo");
        mCurrency = getIntent().getStringExtra("currency");
        mStatus = getIntent().getIntExtra("status", 0);
        initToolbar(this, true, R.string.order_details);
        initHead();
    }


    //初始化头布局  /尾布局
    private void initHead(){
        mCommonFooter = new CommonFooter(mContext,R.layout.layout_order_detail_tail);
        mLayoutOrderImage = (LinearLayout) mCommonFooter.findViewById(R.id.layout_order_image);  //尾布局显示图片的布局
        mOrderDetailsRv = (RecyclerView) mCommonFooter.findViewById(R.id.order_details_rv);  //尾布局图片列表
        mCommonHeader = new CommonHeader(mContext,R.layout.layout_order_detail_head);
        mTvDetailsName = (TextView) mCommonHeader.findViewById(R.id.tv_details_name);  //收件人
        mTvDetailsPhone = (TextView) mCommonHeader.findViewById(R.id.tv_details_phone);  //联系电话
        mTvDetailsAddress = (TextView) mCommonHeader.findViewById(R.id.tv_details_address);  //收货地址
        mTvOrderDetailsNumber = (TextView) mCommonHeader.findViewById(R.id.tv_order_details_number);  //订单编号
        mTvOrderDetailsType = (TextView) mCommonHeader.findViewById(R.id.tv_order_details_type);  //订单类型
        mTvOrderDetailsState = (TextView) mCommonHeader.findViewById(R.id.tv_order_details_state);  //订单状态
        mTvOrderDetailsCount = (TextView) mCommonHeader.findViewById(R.id.tv_order_details_count);  //订单数量
        mTvOrderDetailsMoney = (TextView) mCommonHeader.findViewById(R.id.tv_order_details_money);  //订单金额
        mTvOrderDetailsTime = (TextView) mCommonHeader.findViewById(R.id.tv_order_details_time);   //下单时间
        mLayoutBalanceDeduction = (LinearLayout) mCommonHeader.findViewById(R.id.layout_balance_deduction); //余额抵扣布局
        mTvBalanceDeduction = (TextView) mCommonHeader.findViewById(R.id.tv_order_details_balance);  //余额抵扣
        mTvAgentName = (TextView) mCommonHeader.findViewById(R.id.tv_agent_name);  //代理姓名
        mTvAgentNumber = (TextView) mCommonHeader.findViewById(R.id.tv_agent_number);  //代理编号
        mTvAgentPhone = (TextView) mCommonHeader.findViewById(R.id.tv_agent_phone);  //代理电话
        initAdapter();
    }


    //初始化适配器
    private void initAdapter(){
        mAdapter = new OrderDetailsAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(mCommonHeader);  //添加头布局
        viewAdapter.addFooterView(mCommonFooter);  //添加尾布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(viewAdapter);
        mAdapterTwo = new OrderDetailsImageAdapter(mContext, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mOrderDetailsRv.setLayoutManager(gridLayoutManager);
        mOrderDetailsRv.setAdapter(mAdapterTwo);
        if (mStatus == 3) { //待付款  需要取消订单，支付订单
            mBtnOrderCancelPay.setVisibility(View.VISIBLE);
            mBtnOrderCancelPay.setText("取消订单");
            mBtnOrderDetailsPay.setVisibility(View.VISIBLE);
            mBtnOrderDetailsPay.setText("支付订单");
        } else if (mStatus==1){ //发货中  确认收货，查看物流
            mBtnOrderCancelPay.setVisibility(View.VISIBLE);
            mBtnOrderCancelPay.setText("确认收货");
            mBtnOrderDetailsPay.setVisibility(View.VISIBLE);
            mBtnOrderDetailsPay.setText("查看物流");
        }else if (mStatus==2){//已完成  查看物流
            mBtnOrderCancelPay.setVisibility(View.GONE);
            mBtnOrderDetailsPay.setVisibility(View.VISIBLE);
            mBtnOrderDetailsPay.setText("查看物流");
        }else if (mStatus==99){ //已取消  删除订单
            mBtnOrderCancelPay.setVisibility(View.GONE);
            mBtnOrderDetailsPay.setVisibility(View.VISIBLE);
            mBtnOrderDetailsPay.setText("删除订单");
        }else {
            mBtnOrderCancelPay.setVisibility(View.GONE);
            mBtnOrderDetailsPay.setVisibility(View.GONE);
        }
        mRy.setPullRefreshEnabled(false);  //关闭下拉刷新
        mRy.setLoadMoreEnabled(true);  //打开加载更多
        getOrderDetails();
    }

    //获取订单详情
    private void getOrderDetails() {
        RetrofitClient.getInstancesTest().getOrderDetails(orderNo, mUserId).enqueue(new UICallBack<OrderDetailsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(OrderDetailsResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        mAdapter.addAll(bean.getOrderProduct());
                        mAdapter.notifyDataSetChanged();
                        name = bean.getReceiveName();
                        phone = bean.getMobilephone();
                        address = bean.getAddress();
                        typeName = bean.getTypeName();
                        count = bean.getCount();
                        money = bean.getTotal();
                        isUseBalance = bean.getIsUseBalance();
                        useBalance = bean.getUseBalance();
                        createTime = bean.getCreateTime();
                        mTvDetailsName.setText(bean.getReceiveName() + "");
                        mTvDetailsPhone.setText(bean.getMobilephone() + "");
                        mTvDetailsAddress.setText(bean.getAddress() + "");
                        mTvOrderDetailsNumber.setText(bean.getOrderNumber() + "");
                        mTvOrderDetailsType.setText(bean.getTypeName() + "");
                        mTvOrderDetailsCount.setText("x " + bean.getCount());
                        mTvOrderDetailsMoney.setText(ActivityUtils.changeMoneys(bean.getTotal()) + "元");
                        if (bean.getAgent()!=null){
                            mTvAgentName.setText(bean.getAgent().getName()); //代理姓名
                            mTvAgentNumber.setText(bean.getAgent().getCode());  //代理编号
                            mTvAgentPhone.setText(bean.getAgent().getMobilephone());  //代理电话
                        }
                        if (useBalance!=0 && Integer.valueOf(useBalance) != null){
                            mLayoutBalanceDeduction.setVisibility(View.VISIBLE);
                            mTvBalanceDeduction.setText(ActivityUtils.changeMoneys(useBalance)+"元");
                        }else {
                            mLayoutBalanceDeduction.setVisibility(View.GONE);
                        }
                        if (bean.getTotal()!=0){
                            mTvOrderDetailsTotalMoney.setText(mCurrency + " " + ActivityUtils.changeMoneys(bean.getTotal()));
                        }
                        if (bean.getPayInfo()!=null&&bean.getPayInfoList() != null) {
                            LogUtils.d("截图=="+bean.getPayInfoList().size());
                            mLayoutOrderImage.setVisibility(View.VISIBLE);
                            mAdapterTwo.addAll(bean.getPayInfoList());
                            mAdapterTwo.notifyDataSetChanged();
                            for (int i = 0; i < bean.getPayInfoList().size(); i++) {
                                imageUrl.add(bean.getPayInfoList().get(i).getPayInfo());
                            }
                        } else {
                            mLayoutOrderImage.setVisibility(View.GONE);
                        }
                        long time = bean.getCreateTime();
                        if (time != 0) {
                            mTvOrderDetailsTime.setText("" + ActivityUtils.time(bean.getCreateTime()) + "");
                        }
                        LogUtils.d("订单状态==" + bean.getStatus());
                        if (bean.getStatus() == 0) {
                            mTvOrderDetailsState.setText(R.string.stay_shipments);
                        } else if (bean.getStatus() == 1) {
                            mTvOrderDetailsState.setText(R.string.shipments_loading);
                        } else if (bean.getStatus() == 2) {
                            mTvOrderDetailsState.setText(R.string.order_over);
                        } else if (bean.getStatus() == 3) {
                            mTvOrderDetailsState.setText(R.string.stay_pay);
                            mLayoutBalanceDeduction.setVisibility(View.GONE);
                        } else if (bean.getStatus() == 4) {
                            mTvOrderDetailsState.setText(R.string.stay_shipments);
                        } else if (bean.getStatus() == 99) {
                            mTvOrderDetailsState.setText(R.string.is_cancel);
                            mLayoutBalanceDeduction.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //订单详情图片的点击事件
    @Override
    public void OnItemClickListener(int position, String url) {
        ImageZoom.show(this, url, imageUrl);
    }

    @OnClick({ R.id.btn_order_details_pay,R.id.btn_order_cancel_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_order_cancel_pay:  //取消订单，确认收货 点击事件
                if (mStatus == 3) { //待付款  需要取消订单
                    mType = "cancel";
                    DialogHint.showDialogWithTwoButton(this,R.string.yes_order_no,this);
                } else if (mStatus==1){ //发货中  确认收货
                    mType = "confirm";
                    DialogHint.showDialogWithTwoButton(this,R.string.yes_confirm_order,this);
                }
                break;
            case R.id.btn_order_details_pay: //支付订单，查看物流  点击事件 删除订单
                if (mStatus==3){  //支付订单
                    Intent intent = new Intent(this,PayActivity.class);
                    intent.putExtra("orderName",name);  //收件人
                    intent.putExtra("orderPhone",phone);  //电话
                    intent.putExtra("orderAddress",address);       //地址
                    intent.putExtra("oderNumber",orderNo);      //订单编号
                    intent.putExtra("oderType",typeName);       //订单类型
                    intent.putExtra("orderCount",count);        //订单数量
                    intent.putExtra("orderMoney",money);        //订单金额
                    intent.putExtra("currency",mCurrency);       //货币符号
                    intent.putExtra("orderTime",createTime);   //下单时间
                    intent.putExtra("isUseBalance",isUseBalance);  //是否使用余额
                    //intent.putExtra("useBalance",useBalance);  //使用的余额
                    startActivity(intent);
                }else if (mStatus==1||mStatus==2){ // 1发货中 2 已完成 查看物流
                    mActivityUtils.startActivity(LogisticsLookOver.class,"orderNumber",orderNo);
                }else if (mStatus==99){  //删除订单
                    mType = "delete";
                    DialogHint.showDialogWithTwoButton(this,R.string.yes_delete_order,this);
                }
                break;
        }
    }

    //取消订单
    private void requestCancelOrder(String orderNumber,String userId){
        CancelOrderRequest request = new CancelOrderRequest(orderNumber,userId);
        RetrofitClient.getInstancesTest().requestCancelMyOrder(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.cancel_ok);
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //确认收货
    private void requestConfirm(int userId,String orderNumber){
        ConfirmOrderRequest request = new ConfirmOrderRequest(userId,orderNumber);
        RetrofitClient.getInstancesTest().requestConfirm(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.affirm_ok);
                        finish(); //关闭界面
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //删除订单
    private void requestDeleteOrder(String userId,String OrderNumber){
        DeleteOrderRequest request = new DeleteOrderRequest(userId,OrderNumber);
        RetrofitClient.getInstancesTest().requestDeleteOrder(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.delete_ok);
                        finish(); //关闭界面
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        if (mType.equals("cancel")){ //取消订单
            requestCancelOrder(orderNo,userId);
        }else if (mType.equals("confirm")){  //确认收货
            requestConfirm(mUserId,orderNo);
        }else if (mType.equals("delete")){
            requestDeleteOrder(userId,orderNo);
        }
        dialog.dismiss();
    }

    //弹窗取消的点击事件
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }
}
