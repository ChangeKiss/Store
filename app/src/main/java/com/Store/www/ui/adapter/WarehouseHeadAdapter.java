package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.MyWarehouseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/10/9.
 * 我的仓库头布局的适配器
 */

public class WarehouseHeadAdapter extends BaseRecyclerViewAdapter<MyWarehouseResponse.DataBean,WarehouseHeadAdapter.ViewHolder> {

    OnItemClickListener mListener;

    public WarehouseHeadAdapter(Context context, OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }



    @Override
    public WarehouseHeadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_warehouse,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WarehouseHeadAdapter.ViewHolder holder, final int position) {
        final MyWarehouseResponse.DataBean bean = mDataList.get(position);
        holder.mTvWarehouseName.setText(bean.getName());  //显示用户名
        holder.mTvWarehouseAddress.setText("总库存："+bean.getCount()); //显示总数不显示地址
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setHeadItemClickListener(position,bean.getRepId(),bean.getType());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_warehouse_name)
        TextView mTvWarehouseName;
        @BindView(R.id.tv_warehouse_address)
        TextView mTvWarehouseAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void setHeadItemClickListener(int position,int repId,int type);
    }
}
