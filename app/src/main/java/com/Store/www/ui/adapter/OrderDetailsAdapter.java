package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.OrderDetailsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单详情适配器
 */

public class OrderDetailsAdapter extends BaseRecyclerViewAdapter<OrderDetailsResponse.OrderProductBean,OrderDetailsAdapter.ViewHolder>{

    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public OrderDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_order_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setPosition(position);
        OrderDetailsResponse.OrderProductBean bean = mDataList.get(position);
        if (!(TextUtils.isEmpty((CharSequence) bean.getColor()) || TextUtils.isEmpty((CharSequence) bean.getSize()))){
            holder.mTvItemDetailsName.setText(bean.getName()+" "+bean.getColor()+"|"+bean.getSize());
        }else {
            holder.mTvItemDetailsName.setText(bean.getName()+"");
        }
        holder.mTvItemDetailsNumber.setText(bean.getCount()+"件");
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_item_details_name)
        TextView mTvItemDetailsName;
        @BindView(R.id.tv_item_details_number)
        TextView mTvItemDetailsNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }



}
