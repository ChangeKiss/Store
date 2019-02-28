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
import com.Store.www.entity.MyOrderResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2017/12/14.
 */

    //我的订单的适配器
public class MyOrderAdapter extends BaseRecyclerViewAdapter<MyOrderResponse.DataBean,MyOrderAdapter.ViewHolder>{

    int mTabPosition;
    OnOrderButtonClickListener mListener;


    public int getTabPosition() {
        return mTabPosition;
    }

    public void setTabPosition(int mTabPosition) {
        this.mTabPosition = mTabPosition;
    }

    public MyOrderAdapter(Context context,OnOrderButtonClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_my_order,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder  holder,final int position) {
        final MyOrderResponse.DataBean dataBean = mDataList.get(position);
        holder.mTvOrdersNumber.setText(""+dataBean.getOrderNumber());  //订单编号
        holder.mTvOrdersName.setText(dataBean.getReceiveName());  //姓名
        holder.mTvOrdersphone.setText(""+dataBean.getMobilephone());  //电话
        holder.mTvOrdersAdders.setText(""+dataBean.getAddress());  //地址
        Glide.with(mContext)
               .load(dataBean.getProductImage())
               .crossFade()
               .priority(Priority.HIGH)
               .error(R.mipmap.commodity_nonentity)
               .into(holder.mIvOrdersImages);
        holder.mTvTotalCount.setText(dataBean.getCount()+"件商品,共"+dataBean.getCurrency()+" "+ActivityUtils.changeMoneys(dataBean.getTotal())+"");
        long time = dataBean.getExpiryTime();
        if (time!=0&&dataBean.getStatus()==3){
            LogUtils.d("time=="+time);
            SpannableStringBuilder builder = new SpannableStringBuilder("订单失效时间: "+ActivityUtils.time(dataBean.getExpiryTime()));
            ForegroundColorSpan color = new ForegroundColorSpan(Color.parseColor("#f72d2d"));  //设置字体颜色
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(40);
            builder.setSpan(color,8,24, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            builder.setSpan(sizeSpan,8,24,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTvHintTime.setVisibility(View.VISIBLE);
            holder.mTvHintTime.setText(builder);
        }else {
            holder.mTvHintTime.setVisibility(View.INVISIBLE);
        }
        if (mTabPosition==0){ //全部
            LogUtils.d("被选中的=01=="+mTabPosition);
            if (dataBean.getStatus()==0){
                holder.mTvOrdersState.setText(R.string.stay_shipments);
                holder.mTvCancelOrder.setVisibility(View.INVISIBLE);
                holder.mTvPayOrder.setVisibility(View.INVISIBLE);
            }else if (dataBean.getStatus()==1){
                holder.mTvOrdersState.setText(R.string.shipments_loading);
                holder.mTvCancelOrder.setVisibility(View.VISIBLE);  //功能目前未开放先隐藏起来
                holder.mTvCancelOrder.setText("确认收货");
                holder.mTvCancelOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.setConfirmOrderClickListener(position,dataBean.getOrderNumber());
                    }
                });
                holder.mTvPayOrder.setVisibility(View.VISIBLE);
                holder.mTvPayOrder.setText("查看物流");
                holder.mTvPayOrder.setOnClickListener(new View.OnClickListener() { //查看物流的点击事件
                    @Override
                    public void onClick(View v) {
                        mListener.getExpressNumberClickListener(position,dataBean.getOrderNumber());
                    }
                });
            }else if (dataBean.getStatus()==2){
                holder.mTvOrdersState.setText(R.string.order_over);
                holder.mTvCancelOrder.setVisibility(View.INVISIBLE);
                holder.mTvPayOrder.setVisibility(View.VISIBLE);
                holder.mTvPayOrder.setText("查看物流");
                holder.mTvPayOrder.setOnClickListener(new View.OnClickListener() { //查看物流的点击事件
                    @Override
                    public void onClick(View v) {
                        mListener.getExpressNumberClickListener(position,dataBean.getOrderNumber());
                    }
                });
            }else if (dataBean.getStatus()==3){
                holder.mTvOrdersState.setText(R.string.stay_pay);
                holder.mTvCancelOrder.setVisibility(View.VISIBLE);
                holder.mTvCancelOrder.setText("取消订单");
                holder.mTvCancelOrder.setOnClickListener(new View.OnClickListener() {  //取消订单的点击事件
                    @Override
                    public void onClick(View v) {
                        mListener.setCancelOrderClickListener(position,dataBean.getOrderNumber());
                    }
                });
                holder.mTvPayOrder.setVisibility(View.VISIBLE);
                holder.mTvPayOrder.setText("支付订单");
                holder.mTvPayOrder.setOnClickListener(new View.OnClickListener() {  //支付订单的点击事件
                    @Override
                    public void onClick(View v) {
                        mListener.setPayOrderClickListener(position,dataBean.getReceiveName(),dataBean.getMobilephone(),
                                dataBean.getAddress(),dataBean.getOrderNumber(),dataBean.getTypeName(),dataBean.getCount(),dataBean.getTotal(),
                                dataBean.getCurrency(),dataBean.getCreateTime(),dataBean.getIsUseBalance(),dataBean.getUseBalance());
                    }
                });
            }else if (dataBean.getStatus()==4){
                holder.mTvOrdersState.setText(R.string.stay_shipments);
                holder.mTvCancelOrder.setVisibility(View.INVISIBLE);
                holder.mTvPayOrder.setVisibility(View.INVISIBLE);
            }else if (dataBean.getStatus()==5){
                holder.mTvOrdersState.setText("已结案");
                holder.mTvCancelOrder.setVisibility(View.INVISIBLE);
                holder.mTvPayOrder.setVisibility(View.INVISIBLE);
            }else if (dataBean.getStatus()==98){
                holder.mTvOrdersState.setText("已锁定");
                holder.mTvCancelOrder.setVisibility(View.INVISIBLE);
                holder.mTvPayOrder.setVisibility(View.INVISIBLE);
            }else if (dataBean.getStatus()==99){
                holder.mTvOrdersState.setText("已取消");
                holder.mTvCancelOrder.setVisibility(View.INVISIBLE);
                holder.mTvPayOrder.setVisibility(View.VISIBLE);
                holder.mTvPayOrder.setText("删除订单");
                holder.mTvPayOrder.setOnClickListener(new View.OnClickListener() {  //删除订单
                    @Override
                    public void onClick(View v) {
                        mListener.setDeleteClickListener(position,dataBean.getOrderNumber());
                    }
                });
            }
        }else if (mTabPosition==1){
            holder.mTvOrdersState.setText(R.string.stay_pay);
            holder.mTvCancelOrder.setVisibility(View.VISIBLE);
            holder.mTvCancelOrder.setText("取消订单");
            holder.mTvCancelOrder.setOnClickListener(new View.OnClickListener() {  //取消订单的点击事件
                @Override
                public void onClick(View v) {
                    mListener.setCancelOrderClickListener(position,dataBean.getOrderNumber());
                }
            });
            holder.mTvPayOrder.setVisibility(View.VISIBLE);
            holder.mTvPayOrder.setText("支付订单");
            holder.mTvPayOrder.setOnClickListener(new View.OnClickListener() {  //支付订单的点击事件
                @Override
                public void onClick(View v) {
                    mListener.setPayOrderClickListener(position,dataBean.getReceiveName(),dataBean.getMobilephone(),
                            dataBean.getAddress(),dataBean.getOrderNumber(),dataBean.getTypeName(),dataBean.getCount(),dataBean.getTotal(),
                            dataBean.getCurrency(),dataBean.getCreateTime(),dataBean.getIsUseBalance(),dataBean.getUseBalance());
                }
            });
        }else if (mTabPosition==2){
            holder.mTvOrdersState.setText(R.string.stay_shipments);
            holder.mTvCancelOrder.setVisibility(View.INVISIBLE);
            holder.mTvPayOrder.setVisibility(View.INVISIBLE);
        }else if (mTabPosition==3){
            holder.mTvOrdersState.setText(R.string.shipments_loading);
            holder.mTvCancelOrder.setVisibility(View.VISIBLE);  //功能目前未开放先隐藏起来
            holder.mTvCancelOrder.setText("确认收货");
            holder.mTvCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setConfirmOrderClickListener(position,dataBean.getOrderNumber());
                }
            });
            holder.mTvPayOrder.setVisibility(View.VISIBLE);
            holder.mTvPayOrder.setText("查看物流");
            holder.mTvPayOrder.setOnClickListener(new View.OnClickListener() { //查看物流的点击事件
                @Override
                public void onClick(View v) {
                    mListener.getExpressNumberClickListener(position,dataBean.getOrderNumber());
                }
            });
        }else if (mTabPosition==4){
            holder.mTvOrdersState.setText(R.string.order_over);
            holder.mTvCancelOrder.setVisibility(View.INVISIBLE);
            holder.mTvPayOrder.setVisibility(View.VISIBLE);
            holder.mTvPayOrder.setText("查看物流");
            holder.mTvPayOrder.setOnClickListener(new View.OnClickListener() {  //查看物流的点击事件
                @Override
                public void onClick(View v) {
                    mListener.getExpressNumberClickListener(position,dataBean.getOrderNumber());
                }
            });
        }
        //订单详情的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.getItemIdClickListener(position,dataBean.getOrderNumber(),dataBean.getCurrency(),dataBean.getStatus());
            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_orders_number)
        TextView mTvOrdersNumber;
        @BindView(R.id.tv_orders_name)
        TextView mTvOrdersName;
        @BindView(R.id.tv_orders_phone)
        TextView mTvOrdersphone;
        @BindView(R.id.tv_orders_adders)
        TextView mTvOrdersAdders;
        @BindView(R.id.tv_total_count)
        TextView mTvTotalCount;
        @BindView(R.id.tv_orders_state)
        TextView mTvOrdersState;
        @BindView(R.id.tv_pay_order)
        TextView mTvPayOrder; //支付订单
        @BindView(R.id.tv_cancel_order)
        TextView mTvCancelOrder;   //取消订单
        @BindView(R.id.tv_hint_time)
        TextView mTvHintTime; //提示失效时间
        @BindView(R.id.iv_orders_images)
        ImageView mIvOrdersImages;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnOrderButtonClickListener {
        void getItemIdClickListener(int position,String orderNo,String currency,int status);
        void getExpressNumberClickListener(int position , String orderNo);
        void setCancelOrderClickListener(int position,String orderNumber);
        void setPayOrderClickListener(int position,String name,String phone,String address,String number,String typeName,
                                      int count,int money,String currency,long createTime,int isUseBalance,int useBalance);
        void setDeleteClickListener(int position,String orderNo);
        void setConfirmOrderClickListener(int position,String orderNo);
    }

}
