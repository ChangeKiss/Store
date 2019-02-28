package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.TeamResultsResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *业绩明细列表的适配器
 */

public class ResultsDetailAdapter extends BaseRecyclerViewAdapter<TeamResultsResponse.DataBean.DetailsBean,ResultsDetailAdapter.ViewHolder>{

    OnClickListener mListener;

    public ResultsDetailAdapter(Context context,OnClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_results_detail,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TeamResultsResponse.DataBean.DetailsBean bean =mDataList.get(position);
        holder.mTvResultsOrderNumber.setText("订单编号:"+bean.getOrderNo());
        if (bean.getAgentName()==null){
            holder.mTvAgentNumberName.setText("代理姓名:"+" ");
        }else {
            holder.mTvAgentNumberName.setText("代理姓名:"+bean.getAgentName());
        }
        holder.mTvResultsTime.setText(bean.getCreateTime());
        holder.mTvResultsSettleMoney.setText("结算:"+ ActivityUtils.changeMoneys(bean.getProfit()));
        holder.mTvResultsMoney.setText("下单金额:"+ActivityUtils.changeMoneys(bean.getTotalMoney())+"元");
        if (bean.getAgentCode()==null){
            holder.mTvAgentNumber.setText("代理编号:"+" ");
        }else {
            holder.mTvAgentNumber.setText("代理编号:"+bean.getAgentCode());
        }
        if (bean.getStatus()==1){  //未审核
            holder.mIvResultsStatus.setImageResource(R.mipmap.no_audit);
        }else if (bean.getStatus()==2){  //已审核
            holder.mIvResultsStatus.setImageResource(R.mipmap.ok_audit);
        }else if (bean.getStatus()==3){
            holder.mIvResultsStatus.setImageResource(R.mipmap.audit_defeated);
        }
        holder.mTvAgentNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.AgentNumberClickListener(position,bean.getAgentCode());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_results_order_number)
        TextView mTvResultsOrderNumber;  //订单编号
        @BindView(R.id.tv_agent_number_mane)
        TextView mTvAgentNumberName;  //代理姓名
        @BindView(R.id.tv_results_money)
        TextView mTvResultsMoney;  //  下单金额
        @BindView(R.id.tv_results_time)
        TextView mTvResultsTime; //下单时间
        @BindView(R.id.tv_results_settle_money)
        TextView mTvResultsSettleMoney;  //  结算金额
        @BindView(R.id.iv_results_image)
        ImageView mIvResultsStatus;  //状态
        @BindView(R.id.tv_agent_number)
        TextView mTvAgentNumber;  //代理编号

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnClickListener{
        void AgentNumberClickListener(int position,String number);
    }
}
