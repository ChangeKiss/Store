package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.GainNotComeStockResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/10/15.
 * 实体仓库出库的pop的适配器
 */

public class ComeStockPopAdapter extends BaseRecyclerViewAdapter<GainNotComeStockResponse.ListBean,ComeStockPopAdapter.ViewHolder>{


    minusPlusClickListener mListener;
    public int[] mRepertorySum; //用来存放商品信息
    private int repertoryNumber,mSum;

    public int[] getmRepertorySum() {
        return mRepertorySum;
    }

    public void setmRepertorySum(int[] mRepertorySum) {
        this.mRepertorySum = mRepertorySum;
    }

    public ComeStockPopAdapter(Context context,minusPlusClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_come_stock_commodity,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GainNotComeStockResponse.ListBean bean = mDataList.get(position);
        holder.mTvComeStockName.setText(bean.getName());  //商品名
        holder.mTvComeStockSize.setText(bean.getColor()+"|"+bean.getSize());
        holder.mTvComeStockNumber.setText(mRepertorySum[position]+"");  //设置初始值
        holder.mTvComeStockNumber.setTag(position); //设置标记
        holder.mIvNotComeStockMinus.setEnabled(true);
        holder.mIvNotComeStockPlus.setEnabled(true);
        holder.mIvNotComeStockMinus.setOnClickListener(new View.OnClickListener() {  //减号的点击事件
            @Override
            public void onClick(View v) {
                repertoryNumber = mRepertorySum[position];
                if (repertoryNumber > 0) {
                    repertoryNumber--;
                } else {
                    repertoryNumber = 0;
                }
                mListener.onAlterMinusClickListener(position,bean.getId(),repertoryNumber);
                holder.mIvNotComeStockMinus.setEnabled(false);
            }
        });

        holder.mIvNotComeStockPlus.setOnClickListener(new View.OnClickListener() {  //加号的点击事件
            @Override
            public void onClick(View v) {
                repertoryNumber = mRepertorySum[position];
                if (repertoryNumber<999){
                    repertoryNumber++;
                }
                mListener.onAlterPlusClickListener(position,bean.getId(),repertoryNumber);
                holder.mIvNotComeStockPlus.setEnabled(false);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_come_stock_name)
        TextView mTvComeStockName;  //名称
        @BindView(R.id.tv_come_stock_size)
        TextView mTvComeStockSize; //库存颜色尺码
        @BindView(R.id.iv_not_come_stock_minus)
        ImageView mIvNotComeStockMinus; //减号
        @BindView(R.id.tv_come_stock_number)
        TextView mTvComeStockNumber; //提货数量
        @BindView(R.id.iv_not_come_stock_plus)
        ImageView mIvNotComeStockPlus; //加号



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface minusPlusClickListener{
        void onAlterMinusClickListener(int position,int id,int number);
        void onAlterPlusClickListener(int position,int id,int number);
    }

}
