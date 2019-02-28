package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.BankCardListResponse;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择银行卡的适配器
 */

public class SelectBankCardAdapter extends BaseRecyclerViewAdapter<BankCardListResponse.DataBean,SelectBankCardAdapter.ViewHolder>{

    OnClickListener mListener;
    private int selectId;
    private String mType;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    public SelectBankCardAdapter(Context context,OnClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_select_bank_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BankCardListResponse.DataBean bean = mDataList.get(position);
        if (!TextUtils.isEmpty(mType)){
            LogUtils.d("类型=="+mType);
            if (mType.equals("select")){  //如果类型是选择
                if (selectId==bean.getCardId()){  //如果选中的银行卡ID 等于 当前银行卡ID
                    LogUtils.d("ID=01="+selectId);
                    bean.setSelect(true);
                    holder.mIvSelectCard.setVisibility(View.VISIBLE);
                }else {
                    LogUtils.d("ID=02="+selectId);
                    bean.setSelect(false);
                    holder.mIvSelectCard.setVisibility(View.GONE);
                }
            }
        }else {
            holder.mIvSelectCard.setVisibility(View.VISIBLE);
        }

        holder.mTvBankCardName.setText(bean.getOpenBank());
        if (bean.getCardType()==0){  //储蓄卡
            holder.mTvBankCardNumber.setText("尾号 "+bean.getCardNumber()+" 储蓄卡");
        }else if (bean.getCardType()==1){  //信用卡
            holder.mTvBankCardNumber.setText("尾号 "+bean.getCardNumber()+" 信用卡");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClickListener(position,bean.getOpenBank(),bean.getCardNumber(),bean.getCardId(),bean.getCardType(),bean.getIsCoupletNumber());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.OnLongClickListener(position,bean.getCardId(),bean.isSelect());
                return false;
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_bank_card_name)
        TextView mTvBankCardName;  //银行卡名称
        @BindView(R.id.tv_bank_card_number)
        TextView mTvBankCardNumber;  //银行卡号
        @BindView(R.id.iv_select_card)
        ImageView mIvSelectCard;  //是否选中银行卡
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnClickListener{
        void OnItemClickListener(int position,String cardName,String cardNumber,int cardId,int cardType,int isCardNumber);
        void OnLongClickListener(int position,int cardId,boolean isSelect);
    }
}
