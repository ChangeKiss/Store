package com.Store.www.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.HomeNewArrivalResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页今日新品上线的适配器
 *
 * */

public class HomeNewArrivalAdapter extends BaseRecyclerViewAdapter<HomeNewArrivalResponse.DataBean,HomeNewArrivalAdapter.ViewHolder>{
    ConstraintLayout.LayoutParams params;
    OnNewArrivalClickListener mListener;
    private List<String> bannerUrl ;

    public HomeNewArrivalAdapter(Context context,OnNewArrivalClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home_new,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        params = (ConstraintLayout.LayoutParams) holder.mHomeBanners.getLayoutParams();
        params.height = UserPrefs.getInstance().getWidth()*184/414;
        holder.mHomeBanners.setLayoutParams(params);
        bannerUrl = new ArrayList<>();
        bannerUrl.clear();
        bannerUrl.add("http://fuatee.oss-cn-hangzhou.aliyuncs.com/66e3a4b4-9079-411d-9165-2a43d24e3b9c.png");
        bannerUrl.add("http://fuatee.oss-cn-hangzhou.aliyuncs.com/b725935a-a9f5-4fda-9a84-3979928adf50.png");

        final HomeNewArrivalResponse.DataBean dataBean = mDataList.get(position);

        holder.mHomeBanners.setData(bannerUrl,null);
        holder.mTvHomeHintTitle.setText("公告");
        holder.mHomeBanners.loadImage((banner, model, view, position1) -> {
            Glide.with(mContext).load((String) model).into((ImageView) view);
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClickListener(position,dataBean.getProductId());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.home_banners)
        XBanner mHomeBanners;       // 轮播图
        @BindView(R.id.tv_home_hint_title)
        TextView mTvHomeHintTitle;  // 首页标题

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public interface OnNewArrivalClickListener{

        void OnItemClickListener(int position , int productId);

    }

}
