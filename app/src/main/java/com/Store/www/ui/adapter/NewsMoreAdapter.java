package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.NewsMoreResponse;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻资讯更多的适配器
 */

public class NewsMoreAdapter extends BaseRecyclerViewAdapter<NewsMoreResponse.DataBean,NewsMoreAdapter.ViewHolder>{

    OnContentClickListener mListener;
    RelativeLayout.LayoutParams params;
    public NewsMoreAdapter(Context context,OnContentClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_news_more,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NewsMoreResponse.DataBean dataBean = mDataList.get(position);
        params = (RelativeLayout.LayoutParams) holder.mTvMoreContentTitle.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/2;
        holder.mTvMoreContentTitle.setLayoutParams(params);
        Glide.with(mContext).load(dataBean.getPictueUrl()).error(R.mipmap.jzz_img).into(holder.mIvNewsMoreImage);
        holder.mTvMoreContentTitle.setText(dataBean.getTitle());
        holder.mTvMoreContentAuthor.setText("作者："+dataBean.getAutor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ContentClickListener(position,dataBean.getInfoid(),dataBean.getTitle(),dataBean.getAutor());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_news_more_image)
        ImageView mIvNewsMoreImage;  //图标
        @BindView(R.id.tv_more_content_title)
        TextView mTvMoreContentTitle;  //标题
        @BindView(R.id.tv_more_content_author)
        TextView mTvMoreContentAuthor;  //作者

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnContentClickListener{
        void ContentClickListener(int position,int infoId,String title,String author);
    }

}
