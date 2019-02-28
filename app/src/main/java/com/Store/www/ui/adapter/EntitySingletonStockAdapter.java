package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.EntitySingletonStockResponse;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/10/11.
 * 实体仓库单件商品库存适配器
 */

public class EntitySingletonStockAdapter extends BaseRecyclerViewAdapter<EntitySingletonStockResponse.ListBean,
        EntitySingletonStockAdapter.ViewHolder>{
    OnEntityRepertoryItemClickListener mListener;
    public int[] RepertorySum; //用来存放商品信息
    private int repertoryNumber,mSum;

    public int getmSum() {
        return mSum;
    }

    public void setmSum(int mSum) {
        this.mSum = mSum;
    }

    public int[] getRepertorySum() {
        return RepertorySum;
    }

    public void setRepertorySum(int[] repertorySum) {
        RepertorySum = repertorySum;
    }

    public EntitySingletonStockAdapter(Context context, OnEntityRepertoryItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pick_up_repertory,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final EntitySingletonStockResponse.ListBean bean = getDataList().get(position);

        holder.mTvRepertoryStocks.setVisibility(View.INVISIBLE);
        LogUtils.d("商品颜色=="+bean.getColor());
        holder.mTvRepertoryName.setText(bean.getColor()+"|"+bean.getSize());
        holder.mTvRepertoryNumber.setText(RepertorySum[position]+"");  //设置初始值
        holder.mTvRepertoryNumber.setTag(position); //设置标记
        holder.mIvRepertoryMinus.setOnClickListener(new View.OnClickListener() {  //减号的点击事件
            @Override
            public void onClick(View v) {
                int number = 0;
                repertoryNumber = RepertorySum[position];
                if (repertoryNumber > 0) {
                    repertoryNumber--;
                    for (int i = 0; i < RepertorySum.length; i++) {
                        RepertorySum[position] = repertoryNumber;
                    }
                    for (int i = 0; i < RepertorySum.length; i++) {
                        LogUtils.d("mRepertorySum[i]" + RepertorySum[i]);
                        number += RepertorySum[i];
                    }
                    setmSum(number);
                    LogUtils.d("总数==" + number);
                } else {
                    repertoryNumber = 0;
                }
                holder.mTvRepertoryNumber.setText(repertoryNumber+"");
                mListener.setEntityMinusClickListener(position,getmSum(),repertoryNumber);
            }
        });

        holder.mIvRepertoryPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberTwo = 0;
                repertoryNumber = RepertorySum[position];
                if (repertoryNumber < 9999) {
                    repertoryNumber++;
                    for (int i = 0; i < RepertorySum.length; i++) {
                        RepertorySum[position] = repertoryNumber;
                    }
                    for (int i = 0; i < RepertorySum.length; i++) {
                        LogUtils.d("mRepertorySum[i]" + RepertorySum[i]);
                        numberTwo += RepertorySum[i];
                    }
                    setmSum(numberTwo);
                    LogUtils.d("总数==" + numberTwo);
                } else {
                    repertoryNumber = 9999;
                }
                holder.mTvRepertoryNumber.setText(repertoryNumber+"");
                LogUtils.d("点击加号"+getmSum());
                mListener.setEntityPlusClickListener(position,getmSum(),repertoryNumber);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_repertory_name)
        TextView mTvRepertoryName;  //颜色
        @BindView(R.id.tv_repertory_stocks)
        TextView mTvRepertoryStocks; //库存数量
        @BindView(R.id.iv_repertory_minus)
        ImageView mIvRepertoryMinus; //减号
        @BindView(R.id.tv_repertory_number)
        TextView mTvRepertoryNumber; //提货数量
        @BindView(R.id.iv_repertory_plus)
        ImageView mIvRepertoryPlus; //加号


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnEntityRepertoryItemClickListener {
        void setEntityMinusClickListener(int position,int orderSum,int count);
        void setEntityPlusClickListener(int position,int orderSum,int count);
    }

}
