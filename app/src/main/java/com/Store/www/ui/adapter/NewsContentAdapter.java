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
import com.Store.www.entity.NewsResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻资讯内容适配器
 */

public class NewsContentAdapter extends BaseRecyclerViewAdapter<NewsResponse.DataBean.TextDatasBean,NewsContentAdapter.ViewHolder>{

    RelativeLayout.LayoutParams params;
    OnContentClickListener mListener;

    public NewsContentAdapter(Context context,OnContentClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_news_content,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NewsResponse.DataBean.TextDatasBean bean = mDataList.get(position);
        params = (RelativeLayout.LayoutParams) holder.mTvContentTitle.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/2;
        holder.mTvContentTitle.setLayoutParams(params);
        Glide.with(mContext).load(bean.getPictueUrl()).error(R.mipmap.jzz_img).into(holder.mTvNewsImage);
        holder.mTvContentTitle.setText(bean.getTitle());
        holder.mTvContentAuthor.setText(""+ ActivityUtils.YMDTime(bean.getTime()));  //作者改为显示时间
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ContentClickListener(position,bean.getInfoid(),bean.getTitle(),bean.getAutor());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_news_image)
        ImageView mTvNewsImage;  //图片框
        @BindView(R.id.tv_content_title)
        TextView mTvContentTitle;  //内容标题
        @BindView(R.id.tv_content_author)
        TextView mTvContentAuthor;  //内容作者


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //接口声明单条新闻内容的点击事件
    public interface OnContentClickListener{
        void ContentClickListener(int position,int infoId,String title,String author);

    }
}
