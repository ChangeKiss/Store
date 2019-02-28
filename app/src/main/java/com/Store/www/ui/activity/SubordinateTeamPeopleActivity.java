package com.Store.www.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.base.ChartView.CustomMarkerView;
import com.Store.www.base.ChartView.MyAxisValueFormatterLine;
import com.Store.www.base.ChartView.MyXFormatter;
import com.Store.www.entity.SubordinateTeamPeopleResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 直属下级各团队人员情况
 */

public class SubordinateTeamPeopleActivity extends BaseToolbarActivity {

    @BindView(R.id.line_chart)
    LineChart mLineCharts;   //线性图表
    @BindView(R.id.no_data)
    RelativeLayout mNoData;  //无数据布局
    @BindView(R.id.iv_left)
    ImageView mIvLeft;  //左边按钮
    @BindView(R.id.iv_right)
    ImageView mIvRight;

    private LineDataSet set;
    private String data;
    CustomMarkerView markerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_subordinate_team_people;
    }

    @Override
    public void initView() {
        initToolbar(this, true, "直属下级各团队人员状况");
        ActivityCollector.addActivity(this);
        data = getIntent().getStringExtra("data");
        markerView = new CustomMarkerView(this,R.layout.layout_marker_view);
        mCountPerPage = 8;
        //设置手势滑动事件
        //mLineCharts.setOnChartGestureListener(this);
        //设置数值选择监听
        //mLineCharts.setOnChartValueSelectedListener(this);
        //后台绘制
        mLineCharts.setDrawGridBackground(false);
        //设置描述文本
        mLineCharts.getDescription().setEnabled(false);
        //设置支持触控手势
        mLineCharts.setTouchEnabled(true);
        //设置拖动
        mLineCharts.setDragEnabled(true);
        //设置缩放
        mLineCharts.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        mLineCharts.setPinchZoom(true);

        //x轴
        LimitLine llXAxis = new LimitLine(0f, "");
        //设置线宽
        llXAxis.setLineWidth(4f);
        //
        llXAxis.enableDashedLine(0f, 0f, 0f);
        //设置
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        llXAxis.setTextSize(10f);


        //y轴设置
        YAxis leftAxis = mLineCharts.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);//折线上显示值，则不显示坐标轴上的值
        leftAxis.setDrawZeroLine(false);
        // 限制数据(而不是背后的线条勾勒出了上面)
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        mLineCharts.getAxisRight().setEnabled(false);
        mLineCharts.getXAxis().setLabelRotationAngle(-45);
        //请求数据
        getSubordinateTeamPeople(mUserId,data,mPageIndex,mCountPerPage);
        //默认动画
        mLineCharts.animateXY(3000,3000);
        //刷新
        mLineCharts.invalidate();
        // 得到这个文字
        Legend l = mLineCharts.getLegend();
        // 修改文字 ...
        l.setForm(Legend.LegendForm.NONE);

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

    //获取直属下级各团队人员情况
    private void getSubordinateTeamPeople(int agentId,String data,int pageIndex,int countPage){
        DialogLoading.shows(this,"加载中...");
        RetrofitClient.getInstances().getSubordinateTeamPeople(agentId,data,pageIndex,countPage).enqueue(new UICallBack<SubordinateTeamPeopleResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(SubordinateTeamPeopleResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            mIvLeft.setVisibility(View.VISIBLE);
                            mIvRight.setVisibility(View.VISIBLE);
                            if (bean.getDataList().size() ==0 ){
                                showToast("已经是最后一页了");
                                mIvRight.setEnabled(false);
                                return;
                            }else {
                                //设置数据
                                mLineCharts.setMaxVisibleValueCount(bean.getDataList().size());
                                mNoData.setVisibility(View.GONE);
                                mLineCharts.setVisibility(View.VISIBLE);
                                ArrayList<Entry> values = new ArrayList<Entry>();
                                String[] value = new String[bean.getDataList().size()];
                                XAxis xAxis = mLineCharts.getXAxis();
                                for (int i=0;i<bean.getDataList().size();i++){
                                    values.add(new Entry(i,bean.getDataList().get(i).getNum()));
                                    value[i] = bean.getDataList().get(i).getName();
                                    LogUtils.d("底部值=="+value[i]);
                                }
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setAvoidFirstLastClipping(false);
                                xAxis.setValueFormatter(new MyXFormatter(value));
                                setData(values);
                                mLineCharts.invalidate();
                            }
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            mNoData.setVisibility(View.VISIBLE);
                            mLineCharts.setVisibility(View.INVISIBLE);
                            mIvLeft.setVisibility(View.GONE);
                            mIvRight.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }

    //传递设置数据集
    private void setData(ArrayList<Entry> values) {
        if (mLineCharts.getData() != null && mLineCharts.getData().getDataSetCount() > 0) {
            set = (LineDataSet) mLineCharts.getData().getDataSetByIndex(0);
            set.setValues(values);
            mLineCharts.getData().notifyDataChanged();
            mLineCharts.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set = new LineDataSet(values, "");

            // 在这里设置线
            set.enableDashedLine(10f, 0f, 0f);
            set.enableDashedHighlightLine(10f, 5f, 0f);
            set.setColor(Color.parseColor("#69c0de"));
            set.setCircleColor(Color.parseColor("#69c0de"));
            set.setLineWidth(1f);
            set.setCircleRadius(2f);
            set.setDrawCircleHole(false);
            set.setValueTextSize(9f);
            set.setDrawFilled(true);
            set.setDrawCircles(true);
            set.setFormLineWidth(1f);
            set.setValueFormatter(new MyAxisValueFormatterLine());
            //set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set.setFormSize(10.f);

            if (Utils.getSDKInt() >= 18) {
                // 填充背景只支持18以上
                Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                set.setFillDrawable(drawable);
                set.setFillColor(Color.parseColor("#69c0de"));
            } else {
                set.setFillColor(Color.parseColor("#69c0de"));
            }
            //String[] xValue = new String[]{"10-11","10-12","10-13","10-14","10-15","10-16","10-17","10-18","10-19"};
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set);
            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);
            mLineCharts.setMarker(markerView);
            //设置数据
            mLineCharts.setData(data);
            mLineCharts.invalidate();
        }
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
                    mIvRight.setEnabled(true);
                    getSubordinateTeamPeople(mUserId,data,mPageIndex,mCountPerPage);
                }
                break;
            case R.id.iv_right:
                mPageIndex ++;
                getSubordinateTeamPeople(mUserId,data,mPageIndex,mCountPerPage);
                break;
        }
    }
}
