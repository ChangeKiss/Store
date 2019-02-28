package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.EntityWarehouseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 实体仓库适配器
 */

public class WarehouseAdapter extends BaseRecyclerViewAdapter<EntityWarehouseResponse.DataBean,WarehouseAdapter.ViewHolder>{

    OnItemClickListener mListener;

    public WarehouseAdapter(Context context,OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_warehouse,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EntityWarehouseResponse.DataBean bean = mDataList.get(position);
        holder.mTvWarehouseName.setText(bean.getName());  //显示用户名
        holder.mTvWarehouseAddress.setText("总库存："+bean.getNum()); //显示总数不显示地址
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setItemClickListener(position,bean.getId());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.setDeleteAlterClickListener(position,bean.getId(),bean.getName(),bean.getProvince(),bean.getCity(),bean.getArea(),bean.getAddress());
                return false;
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
        void setItemClickListener(int position,int repId);
        void setDeleteAlterClickListener(int position,int repId,String name,String province,String city,String area,String address);
    }
}
