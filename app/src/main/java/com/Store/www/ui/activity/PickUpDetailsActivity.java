package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.PickUpDetailsResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.PickUpDetailsAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;

/**
 * 提货单详情界面
 */
public class PickUpDetailsActivity extends BaseToolbarActivity {
    TextView mTvPickUpName;  //姓名
    TextView mTvPickUpPhone;  //电话
    TextView mTvPickUpAddress;  //地址
    TextView mTvPickUpNumber; //订单编号
    TextView mTvPickUpType; //订单类型
    TextView mTvPickUpState;  //订单状态
    TextView mTvPickUpCount;  //订单数量
    TextView mTvPickUpTime;  //订单时间

    @BindView(R.id.lr_pick_up)
    LRecyclerView mRy;

    PickUpDetailsAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    CommonHeader mCommonHeader;  //头布局
    private String orderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_pick_up_details;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.pick_up_details);
        orderNumber = getIntent().getStringExtra("orderNumber");
        initHead();
    }

    //初始化头布局
    private void initHead(){
        mCommonHeader = new CommonHeader(mContext,R.layout.layout_pickup_detail_head);
        mTvPickUpName = (TextView) mCommonHeader.findViewById(R.id.tv_pick_up_name);   //姓名
        mTvPickUpPhone = (TextView) mCommonHeader.findViewById(R.id.tv_pick_up_phone);  //电话
        mTvPickUpAddress = (TextView) mCommonHeader.findViewById(R.id.tv_pick_up_address);  //地址
        mTvPickUpNumber = (TextView) mCommonHeader.findViewById(R.id.tv_pick_up_number);  //订单编号
        mTvPickUpType = (TextView) mCommonHeader.findViewById(R.id.tv_pick_up_type);  //订单类型
        mTvPickUpState = (TextView) mCommonHeader.findViewById(R.id.tv_pick_up_state);  //订单状态
        mTvPickUpCount = (TextView) mCommonHeader.findViewById(R.id.tv_pick_up_count);  //订单数量
        mTvPickUpTime = (TextView) mCommonHeader.findViewById(R.id.tv_pick_up_time);  //下单时间
        initAdapter();
    }

    //初始化适配器
    private void initAdapter(){
        mAdapter = new PickUpDetailsAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(mCommonHeader);
        mRy.setLayoutManager(new LinearLayoutManager(this));
        mRy.setAdapter(viewAdapter);
        mRy.setPullRefreshEnabled(false);  //关闭下拉刷新
        getPickUpDetails();
    }

    //获取提货单详情
    private void getPickUpDetails(){
        RetrofitClient.getInstances().getPickUpDetails(orderNumber).enqueue(new UICallBack<PickUpDetailsResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(PickUpDetailsResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        mTvPickUpName.setText(bean.getData().getReceiveName());
                        mTvPickUpPhone.setText(bean.getData().getMobilephone()+"");
                        mTvPickUpAddress.setText(bean.getData().getAddress());
                        mTvPickUpNumber.setText(bean.getData().getOrderNumber());
                        mTvPickUpType.setText(bean.getData().getTypeName());
                        mTvPickUpCount.setText("x"+bean.getData().getCount());
                        long time = bean.getData().getCreateTime();
                        if (time!=0){
                            mTvPickUpTime.setText(ActivityUtils.time(bean.getData().getCreateTime())+"");
                        }
                        if (bean.getData().getStatus()==0){//待发货
                            mTvPickUpState.setText(R.string.stay_shipments);
                        }else if (bean.getData().getStatus()==1){ //发货中
                            mTvPickUpState.setText(R.string.shipments_loading);
                        }else if(bean.getData().getStatus()==5){  //已完成
                            mTvPickUpState.setText(R.string.order_over);
                        }else if (bean.getData().getStatus()==99){  //已取消
                            mTvPickUpState.setText(R.string.is_cancel);
                        }
                        mAdapter.addAll(bean.getData().getPickBillProducts());
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

}
