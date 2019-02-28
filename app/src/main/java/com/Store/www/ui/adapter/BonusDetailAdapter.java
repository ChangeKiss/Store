package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.BonusDetailResponse;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/7/10.
 * 分红详情适配器  弹窗的
 */

public class BonusDetailAdapter extends BaseRecyclerViewAdapter<BonusDetailResponse.DataBean,BonusDetailAdapter.ViewHolder>{

    LinearLayout.LayoutParams params;
    public BonusDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_month_bonus_detail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //先设置布局的大小
        params = (LinearLayout.LayoutParams) holder.mTvMonthBonusDetailName.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mTvMonthBonusDetailBra.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mTvMonthBonusDetailRate.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mTvMonthBonusDetailBonus.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/4;
        holder.mTvMonthBonusDetailName.setLayoutParams(params);
        holder.mTvMonthBonusDetailBra.setLayoutParams(params);
        holder.mTvMonthBonusDetailRate.setLayoutParams(params);
        holder.mTvMonthBonusDetailBonus.setLayoutParams(params);

        BonusDetailResponse.DataBean bean = mDataList.get(position);
        holder.mTvMonthBonusDetailName.setText(bean.getCodeAndName());
        holder.mTvMonthBonusDetailBra.setText(ActivityUtils.changeMoneys(bean.getMonthAchievement()));
        holder.mTvMonthBonusDetailRate.setText(ActivityUtils.changeMoneys(bean.getRate()));
        holder.mTvMonthBonusDetailBonus.setText(ActivityUtils.changeMoneys(bean.getBonus()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_month_bonus_detail_name)
        TextView mTvMonthBonusDetailName;   //当月分红姓名
        @BindView(R.id.tv_month_bonus_detail_bra)
        TextView mTvMonthBonusDetailBra;  //当月分红内衣业绩
        @BindView(R.id.tv_month_bonus_detail_rate)
        TextView mTvMonthBonusDetailRate;  //当月分红费率
        @BindView(R.id.tv_month_bonus_detail_bonus)
        TextView mTvMonthBonusDetailBonus;  //当月贡献分红

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
