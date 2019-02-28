package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.MyResultsDetailsResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的业绩明细的适配器
 */

public class GradeDetailsAdapter extends BaseRecyclerViewAdapter<MyResultsDetailsResponse.DataBean,GradeDetailsAdapter.ViewHolder>{

    public GradeDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_results_detail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyResultsDetailsResponse.DataBean bean = mDataList.get(position);
        holder.mTvResultsOrderNumber.setText("订单编号:"+bean.getOrderNo());  //订单编号
        holder.mTvResultsMoney.setText("下单金额:"+ ActivityUtils.changeMoneys(bean.getTotalMoney()));   //下单金额
        holder.mTvResultsSettleMoney.setText("结算:"+ActivityUtils.changeMoneys(bean.getProfit()));     //结算金额
        if (bean.getAgentCode()==null){
            holder.mTvAgentNumber.setText("代理编号:"+" ");  //代理编号
        }else {
            holder.mTvAgentNumber.setText("代理编号:"+bean.getAgentCode());  //代理编号
        }
        if (bean.getAgentName()==null){
            holder.mTvAgentNumberName.setText("代理姓名:"+" ");  //代理姓名
        }else {
            holder.mTvAgentNumberName.setText("代理姓名:"+bean.getAgentName());  //代理姓名
        }
        holder.mTvResultsTime.setText(bean.getCreateTime());  //下单时间
        if (bean.getStatus()==1){  //未审核
            holder.mIvResultsStatus.setImageResource(R.mipmap.no_audit);
        }else if (bean.getStatus()==2){  //已审核
            holder.mIvResultsStatus.setImageResource(R.mipmap.ok_audit);
        }else if (bean.getStatus()==3){  //审核失败
            holder.mIvResultsStatus.setImageResource(R.mipmap.audit_defeated);
        }
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

}
