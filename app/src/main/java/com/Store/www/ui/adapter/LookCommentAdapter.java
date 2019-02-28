package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.LookCommentResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 查看评论的适配器
 */

public class LookCommentAdapter extends BaseRecyclerViewAdapter<LookCommentResponse.DataBean,LookCommentAdapter.ViewHolder>{


    public LookCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_news_comment,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LookCommentResponse.DataBean bean = mDataList.get(position);
        Glide.with(mContext).load(bean.getHeadPicture()).error(R.mipmap.default_head).into(holder.mCvCommentHead);
        holder.mTvCommentName.setText(bean.getNickName());
        holder.mTvCommentTime.setText(bean.getTime());
        holder.mTvCommentContent.setText(bean.getContent());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cv_comment_head)
        CircleImageView mCvCommentHead;  //头像
        @BindView(R.id.tv_comment_name)
        TextView mTvCommentName;  //姓名
        @BindView(R.id.tv_comment_time)
        TextView mTvCommentTime;   //时间
        @BindView(R.id.tv_comment_content)
        TextView mTvCommentContent;  //内容
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
