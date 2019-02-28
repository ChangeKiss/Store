package com.Store.www.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.AnnularView;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BonusQueryResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.BonusQueryAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;

/**
 * 分红查询界面
 */
public class BonusQueryActivity extends BaseToolbarActivity implements BonusQueryAdapter.OnItemClickListener{
    @BindView(R.id.lv_bonus)
    LRecyclerView mLvBonus;  //分红查询列表

    LRecyclerViewAdapter lRecyclerViewAdapter;
    BonusQueryAdapter mAdapter;  //分红查询的适配器

    TextView mTvPeopleMonthGrade;  //几月的业绩
    TextView mTvBonusTotal;   //分红总金额
    TextView mTvBraGrade;  //内衣业绩
    TextView mTvRateOfContribution;  //贡献率
    TextView mTvPeopleMoneys;  //个人分红
    TextView mTvHistoryBraNumber;  //历史内衣数量
    TextView mTvTeamBonus;   //团队分红
    TextView mTvTeamLookDetail;  //团队分红查看明细
    TextView mTvHistoryBonusTotalMoney;  //历史分红总金额
    AnnularView mAvAnnular;  //自定义占比图
    CommonHeader mCommonHeader;  //头布局
    private String mMonth;  //查看详情需要的月份参数



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_bonus_query;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this,true,R.string.bonus_inquire);
        initCommonHeader();  //初始化头布局
        initAdapter();  //初始化适配器
    }

    //初始化头布局
    private void initCommonHeader(){
        mCommonHeader = new CommonHeader(mContext,R.layout.layout_bonus_query_head);  //分红详情界面的头布局
        mTvPeopleMonthGrade = (TextView) mCommonHeader.findViewById(R.id.tv_people_month_grade);  //某月的分红
        mTvBonusTotal = (TextView) mCommonHeader.findViewById(R.id.tv_bonus_total);  //分红总金额
        mTvBraGrade = (TextView) mCommonHeader.findViewById(R.id.tv_bra_grade);  //内衣业绩
        mTvRateOfContribution = (TextView) mCommonHeader.findViewById(R.id.tv_rate_of_contribution);  //贡献率
        mTvPeopleMoneys = (TextView) mCommonHeader.findViewById(R.id.tv_people_moneys);  //个人分红
        mTvHistoryBraNumber = (TextView) mCommonHeader.findViewById(R.id.tv_history_bra_number);  //历史内衣数量
        mTvTeamBonus = (TextView) mCommonHeader.findViewById(R.id.tv_team_bonus);  //团队分红
        mTvTeamLookDetail = (TextView) mCommonHeader.findViewById(R.id.tv_team_look_detail);  //团队分红查看详情
        mTvHistoryBonusTotalMoney = (TextView) mCommonHeader.findViewById(R.id.tv_history_bonus_total_money);  //历史分红总金额
        mAvAnnular = (AnnularView) mCommonHeader.findViewById(R.id.av_annular);  //自定义占比图
        mAvAnnular.setPercentage(50,50);   //给环形占比图设置初始值
        mTvTeamLookDetail.setOnClickListener(this);  //添加点击事件

    }



    //初始化分红查询适配器
    private void initAdapter(){
        mAdapter = new BonusQueryAdapter(this,this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        lRecyclerViewAdapter.addHeaderView(mCommonHeader);
        mLvBonus.setLayoutManager(new LinearLayoutManager(this));
        mLvBonus.setAdapter(lRecyclerViewAdapter);
        mLvBonus.setPullRefreshEnabled(false); //关闭下拉刷新
        getBonusData(UserPrefs.getInstance().getAgentCode());
        //getBonusData("JWD0723371");  //代理编号写死 测试用
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;  //isTop参数用来判断当前Activity是否在最上层 或者说是否可见
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;  //isTop参数用来判断当前Activity是否在最上层 或者说是否可见
    }

    //获取分红数据
    private void getBonusData(String agentId){
        RetrofitClient.getInstances().queryBonus(agentId).enqueue(new UICallBack<BonusQueryResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){  //isTop参数用来判断当前Activity是否在最上层 或者说是否可见
                    checkNet();
                }
            }

            @Override
            public void OnRequestSuccess(BonusQueryResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){   //isTop参数用来判断当前Activity是否在最上层 或者说是否可见
                            mMonth = bean.getData().getChdMonth();
                            mTvPeopleMonthGrade.setText(bean.getData().getChd());  //当前月份的分红提示语句
                            mTvBonusTotal.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getTotalBonus()));  //分红总金额
                            mTvBraGrade.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getMonthAchievement()));  //内衣业绩
                            mTvRateOfContribution.setText(bean.getData().getRate()/100+"");  //贡献率
                            mTvPeopleMoneys.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getPerBonus()));  //个人分红
                            mTvHistoryBraNumber.setText(bean.getData().getHistoryBraNum()+"");  //历史内衣数量
                            mTvTeamBonus.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getTeamBonus()));  //团队分红
                            mTvHistoryBonusTotalMoney.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getHistoryTotal()));  //历史总金额
                            mAvAnnular.setCircleWidth(60);  //设置环形图的宽度
                            LogUtils.d("个人分红"+bean.getData().getHistoryPerBonus());
                            LogUtils.d("团队分红"+bean.getData().getHistoryTeamBonus());
                            mAvAnnular.setPeopleBonus(bean.getData().getHistoryPerBonus());
                            mAvAnnular.setTeamBonus(bean.getData().getHistoryTeamBonus());
                            if (bean.getData().getHistoryPerBonus()==0 && bean.getData().getHistoryTeamBonus()==0){  //如果没有分红
                                mAvAnnular.setPercentage(50,50);   //给环形占比图设置初始值   各占一半
                            }else {  //否则 按实际分红来设置
                                mAvAnnular.setPercentage(bean.getData().getHistoryPerBonus(),bean.getData().getHistoryTeamBonus());
                            }
                            mAdapter.setDataList(bean.getData().getList());
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }






    //查看明细的点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_team_look_detail:
                mActivityUtils.startActivity(BonusDetailActivity.class,"title",mMonth);
                break;
            case R.id.iv_toolbar_left:
                finish();
                break;
        }
    }


    //查看月份明细的点击事件
    @Override
    public void LookDetailClickListener(int position, LinearLayout layout,ImageView imageView) {
        if (layout.getVisibility()==View.GONE){
            imageView.setImageResource(R.mipmap.grey_sjt_icon);
            layout.setVisibility(View.VISIBLE);
        }else if (layout.getVisibility()==View.VISIBLE){
            imageView.setImageResource(R.mipmap.grey_xjt_icon);
            layout.setVisibility(View.GONE);
        }
    }

    //查看分红详情的点击数事件
    @Override
    public void LookBonusDetailClickListener(int position, String month) {
        mActivityUtils.startActivity(BonusDetailActivity.class,"title",month);
    }




}
