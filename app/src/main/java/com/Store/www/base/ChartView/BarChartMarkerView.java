package com.Store.www.base.ChartView;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.Store.www.R;
import com.Store.www.ui.activity.EveryDayGradeActivity;
import com.Store.www.utils.LogUtils;

import java.text.DecimalFormat;

/**
 * @author: haifeng
 * @description: 柱状图自定义顶部标记图  每日团队业绩
 */
public class BarChartMarkerView extends MarkerView {


    DecimalFormat mFormat = new DecimalFormat("##.00");
    private TextView tvContent;
    private TextView tvContentTwo;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public BarChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tv_bar_chart_content);
        tvContentTwo = findViewById(R.id.tv_bar_chart_content_two);
    }



    @Override
    public void setChartView(Chart chart) {
        super.setChartView(chart);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int b = (int) e.getX();
        LogUtils.d("0=="+EveryDayGradeActivity.entry.get(b).getYVals()[0]);
        LogUtils.d("1=="+EveryDayGradeActivity.entry.get(b).getYVals()[1]);
        tvContent.setText("已审核:"+valueFormat(EveryDayGradeActivity.entry.get(b).getYVals()[0]));
        tvContentTwo.setText("未审核:"+valueFormat(EveryDayGradeActivity.entry.get(b).getYVals()[1]));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-getWidth(),-getHeight()-10);
    }

    private String valueFormat(float value){
        if (value==0.0){
            return "0";
        }else {
            return mFormat.format(value);
        }
    }

}
