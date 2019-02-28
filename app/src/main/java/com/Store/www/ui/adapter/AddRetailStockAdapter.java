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
 * Created by www on 2018/10/12.
 * 添加零售记录仓库适配器
 */

public class AddRetailStockAdapter extends BaseRecyclerViewAdapter<EntityWarehouseResponse.DataBean,
        AddRetailStockAdapter.ViewHolder>{
    ClickListener mListener;

    public AddRetailStockAdapter(Context context,ClickListener listener) {
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
        holder.mTvWarehouseName.setText(bean.getName());
        holder.mTvWarehouseAddress.setText("总数:"+bean.getNum()+"件");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setOnItemClickListener(position,bean.getId(),bean.getName(),bean.getProvince(),bean.getCity(),bean.getArea(),bean.getAddress());
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

    public interface ClickListener{
        void setOnItemClickListener(int position,int repId,String name,String province,String city,String area,String address);
    }
}
