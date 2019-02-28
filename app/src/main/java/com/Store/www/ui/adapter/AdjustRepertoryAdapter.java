package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CommodityStocksResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 调整尺码库存的适配器
 */

public class AdjustRepertoryAdapter extends BaseRecyclerViewAdapter<CommodityStocksResponse.DataBean,AdjustRepertoryAdapter.ViewHolder>{

    OnItemClickListener mListener;

    public AdjustRepertoryAdapter(Context context,OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_adjust_repertory,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CommodityStocksResponse.DataBean dataBean = mDataList.get(position);
        holder.mTvAdjustRepertoryName.setText(dataBean.getName());
        holder.mTvAdjustRepertoryNumber.setText(dataBean.getSum()+"件");

        //item点击事件 点击查看单件商品库存
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setItemClickListener(position,dataBean.getNo(),dataBean.getName(),dataBean.getProductId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_adjust_repertory_name)
        TextView mTvAdjustRepertoryName;
        @BindView(R.id.tv_adjust_repertory_number)
        TextView mTvAdjustRepertoryNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void setItemClickListener(int position,String productNo,String title,int productId);
    }

}
