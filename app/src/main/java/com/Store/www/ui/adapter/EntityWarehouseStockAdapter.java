package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.EntityWarehouseStocksResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/10/10.
 * 实体类商品库存的适配器
 */

public class EntityWarehouseStockAdapter extends BaseRecyclerViewAdapter<EntityWarehouseStocksResponse.MapBean,EntityWarehouseStockAdapter.ViewHolder>{
    ClickListener mListener;

    public EntityWarehouseStockAdapter(Context context,ClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_commodity_stocks,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EntityWarehouseStocksResponse.MapBean bean = mDataList.get(position);
        holder.mTvStocksName.setText(bean.getName());
        holder.mTvStocksNumber.setText(bean.getSum()+"件");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickListener(position,bean.getProductId(),bean.getName());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_stocks_name)
        TextView mTvStocksName;
        @BindView(R.id.tv_stocks_number)
        TextView mTvStocksNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ClickListener{
        void onItemClickListener(int position,int productId,String name);
    }
}
