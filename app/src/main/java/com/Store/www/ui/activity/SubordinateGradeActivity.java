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
import com.Store.www.base.ChartView.MyAxisValueFormatter;
import com.Store.www.base.ChartView.MyXFormatter;
import com.Store.www.entity.SubordinateGradeResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 直属下级团队业绩
 */

public class SubordinateGradeActivity extends BaseToolbarActivity {
    @BindView(R.id.chart)
    BarChart mChart;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;  //左切换按钮
    @BindView(R.id.iv_right)
    ImageView mIvRight;  //右切换按钮
    @BindView(R.id.layout_no_data)
    RelativeLayout mLayoutNoData;  //无数据提示

    private String data;
    DecimalFormat mFormat = new DecimalFormat("##.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_subordinate_grade;
    }

    @Override
    public void initView() {
        initToolbar(this, true, "直属下级团队业绩");
        ActivityCollector.addActivity(this);
        mCountPerPage = 8;
        data = getIntent().getStringExtra("data");

        //mChart.setOnChartValueSelectedListener(this);  //选中点击事件监听
        mChart.getDescription().setEnabled(false);
        // 扩展现在只能分别在x轴和y轴
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setMaxVisibleValueCount(10);
        mChart.animateXY(8000, 8000);

        // 改变y标签的位置
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(10f);
        leftAxis.setSpaceTop(50f);
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setLabelRotationAngle(-45);
        mChart.getAxisLeft().setEnabled(false);


        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
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
        getSubordinateGrade(mUserId, data, mPageIndex, mCountPerPage);
    }

    private void getSubordinateGrade(int agentId, String data, int pageIndex, int countPerPage) {
        DialogLoading.shows(this,"加载中...");
        RetrofitClient.getInstances().getSubordinateGrade(agentId, data, pageIndex, countPerPage).enqueue(new UICallBack<SubordinateGradeResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(SubordinateGradeResponse bean) {
                if (isTop) {
                    switch (bean.getReturnValue()) {
                        case 1:
                            mLayoutNoData.setVisibility(View.GONE);
                            mChart.setVisibility(View.VISIBLE);
                            mIvLeft.setVisibility(View.VISIBLE);
                            mIvRight.setVisibility(View.VISIBLE);
                            XAxis xAxis = mChart.getXAxis();
                            ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
                            final String[] values = new String[bean.getDataList().size()];
                            for (int i=0;i<bean.getDataList().size();i++){
                                float val1 = bean.getDataList().get(i).getAudited()/100;
                                float val2 = bean.getDataList().get(i).getUnaudited()/100;
                                values[i] = bean.getDataList().get(i).getName();
                                yVals.add(new BarEntry(i, new float[]{val1, val2}));
                            }
                            xAxis.setValueFormatter(new MyXFormatter(values));
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setAvoidFirstLastClipping(false);
                            BarDataSet set1;
                            if (bean.getDataList().size()==0){
                                mIvRight.setVisibility(View.GONE);
                                showToast("没有更多数据了");
                                return;
                            }
                            if (mChart.getData() != null &&
                                    mChart.getData().getDataSetCount() > 0) {
                                set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
                                set1.setValues(yVals);
                                mChart.getData().notifyDataChanged();
                                mChart.notifyDataSetChanged();
                            } else {
                                set1 = new BarDataSet(yVals,"");
                                set1.setColors(new int[]{Color.parseColor("#69c0de"),Color.parseColor("#b4dfee")});
                                set1.setStackLabels(new String[]{"已审核", "待审核"});
                                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                                dataSets.add(set1);
                                BarData data = new BarData(dataSets);
                                data.setValueFormatter(new IValueFormatter() {
                                    @Override
                                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                                        if (value == 0.0 || value==0){
                                            return "";
                                        }else {
                                            return  ""+mFormat.format(value);
                                        }
                                    }
                                });
                                data.setValueTextColor(Color.WHITE);
                                data.setValueTypeface(Typeface.DEFAULT_BOLD);
                                mChart.setData(data);
                            }
                            mChart.setFitBars(true);
                            mChart.getData().notifyDataChanged();
                            mChart.notifyDataSetChanged();
                            mChart.invalidate();
                            break;
                        default:
                            mLayoutNoData.setVisibility(View.VISIBLE);
                            mChart.setVisibility(View.INVISIBLE);
                            mIvLeft.setVisibility(View.GONE);
                            mIvRight.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                if (mPageIndex==1){
                    showToast("已经是第一页了");
                    return;
                }else {
                    mPageIndex --;
                    getSubordinateGrade(mUserId,data,mPageIndex,mCountPerPage);
                }
                break;
            case R.id.iv_right:
                mPageIndex ++;
                getSubordinateGrade(mUserId,data,mPageIndex,mCountPerPage);
                break;
        }
    }
}
