package com.Store.www.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


/**
 *@author: haifeng
 *@description:使用Kotlin写的适配器要继承这个基类
 */
abstract class KtBaseRecyclerViewAdapter <E, K : RecyclerView.ViewHolder>(var mContext: Context?) : RecyclerView.Adapter<K>() {
    var inflater: LayoutInflater = LayoutInflater.from(mContext)
    var mDataList: ArrayList<E> = ArrayList()

    var dataList: ArrayList<E>
        get() = mDataList
        set(dataList) {
            mDataList = dataList
        }

    fun addDataList(data: E) {
        mDataList.add(data)
    }

    fun addAll(dataList: ArrayList<E>) {
        mDataList.addAll(dataList)
    }



    override fun getItemCount(): Int {
        return mDataList.size
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K

    abstract override fun onBindViewHolder(holder: K, position: Int)

}