package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.ChartView.BarChartMarkerView;
import com.Store.www.base.ChartView.MyAxisValueFormatter;
import com.Store.www.base.ChartView.MyXFormatter;
import com.Store.www.entity.EveryDayGradeResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogConnectTimeOut;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.github.mikephil.charting.data.BarData;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 每日团队业绩
 */

public class EveryDayGradeActivity extends BaseToolbarActivity implements OnChartValueSelectedListener ,DialogConnectTimeOut.TimeOutDialogButtonClickListener {

    @BindView(R.id.bar_chart)
    BarChart mBarChart;
    @BindView(R.id.layout_no_data)
    RelativeLayout mLayoutNoData;  //无数据提示

    private String data;
    DecimalFormat mFormat = new DecimalFormat("##.00");
    BarChartMarkerView markerView;
    public static List<BarEntry> entry = new ArrayList<BarEntry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_every_day_grade;
    }

    @Override
    public void initView() {
        initToolbar(this, true, "每日团队业绩");
        ActivityCollector.addActivity(this);
        data = getIntent().getStringExtra("data");
        markerView = new BarChartMarkerView(mContext,R.layout.layout_bar_chart_marker_view);
        //堆叠条形图
        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setMaxVisibleValueCount(8);  //如果条目超过10个则不显示任何值
        // 扩展现在只能分别在x轴和y轴
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(false);
        mBarChart.setHighlightFullBarEnabled(false);
        mBarChart.animateXY(8000, 8000);

        // 改变y标签的位置
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f);
        leftAxis.setSpaceTop(50f);
        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.getXAxis().setLabelRotationAngle(-45);
        mBarChart.getAxisLeft().setEnabled(false);

        /*XAxis xLabels = mBarChart.getXAxis();
        String[] values = new String[]{"10/19","10/20","10/21","10/22"};
        xLabels.setValueFormatter(new MyXFormatter(values));
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setAvoidFirstLastClipping(false);*/

        Legend l = mBarChart.getLegend();
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
        getEveryDayGrade(mUserId,data);
    }

    private void getEveryDayGrade(int agentId, String data){
        DialogLoading.shows(this,"加载中...");
        RetrofitClient.getInstances().getEveryDayTeamGrade(agentId,data).enqueue(new UICallBack<EveryDayGradeResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(EveryDayGradeResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (bean.getDataList().size() !=0){
                                mLayoutNoData.setVisibility(View.GONE);
                                mBarChart.setVisibility(View.VISIBLE);
                                XAxis xLabels = mBarChart.getXAxis();
                                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                                final String[] values = new String[bean.getDataList().size()];
                                for (int i = 0; i < bean.getDataList().size(); i++) {
                                    float val1 =  bean.getDataList().get(i).getAudited()/100;
                                    float val2 =  bean.getDataList().get(i).getUnaudited()/100;
                                    values[i] = ActivityUtils.MDTime(bean.getDataList().get(i).getTime());
                                    LogUtils.d("val1=="+val1);
                                    LogUtils.d("val2=="+val2);
                                    yVals1.add(new BarEntry(i, new float[]{val1, val2}));
                                }
                                entry = yVals1;
                                xLabels.setValueFormatter(new MyXFormatter(values));
                                xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xLabels.setAvoidFirstLastClipping(false);
                                BarDataSet set1;
                                if (mBarChart.getData() != null &&
                                        mBarChart.getData().getDataSetCount() > 0) {
                                    set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
                                    set1.setValues(yVals1);
                                    set1.setValueTextSize(4);  //设置值的字体大小
                                    mBarChart.getData().notifyDataChanged();
                                    mBarChart.notifyDataSetChanged();
                                } else {
                                    set1 = new BarDataSet(yVals1,"");
                                    set1.setColors(new int[]{Color.parseColor("#f393a5"),Color.parseColor("#f9c9d2")});
                                    set1.setStackLabels(new String[]{"已审核", "待审核"});
                                    set1.setValueTextSize(16f);  //设置值的字体大小
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
                                    mBarChart.setData(data);
                                }
                                mBarChart.setMarker(markerView);
                                mBarChart.setFitBars(false);
                                mBarChart.invalidate();
                            }
                            break;
                        default:
                            mBarChart.setVisibility(View.INVISIBLE);
                            mLayoutNoData.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });
    }



    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void OnCancelClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void OnDismissClickListener(AlertDialog dialog) { //重新连接的点击事件
        showToast("重新请求");
        dialog.dismiss();
    }
}
