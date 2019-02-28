package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.NewsCommentResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 新闻评论列表的适配器
 */

public class NewsCommentAdapter extends BaseRecyclerViewAdapter<NewsCommentResponse.DataBean,NewsCommentAdapter.ViewHolder>{

    OnClickListener  mListener;
    public NewsCommentAdapter(Context context,OnClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_news_comment,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NewsCommentResponse.DataBean bean = mDataList.get(position);
        holder.mTvCommentName.setText(bean.getName());  //评论者姓名
        holder.mTvCommentTime.setText(ActivityUtils.times(bean.getTime()));  //评论时间
        holder.mTvCommentContent.setText(bean.getContent());  //评论内容
        Glide.with(mContext).load(bean.getHeadPicture()).error(R.mipmap.default_head).into(holder.mCvCommentHead);
        if (bean.getIsSelf()==1){  //是自己发布的
            holder.mTvCommentDelete.setVisibility(View.VISIBLE); //将删除按钮显示出来
            holder.mTvCommentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnDeleteClickListener(position,bean.getCommentId());
                }
            });
        }else {  //否则将删除按钮隐藏
            holder.mTvCommentDelete.setVisibility(View.INVISIBLE);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cv_comment_head)  //评论列表头像
        CircleImageView mCvCommentHead;
        @BindView(R.id.tv_comment_name)  //评论者姓名
        TextView mTvCommentName;
        @BindView(R.id.tv_comment_time)  //评论时间
        TextView mTvCommentTime;
        @BindView(R.id.tv_comment_delete)  //删除评论
        TextView mTvCommentDelete;
        @BindView(R.id.tv_comment_content)  //评论内容
        TextView mTvCommentContent;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //定义点击事件的接口
    public interface OnClickListener{
        void OnDeleteClickListener(int position, int commentId);
    }
}
