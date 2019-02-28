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
import com.Store.www.entity.ShoppingCartResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提交订单的适配器
 */

public class SubmitOrderAdapter extends BaseRecyclerViewAdapter<ShoppingCartResponse.DataBean,SubmitOrderAdapter.ViewHolder>{
    private String mCurrency;
    public SubmitOrderAdapter(Context context) {
        super(context);
    }

    public String getmCurrency() {
        return mCurrency;
    }

    public void setmCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_submit_order,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShoppingCartResponse.DataBean bean = mDataList.get(position);
        Glide.with(mContext)
                .load("http://jwbucket.oss-cn-shanghai.aliyuncs.com/"+bean.getProductImg())
                .error(R.mipmap.jzz_img)
                .into(holder.mIvSubmitImage);
        holder.mTvSubmitName.setText(bean.getProductName()); //商品名称
        holder.mTvSubmitNumber.setText(bean.getProductCode()); //商品编号
        holder.mTvSubmitOnePieceMoney.setText(getmCurrency()+" "+ActivityUtils.changeMoneys(bean.getPrice()));  //商品单价
        holder.mTvSubMitAmount.setText("x"+bean.getCount()); //商品总数量

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_submit_image)
        ImageView mIvSubmitImage;
        @BindView(R.id.tv_submit_name)
        TextView mTvSubmitName;
        @BindView(R.id.tv_submit_number)
        TextView mTvSubmitNumber;
        @BindView(R.id.tv_submit_one_piece_money)
        TextView mTvSubmitOnePieceMoney;
        @BindView(R.id.tv_submit_amount)
        TextView mTvSubMitAmount;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
