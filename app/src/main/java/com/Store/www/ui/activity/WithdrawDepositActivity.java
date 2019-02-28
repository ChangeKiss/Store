package com.Store.www.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.RecognizeService;
import com.Store.www.entity.WithdrawBankCardRequest;
import com.Store.www.ui.costomPassword.PopEnterPassword;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.WhetherPasswordRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.FileUtils;
import com.Store.www.utils.LogUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 直接申请界面
 */
public class WithdrawDepositActivity extends BaseToolbarActivity implements TextWatcher,DialogHint.OnDialogTwoButtonClickListener{
    @BindView(R.id.tv_apply_money)
    TextView mTvApplyMoney;  //申请金额
    @BindView(R.id.et_card_name)
    EditText mEtCardName;  //持卡人姓名
    @BindView(R.id.et_card_number)
    EditText mEtCardNumber;  //卡号
    @BindView(R.id.et_open_bank_name)
    EditText mEtOpenBankName;  //身份证号
    @BindView(R.id.et_open_bank_number)
    EditText mEtOpenBankNumber;  //手机号
    @BindView(R.id.iv_scan_bank_card)
    ImageView mIvScanBankCard;
    @BindView(R.id.layout_coupletNumber)
    LinearLayout mLayoutCoupletNumber;   //输入联行号的布局
    @BindView(R.id.et_coupletNumber)
    EditText mEtCoupletNumber;   //联行号输入框
    @BindView(R.id.btn_affirm_withdraw)
    Button mBtnAffirmWithdraw;   //确认提现按钮

    private String mName, mCardNumber, mi,mI,mh, mH,mCoupletNumber;  //姓名  卡号  身份证号  手机号  银行卡联行号
    private boolean hasGotToken = false;
    public static final int MY_SCAN_REQUEST_CODE = 10;
    public static final int REQUEST_CODE_CAMERA = 20;
    private boolean isPassword = false;
    private NetWorks  networks;
    private int mMoney;
    private final int CARD = 1;
    private String mPassword;  //密码
    private String money;
    private int mBalanceMoney;  //上一个界面传过来的总金额
    private PopEnterPassword popEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化 OCR
        OCR.getInstance(mContext).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError ocrError) {
                ocrError.printStackTrace();
                LogUtils.d("错误码=="+ocrError.getErrorCode());
            }
        },getApplicationContext());
        //初始化本地质量控制
        CameraNativeHelper.init(this, OCR.getInstance(this).getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {

                    }
                });
    }

    @Override
    public int initLayout() {
        return R.layout.activity_withdraw_deposit;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, "直接申请");
        mBalanceMoney = getIntent().getIntExtra("applyMoney",0);
        mTvApplyMoney.setText("申请金额: "+ActivityUtils.changeMoneys(mBalanceMoney));
        mEtCardName.addTextChangedListener(this);
        mEtCardNumber.addTextChangedListener(this);
        mEtOpenBankName.addTextChangedListener(this);
        mEtOpenBankNumber.addTextChangedListener(this);
        mEtCoupletNumber.addTextChangedListener(this);  //添加联行号输入监听
        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("bankCard");  //注册银行卡用的广播
        intentFilter.addAction("withdraw");  //注册密码输入完成时用的广播
        intentFilter.addAction("alterPassword");  //注册找回密码时用的广播
        intentFilter.addAction("close");  //关闭密码输入框的广播
        networks = new NetWorks();
        registerReceiver(networks,intentFilter);
    }




    //注册广播并接收
    class NetWorks extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){  //如果广播返回的信息不为空  并且广播是有效的
                if (intent.getAction().equals("bankCard")){  //如果返回的Action是 银行卡那边来的
                    //getCardList();  //就重新获取银行卡数据
                }else if (intent.getAction().equals("withdraw")){  //如果返回的Action是 密码输入框那边来的
                    mPassword = intent.getStringExtra("password");  //获取密码
                    //发起提现请求
                    //LogUtils.d("密码=="+mPassword);
                    //requestWithdraw(mUserId,mMoney,ActivityUtils.Md5Password(mPassword),mCardId,mCoupletNumber); //将密码加盐  发起提现请求
                    requestWithdrawBankCard(mCoupletNumber,(int) mBalanceMoney,userId,mCardNumber,ActivityUtils.Md5Password(mPassword),mh,mName,mi);
                }else if (intent.getAction().equals("alterPassword")&&intent.getStringExtra("password").equals("alter")){
                    LogUtils.d("找回密码");
                    mActivityUtils.startActivity(AlterPayPasswordActivity.class);
                }else if (intent.getAction().equals("close")){
                    mBtnAffirmWithdraw.setEnabled(true);
                }

            } else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }


/*
    //根据是否有联行号来显示或者隐藏联行号的输入框
    private void isShow(boolean b){
        if (b){
            mLayoutCoupletNumber.setVisibility(View.VISIBLE);
        }else {
            mLayoutCoupletNumber.setVisibility(View.GONE);
        }
    }*/

    //获取从下一个界面传回来的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，银行卡识别
        if (requestCode == MY_SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            RecognizeService.recBankCard(this, FileUtils.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            LogUtils.d("银行卡信息=="+ActivityUtils.stringTrim(result));
                            mEtCardNumber.setText(ActivityUtils.stringTrim(result));
                        }
                    });
        }
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtils.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        mBtnAffirmWithdraw.setEnabled(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    //销毁视图时
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraNativeHelper.release();
        OCR.getInstance(this).release();  //释放内存资源
        popEnterPassword = null;
    }

    //点击事件
    @OnClick({ R.id.btn_affirm_withdraw,R.id.iv_scan_bank_card,R.id.iv_scan_identity_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_affirm_withdraw:  //提现
                mBtnAffirmWithdraw.setEnabled(false);
                if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mi) || TextUtils.isEmpty(mh) || TextUtils.isEmpty(mCardNumber)){
                    mBtnAffirmWithdraw.setEnabled(true);
                    showToast("请填写完整的信息");
                    return;
                }
                if (TextUtils.isEmpty(mCoupletNumber) || mCoupletNumber.length()<12){  //如果联行号为空
                    mBtnAffirmWithdraw.setEnabled(true);
                    showToast(R.string.couplet_number_hint);
                    return;
                }
                getWhetherPassword();
                break;
            case R.id.iv_scan_bank_card:  //扫描银行卡
                mEtCardNumber.setText("");
                scanBankCard();
                break;
            case R.id.iv_scan_identity_card:  //扫描身份证
                mEtOpenBankName.setText("");
                scanIdetity();
                break;
        }
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "启动扫描失败,请手动输入!", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    //跳转扫描银行卡
    private void scanBankCard(){
        if (!checkTokenStatus()) {
            return;
        }
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_BANK_CARD);
        startActivityForResult(intent, MY_SCAN_REQUEST_CODE);
    }

    //跳转扫描身份证
    private void  scanIdetity(){
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
       /* Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);*/
    }

    //扫描身份证
    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    //LogUtils.d("身份证内容=="+result.getIdNumber().toString());
                    mEtOpenBankName.setText(result.getIdNumber().toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                LogUtils.d(""+error.getMessage());
            }
        });
    }

    //判断用户是否有设置过支付密码
    private void getWhetherPassword(){
        WhetherPasswordRequest request = new WhetherPasswordRequest(mUserId);
        RetrofitClient.getInstances().requestPassword(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:  //有密码 弹出密码输入框
                        popEnterPassword = new PopEnterPassword(WithdrawDepositActivity.this);
                        // 显示窗口
                        popEnterPassword.showAtLocation(WithdrawDepositActivity.this.findViewById(R.id.layoutContent),
                                Gravity.BOTTOM | Gravity.BOTTOM, 0, 0); // 设置layout在PopupWindow中显示的位置
                        break;
                    case 8:  //没支付密码 ， 弹窗询问是否设置
                        DialogHint.showDialogWithTwoButton(mContext,R.string.no_pay_password,WithdrawDepositActivity.this);
                        break;
                }
            }
        });
    }


    //发起提现到银行卡请求
    private void requestWithdrawBankCard(String coupletNumber,int money,String agentId,String cardNumber,
                                         String password,String phone,String name,String idNumber){
        WithdrawBankCardRequest request = new WithdrawBankCardRequest(coupletNumber,money,agentId,cardNumber,password,phone,name,idNumber);
        RetrofitClient.getInstances().requestWithdrawBackCard(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
                mBtnAffirmWithdraw.setEnabled(true);
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.agent_loding);
                        finish();
                        Intent intent = new Intent();
                        intent.setAction("applyOver");
                        sendBroadcast(intent);  //发送一个提现完成的广播 关闭前面的界面
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        mBtnAffirmWithdraw.setEnabled(true);
                        delayShowPassword();
                        break;
                }
            }
        });
    }


    /*//发起提现请求
    private void requestWithdraw(int agentId,int money,String password,int cardId,String coupletNumber){
        WithdrawDepositRequest request = new WithdrawDepositRequest(agentId,money,password,cardId,coupletNumber);
        RetrofitClient.getInstances().requestWithdrawDeposit(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        finish();
                        showToast(R.string.agent_loding);
                        break;
                    default:  //
                        showToast(bean.getErrMsg());
                        mBtnAffirmWithdraw.setEnabled(true);
                        delayShowPassword();
                        break;
                }
            }
        });
    }*/

    //延迟弹出支付密码框
    private void delayShowPassword(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PopEnterPassword popEnterPassword = new PopEnterPassword(WithdrawDepositActivity.this);
                // 显示窗口
                popEnterPassword.showAtLocation(WithdrawDepositActivity.this.findViewById(R.id.layoutContent),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
            }
        },200);
    }


    //弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        dialog.dismiss();
        mActivityUtils.startActivity(SettingPayPasswordActivity.class,"type","setting");
    }

    //弹窗取消的点击事件
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        mBtnAffirmWithdraw.setEnabled(true);
        dialog.dismiss();
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
        mCoupletNumber = mEtCoupletNumber.getText().toString();
        mName = mEtCardName.getText().toString();
        mCardNumber = mEtCardNumber.getText().toString();
        mi = mEtOpenBankName.getText().toString();
        mh = mEtOpenBankNumber.getText().toString();
    }
}
