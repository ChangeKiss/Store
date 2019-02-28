package com.Store.www.ui.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.SumResultsResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的业绩适配器
 */

public class ResultsAdapter extends BaseRecyclerViewAdapter<SumResultsResponse.DataBean,ResultsAdapter.ViewHolder>{

    OnclickListener mListener;

    public ResultsAdapter(Context context,OnclickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_results,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SumResultsResponse.DataBean bean = mDataList.get(position);
        holder.mTvResultsYearMonth.setText(bean.getTime());
        holder.mTvAuditedResultsMoney.setText("¥"+ActivityUtils.changeMoneys(bean.getAuditedMoney()));
        holder.mTvTotalMoney.setText("¥"+ActivityUtils.changeMoneys(bean.getAllMoney()));
        holder.mTvNoAuditedResultsMoney.setText("¥"+ActivityUtils.changeMoneys(bean.getUnauditedMoney()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClickListener(position,bean.getMonth(),bean.getTime(),bean.getYear());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_results_year_month)
        TextView mTvResultsYearMonth;  //年月日
        @BindView(R.id.tv_audited_results_money)
        TextView mTvAuditedResultsMoney;  //已审核业绩
        @BindView(R.id.tv_total_money)
        TextView mTvTotalMoney;   //总业绩
        @BindView(R.id.tv_no_audited_results_money)
        TextView mTvNoAuditedResultsMoney;   //未审核业绩


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnclickListener{
        void OnItemClickListener(int position,String month,String TimeTitle,int year);
    }
}
