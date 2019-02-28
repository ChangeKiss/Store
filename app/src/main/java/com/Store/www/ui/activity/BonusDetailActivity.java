package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BonusDetailResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.BonusDetailAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;

/**
 * 分红详情界面
 */
public class BonusDetailActivity extends BaseToolbarActivity {
    @BindView(R.id.nodata)
    RelativeLayout mNoData;
    @BindView(R.id.lv_bonus_detail)
    LRecyclerView mLrBonusDetail;  //详情列表

    CommonHeader mPopCommonHeader;   //弹窗的头布局
    TextView mTvBonusDetailName;     //弹窗的姓名标题
    TextView mTvBonusDetailGrade;    //弹窗的业绩标题
    TextView mTvBonusDetailRate;     //弹窗的费率标题
    TextView mTvBonusDetailBonus;    //弹窗的分红标题
    LinearLayout.LayoutParams params;  //设置布局的宽度
    BonusDetailAdapter mDetailAdapter;  //详情适配器
    LRecyclerViewAdapter mViewAdapter;

    private String mTitle;  //标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_bonus_detail;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mTitle = getIntent().getStringExtra("title");
        initToolbar(this,true,mTitle+"月分红详情");
        initHead();
        initAdapter();
    }

    //初始化头布局
    private void initHead(){
        mPopCommonHeader = new CommonHeader(mContext,R.layout.layout_bonus_detail_head);  //分红详情弹窗的头布局
        mPopCommonHeader.setVisibility(View.GONE);
        mTvBonusDetailName = (TextView) mPopCommonHeader.findViewById(R.id.tv_bonus_detail_name);   //弹窗的姓名标题
        mTvBonusDetailGrade = (TextView) mPopCommonHeader.findViewById(R.id.tv_bonus_detail_grade); //弹窗的业绩标题
        mTvBonusDetailRate = (TextView) mPopCommonHeader.findViewById(R.id.tv_bonus_detail_rate);   //弹窗的费率标题
        mTvBonusDetailBonus = (TextView) mPopCommonHeader.findViewById(R.id.tv_bonus_detail_bonus); //弹窗的分红标题
        //设置头布局的标题的宽度
        params = (LinearLayout.LayoutParams) mTvBonusDetailName.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvBonusDetailGrade.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvBonusDetailRate.getLayoutParams();
        params = (LinearLayout.LayoutParams) mTvBonusDetailBonus.getLayoutParams();
        params.width = (UserPrefs.getInstance().getWidth()/4); //设置宽占屏幕宽的4分之一
        mTvBonusDetailName.setLayoutParams(params);
        mTvBonusDetailGrade.setLayoutParams(params);
        mTvBonusDetailRate.setLayoutParams(params);
        mTvBonusDetailBonus.setLayoutParams(params);
    }

    //初始化适配器
    private void initAdapter(){
        mDetailAdapter = new BonusDetailAdapter(this);
        mViewAdapter = new LRecyclerViewAdapter(mDetailAdapter);
        mViewAdapter.addHeaderView(mPopCommonHeader);  //添加头布局
        mLrBonusDetail.setLayoutManager(new LinearLayoutManager(this));  //添加布局管理器
        mLrBonusDetail.setAdapter(mViewAdapter);  //添加适配器
        mLrBonusDetail.setPullRefreshEnabled(false);  //关闭下拉刷新
        //getBonusDetail("JWD0723371", Integer.parseInt(mTitle));  //标题就是接口需要的月份参数  代码编号先用写死的 测试
        if (!TextUtils.isEmpty(mTitle)){   //
            getBonusDetail(UserPrefs.getInstance().getAgentCode(), Integer.parseInt(mTitle));  //标题就是接口需要的月份参数
        }
    }

    //获取弹窗分红详情数据
    private void getBonusDetail(String agentCode,int month){
        DialogLoading.shows(mContext,"加载中...");
        RetrofitClient.getInstances().getBonusDetail(agentCode,month).enqueue(new UICallBack<BonusDetailResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BonusDetailResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (bean.getData().size()==0){
                            mNoData.setVisibility(View.VISIBLE);
                        }else {
                            mNoData.setVisibility(View.GONE);
                            mPopCommonHeader.setVisibility(View.VISIBLE);
                            mDetailAdapter.setDataList(bean.getData());
                            mDetailAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:

                        showToast(bean.getErrMsg());
                        break;
                }

            }
        });

    }
}
