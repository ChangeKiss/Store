package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.SellOrderDetailsResponse;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 销售详情的图片适配器
 */

public class SellDetailsImageAdapter extends BaseRecyclerViewAdapter<SellOrderDetailsResponse.PayInfoListBean,SellDetailsImageAdapter.ViewHolder>{
    OnclickListener mListener;
    public SellDetailsImageAdapter(Context context,OnclickListener listener) {
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
        final SellOrderDetailsResponse.PayInfoListBean bean = mDataList.get(position);
        ViewGroup.LayoutParams params;//初始化布局
        params = holder.mIvSellImage.getLayoutParams();  //获取布局
        params.height = UserPrefs.getInstance().getWidth()/4; //设置布局高 = 屏幕宽的4分之一
        holder.mIvSellImage.setLayoutParams(params); //给布局赋值
        Glide.with(mContext)
                .load(bean.getPayInfo())
                .error(R.mipmap.jzz_img)
                .into(holder.mIvSellImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ItemOnclickListener(position,bean.getPayInfo());
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
        void ItemOnclickListener(int position,String url);
    }
}
