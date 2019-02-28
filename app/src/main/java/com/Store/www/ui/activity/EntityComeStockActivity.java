package com.Store.www.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AffirmComeStockRequest;
import com.Store.www.entity.AlterComeStockRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.CleanComeStockRequest;
import com.Store.www.entity.EntityWarehouseStocksResponse;
import com.Store.www.entity.GainNotComeStockResponse;
import com.Store.www.entity.QueryOutSubordinateResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.ComeStockPopAdapter;
import com.Store.www.ui.adapter.EntityWarehouseStockAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 实体仓库商品出库界面
 */
public class EntityComeStockActivity extends BaseToolbarActivity implements EntityWarehouseStockAdapter.ClickListener,OnLoadMoreListener ,
            ComeStockPopAdapter.minusPlusClickListener,TextWatcher,BaseToolbarActivity.OnToolBarRightClickListener{
    @BindView(R.id.tv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.layout_come_stock)
    LinearLayout mLayoutComeStock;   //pop要弹出的布局
    @BindView(R.id.rv_entity_come)
    LRecyclerView mRvEntityCome;  //实体仓库商品出库列表
    @BindView(R.id.tv_group_name)
    TextView mTvGroupName; //出库商品数量
    @BindView(R.id.tv_clean_come_stocks)
    TextView mTvCleanComeStocks;  //清空出库列表
    @BindView(R.id.popUpLayout)
    RelativeLayout mPopUpLayout;  //弹出弹窗
    @BindView(R.id.iv_arrow_up)
    ImageView mIvArrow;  //箭头
    @BindView(R.id.layout_hint_come)
    LinearLayout mLayoutHintCome;  //出库提示
    @BindView(R.id.layout_btn_come_parent)
    LinearLayout mLayoutBtnComeParent;   //出库按钮父布局
    @BindView(R.id.btn_come_stock)
    Button mBtnComeStock; //出库


    LRecyclerViewAdapter viewAdapter;
    EntityWarehouseStockAdapter mAdapter;
    ComeStockPopAdapter popAdapter;
    private String mTitle;
    private int mRepId;
    private int countPage = 20;  //实体仓库一页加载的商品数量
    LinearLayout.LayoutParams params;
    PopupWindow popupWindow;
    private int notComeStockNumber = 0;
    private int[] NotComeStockNumbers = new int[]{};  //未出库商品数量
    private TextView mTvNameNumber;  //pop弹窗的修改数量
    private ProgressDialog dialog;  //遮盖dialog
    private AlertDialog mAlertDialog;  //确认出库弹窗
    private EditText mEtRemark;  //dialog弹窗备注输入框
    private EditText mEtAgentNumber;  //dialog弹窗代理编号输入框
    private String mRemark = "";  //备注
    private String mInputAgent = "";  //查询代理编号
    private int ClientOrAgent = 0;   //顾客或者代理 0是顾客 1是代理
    private RelativeLayout mLayoutAgentMessage;  //代理信息布局
    private CircleImageView mCv;  //代理头像
    private LinearLayout layoutQueryMessage;  //下级代理信息
    private TextView mTvQueryAgentName;  //下级代理姓名
    private TextView mTvQueryAgentNumber;  //下级代理编号
    private TextView mTvClientHint;   //顾客的提示
    private String mAgentCode= " ";  //出库代理编号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_entity_come_stock;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mRepId = intent.getIntExtra("id",0);
        initToolbar(this, true, mTitle,this);
        ActivityCollector.addActivity(this);
        mIvToolbarRight.setImageResource(R.mipmap.scan_right_icon);
        mIvToolbarRight.setVisibility(View.VISIBLE);  // 扫码出库
        mIvToolbarRight.setOnClickListener(this);
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
        getGainNotCome("");
        initAdapter();
    }

    //初始化适配器
    private void initAdapter(){
        mAdapter = new EntityWarehouseStockAdapter(this,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRvEntityCome.setLayoutManager(new LinearLayoutManager(this));
        mRvEntityCome.setAdapter(viewAdapter);
        mRvEntityCome.setPullRefreshEnabled(false);
        mRvEntityCome.setOnLoadMoreListener(this);
        mRvEntityCome.setNoMore(false);
        mPageIndex =1;
        getEntityStocks(mPageIndex,countPage);
        popAdapter = new ComeStockPopAdapter(this,this);
    }


    /**
     * 设置底部弹窗时背景颜色
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }


    @OnClick({R.id.popUpLayout, R.id.btn_come_stock,R.id.tv_clean_come_stocks})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clean_come_stocks:
                requestCleanComeStock("");
                break;
            case R.id.popUpLayout:  //弹出pop
                showPop();
                mLayoutHintCome.setVisibility(View.GONE);
                mLayoutBtnComeParent.setVisibility(View.GONE);
                setBackgroundAlpha(0.5f,mContext);
                mIvArrow.setEnabled(false);
                break;
            case R.id.btn_come_stock:  //出库
                mBtnComeStock.setEnabled(false);
                showDialog();
                break;
        }
    }

    //为了解决弹出PopupWindow后外部的事件不会分发,既外部的界面不可以点击
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    //弹出商品编辑pop
    private void showPop(){
        View view = getLayoutInflater().inflate(R.layout.pop_come_stock_hint,null);
        RelativeLayout popDownLayout = view.findViewById(R.id.rl_hint_head);  //弹窗顶部的父布局 用来控制弹窗弹出关闭
        mTvNameNumber = (TextView) view.findViewById(R.id.tv_group_name_number);
        final ImageView mIvDown = (ImageView) view.findViewById(R.id.iv_arrow_down);
        TextView mTvClean = (TextView) view.findViewById(R.id.tv_clean_come_stock);
        mTvNameNumber.setText("已选"+notComeStockNumber+"件商品");
        RecyclerView mRv = (RecyclerView) view.findViewById(R.id.rv_come_stock);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(popAdapter);
        popupWindow = new PopupWindow(
                view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
        popupWindow.setAnimationStyle(R.style.showPopupAnimation);  //添加弹出 退出动画
        popupWindow.showAtLocation(mLayoutComeStock, Gravity.BOTTOM, 0, 0);
        popDownLayout.setOnClickListener(new View.OnClickListener() {  //pop收起按钮
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                mLayoutHintCome.setVisibility(View.VISIBLE);
                mLayoutBtnComeParent.setVisibility(View.VISIBLE);
                setBackgroundAlpha(1.0f,mContext);
                mIvArrow.setEnabled(true);
            }
        });
        mTvClean.setOnClickListener(new View.OnClickListener() {  //清空出库单按钮
            @Override
            public void onClick(View v) {
                requestCleanComeStock("showClean");
            }
        });
    }

    //弹出备注dialog
    private void showDialog(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_affirm_come_stock,null);
        mEtRemark =  view.findViewById(R.id.et_remark);  //备注输入框
        mEtAgentNumber = view.findViewById(R.id.et_agent_number);     //查询代理输入框
        Button mBtnCancel =  view.findViewById(R.id.tv_cancel_come);  //取消按钮
        Button mBtnAffirm =  view.findViewById(R.id.tv_affirm_come);  //确认按钮
        LinearLayout layoutInput = view.findViewById(R.id.layout_input_view);  //输入下级代理布局
        TextView mTvQuery = view.findViewById(R.id.tv_query);  //查询按钮
        mLayoutAgentMessage = view.findViewById(R.id.r_layout_agent_message);
        mCv = view.findViewById(R.id.civ_agent_head);  //头像
        layoutQueryMessage = view.findViewById(R.id.layout_query_message);  //查询的下级信息
        mTvQueryAgentName = view.findViewById(R.id.tv_subordinate_agent_name);  //下级代理姓名
        mTvQueryAgentNumber = view.findViewById(R.id.tv_subordinate_agent_number);  //下级代理编号
        mTvClientHint = view.findViewById(R.id.tv_client_hint);  //顾客提示
        mEtAgentNumber.addTextChangedListener(this); //添加监听事件
        mEtRemark.addTextChangedListener(this);  //添加监听事件
        mAlertDialog = new AlertDialog.Builder(mContext).setView(view).create();
        mAlertDialog.setCancelable(false);  //不允许点击外部关闭弹窗
        mAlertDialog.show();
        mTvQuery.setOnClickListener(v ->{  //查询下级代理按钮点击事件
            if (TextUtils.isEmpty(mInputAgent)){
                showToast("请输入完整信息");
                return;
            }
            getQueryAgent(mInputAgent);
        });

        mBtnCancel.setOnClickListener(v -> {  //取消按钮点击事件
            mAlertDialog.dismiss();
            mBtnComeStock.setEnabled(true);
        });

        mBtnAffirm.setOnClickListener(v -> {  //确认出库点击事件
            requestAffirmComeStock(mAgentCode);
        });

    }

    //查询出库下级代理信息
    private void getQueryAgent(String agentCode){
        RetrofitClient.getInstances().getOutSubordinate(agentCode).enqueue(new UICallBack<QueryOutSubordinateResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(QueryOutSubordinateResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){  //查询成功
                        case 1:
                            if (!TextUtils.isEmpty(bean.getData().getHeadPicture()))Glide.with(mContext).
                                                                                    load(bean.getData().
                                                                                    getHeadPicture()).
                                                                                    error(R.mipmap.default_head).
                                                                                    into(mCv);
                            mTvQueryAgentName.setText(bean.getData().getName());
                            mTvQueryAgentNumber.setText(bean.getData().getAgentCode());
                            layoutQueryMessage.setVisibility(View.VISIBLE);
                            mTvClientHint.setVisibility(View.GONE);
                            mAgentCode = bean.getData().getAgentCode();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //item的点击事件
    @Override
    public void onItemClickListener(int position, int productId, String name) {
        Intent intent = new Intent(this,PickUpRepertoryActivity.class);
        intent.putExtra("type","comeStock");
        intent.putExtra("title",name);
        intent.putExtra("id",mRepId);
        intent.putExtra("productId",productId);
        startActivity(intent);
    }

    //上拉加载更多
    @Override
    public void onLoadMore() {
        mPageIndex++;
        getEntityStocks(mPageIndex,countPage);
    }

    //获取实体仓库商品库存
    private void getEntityStocks(int page, final int countPage){
        if (page==1){
            DialogLoading.shows(mContext,"加载中...");
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        RetrofitClient.getInstances().getEntityStocks(mRepId,page,countPage).enqueue(new UICallBack<EntityWarehouseStocksResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(EntityWarehouseStocksResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getMap().size()<countPage){
                                mRvEntityCome.setNoMore(true);
                                mAdapter.addAll(bean.getMap());
                                mAdapter.notifyDataSetChanged();
                            }else if (bean.getMap()!=null){
                                mAdapter.addAll(bean.getMap());
                                mAdapter.notifyDataSetChanged();
                                mRvEntityCome.refreshComplete(countPage);  //加载完成数量20
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    //获取未出库产品
    private void getGainNotCome(final String refresh){
        RetrofitClient.getInstances().getNotComeStock(mRepId).enqueue(new UICallBack<GainNotComeStockResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(GainNotComeStockResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (dialog!=null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                            notComeStockNumber = bean.getTotalNum();
                            if (refresh!=null && refresh.equals("refreshCount")){
                                mTvNameNumber.setText("已选"+bean.getTotalNum()+"件商品");
                            }
                            NotComeStockNumbers = new int[bean.getList().size()];
                            for (int i=0;i<NotComeStockNumbers.length;i++){
                                NotComeStockNumbers[i] = bean.getList().get(i).getCount();
                            }
                            if (bean.getTotalNum()==0){
                                mIvArrow.setEnabled(false);
                                mTvCleanComeStocks.setVisibility(View.GONE);
                                mBtnComeStock.setEnabled(false);
                            }else {
                                mIvArrow.setEnabled(true);
                                mTvCleanComeStocks.setVisibility(View.VISIBLE);
                                mBtnComeStock.setEnabled(true);
                            }
                            mTvGroupName.setText("已选"+bean.getTotalNum()+"件商品");
                            popAdapter.getDataList().clear();
                            popAdapter.setmRepertorySum(NotComeStockNumbers);
                            popAdapter.addAll(bean.getList());
                            popAdapter.notifyDataSetChanged();
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }

    //pop弹窗减号的点击事件
    @Override
    public void onAlterMinusClickListener(int position, int id, int number) {
        dialog = ProgressDialog.show(mContext,"","提交中请稍后...");
        dialog.setCancelable(false);
        requestAlterComeStock(id,number);
    }

    //pop弹窗加号的点击事件
    @Override
    public void onAlterPlusClickListener(int position, int id,int number) {
        dialog = ProgressDialog.show(mContext,"","提交中请稍后...");
        dialog.setCancelable(false);
        requestAlterComeStock(id,number);
    }

    //修改单件出库商品数量
    private void requestAlterComeStock(int id,int number){
        AlterComeStockRequest request = new AlterComeStockRequest(id,number);
        RetrofitClient.getInstances().requestAlterComeStock(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            getGainNotCome("refreshCount");
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            dialog.dismiss();
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
        mRemark = mEtRemark.getText().toString().trim();
        mInputAgent = mEtAgentNumber.getText().toString().trim();
    }

    //确认出库
    private void requestAffirmComeStock(String agentCode){
        AffirmComeStockRequest request = new AffirmComeStockRequest(mRepId,mRemark,agentCode);
        RetrofitClient.getInstances().requestAffirmCome(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mAlertDialog.dismiss();
                            showToast("出库成功");
                            Intent intent = new Intent();
                            intent.setAction("addOver");
                            sendBroadcast(intent);
                            finish();
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            mBtnComeStock.setEnabled(true);
                            break;
                    }
                }
            }
        });
    }

    //清空出库单
    private void requestCleanComeStock(final String type){
        CleanComeStockRequest request = new CleanComeStockRequest(mRepId);
        RetrofitClient.getInstances().requestCleanComeStack(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (type!=null && type.equals("showClean")){
                                popupWindow.dismiss();
                                mLayoutHintCome.setVisibility(View.VISIBLE);
                                mLayoutBtnComeParent.setVisibility(View.VISIBLE);
                                setBackgroundAlpha(1.0f,mContext);
                                mIvArrow.setEnabled(true);
                            }
                            getGainNotCome("");
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //右上角的图标点击事件
    @Override
    public void setOnToolBarRightClickListener() {  //出库扫描
        Intent intent = new Intent(this,ScanQrCodeActivity.class);
        intent.putExtra("type","OUT");
        intent.putExtra("id",mRepId);
        startActivity(intent);
//        mActivityUtils.startActivity(ScanQrCodeActivity.class,"type","OUT");
    }
}
