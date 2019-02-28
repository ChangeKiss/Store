package com.Store.www.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;

/**
 * 代理查询界面  作废了***
 */
public class AgencyQueryActivity extends BaseToolbarActivity implements TextWatcher{

    @BindView(R.id.et_agency_number)
    EditText mEtAgencyNumber;
    @BindView(R.id.btn_query)
    Button mBtnQuery;

    private String agencyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_agency_query;
    }

    @Override
    public void initView() {
        initToolbar(this,true,R.string.agency_query);
        ActivityCollector.addActivity(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //监听用户输入内容
    @Override
    public void afterTextChanged(Editable s) {
        agencyNumber = mEtAgencyNumber.getText().toString();
        if (agencyNumber!=null){
            mBtnQuery.setEnabled(true);
        }else {
            mBtnQuery.setEnabled(false);
        }
    }
}
