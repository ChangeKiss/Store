package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.SizeAdjustResponse;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 尺寸调整单的第二个适配器
 */

public class SizeAdjustInnerAdapter extends BaseRecyclerViewAdapter<SizeAdjustResponse.DataBean.SKUdataBean,SizeAdjustInnerAdapter.ViewHolder>{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SizeAdjustInnerAdapter(Context context) {
        super(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_size_adjust_inner,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SizeAdjustResponse.DataBean.SKUdataBean dataBean= mDataList.get(position);
        LogUtils.d("循环添加数据第"+position+"款颜色"+dataBean.getColorName());
        holder.mTvSizeNumber.setText(getName());
        holder.mTvSizeColor.setText(dataBean.getColorName());
        LogUtils.d("循环添加数据第"+position+"款尺码"+dataBean.getSizeName());
        holder.mTvSizeSize.setText(dataBean.getSizeName());
        holder.mTvSizeCount.setText(dataBean.getCount()+"");

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_size_number)
        TextView mTvSizeNumber;  //商品款式
        @BindView(R.id.tv_size_color)
        TextView mTvSizeColor; //商品颜色
        @BindView(R.id.tv_size_size)
        TextView mTvSizeSize; //商品尺寸
        @BindView(R.id.tv_size_count)
        TextView mTvSizeCount;  //商品数量

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
