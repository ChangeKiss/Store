package com.Store.www.base;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewAdapter的基类
 */

public abstract class BaseRecyclerViewAdapter<E, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {

    public Context mContext;
    public LayoutInflater inflater;
    public List<E> mDataList = new ArrayList<>();

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public List<E> getDataList() {
        return mDataList;
    }

    public void setDataList(List<E> dataList) {
        mDataList = dataList;
    }

    public void addDataList(E data) {
        mDataList.add(data);
    }

    public void addAll(List<E> dataList) {
        mDataList.addAll(dataList);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public abstract K onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(K holder, int position);


}
