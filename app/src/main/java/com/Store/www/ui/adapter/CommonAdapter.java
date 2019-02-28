package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.CommonIssueResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 常见问题的适配器
 */

public class CommonAdapter extends BaseRecyclerViewAdapter<CommonIssueResponse.DataBean,CommonAdapter.ViewHolder>{

    OnItemClickListener mListener;

    public CommonAdapter(Context context,OnItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_common,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CommonIssueResponse.DataBean dataBean = mDataList.get(position);
        holder.mTvIssueHint.setText(dataBean.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ItemClickListener(position,dataBean.getDetail());
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_issue_hint)
        TextView mTvIssueHint;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void ItemClickListener(int position,String content);
    }

}
