package com.Store.www.ui.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.ClearCircleView;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.ScanCodeCallBackRequest;
import com.Store.www.entity.ScanRecordDetailResponse;
import com.Store.www.entity.ScanRecordTimeResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.ScanEntranceTimeAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 扫一扫记录以及进入界面
 */
public class ScanEntranceActivity extends BaseToolbarActivity implements TextWatcher, BaseToolbarActivity.OnToolBarRightClickListener {
    @BindView(R.id.erv_scan)
    ExpandableListView mErvScan;  //扫描列表
    @BindView(R.id.iv_toolbar_right)
    TextView mTvToolbarRight;  //右上角按钮
    ScanEntranceTimeAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;


    private View mCommonHeader;  //头布局
    ClearCircleView mIvScan;  //扫一扫布局
    EditText mEtScanInput;  //唯一码输入框
    TextView mTvScanInputSubmit;  //提交按钮
    private String mInputCode;  //用户输入的唯一码

    private final int SCAN_RESULT = 666;
    private LocationManager locationManager;
    private Location location;
    private String mLocationAddress = " "; //定位地址

    private static final Handler IntentHandler = new Handler();  //延时跳转handler
    List<ScanRecordTimeResponse.DataBean> dataBeen = new ArrayList<>();
    List<ScanRecordDetailResponse.DataBean> mBean = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_scan_entrance;
    }

    @Override
    public void initView() {
        initToolbar(this, true, R.string.scan,this);
        ActivityCollector.addActivity(this);
        mTvToolbarRight.setVisibility(View.VISIBLE);  //下级扫码列表
        mTvToolbarRight.setText("下级扫码列表");
        mTvToolbarRight.setOnClickListener(this);
        initHead();
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            getLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LogUtils.d("StatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                getLocation(location);
            } catch (SecurityException e) {

            }

        }

        @Override
        public void onProviderDisabled(String provider) {
            LogUtils.d("ProviderDisabled");
        }
    };

    //获取当前位置
    private void getLocation(Location location) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses = null;
        LogUtils.d("获取位置");
        if (location != null) {
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.get(0).getAdminArea() != null && addresses.get(0).getLocality() != null) {
                    mLocationAddress += addresses.get(0).getAdminArea();
                    mLocationAddress += " " + addresses.get(0).getLocality();
                    mLocationAddress += " " + addresses.get(0).getThoroughfare();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            LogUtils.d("定位到的位置==" + mLocationAddress);
            LogUtils.d("定位到的经度==" + location.getLongitude());
            LogUtils.d("定位到的纬度==" + location.getLatitude());
        } else {
            mLocationAddress = " ";
        }
    }

    //初始化头布局
    private void initHead() {
        mCommonHeader = LayoutInflater.from(mContext).inflate(R.layout.scan_entrance_head, null);
        mIvScan = (ClearCircleView) mCommonHeader.findViewById(R.id.iv_scan);
        mEtScanInput = (EditText) mCommonHeader.findViewById(R.id.et_scan_input);
        mTvScanInputSubmit = (TextView) mCommonHeader.findViewById(R.id.tv_scan_input_submit);
        mEtScanInput.addTextChangedListener(this);
        mTvScanInputSubmit.setOnClickListener(this);
        mIvScan.setOnClickListener(this);
        mErvScan.addHeaderView(mCommonHeader);
        mErvScan.setGroupIndicator(null);
        //监听一级列表点击事件 获取二级列表数据并展开
        mErvScan.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                String data = ActivityUtils.YMDTime(dataBeen.get(groupPosition).getDate());
                LogUtils.d("当前时间==" + data);
                //获取扫描内容明细
                RetrofitClient.getInstances().getScanContentDetail(agentCode, data).enqueue(new UICallBack<ScanRecordDetailResponse>() {
                    @Override
                    public void OnRequestFail(String msg) {
                        if (isTop) checkNet();
                    }

                    @Override
                    public void OnRequestSuccess(ScanRecordDetailResponse bean) {
                        if (isTop) {
                            switch (bean.getReturnValue()) {
                                case 1:
                                    LogUtils.d("数据长度==" + bean.getData().size());
                                    mAdapter.updateSeconds(dataBeen, bean.getData(), groupPosition);
                                    break;
                                default:

                                    break;
                            }
                        }
                    }
                });
                return false;
            }
        });
        //控制点击展开一级列表关闭其他展开的的一级列表
        mErvScan.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = mErvScan.getExpandableListAdapter().getGroupCount();
                for (int i = 0; i < count; i++) {
                    if (i != groupPosition) mErvScan.collapseGroup(i);
                }
            }
        });
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
        getScanRecordTime(agentCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_RESULT) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }

            }

        }
    }

    //扫描结束回调接口
    private void requestScanCode(String code, String address) {
        ScanCodeCallBackRequest request = new ScanCodeCallBackRequest(agentCode, code, address);
        RetrofitClient.getInstances().requestScanCode(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) {
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop) {
                    switch (bean.getReturnValue()) {
                        case 1:
                            showToast("提交成功!");
                            onResume();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            mEtScanInput.setText("");
                            break;
                    }
                }
            }
        });
    }


    //获取扫描记录时间
    private void getScanRecordTime(final String agentCode) {
        RetrofitClient.getInstances().getScanRecordTime(agentCode).enqueue(new UICallBack<ScanRecordTimeResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(ScanRecordTimeResponse bean) {
                if (isTop) {
                    switch (bean.getReturnValue()) {
                        case 1:
                            dataBeen = bean.getData();
                            LogUtils.d("当前数据长度==" + dataBeen.size());
                            mAdapter = new ScanEntranceTimeAdapter(mContext);
                            mAdapter.updateSeconds(bean.getData(), mBean, 0);
                            LogUtils.d("绑定适配器数据");
                            //mErvScan.setAdapter();
                            mErvScan.setAdapter(mAdapter);
                            break;
                        default:

                            break;
                    }
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

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(mEtScanInput.getText().toString().trim())) {
            mTvScanInputSubmit.setEnabled(true);
        }else {
            mTvScanInputSubmit.setEnabled(false);
        }
        mInputCode = mEtScanInput.getText().toString().trim();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_scan:  //扫描按钮
                mIvScan.startRipple();
                IntentHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIvScan.stopRipple();
                        mActivityUtils.startActivity(ScanQrCodeActivity.class,"type","ENTER");
                    }
                }, 200);
                break;
            case R.id.tv_scan_input_submit:  //提交按钮
                requestScanCode(mInputCode, mLocationAddress);
                break;
        }
    }

    //右上角的扫码列表点击事件
    @Override
    public void setOnToolBarRightClickListener() {
        mActivityUtils.startActivity(SubordinateScanListActivity.class);
    }

}
