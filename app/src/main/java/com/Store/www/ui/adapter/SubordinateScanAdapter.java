package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonFooter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.SubordinateScanResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: haifeng
 * @description: 下级扫码列表适配器
 */
public class SubordinateScanAdapter extends BaseRecyclerViewAdapter<SubordinateScanResponse.DataBean,SubordinateScanAdapter.ViewHolder> {

    SubordinateScanListAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    public SubordinateScanAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_subordinate_scan_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubordinateScanResponse.DataBean bean = mDataList.get(position);
        CommonHeader header = new CommonHeader(mContext,R.layout.item_scan_list_head);
        CommonFooter footer = new CommonFooter(mContext,R.layout.item_scan_list_footer);
        TextView tvOrderNumber = header.findViewById(R.id.tv_scan_order_number);  //订单编号
        TextView tvOrderTime = header.findViewById(R.id.tv_scan_order_time);  //下单时间
        TextView tvAgentNumber = footer.findViewById(R.id.tv_scan_agent_number);  //代理编号
        TextView tvAgentName = footer.findViewById(R.id.tv_scan_agent_name);   //代理姓名
        TextView tvScanDetail = footer.findViewById(R.id.tv_scan_detail);     //扫描详情
        tvOrderNumber.setText(""+bean.getOrder_number());   //订单编号
        tvOrderTime.setText(ActivityUtils.time(bean.getCreat_time()));  //订单时间
        tvAgentNumber.setText("代理编号:  "+bean.getAgent_code());    //代理编号
        tvAgentName.setText("代理姓名:  "+bean.getAgent_name());      //代理姓名
        /*tvScanDetail.setOnClickListener(new View.OnClickListener() {  //扫描详情点击事件
            @Override
            public void onClick(View v) {

            }
        });*/
        mAdapter = new SubordinateScanListAdapter(mContext);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(header);
        viewAdapter.addFooterView(footer);
        holder.mScanLrV.setAdapter(viewAdapter);
        holder.mScanLrV.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mScanLrV.setPullRefreshEnabled(false);  //关闭下拉刷新
        mAdapter.addAll(bean.getProducts());
        mAdapter.notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.scan_lrv)
        LRecyclerView mScanLrV;  //扫描列表

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
