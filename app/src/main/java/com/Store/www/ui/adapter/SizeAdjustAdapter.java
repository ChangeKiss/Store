package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.SizeAdjustResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 尺码调整的第一个适配器
 */

public class SizeAdjustAdapter extends BaseRecyclerViewAdapter<SizeAdjustResponse.DataBean,SizeAdjustAdapter.ViewHolder>{

    SizeAdjustInnerAdapter mAdapters;
    public SizeAdjustAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_size_adjust,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SizeAdjustResponse.DataBean dataBean = mDataList.get(position);
        holder.mTvSizeOrderNumber.setText(dataBean.getChangeListNo());
        holder.mTvSizeTime.setText(ActivityUtils.times(dataBean.getCreateTime()));
        holder.mTvSizeName.setText(dataBean.getRepositoryName());
        LogUtils.d("时间戳转换后时间为"+ActivityUtils.times(dataBean.getCreateTime()));
        //RecyclerView的嵌套
        mAdapters = new SizeAdjustInnerAdapter(mContext);
        holder.mRY.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mRY.setAdapter(mAdapters);
        holder.mRY.setFocusable(false);  //关闭内部列表的可聚焦 这样列表下拉会流畅
        holder.mRY.setFocusableInTouchMode(false);   //关闭内部列表的可聚焦 这样列表下拉会流畅
        mAdapters.addAll(dataBean.getSKUdata());
        mAdapters.setName(dataBean.getProductName());

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_size_order_number)
        TextView mTvSizeOrderNumber;  //订单编号
        @BindView(R.id.tv_size_time)
        TextView mTvSizeTime; //订单时间
        @BindView(R.id.ry_size_inner)
        RecyclerView mRY;
       /* @BindView(R.id.tv_size_number)
        TextView mTvSizeNumber;  //商品款式
        @BindView(R.id.tv_size_color)
        TextView mTvSizeColor; //商品颜色
        @BindView(R.id.tv_size_size)
        TextView mTvSizeSize; //商品尺寸
        @BindView(R.id.tv_size_count)
        TextView mTvSizeCount;  //商品数量*/
        @BindView(R.id.tv_size_name)
        TextView mTvSizeName;  //姓名

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
