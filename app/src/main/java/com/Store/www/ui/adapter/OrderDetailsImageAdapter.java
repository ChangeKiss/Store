package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.OrderDetailsResponse;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单详情图片的适配器
 */

public class OrderDetailsImageAdapter extends BaseRecyclerViewAdapter<OrderDetailsResponse.PayInfoListBean,OrderDetailsImageAdapter.ViewHolder>{

    OnClickListener mListener;

    public OrderDetailsImageAdapter(Context context,OnClickListener listener ) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sell_details_image,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final OrderDetailsResponse.PayInfoListBean bean = mDataList.get(position);
        ViewGroup.LayoutParams params; //设置布局参数
        params=holder.mIvSellImage.getLayoutParams();  //获取布局参数
        params.height = UserPrefs.getInstance().getWidth()/4; //设置高=屏幕宽的4分之一
        holder.mIvSellImage.setLayoutParams(params);//给布局赋值
        LogUtils.d("图片高度=="+holder.mIvSellImage.getWidth());
        Glide.with(mContext).load(bean.getPayInfo()).into(holder.mIvSellImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClickListener(position,bean.getPayInfo());
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

    public interface OnClickListener{
        void OnItemClickListener(int position,String url);
    }

}
