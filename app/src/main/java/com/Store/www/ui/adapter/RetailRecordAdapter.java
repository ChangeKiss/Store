package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonFooter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.RetailRecordResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/10/12.
 * 零售记录列表主适配器
 */

public class RetailRecordAdapter extends BaseRecyclerViewAdapter<RetailRecordResponse.ListBean,RetailRecordAdapter.ViewHolder>{

    private CommonHeader mCommonHeader;  //头布局
    private CommonFooter mCommonFooter;  //尾布局

    private RetailRecordInsideAdapter mAdapter;
    private LRecyclerViewAdapter viewAdapter;

    public RetailRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_retail_record_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RetailRecordResponse.ListBean bean = mDataList.get(position);
        mCommonHeader = new CommonHeader(mContext,R.layout.item_retail_record_head);
        TextView HintRecordNumber = (TextView) mCommonHeader.findViewById(R.id.tv_hint_record_number);
        TextView HintRecordTime = (TextView) mCommonHeader.findViewById(R.id.tv_hint_record_time);
        TextView HintRecordName = mCommonHeader.findViewById(R.id.tv_record_name);
        mCommonFooter = new CommonFooter(mContext,R.layout.item_retail_record_footer);
        TextView mRecordRemark = (TextView) mCommonFooter.findViewById(R.id.record_remark);
        mAdapter = new RetailRecordInsideAdapter(mContext);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(mCommonHeader); //添加头布局
        viewAdapter.addFooterView(mCommonFooter); //添加尾布局
        holder.mRv.setLayoutManager(new LinearLayoutManager(mContext));  //添加布局管理器
        holder.mRv.setAdapter(viewAdapter);
        holder.mRv.setFocusable(false);  //关闭内部适配器列表的获取焦点 解决卡顿问题
        holder.mRv.setFocusableInTouchMode(false);  //关闭背部适配器列表的触摸事件 解决卡顿问题
        holder.mRv.setPullRefreshEnabled(false); //关闭下拉刷新
        if (!TextUtils.isEmpty(bean.getRemark())){
            mCommonFooter.setVisibility(View.VISIBLE);
            mRecordRemark.setText("备注:"+bean.getRemark());
        }else {
            mCommonFooter.setVisibility(View.GONE);
        }
        HintRecordNumber.setText(bean.getOrderNo());  //记录编号
        HintRecordTime.setText(ActivityUtils.YMDTime(bean.getCreateTime()));
        HintRecordName.setText("零售对象:    "+bean.getAgentName());  //零售对象

        mAdapter.addAll(bean.getProductOrderList());
        mAdapter.notifyDataSetChanged();
        //holder.mRv.setLoadMoreEnabled(false);  //关闭上拉加载



    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rv_retail_inside_list)
        LRecyclerView mRv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
