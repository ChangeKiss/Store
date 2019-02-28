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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.ShoppingCartResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车的适配器
 */

public class ShoppingTrolleyAdapter extends BaseRecyclerViewAdapter<ShoppingCartResponse.DataBean,ShoppingTrolleyAdapter.ViewHolder>{

    OnOrderItemClickListener mListener;
    private boolean ischeck;
    private String mCurrency;

    public String getmCurrency() {
        return mCurrency;
    }

    public void setmCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    List<ShoppingCartResponse.DataBean> mCartList = new ArrayList<>();


    public List<ShoppingCartResponse.DataBean> getmCartList() {
        return mCartList;
    }

    public void setmCartList(List<ShoppingCartResponse.DataBean> mCartList) {
        this.mCartList = mCartList;
    }

    public ShoppingTrolleyAdapter(Context context, OnOrderItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_shopping_trolley,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ShoppingCartResponse.DataBean data = mDataList.get(position);
        final ShoppingCartResponse.DataBean dataBean = mDataList.get(position);
        setmCurrency(dataBean.getCurrency());
        long time = dataBean.getExpiryTime();
        if (time!=0){
            LogUtils.d("time=="+time);
            SpannableStringBuilder builder = new SpannableStringBuilder("商品失效时间: "+ActivityUtils.time(dataBean.getExpiryTime())+"！请及时结算！");
            ForegroundColorSpan color = new ForegroundColorSpan(Color.parseColor("#f72d2d"));  //设置字体颜色
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(40);
            builder.setSpan(color,8,24, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            builder.setSpan(sizeSpan,8,24,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mTvLoseEfficacyTime.setVisibility(View.VISIBLE);
            holder.mTvLoseEfficacyTime.setText(builder);
        }else {
            holder.mTvLoseEfficacyTime.setVisibility(View.GONE);
        }

        Glide.with(mContext)
                .load("http://jwbucket.oss-cn-shanghai.aliyuncs.com/"+dataBean.getProductImg())
                .error(R.mipmap.jzz_img)
                .into(holder.mIvShoppingImage);
        holder.mTvShoppingName.setText(dataBean.getProductName());
        holder.mTvShoppingNumber.setText(""+dataBean.getCount()); //总数

        holder.mTvShoppingMoney.setText(dataBean.getCurrency()+" "+ActivityUtils.changeMoneys(dataBean.getPrice())); //单价
        //在初始化CheckBox状态和设置状态变化监听事件之前，先把状态变化监听事件设置为null
        holder.mCbShoppingChoose.setOnCheckedChangeListener(null);
        if (data.isCheck()){  //然后更改复选框的状态
            holder.mCbShoppingChoose.setChecked(true);
        }else {
            holder.mCbShoppingChoose.setChecked(false);
        }
        holder.mCbShoppingChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.setCheck(isChecked);
                mListener.setOnCheckboxChangeListener(isChecked,position,holder.mCbShoppingChoose);
            }
        });

        holder.mCbShoppingChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.getCartIdClickListener(position,dataBean.getProductId(),dataBean.getCount());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setOnItemClickListener(position,dataBean.getId(),dataBean.getProductId(),dataBean.getProductName());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cb_shopping_choose)
        CheckBox mCbShoppingChoose;
        @BindView(R.id.iv_shopping_image)
        ImageView mIvShoppingImage;
        @BindView(R.id.tv_shopping_name)
        TextView mTvShoppingName;
        @BindView(R.id.tv_shopping_money)
        TextView mTvShoppingMoney;
        @BindView(R.id.tv_shopping_number)
        TextView mTvShoppingNumber;
        @BindView(R.id.tv_lose_efficacy_time)
        TextView mTvLoseEfficacyTime; //

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnOrderItemClickListener {

        void setOnCheckboxChangeListener(boolean isChecked,int position,CheckBox checkBox);

        void getCartIdClickListener(int position,int productId,int number);

        void setOnItemClickListener(int position, int cartId,int product,String title);

    }





}
