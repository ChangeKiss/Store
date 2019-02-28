package com.Store.www.base.ChartView;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.Store.www.R;
import com.Store.www.ui.activity.SubordinateTeamPeopleDistributionActivity;

import java.text.DecimalFormat;

/**
 * @author: haifeng
 * @description: 柱状图自定义顶部标记图  直属下级各团队等级人员
 */
public class DistributionBarCharMarkerView extends MarkerView {

    DecimalFormat mFormat = new DecimalFormat("##");
    private TextView tvContentVip;  //vip
    private TextView tvContentDealer;  //经销商
    private TextView tvContentAgent;  //代理商
    private TextView tvContentOneLv;  //一级代理
    private TextView tvContentDiamond;  //钻石代理
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public DistributionBarCharMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContentVip = findViewById(R.id.tv_bar_chart_content_Vip);
        tvContentDealer = findViewById(R.id.tv_bar_chart_content_dealer);
        tvContentAgent = findViewById(R.id.tv_bar_chart_content_agent);
        tvContentOneLv = findViewById(R.id.tv_bar_chart_content_one_lv);
        tvContentDiamond = findViewById(R.id.tv_bar_chart_content_diamond);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-getWidth(),-getHeight()-10);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int valueIndex = (int) e.getX();
        /*LogUtils.d("Y=="+e.toString());
        LogUtils.d("数据长度=="+SubordinateTeamPeopleDistributionActivity.mEntry.size());
        LogUtils.d("值=01="+SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[0]);
        LogUtils.d("值=02="+SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[1]);
        LogUtils.d("值=03="+SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[2]);
        LogUtils.d("值=04="+SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[3]);
        LogUtils.d("值=05="+SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[4]);*/

        if (SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[0] == 0.0){
            tvContentOneLv.setText("一级代理: 0人");
        }else {
            tvContentOneLv.setText("一级代理: "+valueFormat(SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[0])+"人");
        }

        if (SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[1] == 0.0){
            tvContentAgent.setText("代理商: 0人");
        }else {
            tvContentAgent.setText("代理商: "+valueFormat(SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[1])+"人");
        }

        if (SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[2] == 0.0){
            tvContentDealer.setText("经销商: 0人");
        }else {
            tvContentDealer.setText("经销商: "+valueFormat(SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[2])+"人");
        }

        if (SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[3] == 0.0){
            tvContentVip.setText("VIP客户: 0人");
        }else {
            tvContentVip.setText("VIP客户: "+valueFormat(SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[3])+"人");
        }

        if (SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[4] == 0.0){
            tvContentDiamond.setText("钻石代理: 0人");
        }else {
            tvContentDiamond.setText("钻石代理: "+valueFormat(SubordinateTeamPeopleDistributionActivity.mEntry.get(valueIndex).getYVals()[4])+"人");
        }
        super.refreshContent(e, highlight);
    }

    private String valueFormat(float value){
        if (value==0.0){
            return "0";
        }else {
            return mFormat.format(value);
        }
    }
}
