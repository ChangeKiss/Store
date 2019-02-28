package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.OrderFundRollBackResponse;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: haifeng
 * @description: 资金回退订单列表适配器
 */
public class FundOrderRollBackAdapter extends BaseRecyclerViewAdapter <OrderFundRollBackResponse.DataBean.ListBean,FundOrderRollBackAdapter.ViewHolder>{

    OnClickListener mListener;
    SpannableStringBuilder builder;
    private boolean isSelect = false;
    ForegroundColorSpan span;
    private int selectMoney;  //选择金额
    private int applyMoney;   //申请金额
    public FundOrderRollBackAdapter(Context context,OnClickListener listener) {
        super(context);
        mListener = listener;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getSelectMoney() {
        return selectMoney;
    }

    public void setSelectMoney(int selectMoney) {
        this.selectMoney = selectMoney;
    }

    public int getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(int applyMoney) {
        this.applyMoney = applyMoney;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_fund_order_rollback,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final OrderFundRollBackResponse.DataBean.ListBean bean = mDataList.get(position);
        LogUtils.d("订单编号=="+bean.getOrderNo());
        holder.mTvBackMoneyHint.setText("¥"+(bean.getSurplusMoney()/100)); // 剩余可提金额
        holder.mTvOrderBackNumber.setText("订单编号:"+bean.getOrderNo());
        builder = new SpannableStringBuilder(" ¥"+(bean.getOrderMoney()/100));
        holder.mTvOrderBackMoney.setText(builder);
        if (!TextUtils.isEmpty(bean.getPaymentMethod())){
            holder.mTvOrderBackPayType.setText("支付方式: "+bean.getPaymentMethod());
        }else {
            holder.mTvOrderBackPayType.setText("支付方式: 其他");
        }

        holder.mOrderBackCheckBox.setOnCheckedChangeListener(null);  //先把复选框设置为未选中
        holder.mOrderBackCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (getSelectMoney() ==0 ||getSelectMoney()<getApplyMoney()){
                    holder.mOrderBackCheckBox.setChecked(true);
                    bean.setCheck(true);
                    mListener.CheckClickListener(position,isChecked,true);
                }else {
                    holder.mOrderBackCheckBox.setChecked(false);
                    bean.setCheck(false);
                    mListener.CheckClickListener(position,isChecked,false);
                }

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.order_back_checkbox)
        CheckBox mOrderBackCheckBox;  //复选框
        @BindView(R.id.tv_back_money_hint)
        TextView mTvBackMoneyHint;  //可退金额
        @BindView(R.id.tv_order_back_number)
        TextView mTvOrderBackNumber;  //订单编号
        @BindView(R.id.tv_order_back_money)
        TextView mTvOrderBackMoney;  //订单金额
        @BindView(R.id.tv_order_back_pay_type)
        TextView mTvOrderBackPayType;  //支付类型

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnClickListener{
        void CheckClickListener(int position,boolean isCheck,boolean select);
    }
}
