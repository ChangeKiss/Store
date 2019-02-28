package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加图片的适配器
 */

public class AgentApplyAdapter extends BaseRecyclerViewAdapter<String,AgentApplyAdapter.ViewHolder> {

    OnImageClickListener mListener;

    public AgentApplyAdapter(Context context,OnImageClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_agent_apply,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position==0){
            holder.mIvAgentApplyDetail.setImageResource(R.mipmap.add_img);
            holder.mIvAgentDelete.setVisibility(View.GONE);
        }else {
            holder.mIvAgentDelete.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(mDataList.get(position))
                    .error(R.mipmap.jzz_img)
                    .placeholder(R.mipmap.program_icon)
                    .into(holder.mIvAgentApplyDetail);
        }

        holder.mIvAgentApplyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setOnImageClickListener(v,position);
            }
        });

        holder.mIvAgentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setOnDeleteClickListener(position);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_agent_apply_detail)
        ImageView mIvAgentApplyDetail;
        @BindView(R.id.iv_agent_delete)
        ImageView mIvAgentDelete;

     public ViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this,itemView);
     }
 }

    public interface OnImageClickListener {
        void setOnImageClickListener(View view, int position);
        void setOnDeleteClickListener(int position);
    }

}
