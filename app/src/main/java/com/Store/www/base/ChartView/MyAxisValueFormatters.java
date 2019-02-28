package com.Store.www.base.ChartView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * @author: haifeng
 * @description:
 */
public class MyAxisValueFormatters implements IAxisValueFormatter {
    private DecimalFormat mFormat;

    public MyAxisValueFormatters(){
        //格式化数字
        mFormat = new DecimalFormat("##,##,##");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // value参数标识在坐标轴上的位置或者说是数据值,axis标识是哪个坐标轴
        return mFormat.format(value) + "";
    }
}
