package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.AgencyUpDataResponse;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/8/31.
 * 代理权益图标的适配器
 */

public class AgencyEquityAdapter extends BaseRecyclerViewAdapter<AgencyUpDataResponse.DataBean.AllListBean.ListBean,AgencyEquityAdapter.ViewHolder>{

    onItemClickListener mListener;
    LinearLayout.LayoutParams params;

    public AgencyEquityAdapter(Context context,onItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_agency_equity_icon,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        params = (LinearLayout.LayoutParams) holder.mLayoutAgencyEquityHint.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/4-30;
        holder.mLayoutAgencyEquityHint.setLayoutParams(params);
        final AgencyUpDataResponse.DataBean.AllListBean.ListBean bean = mDataList.get(position);
        Glide.with(mContext)
                .load(bean.getPicture())
                .error(R.mipmap.jzz_img)
                .crossFade()
                .into(holder.mIvAgencyEquityIcon);
        holder.mTvAgencyEquityContent.setText(bean.getPowerName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.itemClickListener(position,bean.getPowerName(),bean.getDescribe());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.layout_agency_equity_hint)
        LinearLayout mLayoutAgencyEquityHint;  //代理权益的外层的父布局
        @BindView(R.id.iv_agency_equity_icon)
        ImageView mIvAgencyEquityIcon;  //代理权益图标
        @BindView(R.id.tv_agency_equity_content)
        TextView mTvAgencyEquityContent;  //代理权益
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface onItemClickListener{
        void itemClickListener(int position,String title,String context);
    }
}
