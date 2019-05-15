package com.Store.www.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.SwitchButton.SwitchButton;
import com.Store.www.entity.CommodityHintResponse;
import com.Store.www.entity.LocationResponse;
import com.Store.www.entity.MyPayContentResponse;
import com.Store.www.entity.ShoppingCartResponse;
import com.Store.www.entity.SubmitOrderRequest;
import com.Store.www.entity.SubmitOrderResponse;
import com.Store.www.entity.SuperiorsCloudKcRequest;
import com.Store.www.entity.SuperiorsCloudKcResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.SubmitOrderAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import android.net.ConnectivityManager;
import android.content.BroadcastReceiver;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提交订单界面
 */
public class SubmitOrderActivity extends BaseToolbarActivity implements TextWatcher{
    @BindView(R.id.tv_order_people)
    TextView mTvOrderPeople; //联系人
    @BindView(R.id.tv_order_phone_number)
    TextView mTvOrderPhoneNumber;   //电话
    @BindView(R.id.tv_order_adders)
    TextView mTvOrderAdders;    //地址
    @BindView(R.id.tv_no_address_hint)
    TextView mTvNoAddressHint;  //没有地址的提示
    @BindView(R.id.et_submit_order)   //备注
    EditText mEtSubmitOrder;
    @BindView(R.id.Rl_order_address)  //地址布局
    RelativeLayout mRlOrderAddress;
    @BindView(R.id.rl_submit_order) //商品详情列表
    RecyclerView mRy;
    @BindView(R.id.tv_scroll_view)  //自定义的跑马灯效果的Text文本框
    TextView mTvScrollView;
    @BindView(R.id.r_btn_normal)
    RadioButton mRBtnNormal;  //正常发货按钮
    @BindView(R.id.r_btn_cloud)
    RadioButton mRBtnCloud;  //存入云库按钮
    @BindView(R.id.layout_cloud_number_hint)
    LinearLayout mLayoutCloudNumberHint; //上级云库存提示
    @BindView(R.id.vw_cloud_line)
    View mVwCloudLine;  //分割线
    @BindView(R.id.tv_cloud_number)
    TextView mTvCloudNumber;  //   云库存数量
    @BindView(R.id.switch_button)
    SwitchButton mSwitchButton;   //开关按钮
    @BindView(R.id.layout_cloud)
    LinearLayout mLayoutCloud; //存入云库界面
    @BindView(R.id.vw_cloud)
    View mVwCloud;  //存入云库下面的分隔布局

    SubmitOrderAdapter mAdapter;
    private String name, phone, address, city, area, province,mRemark;
    private String mSelectName,mSelectPhone,mSelectAddress,mSelectCity,mSelectProvince,mSelectArea;
    private int totalPrice, typeId = 1, natureId = 1, orderType = 1;
    private int mSelectAddressId = 0;
    private String repositoryType = "1", receiver = "JW";
    List<Integer> cartId;
    int[] list;  //获取商品提示传的商品ID
    private String commodityHint;
    static final int ORDER_SUBMIT_ADDRESS = 2;
    public String type;
    private Network  network;
    private String mCurrency,mPayContent; //货币符号,上级的收款资料
    WindowManager.LayoutParams params;  //用来动态设置AlertDialog的宽
    private int isPutCloud = 1;  //是否存入云库默认0  0正常发货  1存入云库
    private List<SuperiorsCloudKcRequest.ProductsBean> Bean = new  ArrayList();
    SuperiorsCloudKcRequest.ProductsBean productsBean = new SuperiorsCloudKcRequest.ProductsBean();
    private AlertDialog mDialog;
    private int mIsShow;  //是否显示存入云库界面  1显示 0不显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_submit_order;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mCurrency = getIntent().getStringExtra("currency");
        initToolbar(this, true, R.string.submit_order);
        mAdapter = new SubmitOrderAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(mAdapter);
        mEtSubmitOrder.addTextChangedListener(this);
        initData();
        getLocation();
        mIsShow = getIntent().getIntExtra("isShow",0);  //是否显示存入云库布局
        /*if (mIsShow !=1){
            mLayoutCloud.setVisibility(View.VISIBLE);
            mVwCloud.setVisibility(View.VISIBLE);
        }else {
            mLayoutCloud.setVisibility(View.GONE);

        }*/
        IntentFilter intentFilter = new IntentFilter(); // 注册广播接收器
        intentFilter.addAction("lock");
        network = new Network();
        registerReceiver(network,intentFilter);
        mSwitchButton.setToggleOn(false);
        mSwitchButton.setOnToggleChanged(new SwitchButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on){
                    isPutCloud = 1;
                }else {
                    isPutCloud = 0;
                }
            }
        });

    }


    //注册广播并接收
    class Network extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                mSelectAddress = intent.getStringExtra("address");
                getLocation();
            } else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //获取上级的收款信息
    private void getSuperiorPayContent(int userId){
        RetrofitClient.getInstancesTest().getSuperiorPayContent(userId).enqueue(new UICallBack<MyPayContentResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(MyPayContentResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            mPayContent = " ";
                            mPayContent = bean.getData();
                            //LogUtils.d("1公告内容="+mPayContent);
                            mTvScrollView.setVisibility(View.VISIBLE);
                            TextContent();
                        }
                        break;
                    case 2:
                        if (isTop){
                            mPayContent = " ";
                            mPayContent = bean.getErrMsg();
                            //LogUtils.d("2公告内容="+mPayContent);
                            mTvScrollView.setVisibility(View.VISIBLE);
                            TextContent();
                        }
                        break;
                }
            }
        });
    }

    //设置顶部跑马灯效果的文本
    private void TextContent(){
        mTvScrollView.setText(mPayContent);
        /*mTvScrollView.setSaveEnabled(true);
        mTvScrollView.startScroll();*/
    }

    //接收购物车传过来的数据
    private void initData() {
        List<ShoppingCartResponse.DataBean> dataBeen = (List<ShoppingCartResponse.DataBean>) getIntent().getSerializableExtra("DataBean");
        totalPrice = getIntent().getIntExtra("totalMoney", 0);
        LogUtils.d("总价=="+totalPrice);
        mAdapter.setDataList(dataBeen);
        mAdapter.setmCurrency(mCurrency);
        cartId = new ArrayList<>();
        int [] mList = new int[dataBeen.size()];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dataBeen.size(); i++) {
            cartId.add(dataBeen.get(i).getId());
            productsBean.setCount(dataBeen.get(i).getCount());
            productsBean.setProductId(dataBeen.get(i).getProductId());
            Bean.add(productsBean);
            mList[i] = dataBeen.get(i).getProductId();
            builder.append(dataBeen.get(i).getProductId());
            if (i<dataBeen.size()-1){
                builder.append(",");
            }
        }
        LogUtils.d("拼接结果=="+"["+builder.toString()+"]");
        list = mList;
        getCommodityHint();
    }

    /**
     * 获取上级云仓库产品库存
     * @param UserId
     * @param productsBeen
     */
    private void requestGetSuperiorsCloudKc(String UserId,List<SuperiorsCloudKcRequest.ProductsBean> productsBeen){
        SuperiorsCloudKcRequest request = new SuperiorsCloudKcRequest(UserId,productsBeen);
        RetrofitClient.getInstances().requestCloudRepository(request).enqueue(new UICallBack<SuperiorsCloudKcResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(SuperiorsCloudKcResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mVwCloudLine.setVisibility(View.VISIBLE);
                            mLayoutCloudNumberHint.setVisibility(View.VISIBLE);
                            mTvCloudNumber.setText(bean.getData()+"件");
                            break;
                        default:
                            mVwCloudLine.setVisibility(View.GONE);
                            mLayoutCloudNumberHint.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

    }

    //获取商品提示
    private void getCommodityHint(){
        RetrofitClient.getInstances().getCommodityHint(list).enqueue(new UICallBack<CommodityHintResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(CommodityHintResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            for (int i=0;i<bean.getData().size();i++){
                                commodityHint += i+1+":"+bean.getData().get(i).getRemind()+"\n";
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

    //地址管理界面返回的选中的地址信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ORDER_SUBMIT_ADDRESS:
                if (resultCode==2){
                    mSelectName = data.getStringExtra("name");
                    mSelectPhone = data.getStringExtra("phoneNumber");
                    mSelectAddress = data.getStringExtra("address");
                    mSelectCity = data.getStringExtra("city");
                    mSelectArea = data.getStringExtra("area");
                    mSelectProvince = data.getStringExtra("province");
                    mSelectAddressId = data.getIntExtra("addressId",0);
                    LogUtils.d("默认地址=="+mSelectAddressId);
                    LogUtils.d("选择地址后城市为"+mSelectCity);
                }
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        LogUtils.d("OnResume");
        getSuperiorPayContent(mUserId);
        requestGetSuperiorsCloudKc(userId,Bean);
        LogUtils.d("mSelectAddressId=="+mSelectAddressId);
        if (mSelectAddressId!=0){
            mTvOrderPeople.setVisibility(View.VISIBLE);
            mTvOrderPhoneNumber.setVisibility(View.VISIBLE);
            name = mSelectName;
            phone = mSelectPhone;
            address = mSelectAddress;
            city = mSelectCity;
            area = mSelectArea;
            province = mSelectProvince;
            mTvOrderPeople.setText("收货人：" + name);
            mTvOrderPhoneNumber.setText(phone);
            mTvNoAddressHint.setVisibility(View.INVISIBLE);
            mTvOrderAdders.setVisibility(View.VISIBLE);
            mTvOrderAdders.setText("收货地址：" +mSelectProvince+mSelectCity+mSelectArea+address);
        }else if (mSelectAddressId ==0){
            getLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    //获取地址信息
    private void getLocation() {
        RetrofitClient.getInstances().getLocation(mUserId).enqueue(new UICallBack<LocationResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
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
                                    /*LogUtils.d("收件人" + name);
                                    LogUtils.d("电话" + phone);
                                    LogUtils.d("地址" + address);*/
                                    mTvOrderPeople.setVisibility(View.VISIBLE);
                                    mTvOrderPhoneNumber.setVisibility(View.VISIBLE);
                                    mTvOrderPeople.setText("收货人：" + name);
                                    mTvOrderPhoneNumber.setText(phone);
                                    mTvNoAddressHint.setVisibility(View.INVISIBLE);
                                    mTvOrderAdders.setVisibility(View.VISIBLE);
                                    mTvOrderAdders.setText("收货地址：" + province+city+area+address);
                                    return;
                                }else if (bean.getData().size()<1){
                                    mTvOrderAdders.setVisibility(View.INVISIBLE);
                                    mTvNoAddressHint.setText(R.string.no_address);
                                    mTvOrderPeople.setVisibility(View.INVISIBLE);
                                    mTvOrderPhoneNumber.setVisibility(View.INVISIBLE);
                                }else {
                                    mTvOrderAdders.setVisibility(View.INVISIBLE);
                                    mTvNoAddressHint.setText(R.string.no_default);
                                    mTvOrderPeople.setVisibility(View.INVISIBLE);
                                    mTvOrderPhoneNumber.setVisibility(View.INVISIBLE);
                                }
                            }
                            break;
                    }
                }
            }
        });
    }


    //向服务器提交订单
    private void requestSubmitOrder() {
        SubmitOrderRequest request = new SubmitOrderRequest(userId, totalPrice, typeId, natureId, orderType, city,
                area, address, phone, province, name, receiver, repositoryType,mRemark, isPutCloud,cartId);
        RetrofitClient.getInstancesTest().requestSubmitOrder(request).enqueue(new UICallBack<SubmitOrderResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(SubmitOrderResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        if (bean.getData().getStatus() == 3) {//订单状态 3)
                            LogUtils.d("跳转之前收件人==" + bean.getData().getRecieveName());
                            LogUtils.d("跳转之前电话==" + bean.getData().getMobilephone());
                            Intent intent = new Intent(SubmitOrderActivity.this, PayActivity.class);
                            intent.putExtra("orderName", bean.getData().getRecieveName());  //收件人
                            intent.putExtra("orderPhone", bean.getData().getMobilephone());  //电话
                            intent.putExtra("orderAddress", bean.getData().getAddress());       //地址
                            intent.putExtra("oderNumber", bean.getData().getOrderNumber());      //订单编号
                            intent.putExtra("oderType", bean.getData().getTypeName());       //订单类型
                            intent.putExtra("orderCount", bean.getData().getCount());        //订单数量
                            intent.putExtra("orderMoney", bean.getData().getTotal());        //订单金额
                            intent.putExtra("orderTime",bean.getData().getCreateTime());   //下单时间
                            intent.putExtra("currency",mCurrency); //货币
                            startActivity(intent);
                        }
                        if (mDialog!=null&&mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                        finish();
                        break;
                    default:
                        Toast toast = Toast.makeText(SubmitOrderActivity.this,bean.getErrMsg(),Toast.LENGTH_LONG);
                        ActivityUtils.showMyToast(toast,10*1000);
                        break;

                }
            }
        });
    }

    //点击事件
    @OnClick({R.id.Rl_order_address, R.id.layout_submit_order,R.id.tv_scroll_view,R.id.r_btn_normal,R.id.r_btn_cloud})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Rl_order_address: //选择地址
                Intent intent = new Intent(this,LocationManagerActivity.class);
                intent.putExtra("selectAddress", "submit");
                startActivityForResult(intent, ORDER_SUBMIT_ADDRESS);
                break;
            case R.id.layout_submit_order: //提交订单
                if (address ==null){
                    showToast(R.string.no_address);
                    return;
                }
                LogUtils.d("商品提示=="+commodityHint);
                if (!TextUtils.isEmpty(commodityHint)){
                    CommodityHintDialog();
                }else {
                    requestSubmitOrder();
                }
                break;
            case R.id.tv_scroll_view:
                AlterPay();
                break;
            case R.id.r_btn_normal: //正常发货
                isPutCloud = 0;
                //LogUtils.d("isPutCloud=="+isPutCloud);
                break;
            case  R.id.r_btn_cloud: //存入云库
                isPutCloud = 1;
                //LogUtils.d("isPutCloud=="+isPutCloud);
                break;

        }
    }

    //弹出商品提示弹窗
    private void CommodityHintDialog(){
        View view = View.inflate(mContext,R.layout.dialog_commodity_hint,null);
        TextView mTvContent = (TextView) view.findViewById(R.id.tv_hint_content);
        Button mBtnCancel = (Button) view.findViewById(R.id.tv_hint_cancel_come);
        Button mBtnOk = (Button) view.findViewById(R.id.tv_hint_affirm_come);
        mDialog = new AlertDialog.Builder(mContext).setView(view).create();
        mDialog.setCancelable(false);
        mDialog.show();
        mTvContent.setText(commodityHint.trim());
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());  //设置TextView可以滚动
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSubmitOrder();
            }
        });
    }

    //弹出收款公告
    private void AlterPay(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(mContext,R.layout.pay_alter_dialog,null);
        TextView buttonOk = (TextView) dialogView.findViewById(R.id.tv_pay_ok);
        TextView content = (TextView) dialogView.findViewById(R.id.tv_pay_content);
        //设置对话框布局
        dialog.setView(dialogView);
        dialog.show();
        params = dialog.getWindow().getAttributes();
        params.width = UserPrefs.getInstance().getWidth()/4*3;
        dialog.getWindow().setAttributes(params);
        if (mPayContent!=null){
            content.setText(mPayContent);
        }else {
            content.setText("");
        }

        buttonOk.setOnClickListener(new View.OnClickListener() { //关闭的点击事件
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入文本框的监听
    @Override
    public void afterTextChanged(Editable s) {
        mRemark = mEtSubmitOrder.getText().toString();
        LogUtils.d("备注:"+mRemark);
    }
}
