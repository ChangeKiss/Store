package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.DeleteAddressRequest;
import com.Store.www.entity.LocationResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.LocationAdapter;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;

/**
 * 地址管理界面
 */

public class LocationManagerActivity extends BaseToolbarActivity implements LocationAdapter.OnItemClickListener,
        DialogHint.OnDialogTwoButtonClickListener{

    @BindView(R.id.iv_toolbar_left)
    ImageView mIvToolbarLeft;
    @BindView(R.id.iv_toolbar_right)
    TextView mTvToolbarRight;
    @BindView(R.id.ry_location)
    RecyclerView mRy;

    LocationAdapter mAdapter;
    private String type;
    private int mAddressId;
    static final int ORDER_SELECT_ADDRESS = 11;
    static final int ORDER_SUBMIT_ADDRESS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_location_manager;

    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        type = getIntent().getStringExtra("selectAddress");
        if (type == null) {
            type = "location";
        }
        LogUtils.d("初始type==" + type);
        initToolbar(this, true, R.string.location_manager);
        mTvToolbarRight.setVisibility(View.VISIBLE);
        mTvToolbarRight.setText(R.string.add_location);
        mTvToolbarRight.setOnClickListener(this);
        mAdapter = new LocationAdapter(this, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        mAdapter.getDataList().clear();
            getLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
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
                            mAdapter.getDataList().clear();
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_toolbar_right:
                LogUtils.d("点击了添加新地址");
                Intent intent = new Intent(this,AddLocationActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }

    //适配器的item的点击事件
    @Override
    public void setOnItemClickListener(int position, String name, String phone, String address, int isUsed,
                                       String country, String province, String city, String street, int id) {
        LogUtils.d("type==" + type);
        if (type.equals("location")) {
            Intent intent = new Intent(this, AddLocationActivity.class);
            intent.putExtra("type", "alter");
            intent.putExtra("name", name);
            intent.putExtra("phoneNumber", phone);
            intent.putExtra("address", address);
            intent.putExtra("isused", isUsed);
            intent.putExtra("country", country);
            intent.putExtra("province", province);
            intent.putExtra("city", city);
            intent.putExtra("street", street);
            intent.putExtra("addressId", id);
            startActivity(intent);
        } else if (type.equals("order")) {  //提货单选择地址
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("phoneNumber", phone);
            intent.putExtra("address", address);
            intent.putExtra("city", city);
            intent.putExtra("province", province); //省直辖市
            intent.putExtra("addressId", 2);
            intent.putExtra("area", street);  //区域
            setResult(ORDER_SELECT_ADDRESS, intent);
            finish();
        } else if (type.equals("submit")) {  //提交订单选择地址
            Intent intent = new Intent();
            intent.putExtra("name", name);  //名字
            intent.putExtra("phoneNumber", phone);  //电话
            intent.putExtra("city", city);  //城市
            intent.putExtra("address", address);  //详细地址
            intent.putExtra("area", street);  //区域
            intent.putExtra("province", province); //省直辖市
            intent.putExtra("addressId", 2);
            setResult(ORDER_SUBMIT_ADDRESS, intent);
            finish();
        }

    }

    //长按事件
    @Override
    public void setDeleteClickListener(int position, int id) {
        mAddressId = id;
        DialogHint.showDialogWithTwoButton(this, R.string.ok_delete_address, this);
    }

    //删除地址
    private void deleteAddress(int id, String userid) {
        DeleteAddressRequest request = new DeleteAddressRequest(id, userid);
        RetrofitClient.getInstances().requestDeleteAddress(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        Intent intent = new Intent();  //发送广播
                        intent.putExtra("address"," ");
                        intent.setAction("lock");
                        sendBroadcast(intent);   //发送广播
                        showToast(R.string.delete);
                        getLocation();
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
        deleteAddress(mAddressId, userId);
        dialog.dismiss();
    }

    //弹窗取消的点击事件
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }


}
