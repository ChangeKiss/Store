package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.SubordinateScanResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: haifeng
 * @description: 下级扫码列表中的适配器
 */
public class SubordinateScanListAdapter extends BaseRecyclerViewAdapter<SubordinateScanResponse.DataBean.ProductsBean,SubordinateScanListAdapter.ViewHolder> {
    public SubordinateScanListAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_subordinate_scan,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubordinateScanResponse.DataBean.ProductsBean bean = mDataList.get(position);
        if (bean.getProduct_img()!=null){
            Glide.with(mContext).load(bean.getProduct_img()).error(R.mipmap.jzz_img).into(holder.mScanCommodityImage);
        }
        holder.mTvScanCommodityNames.setText("商品:  "+bean.getProduct_name());
        holder.mTvScanCommodityNumber.setText("件数:  "+bean.getProduct_count());
        holder.mTvScanOkNumber.setText("已扫描件数:  "+bean.getScanning_number()+"件");
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.scan_commodity_image)
        ImageView mScanCommodityImage;  //扫码商品图片
        @BindView(R.id.tv_scan_commodity_names)
        TextView mTvScanCommodityNames;  //扫码商品名
        @BindView(R.id.tv_scan_commodity_number)
        TextView mTvScanCommodityNumber;   //件数
        @BindView(R.id.tv_scan_ok_number)
        TextView mTvScanOkNumber;  //已扫码件数
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
