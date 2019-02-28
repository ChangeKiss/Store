package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.AlterOrderDetailsResponse;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *修改商品信息的适配器
 */

public class AlterOrderAdapter extends BaseRecyclerViewAdapter<AlterOrderDetailsResponse.SKUdataBean,AlterOrderAdapter.ViewHolder>{

    private int[] mOrderNumber;
    private int orderNumber,mSum;
    OnOrderItemClickListener mListener;
    LinearLayout.LayoutParams params;
    LinearLayout.LayoutParams paramsTwo;
    LinearLayout.LayoutParams paramsThree;

    public int[] getmOrderNumber() {
        return mOrderNumber;
    }

    public void setmOrderNumber(int[] mOrderNumber) {
        this.mOrderNumber = mOrderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getmSum() {
        return mSum;
    }

    public void setmSum(int mSum) {
        this.mSum = mSum;
    }

    public AlterOrderAdapter(Context context, OnOrderItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_alter_order,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AlterOrderDetailsResponse.SKUdataBean dataBean = mDataList.get(position);
        params = (LinearLayout.LayoutParams) holder.mLayoutAlterLocation.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3;
        holder.mTvAlterStockNumber.setLayoutParams(params);
        holder.mLayoutAlterLocation.setLayoutParams(params);
        holder.mLayoutNumberLocation.setLayoutParams(params);
        paramsTwo = (LinearLayout.LayoutParams) holder.mTvAlterLine.getLayoutParams();
        paramsTwo.leftMargin = UserPrefs.getInstance().getWidth()/3/3/3;
        holder.mTvAlterLine.setLayoutParams(paramsTwo);
        paramsThree = (LinearLayout.LayoutParams) holder.mTvAlterOrderName.getLayoutParams();
        paramsThree.width = UserPrefs.getInstance().getWidth()/3/3;
        holder.mTvAlterOrderName.setLayoutParams(paramsThree);
        holder.mTvAlterSize.setLayoutParams(paramsThree);

        //如果输入文本框重绘时，将文本框的监听事件移出 ，否则数据会丢失
        if (holder.mTvAlterOrderNumber.getTag() instanceof TextWatcher){
            holder.mTvAlterOrderNumber.removeTextChangedListener((TextWatcher) holder.mTvAlterOrderNumber.getTag());
        }
        holder.mTvAlterStockNumber.setText(dataBean.getStock()+"");
        holder.mTvAlterOrderName.setText(dataBean.getColorName());
        holder.mTvAlterLine.setText("|");
        holder.mTvAlterSize.setText(dataBean.getSizeName()+"");
        holder.mTvAlterOrderNumber.setText(mOrderNumber[position]+"");

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)){
                    mOrderNumber[position] = 0;
                }else {
                    mOrderNumber[position] = Integer.parseInt(s.toString());
                    int numberThree = 0;
                    for (int i = 0; i < mOrderNumber.length; i++) {
                        numberThree += mOrderNumber[i];
                        LogUtils.d("数量==" + mOrderNumber[i]);
                    }
                    LogUtils.d("总数=03=" + numberThree);
                    setmOrderNumber(mOrderNumber);
                    setmSum( numberThree);
                    mListener.setOrdersNumber(position,getmSum());
                }
            }
        };

        holder.mTvAlterOrderNumber.addTextChangedListener(textWatcher);
        holder.mTvAlterOrderNumber.setTag(textWatcher);

        holder.mIvAlterMinus.setOnClickListener(new View.OnClickListener() {  //减号的点击事件
            @Override
            public void onClick(View v) {
                int number = 0;
                orderNumber = mOrderNumber[position];
                if (orderNumber > 0) {
                    orderNumber--;
                    mOrderNumber[position] = orderNumber;
                    LogUtils.d("mOrderNumber[i]" + mOrderNumber[position]);
                    for (int i = 0; i < mOrderNumber.length; i++) {
                        number += mOrderNumber[i];
                    }
                    LogUtils.d("总数==" + number);
                    setmSum(number);
                } else {
                    orderNumber = 0;
                }
                LogUtils.d("当前数量==="+mOrderNumber[position]);
                setmOrderNumber(mOrderNumber);
                holder.mTvAlterOrderNumber.setText("" + orderNumber);
                LogUtils.d("商品总数=01="+getmSum());
                mListener.setOnMinusClickListener(position,getmSum());
            }
        });

        holder.mIvAlterPlus.setOnClickListener(new View.OnClickListener() {  //加号的点击事件
            @Override
            public void onClick(View v) {
                int numberTwo = 0;
                orderNumber = mOrderNumber[position];
                LogUtils.d("数量==="+orderNumber);
                if (orderNumber <9999) {
                    orderNumber++;
                    mOrderNumber[position] = orderNumber;
                    LogUtils.d("mOrderNumber[i]" + mOrderNumber[position]);
                    for (int i = 0; i < mOrderNumber.length; i++) {
                        numberTwo += mOrderNumber[i];
                    }
                    LogUtils.d("总数==" + numberTwo);
                    setmSum(numberTwo);
                } else {
                    orderNumber =9999;
                }
                LogUtils.d("当前数量==="+mOrderNumber[position]);
                setmOrderNumber(mOrderNumber);
                holder.mTvAlterOrderNumber.setText("" + orderNumber);
                LogUtils.d("商品总数=01="+getmSum());
                mListener.setOnPlusClickListener(position,getmSum());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_alter_order_name)
        TextView mTvAlterOrderName; //商品
        @BindView(R.id.tv_alter_order_number)
        EditText mTvAlterOrderNumber;  //商品数量
        @BindView(R.id.iv_alter_minus)
        ImageView mIvAlterMinus;  //减号
        @BindView(R.id.iv_alter_plus)
        ImageView mIvAlterPlus;  //加号
        @BindView(R.id.tv_alter_stock_number)
        TextView mTvAlterStockNumber;  //显示库存
        @BindView(R.id.layout_number_location)
        LinearLayout mLayoutNumberLocation;  //修改数量的布局
        @BindView(R.id.layout_alter_location)
        LinearLayout mLayoutAlterLocation; //显示信息的布局
        @BindView(R.id.tv_alter_line)
        TextView mTvAlterLine;  //中间分割线
        @BindView(R.id.tv_alter_size)
        TextView mTvAlterSize;  //尺码

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnOrderItemClickListener {
        void setOnMinusClickListener(int position,int sum);
        void setOnPlusClickListener(int position,int sum);
        void setOrdersNumber(int position,int sum);
    }
}
