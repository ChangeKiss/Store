package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.BonusQueryResponse;
import com.Store.www.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/7/9.
 * 分红查询适配器
 */

public class BonusQueryAdapter extends BaseRecyclerViewAdapter<BonusQueryResponse.DataBean.ListBean,BonusQueryAdapter.ViewHolder>{

    OnItemClickListener mListener;
    public BonusQueryAdapter(Context context,OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bonus_inquire,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BonusQueryResponse.DataBean.ListBean listBean = mDataList.get(position);
        holder.mTvMonthBonus.setText(listBean.getMonth());  //当前月份
        holder.mTvBonusTotalMoney.setText(ActivityUtils.changeMoneys(listBean.getDateTotalBonus())); //月度分红总金额
        holder.mLayoutLookDetail.setOnClickListener(new View.OnClickListener() {  //查看当月明细的点击事件
            @Override
            public void onClick(View v) {
                mListener.LookDetailClickListener(position,holder.mLayoutMonthDetail,holder.mIvClickHint);
            }
        });
        holder.mTvPeopleBonusMoney.setText(ActivityUtils.changeMoneys(listBean.getDatePerBonus()));  //个人分红
        holder.mTvTeamBonusMoney.setText(ActivityUtils.changeMoneys(listBean.getDateTeamBonus()));  //团队分红
        holder.mTvMonthBonusDetail.setOnClickListener(new View.OnClickListener() {  //查看分红明细的点击事件
            @Override
            public void onClick(View v) {
                mListener.LookBonusDetailClickListener(position,listBean.getMonth());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.layout_look_detail)
        LinearLayout mLayoutLookDetail;  //点击可查看当月分红明细
        @BindView(R.id.tv_month_bonus)
        TextView mTvMonthBonus;  // 月份
        @BindView(R.id.tv_bonus_total_money)
        TextView mTvBonusTotalMoney;  //当月总金额
        @BindView(R.id.iv_click_hint)
        ImageView mIvClickHint;  //提示的图片
        @BindView(R.id.layout_month_detail)
        LinearLayout mLayoutMonthDetail;  //月度明细布局
        @BindView(R.id.tv_people_bonus_money)
        TextView mTvPeopleBonusMoney;  //个人分红
        @BindView(R.id.tv_team_bonus_money)
        TextView mTvTeamBonusMoney;  //团队分红
        @BindView(R.id.tv_month_bonus_detail)
        TextView mTvMonthBonusDetail;  //查看明细按钮

       public ViewHolder(View itemView) {
           super(itemView);
           ButterKnife.bind(this,itemView);
       }
   }

   //定义点击事件的接口
   public interface OnItemClickListener{
       void LookDetailClickListener(int position,LinearLayout layout,ImageView imageView);  //查看当月明细的点击事件
       void LookBonusDetailClickListener(int position,String month);   //查看分红明细

   }
}
