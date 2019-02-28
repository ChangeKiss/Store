package com.Store.www.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.ChartView.CustomMarkerView;
import com.Store.www.base.ChartView.MyAxisValueFormatterLine;
import com.Store.www.base.ChartView.MyXFormatter;
import com.Store.www.entity.EveryDayNewPeopleResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *每日招新人数界面
 */

public class EveryDayNewPeopleActivity extends BaseToolbarActivity implements OnChartValueSelectedListener,OnChartGestureListener {
    @BindView(R.id.m_Line_Chart)
    LineChart mLineChart;  //线性图表
    @BindView(R.id.no_data)
    RelativeLayout mNoData;  //无数据提示

    private String data;
    private LineDataSet set1;
    CustomMarkerView markerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_every_day_new_people;
    }

    @Override
    public void initView() {
        initToolbar(this,true,"每日招新人数");
        ActivityCollector.addActivity(this);
        data = getIntent().getStringExtra("data");
        markerView = new CustomMarkerView(this,R.layout.layout_marker_view);
        //设置手势滑动事件
        mLineChart.setOnChartGestureListener(this);
        //设置数值选择监听
        mLineChart.setOnChartValueSelectedListener(this);
        //后台绘制
        mLineChart.setDrawGridBackground(false);
        //设置描述文本
        mLineChart.getDescription().setEnabled(false);
        //设置支持触控手势
        mLineChart.setTouchEnabled(true);
        //设置拖动
        mLineChart.setDragEnabled(true);
        //设置缩放
        mLineChart.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        mLineChart.setPinchZoom(true);

        //x轴
        LimitLine llXAxis = new LimitLine(0f, "");
        //设置线宽
        llXAxis.setLineWidth(4f);
        //
        llXAxis.enableDashedLine(0f, 0f, 0f);
        //设置
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        llXAxis.setTextSize(12f);


        //y轴设置
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);//折线上显示值，则不显示坐标轴上的值
        leftAxis.setDrawZeroLine(false);
        // 限制数据(而不是背后的线条勾勒出了上面)
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getXAxis().setLabelRotationAngle(-45);

        //默认动画
        mLineChart.animateXY(3000,3000);
        //刷新
        //mChart.invalidate();
        // 得到这个文字
        Legend l = mLineChart.getLegend();
        // 修改文字 ...
        l.setForm(Legend.LegendForm.NONE);

        //请求数据
        getEveryDayNewPeople(mUserId,data);
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

    //获取每日招新人数
    private void getEveryDayNewPeople(int agentId,String date){
        DialogLoading.shows(this,"加载中...");
        RetrofitClient.getInstances().getNewPeople(agentId,date).enqueue(new UICallBack<EveryDayNewPeopleResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(EveryDayNewPeopleResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            //设置数据
                            mLineChart.setMaxVisibleValueCount(bean.getDataList().size());
                            ArrayList<Entry> values = new ArrayList<Entry>();
                            String[] value = new String[bean.getDataList().size()];
                            XAxis xAxis = mLineChart.getXAxis();
                            for (int i=0;i<bean.getDataList().size();i++){
                                values.add(new Entry(i,bean.getDataList().get(i).getNum()));
                                value[i] = ActivityUtils.MDTime(bean.getDataList().get(i).getTime());
                                LogUtils.d("底部值=="+value[i]);
                            }
                            mNoData.setVisibility(View.GONE);
                            mLineChart.setVisibility(View.VISIBLE);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setAvoidFirstLastClipping(false);
                            xAxis.setValueFormatter(new MyXFormatter(value));
                            setData(values);
                            break;
                        default :
                            mNoData.setVisibility(View.VISIBLE);
                            mLineChart.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }
        });
    }

    //传递设置数据集
    private void setData(ArrayList<Entry> values) {
        if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "");

            // 在这里设置线
            set1.enableDashedLine(10f, 0f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.parseColor("#f393a5"));
            set1.setCircleColor(Color.parseColor("#f393a5"));
            set1.setLineWidth(1f);
            set1.setCircleRadius(2f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setFormLineWidth(1f);
            set1.setValueFormatter(new MyAxisValueFormatterLine());
            //set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // 填充背景只支持18以上
                Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                set1.setFillDrawable(drawable);
                set1.setFillColor(Color.parseColor("#F393A5"));
            } else {
                set1.setFillColor(Color.parseColor("#F393A5"));
            }
            //String[] xValue = new String[]{"10-11","10-12","10-13","10-14","10-15","10-16","10-17","10-18","10-19"};
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);
            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);
            mLineChart.setMarker(markerView);
            //设置数据
            mLineChart.setData(data);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
