package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AddWarehouseRequest;
import com.Store.www.entity.AlterEntityWarehouseRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加实体仓库界面
 */
public class AddWarehouseActivity extends BaseToolbarActivity implements TextWatcher {
    @BindView(R.id.et_warehouse_name)
    EditText mEtWarehouseName;  //仓库名称
    @BindView(R.id.tv_select_area)
    TextView mTvSelectArea;   //选择区域
    @BindView(R.id.et_detail_location)
    EditText mEtDetailLocation; //详细地址
    @BindView(R.id.btn_warehouse_location_save)
    Button mBtnWarehouseLocationSave;  //保存地址按钮

    public static final int SELECT_AREA = 3;
    private final int ADDRESS_PROVINCE = 0;
    private final int CITY_FLAG = 1;
    private final int RETURN_AREA = 2;
    private int repId;   //修改的仓库ID
    private String mProvince,mCountry,mCity,mArea,mWarehouseName,mAddress;
    private String mType,name,province,city,area,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_add_warehouse;
    }

    @Override
    public void initView() {
        initToolbar(this, true, R.string.add_entity_warehouse);
        ActivityCollector.addActivity(this);
        mEtWarehouseName.addTextChangedListener(this);
        mEtDetailLocation.addTextChangedListener(this);
        Intent intent = getIntent();
        mType = intent.getStringExtra("type");
        repId = intent.getIntExtra("id",0);
        name = intent.getStringExtra("name");
        province = intent.getStringExtra("province");
        city = intent.getStringExtra("city");
        area = intent.getStringExtra("area");
        address = intent.getStringExtra("address");
        if (mType!=null&&mType.equals("alter")){
            mEtWarehouseName.setText(name);
            mTvSelectArea.setText(province+city+area);
            mEtDetailLocation.setText(address);
        }
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
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(mEtWarehouseName.getText().toString())
                && !TextUtils.isEmpty(mEtDetailLocation.getText().toString())
                && !TextUtils.isEmpty(mTvSelectArea.getText().toString())){
            mBtnWarehouseLocationSave.setEnabled(true);
        }else {
            mBtnWarehouseLocationSave.setEnabled(false);
        }
        mWarehouseName = mEtWarehouseName.getText().toString();
        mAddress = mEtDetailLocation.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==ADDRESS_PROVINCE){ //省级返回的值
            if (requestCode ==SELECT_AREA){
                mCountry = data.getStringExtra("country");
                mProvince = data.getStringExtra("province");
                mCity = "";
                mArea = "";
                if (!TextUtils.isEmpty(mCountry))mTvSelectArea.setText(mCountry);

            }
        }else if (resultCode ==CITY_FLAG){ //市级返回的值
            if (requestCode == SELECT_AREA){
                mCountry = data.getStringExtra("country");
                mProvince = data.getStringExtra("province");
                mCity = data.getStringExtra("city");
                mArea = "";
                mTvSelectArea.setText(mProvince+mCity);
            }
        }else if (resultCode ==RETURN_AREA){  //区域返回的值
            if (requestCode == SELECT_AREA){
                mCountry = data.getStringExtra("country");
                mProvince = data.getStringExtra("province");
                mCity = data.getStringExtra("city");
                mArea = data.getStringExtra("area");
                mTvSelectArea.setText(mProvince+mCity+mArea);
            }
        }

    }

    //按钮点击事件
    @OnClick({R.id.tv_select_area, R.id.btn_warehouse_location_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_area:  //选择区域
                Intent intent = new Intent(this,AlterCityAddressActivity.class);
                startActivityForResult(intent,SELECT_AREA);
                break;
            case R.id.btn_warehouse_location_save:  //保存仓库地址
                mBtnWarehouseLocationSave.setEnabled(false);  //点击提交之后 关闭按钮点击事件以免多次重复提交
                if (mType!=null && mType.equals("alter")){  //修改仓库
                    requestAlterWarehouse(mUserId,repId,mProvince,mCity,mArea,mAddress,mWarehouseName);
                }else {  //否则默认是添加仓库
                    requestAddWarehouse(userId,mProvince,mCity,mArea,mAddress,mWarehouseName);
                }
                break;
        }
    }

    //添加实体仓库请求
    private void requestAddWarehouse(String agentId,String province,String city,String are,String address,String warehouseName){
        AddWarehouseRequest request = new AddWarehouseRequest(agentId,province,city,are,address,warehouseName);
        RetrofitClient.getInstances().requestAddWarehouse(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    mBtnWarehouseLocationSave.setEnabled(true);
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        mBtnWarehouseLocationSave.setEnabled(true);
                        break;
                }
            }
        });
    }

    //修改实体仓库
    private void requestAlterWarehouse(int agentId,int Id,String province,String city,String area,String address,String warehouseName){
        AlterEntityWarehouseRequest request = new AlterEntityWarehouseRequest(agentId,Id,province,city,area,address,warehouseName);
        RetrofitClient.getInstances().requestAlterEntityWarehouse(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    mBtnWarehouseLocationSave.setEnabled(true);
                }
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
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
}
