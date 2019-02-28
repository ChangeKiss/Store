package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AlterAddressResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.AlterCityAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改城市地址界面
 */
public class AlterCityAddressActivity extends BaseToolbarActivity implements
        AdapterView.OnItemClickListener {
    @BindView(R.id.iv_toolbar_left)
    ImageView mIvToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_province)
    ListView mLvAddressSelect;

    AlterCityAdapter mAdapter;
    private final int ADDRESS_PROVINCE = 0;
    private final int CITY_FLAG = 1;
    private final int RETURN_AREA = 2;
    private int mCityId;



    private String mProvince, mCountry, mArea, mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
    }

    @Override
    public int initLayout() {
        return R.layout.activity_alter_city_address;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mTvToolbarTitle.setText(R.string.location_province_select);
        mIvToolbarLeft.setVisibility(View.VISIBLE);
    }

    private void initAdapter() {
        mAdapter = new AlterCityAdapter(this);
        getCityContent(ADDRESS_PROVINCE,0); //请求省份的数据
        mLvAddressSelect.setAdapter(mAdapter);
        mLvAddressSelect.setOnItemClickListener(this);
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

    //获取地址信息
    private void getCityContent(final int flag, int cityId) {
        RetrofitClient.getInstances().getProvince(flag, cityId).enqueue(new UICallBack<AlterAddressResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(AlterAddressResponse bean) {
                List<AlterAddressResponse.DataBean.CityListBean> cityList
                        = bean.getData().getCityList();
                switch (bean.getReturnValue()) {
                    case 1:
                        if (isTop){
                            if (cityList.size()==0){
                                Intent intent = new Intent();
                                intent.putExtra("province",mProvince);
                                intent.putExtra("city",mCity);
                                intent.putExtra("country","中国");
                                setResult(CITY_FLAG,intent);
                                finish();
                            }else {
                                mAdapter.setFlag(flag);
                                mAdapter.setDataList(cityList);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    default:
                        checkNet();
                        break;
                }
            }
        });
    }

    //Item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (mAdapter.getFlag()){
            case ADDRESS_PROVINCE: //点击的是省的列表，刷新获取对应的地级市的列表
                int provinceId = mAdapter.getDataList().get(position).getId();
                mCityId = provinceId;
                LogUtils.d("城市ID"+mCityId);
                LogUtils.d("市级ID=="+provinceId);
                mProvince = mAdapter.getDataList().get(position).getName();
                LogUtils.d("省=="+mProvince);
                if (provinceId==99){  //如果id 99点击的是海外,将地址传回去
                    Intent intent = new Intent();
                    intent.putExtra("province",mProvince);
                    intent.putExtra("country","海外");
                    setResult(ADDRESS_PROVINCE,intent);
                    finish();
                }else {
                    LogUtils.d("省=="+mProvince);
                    mAdapter.getDataList().clear();
                    mTvToolbarTitle.setText(R.string.location_city_select);
                    getCityContent(CITY_FLAG,provinceId);
                }
                break;
            case CITY_FLAG:  //点击市获取对应的区，县列表
                int cityId = mAdapter.getDataList().get(position).getId();
                mCity = mAdapter.getDataList().get(position).getName();
                LogUtils.d("市=="+mCity);
                if (mAdapter.getDataList().size()==0){
                    Intent intent = new Intent();
                    intent.putExtra("province",mProvince);
                    intent.putExtra("city",mCity);
                    intent.putExtra("country","中国");
                    setResult(CITY_FLAG,intent);
                    finish();
                }else {
                    mAdapter.getDataList().clear();
                    mTvToolbarTitle.setText(R.string.location_area);
                    getCityContent(RETURN_AREA,cityId);
                }
                break;
            case RETURN_AREA:  //点击区域，将地址信息返回选择添加地址界面
                mArea = mAdapter.getDataList().get(position).getName();
                Intent intent = new Intent();
                intent.putExtra("province",mProvince);
                intent.putExtra("city",mCity);
                intent.putExtra("area",mArea);
                intent.putExtra("country","中国");
                setResult(RETURN_AREA,intent);
                finish();
                break;

        }

    }


    @OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        onBackPressed();
    }

    //处理返回键
    @Override
    public void onBackPressed() {
        switch (mAdapter.getFlag()){
            case ADDRESS_PROVINCE:  //省
                LogUtils.d("选择省点击返回键");
                Intent intent = new Intent();
                intent.putExtra("province","");
                intent.putExtra("country","");
                setResult(ADDRESS_PROVINCE,intent);
                finish();
                break;
            case CITY_FLAG:  //市
                LogUtils.d("选择市点击返回键");
                mTvToolbarTitle.setText(R.string.location_province_select);
                getCityContent(ADDRESS_PROVINCE,0); //请求省份的数据
                break;
            case RETURN_AREA: //区域
                LogUtils.d("选择区域点击返回键");
                mTvToolbarTitle.setText(R.string.location_city_select);
                getCityContent(CITY_FLAG,mCityId); //请求市的数据
                break;
        }
    }
}
