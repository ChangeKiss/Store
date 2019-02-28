package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.PickUpDetailsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提货单详情的适配器
 */

public class PickUpDetailsAdapter extends BaseRecyclerViewAdapter<PickUpDetailsResponse.DataBean.PickBillProductsBean,PickUpDetailsAdapter.ViewHolder>{


    public PickUpDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pick_up_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PickUpDetailsResponse.DataBean.PickBillProductsBean bean = mDataList.get(position);
        holder.mTvPickUpDetailsName.setText(bean.getProductName()+" "+bean.getColor()+"|"+bean.getSize());
        holder.mTvPickUpDetailsNumber.setText(bean.getCount()+"件");

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_pick_up_details_name)
        TextView mTvPickUpDetailsName;
        @BindView(R.id.tv_pick_up_details_number)
        TextView mTvPickUpDetailsNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
