package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.LocationResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地址管理适配器
 */

public class LocationAdapter extends BaseRecyclerViewAdapter<LocationResponse.DataBean,LocationAdapter.ViewHolder>{


    private String area;

    OnItemClickListener mListener;

    public LocationAdapter(Context context,OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_location,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        final LocationResponse.DataBean dataBean = mDataList.get(position);
        area = dataBean.getCountry()+dataBean.getProvince()+dataBean.getCity()+dataBean.getStreet();
        holder.mTvLocationName.setText("收件人："+dataBean.getReceiveName());
        holder.mTvLocationPhone.setText(dataBean.getPhone());
        holder.mTvLocationAdders.setText("地址："+area+dataBean.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setOnItemClickListener(position,dataBean.getReceiveName(),dataBean.getPhone(),dataBean.getAddress(),
                        dataBean.getIsUsed(),dataBean.getCountry(),dataBean.getProvince(),dataBean.getCity(),dataBean.getStreet(),dataBean.getId());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.setDeleteClickListener(position,dataBean.getId());
                return false;
            }
        });
        if (dataBean.getIsUsed()==1){
            holder.mTvDefaultAddressHint.setVisibility(View.VISIBLE);
        }else {
            holder.mTvDefaultAddressHint.setVisibility(View.INVISIBLE);
        }

    }




    public class ViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.tv_location_name)
        TextView mTvLocationName;
        @BindView(R.id.tv_location_phone)
        TextView mTvLocationPhone;
        @BindView(R.id.tv_location_adders)
        TextView mTvLocationAdders;
        @BindView(R.id.item_layout)
        LinearLayout mItemLayout;
        @BindView(R.id.item_delete)
        TextView mItemDelete;
        @BindView(R.id.tv_default_address_hint)
        TextView mTvDefaultAddressHint;   //默认地址提示



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }






    }

    public interface OnItemClickListener {


        //点击
        void setOnItemClickListener(int position,String name,String phone,String address,int isUsed,
                                    String country,String province,String city,String street,int id);

        //删除
        void setDeleteClickListener(int position,int id);


    }




}
