package com.Store.www.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseFragment;
import com.Store.www.base.BubbleView.BubbleTextView;
import com.Store.www.base.TableView.TableView;
import com.Store.www.entity.AgentDataResponse;
import com.Store.www.entity.BalanceResponse;
import com.Store.www.entity.CMBPayUrlResponse;
import com.Store.www.entity.MarginResponse;
import com.Store.www.entity.PayBoundRequest;
import com.Store.www.entity.QRCodeResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.activity.CommonIssueActivity;
import com.Store.www.ui.activity.CommonWebActivity;
import com.Store.www.ui.activity.CompanySystemActivity;
import com.Store.www.ui.activity.FeedBackActivity;
import com.Store.www.ui.activity.LocationManagerActivity;
import com.Store.www.ui.activity.LoginActivity;
import com.Store.www.ui.activity.MyBalanceActivity;
import com.Store.www.ui.activity.MyCenterActivity;
import com.Store.www.ui.activity.MyOrderActivity;
import com.Store.www.ui.activity.MyQrCodeActivity;
import com.Store.www.ui.activity.MyWarehouseActivity;
import com.Store.www.ui.activity.PickUpGoodsActivity;
import com.Store.www.ui.activity.RegardActivity;
import com.Store.www.ui.activity.ResultsActivity;
import com.Store.www.ui.activity.RetailRecordActivity;
import com.Store.www.ui.activity.ScanEntranceActivity;
import com.Store.www.ui.activity.SelectAlterPasswordActivity;
import com.Store.www.ui.activity.SelectBankCardActivity;
import com.Store.www.ui.activity.SelectCredentialActivity;
import com.Store.www.ui.activity.SellManageActivity;
import com.Store.www.ui.activity.SizeAdjustActivity;
import com.Store.www.ui.activity.UpDataBegActivity;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.mob.MobSDK;



import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的金薇碎片
 */


public class MyKivieFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener{
    @BindView(R.id.tv_personage_name)
    TextView mTvPersonageName;
    @BindView(R.id.iv_mine_icon)
    CircleImageView mIvMineIcon;
    @BindView(R.id.tv_personage_grade)
    TextView mTvPersonageGrade;
    @BindView(R.id.layout_user_detail)
    LinearLayout mLayoutUserDetail;  //登录后用户信息
    @BindView(R.id.r_layout_shapewear_lv)
    RelativeLayout mRLayoutShapeWearLv;  //代理塑身衣等级布局
    @BindView(R.id.tv_shapewear_grade)
    TextView mTvShapeWearGrade;  //代理塑身衣等级
    @BindView(R.id.layout_login)
    LinearLayout mLayoutLogin;  //登录界面
    @BindView(R.id.layout_my_one)
    LinearLayout mLayoutMyOne;
    @BindView(R.id.layout_my_two)
    LinearLayout mLayoutMyTwo;
    ActivityUtils mActivityUtils;
    @BindView(R.id.tv_set)
    TextView mTvSet;  //设置按钮
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;  //二维码按钮
    @BindView(R.id.tv_personage_feedback)
    TextView mTvPersonageFeedBack;  //意见发反馈
    @BindView(R.id.tv_balance_money)
    TextView mTvBalanceMoney;  //余额
    @BindView(R.id.tv_apply_for_hint_number)
    TextView mTvApplyForHintNumber;  //升级请求数量
    @BindView(R.id.tv_settings)
    TextView mTvSettings;  //上拉显示的隐藏布局的设置按钮
    @BindView(R.id.iv_qr_codes)
    ImageView mIvQrCodes;   //上拉显示的隐藏布局的二维码按钮
    @BindView(R.id.layout_shape_wear_grade_hint)
    LinearLayout mLayoutShapeWearGradeHint;  //塑身衣等级提示
    @BindView(R.id.tv_grade_rule)
    TextView mTvGradeRule;  //塑身衣等级规则
    @BindView(R.id.layout_lv_progressBar)
    LinearLayout mLayoutLvProgressBar;  //动态添加父布局
    @BindView(R.id.tv_shape_wear_number)
    BubbleTextView mTvShapeWearNumber;  //塑身衣数量
    @BindView(R.id.layout_lv_nickname_hint)
    LinearLayout mLayoutLvNickNameHint;  //动态添加等级名
    @BindView(R.id.layout_lv_number_hint)
    LinearLayout mLayoutLvNumberHint;  //动态添加等级数量
    @BindView(R.id.tv_now_grade)
    TextView mTvNowGrade;  //当前塑身衣等级
    @BindView(R.id.tv_next_grade)
    TextView mTvNextGrade;  //下一等级
    @BindView(R.id.layout_cultivate_message)
    LinearLayout mLayoutCultivateMessage;  //培训信息 代理培训后才会显示
    @BindView(R.id.layout_cut_off_day)
    LinearLayout mLayoutCutOffDay;  //截止培训天数父布局
    @BindView(R.id.tv_cut_off_day)
    TextView mTvCutOffDay; //培训截止天数
    @BindView(R.id.layout_cut_off_month)
    LinearLayout mLayoutCutOffMonth;  //截止补货月份父布局
    @BindView(R.id.tv_cut_off_month)
    TextView mTvCupOffMonth;  //补货截止月份
    @BindView(R.id.tv_margin_hint_tag)
    TextView mTvMarginHintTag;  //保证金提示标志
    TableView mTableView;

    private String IsPayBound;
    private int mPayType =0;  //保证金支付类型
    private LinearLayout mLayoutPay;  //需要支付布局
    private LinearLayout mLayoutPayNo; //无需支付布局
    private ProgressBar mProgressBar[]= new ProgressBar[]{};  //进度条
    private TextView mShapeWearLvName[] = new TextView[]{};  //塑身衣等级名
    private TextView mShapeWearLvNumber[] = new TextView[]{}; //塑身衣数量
    private AlertDialog mShapeWearDialog;  //塑身衣等级说明的弹窗
    private AlertDialog mMarginHintDialog;  //保证金提示弹窗
    private TextView mRuleGrade[] ;  //塑身衣规则等级
    private TextView mGradeRuleOne[];  //塑身衣等级规则一
    private TextView mGradeRuleTwo[];  //塑身衣等级规则二
    private List<String[]> tableHead = new ArrayList<>();
    private List<String[]> tableContent = new ArrayList<>();
    private String[] head;
    private String[] content;
    private String marginHintContent;  //保证金提示内容
    private String marginHintMoney;   //保证金提示金额
    SpannableStringBuilder builder;
    SpannableStringBuilder mBuilder;
    ForegroundColorSpan mSpan;
    private AppBarLayout appBar;
    private static final int HANDLER_COUNT = 6;  //Handler更新UI的值
    private List<AgentDataResponse.DataBean.SsyLevelListBean> Data ;
    private AgentDataResponse.DataBean dataBeen = new AgentDataResponse.DataBean();
    /**
     * 大布局背景，遮罩层
     */
    private View bgContent;
    /**
     * 展开状态下toolbar显示的内容
     */
    private View toolbarOpen;
    /**
     * 展开状态下toolbar的遮罩层
     */
    private View bgToolbarOpen;
    /**
     * 收缩状态下toolbar显示的内容
     */
    private View toolbarClose;
    /**
     * 收缩状态下toolbar的遮罩层
     */
    private View bgToolbarClose;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.d("onCreateView");
        View view = inflater.inflate(R.layout.fragment_my_kivie, container, false);
        unbinder = ButterKnife.bind(this, view);
        mActivityUtils = new ActivityUtils(this);
        //judgeLogin();
        MobSDK.init(mContext,"245a466822227","a7a415e1869609aaeac6d8cdc08b7411");  //初始化MOB分享*****
        appBar = (AppBarLayout) view.findViewById(R.id.app_bar);
        bgContent = view.findViewById(R.id.bg_content);
        toolbarOpen = view.findViewById(R.id.include_toolbar_open);
        bgToolbarOpen = view.findViewById(R.id.bg_toolbar_open);
        toolbarClose = view.findViewById(R.id.include_toolbar_close);
        bgToolbarClose = view.findViewById(R.id.bg_toolbar_close);
        appBar.addOnOffsetChangedListener(this);  //添加监听变化监听事件
        getAgentShapeWear(mUserId);  //首次只获取塑身衣信息
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder = null;
        appBar.removeOnOffsetChangedListener(this);  //移除变化监听事件
    }

    //切换fragment时刷新数据*****
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !mIsGetData) {
            //   这里可以做网络请求或者需要的数据刷新操作
            mIsGetData = true;
            judgeLogin();
            // 切换fragment 之后回到当前页面重新请求数据
            getMarginStatus(mUserId);  //获取保证金
            getAgentData(mUserId);
            //LogUtils.d("保证金弹窗是显示的？=="+mMarginHintDialog.isShowing());
            if (mMarginHintDialog!=null&&mMarginHintDialog.isShowing()){
                mMarginHintDialog.dismiss();
            }
            LogUtils.d("666？"+enter);
        } else {
            mIsGetData = false;
            LogUtils.d("666"+enter);
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

   /* @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.d("onHiddenChanged"+hidden);
        if (!hidden){
            getAgentShapeWear();  //切换fragment 之后回到当前页面重新请求数据
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        //LogUtils.d("我的金薇onResume");
        //这里可以做网络请求或者需要的数据刷新操作
        mIsGetData = true;
        mIsTopShow = true;
        if (mMarginHintDialog!=null&&mMarginHintDialog.isShowing()){
            LogUtils.d("保证金弹窗是显示的？=="+mMarginHintDialog.isShowing());
            mMarginHintDialog.dismiss();
        }
        judgeLogin();  //判断是否登录
    }

    @Override
    public void onPause() {
        //LogUtils.d("我的金薇onPause");
        super.onPause();
        mIsGetData = false;
        mIsTopShow = false;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarOpen.setVisibility(View.VISIBLE);
            toolbarClose.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (255 * scale2);
            bgToolbarOpen.setBackgroundColor(Color.argb(alpha2, 243, 147, 165));
        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarClose.setVisibility(View.VISIBLE);
            toolbarOpen.setVisibility(View.GONE);
            float scale3 = (float) (scrollRange  - offset) / (scrollRange / 2);
            int alpha3 = (int) (255 * scale3);
            bgToolbarClose.setBackgroundColor(Color.argb(alpha3, 243, 147, 165));
        }
        //根据偏移百分比计算头像布局的透明度值
        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);
        bgContent.setBackgroundColor(Color.argb(alpha, 243, 147, 165));
    }

    /**
     * 动态添加塑身衣下单数据进度条,塑身衣等级名
     * @param number
     */
    private void addProgressBar(int number){
        LogUtils.d("添加控件数量=="+number);
        mProgressBar = new ProgressBar[number];
        mShapeWearLvName = new TextView[number+1];
        mShapeWearLvNumber = new TextView[number+1];
        LogUtils.d("当前屏幕宽=="+UserPrefs.getInstance().getWidth());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((UserPrefs.getInstance().getWidth()/number)/1.2),10);  //设置进度条的参数
        params.setMargins(0,0,5,0);
        LogUtils.d("params宽=="+params.width);
        LinearLayout.LayoutParams lvNameParams = new LinearLayout.LayoutParams((int) (UserPrefs.getInstance().getWidth()/number/1.5), //设置等级名及等级数量的参数
                WindowManager.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<number;i++){
            mProgressBar[i] = new ProgressBar(mContext,null,android.R.drawable.progress_indeterminate_horizontal);
            mProgressBar[i].setProgressDrawable(getResources().getDrawable(R.drawable.shape_wear_progressbar));
            mProgressBar[i].setLayoutParams(params);
            if (mLayoutLvProgressBar.getChildCount()<number)mLayoutLvProgressBar.addView(mProgressBar[i]);
        }
        LogUtils.d("添加后进度条的宽=="+mProgressBar[0].getWidth());
        for (int i=0;i<number+1;i++){
            mShapeWearLvName[i] = new TextView(mContext);
            mShapeWearLvNumber[i] = new TextView(mContext);
            mShapeWearLvName[i].setGravity(Gravity.CENTER);
            mShapeWearLvName[i].setTextSize(12);
            mShapeWearLvName[i].setLayoutParams(lvNameParams);
            if (mLayoutLvNickNameHint.getChildCount()<number+1)mLayoutLvNickNameHint.addView(mShapeWearLvName[i]);
            mShapeWearLvNumber[i].setGravity(Gravity.CENTER);
            mShapeWearLvNumber[i].setTextSize(12);
            mShapeWearLvNumber[i].setLayoutParams(lvNameParams);
            if (mLayoutLvNumberHint.getChildCount()<number+1)mLayoutLvNumberHint.addView(mShapeWearLvNumber[i]);
        }

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_COUNT:
                    mTvShapeWearNumber.setVisibility(View.INVISIBLE);
                    if (mProgressBar.length>0){
                        //设置塑身衣等级数量名
                        for (int i=0;i<mProgressBar.length;i++){
                            LogUtils.d("进度条的宽=="+mProgressBar[i].getWidth());
                            mProgressBar[i].setMax(Integer.valueOf(dataBeen.getSsyLevelList().get(i+1).getSsyLevelCondition())-Integer.valueOf(dataBeen.getSsyLevelList().get(i).getSsyLevelCondition()));
                            mShapeWearLvName[i].setText(dataBeen.getSsyLevelList().get(i).getSsyLevelName());
                            mShapeWearLvName[i].setX(mProgressBar[i].getX()-mShapeWearLvName[i].getWidth()/2);
                            mShapeWearLvNumber[i].setText(dataBeen.getSsyLevelList().get(i).getSsyLevelCondition());
                            mShapeWearLvNumber[i].setX(mProgressBar[i].getX()-mShapeWearLvNumber[i].getWidth()/2);
                        }
                        //设置进度条进度以及气泡的x坐标
                        mTvShapeWearNumber.setText(dataBeen.getSsyCount()+"");
                        for (int i=0;i<Data.size()-1;i++){
                            AgentDataResponse.DataBean.SsyLevelListBean data = Data.get(i);
                            AgentDataResponse.DataBean.SsyLevelListBean nextData = Data.get(i+1);
                            mProgressBar[i].setProgress(0);
                            if (dataBeen.getSsyCount()>=Integer.valueOf(data.getSsyLevelCondition())){  //如果下单数量大于第0个数组的标准数量(20套)
                                mTvShapeWearNumber.setVisibility(View.VISIBLE);
                                if (dataBeen.getSsyCount()>=Integer.valueOf(nextData.getSsyLevelCondition())){
                                    //LogUtils.d("进度=if="+((float)(dataBeen.getSsyCount()-Integer.valueOf(data.getSsyLevelCondition()))));
                                    //设置最后的进度条进度以及气泡的位置
                                    mProgressBar[i].setProgress(dataBeen.getSsyCount()-Integer.valueOf(data.getSsyLevelCondition()));
                                    mTvNextGrade.setText("您已达到最高级别");
                                    mTvShapeWearNumber.setVisibility(View.VISIBLE);
                                    if (i== Data.size()-2){
                                        mTvShapeWearNumber.setX( (mProgressBar[i].getX()+mProgressBar[i].getWidth()-20- mTvShapeWearNumber.getWidth()/2));
                                    }
                                }else {
                                    //设置前面的进度条进度以及气泡位置;
                                    //LogUtils.d("进度=else="+((float)(Integer.valueOf(nextData.getSsyLevelCondition())-Integer.valueOf(data.getSsyLevelCondition()))));
                                    LogUtils.d("当前进度=="+((float)Integer.valueOf(dataBeen.getSsyLevelList().get(i+1).getSsyLevelCondition())-
                                            Integer.valueOf(dataBeen.getSsyLevelList().get(i).getSsyLevelCondition())));
                                    //LogUtils.d("第一个进度条的最大值=="+((float)Integer.valueOf(dataBeen.getSsyLevelList().get(1).getSsyLevelCondition())-Integer.valueOf(dataBeen.getSsyLevelList().get(0).getSsyLevelCondition())));
                                    mProgressBar[i].setProgress(dataBeen.getSsyCount()-Integer.valueOf(data.getSsyLevelCondition())/
                                            (Integer.valueOf(nextData.getSsyLevelCondition())- Integer.valueOf(data.getSsyLevelCondition()))-
                                            Integer.valueOf(data.getSsyLevelCondition()));
                                    if (dataBeen.getSsyCount()==0){
                                        mTvNextGrade.setText("还差"+(nextData.getSsyLevelCondition())+"套到达下一级");
                                    }else {
                                        mTvNextGrade.setText("还差"+(Integer.valueOf(nextData.getSsyLevelCondition())-dataBeen.getSsyCount())+"套到达下一级");
                                    }
                                    mTvShapeWearNumber.setVisibility(View.VISIBLE);
                                    mTvShapeWearNumber.setX((float) (mProgressBar[i].getX()+mProgressBar[i].getWidth()*(mProgressBar[i].getProgress()/(
                                            (float)Integer.valueOf(dataBeen.getSsyLevelList().get(i+1).getSsyLevelCondition())-
                                                    Integer.valueOf(dataBeen.getSsyLevelList().get(i).getSsyLevelCondition())))-
                                            mTvShapeWearNumber.getWidth()/2));
                               /* LogUtils.d("进度条坐标="+mProgressBar[i].getX());
                                LogUtils.d("气泡坐标=="+mTvShapeWearNumber.getX());*/
                                }
                            }
                        }
                        //设置塑身衣最后一个的等级名数量
                        mShapeWearLvName[mShapeWearLvName.length-1].setText(dataBeen.getSsyLevelList().get(dataBeen.getSsyLevelList().size()-1).getSsyLevelName());
                        mShapeWearLvName[mShapeWearLvName.length-1].setX(mProgressBar[mProgressBar.length-1].getX()+mProgressBar[mProgressBar.length-1].getWidth()/2);
                        mShapeWearLvNumber[mShapeWearLvNumber.length-1].setText(dataBeen.getSsyLevelList().get(dataBeen.getSsyLevelList().size()-1).getSsyLevelCondition());
                        mShapeWearLvNumber[mShapeWearLvNumber.length-1].setX(mProgressBar[mProgressBar.length-1].getX()+mProgressBar[mProgressBar.length-1].getWidth()/2);
                    /*LogUtils.d("X坐标=="+mProgressBar[0].getX());
                    LogUtils.d("气泡宽=="+mTvShapeWearNumber.getWidth());
                    LogUtils.d("进度条宽=="+mProgressBar[0].getWidth());
                    LogUtils.d("进度=="+mProgressBar[0].getProgress());
                    LogUtils.d("X坐标=="+(mProgressBar[0].getX()+mProgressBar[0].getProgress()));
                    LogUtils.d("X坐标=*="+(mProgressBar[0].getX()+mProgressBar[0].getWidth()*(mProgressBar[0].getProgress()/100.0)));*/
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取用户信息
     */
    private void getAgentData(int userId){
        LogUtils.d("userId=="+userId);
        RetrofitClient.getInstances().getAgentData(userId).enqueue(new UICallBack<AgentDataResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                //LogUtils.d("userId=="+userId);
                if (mIsTopShow)checkNet();
            }

            @Override
            public void OnRequestSuccess(AgentDataResponse bean) {
               // LogUtils.d("userId=="+userId);
                switch (bean.getReturnValue()){
                    case 1:
                        if (mIsTopShow){
                            UserPrefs.getInstance().setIcon(bean.getData().getHeadPicture());  //代理头像
                            UserPrefs.getInstance().setUserName(bean.getData().getName());   //代理姓名
                            UserPrefs.getInstance().setLevel(bean.getData().getLevelName());  //代理等级
                            UserPrefs.getInstance().setCode(bean.getData().getCode());  //代理编号
                            UserPrefs.getInstance().setPhone(bean.getData().getPhone());  //代理手机号码
                            UserPrefs.getInstance().setWxi(bean.getData().getWeChat());  //代理微信号
                            UserPrefs.getInstance().setEmail(bean.getData().getEmail());  //代理人邮箱
                            UserPrefs.getInstance().setCorsetLevelName(bean.getData().getCorsetLevelName());  //代理塑身衣等级
                            if (bean.getData().getPromoteSize()!=0&&bean.getData().getPromoteSize()<99){
                                mTvApplyForHintNumber.setVisibility(View.VISIBLE);
                                mTvApplyForHintNumber.setText(""+bean.getData().getPromoteSize());
                            }else if (bean.getData().getPromoteSize()!=0 && bean.getData().getPromoteSize()>99){
                                mTvApplyForHintNumber.setVisibility(View.VISIBLE);
                                mTvApplyForHintNumber.setText("99+");
                            }else {
                                mTvApplyForHintNumber.setVisibility(View.INVISIBLE);
                            }
                            Data = bean.getData().getSsyLevelList();
                            dataBeen = bean.getData();
                            //显示并提示当前代理塑身衣等级
                            if (bean.getData().getCorsetLevelName()==null){
                                mRLayoutShapeWearLv.setVisibility(View.GONE);
                                mTvNowGrade.setText("当前等级:暂无");
                            }else {
                                //mTvShapeWearGrade.setText(bean.getData().getCorsetLevelName());
                                mTvNowGrade.setText("当前等级:"+bean.getData().getCorsetLevelName());
                                mLayoutShapeWearGradeHint.setVisibility(View.VISIBLE);
                                //mRLayoutShapeWearLv.setVisibility(View.VISIBLE);
                            }
                            /*if (bean.getData().getSsyCount()==0){
                                mTvNextGrade.setText("还差"+(bean.getData().getSsyLevelList().get(0).getSsyLevelCondition())+"套到达下一级");
                            }*/
                            LogUtils.d("时间=="+bean.getData().getDueTime());
                            if (bean.getData().getDueTime()==0 && bean.getData().getSsyYear()==0){
                                mLayoutCultivateMessage.setVisibility(View.GONE);
                                mLayoutCutOffMonth.setVisibility(View.GONE);
                            }
                            if (bean.getData().getDueTime()!=0){
                                mLayoutCultivateMessage.setVisibility(View.VISIBLE);
                                mLayoutCutOffDay.setVisibility(View.VISIBLE);
                                mTvCutOffDay.setVisibility(View.VISIBLE);
                                mTvCutOffDay.setText(" "+bean.getData().getDueTime());
                            }
                            if (bean.getData().getSsyYear()!=0){
                                mLayoutCultivateMessage.setVisibility(View.VISIBLE);
                                mLayoutCutOffMonth.setVisibility(View.VISIBLE);
                                mTvCupOffMonth.setVisibility(View.VISIBLE);
                                mTvCupOffMonth.setText(" "+bean.getData().getSsyYear()+"年"+bean.getData().getSsyMonth()+"月");
                            }
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    handler.sendEmptyMessage(HANDLER_COUNT) ;
                                }
                            },300);

                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }

            }
        });
        LogUtils.d("userId=="+userId);
    }


    /**
     * 获取代理塑身衣信息
     */
    private void getAgentShapeWear(int userId){
        RetrofitClient.getInstances().getAgentData(userId).enqueue(new UICallBack<AgentDataResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow){
                    checkNet();
                }
            }
            @Override
            public void OnRequestSuccess(AgentDataResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (mIsTopShow){
                            //LogUtils.d("获取塑身衣等级信息");
                            Data = bean.getData().getSsyLevelList();
                            dataBeen = bean.getData();
                            addProgressBar(bean.getData().getSsyLevelList().size()-1);
                            mLayoutShapeWearGradeHint.setVisibility(View.VISIBLE);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    handler.sendEmptyMessage(HANDLER_COUNT) ;
                                }
                            },300);
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }

            }
        });
        LogUtils.d("userId=="+userId);
    }

    //判断是否登录过
    private void judgeLogin(){
        //进入页面先判断是否登录
        if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())){ //没有登录
            mLayoutUserDetail.setVisibility(View.GONE);
            mLayoutLogin.setVisibility(View.VISIBLE);
            mLayoutMyOne.setVisibility(View.GONE);
            mLayoutMyTwo.setVisibility(View.GONE);
            mTvSet.setVisibility(View.VISIBLE);
            mIvQrCode.setVisibility(View.INVISIBLE);
            mTvPersonageFeedBack.setVisibility(View.GONE);
            mIvQrCodes.setVisibility(View.INVISIBLE);
            mIvMineIcon.setEnabled(false);
            Glide.with(mContext).load(R.mipmap.program_icon).error(R.mipmap.program_icon).into(mIvMineIcon);
        }else { //登录过
            getAgentData(mUserId);     //获取代理信息
            getMarginStatus(mUserId);  //获取保证金
            mLayoutUserDetail.setVisibility(View.VISIBLE);
            mTvPersonageName.setText(UserPrefs.getInstance().getNickName());
            mTvPersonageGrade.setText(UserPrefs.getInstance().getLevel());
            mLayoutLogin.setVisibility(View.GONE);
            mLayoutMyOne.setVisibility(View.VISIBLE);
            mLayoutMyTwo.setVisibility(View.VISIBLE);
            mTvSet.setVisibility(View.VISIBLE);
            mIvQrCode.setVisibility(View.VISIBLE);
            mTvPersonageFeedBack.setVisibility(View.VISIBLE);
            mIvQrCodes.setVisibility(View.VISIBLE);
            mIvMineIcon.setEnabled(true);
            Glide.with(mContext).load(UserPrefs.getInstance().getIcon()).error(R.mipmap.default_head).into(mIvMineIcon);
            getMyBalance(mUserId); //获取我的余额
        }
    }

    //点击事件
    @OnClick({R.id.tv_login,R.id.tv_set,R.id.tv_settings,R.id.iv_mine_icon, R.id.tv_personage_grade, R.id.tv_personage_address,
            R.id.tv_stay_pay,R.id.tv_stay_shipments,R.id.tv_shipments_loading,R.id.tv_order_over,R.id.tv_personage_bankCard,
            R.id.tv_personage_password, R.id.tv_personage_apply, R.id.layout_my_order,R.id.layout_my_balance,R.id.iv_qr_codes,
            R.id.tv_personage_sell_manager,R.id.tv_personage_performance, R.id.tv_personage_warehouse, R.id.tv_personage_pickup,
            R.id.tv_personage_size, R.id.tv_personage_certificate, R.id.iv_qr_code,R.id.layout_personage_apply_for_beg,
            R.id.tv_personage_centre,R.id.tv_personage_company, R.id.tv_personage_feedback,R.id.tv_personage_retail_recort,
            R.id.tv_personage_scan,R.id.tv_grade_rule,R.id.tv_margin_hint_tag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_set: //左上角 设置
                mActivityUtils.startActivity(RegardActivity.class);
                break;
            case R.id.tv_settings:  //折叠头部的 设置
                mActivityUtils.startActivity(RegardActivity.class);
                break;
            case R.id.iv_qr_code:  //右上角我的二维码
                //mActivityUtils.startActivity(ScanQrCodeActivity.class);  //扫描二维码界面
                getQRCode(mUserId);
                break;
            case R.id.iv_qr_codes:  //折叠头部的 我的二维码
                //mActivityUtils.startActivity(ScanQrCodeActivity.class);  //扫描二维码界面
                getQRCode(mUserId);
                break;
            case R.id.iv_mine_icon: //头像 -->进入个人中心
                //initQiYuService();  //客服
                mActivityUtils.startActivity(MyCenterActivity.class);
                break;
            case R.id.tv_personage_grade: //代理等级
                break;
            case R.id.tv_login: //登录
                mActivityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.layout_my_balance:  //我的余额
                mActivityUtils.startActivity(MyBalanceActivity.class);
                break;
            case R.id.tv_personage_address: //收货地址
                mActivityUtils.startActivity(LocationManagerActivity.class);
                break;
            case R.id.tv_personage_password: //修改密码
                mActivityUtils.startActivity(SelectAlterPasswordActivity.class);  //***可以选择修改支付密码还是登录密码
                break;
            case R.id.tv_personage_bankCard:  //我的银行卡
                mActivityUtils.startActivity(SelectBankCardActivity.class,"type","no");
                break;
            /*case R.id.tv_personage_apply:  //代理商申请
                mActivityUtils.startActivity(AgentApplyActivity.class);
                break;*/
            case R.id.layout_my_order:  //我的订单
                mActivityUtils.startActivity(MyOrderActivity.class);
                break;
            case R.id.tv_stay_pay: //待付款
                mActivityUtils.startActivity(MyOrderActivity.class,"style","stayPay");
                break;
            case R.id.tv_stay_shipments:  //待发货
                mActivityUtils.startActivity(MyOrderActivity.class,"style","stayShipments");
                break;
            case R.id.tv_shipments_loading: //发货中
                mActivityUtils.startActivity(MyOrderActivity.class,"style","stayLoading");
                break;
            case R.id.tv_order_over:  //已完成
                mActivityUtils.startActivity(MyOrderActivity.class,"style","stayOver");
                break;
            case R.id.tv_margin_hint_tag:  //保证金提示弹窗
                if (!TextUtils.isEmpty(marginHintMoney)&&!TextUtils.isEmpty(marginHintContent)){
                    showMarginHintDialog(mContext,marginHintMoney,marginHintContent);
                }
                break;
            case R.id.tv_grade_rule:  //塑身衣等级规则
                if (dataBeen.getSsyLevelList()!=null){  //如果请求到数据 展示有数据的弹窗
                    showDialogGradeRule(mContext,dataBeen.getSsyLevelList().size());
                }else {
                    showDialogGradeRule(mContext,0);  //否则展示没有数据的弹窗
                }
                break;
            case R.id.tv_personage_scan:  //扫一扫
                mActivityUtils.startActivity(ScanEntranceActivity.class);
                break;
            case R.id.layout_personage_apply_for_beg:  //下级代理升级请求
                mActivityUtils.startActivity(UpDataBegActivity.class);
                break;
            case R.id.tv_personage_sell_manager:  //销售管理
                mActivityUtils.startActivity(SellManageActivity.class);
                break;
            case R.id.tv_personage_warehouse:  //仓库管理
                mActivityUtils.startActivity(MyWarehouseActivity.class);
                break;
            case R.id.tv_personage_pickup:  //提货管理
                mActivityUtils.startActivity(PickUpGoodsActivity.class);
                break;
            case R.id.tv_personage_size:  //尺码调整
                mActivityUtils.startActivity(SizeAdjustActivity.class);
                break;
            case R.id.tv_personage_certificate:  //我的证书
                mActivityUtils.startActivity(SelectCredentialActivity.class);
                break;
            case R.id.tv_personage_performance:  //我的业绩
                mActivityUtils.startActivity(ResultsActivity.class);
                break;
            case R.id.tv_personage_retail_recort:
                mActivityUtils.startActivity(RetailRecordActivity.class);
                break;
            case R.id.tv_personage_centre:  //帮助中心
                mActivityUtils.startActivity(CommonIssueActivity.class);
                break;
            case R.id.tv_personage_company:  //公司制度
                mActivityUtils.startActivity(CompanySystemActivity.class);
                break;
            case R.id.tv_personage_feedback:  //意见反馈
                mActivityUtils.startActivity(FeedBackActivity.class,"type","feedback");
                //showShare();
                break;
        }
    }

    /**
     * 显示弹出塑身衣等级规则弹窗
     */
    private void showDialogGradeRule(Context context,int data){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_shape_wear_grade_rule,null);
        LinearLayout mLayoutRuleGrade = (LinearLayout) view.findViewById(R.id.layout_rule_grade);  //规则等级父布局
        LinearLayout mLayoutGradeRuleOne = (LinearLayout) view.findViewById(R.id.layout_grade_rule_one); //等级规则一
        LinearLayout mLayoutGradeRuleTwo = (LinearLayout) view.findViewById(R.id.layout_grade_two);  //等级规则二
        Button mBtnOkDismiss = (Button) view.findViewById(R.id.btn_ok_dismiss);  //关闭弹窗按钮
        if (data !=0){
            mRuleGrade = new TextView[data];
            mGradeRuleOne = new TextView[data];
            mGradeRuleTwo = new TextView[data];
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.setMargins(0,15,0,0);
            for (int i=0;i<dataBeen.getSsyLevelList().size(); i++){
                mRuleGrade[i] = new TextView(context);
                mGradeRuleOne[i] = new TextView(context);
                mGradeRuleTwo[i] = new TextView(context);
                mRuleGrade[i].setTextSize(13);
                mRuleGrade[i].setLayoutParams(params);
                mRuleGrade[i].setText(dataBeen.getSsyLevelList().get(i).getSsyLevelName());
                mGradeRuleOne[i].setTextSize(13);
                mGradeRuleOne[i].setLayoutParams(params);
                mGradeRuleOne[i].setText(dataBeen.getSsyLevelList().get(i).getDisposableGoods());
                mGradeRuleTwo[i].setTextSize(13);
                mGradeRuleTwo[i].setLayoutParams(params);
                mGradeRuleTwo[i].setText(dataBeen.getSsyLevelList().get(i).getSsyLevelCondition());
                mLayoutRuleGrade.addView(mRuleGrade[i]);
                mLayoutGradeRuleOne.addView(mGradeRuleOne[i]);
                mLayoutGradeRuleTwo.addView(mGradeRuleTwo[i]);
            }
        }else {

        }
        mShapeWearDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();
        Window window = mShapeWearDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //给弹窗设置一个透明背景这样就可以显示圆角的背景(自定义的shape)
        mShapeWearDialog.show();
        mShapeWearDialog.getWindow().setLayout(UserPrefs.getInstance().getWidth()-220,WindowManager.LayoutParams.WRAP_CONTENT); //设置弹窗的宽高
        mBtnOkDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShapeWearDialog.dismiss();
            }
        });

    }

    /**
     * 弹出保证金弹窗
     */
    private void showMarginHintDialog(Context context,String hintMoney,String hintContent){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_margin_hint,null);
        TextView mTvPresentMargin = (TextView) view.findViewById(R.id.tv_present_margin);
        TextView mTvMarginHint = (TextView) view.findViewById(R.id.tv_margin_hint_content);
        mLayoutPay = (LinearLayout) view.findViewById(R.id.layout_pay);
        mLayoutPayNo = (LinearLayout) view.findViewById(R.id.layout_no_pay);
        TextView mTvNoPay = (TextView) view.findViewById(R.id.tv_no_pay);  //暂不支付
        TextView mTvPromptlyPay = (TextView) view.findViewById(R.id.tv_promptly_pay); //立即支付
        TextView mTvIKnow = (TextView) view.findViewById(R.id.tv_i_know);
        if (!TextUtils.isEmpty(IsPayBound)&&IsPayBound.equals("0")){  //要缴纳保证金
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTvPresentMargin.setText(Html.fromHtml(hintMoney,Html.FROM_HTML_MODE_COMPACT));
                mTvMarginHint.setText(Html.fromHtml(hintContent,Html.FROM_HTML_MODE_COMPACT));
            }else {
                mTvPresentMargin.setText(Html.fromHtml(marginHintMoney));
                mTvMarginHint.setText(Html.fromHtml(marginHintContent));
            }
            mLayoutPay.setVisibility(View.VISIBLE);
        }else if (!TextUtils.isEmpty(IsPayBound)&&IsPayBound.equals("1")){  //无需缴纳保证金
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTvPresentMargin.setText(Html.fromHtml(hintMoney,Html.FROM_HTML_MODE_COMPACT));
                mTvMarginHint.setText("无需缴纳保证金");
            }else {
                mTvPresentMargin.setText(Html.fromHtml(marginHintMoney));
                mTvMarginHint.setText("无需缴纳保证金");
            }
            mLayoutPay.setVisibility(View.GONE);
            mLayoutPayNo.setVisibility(View.VISIBLE);
        }
        //mTableView = (TableView) view.findViewById(R.id.table_view);
        /*mTableView.setHeader(tableHead)  //表格功能取消 先注释掉
                .addContents(tableContent)
                .refreshTable();*/
        //mTvMarginHint.setText("您当前塑身衣等级是"+mBuilder+",需补缴"+builder+"保证金,请及时补缴保证金,以免影响正常提货。");
        //String content = "您当前塑身衣等级是<font color='#fd9a25'>"+mBuilder+"</font>,需补缴<font color='#fd9a25'>"+builder+"</font>保证金,请及时补缴保证金,以免影响正常提货。";
        mMarginHintDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();
        Window window = mMarginHintDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mMarginHintDialog.show();
        mMarginHintDialog.getWindow().setLayout(UserPrefs.getInstance().getWidth()-220,WindowManager.LayoutParams.WRAP_CONTENT);
        mTvNoPay.setOnClickListener(new View.OnClickListener() {  //暂不支付
            @Override
            public void onClick(View v) {
                mMarginHintDialog.dismiss();
            }
        });
        mTvIKnow.setOnClickListener(new View.OnClickListener() {  //我知道了
            @Override
            public void onClick(View v) {
                mMarginHintDialog.dismiss();
            }
        });
        mTvPromptlyPay.setOnClickListener(new View.OnClickListener() {  //立即支付
            @Override
            public void onClick(View v) {
                if (ActivityUtils.isCMBAppInstalled(mContext)){
                    mPayType = 1;
                    requestPayBound(mUserId,mPayType);
                }else {
                    mPayType = 0;
                    requestPayBound(mUserId,mPayType);
                }
            }
        });
    }


    /**
     * 支付保证金
     */
    private void requestPayBound(int userId, final int payType){
        PayBoundRequest request = new PayBoundRequest(userId,payType);
        RetrofitClient.getInstances().requestPayBound(request).enqueue(new UICallBack<CMBPayUrlResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow)checkNet();
            }

            @Override
            public void OnRequestSuccess(CMBPayUrlResponse bean) {
                if (mIsTopShow){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData()!=null){
                                if (payType==0){
                                    Intent intent = new Intent(getActivity(),CommonWebActivity.class);
                                    intent.putExtra("url",bean.getData());
                                    intent.putExtra("type","HTML");
                                    intent.putExtra("title","一网通支付");
                                    startActivity(intent);
                                }else if (payType ==1){
                                    final String payUrl = "cmbmobilebank://CMBLS/FunctionJump?action=gofuncid&funcid=200007&serverid=CMBEUserPay&requesttype=post&cmb_app_trans_parms_start=here&charset=utf-8&jsonRequestData="+
                                            bean.getData();
                                    LogUtils.d("支付链接=="+payUrl);
                                    Intent intent = new Intent();
                                    Uri uri = Uri.parse(payUrl);
                                    intent.setData(uri);
                                    intent.setAction("android.intent.action.VIEW");
                                    startActivity(intent);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    /**
     *获取保证金信息
     */
    private void getMarginStatus(int userId){
        RetrofitClient.getInstances().getMarginStatus(userId).enqueue(new UICallBack<MarginResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow) checkNet();
            }
            @Override
            public void OnRequestSuccess(MarginResponse bean) {
                if (mIsTopShow){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getData().getCurrentPayedBound()!=0){
                                mTvMarginHintTag.setText((bean.getData().getCurrentPayedBound()/100)+"");
                            }else {
                                mTvMarginHintTag.setText("0");
                            }
                            IsPayBound = String.valueOf(bean.getData().getIsPayBound());
                            String level= bean.getData().getLevelName();
                            String needPay = "¥"+String.valueOf(bean.getData().getNeedPayTotal()/100);
                            marginHintMoney = "当前保证金金额: <font color='#fd9a25'>"+(bean.getData().getCurrentPayedBound()/100)+"</font>";
                            marginHintContent = "您当前塑身衣等级是<font color='#fd9a25'>"+level+"</font>,需补缴<font color='#fd9a25'>"+needPay+"</font>保证金,请及时补缴保证金,以免影响正常提货。";
                        /*head = new String[bean.getData().getBoundInfo().size()+1];
                        content = new String[bean.getData().getBoundInfo().size()+1];
                        head[0] = "代理层级";
                        content[0] = "保证金金额";
                        for (int i=0;i<bean.getData().getBoundInfo().size();i++){
                            head[i+1] = bean.getData().getBoundInfo().get(i).getName();
                            content[i+1] = String.valueOf(bean.getData().getBoundInfo().get(i).getBoundMoney()/100);
                        }
                        tableHead.add(head);
                        tableContent.add(content);*/
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }


    //判断用户是否有二维码
    private void getQRCode(int userId){
        RetrofitClient.getInstances().getQRCode(userId).enqueue(new UICallBack<QRCodeResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow) checkNet();
            }
            @Override
            public void OnRequestSuccess(QRCodeResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (mIsTopShow){
                            Intent intent = new Intent(getActivity(),MyQrCodeActivity.class);
                            intent.putExtra("codeUrl",bean.getCode());
                            intent.putExtra("shareUrl",bean.getShareUrl());
                            intent.putExtra("title",bean.getTitle());
                            intent.putExtra("context",bean.getContext());
                            startActivity(intent);
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }

            }
        });
    }

    //获取我的余额
    private void getMyBalance(final int userId){
        RetrofitClient.getInstances().getMyBalance(userId).enqueue(new UICallBack<BalanceResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (mIsTopShow){
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(BalanceResponse bean) {

                switch (bean.getReturnValue()){
                    case 1:
                        if (mIsTopShow)mTvBalanceMoney.setText(ActivityUtils.changeMoneys(bean.getData().getCurrentBalance())+"元");
                        break;
                    default:

                        break;
                }
            }
        });
    }



}
