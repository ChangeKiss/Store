package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.PickUpGoodsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提货管理的适配器
 */

public class PickUpGoodsAdapter extends BaseRecyclerViewAdapter<PickUpGoodsResponse.DataBean,PickUpGoodsAdapter.ViewHolder>{

    private int mTabNumber; //获取顶部tab的位置
    OnButtonClickListener mListener;

    public int getmTabNumber() {
        return mTabNumber;
    }

    public void setmTabNumber(int mTabNumber) {
        this.mTabNumber = mTabNumber;
    }

    public PickUpGoodsAdapter(Context context,OnButtonClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pick_up_goods,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PickUpGoodsResponse.DataBean bean = mDataList.get(position);
        holder.mTvPickUpNumber.setText(bean.getOrderNumber()+"");
        holder.mTvPickUpName.setText(bean.getReceiver()+"");
        holder.mTvPickUpPhone.setText(bean.getMobilephone()+"");
        holder.mTvPickUpAdders.setText(bean.getAddress()+"");
        holder.mTvPickUpCount.setText(bean.getCount()+"件商品");
        Glide.with(mContext)
                .load(bean.getProductImage())
                .error(R.mipmap.commodity_nonentity)
                .crossFade()
                .priority(Priority.HIGH)
                .into(holder.mIvPickUpImages);
        switch (mTabNumber){
            case 0:
                if (bean.getStatus()==0){ //订单状态为待发货时
                    holder.mTvPickUpState.setText(R.string.stay_shipments);
                    holder.mTvLookPiclUpAdders.setEnabled(true);
                    holder.mTvLookPiclUpAdders.setVisibility(View.VISIBLE);
                    holder.mTvLookPiclUpAdders.setText(R.string.order_no); //右下角按钮为取消订单
                    holder.mTvLookPiclUpAdders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.OnCancelOrderClickListener(position,bean.getOrderNumber(),bean.getUserId());
                        }
                    });
                    if (bean.getPayExpressStats()==1){ //需要支付运费
                        holder.mTvPayExpress.setText("支付运费");
                        holder.mTvPayExpress.setVisibility(View.VISIBLE);
                        holder.mTvPayExpress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mListener.OnPayExpressClickListener(position,bean.getOrderNumber());
                            }
                        });
                    }else {
                        holder.mTvPayExpress.setVisibility(View.GONE);
                    }
                }else if (bean.getStatus()==1){  //订单状态为发货中时
                    holder.mTvPickUpState.setText(R.string.shipments_loading);
                    holder.mTvLookPiclUpAdders.setEnabled(true);
                    holder.mTvLookPiclUpAdders.setVisibility(View.VISIBLE);
                    holder.mTvLookPiclUpAdders.setText(R.string.look_adders);//右下角按钮为查看物流
                    holder.mTvPayExpress.setVisibility(View.GONE);  //支付运费按钮
                    holder.mTvLookPiclUpAdders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.OnButtonClickListener(position,bean.getOrderNumber());
                        }
                    });
                }else if (bean.getStatus()==5){ //订单状态为已完成时
                    holder.mTvPickUpState.setText(R.string.order_over);
                    holder.mTvLookPiclUpAdders.setEnabled(true);
                    holder.mTvLookPiclUpAdders.setVisibility(View.VISIBLE);
                    holder.mTvLookPiclUpAdders.setText(R.string.look_adders);//右下角按钮为查看物流
                    holder.mTvPayExpress.setVisibility(View.GONE);  //支付运费按钮
                    holder.mTvLookPiclUpAdders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.OnButtonClickListener(position,bean.getOrderNumber());
                        }
                    });
                }else if (bean.getStatus()==99){
                    holder.mTvPickUpState.setText("已取消");
                    holder.mTvPayExpress.setVisibility(View.GONE);  //支付运费按钮
                    holder.mTvLookPiclUpAdders.setVisibility(View.INVISIBLE);

                }
                break;
            case 1://待发货时
                holder.mTvPickUpState.setText(R.string.stay_shipments);
                holder.mTvLookPiclUpAdders.setEnabled(true);
                holder.mTvLookPiclUpAdders.setVisibility(View.VISIBLE);
                holder.mTvLookPiclUpAdders.setText(R.string.order_no);//右下角按钮为取消订单
                holder.mTvLookPiclUpAdders.setOnClickListener(new View.OnClickListener() { //取消订单
                    @Override
                    public void onClick(View v) {
                        mListener.OnCancelOrderClickListener(position,bean.getOrderNumber(),bean.getUserId());
                    }
                });
                if (bean.getPayExpressStats()==1){ //需要支付运费
                    holder.mTvPayExpress.setText("支付运费");
                    holder.mTvPayExpress.setVisibility(View.VISIBLE);
                    holder.mTvPayExpress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.OnPayExpressClickListener(position,bean.getOrderNumber());
                        }
                    });
                }else {
                    holder.mTvPayExpress.setVisibility(View.GONE);  //支付运费按钮
                }
                break;
            case 2: //发货中时
                holder.mTvPickUpState.setText(R.string.shipments_loading);
                holder.mTvLookPiclUpAdders.setEnabled(true);
                holder.mTvLookPiclUpAdders.setVisibility(View.VISIBLE);
                holder.mTvLookPiclUpAdders.setText(R.string.look_adders);//右下角按钮为查看物流
                holder.mTvPayExpress.setVisibility(View.GONE);  //支付运费按钮
                holder.mTvLookPiclUpAdders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.OnButtonClickListener(position,bean.getOrderNumber()); //查看物流
                    }
                });
                break;
            case 3: //已完成时
                holder.mTvPickUpState.setText(R.string.order_over);
                holder.mTvLookPiclUpAdders.setEnabled(true);
                holder.mTvLookPiclUpAdders.setVisibility(View.VISIBLE);
                holder.mTvLookPiclUpAdders.setText(R.string.look_adders);//右下角按钮为查看物流
                holder.mTvPayExpress.setVisibility(View.GONE);  //支付运费按钮
                holder.mTvLookPiclUpAdders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.OnButtonClickListener(position,bean.getOrderNumber()); //查看物流
                    }
                });
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClickListener(position,bean.getOrderNumber());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_pick_up_number)
        TextView mTvPickUpNumber; //编号
        @BindView(R.id.tv_pick_up_name)
        TextView mTvPickUpName;  //姓名
        @BindView(R.id.tv_pick_up_phone)
        TextView mTvPickUpPhone; //电话
        @BindView(R.id.tv_pick_up_adders)
        TextView mTvPickUpAdders;  //地址
        @BindView(R.id.tv_pick_up_count)
        TextView mTvPickUpCount;  //商品数量
        @BindView(R.id.tv_pay_express)
        TextView mTvPayExpress;  //支付运费按钮
        @BindView(R.id.tv_look_pick_up_adders)
        TextView mTvLookPiclUpAdders; //右下角的取消订单和查看物流的按钮
        @BindView(R.id.tv_pick_up_state)
        TextView mTvPickUpState; //订单状态
        @BindView(R.id.iv_pick_up_images)
        ImageView mIvPickUpImages;  //商品图片

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnButtonClickListener{
        void OnButtonClickListener(int position,String orderNumber);
        void OnCancelOrderClickListener(int position,String orderNumber,int userIdInt);
        void OnItemClickListener(int position,String orderNumber);
        void OnPayExpressClickListener(int position,String orderNumber);
    }
}
