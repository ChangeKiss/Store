package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CommodityStocksResponse;
import com.Store.www.entity.PickUpAndCommodityResponse;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新增提货单的适配器
 * 再次获取商品库存
 */

public class NewLadingAdapter extends BaseRecyclerViewAdapter<PickUpAndCommodityResponse.DataBean,NewLadingAdapter.ViewHolder>{

    public int ladingSum;
    LinearLayout.LayoutParams params;
    List<CommodityStocksResponse.DataBean> dataBeen ;
    CommodityStocksResponse.DataBean been = new CommodityStocksResponse.DataBean();

    OnItemClickListener mListener;

    public List<CommodityStocksResponse.DataBean> getDataBeen() {
        return dataBeen;
    }

    public void setDataBeen(List<CommodityStocksResponse.DataBean> dataBeen) {
        this.dataBeen = dataBeen;
    }

    public int getLadingSum() {
        return ladingSum;
    }

    public void setLadingSum(int ladingSum) {
        this.ladingSum = ladingSum;
    }

    public NewLadingAdapter(Context context, OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_new_lading,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PickUpAndCommodityResponse.DataBean bean = mDataList.get(position);
        params = (LinearLayout.LayoutParams) holder.mTvLadingName.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mLayoutLadingContent.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mTvNewsLadingNumber.getLayoutParams();
        params.width = UserPrefs.getInstance().getWidth()/3-15;
        LogUtils.d("提货管理控件宽="+UserPrefs.getInstance().getWidth()/3);
        holder.mTvLadingName.setLayoutParams(params);
        holder.mTvNewsLadingNumber.setLayoutParams(params);
        holder.mLayoutLadingContent.setLayoutParams(params);
        holder.mTvNewsLadingSurplus.setTag(position);
        holder.mTvLadingName.setText(bean.getProductName());
        holder.mTvNewsLadingNumber.setText("提货:"+bean.getCount()+"件");
        holder.mTvNewsLadingRepertory.setText("库存:"+bean.getRepositoryCount()+"件");
        holder.mTvNewsLadingSurplus.setText("剩余:"+bean.getSurplusCount()+"件");
        holder.mCbPickupChoose.setOnCheckedChangeListener(null);  //这个设置 解决item复用是CheckBox混乱的问题
        holder.mCbPickupChoose.setChecked(bean.isCheck());
        holder.mCbPickupChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.setCheck(isChecked);
                mListener.OnCheckBokClickListener(position,isChecked,holder.mCbPickupChoose);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClickListener(position,bean.getProductNo(),bean.getProductName(),bean.getProductId(),bean.getId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cb_pickup_choose)
        CheckBox mCbPickupChoose;   //提交时的复选框
        @BindView(R.id.tv_news_lading_name)
        TextView mTvLadingName;  //商品名称
        @BindView(R.id.tv_news_lading_number)
        TextView mTvNewsLadingNumber;  //提货数量
        @BindView(R.id.tv_news_lading_repertory)
        TextView mTvNewsLadingRepertory;  // 库存数量
        @BindView(R.id.tv_news_lading_surplus)
        TextView mTvNewsLadingSurplus;  //剩余数量
        @BindView(R.id.layout_lading_content)
        LinearLayout mLayoutLadingContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void OnItemClickListener(int position,String productNo,String title,int productId,int id);
        void OnCheckBokClickListener(int position, boolean isCheck, CheckBox checkBox);
    }
}
