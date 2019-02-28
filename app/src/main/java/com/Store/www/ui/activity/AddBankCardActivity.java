package com.Store.www.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.Store.www.entity.AddBankCardRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.FileUtils;
import com.Store.www.utils.LogUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加银行卡界面
 */
public class AddBankCardActivity extends BaseToolbarActivity implements TextWatcher{
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

    private String mName, mCardNumber, mi,mI,mh, mH;  //姓名  卡号  身份证号  手机号
    private String CreateTime;
    private boolean hasGotToken = false;
    public static final int MY_SCAN_REQUEST_CODE = 10;
    public static final int REQUEST_CODE_CAMERA = 20;
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
        return R.layout.activity_add_bank_card;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);  //将次界面添加至管理器中
        initToolbar(this, true, R.string.add_bank_card);  //添加银行卡
        mEtCardName.addTextChangedListener(this);
        mEtCardNumber.addTextChangedListener(this);
        mEtOpenBankName.addTextChangedListener(this);
        mEtOpenBankNumber.addTextChangedListener(this);

    }

    //销毁视图时
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraNativeHelper.release();
        OCR.getInstance(this).release();  //释放内存资源
    }

    public static String hashKey = "JW-BANK-INFO";
    private  int C1 = 520;
    private  int C2 = 1314;

    //char转化为byte：

    public byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00)>> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }




    //byte转换为char：
    public char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF)<< 8) | (b[1] &0xFF));
        return c;
    }



    /**
     *    加密函数
     */
    public  String encrypt(String S, Long Key) {
        S =  S+hashKey;
        ByteBuffer buffer=ByteBuffer.allocate((S).length()*2);
        for (int i = 0; i < S.length(); i++) {
            // 依次对字符串中各字符进行操作
            char c=(char)((S.charAt(i)) ^ (Key >> 8));
            byte[] b=charToByte(c);
            buffer.put(b);
            Key = (Key>>8) * C1 + C2; // 产生下一个密钥
        }
        // System.out.println(buffer.);
        buffer.flip();
        // 构建一个byte数组
        byte[] content = new byte[buffer.limit()];
        // 从ByteBuffer中读取数据到byte数组中
        buffer.get(content);
        // 把byte数组的内容写到标准输出
        return   new String(Base64.encodeToString(content,Base64.NO_WRAP).trim()).trim();

    }



    /**
     *  解密函数
     */


    public  String decrypt(String S, Long Key) {
        StringBuffer Result = new StringBuffer();
        byte[] content;
        try {
            content=Base64.decode(S.getBytes("UTF-8"),Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return "";
        }
        for (int i = 0; i < (content.length)/2-hashKey.length(); i++) // 依次对字符串中各字符进行操作
        {
            byte [] b={content[i*2],content[i*2+1]};
            char c=byteToChar(b);

            Result.append((char)( c ^ (Key >> 8))); // 将密钥移位后与字符异或


            Key = (Key>>8) * C1 + C2; // 产生下一个密钥;
        }
        return Result.toString();
    }

    //添加银行卡
    private void requestAddCard() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        CreateTime = sDateFormat.format(new java.util.Date());
        LogUtils.d("当前时间=="+Long.valueOf(CreateTime));
        mI = encrypt(mi, Long.valueOf(CreateTime));
        mH = encrypt(mh, Long.valueOf(CreateTime));
        LogUtils.d("身份证加密内容="+mI);
        LogUtils.d("手机号加密内容="+mH);
        AddBankCardRequest request = new AddBankCardRequest(mName, mCardNumber,mI, userId,mH,CreateTime);
        RetrofitClient.getInstances().requestAddBankCard(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        showToast("添加成功");
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
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
        mName = mEtCardName.getText().toString();
        mCardNumber = mEtCardNumber.getText().toString();
        mi = mEtOpenBankName.getText().toString();
        mh = mEtOpenBankNumber.getText().toString();
    }

    @OnClick({R.id.btn_add_bank_card,R.id.iv_scan_bank_card,R.id.iv_scan_identity_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_bank_card:  //添加银行卡
                if (TextUtils.isEmpty(mName)||TextUtils.isEmpty(mCardNumber)||TextUtils.isEmpty(mi)||TextUtils.isEmpty(mh)){
                    showToast(R.string.please_perfect_message);
                    return;
                }
                requestAddCard();
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
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
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
    //获取扫描后的银行卡或者身份证信息
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
                    LogUtils.d("身份证内容=="+result.getIdNumber().toString());
                    mEtOpenBankName.setText(result.getIdNumber().toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                LogUtils.d(""+error.getMessage());
            }
        });
    }


}
