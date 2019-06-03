package com.Store.www.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.ChartView.DistributionBarCharMarkerView;
import com.Store.www.base.ChartView.MyAxisValueFormatters;
import com.Store.www.base.ChartView.MyXFormatter;
import com.Store.www.entity.SubordinateTeamPeopleDistributionResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SubordinateTeamPeopleDistributionActivity extends BaseToolbarActivity {
    @BindView(R.id.distribution_chart)
    BarChart mDistributionChart;   //分布图
    @BindView(R.id.layout_no_data)
    RelativeLayout mLayoutNoData;
    @BindView(R.id.iv_left_distribution)
    ImageView mIvLeftDistribution;  //左切换按钮
    @BindView(R.id.iv_right_distribution)
    ImageView mIvRightDistribution;  //右切换按钮

    DecimalFormat mFormat = new DecimalFormat("##0");
    List<SubordinateTeamPeopleDistributionResponse.DataListBean.SmallBean> Bean = new ArrayList<>();
    SubordinateTeamPeopleDistributionResponse.DataListBean.SmallBean smallBean;
    private float[] valuesFloat ;
    DistributionBarCharMarkerView markerView;
    public static ArrayList<BarEntry> mEntry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_subordinate_team_people_distribution;
    }

    @Override
    public void initView() {
        initToolbar(this, true, "直属下级各团队等级人员分布");
        ActivityCollector.addActivity(this);
        markerView = new DistributionBarCharMarkerView(mContext,R.layout.layout_bar_chart_distribution_markerview);
        mDistributionChart.getDescription().setEnabled(false);
        // 扩展现在只能分别在x轴和y轴
        mDistributionChart.setPinchZoom(false);
        mDistributionChart.setDrawGridBackground(false);
        mDistributionChart.setDrawBarShadow(false);
        mDistributionChart.setDrawValueAboveBar(false);
        mDistributionChart.setHighlightFullBarEnabled(false);
        mDistributionChart.setMaxVisibleValueCount(8);
        mDistributionChart.animateXY(8000, 8000);
        mCountPerPage = 8;

        // 改变y标签的位置
        YAxis leftAxis = mDistributionChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatters());
        leftAxis.setAxisMinimum(1f);  //设置Y轴坐标最小显示单位
        leftAxis.setSpaceTop(50f);
        mDistributionChart.getAxisRight().setEnabled(false);
        mDistributionChart.getXAxis().setLabelRotationAngle(-45);
        mDistributionChart.getAxisLeft().setEnabled(false);
        Legend l = mDistributionChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
        getTeamPeopleDistribution(mUserId, mPageIndex, mCountPerPage);
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

    //获取直属下级各团队人员分布
    private void getTeamPeopleDistribution(int agentId, int pageIndex, int countPerPage) {
        DialogLoading.shows(this, "加载中...");
        RetrofitClient.getInstances().getSubordinationTeamDistribution(agentId, pageIndex, countPerPage).enqueue(new UICallBack<SubordinateTeamPeopleDistributionResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(SubordinateTeamPeopleDistributionResponse bean) {
                if (isTop) {
                    switch (bean.getReturnValue()) {
                        case 1:
                            mLayoutNoData.setVisibility(View.GONE);
                            mDistributionChart.setVisibility(View.VISIBLE);
                            mIvLeftDistribution.setVisibility(View.VISIBLE);
                            mIvRightDistribution.setVisibility(View.VISIBLE);
                            XAxis xAxis = mDistributionChart.getXAxis();
                            ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
                            //LogUtils.d("外层数据长度01==" + bean.getDataList().size());
                            //LogUtils.d("外层数据长度02==" + bean.getDataList().get(0).getSmall().size());
                            if (bean.getDataList().size()==0){
                                showToast("没有更多数据了");
                                return;
                            }else {
                                final String[] values = new String[bean.getDataList().size()];
                                final String[] hintValue = new String[bean.getDataList().get(0).getSmall().size()];

                                for (int a = 0; a < bean.getDataList().get(0).getSmall().size(); a++) {
                                    hintValue[a] = bean.getDataList().get(0).getSmall().get(a).getLevelName();
                                }

                                for (int k =0;k<bean.getDataList().size();k++){
                                    values[k] = bean.getDataList().get(k).getAgentName();
                                    valuesFloat = new float[hintValue.length];
                                    for (int s=0;s<hintValue.length;s++){
                                        valuesFloat[s] = bean.getDataList().get(k).getSmall().get(s).getCount();
                                    }
                                    yVals.add(new BarEntry(k,valuesFloat));
                                }

                                mEntry = yVals;
                                xAxis.setValueFormatter(new MyXFormatter(values));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setAvoidFirstLastClipping(false);
                                BarDataSet set1;

                                if (mDistributionChart.getData() != null &&
                                        mDistributionChart.getData().getDataSetCount() > 0) {
                                    set1 = (BarDataSet) mDistributionChart.getData().getDataSetByIndex(0);
                                    set1.setValues(yVals);
                                    mDistributionChart.getData().notifyDataChanged();
                                    mDistributionChart.notifyDataSetChanged();
                                } else {
                                    set1 = new BarDataSet(yVals, "");
                                    set1.setColors(new int[]{Color.parseColor("#4edee0"), Color.parseColor("#5cabe3"),
                                            Color.parseColor("#ffb980"), Color.parseColor("#f393a5"), Color.parseColor("#b6a2de")});
                                    set1.setStackLabels(hintValue);
                                    ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                                    dataSets.add(set1);
                                    BarData data = new BarData(dataSets);
                                    data.setValueFormatter(new IValueFormatter() {
                                        @Override
                                        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                                            if (value == 0.0 || value == 0) {
                                                return "";
                                            } else {
                                                return "" + mFormat.format(value);
                                            }
                                        }
                                    });
                                    data.setValueTextColor(Color.WHITE);
                                    data.setValueTypeface(Typeface.DEFAULT_BOLD);
                                    mDistributionChart.setData(data);
                                }
                                mDistributionChart.setMarker(markerView);
                                mDistributionChart.setFitBars(false);
                                mDistributionChart.invalidate();
                            }
                            break;
                        default:
                            mLayoutNoData.setVisibility(View.VISIBLE);
                            mDistributionChart.setVisibility(View.INVISIBLE);
                            mIvLeftDistribution.setVisibility(View.GONE);
                            mIvRightDistribution.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }

    //点击事件
    @OnClick({R.id.iv_left_distribution, R.id.iv_right_distribution})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left_distribution:
                if (mPageIndex==1){
                    showToast("已经是第一页了");
                    return;
                }else {
                    mPageIndex --;
                    getTeamPeopleDistribution(mUserId,mPageIndex,mCountPerPage);
                }
                break;
            case R.id.iv_right_distribution:
                mPageIndex ++;
                getTeamPeopleDistribution(mUserId,mPageIndex,mCountPerPage);
                break;
        }
    }
}
