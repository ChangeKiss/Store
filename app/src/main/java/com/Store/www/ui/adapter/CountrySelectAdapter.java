package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CountryCodeResponse;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by www on 2018/10/11.
 * 选择国家适配器
 */

public class CountrySelectAdapter extends BaseRecyclerViewAdapter<CountryCodeResponse.DataBean, CountrySelectAdapter.ViewHolder>{

    ClickListener mListener;

    public CountrySelectAdapter(Context context,ClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_country_select,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CountryCodeResponse.DataBean bean = mDataList.get(position);
        LogUtils.d("编号=="+bean.getPrefix());
        if (!TextUtils.isEmpty(bean.getCountry())) holder.mTvCountry.setText(bean.getCountry());
        if (bean.getPrefix()!=0)holder.mTvCountryCode.setText("+"+bean.getPrefix());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickListener(position,bean.getPrefix());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_country)
        TextView mTvCountry;
        @BindView(R.id.tv_country_code)
        TextView mTvCountryCode;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ClickListener{
        void onItemClickListener(int position,int areaCode);
    }

}
