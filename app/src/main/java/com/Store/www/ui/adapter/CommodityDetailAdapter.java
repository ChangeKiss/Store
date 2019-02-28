package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.IntroduceResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情适配器
 */

public class CommodityDetailAdapter extends BaseRecyclerViewAdapter<IntroduceResponse.DataBean.ImagesBean,CommodityDetailAdapter.ViewHolder>{

    public CommodityDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_commodity_detail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IntroduceResponse.DataBean.ImagesBean bean = mDataList.get(position);
        Glide.with(mContext)
                .load(bean.getUrl())  //加载数据
                .priority(Priority.HIGH)    //优先级高
                .crossFade()  //加载动画 淡入淡出
                .error(R.mipmap.jzz_img)  //错误时显示
                .into(holder.mIvDetailImage); //加载到
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_detail_image)
        ImageView mIvDetailImage;  //商品图片

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
