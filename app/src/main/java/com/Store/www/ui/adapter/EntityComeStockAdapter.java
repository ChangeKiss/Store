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
 * Created by www on 2018/10/15.
 * 实体商品出库适配器
 */

public class EntityComeStockAdapter extends BaseRecyclerViewAdapter<EntitySingletonStockResponse.ListBean,EntityComeStockAdapter.ViewHolder>{

    OnComeStockItemClickListener mListener;
    public int[] mComeStockSum; //用来存放出库商品信息
    private int ComeStockNumber,mSum;

    public int[] getmComeStockSum() {
        return mComeStockSum;
    }

    public void setmComeStockSum(int[] mComeStockSum) {
        this.mComeStockSum = mComeStockSum;
    }

    public int getmSum() {
        return mSum;
    }

    public void setmSum(int mSum) {
        this.mSum = mSum;
    }

    public EntityComeStockAdapter(Context context, OnComeStockItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_entity_come_stock,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final EntitySingletonStockResponse.ListBean bean = mDataList.get(position);
        holder.mTvComeStockName.setText(bean.getColor()+"|"+bean.getSize());  //颜色尺码
        holder.mTvStockNumber.setText(bean.getRepositoryCount()+"");  //库存数量
        holder.mTvComeStockNumber.setText(mComeStockSum[position]+"");  //设置初始值
        holder.mTvComeStockNumber.setTag(position);
        holder.mIvComeStockMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = 0;
                ComeStockNumber = mComeStockSum[position];
                if (ComeStockNumber > 0) {
                    ComeStockNumber--;
                    for (int i = 0; i < mComeStockSum.length; i++) {
                        mComeStockSum[position] = ComeStockNumber;
                    }
                    for (int i = 0; i < mComeStockSum.length; i++) {
                        LogUtils.d("mComeStockSum[i]" + mComeStockSum[i]);
                        number += mComeStockSum[i];
                    }
                    setmSum(number);
                    LogUtils.d("总数==" + number);
                } else {
                    ComeStockNumber = 0;
                }
                holder.mTvComeStockNumber.setText(ComeStockNumber+"");
                mListener.setComeStockMinusClickListener(position,getmSum(),ComeStockNumber);
            }
        });

        holder.mIvComeStockPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberTwo = 0;
                ComeStockNumber = mComeStockSum[position];
                /*if (ComeStockNumber < bean.getRepositoryCount()) {*/
                ComeStockNumber++;
                for (int i = 0; i < mComeStockSum.length; i++) {
                    mComeStockSum[position] = ComeStockNumber;
                }
                for (int i = 0; i < mComeStockSum.length; i++) {
                    LogUtils.d("mRepertorySum[i]" + mComeStockSum[i]);
                    numberTwo += mComeStockSum[i];
                }
                setmSum(numberTwo);
                LogUtils.d("总数==" + numberTwo);
                /*} else {
                    ComeStockNumber = bean.getRepositoryCount();
                }*/
                holder.mTvComeStockNumber.setText(ComeStockNumber+"");
                LogUtils.d("点击加号"+getmSum());
                mListener.setComeStockPlusClickListener(position,getmSum(),ComeStockNumber);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_come_stock_name)
        TextView mTvComeStockName;  //商品名称
        @BindView(R.id.tv_stock_number)
        TextView mTvStockNumber;  //库存数量
        @BindView(R.id.iv_come_stock_minus)
        ImageView mIvComeStockMinus;  //减号
        @BindView(R.id.tv_come_stock_number)
        TextView mTvComeStockNumber;  //出库数量
        @BindView(R.id.iv_come_stock_plus)
        ImageView mIvComeStockPlus;  //加号

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnComeStockItemClickListener {
        void setComeStockMinusClickListener(int position,int orderSum,int count);
        void setComeStockPlusClickListener(int position,int orderSum,int count);
    }

}
