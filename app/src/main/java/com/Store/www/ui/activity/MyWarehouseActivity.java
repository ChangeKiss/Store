package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.DeleteEntityWarehouseRequest;
import com.Store.www.entity.EntityWarehouseResponse;
import com.Store.www.entity.MyWarehouseResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.WarehouseAdapter;
import com.Store.www.ui.adapter.WarehouseHeadAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;

/**
 * 我的仓库界面
 */
public class MyWarehouseActivity extends BaseToolbarActivity implements WarehouseAdapter.OnItemClickListener,
WarehouseHeadAdapter.OnItemClickListener{
    @BindView(R.id.ry_warehouse)
    LRecyclerView mRy;
    @BindView(R.id.nodata)
    RelativeLayout mNodata;
    RecyclerView mRvHead;  //仓库的头布局列表
    LinearLayout mLayoutAddWarehouse;  //添加仓库按钮布局
    TextView mTvUserWarehouseName;  //用户仓库名
    TextView mTvAddWarehouse; //添加仓库
    TextView mTvAgencyNameHint;  //代理云仓库名
    LRecyclerViewAdapter viewAdapter;
    WarehouseAdapter mAdapter;  //主适配器
    WarehouseHeadAdapter mAdapterHead;  //头布局适配器
    private CommonHeader mCommonHeader;  //头布局
    private boolean yunNotNullData =false;
    private boolean entityNotNullData = false;
    private AlertDialog dialog;  //修改或删除实体仓库的弹窗

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_warehouse;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.my_warehouse);
        initHead();
    }

    //初始化头布局
    private void initHead(){
        mCommonHeader = new CommonHeader(mContext,R.layout.layout_warehouse_head);
        mTvAgencyNameHint = (TextView) mCommonHeader.findViewById(R.id.tv_agent_yun_warehouse_name);
        mRvHead = (RecyclerView) mCommonHeader.findViewById(R.id.rv_wh_head);
        mLayoutAddWarehouse = (LinearLayout) mCommonHeader.findViewById(R.id.layout_add_warehouse);
        mTvUserWarehouseName = (TextView) mCommonHeader.findViewById(R.id.tv_user_warehouse_name);
        mTvAddWarehouse = (TextView) mCommonHeader.findViewById(R.id.tv_add_warehouse);
        mTvAddWarehouse.setOnClickListener(this);
        initAdapter();
    }

    //初始化适配器
    private void initAdapter(){
        mAdapter = new WarehouseAdapter(this,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(mCommonHeader);  //添加头布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRy.setLayoutManager(manager);
        mRy.setAdapter(viewAdapter);
        mRy.setPullRefreshEnabled(false);  //关闭下拉刷新
        mAdapterHead = new WarehouseHeadAdapter(this,this);
        mRvHead.setLayoutManager(new LinearLayoutManager(this));
        mRvHead.setAdapter(mAdapterHead);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        getWarehouse();
        getEntityWarehouse();
        if (yunNotNullData && entityNotNullData){
            mNodata.setVisibility(View.GONE);
        }else if (yunNotNullData){
            mNodata.setVisibility(View.GONE);
        }else if (entityNotNullData){
            mNodata.setVisibility(View.GONE);
        }else {
            mNodata.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    //获取云仓库信息
    private void getWarehouse(){
        RetrofitClient.getInstances().getWarehouse(agentCode).enqueue(new UICallBack<MyWarehouseResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
                yunNotNullData = false;
            }

            @Override
            public void OnRequestSuccess(MyWarehouseResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            yunNotNullData = true;
                            mAdapterHead.getDataList().clear();
                            mAdapterHead.addAll(bean.getData());
                            mAdapterHead.notifyDataSetChanged();
                            mTvAgencyNameHint.setText(UserPrefs.getInstance().getNickName()+"的云仓库");
                            mTvUserWarehouseName.setText(UserPrefs.getInstance().getNickName()+"的实体仓库");
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //获取实体仓库信息
    private void getEntityWarehouse(){
        RetrofitClient.getInstances().getEntityWarehouse(mUserId).enqueue(new UICallBack<EntityWarehouseResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
                entityNotNullData = false;
            }

            @Override
            public void OnRequestSuccess(EntityWarehouseResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mLayoutAddWarehouse.setVisibility(View.VISIBLE);
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

    //实体仓库item的点击事件
    @Override
    public void setItemClickListener(int position,int repId) {
        Intent intent = new Intent(this,CommodityStocksActivity.class);
        intent.putExtra("type","entity");
        intent.putExtra("id",repId);
        startActivity(intent);
    }

    //实体仓库的item的长按事件
    @Override
    public void setDeleteAlterClickListener(int position, int repId, String name, String province, String city, String area, String address) {
        showDialog(repId,name,province,city,area,address);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add_warehouse:
                mActivityUtils.startActivity(AddWarehouseActivity.class);
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }

    //云仓库item的点击事件
    @Override
    public void setHeadItemClickListener(int position, int repId, int type) {
        Intent intent = new Intent(this,CommodityStocksActivity.class);
        intent.putExtra("repId",repId);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    //初始化弹窗
    private void showDialog(final int repId, final String name, final String province, final String city, final String area, final String address){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_warehouse_two_button,null);
        ImageView closeIv = (ImageView) view.findViewById(R.id.iv_warehouse_close);
        Button alterButton = (Button) view.findViewById(R.id.btn_dialog_alter);
        Button deleteButton = (Button) view.findViewById(R.id.btn_dialog_delete);
        dialog = new AlertDialog.Builder(mContext).setView(view).create();
        dialog.setCancelable(true);
        dialog.show();
        closeIv.setOnClickListener(new View.OnClickListener() {  //关闭弹窗按钮
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        alterButton.setOnClickListener(new View.OnClickListener() {  //修改仓库按钮
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWarehouseActivity.this,AddWarehouseActivity.class);
                intent.putExtra("type","alter");
                intent.putExtra("id",repId);
                intent.putExtra("name",name);
                intent.putExtra("province",province);
                intent.putExtra("city",city);
                intent.putExtra("area",area);
                intent.putExtra("address",address);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {  //删除仓库按钮
            @Override
            public void onClick(View v) {
                requestDelete(repId,mUserId);
            }
        });

    }

    //删除实体仓库
    private void requestDelete(int id,int agentId){
        DeleteEntityWarehouseRequest request = new DeleteEntityWarehouseRequest(id,agentId);
        RetrofitClient.getInstances().requestDeleteWarehouse(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            dialog.dismiss();
                            getEntityWarehouse();
                            break;
                        default:
                            dialog.dismiss();
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

}
