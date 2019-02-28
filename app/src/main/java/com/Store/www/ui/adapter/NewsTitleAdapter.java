package com.Store.www.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.NewsResponse;
import com.Store.www.ui.activity.NewsWebActivity;
import com.Store.www.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新闻资讯标题的适配器
 */

public class NewsTitleAdapter extends BaseRecyclerViewAdapter<NewsResponse.DataBean,NewsTitleAdapter.ViewHolder>
        implements NewsContentAdapter.OnContentClickListener{

    private String bigTitle;
    NewsContentAdapter mAdapterTwo;
    OnItemClickListener mListener;
    List<NewsResponse.DataBean> beanList = new ArrayList<>();
    NewsResponse.DataBean bean ;


    public NewsTitleAdapter(Context context, OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_news_noe,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NewsResponse.DataBean dataBean = mDataList.get(position);
        holder.mTvNewsTitle.setText(dataBean.getTypeTitle());
        LogUtils.d("长度=="+mDataList.size());
        /*bean = new NewsResponse.DataBean();
        bean.setTypeTitle(getDataList().get(position).getTypeTitle());*/
       /* beanList.add(bean);
        LogUtils.d("第"+position+"个标题="+getDataList().get(position).getTypeTitle());
        LogUtils.d("标题集合+"+beanList.get(position).getTypeTitle());*/
        //开始嵌套布局
        mAdapterTwo = new NewsContentAdapter(mContext,this);
        holder.mRvNewsTwo.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mRvNewsTwo.setAdapter(mAdapterTwo);
        holder.mRvNewsTwo.setFocusable(false);  //取消内部列表自动获取焦点  解决下拉刷新卡顿的问题
        holder.mRvNewsTwo.setFocusableInTouchMode(false); //取消内部列表自动获取焦点  解决下拉刷新卡顿的问题
        mAdapterTwo.addAll(dataBean.getTextDatas());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ItemClickListener(position,dataBean.getTypenum(),dataBean.getTypeTitle());
                LogUtils.d("点击时大标题=="+dataBean.getTypeTitle());
            }
        });

    }

    //嵌套布局的Item的点击事件
    @Override
    public void ContentClickListener(int position, int infoId,String title,String author) {
        Intent intent = new Intent(mContext, NewsWebActivity.class);
        intent.putExtra("infoid",infoId);
        intent.putExtra("title",title);
        intent.putExtra("author",author);
        intent.putExtra("bigtitle","公司新闻"); //大标题
        LogUtils.d("大标题=="+bigTitle);
        mContext.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_news_title)
        TextView mTvNewsTitle;
        @BindView(R.id.rv_news_two)
        RecyclerView mRvNewsTwo;  //嵌套布局

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //接口用于定义item点击事件
    public interface OnItemClickListener{
        void ItemClickListener(int position,int typeNumber,String title);
    }

}
