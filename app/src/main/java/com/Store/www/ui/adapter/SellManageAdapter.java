package com.Store.www.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.SellManageResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 销售管理适配器
 */

public class SellManageAdapter extends BaseRecyclerViewAdapter<SellManageResponse.DataBean,SellManageAdapter.ViewHolder>{

    onClickListener mListener;
    private int TabPosition;

    public int getTabPosition() {
        return TabPosition;
    }

    public void setTabPosition(int tabPosition) {
        TabPosition = tabPosition;
    }

    public SellManageAdapter(Context context, onClickListener listener) {
        super(context);
        mListener =listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sell_manage,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SellManageResponse.DataBean bean = mDataList.get(position);
        holder.mTvSellNumber.setText(bean.getOrderNumber()); //订单编号
        holder.mTvSellName.setText(bean.getReceiveName()+" ");    //姓名
        holder.mTvSellPhone.setText(bean.getMobilephone()+"");  //联系电话
        holder.mTvSellAddress.setText(bean.getAddress());  //地址
        holder.mTvSellAmount.setText(bean.getCount()+"件商品");
        holder.mTvSellMoney.setText("共"+bean.getCurrency()+" "+ActivityUtils.changeMoneys(bean.getTotal())+"元");
        Glide.with(mContext)
                .load(bean.getProductImage())
                .error(R.mipmap.commodity_nonentity)
                .crossFade()
                .priority(Priority.HIGH)
                .into(holder.mIvSellCard);
        long time = bean.getExpiryTime();
        if (time!=0){
            LogUtils.d("time=="+time);
            SpannableStringBuilder builder = new SpannableStringBuilder("订单失效时间: "+ ActivityUtils.time(bean.getExpiryTime())+"！");
            ForegroundColorSpan color = new ForegroundColorSpan(Color.parseColor("#f72d2d"));  //设置字体颜色
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(40);
            builder.setSpan(color,8,24, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            builder.setSpan(sizeSpan,8,24,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTvSellHintTime.setVisibility(View.VISIBLE);
            holder.mTvSellHintTime.setText(builder);
        }else {
            holder.mTvSellHintTime.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickListener(position,bean.getOrderNumber(),bean.getStatus(),bean.getCurrency(),bean.getIsIShow());
            }
        });
        switch (TabPosition){ //选择的Tab
            case 0:  //全部
                if (bean.getStatus()==0){  //待发货
                    holder.mTvSellStatus.setText(R.string.stay_shipments);
                    holder.mTvSellButton.setVisibility(View.VISIBLE);
                    holder.mTvSellHintTime.setVisibility(View.INVISIBLE);
                    holder.mTvSellButton.setText("物流发货");
                    holder.mTvSellButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onShipmentsClickListener(position,bean.getOrderNumber());
                        }
                    });
                }else if (bean.getStatus()==1){//已发货
                    holder.mTvSellStatus.setText(R.string.already_shipments);
                    holder.mTvSellButton.setVisibility(View.VISIBLE);
                    holder.mTvSellHintTime.setVisibility(View.INVISIBLE);
                    holder.mTvSellButton.setText(R.string.look_adders);
                    holder.mTvSellButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onAddressClickListener(position,bean.getOrderNumber());
                        }
                    });
                }else if (bean.getStatus()==2){ //已完成
                    holder.mTvSellStatus.setText(R.string.order_over);
                    holder.mTvSellButton.setVisibility(View.VISIBLE);
                    holder.mTvSellHintTime.setVisibility(View.INVISIBLE);
                    holder.mTvSellButton.setText(R.string.look_adders);
                    holder.mTvSellButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onAddressClickListener(position,bean.getOrderNumber());
                        }
                    });
                }else if (bean.getStatus()==3){  //待付款
                    holder.mTvSellStatus.setText(R.string.stay_pay);
                    holder.mTvSellButton.setVisibility(View.INVISIBLE);
                }else if (bean.getStatus()==4){  //待确认
                    if (bean.getIsIShow()==1){ //如果是爱瘦订单
                        holder.mTvSellStatus.setText(R.string.stay_ok);
                        holder.mTvSellButton.setVisibility(View.VISIBLE);
                        holder.mTvSellHintTime.setVisibility(View.INVISIBLE);
                        holder.mTvSellButton.setText("接受订单");
                    }else {
                        holder.mTvSellStatus.setText(R.string.stay_ok);
                        holder.mTvSellButton.setVisibility(View.VISIBLE);
                        holder.mTvSellHintTime.setVisibility(View.INVISIBLE);
                        holder.mTvSellButton.setText(R.string.pay_affirm);
                    }
                    holder.mTvSellButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onPayClickListener(position,bean.getOrderNumber(),bean.getIsIShow());
                        }
                    });
                }else if (bean.getStatus()==99){ //已取消
                    holder.mTvSellButton.setVisibility(View.INVISIBLE);
                    holder.mTvSellHintTime.setVisibility(View.INVISIBLE);
                    holder.mTvSellStatus.setText(R.string.is_cancel);
                }else {
                    holder.mTvSellButton.setVisibility(View.INVISIBLE);
                    holder.mTvSellHintTime.setVisibility(View.INVISIBLE);
                }
                break;
            case 1: //待确认
                holder.mTvSellStatus.setText(R.string.stay_ok);
                holder.mTvSellButton.setVisibility(View.VISIBLE);
                holder.mTvSellButton.setText(R.string.pay_affirm);
                holder.mTvSellButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onPayClickListener(position,bean.getOrderNumber(),bean.getIsIShow());
                    }
                });
                break;
            case 2: //待发货
                holder.mTvSellStatus.setText(R.string.stay_shipments);
                holder.mTvSellButton.setVisibility(View.VISIBLE);
                holder.mTvSellButton.setText("物流发货");
                holder.mTvSellButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onShipmentsClickListener(position,bean.getOrderNumber());
                    }
                });
                break;
            case 3: //已发货
                holder.mTvSellStatus.setText(R.string.already_shipments);
                holder.mTvSellButton.setVisibility(View.VISIBLE);
                holder.mTvSellButton.setText(R.string.look_adders);
                holder.mTvSellButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onAddressClickListener(position,bean.getOrderNumber());
                    }
                });
                break;
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_sell_number)
        TextView mTvSellNumber;  //订单编号
        @BindView(R.id.tv_sell_name)
        TextView mTvSellName;       //姓名
        @BindView(R.id.tv_sell_phone)
        TextView mTvSellPhone;  //电话号码
        @BindView(R.id.tv_sell_address)
        TextView mTvSellAddress;    //地址
        @BindView(R.id.tv_sell_amount)
        TextView mTvSellAmount;  //数量
        @BindView(R.id.tv_sell_status)
        TextView mTvSellStatus;  //订单状态
        @BindView(R.id.tv_sell_button)
        TextView mTvSellButton;
        @BindView(R.id.tv_sell_hint_time)
        TextView mTvSellHintTime;  //提示时间
        @BindView(R.id.tv_sell_money)
        TextView mTvSellMoney;
        @BindView(R.id.iv_sell_card)
        ImageView mIvSellCard;  //展示商品图片

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface onClickListener{
        void onItemClickListener(int position,String orderNumber, int status,String currency,int isIShow);
        void onPayClickListener(int position,String orderNumber,int isIShow);  //支付确认的点击事件
        void onShipmentsClickListener(int position,String orderNumber); //发货的点击事件
        void onAddressClickListener(int position,String orderNumber); //查看物流的点击事件
    }
}
