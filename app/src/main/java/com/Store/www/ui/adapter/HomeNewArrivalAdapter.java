package com.Store.www.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页今日新品上线的适配器
 *
 * */

public class HomeNewArrivalAdapter extends BaseRecyclerViewAdapter<HomeNewArrivalResponse.DataBean,HomeNewArrivalAdapter.ViewHolder>{

    OnNewArrivalClickListener mListener;
    LinearLayout.LayoutParams params;
    LinearLayout.LayoutParams paramsTwo;
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
        final HomeNewArrivalResponse.DataBean dataBean = mDataList.get(position);
        params = (LinearLayout.LayoutParams) holder.mIvHomeNewImage.getLayoutParams();
        paramsTwo = (LinearLayout.LayoutParams) holder.mVwHomePageLine.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/360*320;
        params.height = UserPrefs.getInstance().getWidth()/360*79*2;
        holder.mIvHomeNewImage.setLayoutParams(params);
        paramsTwo.width = UserPrefs.getInstance().getWidth()/360*320;
        holder.mVwHomePageLine.setLayoutParams(paramsTwo);
        if (mDataList.get(position).getImages()!=null){
            Glide.with(mContext)
                    .load(dataBean.getImages().get(0).getUrl())
                    .error(R.mipmap.jzz_img)
                    .into(holder.mIvHomeNewImage);
        }else {
            Glide.with(mContext)
                    .load(R.mipmap.jzz_img)
                    .error(R.mipmap.jzz_img)
                    .into(holder.mIvHomeNewImage);
        }

        LogUtils.d("图片控件的宽=="+holder.mIvHomeNewImage.getWidth());
        holder.mTvHomeNewName.setText(dataBean.getName());
        holder.mTvHomeNewMoney.setText("￥"+ActivityUtils.changeMoneys(dataBean.getRetailSale())); //零售价
        holder.mTvHomeNewMoneyTwo.setText("￥"+ActivityUtils.changeMoneys(dataBean.getPrice()));  //原价
        holder.mTvHomeNewMoneyTwo.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);  //给原价画一条线
        holder.mTvHomeNewMoneyTwo.getPaint().setAntiAlias(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClickListener(position,dataBean.getProductId());
            }
        });
        if (position==mDataList.size()-1){
            holder.mVwHomePageLine.setVisibility(View.GONE);
            holder.mVwHomePageView.setVisibility(View.VISIBLE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_home_new_image)
        ImageView mIvHomeNewImage;  //商品图片
        @BindView(R.id.tv_home_new_name)
        TextView mTvHomeNewName; //商品名
        @BindView(R.id.tv_home_new_money)
        TextView mTvHomeNewMoney; //商品最新价
        @BindView(R.id.tv_home_new_money_two)
        TextView mTvHomeNewMoneyTwo; //商品原价
        @BindView(R.id.vw_homePage_line)
        View mVwHomePageLine;  //底部的线
        @BindView(R.id.vw_homePage_view)
        View mVwHomePageView;  //最底部的占位布局

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public interface OnNewArrivalClickListener{

        void OnItemClickListener(int position , int productId);

    }

}
