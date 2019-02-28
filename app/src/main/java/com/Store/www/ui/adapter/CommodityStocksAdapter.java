package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CommodityStocksResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *商品库存适配器
 */

public class CommodityStocksAdapter extends BaseRecyclerViewAdapter<CommodityStocksResponse.DataBean,CommodityStocksAdapter.ViewHolder>{

    OnItemClickListener mListener;

    public CommodityStocksAdapter(Context context,OnItemClickListener listener) {
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
        final CommodityStocksResponse.DataBean bean = mDataList.get(position);
        holder.mIvEnter.setVisibility(View.INVISIBLE);
        holder.mTvStocksName.setText(bean.getName());
        holder.mTvStocksNumber.setText(bean.getSum()+"件");  //显示总数
        //item点击事件 点击查看单件商品库存
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setItemClickListener(position,bean.getNo(),bean.getName());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_stocks_name)
        TextView mTvStocksName;
        @BindView(R.id.tv_stocks_number)
        TextView mTvStocksNumber;
        @BindView(R.id.iv_enter)
        ImageView mIvEnter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //写接口用来设置点击事件或者传参

    public interface OnItemClickListener{
        void setItemClickListener(int position,String productNo,String title);
    }

}
