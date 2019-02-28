package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.WarehouseStocksResponse;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 单件商品库存的适配器
 */

public class OnePieceStocksAdapter extends BaseRecyclerViewAdapter<WarehouseStocksResponse.DataBean,OnePieceStocksAdapter.ViewHolder>{

    LinearLayout.LayoutParams params;
    LinearLayout.LayoutParams paramsTwo;
    LinearLayout.LayoutParams paramsThree;
    public OnePieceStocksAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_one_piece_commodity,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WarehouseStocksResponse.DataBean bean = mDataList.get(position);
        params = (LinearLayout.LayoutParams) holder.mLayoutWarehouseSizeName.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3;
        holder.mLayoutWarehouseSizeName.setLayoutParams(params);
        holder.mTvCommodityStocks.setLayoutParams(params);

        paramsTwo = (LinearLayout.LayoutParams) holder.mTvCommodityLine.getLayoutParams();
        paramsTwo.leftMargin = UserPrefs.getInstance().getWidth()/3/5/4;
        holder.mTvCommodityLine.setLayoutParams(paramsTwo);

        paramsThree = (LinearLayout.LayoutParams) holder.mTvCommodityName.getLayoutParams();
        paramsThree.width = UserPrefs.getInstance().getWidth()/3/2;
        holder.mTvCommodityName.setLayoutParams(paramsThree);
        holder.mTvCommoditySize.setLayoutParams(paramsThree);

        holder.mTvCommodityName.setText(bean.getColor()+"");
        holder.mTvCommodityLine.setText("|");
        holder.mTvCommoditySize.setText(bean.getSize()+"");
        holder.mTvCommodityStocks.setText(bean.getRepositoryCount()+"");
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_commodity_name)
        TextView mTvCommodityName;  //商品名称
        @BindView(R.id.tv_commodity_stocks)
        TextView mTvCommodityStocks;
        @BindView(R.id.layout_warehouse_size_name)
        LinearLayout mLayoutWarehouseSizeName;  //显示库存商品尺寸和名称的布局
        @BindView(R.id.tv_commodity_line)
        TextView mTvCommodityLine;  //中间的分割线
        @BindView(R.id.tv_commodity_size)
        TextView mTvCommoditySize;  //商品尺寸

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
