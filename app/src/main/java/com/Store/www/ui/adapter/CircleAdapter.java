package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CircleResponse;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.TransCodingUtils;
import com.liji.imagezoom.util.ImageZoom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 圈子的适配器
 */

public class CircleAdapter extends BaseRecyclerViewAdapter<CircleResponse.DataBean,CircleAdapter.ViewHolder>implements CircleImageAdapter.OnclickListener{
    CircleImageAdapter mAdapter;
    OnclickListener mListener;
    public CircleAdapter(Context context,OnclickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_circle,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CircleResponse.DataBean dataBean = mDataList.get(position);
        CircleResponse.DataBean.UserInfoBean infoBean = dataBean.getUserInfo();
        Glide.with(mContext).load(infoBean.getHeadPicture()).error(R.mipmap.default_head).into(holder.mCvCircleHead);
        mAdapter = new CircleImageAdapter(mContext,this);
        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        holder.mRv.setLayoutManager(manager);
        holder.mRv.setAdapter(mAdapter);
        holder.mRv.setFocusable(false); //取消内部列表自动获取焦点  解决下拉刷新卡顿的问题
        holder.mRv.setFocusableInTouchMode(false); //取消内部列表自动获取焦点  解决下拉刷新卡顿的问题
        if (getDataList().get(position).getImages()!=null){
            mAdapter.addAll(dataBean.getImages());
            mAdapter.notifyDataSetChanged();
        }
        holder.mTvCircleName.setText(infoBean.getNickName()); //姓名

        holder.mTvCircleContent.setText(TransCodingUtils.Decode(dataBean.getContent())); //圈子内容（需要将特殊符号或表情解码显示）

        holder.mTvCircleComment.setText(""+dataBean.getCommentCount()+""); //评论数量
        long thisTime = System.currentTimeMillis()/1000;  //当前系统毫秒数
        long getTime = dataBean.getTime()/1000;  //服务器返回的毫秒数
        long time = thisTime-getTime;  //我要的时间
        LogUtils.d("Time="+time);
        int myTime=0;
        if (time<60){
            //60秒以内显示刚刚
            holder.mTvCircleTime.setText("刚刚");
        }else if (time/60<60){
            //一小时以内显示分钟
            myTime = (int) (time/60);
            holder.mTvCircleTime.setText(myTime+"分钟前");
        }else if (time/3600>1&&time/3600<24){
            //一天以内显示小时
            myTime = (int) (time/3600);
            holder.mTvCircleTime.setText(myTime+"小时前");
        }else {
            //显示天数
            myTime = (int) (time/3600/24);
            holder.mTvCircleTime.setText(myTime+"天前");
        }
        if (dataBean.getLikeCount()>0&&dataBean.getIsLike()==1){
            holder.mIvCirclePraise.setImageResource(R.mipmap.family_dz_hover_icon);
        }else {
            holder.mIvCirclePraise.setImageResource(R.mipmap.family_dz_icon);
        }
        holder.mTvCirclePraise.setText("("+dataBean.getLikeCount()+")"); //点赞数
        //点赞的点击事件
        holder.mLayoutCirclePraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.PraiseClickListener(position,holder.mIvCirclePraise,holder.mTvCirclePraise,dataBean.getIsLike(),dataBean.getCircleId(),
                        dataBean.getLikeCount());
            }
        });

        //举报的点击事件
        holder.mLayoutCircleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ReportClickListener(position,dataBean.getCircleId());
            }
        });


        //评论的点击事件
        holder.mLayoutCircleComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.CommentClickListener(position,dataBean.getCircleId());
            }
        });

        //分享的点击事件
        holder.mTvShare_wex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ShareClickListener(position,dataBean.getImages(),dataBean.getContent());
            }
        });

    }

    //九宫格图片的点击事件
    @Override
    public void ItemOnclickListener(int position, String url,List<String> imageList) {
        ImageZoom.show(mContext,url,imageList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cv_circle_head)
        CircleImageView mCvCircleHead;  //头像
        @BindView(R.id.tv_circle_name)
        TextView mTvCircleName;  //姓名
        @BindView(R.id.tv_circle_time)
        TextView mTvCircleTime;  //时间
        @BindView(R.id.tv_circle_content)
        TextView mTvCircleContent; //内容
        @BindView(R.id.rv_circle_image)
        RecyclerView mRv;  //加载图片列表
        @BindView(R.id.layout_circle_praise)
        LinearLayout mLayoutCirclePraise;//点赞布局
        @BindView(R.id.iv_circle_praise)
        ImageView mIvCirclePraise;//点赞的图片
        @BindView(R.id.tv_circle_praise)
        TextView mTvCirclePraise;  //点赞的数量
        @BindView(R.id.layout_circle_comment)
        LinearLayout mLayoutCircleComment;//评论布局
        @BindView(R.id.tv_circle_comment)
        TextView mTvCircleComment;  //评论的数量
        @BindView(R.id.layout_circle_report)
        LinearLayout mLayoutCircleReport;  //举报布局
        @BindView(R.id.tv_share_wex)
        TextView mTvShare_wex;  // 分享至微信朋友圈

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //点击事件接口
    public interface OnclickListener{
        void PraiseClickListener(int position,ImageView imageView,TextView textView,int like,int circleId,int likeCount);
        void ReportClickListener(int position,int circleId);
        void CommentClickListener(int position,int circleId);
        void ShareClickListener(int position, List<CircleResponse.DataBean.ImagesBean> Images,String content);
    }

}
