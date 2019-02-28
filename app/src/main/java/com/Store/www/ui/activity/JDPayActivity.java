package com.Store.www.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BankCardListResponse;
import com.Store.www.entity.JDPayRequest;
import com.Store.www.entity.JDPayResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 京东支付界面
 */
public class JDPayActivity extends BaseToolbarActivity implements TextWatcher{
    @BindView(R.id.tv_hint_pay_money)
    TextView mTvHintPayMoney;  //付款金额提示
    @BindView(R.id.layout_select_card)
    LinearLayout mLayoutSelectCard;  //选择银行卡布局
    @BindView(R.id.layout_default_card)
    LinearLayout mLayoutDefaultCard;   //默认银行卡信息的布局
    @BindView(R.id.tv_default_card_name)
    TextView mTvDefaultCartName;  //开户银行
    @BindView(R.id.tv_default_card_number)
    TextView mTvDefaultCardNumber;  //卡号
    @BindView(R.id.tv_no_card_hint)
    TextView mTvNoCardHint;  //没有银行卡信息时的提示
    @BindView(R.id.layout_input_data)
    LinearLayout mLayoutInputData;   //输入银行卡信息布局
    @BindView(R.id.layout_credit_card_data)
    LinearLayout mLayoutCreditCardData;  //信用卡信息
    @BindView(R.id.et_security_code)
    EditText mEtSecurityCode;  //信用卡安全码
    @BindView(R.id.et_period_of_validity)
    EditText mEtPeriodOfValidity;  //信用卡有效期
    @BindView(R.id.btn_next_jd)
    Button mBtnNextJd;  //下一步按钮

    private final int CARD = 1;
    private int selectId;
    private String cardNumber,mCardNumber,mOrderNumber,cardName,mCardName;  //  银行卡号  手机号  身份证号码  订单号
    private String JDNumber;  //京东订单编号
    private String mType,mExp;
    private int JdType = 0;  //京东支付银行卡类型
    private String JdCode;  //京东支付 卡的安全码
    private int mOrderMoney,mCardId,cardId,cardType;  //付款金额  订单编号 银行卡ID  信用卡有效期
    private jDNextWork jDNextWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_jdpay;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.pay_jd);
        mOrderMoney = getIntent().getIntExtra("orderMoney",0);
        mOrderNumber = getIntent().getStringExtra("orderNumber");
        mTvHintPayMoney.setText(ActivityUtils.changeMoneys(mOrderMoney)+"元");
        mEtSecurityCode.addTextChangedListener(this);
        mEtPeriodOfValidity.addTextChangedListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction("bankCard");
        filter.addAction("payOver");
        jDNextWork = new jDNextWork();
        registerReceiver(jDNextWork,filter);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入完成后文本框的监听
    @Override
    public void afterTextChanged(Editable s) {
        JdCode = mEtSecurityCode.getText().toString();
        mExp = mEtPeriodOfValidity.getText().toString();
    }

    //接收广播并处理相关事务
    class jDNextWork extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo!=null&&networkInfo.isAvailable()){
                if (intent.getAction().equals("bankCard")){
                    LogUtils.d("接收到了删除银行卡的广播");
                    getCardList();
                }else if (intent.getAction().equals("payOver")){
                    finish();
                }
            }
        }
    }

    //获取下一个界面返回的值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CARD:
                if (resultCode==2){
                    selectId = data.getIntExtra("cardId",0);
                    cardName = data.getStringExtra("name");
                    cardNumber = data.getStringExtra("cardNumber");
                    cardType = data.getIntExtra("cardType",0);
                    mTvDefaultCartName.setText(cardName);
                    LogUtils.d("设置银行卡信息01");
                    mTvDefaultCardNumber.setText("尾号"+cardNumber+"");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBtnNextJd.setEnabled(true);  //开启点击事件
        if (selectId!=0){
            LogUtils.d("请求数据02"+selectId);
            mCardId = selectId;  //选中的银行卡ID
            mCardNumber = cardNumber;
            mCardName = cardName;
            JdType = cardType;
            if (JdType ==1){  //信用卡
                mTvDefaultCardNumber.setText("尾号 "+mCardNumber+" 信用卡");
                mLayoutCreditCardData.setVisibility(View.VISIBLE);
            }else if (JdType ==0){  //储蓄卡
                mTvDefaultCardNumber.setText("尾号 "+mCardNumber+" 储蓄卡");
                mLayoutCreditCardData.setVisibility(View.GONE);
            }
            LogUtils.d("设置银行卡信息02");
            mTvDefaultCartName.setText(mCardName);
        }else if (selectId==0){
            LogUtils.d("请求数据03"+selectId);
            getCardList();
        }
    }

    //获取银行卡
    private void getCardList(){
        RetrofitClient.getInstances().getBankCard(mUserId).enqueue(new UICallBack<BankCardListResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BankCardListResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        LogUtils.d("设置银行卡信息03");
                        mLayoutDefaultCard.setVisibility(View.VISIBLE);
                        mTvNoCardHint.setVisibility(View.GONE);
                        cardName = bean.getData().get(0).getOpenBank();  //开户银行
                        mCardId = bean.getData().get(0).getCardId();  //银行卡ID
                        cardNumber = bean.getData().get(0).getCardNumber();  //银行卡号
                        JdType = bean.getData().get(0).getCardType();  //银行卡类型
                        LogUtils.d("开户行=="+bean.getData().get(0).getOpenBank());
                        mTvDefaultCartName.setText(bean.getData().get(0).getOpenBank());
                        if (JdType ==1){  //信用卡
                            mTvDefaultCardNumber.setText("尾号 "+bean.getData().get(0).getCardNumber()+" 信用卡");
                            mLayoutCreditCardData.setVisibility(View.VISIBLE);
                        }else if (JdType ==0){  //储蓄卡
                            mTvDefaultCardNumber.setText("尾号 "+bean.getData().get(0).getCardNumber()+" 储蓄卡");
                            mLayoutCreditCardData.setVisibility(View.GONE);
                        }
                        selectId = bean.getData().get(0).getCardId();
                        break;
                    case 8:  //没有银行卡信息的时候
                        selectId = 0;
                        mLayoutDefaultCard.setVisibility(View.GONE);
                        mTvNoCardHint.setVisibility(View.VISIBLE);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //创建京东支付
    private void createJdPay(int cardId,String code,String orderNumber,String exp){
        JDPayRequest request = new JDPayRequest(cardId,code,orderNumber,exp);
        RetrofitClient.getInstances().requestJDPay(request).enqueue(new UICallBack<JDPayResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
                mBtnNextJd.setEnabled(true);  //开启点击事件
            }

            @Override
            public void OnRequestSuccess(JDPayResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:  //创建成功
                        JDNumber = bean.getJdNo();  //获取京东支付订单编号
                        LogUtils.d("京东订单编号=="+JDNumber);
                        Intent intent = new Intent(JDPayActivity.this,AffirmJDPayActivity.class);
                        intent.putExtra("jdNumber",JDNumber);
                        intent.putExtra("orderMoney",mOrderMoney);
                        startActivity(intent);
                        break;
                    default:
                        mBtnNextJd.setEnabled(true);  //开启点击事件
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //点击事件
    @OnClick({R.id.layout_select_card, R.id.btn_next_jd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_select_card: //选择银行卡
                if (selectId!=0){  //如果已经有银行卡  就去选择新的卡
                    Intent intent = new Intent(this,SelectBankCardActivity.class);
                    intent.putExtra("selectId",selectId);
                    intent.putExtra("type","select");
                    startActivityForResult(intent,CARD);
                }else if (selectId==0){  //如果没有银行卡
                    mActivityUtils.startActivity(AddBankCardActivity.class);
                }
                break;
            case R.id.btn_next_jd:  //下一步
                mBtnNextJd.setEnabled(false);  //关闭点击事件
                //创建京东支付
                if (selectId==0){
                    showToast(R.string.please_select_bank_card);
                    mBtnNextJd.setEnabled(true);  //开启点击事件
                    return;
                }

                if (JdType ==1){  //如果支付类型是信用卡
                    if (TextUtils.isEmpty(JdCode)||TextUtils.isEmpty(mExp)){
                        showToast(R.string.intact_message);
                        mBtnNextJd.setEnabled(true);  //开启点击事件
                        return;
                    }
                }

                LogUtils.d("银行卡类型=="+JdType);
                LogUtils.d("信用卡有效期=="+mExp);
                createJdPay(mCardId,JdCode,mOrderNumber,mExp);
                break;
        }
    }
}
