package com.Store.www.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.BubbleView.BubbleTextView;
import com.Store.www.entity.MyTeamDetailResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的团队界面
 */
public class MyTeamActivity extends BaseToolbarActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;  //返回键
    @BindView(R.id.tv_month)
    TextView mTvMonth;  //当前月份
    @BindView(R.id.cv_team_leader_head)
    CircleImageView mCvTeamLeaderHead;  //团队领导的头像
    @BindView(R.id.tv_team_leader_name)
    TextView mTvTeamLeaderName; //团队领导姓名
    @BindView(R.id.tv_team_people_total)
    TextView mTvTeamPeopleTotal;  //团队总人数
    @BindView(R.id.tv_team_add_people)
    BubbleTextView mTvTeamAddPeople;  //团队新增人数
    @BindView(R.id.tv_bra_ok_check_grade)
    TextView mTvBraOkCheckGrade;  //内衣已审核业绩
    @BindView(R.id.tv_bra_no_check_grade)
    TextView mTvBraNoCheckGrade;  //内衣未审核业绩
    @BindView(R.id.tv_shapeWear_ok_check_grade)
    TextView mTvShapeWearOkCheckGrade;  //塑身衣已审核业绩
    @BindView(R.id.tv_shapeWear_no_check_grade)
    TextView mTvShapeWearNoCheckGrade;  //塑身衣未审核业绩

    LinearLayout.LayoutParams params;
    private TimePickerView timePickerView;
    private String SelectData =" ";  //选择的时间
    private String SelectYear=" ";  //选择的年
    private String SelectMonth=" "; //选择的月

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_team;
    }

    @Override
    public void initView() {
        SelectData = UserPrefs.getInstance().getYear()+"-"+UserPrefs.getInstance().getMonth();
        SelectYear = UserPrefs.getInstance().getYear()+"";
        SelectMonth = UserPrefs.getInstance().getMonth();
        mTvMonth.setText(SelectYear+"年"+SelectMonth+"月");
        Glide.with(mContext).load(UserPrefs.getInstance().getIcon()).error(R.mipmap.default_head).into(mCvTeamLeaderHead);
        mTvTeamLeaderName.setText(UserPrefs.getInstance().getNickName());
        showTimeSelector();
        getMyTeamDetail(mUserId,SelectMonth,SelectYear);
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

    //获取我的团队详情
    private void getMyTeamDetail(int agentId,String month,String year){
        DialogLoading.shows(mContext,"加载中...");
        RetrofitClient.getInstances().getMyTeamDetail(agentId,month,year).enqueue(new UICallBack<MyTeamDetailResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(MyTeamDetailResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mTvTeamPeopleTotal.setText("总人数"+bean.getData().getSubordinateCount());
                            mTvTeamAddPeople.setText("↑"+bean.getData().getNewNumber());  //新增人数
                            mTvBraOkCheckGrade.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getAuditedMoney()));  //内衣已审核
                            mTvBraNoCheckGrade.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getUnauditedMoney())); //内衣未审核
                            //mTvShapeWearOkCheckGrade.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getAuditedCorsetMoney()));  //塑身衣已审核
                            //mTvShapeWearOkCheckGrade.setText("¥ "+ActivityUtils.changeMoneys(bean.getData().getUnauditedCorsetMoney()));  //塑身衣未审核
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_calendar, R.id.layout_everyday_team_grade, R.id.layout_direct_subordinate_grade, R.id.layout_everyday_attract_people, R.id.layout_direct_subordinate_team_status, R.id.layout_people_distribution, R.id.layout_subordinate_people_distribution})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:  //返回键
                finish();
                break;
            case R.id.iv_calendar:  //选择年月日历
                timePickerView.show();
                break;
            case R.id.layout_everyday_team_grade: //每日团队业绩
                mActivityUtils.startActivity(EveryDayGradeActivity.class,"data",SelectData);
                break;
            case R.id.layout_direct_subordinate_grade:  //直属下级团队业绩
                mActivityUtils.startActivity(SubordinateGradeActivity.class,"data",SelectData);
                break;
            case R.id.layout_everyday_attract_people:  //每日新招人数
                mActivityUtils.startActivity(EveryDayNewPeopleActivity.class,"data",SelectData);
                break;
            case R.id.layout_direct_subordinate_team_status:  //直属下级各团队人员状态
                mActivityUtils.startActivity(SubordinateTeamPeopleActivity.class,"data",SelectData);
                break;
            case R.id.layout_people_distribution:  //各等级人员分布
                mActivityUtils.startActivity(EachLevelPeopleActivity.class);
                break;
            case R.id.layout_subordinate_people_distribution:  //直属下级各团队等级人员分布
                mActivityUtils.startActivity(SubordinateTeamPeopleDistributionActivity.class);
                break;
        }
    }


    //时间选择器初始化
    private void showTimeSelector() {
        Calendar selectData = Calendar.getInstance();//  获取当前系统时间 用来作为终止时间的范围
        Calendar startData = Calendar.getInstance();  //获取当前系统时间
        startData.set(2018, 10, 0);
        Calendar endData = Calendar.getInstance();  //获取当前系统时间
        endData.set(3000, 11, 31);
        timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                LogUtils.d("当前选中的年==" + ActivityUtils.getYear(date));
                SelectData =ActivityUtils.getYear(date)+"-"+ String.valueOf(ActivityUtils.getMonth(date));
                /*SelectYear = ActivityUtils.getYear(date)+"";
                SelectMonth = ActivityUtils.getMonth(date)+"";*/
                //LogUtils.d("当前选中的月=="+ SelectData);
                mTvMonth.setText(ActivityUtils.getYear(date)+"年"+ String.valueOf(ActivityUtils.getMonth(date))+"月");
                getMyTeamDetail(mUserId,String.valueOf(ActivityUtils.getMonth(date)),String.valueOf(ActivityUtils.getYear(date)));
            }
        })
                .setDate(selectData)
                .setRangDate(startData, selectData)
                .setLayoutRes(R.layout.layout_team_time_select, new CustomListener() {
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
                .isCyclic(false)  //循环滚动
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFFbdbdbd)  //设置分割线的颜色
                .setTextColorCenter(0xfff393a5)  //选中的文字的颜色
                .isDialog(true) //设置为弹窗模式
                .build();

    }

}
