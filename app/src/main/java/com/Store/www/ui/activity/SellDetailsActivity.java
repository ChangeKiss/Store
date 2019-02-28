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
import com.Store.www.entity.PayAffirmRequest;
import com.Store.www.entity.SellOrderDetailsResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.SellDetailsImageAdapter;
import com.Store.www.ui.adapter.SellOrderDetailAdapter;
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
 * 销售详情界面  详情和支付确认 复用此页面
 */
public class SellDetailsActivity extends BaseToolbarActivity implements SellDetailsImageAdapter.OnclickListener,DialogHint.OnDialogTwoButtonClickListener{

    @BindView(R.id.lv_sell_details)
    LRecyclerView mLvSellDetails;  //商品清单加载列表
    TextView mTvSellDetailsName; //收件人姓名
    TextView mTvSellDetailsPhone;   //收件人电话
    TextView mTvSellDetailsAddress; //收件人地址
    TextView mTvSellDetailsNumber;  //订单编号
    TextView mTvSellDetailsType;  //订单类型
    TextView mTvSellDetailsStatus;  //订单状态
    TextView mTvSellDetailsCount;  //订单数量
    TextView mTvSellDetailsMoney;  //订单金额
    TextView mTvSellDetailsTime; //订单时间
    LinearLayout mLayoutSellBalanceDeduction;  //销售管理余额抵扣布局
    TextView mTvSellDetailsBalanceDeduction;  //销售余额抵扣
    TextView mTvAgentName; //代理姓名
    TextView mTvAgentNumber;  //代理编号
    TextView mTvAgentPhone;  //代理电话

    @BindView(R.id.tv_sell_money)
    TextView mTvSellMoney;  //合计金额
    @BindView(R.id.tv_sell_pay_button)
    TextView mTvSellPayButton;  //确认支付的按钮
    /*@BindView(R.id.tv_sell_look_hint)
    TextView mTvSellLookHint;  //查看图片的提示*/
    LinearLayout mLayoutSellPayAffirm;  //支付图片的布局
    RecyclerView mRvSellImage;  //图片加载列表


    private CommonHeader commonHeader;
    private CommonFooter commonFooter;
    LRecyclerViewAdapter viewAdapter;
    SellDetailsImageAdapter mImageAdapter;  //展示图片的适配器
    SellOrderDetailAdapter mOrderDetailsAdapter;  //展示商品清单的适配器
    private String mTitle; //标题
    private String mType;   //用来判断是普通的销售详情还是支付确认 类型
    private String mOrderNumber,mCurrency;  //订单编号 ,货币符号
    private List<String> imageList = new ArrayList<>(); //存放图片url
    private int mStatus;   //订单状态
    private int useBalance;  //使用了的余额
    private int isIShow;  //是否是爱瘦订单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_sell_details;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mType = getIntent().getStringExtra("type");
        mOrderNumber = getIntent().getStringExtra("orderNumber");
        mCurrency = getIntent().getStringExtra("currency");
        mStatus = getIntent().getIntExtra("status",0);
        isIShow = getIntent().getIntExtra("isIShow",0);
        if (mType.equals("ordinary")) { //普通的销售订单详情
            mTitle = "销售详情";
            if (mStatus==4){
                if (isIShow==1){ //是爱瘦订单
                    mTvSellPayButton.setText("接受订单");
                }else {  //普通订单
                    mTvSellPayButton.setText("支付确认");
                }
                mTvSellPayButton.setVisibility(View.VISIBLE);
            }else if(mStatus==0){
                mTvSellPayButton.setText("物流发货");
                mTvSellPayButton.setVisibility(View.VISIBLE);
            }else if (mStatus==1){
                mTvSellPayButton.setText("查看物流");
                mTvSellPayButton.setVisibility(View.VISIBLE);
            }else {
                mTvSellPayButton.setVisibility(View.INVISIBLE);
            }
        } else if (mType.equals("pay")) { //支付确认销售订单详情
            mTitle = "支付确认";
            mTvSellPayButton.setText("支付确认");
            mTvSellPayButton.setVisibility(View.VISIBLE);
        }
        initToolbar(this, true, mTitle);
        initHead();
    }

    //初始化头布局
    private void initHead(){
        commonHeader = new CommonHeader(mContext,R.layout.layout_sell_detail_head);
        mTvSellDetailsName = (TextView) commonHeader.findViewById(R.id.tv_sell_details_name);  //收件人姓名
        mTvSellDetailsPhone = (TextView) commonHeader.findViewById(R.id.tv_sell_details_phone);  //收件人电话
        mTvSellDetailsAddress = (TextView) commonHeader.findViewById(R.id.tv_sell_details_address);  //收件人地址
        mTvSellDetailsNumber = (TextView) commonHeader.findViewById(R.id.tv_sell_details_number);  //订单编号
        mTvSellDetailsType = (TextView) commonHeader.findViewById(R.id.tv_tv_sell_details_type);  //订单类型
        mTvSellDetailsStatus = (TextView) commonHeader.findViewById(R.id.tv_sell_details_status);  //订单状态
        mTvSellDetailsCount = (TextView) commonHeader.findViewById(R.id.tv_sell_details_count);   //订单数量
        mTvSellDetailsMoney = (TextView) commonHeader.findViewById(R.id.tv_sell_details_money);   //订单金额
        mTvSellDetailsTime = (TextView) commonHeader.findViewById(R.id.tv_sell_details_time);  //下单时间
        mLayoutSellBalanceDeduction = (LinearLayout) commonHeader.findViewById(R.id.layout_sell_balance_deduction);  //余额抵扣布局
        mTvSellDetailsBalanceDeduction = (TextView) commonHeader.findViewById(R.id.tv_sell_details_balance);  //抵扣余额
        mTvAgentName = (TextView) commonHeader.findViewById(R.id.tv_agent_name);  //代理姓名
        mTvAgentNumber = (TextView) commonHeader.findViewById(R.id.tv_agent_number);  //代理编号
        mTvAgentPhone = (TextView) commonHeader.findViewById(R.id.tv_agent_phone);  //代理编号
        commonFooter = new CommonFooter(mContext,R.layout.layout_sell_detail_stern);
        mLayoutSellPayAffirm = (LinearLayout) commonFooter.findViewById(R.id.layout_sell_pay_affirm);   //显示付款图片的布局
        mRvSellImage = (RecyclerView) commonFooter.findViewById(R.id.rv_sell_image);  //加载图片的列表
        initAdapter();
        getSellOrderDetails(userId,mOrderNumber);
    }

    //初始化适配器
    private void initAdapter(){
        mImageAdapter = new SellDetailsImageAdapter(this,this);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        mRvSellImage.setLayoutManager(manager);
        mRvSellImage.setAdapter(mImageAdapter);
        mOrderDetailsAdapter = new SellOrderDetailAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mOrderDetailsAdapter);
        viewAdapter.addHeaderView(commonHeader);
        viewAdapter.addFooterView(commonFooter);
        mLvSellDetails.setLayoutManager(new LinearLayoutManager(this));
        mLvSellDetails.setAdapter(viewAdapter);
        mLvSellDetails.setPullRefreshEnabled(false);  //关闭下拉刷新
    }


    //获取订单详情
    private void getSellOrderDetails(String userId,String orderNumber){
        RetrofitClient.getInstances().getSellOrderDetails(userId,orderNumber).enqueue(new UICallBack<SellOrderDetailsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(SellOrderDetailsResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        LogUtils.d("获取到了订单详情");
                        mTvSellDetailsName.setText(bean.getReceiveName());
                        mTvSellDetailsPhone.setText(bean.getMobilephone());
                        mTvSellDetailsAddress.setText(bean.getAddress());
                        mTvSellDetailsNumber.setText(bean.getOrderNumber());
                        mTvSellDetailsType.setText(bean.getTypeName());
                        mTvSellDetailsCount.setText("x" + bean.getCount());
                        mTvSellDetailsMoney.setText(ActivityUtils.changeMoneys(bean.getTotal()) + "元");
                        mTvSellMoney.setText(mCurrency + ActivityUtils.changeMoneys(bean.getTotal()) + "元");
                        if (bean.getAgent()!=null){
                            mTvAgentName.setText(bean.getAgent().getName());    //代理姓名
                            mTvAgentNumber.setText(bean.getAgent().getCode());  //代理编号
                            mTvAgentPhone.setText(bean.getAgent().getMobilephone());  //代理电话
                        }
                        if (bean.getUseBalance()!=0&&Integer.valueOf(bean.getUseBalance())!=null){
                            mLayoutSellBalanceDeduction.setVisibility(View.VISIBLE);
                            mTvSellDetailsBalanceDeduction.setText(ActivityUtils.changeMoneys(bean.getUseBalance())+"元");
                        }else {
                            mLayoutSellBalanceDeduction.setVisibility(View.GONE);
                        }
                        long time = bean.getCreateTime();
                        if (time != 0) {
                            mTvSellDetailsTime.setText("" + ActivityUtils.time(bean.getCreateTime()) + "");
                        }
                        if (bean.getPayInfo()!=null&&bean.getPayInfoList()!=null){
                            mLayoutSellPayAffirm.setVisibility(View.VISIBLE);
                            for (int i=0;i<bean.getPayInfoList().size();i++){
                                imageList.add(bean.getPayInfoList().get(i).getPayInfo());
                            }
                            mImageAdapter.addAll(bean.getPayInfoList());
                            mImageAdapter.notifyDataSetChanged();
                        }else {
                            mLayoutSellPayAffirm.setVisibility(View.GONE);
                        }
                        if (bean.getStatus()==0){  //待发货
                            mTvSellDetailsStatus.setText(R.string.stay_shipments);
                        }else if (bean.getStatus()==1){  //已发货
                            mTvSellDetailsStatus.setText(R.string.already_shipments);
                        }else if (bean.getStatus()==3){  //待付款
                            mTvSellDetailsStatus.setText(R.string.stay_pay);
                            mLayoutSellBalanceDeduction.setVisibility(View.GONE);
                        }else if (bean.getStatus()==4){  //待确认
                            mTvSellDetailsStatus.setText(R.string.stay_ok);
                        }else if (bean.getStatus()==99){  //已取消
                            mTvSellDetailsStatus.setText(R.string.is_cancel);
                            mLayoutSellBalanceDeduction.setVisibility(View.GONE);
                        }
                        mOrderDetailsAdapter.addAll(bean.getOrderProduct());
                        mOrderDetailsAdapter.notifyDataSetChanged();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }


    @OnClick({R.id.tv_sell_pay_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sell_pay_button://确认支付
                if (mStatus==0){ //0待发货
                    Intent intent = new Intent(this,AffirmShipmentsActivity.class);
                    intent.putExtra("orderNumber",mOrderNumber);
                    startActivity(intent);
                    finish();
                }else if (mStatus==4){  //4待确认
                    LogUtils.d("点击了支付按钮");
                    LogUtils.d("订单编号=="+mOrderNumber);
                    if (isIShow==1){  //确认订单时如果是爱瘦订单  弹出提示框提示
                        DialogHint.showDialogWithTwoButton(mContext,R.string.sellOrder_iShow_affirm_hint,this);
                    }else {  //如果是普通订单  直接确认
                        requestPay(mOrderNumber,isIShow);
                    }
                }else if (mStatus==1){ //查看物流
                    //CommonWebActivity.startWebActivity(this,"物流详情","http://121.43.59.111:9005/suiwap/mobile/Kdniao/"+mOrderNumber);
                    mActivityUtils.startActivity(LogisticsLookOver.class,"orderNumber",mOrderNumber);  //查看物流
                }
                break;
        }
    }

    //支付确认
    private void requestPay(String orderNo, final int orderType){
        PayAffirmRequest request = new PayAffirmRequest(orderNo,orderType);
        RetrofitClient.getInstances().requestPayAffirm(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (orderType==1){
                            showToast("接单成功!");
                        }else {
                            showToast(R.string.affirm_ok);
                        }
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //图片的点击事件
    @Override
    public void ItemOnclickListener(int position, String url) {
        ImageZoom.show(this,url,imageList);  //打开图片
    }

    //弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        requestPay(mOrderNumber,isIShow);
        dialog.dismiss();
    }

    //弹窗取消的点击事件
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }
}
