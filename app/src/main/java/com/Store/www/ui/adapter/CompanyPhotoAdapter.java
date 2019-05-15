package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CompanyPhotoResponse;
import com.Store.www.test.Mode;
import com.Store.www.utils.ActivityUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: haifeng
 * @description:  公司相册适配器
 */
public class CompanyPhotoAdapter extends BaseRecyclerViewAdapter <CompanyPhotoResponse.DataBean,CompanyPhotoAdapter.ViewHolder>{

    private ClickListener mListener;
    public CompanyPhotoAdapter(Context context,ClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_company_photo,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CompanyPhotoResponse.DataBean bean = mDataList.get(position);
        holder.mTvPhotoName.setText(bean.getName());
        holder.mTvPhotoTime.setText(ActivityUtils.YMDTime(bean.getCreateTime()));
        Glide.with(mContext)
                .load(bean.getPhoto())
                .error(R.mipmap.jzz_img)
                .into(holder.mIvPhotoCover);
        holder.itemView.setOnClickListener(v -> {
            mListener.itemClickListener(position,bean.getName(),bean.getId());
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_photo_cover)
        ImageView mIvPhotoCover;  //相册封面
        @BindView(R.id.tv_photo_name)
        TextView mTvPhotoName;  //相册名
        @BindView(R.id.tv_photo_time)
        TextView mTvPhotoTime; //时间

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ClickListener{
        void itemClickListener(int position,String title,int id);
    }
}
