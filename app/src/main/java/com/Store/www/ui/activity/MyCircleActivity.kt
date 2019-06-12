@file:Suppress("DEPRECATION")

package com.Store.www.ui.activity

import android.app.PendingIntent.getActivity
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.onekeyshare.OnekeyShare
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback
import cn.sharesdk.wechat.moments.WechatMoments
import com.Store.www.R
import com.Store.www.base.BaseRecyclerAdapter
import com.Store.www.base.KtBaseToolbarActivity
import com.Store.www.entity.BaseBenTwo
import com.Store.www.entity.MyCircleResponse
import com.Store.www.entity.PraiseRequest
import com.Store.www.net.RetrofitClient
import com.Store.www.net.UICallBack
import com.Store.www.utils.ActivityCollector
import com.Store.www.utils.ActivityUtils
import com.Store.www.utils.LogUtils
import com.Store.www.utils.TransCodingUtils
import com.bumptech.glide.Glide
import com.img.wechatimg.adapter.imgAdapter
import com.mob.MobSDK
import com.sch.share.WXShareMultiImageHelper
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_my_circle.*
import kotlinx.android.synthetic.main.item_circle.view.*
import kotlinx.android.synthetic.main.item_sell_details_image.view.*
import kotlinx.android.synthetic.main.layout_nodata.*
import java.util.HashMap

/**
 * 我的圈子
 */
class MyCircleActivity : KtBaseToolbarActivity() {

    private lateinit var mAdapter : BaseRecyclerAdapter<MyCircleResponse.Data>
    private var shareContent :String ? = null
    private var mType : String? = null;
    private var circleId : Int = 0;

    private val imageList = ArrayList<Bitmap>() //图片集合 用来分享图片的

    override fun initLayout(): Int = R.layout.activity_my_circle

    override fun onPause() {
        super.onPause()
        isTop = false
    }

    override fun onResume() {
        super.onResume()
        isTop = true
        imageList.clear()
        mImageList.clear()
    }

    override fun initView() {
        ActivityCollector.addActivity(this)
        circleId = intent.getIntExtra("id",0)
        mType = intent.getStringExtra("type")
        if (mType!=null && mType.equals("HotCircle")){
            initToolbar(this,true,"热门圈子")
        }else{
            initToolbar(this,true,"我的圈子")
        }
        sr_layout.setRefreshHeader(ClassicsHeader(this))  //添加刷新头样式
        sr_layout.setRefreshFooter(ClassicsFooter(this))  //添加刷新尾样式
        MobSDK.init(mContext, "245a466822227", "a7a415e1869609aaeac6d8cdc08b7411")  //初始化MOB分享*****
        mAdapter = object : BaseRecyclerAdapter<MyCircleResponse.Data>(this@MyCircleActivity){
            override fun getAdapterLayoutId(viewType: Int): Int = R.layout.item_circle

            override fun convertView(itemView: View, t: MyCircleResponse.Data, position: Int) {
                var bean : MyCircleResponse.Data.UserInfo = t.userInfo!!
                Glide.with(this@MyCircleActivity)
                        .load(bean.headPicture)
                        .error(R.mipmap.default_head)
                        .into(itemView.cv_circle_head)
                itemView.tv_circle_name.text = bean.nickName
                var myTime : Long = (System.currentTimeMillis() / 1000) - t.time/1000
                var  time : Int = 0
                if (myTime < 60){
                    itemView.tv_circle_time.text = "刚刚"
                }else if (myTime/60<60){
                    time = ((myTime/60).toInt())
                    itemView.tv_circle_time.text = time.toString()+"分钟前"
                }else if(myTime/3600 in 2..23){
                    time = (myTime/3600).toInt()
                    itemView.tv_circle_time.text = time.toString()+"小时前"
                }else {
                    time = (myTime/3600/24).toInt()
                    if (time<=7){
                        itemView.tv_circle_time.text = time.toString()+"天前"
                    }else{
                        itemView.tv_circle_time.text = ActivityUtils.YMDTime(t.time)
                    }

                }
                if (t.likeCount>0 && t.isLike==1){
                    itemView.iv_circle_praise.setImageResource(R.mipmap.family_dz_hover_icon)
                }else{
                    itemView.iv_circle_praise.setImageResource(R.mipmap.family_dz_icon)
                }
                itemView.tv_circle_praise.text = t.likeCount.toString()
                itemView.tv_circle_content.text = TransCodingUtils.Decode(t.content) //发布内容解码显示
                itemView.tv_circle_comment.text = t.commentCount.toString()
                itemView.rv_circle_image.layoutManager = GridLayoutManager(this@MyCircleActivity,3)
                if (t.images !=null){
                    itemView.rv_circle_image.adapter = imgAdapter(t.images,this@MyCircleActivity)
                }
                itemView.tv_share_wex.setOnClickListener { //分享的点击事件
                    shareContent = TransCodingUtils.Decode(t.content)
                    if (t.images == null){ //没有图片
                        showShareContext()
                    }else{  //有图片
                        for (i in t.images.indices){
                            mImageList.add(t.images[i].url!!)
                        }
                        Thread(Runnable {
                            for (url in mImageList!!){
                                imageList.add(ActivityUtils.getBitMBitmap(url))
                            }
                        }).start()
                        lodImage(object :OnLoadImageEndCallback{
                            override fun onEnd(bitmapList: List<Bitmap>) {
                                shareToTimeline(imageList)
                            }
                        })
                    }
                }
                itemView.layout_circle_praise.setOnClickListener {  //点赞的点击事件
                    requestPraise(position,mUserId,t.circleId,itemView.iv_circle_praise,itemView.tv_circle_praise,t.likeCount,t.isLike)
                }
            }

        }
        mAdapter.updateSelectItem(0)
        rv_my_circle.layoutManager = LinearLayoutManager(this)
        rv_my_circle.adapter = mAdapter
        if (mType!=null && mType.equals("HotCircle")){  //获取热门圈子
            getHotCircle(mPageIndex,mCountPerPage,mUserId)
        }else{ //获取我的圈子
            getMyCircle(mUserId,0,mPageIndex,mCountPerPage)
        }

    }

    /**
     * 发起点赞 或取消点赞
     */
    fun requestPraise(position :Int,userId: Int,circleId: Int,imageView : ImageView,textView: TextView,lickCount:Int,lik:Int){
        val request: PraiseRequest = PraiseRequest(userId,circleId)
        RetrofitClient.getInstances().requestPraise(request).enqueue(object :UICallBack<BaseBenTwo>(){
            override fun OnRequestFail(msg: String?) {
                if (isTop)checkNet()
            }

            override fun OnRequestSuccess(bean: BaseBenTwo?) {
                if (isTop){
                    when(bean!!.returnValue){
                        1->{
                            var mLickCount: Int
                            if (lik==0){  //是否已点赞 0未点赞  1已点赞
                                mLickCount = lickCount+1
                                mAdapter.getAdapterData()!![position].isLike = 1
                                mAdapter.getAdapterData()!![position].likeCount = mLickCount
                                imageView.setImageResource(R.mipmap.family_dz_hover_icon)
                                textView.text = mLickCount.toString()
                                showToast("~Thanks♪(･ω･)ﾉ~")
                            }else{
                                mLickCount = lickCount-1
                                mAdapter.getAdapterData()!![position].isLike = 1
                                mAdapter.getAdapterData()!![position].likeCount = mLickCount
                                imageView.setImageResource(R.mipmap.family_dz_icon)
                                textView.text = mLickCount.toString()
                                showToast("(｡•ˇ‸ˇ•｡)")
                            }

                        }
                        else->{
                            showToast(bean.errMsg)
                        }
                    }
                }
            }

        })
    }

    /**
     *获取我的圈子
     */
    private fun getMyCircle(userId: Int,type : Int, pageIndex : Int,countPerPage : Int){
        RetrofitClient.getInstances().getMyCircles(countPerPage,pageIndex,type,userId).enqueue(object :UICallBack<MyCircleResponse>(){
            override fun OnRequestFail(msg: String?) {
                if (isTop)checkNet()
            }

            override fun OnRequestSuccess(bean: MyCircleResponse?) {
                if (isTop){
                    when(bean!!.returnValue){
                        1->{
                            mAdapter.updateAdapterData(bean.data as ArrayList<MyCircleResponse.Data>)
                            nodata.visibility = View.GONE
                        }
                        8->{
                            if (mPageIndex ==1){
                                nodata.visibility = View.VISIBLE
                            }else{
                                nodata.visibility = View.GONE
                            }
                        }
                        else-> nodata.visibility = View.VISIBLE
                    }
                }
            }


        })
    }

    /**
     * 获取热门圈子
     */
    private fun getHotCircle(pageIndex: Int,countPerPage: Int,userId: Int){
        RetrofitClient.getInstances().getHotCircle(pageIndex,countPerPage,userId).enqueue(object :UICallBack<MyCircleResponse>(){
            override fun OnRequestFail(msg: String?) {
                if (isTop)checkNet()
            }

            override fun OnRequestSuccess(bean: MyCircleResponse?) {
                if (isTop){
                    when(bean!!.returnValue){
                        1->{
                            mAdapter.updateAdapterData(bean.data as ArrayList<MyCircleResponse.Data>)
                            nodata.visibility = View.GONE
                        }
                        8->{
                            if (mPageIndex ==1){
                                nodata.visibility = View.VISIBLE
                            }else{
                                nodata.visibility = View.GONE
                            }
                        }
                        else-> nodata.visibility = View.VISIBLE
                    }
                }
            }


        })
    }

    //事件监听
    override fun setListener() {
        sr_layout.setOnRefreshListener {
            mPageIndex = 1
            if (mType!=null && mType.equals("HotCircle")){  //获取热门圈子
                getHotCircle(mPageIndex,mCountPerPage,mUserId)
            }else{ //获取我的圈子
                getMyCircle(mUserId,0,mPageIndex,mCountPerPage)
            }
            sr_layout.finishRefresh()
        }

        sr_layout.setOnLoadMoreListener {
            mPageIndex ++
            if (mType!=null && mType.equals("HotCircle")){  //获取热门圈子
                getHotCircle(mPageIndex,mCountPerPage,mUserId)
            }else{ //获取我的圈子
                getMyCircle(mUserId,0,mPageIndex,mCountPerPage)
            }
            sr_layout.finishLoadMore()
        }
    }

    fun lodImage(callback : OnLoadImageEndCallback){
        val  dialog : ProgressDialog = ProgressDialog.show(mContext,"","正在加载图片...")
        Thread(Runnable {
            var bitmapList : ArrayList<Bitmap> = ArrayList()
            for (images in mImageList){
                bitmapList.add(ActivityUtils.getBitMBitmap(images))
            }
            runOnUiThread { dialog.cancel() }
            callback.onEnd(bitmapList)
        }).start()
    }

    interface OnLoadImageEndCallback{
        fun onEnd( bitmapList :List<Bitmap>)
    }

    companion object {
        // 待分享图片。
        private val mImageList = ArrayList<String>()
    }

    // 分享到朋友圈。
    private fun shareToTimeline(bitmapList: List<Bitmap>) {
        LogUtils.d("数据长度=="+bitmapList.size)
        WXShareMultiImageHelper.shareToTimeline(this, bitmapList, shareContent)
    }

    /**
     * 纯文字分享
     */
    private fun showShareContext() {

        val oks = OnekeyShare()
        //关闭sso授权
        oks.disableSSOWhenAuthorize()

        // text是分享文本，所有平台都需要这个字段
        oks.text = shareContent

        oks.shareContentCustomizeCallback = ShareContentCustomizeCallback { platform, paramsToShare ->
            if (platform.name == WechatMoments.NAME) {
                paramsToShare.shareType = Platform.SHARE_TEXT
            }
        }

        oks.callback = object : PlatformActionListener {
            override fun onComplete(platform: Platform, i: Int, hashMap: HashMap<String, Any>) {
                LogUtils.d("回调成功")
            }

            override fun onError(platform: Platform, i: Int, throwable: Throwable) {
                LogUtils.d("回调失败")
            }

            override fun onCancel(platform: Platform, i: Int) {
                LogUtils.d("回调取消")
            }
        }
        // 启动分享GUI
        oks.show(mContext)

    }
}
