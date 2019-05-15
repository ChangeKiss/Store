package com.Store.www.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.Store.www.R
import com.Store.www.base.BaseRecyclerAdapter
import com.Store.www.base.KtBaseToolbarActivity
import com.Store.www.entity.SumResultsResponse
import com.Store.www.net.RetrofitClient
import com.Store.www.net.UICallBack
import com.Store.www.ui.commom.DialogLoading
import com.Store.www.utils.ActivityCollector
import com.Store.www.utils.ActivityUtils
import com.Store.www.utils.LogUtils
import kotlinx.android.synthetic.main.activity_my_results.*
import kotlinx.android.synthetic.main.item_results.view.*
import kotlinx.android.synthetic.main.layout_nodata.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的业绩界面
 */
class MyResultsActivity : KtBaseToolbarActivity() {

    val mType : String = "bar"
    var mYear : Int = 0
    var month : String? =null
    var mTitle : String? = null

    private lateinit var mAdapter : BaseRecyclerAdapter<SumResultsResponse.DataBean>

    override fun initLayout(): Int {
        return  R.layout.activity_my_results
    }

    override fun initView() {
        ActivityCollector.addActivity(this)
        initToolbar(this,true,"我的业绩")
        tv_toolbar_right.visibility = View.VISIBLE
        tv_toolbar_right.setImageResource(R.mipmap.report_forms_icon)
        tv_toolbar_right.setOnClickListener(this)
        mAdapter = object : BaseRecyclerAdapter<SumResultsResponse.DataBean>(this@MyResultsActivity){

            override fun getAdapterLayoutId(viewType: Int): Int = R.layout.item_results

            override fun convertView(itemView: View, t: SumResultsResponse.DataBean, position: Int) {
                itemView.tv_results_year_month.text = t.time   //年月日
                itemView.tv_audited_results_money.text = "¥"+ActivityUtils.changeMoneys(t.auditedMoney)  //已审核业绩
                itemView.tv_no_audited_results_money.text = "¥"+ActivityUtils.changeMoneys(t.unauditedMoney) //未审核业绩
                itemView.tv_total_money.text = "¥"+ActivityUtils.changeMoneys(t.allMoney)  //总业绩
            }
        }
        mAdapter.updateSelectItem(0)
        rv_my_results.layoutManager = LinearLayoutManager(this);
        rv_my_results.adapter = mAdapter
        getMyResults()
    }

    private fun getMyResults(){
        DialogLoading.shows(this,"加载中...")
        RetrofitClient.getInstances().getSumResults(mUserId).enqueue(object :UICallBack<SumResultsResponse>(){
            override fun OnRequestFail(msg: String?) {
                if (isTop)checkNet()
            }

            override fun OnRequestSuccess(bean: SumResultsResponse?) {
                if (isTop){
                    when(bean!!.returnValue){
                        1->{
                            mAdapter.updateAdapterData(bean.data as ArrayList<SumResultsResponse.DataBean>)
                            nodata.visibility = View.GONE
                        }
                        5->{
                            nodata.visibility = View.VISIBLE
                        }
                        else-> showToast(bean.errMsg!!)
                    }
                }
            }

        })
    }

    override fun setListener(){
        mAdapter.setOnItemClickListener { position, _ ->
            mYear = mAdapter.getAdapterData()!![position].year
            month = mAdapter.getAdapterData()!![position].month
            mTitle = mAdapter.getAdapterData()!![position].time
            val intent = Intent(this,MyResultsDetailsActivity::class.java)
            intent.putExtra("title",mTitle)
            intent.putExtra("type",mType)
            intent.putExtra("year",mYear)
            intent.putExtra("month",month)
            startActivity(intent)
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.tv_toolbar_right ->mActivityUtils!!.startActivity(MyTeamActivity::class.java)
        }
    }
}
