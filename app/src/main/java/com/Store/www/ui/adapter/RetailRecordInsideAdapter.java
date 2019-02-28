package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.base.RoundAngleImageView;
import com.Store.www.entity.RetailRecordResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/10/12.
 * 零售记录内部适配器
 */

public class RetailRecordInsideAdapter extends BaseRecyclerViewAdapter<RetailRecordResponse.ListBean.ProductOrderListBean,
        RetailRecordInsideAdapter.ViewHolder>{

    public RetailRecordInsideAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_retail_record,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RetailRecordResponse.ListBean.ProductOrderListBean bean = mDataList.get(position);
        Glide.with(mContext)
                .load(bean.getProImg())
                .error(R.mipmap.jzz_img)
                .into(holder.mIvRetailIcon);
        holder.mTvRetailName.setText(bean.getProName());
        holder.mTvRetailSizeColor.setText(bean.getProColor()+"|"+bean.getProSize());
        holder.mTvRetailNumber.setText("x"+bean.getCount()+"件");

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_retail_icon)
        RoundAngleImageView mIvRetailIcon;  //商品图片
        @BindView(R.id.tv_retail_name)
        TextView mTvRetailName;  //商品名称
        @BindView(R.id.tv_retail_size_color)
        TextView mTvRetailSizeColor;  //商品尺码颜色
        @BindView(R.id.tv_retail_number)
        TextView mTvRetailNumber;   //商品数量

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
