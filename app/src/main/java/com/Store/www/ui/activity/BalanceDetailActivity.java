package com.Store.www.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BalanceDetailResponse;
import com.Store.www.entity.BalanceResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.BalanceDetailTwoAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * 余额详情界面  查看收入/支出
 */
public class BalanceDetailActivity extends BaseToolbarActivity implements BalanceDetailTwoAdapter.OnItemClickListener{
    @BindView(R.id.iv_toolbar_right)
    TextView mTvToolbarRight;  //标题栏顶部右侧的提现按钮
    @BindView(R.id.lr_balance)
    LRecyclerView mLrBalance;  //余额列表

    LinearLayout.LayoutParams params;
    private TimePickerView timePickerView;
    private CommonHeader commonHeader;  //头布局
    private TextView mTvBalanceMonth;  //显示月份
    private TextView mTvBalanceTotalMoney;  //显示金额
    private LinearLayout mLayoutSelectMonth;  //头布局选择月份
    BalanceDetailTwoAdapter mAdapter;  //余额适配器
    LRecyclerViewAdapter viewAdapter;
    Window window;
    private int mType;  //用来判断类型是收入 还是支出
    private int month, year, day;
    private int totalBalance;  //总余额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_balance_detail;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mType = getIntent().getIntExtra("type", 0);
        mTvToolbarRight.setVisibility(View.VISIBLE);  //标题栏右侧的提现按钮显示出来
        mTvToolbarRight.setText(R.string.withdraw_deposit);
        initHead();//初始化头布局
        showTimeSelector(); //  初始化时间选择器
        window = timePickerView.getDialog().getWindow();  //获取时间选择器对象
        WindowManager.LayoutParams manager = window.getAttributes();
        manager.x = 0;
        manager.y = -320; //设置dialog 弹出位置Y轴偏移量
        timePickerView.getDialog().onWindowAttributesChanged(manager);  //设置选择器弹出位置
        year = UserPrefs.getInstance().getYear();
        month = Integer.parseInt(UserPrefs.getInstance().getMonth());
        mLrBalance.setPullRefreshEnabled(false);  //关闭下拉刷新
        //mLrBalance.setLoadMoreEnabled(false); //关闭上拉加载
        if (mType == 1) {  //如果类型是收入
            initToolbar(this, true, R.string.income);
        } else if (mType == 0) {  //如果类型是消费 支出
            initToolbar(this, true, R.string.expend);
        }
        getBalanceDetail(mUserId, month, year, mType);
        getMyBalance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("applyOver");
        registerReceiver(new BroadCast(),intentFilter);
    }

    //注册广播并接收
    class BroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo.isAvailable() && networkInfo!=null){
                if (intent.getAction().equals("applyOver")){
                    finish();
                }
            }
        }
    }

    //初始化头布局
    private void initHead() {
        commonHeader = new CommonHeader(mContext, R.layout.layout_balance_head); //头布局
        mLayoutSelectMonth = (LinearLayout) commonHeader.findViewById(R.id.layout_select_month);  //选择月份按钮
        mTvBalanceMonth = (TextView) commonHeader.findViewById(R.id.tv_balance_detail_month);  //显示当前月份余额
        mTvBalanceTotalMoney = (TextView) commonHeader.findViewById(R.id.tv_this_month_money);  //显示当前月份余额总金额
        mLayoutSelectMonth.setOnClickListener(this);  //注册点击事件
        mAdapter = new BalanceDetailTwoAdapter(this,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(commonHeader);  //添加头布局
        mLrBalance.setLayoutManager(new LinearLayoutManager(this));  //添加布局管理器
        mLrBalance.setAdapter(viewAdapter);

    }


    //获取我的余额
    private void getMyBalance() {
        RetrofitClient.getInstances().getMyBalance(mUserId).enqueue(new UICallBack<BalanceResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                LogUtils.d("获取余额");
                checkNet();
            }
            @Override
            public void OnRequestSuccess(BalanceResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        totalBalance = bean.getData().getUsableBalance();  //可用金额 用于提现
                        break;
                    default:

                        break;
                }
            }
        });
    }

    //请求余额详情
    private void getBalanceDetail(int userId, int month, int year, int type) {
        RetrofitClient.getInstances().getBalanceDetail(userId, month, year, type).enqueue(new UICallBack<BalanceDetailResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BalanceDetailResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        if (mType == 1) {
                            mAdapter.setmType("income");
                            mTvBalanceTotalMoney.setText("收入¥" + ActivityUtils.changeMoneys(bean.getData().getTotalMoney()));
                        } else if (mType == 0) {
                            mAdapter.setmType("expend");
                            mTvBalanceTotalMoney.setText("支出¥" + ActivityUtils.changeMoneys(bean.getData().getTotalMoney()));
                        }
                        mTvBalanceMonth.setText(bean.getData().getMonth() + "月");
                        mAdapter.getDataList().clear();  //请求成功 先清空数据
                        mAdapter.addAll(bean.getData().getDetails());  //加载数据
                        mAdapter.notifyDataSetChanged();  //刷新适配器
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_select_month:
                timePickerView.show();  //显示时间选择器
                break;
            case R.id.iv_toolbar_right:  //提现按钮
                mActivityUtils.startActivity(WithdrawMoneyInputActivity.class,"balance",totalBalance);
                break;
            case R.id.iv_toolbar_left:  //左上角的返回键
                finish();
                break;
        }

    }


    //时间选择器初始化
    private void showTimeSelector() {
        Calendar selectData = Calendar.getInstance();//  获取当前系统时间 用来作为终止时间的范围
        Calendar startData = Calendar.getInstance();  //获取当前系统时间
        startData.set(2017, 0, 1);
        Calendar endData = Calendar.getInstance();  //获取当前系统时间
        endData.set(3000, 11, 31);
        timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                LogUtils.d("当前选中的年==" + ActivityUtils.getYear(date));
                LogUtils.d("当前选中的月==" + ActivityUtils.getMonth(date));
                getBalanceDetail(mUserId, ActivityUtils.getMonth(date), ActivityUtils.getYear(date), mType);
            }
        })
                .setDate(selectData)
                .setRangDate(startData, selectData)
                .setLayoutRes(R.layout.layout_time_select, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView submit = (TextView) v.findViewById(R.id.tv_submit);
                        TextView cancel = (TextView) v.findViewById(R.id.tv_cancel);
                        params = (LinearLayout.LayoutParams) submit.getLayoutParams();
                        params = (LinearLayout.LayoutParams) cancel.getLayoutParams();
                        params.width = UserPrefs.getInstance().getWidth() / 2;  //设置两个按钮的宽度 个占屏幕宽的二分之一
                        submit.setLayoutParams(params);
                        cancel.setLayoutParams(params);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timePickerView.returnData();
                                timePickerView.dismiss();
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timePickerView.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(16)  //设置选中项文字的大小
                .setType(new boolean[]{true, true, false, false, false, false})  //此处只需显示年月
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(2.0f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFFbdbdbd)  //设置分割线的颜色
                .setTextColorCenter(0xfff393a5)  //选中的文字的颜色
                .isDialog(true) //设置为弹窗模式
                .build();

    }

    //Item的点击事件
    @Override
    public void ItemClickListener(int position, int id,int type) {
        Intent intent = new Intent(this,AccountBalanceDetailActivity.class);  //点击查看账户余额明细
        intent.putExtra("id",id);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
