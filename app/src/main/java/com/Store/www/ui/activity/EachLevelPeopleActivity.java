package com.Store.www.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.ChartView.MyAxisValueFormatterPeiChart;
import com.Store.www.entity.BraEachLevelPeopleResponse;
import com.Store.www.entity.ShapeWearEachLevelPeopleResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.UserPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 各等级人员分布报表
 */
public class EachLevelPeopleActivity extends BaseToolbarActivity {
    @BindView(R.id.pie_chart_one)
    PieChart mPieChartOne;  //第一个饼状图
    @BindView(R.id.pie_chart_two)
    PieChart mPeiChartTwo;
    @BindView(R.id.layout_no_data)
    RelativeLayout mLayoutNoData;   //无数据提示图

    LinearLayout.LayoutParams params;
    private String mYear;  //年份
    private String mMonth;  //月份
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_each_level_people;
    }

    @Override
    public void initView() {
        initToolbar(this,true,"各等级人员分布");
        params = (LinearLayout.LayoutParams) mPieChartOne.getLayoutParams();
        params.height = UserPrefs.getInstance().getHeight()/2-90;
        initPieChartOne();
        initPieChartTwo();
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

    //初始化内衣饼图
    private void initPieChartOne(){
        mPieChartOne.setLayoutParams(params);
        //折现饼状图
        mPieChartOne.setUsePercentValues(true);
        mPieChartOne.getDescription().setEnabled(false);
        mPieChartOne.setExtraOffsets(5, 10, 5, 5);

        mPieChartOne.setDragDecelerationFrictionCoef(0.95f);
        //绘制中间文字
        mPieChartOne.setCenterText(pieChartOneSpannableText());
        mPieChartOne.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        mPieChartOne.setDrawHoleEnabled(true);
        mPieChartOne.setHoleColor(Color.WHITE);
        mPieChartOne.setTransparentCircleColor(Color.WHITE);
        mPieChartOne.setTransparentCircleAlpha(110);
        mPieChartOne.setHoleRadius(30f);
        mPieChartOne.setTransparentCircleRadius(32f);
        mPieChartOne.setDrawCenterText(true);
        mPieChartOne.setRotationAngle(0);
        // 触摸旋转
        mPieChartOne.setRotationEnabled(true);
        mPieChartOne.setHighlightPerTapEnabled(true);
        getBraEachLevelPeople(mUserId);
    }

    //绘制内衣图中心文字
    private SpannableString pieChartOneSpannableText() {
        SpannableString s = new SpannableString("内衣代理");
        return s;
    }

    //获取内衣代理等级分布
    private void getBraEachLevelPeople(int agentId){
        RetrofitClient.getInstances().getEachLevelPeople(agentId).enqueue(new UICallBack<BraEachLevelPeopleResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BraEachLevelPeopleResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (mLayoutNoData.getVisibility()==View.VISIBLE){
                                mLayoutNoData.setVisibility(View.GONE);
                            }
                            mPieChartOne.setVisibility(View.VISIBLE);
                            //模拟数据
                            ArrayList<PieEntry> entries = new ArrayList<>();
                            for (int i=0;i<bean.getDataList().size();i++){
                                entries.add(new PieEntry(bean.getDataList().get(i).getCount(),bean.getDataList().get(i).getLevelName()));
                            }
                            //设置数据
                            setBraData(entries);
                            //默认动画
                            mPieChartOne.animateY(1400, Easing.EasingOption.EaseInOutQuad);
                            Legend l = mPieChartOne.getLegend();
                            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                            l.setOrientation(Legend.LegendOrientation.VERTICAL);
                            l.setDrawInside(false);
                            l.setEnabled(false);
                            break;
                        default:
                            showToast("内衣代理"+bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }


    //设置内衣数据
    private void setBraData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(15f);
        Integer[] colors = new Integer[]{Color.parseColor("#4edee0"), Color.parseColor("#5cabe3"),
                Color.parseColor("#ffb980"), Color.parseColor("#f393a5"), Color.parseColor("#b6a2de")};
        //添加对应的颜色值
        List<Integer> colorSum = new ArrayList<>();
        for (Integer color : colors) {
            colorSum.add(color);
        }
        dataSet.setColors(colorSum);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(0.6f);
        dataSet.setValueLineColor(Color.parseColor("#ffb980"));
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyAxisValueFormatterPeiChart());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        mPieChartOne.setData(data);
        // 撤销所有的亮点
        mPieChartOne.highlightValues(null);
        mPieChartOne.invalidate();
    }



    //初始塑身衣饼图
    private void initPieChartTwo(){
        mPeiChartTwo.setLayoutParams(params);
        //折现饼状图
        mPeiChartTwo.setUsePercentValues(true);
        mPeiChartTwo.getDescription().setEnabled(false);
        mPeiChartTwo.setExtraOffsets(5, 10, 5, 5);
        mPeiChartTwo.setDragDecelerationFrictionCoef(0.95f);
        //绘制中间文字
        mPeiChartTwo.setCenterText(pieChartTwoSpannableText());
        mPeiChartTwo.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        mPeiChartTwo.setDrawHoleEnabled(true);
        mPeiChartTwo.setHoleColor(Color.WHITE);
        mPeiChartTwo.setTransparentCircleColor(Color.WHITE);
        mPeiChartTwo.setTransparentCircleAlpha(110);
        mPeiChartTwo.setHoleRadius(30f);
        mPeiChartTwo.setTransparentCircleRadius(32f);
        mPeiChartTwo.setDrawCenterText(true);
        mPeiChartTwo.setRotationAngle(0);
        // 触摸旋转
        mPeiChartTwo.setRotationEnabled(true);
        mPeiChartTwo.setHighlightPerTapEnabled(true);
        getShapeWearEachLevelPeople(mUserId);

    }


    //绘制塑身衣图中心文字
    private SpannableString pieChartTwoSpannableText(){
        SpannableString s = new SpannableString("塑身衣代理");
        return s;
    }

    //获取塑身衣代理分布
    private void getShapeWearEachLevelPeople(int agentId){
        DialogLoading.shows(this,"加载中...");
        RetrofitClient.getInstances().getShapeWearEachLevelPeople(agentId).enqueue(new UICallBack<ShapeWearEachLevelPeopleResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop) checkNet();
            }

            @Override
            public void OnRequestSuccess(ShapeWearEachLevelPeopleResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            if (mLayoutNoData.getVisibility()==View.VISIBLE){
                                mLayoutNoData.setVisibility(View.GONE);
                            }
                            mPeiChartTwo.setVisibility(View.VISIBLE);
                            //模拟数据
                            ArrayList<PieEntry> entries = new ArrayList<>();
                            for (int i=0;i<bean.getDataList().size();i++){
                                entries.add(new PieEntry(bean.getDataList().get(i).getCount(),bean.getDataList().get(i).getLevelName()));
                            }
                           /* entries.add(new PieEntry(234, "VIP客户"));  // 4
                            entries.add(new PieEntry(200, "一级代理")); // 1
                            entries.add(new PieEntry(300, "钻石总代")); //5
                            entries.add(new PieEntry(400, "代理商"));  //2
                            entries.add(new PieEntry(100, "经销商")); //3*/
                            //设置数据
                            setShapeWearData(entries);
                            //默认动画
                            mPeiChartTwo.animateY(1400, Easing.EasingOption.EaseInOutQuad);
                            Legend l = mPeiChartTwo.getLegend();
                            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                            l.setOrientation(Legend.LegendOrientation.VERTICAL);
                            l.setDrawInside(false);
                            l.setEnabled(false);
                            break;
                        default:
                            showToast("塑身衣代理"+bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }

    //设置塑身衣数据
    private void setShapeWearData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(15f);
        Integer[] colors = new Integer[]{Color.parseColor("#4edee0"), Color.parseColor("#5cabe3"),
                Color.parseColor("#ffb980"), Color.parseColor("#f393a5"), Color.parseColor("#b6a2de")};
        //添加对应的颜色值
        List<Integer> colorSum = new ArrayList<>();
        for (Integer color : colors) {
            colorSum.add(color);
        }
        dataSet.setColors(colorSum);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(0.6f);
        dataSet.setValueLineColor(Color.parseColor("#ffb980"));
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyAxisValueFormatterPeiChart());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        mPeiChartTwo.setData(data);
        mPeiChartTwo.highlightValues(null);
        mPeiChartTwo.invalidate();
    }
}
