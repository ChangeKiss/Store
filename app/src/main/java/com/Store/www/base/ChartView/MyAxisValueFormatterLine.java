package com.Store.www.base.ChartView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * @author: haifeng
 * @description: 线性报表自定义值格式
 */
public class MyAxisValueFormatterLine implements IValueFormatter {
    private DecimalFormat mFormat;

    public MyAxisValueFormatterLine() {
        //格式化数字
        mFormat = new DecimalFormat("##");
    }


    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // value参数标识在坐标轴上的位置或者说是数据值,axis标识是哪个坐标轴
        return mFormat.format(value) + "";
    }

    /** 只有为数字时返回 其余返回0*/


}
