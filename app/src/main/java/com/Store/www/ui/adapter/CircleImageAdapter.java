package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CircleResponse;
import com.Store.www.utils.UserPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 圈子展示图片的适配器
 */

public class CircleImageAdapter extends BaseRecyclerViewAdapter<CircleResponse.DataBean.ImagesBean,CircleImageAdapter.ViewHolder>{

    OnclickListener mListener;
    List<String> imageUrl = new ArrayList<>();

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CircleImageAdapter(Context context, OnclickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sell_details_image,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CircleResponse.DataBean.ImagesBean bean = mDataList.get(position);
        ViewGroup.LayoutParams params;
        params = holder.mIvSellImage.getLayoutParams();  //获取布局
        params.height = UserPrefs.getInstance().getWidth()/3; //设置高=屏幕宽的3分之一
        params.width = UserPrefs.getInstance().getWidth()/3;  //设置宽=屏幕宽的3分之一
        holder.mIvSellImage.setLayoutParams(params);  //给布局的高赋值
        Glide.with(mContext).
                load(bean.getUrl())
                .into(holder.mIvSellImage);
        if (bean.getUrl()!=null){
            for (int i=0;i<1;i++){
                imageUrl.add(bean.getUrl());
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ItemOnclickListener(position,bean.getUrl(),getImageUrl());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_sell_image)
        ImageView mIvSellImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnclickListener{
        void ItemOnclickListener(int position,String url,List<String> imageList);
    }
}
