package com.Store.www.base.ChartView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * @author: haifeng
 * @description:
 */
public class MyXFormatter implements IAxisValueFormatter {
    private String[] mValues;

    public MyXFormatter(String[] values) {
        this.mValues = values;
    }
    private static final String TAG = "MyXFormatter";

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        if (mValues.length ==0){
            return "";
        }else {
            return mValues[(int) value % mValues.length];
        }
    }



}
