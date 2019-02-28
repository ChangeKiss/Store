package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.AgencyQueryResponse;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 代理商列表的适配器
 */

public class AgencyListAdapter extends BaseRecyclerViewAdapter<AgencyQueryResponse.DataBean,AgencyListAdapter.ViewHolder>{

    private int flag;
    private int type;
    OnClickListener mListener;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public AgencyListAdapter(Context context, OnClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_agency_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AgencyQueryResponse.DataBean bean = mDataList.get(position);
        Glide.with(mContext)
                .load(bean.getHeadPicture())
                .error(R.mipmap.default_head)
                .into(holder.mCvAgencyHead);
        holder.mTvAgencyName.setText(bean.getName()); //代理姓名
        holder.mTvAgencyNumber.setText(bean.getCode()); //代理编号
        holder.mTvAgencyPhones.setText(bean.getMobilephone()); //手机号码
        holder.mTvAgencyWeXin.setText(bean.getWeChat());  //微信号码
        holder.mTvAgencyLv.setText(bean.getLevelName());   //代理等级

        holder.mTvAgencyPhones.setOnClickListener(new View.OnClickListener() { //拨打电话
            @Override
            public void onClick(View v) {
                mListener.CallClickListener(position,bean.getMobilephone());
            }
        });
        LogUtils.d("type=="+getType());

        holder.mTvLookOver.setEnabled(true);
        holder.mTvLookLower.setEnabled(true);
        holder.mTvLookOver.setOnClickListener(new View.OnClickListener() {  //查看上级
            @Override
            public void onClick(View v) {
                mListener.OverClickListener(position,bean.getParentCode(),bean.getId());
            }
        });

        holder.mTvLookLower.setOnClickListener(new View.OnClickListener() {  //查看下级
            @Override
            public void onClick(View v) {
                mListener.LowerClickListener(position,bean.getId());
            }
        });



    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cv_agency_head)
        CircleImageView mCvAgencyHead; //头像
        @BindView(R.id.tv_agency_name)
        TextView mTvAgencyName;  //代理姓名
        @BindView(R.id.tv_agency_numbers)
        TextView mTvAgencyNumber;  //代理编号
        @BindView(R.id.tv_agency_phones)
        TextView mTvAgencyPhones;  //手机号码
        @BindView(R.id.tv_agency_weXin)
        TextView mTvAgencyWeXin;  //微信号码
        @BindView(R.id.tv_agency_lv)
        TextView mTvAgencyLv;   //代理等级
        @BindView(R.id.tv_look_over)
        TextView mTvLookOver;  //查看上级
        @BindView(R.id.tv_look_lower)
        TextView mTvLookLower;  //查看下级
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnClickListener{
        void OverClickListener(int position,String parentCode,int id);
        void LowerClickListener(int position,int id);
        void CallClickListener(int position,String phone);
    }
}
