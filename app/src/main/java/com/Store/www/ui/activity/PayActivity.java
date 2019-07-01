package com.Store.www.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AliPayResponse;
import com.Store.www.entity.BalancePayRequest;
import com.Store.www.entity.BalancePayResponse;
import com.Store.www.entity.BalanceResponse;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.CCBPayResponse;
import com.Store.www.entity.CMBPayRequest;
import com.Store.www.entity.CMBPayUrlResponse;
import com.Store.www.entity.GetPayTypeResponse;
import com.Store.www.entity.GetPayUrlResponse;
import com.Store.www.entity.PayOverResponse;
import com.Store.www.entity.PayTypeResponse;
import com.Store.www.entity.ScreenPayRequest;
import com.Store.www.entity.WhetherPasswordRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.AgentApplyAdapter;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.ui.costomPassword.PopEnterPassword;
import com.Store.www.ui.imageManager.ImageLoader;
import com.Store.www.ui.imageManager.ImgSelActivity;
import com.Store.www.ui.imageManager.ImgSelConfig;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.CompressImage;
import com.Store.www.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付界面
 */
public class PayActivity extends BaseToolbarActivity implements AgentApplyAdapter.OnImageClickListener,
        DialogHint.OnDialogTwoButtonClickListener{
    @BindView(R.id.tv_pay_people)
    TextView mTvPayPeople; //收件人
    @BindView(R.id.tv_pay_phone_number)
    TextView mTvPayPhoneNumber; //电话
    @BindView(R.id.tv_pay_adders)
    TextView mTvPayAddress;     //收货地址
    @BindView(R.id.tv_pay_order_number)
    TextView mTvPayOrderNumber;     //订单编号
    @BindView(R.id.tv_pay_order_type)
    TextView mTvPayOrderType;       //订单类型
    @BindView(R.id.tv_pay_order_state)
    TextView mTvPayOrderState;      //订单状态
    @BindView(R.id.tv_pay_order_count)
    TextView mTvPayOrderCount;      //订单数量
    @BindView(R.id.tv_pay_order_money)
    TextView mTvPayOrderMoney;      //订单金额
    @BindView(R.id.tv_pay_order_time)
    TextView mTvPayOrderTime;  //下单时间
    @BindView(R.id.tv_account_ok_money)
    TextView mTvAccountOkMoney;   //可用余额
    @BindView(R.id.tv_gpfd_pay)
    TextView mTvGpfdPay;     //还需付款金额
    @BindView(R.id.cb_balance_pay)
    CheckBox mCbBalancePay;  //是否使用余额 支付
    @BindView(R.id.layout_is_balance)
    LinearLayout mLayoutIsBalance;  //余额布局
    @BindView(R.id.tv_pay_total_money)
    TextView mTvPayTotalMoney;   //付款总金额
    @BindView(R.id.layout_order_parent)
    LinearLayout mLayoutOrderParent;  //布局
    @BindView(R.id.layout_pay_mode)
    LinearLayout mLayoutPayMode; //选择支付方式
    @BindView(R.id.tv_order_pay_mode)
    TextView mTvOrderPayMode;   //显示支付方式
    @BindView(R.id.layout_plus_image)
    LinearLayout mLayoutPlusImage; //添加截图布局
    @BindView(R.id.rl_pay_image)
    RecyclerView mRy;
    @BindView(R.id.btn_pay_order) //支付按钮
    TextView mBtnPayOrder;
    @BindView(R.id.iv_toolbar_left)
    ImageView mIvToolbarLeft;

    private String mCurrency; //货币符号
    private ProgressDialog mProgressDialog;
    AgentApplyAdapter mAdapter;
    private PopEnterPassword popEnterPassword;  //输入密码pop弹窗
    private static final int IMAGE_MULTI_SELECTOR = 1;  //选择完图片获取参数的值
    private int mPayWay = 0;//支付方式，0是支付宝,1是截图 ,2建行支付,3是京东支付 4是一网通支付
    private int mPays;   //接收请求下来的默认支付方式
    private int mStatus;  //是否还需继续支付
    private int isUserBalance = 1;  //是否使用了余额  默认是使用了
    List<String> mImageData = new ArrayList<>();    //本地图片集合
    List<String> mImagePostData = new ArrayList<>();    //上传到OSS的图片集合
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAIuLid3ovvgdGR";
    private static String accessKeySecret = "H5wC17dWuciVtLYjjx5SRoaucyRMbI";
    private static String bucketName = "fuatee";
    private int balanceMoney,payMoney;
    private String mDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Kivie";
    private String people,mPhone,mAddress,mOrderNumber,mOrderType;
    private int mOrderCount,mOrderMoney;
    public String aliPay;  //支付链接
    private long orderTime;
    private int mPayType; //支付类型 0对私 1对公
    private int payType = 1; //截图支付时用的参数写死1
    private NetWork  networks;
    private String mPassword; //支付密码
    private int mUseBalance,aliPayMoney;  //使用的余额  支付宝要支付的余额这个字段用来显示在刷新支付状态时的标题位置
    private int mIsUseBalance,useBalance;  //是否使用了余额  使用了多少余额  这两个参数是订单界面传过来的
    private boolean isBalance;  //是否使用了余额支付
    private TextView mPayMode[];  //支付类型  动态添加
    private int mAppType = 0;  //判断是否安装过招行APP 0未安装(使用H5支付)， 1已安装(使用APP支付)
    private List<PayTypeResponse.DataBean> DataBeen = new ArrayList<>();  //可用支付方式集合
    PayTypeResponse.DataBean Beans ;
    private String DialogType;  //弹窗类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.pay_order);
        Intent intent = getIntent();
        people = intent.getStringExtra("orderName");
        mPhone = intent.getStringExtra("orderPhone");
        mAddress = intent.getStringExtra("orderAddress");
        mOrderNumber = intent.getStringExtra("oderNumber");
        mOrderType = intent.getStringExtra("oderType");
        mOrderCount = intent.getIntExtra("orderCount",0);
        mOrderMoney = intent.getIntExtra("orderMoney",0);
        mCurrency =intent.getStringExtra("currency");
        orderTime = intent.getLongExtra("orderTime",0);
        mIsUseBalance = intent.getIntExtra("isUseBalance",0);
        useBalance = intent.getIntExtra("useBalance",0);
        mTvPayPeople.setText("收货人:"+people);
        mTvPayPhoneNumber.setText(mPhone);
        mTvPayAddress.setText("收货地址:"+mAddress);
        mTvPayOrderNumber.setText(mOrderNumber);
        mTvPayOrderType.setText(mOrderType);
        mTvPayOrderState.setText(R.string.stay_pay);
        mTvPayOrderCount.setText("x "+mOrderCount);
        mTvPayOrderMoney.setText(ActivityUtils.changeMoneys(mOrderMoney)+"元");
        mTvPayTotalMoney.setText(mCurrency+""+ActivityUtils.changeMoneys(mOrderMoney));
        payMoney = mOrderMoney;
        mTvPayOrderTime.setText(ActivityUtils.times(orderTime));  //下单时间
        aliPay= mOrderNumber;//"DD20180316223676"
        initImageAdapter();  //初始化添加图片的适配器
        popEnterPassword = new PopEnterPassword(PayActivity.this);

        mCbBalancePay.setVisibility(View.VISIBLE);  //余额勾选框展示起来
        mCbBalancePay.setChecked(true);  //把余额勾选框设置为 选中
        getMyBalance();   //获取余额
        getPayType(token,mUserId); //先获取支付类型  0对私  1对公
        getUsablePayType(mUserId,mOrderNumber);  //获取支付方式

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction("withdraw");  //添加支付广播
        filter.addAction("alterPassword");  //添加找回密码广播
        filter.addAction("payOver");  //添加支付完成广播
        filter.addAction("ccb");  //添加建行支付广播
        filter.addAction("close"); //添加关闭密码输入框广播
        networks = new NetWork();
        registerReceiver(networks,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        mBtnPayOrder.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        popEnterPassword = null;
    }

    //获取用户可用的支付方式
    private void getUsablePayType(int userId,String orderNo){
        RetrofitClient.getInstancesTest().getUsablePayType(userId,orderNo).enqueue(new UICallBack<PayTypeResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(PayTypeResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            for (int i=0;i<bean.getData().size();i++){
                                Beans = new PayTypeResponse.DataBean();
                                Beans.setIsOpen(bean.getData().get(i).getIsOpen());
                                Beans.setId(bean.getData().get(i).getId());
                                Beans.setName(bean.getData().get(i).getName());
                                Beans.setChd(bean.getData().get(i).getChd());
                                DataBeen.add(Beans);
                            }
                            //LogUtils.d("共有支付方式"+DataBeen.size()+"种");
                            for (int i=0;i<DataBeen.size();i++){
                                if (DataBeen.get(i).getIsOpen()==1){
                                    //LogUtils.d("默认支付方式="+DataBeen.get(i).getName().toString());
                                    //LogUtils.d("默认支付方式=="+i+"    || 0支付宝支付,1截图支付,2建行支付,3京东支付");
                                    mPayWay = DataBeen.get(i).getChd();
                                    if (mPayWay==0){
                                        mTvOrderPayMode.setText("支付宝支付");
                                        mLayoutPlusImage.setVisibility(View.GONE);
                                    }else if (mPayWay==1){
                                        mTvOrderPayMode.setText("上传截图");
                                        mLayoutPlusImage.setVisibility(View.VISIBLE);
                                    }else if (mPayWay==2){
                                        mTvOrderPayMode.setText("建行支付");
                                        mLayoutPlusImage.setVisibility(View.GONE);
                                    }else if (mPayWay==3){
                                        mTvOrderPayMode.setText("京东支付");
                                        mLayoutPlusImage.setVisibility(View.GONE);
                                    }else if (mPayWay ==4){
                                        mTvOrderPayMode.setText("一网通支付");
                                        mLayoutPlusImage.setVisibility(View.GONE);
                                    }
                                    break;
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

    //弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        if (!TextUtils.isEmpty(DialogType)&&DialogType.equals("setPassword")){
            dialog.dismiss();
            mActivityUtils.startActivity(SettingPayPasswordActivity.class,"type","setting");
        }else if (!TextUtils.isEmpty(DialogType)&&DialogType.equals("CMBPay")){
            if (ActivityUtils.isCMBAppInstalled(mContext)){
                mAppType = 1;  //客户端支付
                getCMBPayUrl(mAppType);
            }else {
                mAppType = 0;  //H5支付
                getCMBPayUrl(mAppType);
            }
            dialog.dismiss();
        }
    }

    //弹窗取消的点击事件
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        dialog.dismiss();
        mBtnPayOrder.setEnabled(true);
    }

    //接收广播
    class NetWork extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null&&networkInfo.isAvailable()){  //如果网络不为空 并且广播是有效的
                if (intent.getAction().equals("withdraw")){  //用户输入了密码
                    mPassword = intent.getStringExtra("password");
                    mBtnPayOrder.setEnabled(false);  //设置支付按钮不可点击
                    //发起余额支付
                    requestBalancePay(mUserId,mOrderNumber,ActivityUtils.Md5Password(mPassword),mStatus,mUseBalance,isUserBalance);  //支付密码需要MD5加密
                }else if (intent.getAction().equals("alterPassword")){
                    LogUtils.d("找回密码");
                    mActivityUtils.startActivity(AlterPayPasswordActivity.class);
                }else if (intent.getAction().equals("ccb")||intent.getAction().equals("payOver")){
                    //mActivityUtils.startActivity(MyOrderActivity.class);
                    Intent intentOrder = new Intent(mContext,MyOrderActivity.class);
                    intentOrder.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intentOrder);
                    finish();
                }else if (intent.getAction().equals("close")){  //关闭了密码输入框
                    LogUtils.d("关闭了输入框");
                    mBtnPayOrder.setEnabled(true);
                }
            }
        }
    }

    //初始化图片适配器
    private void initImageAdapter() {
        mImageData.add("显示加号");//为0设置任意数据，0设置成加号
        mAdapter = new AgentApplyAdapter(this, this);
        mAdapter.setDataList(mImageData);
        GridLayoutManager manager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRy.setLayoutManager(manager);
        mRy.setAdapter(mAdapter);
    }


    //进入支付界面先判断是对公还是对私支付
    private void getPayType(int token,int userId){
        RetrofitClient.getInstances().getPayType(token,userId).enqueue(new UICallBack<GetPayTypeResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(GetPayTypeResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mPayType = bean.getData();
                            LogUtils.d("付款类型对公或对私=="+mPayType);
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }


    //获取我的余额
    private void getMyBalance(){
        RetrofitClient.getInstances().getMyBalance(mUserId).enqueue(new UICallBack<BalanceResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BalanceResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mBtnPayOrder.setEnabled(true);  //余额请求成功之后 支付按钮才可以点击
                            balanceMoney = bean.getData().getUsableBalance();
                            LogUtils.d("余额=="+balanceMoney);
                            mTvAccountOkMoney.setText("账户可用余额："+ActivityUtils.changeMoneys(bean.getData().getUsableBalance())+"元");
                            if (balanceMoney ==0){  //如果没有余额
                                LogUtils.d("没有余额");
                                isUserBalance = 0;   // 是否使用了余额  不使用余额
                                mCbBalancePay.setChecked(false);  //选中余额设置 为不选中
                                mTvGpfdPay.setText("还需付款："+ActivityUtils.changeMoneys(payMoney-balanceMoney)+"元");
                                mTvPayTotalMoney.setText(mCurrency+""+ActivityUtils.changeMoneys(payMoney-balanceMoney));
                            }else if (balanceMoney>=payMoney){  //如果余额大于等于 要付款的金额
                                LogUtils.d("余额是否是选中的=="+mCbBalancePay.isChecked());
                                //mCbBalancePay.setChecked(true);  //选中余额设置 为选中
                                if (mCbBalancePay.isChecked()){
                                    isBalance = true;  // 余额大于要付款的金额 并且使用了余额
                                }else {
                                    isBalance = false;
                                }
                                mTvGpfdPay.setText("还需付款："+"0元");
                                mTvPayTotalMoney.setText(mCurrency+"0元");
                            }else if (balanceMoney<payMoney){
                                mTvGpfdPay.setText("还需付款："+ActivityUtils.changeMoneys(payMoney-balanceMoney)+"元");
                                mTvPayTotalMoney.setText(mCurrency+ActivityUtils.changeMoneys(payMoney-balanceMoney));
                                aliPayMoney = payMoney-balanceMoney;
                            }
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }

    @OnClick({R.id.layout_pay_mode, R.id.btn_pay_order,R.id.cb_balance_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_pay_mode: //选择支付方式
                if (isBalance){
                    showToast("当前余额可抵扣订单金额，无需使用其他支付方式");
                    return;
                }
                showChooseWindow(); //弹出支付方式选择
                setBackgroundAlpha(0.5f,PayActivity.this);
                break;
            case R.id.cb_balance_pay:  //是否使用余额支付复选框
                if (mCbBalancePay.isChecked()){//如果选择按钮是选中的
                    isUserBalance = 1;   //是否使用了余额   默认使用
                    if (balanceMoney>=payMoney){  //如果余额大于付款金额
                        isBalance = true;  //使用了余额 并且余额足够抵扣付款金额
                        mTvGpfdPay.setText("还需付款："+"0元");
                        mTvPayTotalMoney.setText(mCurrency+"0元");
                        LogUtils.d("使用了的余额="+mUseBalance);
                        mStatus = 0;  //就不需要再调支付宝了
                    }else if (balanceMoney<payMoney){  //如果余额小于付款金额
                        mLayoutPayMode.setEnabled(true);  //那么选择支付方式  就能选择
                        mStatus = 1;  //就需要调支付宝或者截图支付
                        mTvGpfdPay.setText("还需付款："+ActivityUtils.changeMoneys(payMoney-balanceMoney)+"元");
                        aliPayMoney = payMoney-balanceMoney;
                        mTvPayTotalMoney.setText(mCurrency+ActivityUtils.changeMoneys(payMoney-balanceMoney));
                        LogUtils.d("支付宝需要支付的金额=="+aliPayMoney);
                    }
                }else {  //如果是没选中的
                    isUserBalance = 0;   // 是否使用了余额  不使用余额
                    mStatus = 1;  //也需要调支付宝或者截图支付
                    isBalance = false;  //没使用余额
                    mLayoutPayMode.setEnabled(true);  //可以选择支付方式
                    mTvGpfdPay.setText("还需付款："+ActivityUtils.changeMoneys(payMoney)+"元");
                    mTvPayTotalMoney.setText(mCurrency+ActivityUtils.changeMoneys(payMoney));
                }
                break;
            case R.id.btn_pay_order:  //支付
                LogUtils.d("发起支付，支付方式=="+mPayWay+"  ||0支付宝支付,1截图支付,2建行支付,3京东支付");
                mBtnPayOrder.setEnabled(false);
                LogUtils.d("余额支付是否被选中"+mCbBalancePay.isChecked());
                //delayButtonEnabled();  //延时设置按钮可点击
                if (mCbBalancePay.isChecked()){ //如果余额支付是被选中的  使用余额
                    if (balanceMoney>=payMoney){  //如果余额大于付款金额
                        mUseBalance =  payMoney;
                        mStatus = 0;  //就不需要再调支付宝了
                        LogUtils.d("使用了的余额="+mUseBalance);
                    }else if (balanceMoney<payMoney){  //如果余额小于付款金额
                        mUseBalance =  balanceMoney;
                        mStatus = 1;  //就需要调支付宝或者截图支付或者建行
                    }
                    getWhetherPassword(); //判断是否有支付密码  发起余额支付
                    //requestBalancePay(mUserId,mOrderNumber," ",mStatus,mUseBalance,isUserBalance);  //支付密码需要MD5加密
                }else {  //否则余额支付没有选中  不使用余额
                    mStatus = 1;  //也需要调支付宝或者截图支付
                    //不管是否使用余额都要先走余额支付
                    requestBalancePay(mUserId,mOrderNumber," ",mStatus,mUseBalance,isUserBalance);  //支付密码需要MD5加密
                }

                break;
        }
    }


    //判断用户是否有设置过支付密码
    private void getWhetherPassword(){
        WhetherPasswordRequest request = new WhetherPasswordRequest(mUserId);
        RetrofitClient.getInstances().requestPassword(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    delayButtonEnabled();  //延时设置按钮可点击
                }
            }
            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop) {
                    switch (bean.getReturnValue()){
                        case 1:  //有密码 弹出密码输入框
                            // 显示窗口
                            //PopEnterPassword popEnterPassword = new PopEnterPassword(PayActivity.this);
                            popEnterPassword.showAtLocation(PayActivity.this.findViewById(R.id.layout_order_parent),
                                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
                            //delayButtonEnabled();  //延时设置按钮可点击
                            break;
                        case 8:  //没支付密码 ， 弹窗询问是否设置
                            DialogType = "setPassword";  //设置密码
                            DialogHint.showDialogWithTwoButton(mContext,R.string.no_pay_password,PayActivity.this);
                            delayButtonEnabled();  //延时设置按钮可点击
                            break;
                    }
                }
            }
        });
    }

    //发起建行支付请求
    private void requestCCBPay(String orderNumber){
        RetrofitClient.getInstances().getCCBPay(orderNumber).enqueue(new UICallBack<CCBPayResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(CCBPayResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData()!=null){  //如果请求成功并且返回的链接不为空
                                //CommonWebActivity.startWebActivity(mContext,"建行支付",bean.getData());
                                Intent intent = new Intent(PayActivity.this,CommonWebActivity.class);
                                intent.putExtra("url",bean.getData());
                                intent.putExtra("type","common");
                                startActivity(intent);
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

    //发起余额支付请求
    private void requestBalancePay(int agentId, String orderNumber, String password, int status, final int money, int isBalance){
        BalancePayRequest request = new BalancePayRequest(agentId,orderNumber,password,status,money,isBalance);
        RetrofitClient.getInstancesTest().requestBalancePay(request).enqueue(new UICallBack<BalancePayResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BalancePayResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:   //余额支付成功
                            if (mStatus==0){  //如果不需要继续支付
                                showToast(R.string.pay_OK);
                                finish();
                            }else if (mStatus==1){  //如果需要继续支付
                                if (mPayWay==0){ //支付宝
                                    LogUtils.d("支付方式"+mPayWay);
                                    if (mPayType==0){ //对私
                                        requestAliPay();  //老的获取支付链接方法
                                    }else if (mPayType==1){  //对公
                                        getPayUrl(aliPay);  //新的获取支付链接方法
                                    }
                                }else if (mPayWay==2){  //建行
                                    requestCCBPay(mOrderNumber);
                                    LogUtils.d("支付方式"+mPayWay);
                                }else if (mPayWay==1){ //截图
                                    LogUtils.d("支付方式"+mPayWay);
                                    mActivityUtils.hideSoftKeyboard();  //隐藏软键盘
                                    mImagePostData.clear();//把要传给服务器的图片集合先设空，在oss中会重新赋值
                                    LogUtils.d("本地图片集合长度=="+mImageData.size());
                                    if (mImageData.size()==1||mImageData.size()==0){ //没有照片
                                        showToast(R.string.please_plus_image);
                                        mBtnPayOrder.setEnabled(true);
                                        return;
                                    }else {
                                        //上传图片到OSS
                                        mProgressDialog = ProgressDialog.show(PayActivity.this, "", "上传中，请稍候...");
                                        mProgressDialog.setCancelable(false);
                                        mBtnPayOrder.setEnabled(false);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (int i = 1; i < mImageData.size(); i++) {
                                                    File compressFile = new File(mDirPath, "/" + System.currentTimeMillis() + ".jpg");
                                                    CompressImage.compressImage(mImageData.get(i), compressFile);
                                                    LogUtils.e("compressFile.length()=" + compressFile.length());
                                                    postPictureToOSS(compressFile.getPath());
                                                }
                                            }
                                        }).start();
                                    }
                                }else if (mPayWay == 3){  //京东支付
                                    Intent intent = new Intent(PayActivity.this,JDPayActivity.class);
                                    intent.putExtra("orderMoney",bean.getTotal());
                                    intent.putExtra("orderNumber",mOrderNumber);
                                    startActivity(intent);
                                }else if (mPayWay ==4){  //招行支付
                                    DialogType = "CMBPay";  //招行支付
                                    DialogHint.showDialogWithTwoButton(mContext,R.string.cmbPayHint,PayActivity.this);
                                    /*if (ActivityUtils.isCMBAppInstalled(mContext)){
                                        mAppType = 1;  //客户端支付
                                        getCMBPayUrl(mAppType);
                                    }else {
                                        mAppType = 0;  //H5支付
                                        getCMBPayUrl(mAppType);
                                    }*/
                                }
                            }

                            break;
                        default:
                            showToast(bean.getErrMsg());
                            delayShowPassword(); //延迟一秒弹出密码输入框
                            delayButtonEnabled();
                            break;
                    }
                }
            }
        });
    }

    //获取招行链接
    private void getCMBPayUrl(final int appType){
        CMBPayRequest request = new CMBPayRequest(mOrderNumber,userId,appType);
        RetrofitClient.getInstances().getCmbPayUrl(request).enqueue(new UICallBack<CMBPayUrlResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }
            @Override
            public void OnRequestSuccess(CMBPayUrlResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (bean.getData()!=null){
                            if (appType==0){
                                Intent intent = new Intent(PayActivity.this,CommonWebActivity.class);
                                intent.putExtra("url",bean.getData());
                                intent.putExtra("type","HTML");
                                intent.putExtra("title","一网通支付");
                                startActivity(intent);
                            }else if (appType ==1){
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
        });
    }

    //延迟弹出支付密码框
    private void delayShowPassword(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 显示窗口
                popEnterPassword = new PopEnterPassword(PayActivity.this);
                popEnterPassword.showAtLocation(PayActivity.this.findViewById(R.id.layout_order_parent),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
            }
        },500);
    }



    //延时设置按钮可点击
    private void delayButtonEnabled(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isTop){
                    mBtnPayOrder.setEnabled(true);
                }
            }
        },2000);
    }

    //把照片上传到OSS
    private void postPictureToOSS(String path) {
        LogUtils.e("postPictureToOSS+path=" + path);
        final long mObjectId = System.currentTimeMillis();
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, mObjectId + "", path);

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.e("UploadSuccess");
                mImagePostData.add("http://jwbucket.oss-cn-shanghai.aliyuncs.com/"+mObjectId + "");
                LogUtils.e("mObjectId" + mObjectId);
                LogUtils.d("本地图片集合长度=="+mImageData.size());
                LogUtils.d("OSS图片集合长度=="+mImagePostData.size());
                if (mImagePostData.size() == mImageData.size()-1) {
                    mProgressDialog.dismiss();
                    //发起支付请求
                    requestScreenPay(mOrderNumber,userId,mOrderMoney,payType,mImagePostData);
                    LogUtils.d("上传截图支付");
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                mProgressDialog.dismiss();
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    mActivityUtils.showToast("请检查网络配置");
                    mBtnPayOrder.setEnabled(true);
                }
                if (serviceException != null) {
                    // 服务异常
                    LogUtils.e("ErrorCode=" + serviceException.getErrorCode());
                    LogUtils.e("RequestId=" + serviceException.getRequestId());
                    LogUtils.e("HostId=" + serviceException.getHostId());
                    LogUtils.e("RawMessage=" + serviceException.getRawMessage());
                    mActivityUtils.showToast(serviceException.getRawMessage());
                }
            }
        });
    }

    //上传截图支付
    private void requestScreenPay(String orderNo,String userid,int money,int payType,List<String> image){
        ScreenPayRequest request = new ScreenPayRequest(orderNo,userid,money,payType,image);
        RetrofitClient.getInstancesTest().requestScreenPay(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            showToast(R.string.pay_loading);
                            mImagePostData.clear();
                            mActivityUtils.startActivity(MyOrderActivity.class);
                            finish();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //获取支付链接**新接口
    private void getPayUrl(String orderNumber){
        RetrofitClient.getInstances().getPayUrl(orderNumber).enqueue(new UICallBack<GetPayUrlResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(GetPayUrlResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            String payUrl = bean.getUrl();
                            openAliPay(payUrl);
                            showPayLaterStatus();
                            mLayoutPayMode.setEnabled(false);
                            mIvToolbarLeft.setEnabled(false);
                            setBackgroundAlpha(0.5f,PayActivity.this);
                            LogUtils.d("支付时生成的支付订单编号"+payUrl);
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }


    //发起支付宝支付,获取支付链接**  老接口
    private void requestAliPay(){
        LogUtils.d("支付时传值=="+aliPay);
        RetrofitClient.getInstancesTwo().requestAliPay(aliPay).enqueue(new UICallBack<AliPayResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }
            @Override
            public void OnRequestSuccess(AliPayResponse bean) {
                if (isTop){
                    switch (bean.getState()){
                        case 1:
                            String url =bean.getUrl();
                            openAliPay(url); //打开支付宝
                            showPayLaterStatus();
                            mLayoutPayMode.setEnabled(false);
                            mIvToolbarLeft.setEnabled(false);
                            setBackgroundAlpha(0.5f,PayActivity.this);
                            LogUtils.d("支付时生成的支付订单编号"+url);
                            break;
                        default:
                            showToast(bean.getMesage());
                            break;

                    }
                }
            }
        });
    }

    //从支付状态界面提交成功返回
    private void openAliPay(String Url){  //打开支付宝
        LogUtils.d("支付宝URL:"+Url);
        try {
            PackageManager packageManager
                    = this.getApplicationContext().getPackageManager();
            /*Intent intent = packageManager.
                    getLaunchIntentForPackage("com.eg.android.AlipayGphone");
            LogUtils.d("支付宝URL:"+Uri.parse(Url));
            intent.setData(Uri.parse(Url));
            startActivity(intent);*/
            Uri uri = Uri.parse(Url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }catch (Exception e) {
            String url = "https://ds.alipay.com/?from=mobileweb";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//重点是加这个
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }


    //延时显示刷新支付状态的界面
    private void showPayLaterStatus(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showPayStatus();
                popupWindows.setOutsideTouchable(false);
            }
        },1200);
    }


    PopupWindow popupWindow;
    //底部弹出选择支付方式的窗口
    private void showChooseWindow() {
        mImageData.clear();
        initImageAdapter();  //初始化添加图片的适配器
        View view = getLayoutInflater().inflate(R.layout.popwindow_pay_mode, null);
        TextView cancel = (TextView) view.findViewById(R.id.tv_payWay_cancel);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout_select_pay_mode);
        mPayMode = new TextView[DataBeen.size()];
        LinearLayout.LayoutParams params;
        params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMargins(0,2,0,0);
        for (int i=0;i<DataBeen.size();i++){
            mPayMode[i] = new TextView(mContext);
            mPayMode[i].setGravity(Gravity.CENTER);
            mPayMode[i].setText(DataBeen.get(i).getName());
            mPayMode[i].setTextColor(Color.BLACK);
            mPayMode[i].setId(DataBeen.get(i).getId());
            mPayMode[i].setHeight(120);
            mPayMode[i].setTextSize(18);
            mPayMode[i].setBackgroundColor(Color.WHITE);
            mPayMode[i].setLayoutParams(params);
            linearLayout.addView(mPayMode[i]);
            if (DataBeen.get(i).getIsOpen()==1){
                mPayMode[i].setVisibility(View.VISIBLE);
            }else {
                mPayMode[i].setVisibility(View.GONE);
            }
            LogUtils.d("第"+i+"个按钮是"+mPayMode[i].getText().toString());
            mPayMode[i].setTag(i);
            mPayMode[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int k=0; k<DataBeen.size(); k++){
                        if (mPayMode[k].getId()==v.getId() && mPayMode[k].getText().toString().equals("京东支付")){
                            //LogUtils.d("点击了"+mPayMode[k].getText().toString());
                            mLayoutPlusImage.setVisibility(View.GONE);
                            mAdapter.getDataList().clear();
                            mTvOrderPayMode.setText(DataBeen.get(k).getName().toString());
                            mPayWay = 3;
                            setBackgroundAlpha(1.0f,PayActivity.this);
                            popupWindow.dismiss();
                        }else if (mPayMode[k].getId()==v.getId() && mPayMode[k].getText().toString().equals("支付宝支付")){
                            mLayoutPlusImage.setVisibility(View.GONE);
                            mTvOrderPayMode.setText(DataBeen.get(k).getName().toString());
                            mPayWay = 0;
                            setBackgroundAlpha(1.0f,PayActivity.this);
                            popupWindow.dismiss();
                            mAdapter.getDataList().clear();
                        }else if (mPayMode[k].getId()==v.getId() && mPayMode[k].getText().toString().equals("建行支付")){
                            mTvOrderPayMode.setText(DataBeen.get(k).getName().toString());
                            mPayWay = 2;
                            setBackgroundAlpha(1.0f,PayActivity.this);
                            popupWindow.dismiss();
                            mLayoutPlusImage.setVisibility(View.GONE);
                            mAdapter.getDataList().clear();
                        }else if (mPayMode[k].getId()==v.getId() && mPayMode[k].getText().toString().equals("上传截图")){
                            mLayoutPlusImage.setVisibility(View.VISIBLE);
                            mTvOrderPayMode.setText(DataBeen.get(k).getName().toString());
                            mPayWay = 1;
                            setBackgroundAlpha(1.0f,PayActivity.this);
                            popupWindow.dismiss();
                        }else if (mPayMode[k].getId() == v.getId() && mPayMode[k].getText().toString().equals("一网通支付")){
                            mLayoutPlusImage.setVisibility(View.GONE);
                            mTvOrderPayMode.setText(DataBeen.get(k).getName().toString());
                            mPayWay = 4;
                            setBackgroundAlpha(1.0f,PayActivity.this);
                            popupWindow.dismiss();
                        }
                    }
                }
            });
        }
        popupWindow =
                new PopupWindow(view,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
        popupWindow.showAtLocation(mLayoutOrderParent, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {  //监听弹窗消失后的操作
            @Override
            public void onDismiss() {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundAlpha(1.0f,PayActivity.this);
                popupWindow.dismiss();
            }
        });

    }


    PopupWindow popupWindows;
    //底部弹出刷新支付状态的弹窗
    private void showPayStatus(){
        View view = getLayoutInflater().inflate(R.layout.popwindow_pay_status,null);
        TextView hint = (TextView) view.findViewById(R.id.tv_money_hint);  //显示付款金额
        TextView refresh = (TextView) view.findViewById(R.id.tv_refresh_pay);  //刷新
        TextView back = (TextView) view.findViewById(R.id.tv_pay_issue);  //返回订单界面
        popupWindows = new PopupWindow(
                        view,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindows.setBackgroundDrawable(new BitmapDrawable());
        popupWindows.setOutsideTouchable(false);
        popupWindows.setFocusable(false);
        popupWindows.showAtLocation(mLayoutOrderParent, Gravity.BOTTOM, 0, 0);
        hint.setText(mCurrency+""+ActivityUtils.changeMoneys(aliPayMoney));
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayType==0){ //对私
                    requestPayOver();
                }else if (mPayType==1){  //对公
                    getPayStatus(aliPay);  //获取支付状态的新接口
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindows.dismiss();
                setBackgroundAlpha(1.0f,PayActivity.this);
                mLayoutPayMode.setEnabled(true);
                mIvToolbarLeft.setEnabled(true);
            }
        });
    }

    /**
     * 解决popupWindow点击弹窗外部消失的问题
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (popupWindow != null && popupWindow.isShowing()){
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置底部弹窗时背景颜色
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    //获取支付后的状态   老接口
    private void requestPayOver(){
        RetrofitClient.getInstancesTwo().requestPayOver(aliPay).enqueue(new UICallBack<PayOverResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(PayOverResponse bean) {
                if (isTop){
                    switch (bean.getState()){
                        case 1:
                            showToast(R.string.pay_OK);
                            mActivityUtils.startActivity(MyOrderActivity.class);
                            mLayoutPayMode.setEnabled(true);
                            mIvToolbarLeft.setEnabled(true);
                            popupWindows.dismiss();
                            setBackgroundAlpha(1.0f,PayActivity.this);
                            finish();
                            break;
                        default:
                            showToast(bean.getMessage());
                            break;
                    }
                }
            }
        });
    }

    //获取支付后的状态  新接口
    private void getPayStatus(String orderNumber){
        RetrofitClient.getInstances().getPayStatus(orderNumber).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            showToast(R.string.pay_OK);
                            mActivityUtils.startActivity(MyOrderActivity.class);
                            mLayoutPayMode.setEnabled(true);
                            mIvToolbarLeft.setEnabled(true);
                            popupWindows.dismiss();
                            setBackgroundAlpha(1.0f,PayActivity.this);
                            finish();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //选择的图片和添加的图片的监听
    @Override
    public void setOnImageClickListener(View view, int position) {
        if (position == 0) {
            showImageSelector();//调出图片选择器
        } else {
            //大图预览选择的图片
            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
            List<String> photoUrls = new ArrayList<>();
            for (int i = 1; i < mImageData.size(); i++) {
                photoUrls.add(mImageData.get(i));

            }
            ImagePagerActivity.startImagePagerActivity(this, photoUrls, position - 1, imageSize);
        }

    }

    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    //图片选择器
    private void showImageSelector() {
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
                // 是否多选
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                .btnText("确定")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#dcdcdc"))
                // 返回图标ResId
                .backResId(R.mipmap.back_icon)
                .title("选择照片")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#dcdcdc"))
                .allImagesText("更多图片")
                .needCrop(false)
                // 第一个是否显示相机
                .needCamera(false)
                // 最大选择图片数量
                .maxNum(7 - mImageData.size())
                .build();
        ImgSelActivity.startActivity(this, config, IMAGE_MULTI_SELECTOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_MULTI_SELECTOR:
                    if (resultCode == RESULT_OK && data != null) {
                        List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                        for (String path : pathList) {
                            mImageData.add(path);
                            LogUtils.d("path" + path);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    break;

            }
        }

    }

    //删除的点击事件
    @Override
    public void setOnDeleteClickListener(int position) {
        mImageData.remove(position);
        mAdapter.notifyDataSetChanged();
    }

}
