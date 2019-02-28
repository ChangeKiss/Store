package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.BalanceDetailResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 余额明细适配器
 */

public class BalanceDetailTwoAdapter extends BaseRecyclerViewAdapter<BalanceDetailResponse.DataBean.DetailsBean,BalanceDetailTwoAdapter.ViewHolder>{

    OnItemClickListener mListener;
    LinearLayout.LayoutParams params;
    private String mType;

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public BalanceDetailTwoAdapter(Context context, OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_balance_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //先动态设置控件的宽
        params = (LinearLayout.LayoutParams) holder.mLayoutTime.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mTvBalanceDetailMoney.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mTvBalanceDetailContent.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3-25;
        holder.mLayoutTime.setLayoutParams(params);
        holder.mTvBalanceDetailMoney.setLayoutParams(params);
        holder.mTvBalanceDetailContent.setLayoutParams(params);

        final BalanceDetailResponse.DataBean.DetailsBean bean = mDataList.get(position);
        if (bean.getType()==1){  //类型1 是收入
            holder.mTvBalanceDetailMoney.setTextColor(0xffEEB500);  //设置字体颜色
            holder.mTvBalanceDetailMoney.setText("+"+ActivityUtils.changeMoneys(bean.getMoney()));
        }else if (bean.getType()==0){  //类型0 是支出
            holder.mTvBalanceDetailMoney.setTextColor(0xffEE2D1B);  //设置字体颜色
            holder.mTvBalanceDetailMoney.setText("-"+ActivityUtils.changeMoneys(bean.getMoney()));
        }
        holder.mTvBalanceDetailTime.setText(bean.getCreateTime());
        holder.mTvBalanceDetailContent.setText(bean.getDetail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ItemClickListener(position,bean.getId(),bean.getType());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.layout_time)
        LinearLayout mLayoutTime;  //时间外层的布局
        @BindView(R.id.tv_balance_detail_time)
        TextView mTvBalanceDetailTime;  //详细的时间
        @BindView(R.id.tv_balance_detail_money)
        TextView mTvBalanceDetailMoney;  //详细金额
        @BindView(R.id.tv_balance_detail_content)
        TextView mTvBalanceDetailContent;  //内容
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void ItemClickListener(int position,int id,int type);
    }
}
