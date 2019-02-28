package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.PickUpRepertoryResponse;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 单件商品的提货库存适配器
 */

public class PickUpRepertoryAdapter extends BaseRecyclerViewAdapter<PickUpRepertoryResponse.DataBean.ListBean,
        PickUpRepertoryAdapter.ViewHolder>{

    public int[] mRepertorySum; //用来存放商品信息
    private int repertoryNumber,mSum;
    public int totalCount;  //我的库存总数
    OnRepertoryItemClickListener mListener;

    public int getmSum() {
        return mSum;
    }

    public void setmSum(int mSum) {
        this.mSum = mSum;
    }

    public int[] getmRepertorySum() {
        return mRepertorySum;
    }

    public void setmRepertorySum(int[] mRepertorySum) {
        this.mRepertorySum = mRepertorySum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public PickUpRepertoryAdapter(Context context, OnRepertoryItemClickListener listener) {
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
        final PickUpRepertoryResponse.DataBean.ListBean bean = mDataList.get(position);
        if (bean.getIsEnable()==0 ){  //判断当前商品是否可选择  0不可选择
            holder.mIvRepertoryPlus.setImageResource(R.mipmap.plus_no);
            holder.mIvRepertoryPlus.setEnabled(false);
        }else if (bean.getIsEnable()==1){  //1可以选择
            holder.mIvRepertoryPlus.setImageResource(R.mipmap.plus_icon);
            holder.mIvRepertoryPlus.setEnabled(true);
        }
        holder.mTvRepertoryName.setText(bean.getColor()+"|"+bean.getSizeCode());
        holder.mTvRepertoryStocks.setText(bean.getRepositoryCount());
        holder.mTvRepertoryNumber.setTag(position); //设置标记
        holder.mTvRepertoryNumber.setText(mRepertorySum[position]+"");  //设置初始值

        holder.mIvRepertoryPlus.setOnClickListener(new View.OnClickListener() {  //加号的点击事件
            @Override
            public void onClick(View v) {
                int countNumber = 0;
                for (int i=0;i<mRepertorySum.length;i++){
                    countNumber+=mRepertorySum[i];
                }
                if (countNumber <getTotalCount()){
                    int mOrderSum = Integer.parseInt(bean.getRepositoryCount());
                    int numberTwo = 0;
                    repertoryNumber = mRepertorySum[position];
                    if (repertoryNumber < mOrderSum) {
                        repertoryNumber++;
                        for (int i = 0; i < mRepertorySum.length; i++) {
                            mRepertorySum[position] = repertoryNumber;
                            numberTwo += mRepertorySum[i];
                        }
                        setmSum(numberTwo);
                        LogUtils.d("getTotalCount=="+getTotalCount());
                        LogUtils.d("总数==" + numberTwo);
                    } else {
                        repertoryNumber = mOrderSum;
                        for (int i = 0; i < mRepertorySum.length; i++) {
                            mRepertorySum[position] = repertoryNumber;
                            numberTwo += mRepertorySum[i];
                        }
                        setmSum(numberTwo);
                        LogUtils.d("总数==" + numberTwo);
                        LogUtils.d("getTotalCount=="+getTotalCount());
                    }
                    holder.mTvRepertoryNumber.setText(repertoryNumber+"");
                }else {
                    return;
                }
                LogUtils.d("点击加号"+getmSum());
                mListener.setPlusClickListener(position,getmSum(),repertoryNumber);
            }
        });

        holder.mIvRepertoryMinus.setOnClickListener(new View.OnClickListener() {  //减号的点击事件
            @Override
            public void onClick(View v) {
                holder.mIvRepertoryPlus.setEnabled(true);
                int number = 0;
                repertoryNumber = mRepertorySum[position];
                if (repertoryNumber > 0) {
                    repertoryNumber--;
                    for (int i = 0; i < mRepertorySum.length; i++) {
                        mRepertorySum[position] = repertoryNumber;
                        number += mRepertorySum[i];
                    }
                    setmSum(number);
                    LogUtils.d("总数==" + number);
                } else {
                    repertoryNumber = 0;
                }
                holder.mTvRepertoryNumber.setText(repertoryNumber+"");
                mListener.setMinusClickListener(position,getmSum(),repertoryNumber);
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

    public interface OnRepertoryItemClickListener {
        void setMinusClickListener(int position,int orderSum,int count);
        void setPlusClickListener(int position,int orderSum,int count);
    }
}
