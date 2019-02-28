package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.UpDataBegResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by www on 2018/9/1.
 * 升级请求适配器
 */

public class UpDataBegAdapter extends BaseRecyclerViewAdapter<UpDataBegResponse.DataBean,UpDataBegAdapter.ViewHolder>{
    onButtonOnclickListener mListener;

    public UpDataBegAdapter(Context context,onButtonOnclickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_up_data_beg,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UpDataBegResponse.DataBean bean = mDataList.get(position);
        if (bean.getHeadPicture()!=null){
            Glide.with(mContext)
                    .load(bean.getHeadPicture())
                    .crossFade()
                    .error(R.mipmap.default_head)
                    .into(holder.mCvBegAgencyHeadPortrait);
        }
        holder.mTvBegAgencyName.setText(bean.getName());  //代理姓名
        holder.mTvBegAgencyNumber.setText(bean.getCode());  //代理编号
        holder.mTvBegUpTime.setText(ActivityUtils.time(bean.getTime()));  //申请时间
        holder.mTvPresentLv.setText(bean.getCurrentLevelName());  //当前等级
        holder.mTvBegUpTo.setText(bean.getApplyLevelName());  //申请升级到的等级
        if (bean.getStatus()==0){
            holder.mLayoutRejectConsent.setVisibility(View.VISIBLE);
            holder.mTvBegRejectConsent.setVisibility(View.GONE);
        }else if (bean.getStatus()==1){
            holder.mLayoutRejectConsent.setVisibility(View.GONE);
            holder.mTvBegRejectConsent.setVisibility(View.VISIBLE);
            holder.mTvBegRejectConsent.setText("已同意,等待公司审核");
        }else if (bean.getStatus()==2){
            holder.mLayoutRejectConsent.setVisibility(View.GONE);
            holder.mTvBegRejectConsent.setVisibility(View.VISIBLE);
            holder.mTvBegRejectConsent.setText("已升级");
        }else if (bean.getStatus()==3){
            holder.mLayoutRejectConsent.setVisibility(View.GONE);
            holder.mTvBegRejectConsent.setVisibility(View.VISIBLE);
            holder.mTvBegRejectConsent.setText("已拒绝");
        }
        //同意按钮的点击事件
        holder.mBtnBegConsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onConsentClickListener(position,bean.getId());
            }
        });
        //拒绝按钮的点击事件
        holder.mBtnBegReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRejectClickListener(position,bean.getId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cv_beg_agency_head_portrait)
        CircleImageView mCvBegAgencyHeadPortrait;   //请求代理头像
        @BindView(R.id.tv_beg_agency_name)
        TextView mTvBegAgencyName;   //请求代理姓名
        @BindView(R.id.tv_beg_agency_number)
        TextView mTvBegAgencyNumber;  //请求代理编号
        @BindView(R.id.tv_beg_up_time)
        TextView mTvBegUpTime; // 请求升级时间
        @BindView(R.id.tv_present_lv)
        TextView mTvPresentLv;  //当前等级
        @BindView(R.id.tv_beg_up_to)
        TextView mTvBegUpTo;  //升级到的等级
        @BindView(R.id.layout_reject_consent)
        LinearLayout mLayoutRejectConsent;  //拒绝同意的父布局 控制显示隐藏
        @BindView(R.id.btn_beg_reject)
        Button mBtnBegReject;   //拒绝按钮
        @BindView(R.id.btn_beg_consent)
        Button mBtnBegConsent;   //同意按钮
        @BindView(R.id.tv_beg_reject_consent)
        TextView mTvBegRejectConsent;   //拒绝或同意的提示


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //按钮的点击时间
    public interface onButtonOnclickListener{
        void onRejectClickListener(int position,int id);  //拒绝
        void onConsentClickListener(int position,int id);  //同意
    }
}
