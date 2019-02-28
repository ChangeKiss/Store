package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.OnePieceStocksResponse;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 单件商品尺码调整库存的适配器
 */

public class OnePieceAdjustAdapter extends BaseRecyclerViewAdapter<OnePieceStocksResponse.DataBean,
        OnePieceAdjustAdapter.ViewHolder>{

    public int[] mCountSum; //用来存放原始库存数量
    public int[] mRepertorySum; //用来存放修改后的商品数量
    private int repertoryNumber,mSum;
    OnAdjustItemClickListener mListener;
    LinearLayout.LayoutParams params;
    LinearLayout.LayoutParams paramsTwo;
    LinearLayout.LayoutParams paramsThree;

    public int[] getmRepertorySum() {
        return mRepertorySum;
    }

    public void setmRepertorySum(int[] mRepertorySum) {
        this.mRepertorySum = mRepertorySum;
    }

    public int[] getmCountSum() {
        return mCountSum;
    }

    public void setmCountSum(int[] mCountSum) {
        this.mCountSum = mCountSum;
    }

    public int getRepertoryNumber() {
        return repertoryNumber;
    }

    public void setRepertoryNumber(int repertoryNumber) {
        this.repertoryNumber = repertoryNumber;
    }

    public int getmSum() {
        return mSum;
    }

    public void setmSum(int mSum) {
        this.mSum = mSum;
    }

    public OnePieceAdjustAdapter(Context context, OnAdjustItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_one_piece_adjust,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OnePieceStocksResponse.DataBean dataBean = mDataList.get(position);
        params = (LinearLayout.LayoutParams) holder.mLayoutAdjustSizeName.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mLayoutAdjustNumber.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mTvAdjustSizeStocks.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3;
        holder.mLayoutAdjustSizeName.setLayoutParams(params);
        holder.mLayoutAdjustNumber.setLayoutParams(params);
        holder.mTvAdjustSizeStocks.setLayoutParams(params);
        paramsTwo = (LinearLayout.LayoutParams) holder.mTvAdjustLine.getLayoutParams();
        paramsTwo.leftMargin = UserPrefs.getInstance().getWidth()/3/5/4;
        holder.mTvAdjustLine.setLayoutParams(paramsTwo);
        paramsThree = (LinearLayout.LayoutParams) holder.mTvAdjustSizeName.getLayoutParams();
        paramsThree = (LinearLayout.LayoutParams) holder.mTvAdjustSize.getLayoutParams();
        paramsThree.width = UserPrefs.getInstance().getWidth()/3/2;
        holder.mTvAdjustSizeName.setLayoutParams(paramsThree);
        holder.mTvAdjustSize.setLayoutParams(paramsThree);

        if (dataBean.getIsEnable()==1){
            holder.mIvAdjustSizePlus.setEnabled(true);
            holder.mIvAdjustSizePlus.setImageResource(R.mipmap.plus_icon);
        }else{
            holder.mIvAdjustSizePlus.setEnabled(false);
            holder.mIvAdjustSizePlus.setImageResource(R.mipmap.plus_no);
        }
        holder.mTvAdjustSizeName.setText(dataBean.getColor()); //款式颜色
        holder.mTvAdjustLine.setText("|");
        holder.mTvAdjustSize.setText(dataBean.getSizeCode());  //尺码
        holder.mTvAdjustSizeStocks.setText(dataBean.getRepositoryCount()); //库存数
        holder.mTvAdjustSizeNumber.setText(mRepertorySum[position]+"");  //设置初始值
        holder.mTvAdjustSizeNumber.setTag(position);
        holder.mIvAdjustSizeMinus.setOnClickListener(new View.OnClickListener() {  //减号的点击事件
            @Override
            public void onClick(View v) {
                int number = 0;
                repertoryNumber = mRepertorySum[position];
                if (repertoryNumber+mCountSum[position]>0) {
                    repertoryNumber--;
                    for (int i = 0; i < mRepertorySum.length; i++) {
                        mRepertorySum[position] = repertoryNumber;
                    }
                    for (int i = 0; i < mRepertorySum.length; i++) {
                        LogUtils.d("mRepertorySum[i]" + mRepertorySum[i]);
                        LogUtils.d("第"+i+"款商品换货数+"+mRepertorySum[i]);
                        number += mRepertorySum[i];
                    }
                    setmSum(number);
                    LogUtils.d("总数==" + number);
                }
                holder.mTvAdjustSizeNumber.setText(repertoryNumber+"");
                mListener.setMinusClickListener(position,getmSum());
            }
        });

        holder.mIvAdjustSizePlus.setOnClickListener(new View.OnClickListener() {  //加号的点击事件
            @Override
            public void onClick(View v) {
                int numberTwo = 0;
                repertoryNumber = mRepertorySum[position];
                if (repertoryNumber < 999) {
                    repertoryNumber++;
                    for (int i = 0; i < mRepertorySum.length; i++) {
                        mRepertorySum[position] = repertoryNumber;
                    }
                    for (int i = 0; i < mRepertorySum.length; i++) {
                        LogUtils.d("mRepertorySum[i]" + mRepertorySum[i]);
                        numberTwo += mRepertorySum[i];
                    }
                    setmSum(numberTwo);
                    LogUtils.d("总数==" + numberTwo);
                } else {
                    repertoryNumber = 999;
                }
                holder.mTvAdjustSizeNumber.setText(repertoryNumber+"");
                LogUtils.d("点击加号"+getmSum());
                mListener.setPlusClickListener(position,getmSum());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_adjust_size_name)
        TextView mTvAdjustSizeName;  //库存商品型号
        @BindView(R.id.tv_adjust_size_stocks)
        TextView mTvAdjustSizeStocks;  //库存数量
        @BindView(R.id.iv_adjust_size_minus)
        ImageView mIvAdjustSizeMinus; //减号
        @BindView(R.id.tv_adjust_size_number)
        TextView mTvAdjustSizeNumber; //调整中的数量
        @BindView(R.id.iv_adjust_size_plus)
        ImageView mIvAdjustSizePlus; //加号
        @BindView(R.id.layout_adjust_size_name)
        LinearLayout mLayoutAdjustSizeName; //显示名称尺寸的布局
        @BindView(R.id.layout_adjust_number)
        LinearLayout mLayoutAdjustNumber; //调整数量的布局
        @BindView(R.id.tv_adjust_line)
        TextView mTvAdjustLine;  //中间的分割线
        @BindView(R.id.tv_adjust_size)
        TextView mTvAdjustSize; //显示尺寸的控件

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public interface OnAdjustItemClickListener {
        void setMinusClickListener(int position,int orderSum);
        void setPlusClickListener(int position,int orderSum);
    }
}
