package com.Store.www.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CommodityManagerResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2017/12/13.
 */
    //商品管理的适配器
public class CommodityAdapter extends BaseRecyclerViewAdapter<CommodityManagerResponse.DataBean, CommodityAdapter.ViewHolder> {

    OnOrderButtonClickListener mListener;

    public CommodityAdapter(Context context) {
        super(context);
    }

    public void setListener(OnOrderButtonClickListener listener) {
        mListener = listener;
    }

    @Override
    public CommodityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_commodity,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommodityAdapter.ViewHolder holder, final int position) {
        final CommodityManagerResponse.DataBean dataBean =mDataList.get(position);
        if (dataBean.getImages()!=null){
            if (dataBean.getImages().size()>0){
                CommodityManagerResponse.DataBean.ImagesBean imagesBean =dataBean.getImages().get(0);
                Glide.with(mContext).load(imagesBean.getUrl())
                        .priority(Priority.HIGH)
                        .crossFade()
                        .error(R.mipmap.jzz_img)
                        .into(holder.mIvItemCommodity);
            }
        }else {
            Glide.with(mContext).load("").error(R.mipmap.jzz_img).into(holder.mIvItemCommodity);
        }

        holder.mTvItemName.setText(dataBean.getName());
        holder.mTvCommodityMoney.setText(dataBean.getCurrency()+ActivityUtils.changeMoneys(dataBean.getPrice()));
        holder.mLayoutItemCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("商品ID=01="+dataBean.getProductId());
                mListener.setItemButtonClickListener(position,dataBean.getProductId());
            }
        });
        /*holder.mTvCommodityMoneyTwo.setText(dataBean.getCurrency()+ActivityUtils.changeMoneys(dataBean.getSalePrice()));
        holder.mTvCommodityMoneyTwo.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //从这里开始下面都是给原价设置斜线
        holder.mTvCommodityMoneyTwo.getPaint().setAntiAlias(true);*/

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_item_commodity)
        ImageView mIvItemCommodity;
        @BindView(R.id.tv_item_name)
        TextView mTvItemName;
        @BindView(R.id.tv_commodity_money)
        TextView mTvCommodityMoney;
        @BindView(R.id.layout_item_commodity)
        LinearLayout mLayoutItemCommodity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnOrderButtonClickListener {

        void setItemButtonClickListener(int position, int productId);
    }
}