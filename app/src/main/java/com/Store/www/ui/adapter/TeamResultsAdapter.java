package com.Store.www.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.TeamResultsResponse;
import com.Store.www.ui.activity.AgencyListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 业绩明细的适配器
 */

public class TeamResultsAdapter extends BaseRecyclerViewAdapter<TeamResultsResponse.DataBean,TeamResultsAdapter.ViewHolder>
        implements ResultsDetailAdapter.OnClickListener{

    ResultsDetailAdapter mAdapter;
    public TeamResultsAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_team_results,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeamResultsResponse.DataBean bean = mDataList.get(position);
        holder.mTvResultsYearMonth.setText(bean.getTime());
        mAdapter = new ResultsDetailAdapter(mContext,this);
        holder.mRvResultsDetails.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mRvResultsDetails.setAdapter(mAdapter);
        mAdapter.addAll(bean.getDetails());
        mAdapter.notifyDataSetChanged();

    }

    //  代理编号的点击事件
    @Override
    public void AgentNumberClickListener(int position, String number) {
        Intent intent = new Intent(mContext, AgencyListActivity.class);
        intent.putExtra("type","results");
        intent.putExtra("agentNumber",number);
        mContext.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_results_year_month)
        TextView mTvResultsYearMonth;  //年月日
        @BindView(R.id.rv_results_details)
        RecyclerView mRvResultsDetails;  //明细列表


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
