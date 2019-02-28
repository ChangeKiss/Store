package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.LookLogisticsResponse;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/8/14.
 * 查看物流适配器
 */

public class LogisticsAdapter extends BaseRecyclerViewAdapter<LookLogisticsResponse.DataBean,LogisticsAdapter.ViewHolder>{

    String message = "";
    public LogisticsAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_lookover_logistics,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LookLogisticsResponse.DataBean bean = mDataList.get(position);
        holder.mTvLogisticsNumber.setText(bean.getNo());
        LogUtils.d("长度=="+bean.getInfo().size());
        for (int i=0;i<bean.getInfo().size();i++){
            message += bean.getInfo().get(i)+"\n";
        }
        holder.mTvLogisticsMessage.setText(message.toString().trim());

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_logistics_number)
        TextView mTvLogisticsNumber;  //物流编号
        @BindView(R.id.tv_logistics_message)
        TextView mTvLogisticsMessage;  //物流信息

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
