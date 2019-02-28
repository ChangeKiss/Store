package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.SellOrderDetailsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 销售商品清单适配器
 */

public class SellOrderDetailAdapter extends BaseRecyclerViewAdapter<SellOrderDetailsResponse.OrderProductBean,SellOrderDetailAdapter.ViewHolder>{
    public SellOrderDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SellOrderDetailsResponse.OrderProductBean bean = mDataList.get(position);
        if (bean.getColor()==null){
            holder.mTVItemDetailsName.setText(bean.getName()+" "+"**"+"|"+bean.getSizeCode());
            holder.mTvItemDetailsNumber.setText(bean.getCount()+"件");
        }else {
            holder.mTVItemDetailsName.setText(bean.getName()+" "+bean.getColor()+"|"+bean.getSizeCode());
            holder.mTvItemDetailsNumber.setText(bean.getCount()+"件");
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_item_details_name)
        TextView mTVItemDetailsName;  //商品名称
        @BindView(R.id.tv_item_details_number)
        TextView mTvItemDetailsNumber;  //商品数量
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
