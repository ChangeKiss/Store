package com.Store.www.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.RoundAngleImageView;
import com.Store.www.entity.ScanRecordDetailResponse;
import com.Store.www.entity.ScanRecordTimeResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2018/10/24.
 * 扫描入口记录的时间适配器
 */

public class ScanEntranceTimeAdapter extends BaseExpandableListAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ScanRecordTimeResponse.DataBean> mData;
    private List<List<ScanRecordDetailResponse.DataBean>> mDataBean;

    public ScanEntranceTimeAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void updateSeconds(List<ScanRecordTimeResponse.DataBean> been, List<ScanRecordDetailResponse.DataBean> b, int position) {
        this.mData = been;
        List<List<ScanRecordDetailResponse.DataBean>> ben = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            if (i != position)
                ben.add(new ArrayList<ScanRecordDetailResponse.DataBean>());
            else
                ben.add(b);
        }
        this.mDataBean = ben;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mDataBean==null){
            LogUtils.d("无");
            return  0;
        }else {
            LogUtils.d("有");
            return mDataBean.get(groupPosition).size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataBean.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if(view == null){
            holder = new GroupHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_scan_time, null);
            holder.scanDay = (TextView)view.findViewById(R.id.tv_scan_day);
            holder.scanNumber = (TextView) view.findViewById(R.id.tv_scan_number);
            holder.scanHint = (ImageView) view.findViewById(R.id.iv_scan_hint);
            view.setTag(holder);
        }else{
            holder = (GroupHolder)view.getTag();
        }

        //判断是否已经打开列表
        if(isExpanded){
            holder.scanHint.setImageResource(R.mipmap.right_arrow_up);
        }else{
            holder.scanHint.setImageResource(R.mipmap.right_arrow_down);
        }
        holder.scanDay.setText(ActivityUtils.YMDTime(mData.get(groupPosition).getDate()));
        holder.scanNumber.setText(mData.get(groupPosition).getCount()+"件");
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if (view == null){
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_scan_detail,null);
            holder.scanDetailIv = (RoundAngleImageView) view.findViewById(R.id.riv_scan_detail);
            holder.scanCommodityName = (TextView) view.findViewById(R.id.tv_scan_commodity_name);
            holder.scanOnlyCode = (TextView) view.findViewById(R.id.tv_only_code);
            holder.scanTime = (TextView) view.findViewById(R.id.tv_scan_time);
            view.setTag(holder);
        }else{
            holder = (ChildHolder)view.getTag();
        }

        Glide.with(mContext)
                .load(mDataBean.get(groupPosition).get(childPosition).getImageUrl())
                .error(R.mipmap.jzz_img)
                .into(holder.scanDetailIv);
        holder.scanCommodityName.setText(mDataBean.get(groupPosition).get(childPosition).getProductName());
        holder.scanOnlyCode.setText(mDataBean.get(groupPosition).get(childPosition).getBarCode());
        holder.scanTime.setText(ActivityUtils.getHoursAndMinutes(mDataBean.get(groupPosition).get(childPosition).getDate()));
        return view;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }







    class GroupHolder{
        public TextView scanDay;
        public TextView scanNumber;
        public ImageView scanHint;

    }

    class ChildHolder{
        public RoundAngleImageView scanDetailIv;
        public TextView scanCommodityName;
        public TextView scanOnlyCode;
        public TextView scanTime;
    }


}
