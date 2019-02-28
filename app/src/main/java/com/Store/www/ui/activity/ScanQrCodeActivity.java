package com.Store.www.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.Store.www.MyApplication;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.OutScanCodeRequest;
import com.Store.www.entity.ScanCodeCallBackRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LocationUtil;
import com.Store.www.utils.LogUtils;
import com.yanzhenjie.zbar.camera.CameraPreview;
import com.yanzhenjie.zbar.camera.ScanCallback;

import butterknife.BindView;

/**
 * 扫描二维码界面
 */

public class ScanQrCodeActivity extends BaseToolbarActivity implements DialogHint.OnDialogOneButtonClickListener{
    @BindView(R.id.cp_scan)
    CameraPreview mCpScan;
    @BindView(R.id.iv_scan_cartoon)
    ImageView mIvScanCartoon;


    private String mLocationAddress = " "; //定位地址
    private String DialogType;  //弹窗dialog类型
    private MediaPlayer player;
    private final int RESULT_SCAN = -666;
    private String mScanType;
    private int mWarehouseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_scan_qr_code;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mCpScan.setScanCallback(callback);
        mScanType = getIntent().getStringExtra("type");
        mWarehouseId = getIntent().getIntExtra("id",0);
        LogUtils.d("扫描类型=="+mScanType);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLocationAddress = LocationUtil.getInstance().getLocations(MyApplication.getmContext());
            }
        }).start();
        //LogUtils.d("定位到的位置=="+LocationUtil.getInstance().getLocations(MyApplication.getmContext()));
    }







    //监听扫描结果
    private ScanCallback callback = new ScanCallback() {
        @Override
        public void onScanResult(String content) {
            if (!TextUtils.isEmpty(content)){
                LogUtils.d("扫描结果=="+content.toString());
                //showToast("扫描结果=="+content.toString());
                String code = ActivityUtils.getInsideString(content.toString(),"=","&");
                if (!TextUtils.isEmpty(mScanType)&&mScanType.equals("OUT")){  //出库扫描
                    if (content.toString().contains("sys.kivie.com")){  //检测是否是金薇商品
                        requestOutScanCode(userId,code,mWarehouseId);
                    }else {
                        DialogType = "NOT_HINT";
                        mCpScan.stop();
                        startPayHintMusic();
                        mIvScanCartoon.setVisibility(View.INVISIBLE);
                        DialogHint.showDialogWithOneButton(ScanQrCodeActivity.this,"扫描只支持金薇商品",ScanQrCodeActivity.this);
                    }
                }else if (!TextUtils.isEmpty(mScanType) && mScanType.equals("ENTER")){  //入库扫描
                    LogUtils.d("截取结果=="+code);
                    if (content.contains("sys.kivie.com")){  //检测是否是金薇商品
                        //LogUtils.d("截取的扫描类型==");
                        LogUtils.d("截取的code="+ActivityUtils.getInsideString(content.toString(),"=","&"));
                        requestScanCode(code,mLocationAddress);
                    }else {
                        DialogType = "NOT_HINT";
                        mCpScan.stop();
                        startPayHintMusic();
                        mIvScanCartoon.setVisibility(View.INVISIBLE);
                        DialogHint.showDialogWithOneButton(ScanQrCodeActivity.this,"扫描只支持金薇商品",ScanQrCodeActivity.this);
                    }
                }
            }
        }
    };


    /**
     *播放提示音
     */
    private void startPayHintMusic(){
        player = MediaPlayer.create(mContext,R.raw.beep);
        player.start();
    }

    //出库扫描结束回调接口
    private void requestScanCode(String code,String address){
        ScanCodeCallBackRequest request = new ScanCodeCallBackRequest(agentCode,code,address);
        RetrofitClient.getInstances().requestScanCode(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    mCpScan.start();
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            startPayHintMusic();
                            showToast("扫描成功!");
                            mCpScan.start();
                            break;
                        default:
                            DialogType = "ERR_HINT";
                            startPayHintMusic();
                            DialogHint.showDialogWithOneButton(ScanQrCodeActivity.this,bean.getErrMsg(),ScanQrCodeActivity.this);
                            break;
                    }
                }
            }
        });
    }

    //出库扫描成功回调
    private void requestOutScanCode(String agentCode,String code,int warehouseId){
        OutScanCodeRequest request = new OutScanCodeRequest(agentCode,code,warehouseId);
        RetrofitClient.getInstances().requestOutScanCode(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    mCpScan.start();
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            startPayHintMusic();
                            showToast("扫描成功!");
                            mCpScan.start();
                            break;
                        default:
                            DialogType = "ERR_HINT";
                            startPayHintMusic();
                            DialogHint.showDialogWithOneButton(ScanQrCodeActivity.this,bean.getErrMsg(),ScanQrCodeActivity.this);
                            break;
                    }
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){  //判断是否开启相机权限
            DialogType = "NOT_CAMERA";
            DialogHint.showDialogWithOneButton(this,R.string.please_open_camera,this);
        }else if (!ActivityUtils.isOPenGps(mContext)){  //判断是否开启GPS权限
            DialogType = "NOT_CAMERA";
            DialogHint.showDialogWithOneButton(this,R.string.no_open_gps_hint,this);
        }else {
            mCpScan.start();
            TranslateAnimation mAnimation =
                    new TranslateAnimation(TranslateAnimation.ABSOLUTE,
                            0f, TranslateAnimation.ABSOLUTE,
                            0f,TranslateAnimation.RELATIVE_TO_PARENT,
                            0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
            mAnimation.setDuration(1500);
            mAnimation.setRepeatCount(-1);
            mAnimation.setRepeatMode(Animation.RESTART);
            mAnimation.setInterpolator(new LinearInterpolator());
            mIvScanCartoon.setVisibility(View.VISIBLE);
            mIvScanCartoon.setAnimation(mAnimation);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCpScan.stop();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
        mCpScan.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        if (!TextUtils.isEmpty(DialogType)){
            if (DialogType.equals("NOT_CAMERA")){
                dialog.dismiss();
                finish();
            }else if (DialogType.equals("ERR_HINT")){
                dialog.dismiss();
                mCpScan.start();
            }else if (DialogType.equals("NOT_HINT")){
                dialog.dismiss();
                mCpScan.start();
            }else {
                dialog.dismiss();
                mCpScan.start();
            }
        }
    }

    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId) {
        LogUtils.d("当前dialog类型=="+DialogType.toString());
        if (!TextUtils.isEmpty(DialogType)){
            if (DialogType.equals("NOT_CAMERA")){
                dialog.dismiss();
                finish();
            }else if (DialogType.equals("ERR_HINT")){
                dialog.dismiss();
                mCpScan.start();
                mIvScanCartoon.setVisibility(View.VISIBLE);
            }else if (DialogType.equals("NOT_HINT")){
                dialog.dismiss();
                mCpScan.start();
                mIvScanCartoon.setVisibility(View.VISIBLE);
            }else {
                dialog.dismiss();
            }
        }
    }

}
