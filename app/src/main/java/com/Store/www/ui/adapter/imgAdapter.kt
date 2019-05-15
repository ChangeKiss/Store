package com.img.wechatimg.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Store.www.R
import com.Store.www.entity.MyCircleResponse
import com.Store.www.utils.UserPrefs
import com.bumptech.glide.Glide
import com.liji.imagezoom.util.ImageZoom
import kotlinx.android.synthetic.main.item_img.view.*
import kotlinx.android.synthetic.main.item_sell_details_image.view.*
import java.util.ArrayList

/**
 * 加载图片的适配器
 */
class imgAdapter(var list: List<MyCircleResponse.Data.Image>?, var context: Context) : RecyclerView.Adapter<imgAdapter.ViewHolder>() {
    val pairs= arrayOfNulls<Pair<View, String>>(list!!.size)

    internal var imageUrl: MutableList<String> = ArrayList()

    fun getImageUrl(): List<String> {
        return imageUrl
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        pairs[position] = Pair<View, String>(holder.itemView.iv_sell_image, "img$position")
        if (list!![position].url != null) {
            for (i in 0..0) {
                imageUrl.add(list!![position].url!!)
            }
        }
        val  params :ViewGroup.LayoutParams = holder.itemView.iv_sell_image.layoutParams
        params.height = UserPrefs.getInstance().width / 3
        params.width = UserPrefs.getInstance().width / 3
        holder.itemView.iv_sell_image.layoutParams = params
        Glide.with(context).load(list!![position].url).thumbnail(0.4f).into(holder.itemView.iv_sell_image)
        holder.itemView.iv_sell_image.setOnClickListener {
            ImageZoom.show(context, list!![position].url, getImageUrl())
        }
    }

    override fun getItemCount(): Int = list!!.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sell_details_image, parent, false))

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)
}