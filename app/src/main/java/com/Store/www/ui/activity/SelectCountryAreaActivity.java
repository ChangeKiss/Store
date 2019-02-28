package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.CountryCodeResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.CountrySelectAdapter;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;
/**
 *选择国家区域界面
 */
public class SelectCountryAreaActivity extends BaseToolbarActivity implements CountrySelectAdapter.ClickListener{
    @BindView(R.id.rv_country)
    RecyclerView mRv;

    CountrySelectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_select_country_area;
    }

    @Override
    public void initView() {
        initToolbar(this,true,"选择国家和区域");
        ActivityCollector.addActivity(this);
        mAdapter = new CountrySelectAdapter(this,this);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
        getCountryCode();
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

    private void getCountryCode(){
        RetrofitClient.getInstancesTest().getCountryCode().enqueue(new UICallBack<CountryCodeResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(CountryCodeResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mAdapter.getDataList().clear();
                            mAdapter.addAll(bean.getData());
                            mAdapter.notifyDataSetChanged();
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }


    //item的点击事件
    @Override
    public void onItemClickListener(int position, int areaCode) {
        Intent intent = new Intent();
        intent.putExtra("areaCode","+"+areaCode);
        setResult(6,intent);
        finish();
    }
}
