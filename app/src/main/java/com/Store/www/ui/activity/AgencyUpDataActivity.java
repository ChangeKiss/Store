package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonFooter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.AgencyUpDataResponse;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.SubmitUpBegRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.AgencyEquityAdapter;
import com.Store.www.ui.adapter.AgencyUpDataAdapter;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 代理升级界面
 */
public class AgencyUpDataActivity extends BaseToolbarActivity implements DialogHint.OnDialogOneButtonClickListener,AgencyEquityAdapter.onItemClickListener{
    @BindView(R.id.lv_agency_upData)
    LRecyclerView mLvAgencyUpData;  //列表

    ImageView mIvLeftBack;  //左上角的返回键
    TextView mTvAgencyTitle;  //顶部的标题
    CircleImageView mAgencyCvHead;  //代理头像
    TextView mTvAgencyUpName;  //代理姓名
    TextView mTvAgencyLv;  //代理等级
    LinearLayout mLayoutAgencyLvBtn;  //代理等级按钮的父布局
    LinearLayout mLayoutAgencyLvHint;  //代理等级提示图标的父布局
    ImageView mIvAgencyHint;  //提示图标
    //MyLinearLayout mLayoutAgencyEquityOne;  //代理权益父布局  这是自定义的可以自动换行的布局
    RecyclerView mRvAgencyEquity;  //代理权益图标列表
    Button mBtnApplyForUpData;  //申请升级按钮

    private String mTitle;
    private Button mAgencyLvBtn[];  //代理等级按钮  动态添加
    private CommonHeader mCommonHeader;  //头布局
    private CommonFooter mCommonFooter; //尾布局
    private int btnX = 0;  // 用来记录初始按钮是哪一个
    private Timer mTimer;  //定时器
    private static final int HANDLER_COUNT = 2;  //Handler更新UI的值
    private AlertDialog mDialog ;  //升级提示的弹窗
    private AlertDialog mEquityExplainDialog;  //权益描述的弹窗

    AgencyUpDataAdapter mAdapter;  //代理升级的适配器
    LRecyclerViewAdapter viewAdapter;
    AgencyEquityAdapter mAgencyAdapter;  //代理权益图标的适配器
    List<AgencyUpDataResponse.DataBean.AllListBean> allListBeen = new ArrayList<>();
    private AgencyUpDataResponse.DataBean dataBean;
    private LinearLayout.LayoutParams bottomParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_agency_up_data;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mTitle = getIntent().getStringExtra("title");
        initHeadView();
        mTvAgencyTitle.setText(mTitle);  //设置界面标题
    }


    //初始化头布局
    private void initHeadView(){
        mCommonHeader = new CommonHeader(mContext,R.layout.agency_up_data_head);
        mIvLeftBack = (ImageView) mCommonHeader.findViewById(R.id.iv_left_back); //左上角的返回键
        mTvAgencyTitle = (TextView) mCommonHeader.findViewById(R.id.tv_agency_title); //顶部的标题
        mAgencyCvHead = (CircleImageView) mCommonHeader.findViewById(R.id.agency_cv_head); //代理头像
        mTvAgencyUpName = (TextView) mCommonHeader.findViewById(R.id.tv_agency_up_name); //代理姓名
        mTvAgencyLv = (TextView) mCommonHeader.findViewById(R.id.tv_agency_lv);   //代理等级
        mLayoutAgencyLvBtn = (LinearLayout) mCommonHeader.findViewById(R.id.layout_agency_lv_btn);  //代理等级按钮的父布局
        mLayoutAgencyLvHint = (LinearLayout) mCommonHeader.findViewById(R.id.layout_agency_lv_hint);  //代理等级提示图标的父布局
        mIvAgencyHint = (ImageView) mCommonHeader.findViewById(R.id.iv_agency_hint);  //提示图标
        mRvAgencyEquity = (RecyclerView) mCommonHeader.findViewById(R.id.rv_agency_equity);  //代理权益图标列表
        mCommonFooter = new CommonFooter(mContext,R.layout.agency_uo_data_footer);
        mBtnApplyForUpData = (Button) mCommonFooter.findViewById(R.id.btn_apply_for_upData);  //申请升级按钮
        mIvLeftBack.setOnClickListener(this);
        mBtnApplyForUpData.setOnClickListener(this);
        initAdapter();

    }

    //初始化适配器
    private void initAdapter(){
        mAdapter = new AgencyUpDataAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(mCommonHeader);  //添加头布局
        viewAdapter.addFooterView(mCommonFooter);  //添加尾布局
        mLvAgencyUpData.setLayoutManager(new LinearLayoutManager(this));  //添加布局管理器
        mLvAgencyUpData.setAdapter(viewAdapter);
        mLvAgencyUpData.setPullRefreshEnabled(false);  //关闭下拉刷新
        mAgencyAdapter = new AgencyEquityAdapter(this,this);
        GridLayoutManager manager = new GridLayoutManager(mContext,4);  //添加格子布局管理器
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mRvAgencyEquity.setLayoutManager(manager);
        mRvAgencyEquity.setAdapter(mAgencyAdapter);
        getAgencyGrade();
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

    //获取代理当前等级信息
    private void getAgencyGrade(){
        RetrofitClient.getInstances().getAgencyUpData(mUserId).enqueue(new UICallBack<AgencyUpDataResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(AgencyUpDataResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData().getIsApply()==0||bean.getData().getIsApply()==1){
                                mBtnApplyForUpData.setEnabled(false);
                                mBtnApplyForUpData.setText("您的升级请求已经提交,请耐心等待");
                            }else {
                                mBtnApplyForUpData.setEnabled(true);
                            }

                            if (bean.getData().getIsDiamonds()==1){
                                mBtnApplyForUpData.setEnabled(false);
                                mBtnApplyForUpData.setText("您已达到最高级别");
                            }

                            if (!TextUtils.isEmpty(bean.getData().getRefusal())){
                                DialogHint.showDialogWithOneButton(mContext,"您的升级请求已被拒绝,可重新申请,拒绝理由:"+bean.getData().getRefusal(),AgencyUpDataActivity.this);
                            }
                            allListBeen = bean.getData().getAllList();
                            mTvAgencyUpName.setText(bean.getData().getAgentName());
                            mTvAgencyLv.setText(bean.getData().getLevelName());
                            mAdapter.setDataList(bean.getData().getAllList());
                            dynamicSetControlNumber(bean.getData());  //根据数据设置按钮数量
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

    //利用Handler更新按钮下箭头位置 以及权益适配器内容
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_COUNT:
                    mIvAgencyHint.setVisibility(View.VISIBLE);
                    for (int k = 0;k<mAgencyLvBtn.length; k++){
                        if (mAgencyLvBtn[btnX].getId() ==mAgencyLvBtn[k].getId()){
                            mAgencyAdapter.setDataList(allListBeen.get(k).getList());
                            mAgencyAdapter.notifyDataSetChanged();
                        }
                    }
                    mIvAgencyHint.setAnimation(AnimationUtils.makeInAnimation(mContext,true));  //设置动画
                    setLayoutX(mIvAgencyHint, (int)mAgencyLvBtn[btnX].getX()+(mLayoutAgencyLvHint.getPaddingLeft()/2));
                    break;
                default:
                    break;
            }
        }
    };

    //动态设置控件数量以及大小
    private void dynamicSetControlNumber(final AgencyUpDataResponse.DataBean bean) {
        mAgencyLvBtn = new Button[bean.getAllList().size()];
        mAgencyAdapter.setDataList(bean.getAllList().get(0).getList());
        mAgencyAdapter.notifyDataSetChanged();
        for (int i = 0; i < mAgencyLvBtn.length; i++) {
            mAgencyLvBtn[i] = new Button(mContext);
            mAgencyLvBtn[i].setId(2048 + i);
            mAgencyLvBtn[i].setTextSize(16-mAgencyLvBtn.length);
            mAgencyLvBtn[i].setText(bean.getAllList().get(i).getName());
            LogUtils.d("当前屏幕的宽=="+UserPrefs.getInstance().getWidth());
            if (UserPrefs.getInstance().getWidth()==480){
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams((UserPrefs.getInstance().getWidth() / mAgencyLvBtn.length) - (120 / mAgencyLvBtn.length),
                                120);
                mLayoutAgencyLvBtn.addView(mAgencyLvBtn[i],params);
            }else {
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams((UserPrefs.getInstance().getWidth() / mAgencyLvBtn.length) - (120 / mAgencyLvBtn.length),
                                120);
                mLayoutAgencyLvBtn.addView(mAgencyLvBtn[i],params);
            }
            mAgencyLvBtn[i].setTextColor(Color.GRAY);
            if (bean.getFictitiousName().equals(bean.getAllList().get(i).getName())){
                //LogUtils.d("设置"+i+"次");
                for (int s=0;s<i+1;s++){
                    LogUtils.d("设置+"+s+"次");
                    mAgencyLvBtn[s].setBackgroundResource(R.mipmap.agency_ud_illume);
                    mAgencyLvBtn[s].setTextColor(Color.WHITE);
                }
                btnX = i;
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {  //定时任务0.5秒后更新箭头位置
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(HANDLER_COUNT);
                    }
                },600);
            }
            mAgencyLvBtn[i].setTag(i);
            mAgencyLvBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.d("点击按钮的ID=="+v.getId());
                    LogUtils.d("父布局的内边距=="+mLayoutAgencyLvHint.getPaddingLeft());
                    //View view = mAgencyLvBtn[1].getRootView();
                    LogUtils.d("初始X坐标位置=="+mAgencyLvBtn[1].getX());
                    for (int k = 0;k<mAgencyLvBtn.length; k++){
                        if (v.getId() ==mAgencyLvBtn[k].getId()){
                            mAgencyAdapter.setDataList(bean.getAllList().get(k).getList());
                            mAgencyAdapter.notifyDataSetChanged();
                        }
                    }
                    mIvAgencyHint.setAnimation(AnimationUtils.makeInAnimation(mContext,true));  //设置动画
                    setLayoutX(mIvAgencyHint, (int) v.getX()+(mLayoutAgencyLvHint.getPaddingLeft()/2));

                }
            });
        }

    }


    //设置控件的X
    public void setLayoutX(View view, int x) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, margin.topMargin, margin.width, margin.bottomMargin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_left_back:
                finish();
                break;
            case R.id.btn_apply_for_upData:
                //DialogHint.showDialogWithTwoButton(this,R.string.agency_apply_for_hint,this);
                showDialog(mContext);
                break;
        }
    }


    //提交升级请求
    private void requestUpBeg(){
        SubmitUpBegRequest request = new SubmitUpBegRequest(mUserId);
        RetrofitClient.getInstances().requestSubmitBeg(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        mBtnApplyForUpData.setEnabled(false);
                        mBtnApplyForUpData.setText("您的升级请求已经提交,请耐心等待");
                        mDialog.dismiss();
                        break;
                    default:
                        mDialog.dismiss();
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }


    //弹出提示框
    private void showDialog(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_apply_for_ok_two_btn,null);
        Button buttonOk = (Button) view.findViewById(R.id.btn_apply_for_dialog_ok);
        Button buttonCancel = (Button) view.findViewById(R.id.btn_apply_for_dialog_cancel);
        TextView mTvTitle = (TextView) view.findViewById(R.id.tv_apply_for_dialog_title);
        TextView mTvContext = (TextView) view.findViewById(R.id.tv_apply_for_context);
        mTvTitle.setText(R.string.agency_apply);
        mTvContext.setText(R.string.agency_apply_for_hint);
        mDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();
        mDialog.show();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUpBeg();
            }
        });

    }

    //弹出权益描述框
    private void showAgencyEquityExplainDialog(Context context,String title,String text){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_equity_explain,null);
        TextView mTvAgencyEquityTitle = (TextView) view.findViewById(R.id.tv_agency_equity_title);
        TextView mTvAgencyEquityHint  = (TextView) view.findViewById(R.id.tv_agency_equity_hint);
        Button mBtnEquityDismiss = (Button) view.findViewById(R.id.btn_equity_ok_dismiss);
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(title)){
            mTvAgencyEquityTitle.setText(title);  //设置标题
            mTvAgencyEquityHint.setText(text);   //设置提示内容
        }
        mEquityExplainDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();
        mEquityExplainDialog.show();

        //设置弹出框的宽高
        mEquityExplainDialog.getWindow().setLayout(UserPrefs.getInstance().getWidth()-200,WindowManager.LayoutParams.WRAP_CONTENT);

        Window window = mEquityExplainDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //设置AlterDialog为圆角 这里设置AlterDialog的Window为透明这样就可以显示设置的背景
        mBtnEquityDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEquityExplainDialog.dismiss();
            }
        });
    }

    //一个按钮的弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        dialog.dismiss();
    }

    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId) {
        dialog.dismiss();
    }

    //权益图标适配器Item的点击事件
    @Override
    public void itemClickListener(int position, String title, String context) {
        showAgencyEquityExplainDialog(mContext,title,context);
    }
}
