package com.Store.www.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView的Adapter的基类 test test
 */

public abstract class BaseListViewAdapter<E> extends BaseAdapter {
    public Context context;
    public LayoutInflater layoutInflater;
    List<E> dataList=new ArrayList<E>();

    public BaseListViewAdapter(Context context) {
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }
    public void addDataList(E data) {
        this.dataList.add(data);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public E getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}
