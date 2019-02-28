package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.AgencyUpDataResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/8/31.
 * 代理升级适配器
 */

public class AgencyUpDataAdapter extends BaseRecyclerViewAdapter<AgencyUpDataResponse.DataBean.AllListBean,AgencyUpDataAdapter.ViewHolder>{

    public AgencyUpDataAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_agency_up_data_lv,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AgencyUpDataResponse.DataBean.AllListBean bean = mDataList.get(position);
        Glide.with(mContext).load(bean.getChdPicture())
                .error(R.mipmap.jzz_img)
                .into(holder.mIvAgencyLvIcon);
        holder.mTvAgencyRank.setText(bean.getName());  //代理等级
        holder.mTvAgencyUpCondition.setText(bean.getCondition()); //升级条件
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_agency_lv_icon)
        ImageView mIvAgencyLvIcon;  //代理等级图标
        @BindView(R.id.tv_agency_rank)
        TextView mTvAgencyRank;  //代理等级
        @BindView(R.id.tv_agency_up_condition)
        TextView mTvAgencyUpCondition; //升级条件

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
