package com.Store.www.base

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.Store.www.MyApplication
import com.Store.www.R
import com.Store.www.utils.ActivityUtils
import com.Store.www.utils.NetWorkUtil
import com.Store.www.utils.UserPrefs

/**
 *@author: haifeng
 *@description: Kotlin的Base基类
 */
 abstract class KtBaseActivity : AppCompatActivity(){
    var userId: String? =null
    var mUserId: Int = 0
    var mPhone: String? =null
    var year: Int = 0
    var mMonth: String?=null
    var agentCode: String?=null
    var mActivityUtils: ActivityUtils? = null
    var mActivity: Activity?=null
    private var mUnbinder: Unbinder? = null
    var mCountPerPage = 10 //全局变量 分页用展示数据
    var mPageIndex = 1 //全局变量分页用
    var mCurrentFragment: Fragment? = null
    var isTop = false  //判断当前界面是否处于活动最上层(可见)
    var mIsPasswordShow = false//true有显示
    var mCanLoadMore = true
    var mContext: Context? = null
    var token = 2
    var netWork = " "  //网络连接类型

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityUtils = ActivityUtils(this)
        mActivity = this
        mContext = this
        mUserId = UserPrefs.getInstance().id //userId Int型
        //agentCode = "18857738393"; //测试用代理人ID 先用着
        agentCode = UserPrefs.getInstance().agentCode  //代理人ID
        year = UserPrefs.getInstance().year //网络获取当前年份
        mMonth = UserPrefs.getInstance().month //网络获取当前月份
        userId = UserPrefs.getInstance().userId  //userId String型
        mPhone = UserPrefs.getInstance().phone
        setContentView(initLayout()) //这里传布局/继承此类之后不用原生的引入布局了
        mUnbinder = ButterKnife.bind(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)//禁止一进入界面就因为et的原因弹出软键盘
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//禁止横竖屏切换
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //设置软键盘弹出模式覆盖Activity*****
        MyApplication.getInstance().addActivity(this)  //初始化网易七鱼云客服
        initView()
        setListener()
        setStatusBarFullTransparent()
    }

    //获取设备网络连接类型
    private fun getPhoneNetWork() {
        //没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
        when (ActivityUtils.getPhoneNetWork(mContext)) {
            1 -> netWork = "WiFi"
            2 -> netWork = "2G"
            3 -> netWork = "3G"
            4 -> netWork = "4G"
            else -> netWork = "0"
        }
    }


    abstract fun initLayout(): Int

    abstract fun initView()

    protected open fun setListener() {}

    override fun onDestroy() {
        super.onDestroy()
        mActivityUtils!!.hideSoftKeyboard()
        if (mActivityUtils != null) {
            mActivityUtils = null
        }
        mUnbinder!!.unbind()
        mUnbinder = null
    }

    override fun onPause() {
        super.onPause()
        isTop = false
    }

    override fun onResume() {
        super.onResume()
        isTop = true
    }

    fun switchFragmentByShow(targetFragment: Fragment) {
        if (mCurrentFragment === targetFragment) return
        val transaction = supportFragmentManager.beginTransaction()

        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment)
        }
        if (targetFragment.isAdded) {
            // 如果已经添加到FragmentManager里面，就展示
            transaction.show(targetFragment)
        } else {
            // 添加Fragment
            transaction.add(R.id.frame, targetFragment)
        }
        transaction.commitAllowingStateLoss()
        mCurrentFragment = targetFragment
    }


    /** * 全透状态栏  */
    protected fun setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.rgb(27, 26, 32)  //设置系统状态栏 颜色
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4//
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明 //
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }


    fun showToast(msg: String) {
        mActivityUtils!!.showToast(msg)
    }

    fun showToast(msgId: Int) {
        mActivityUtils!!.showToast(msgId)
    }

    fun checkNet() {
        val networkAvailable = NetWorkUtil.isNetworkAvailable(mActivity)
        if (!networkAvailable) {
            showToast(R.string.hint_net_fail)
        } else {
            showToast(R.string.hint_service)
        }
    }

    fun resetNodata(nodata: RelativeLayout, hintId: Int) {
        val ivNodata = nodata.findViewById(R.id.iv_nodata) as ImageView
        val tvNodata = nodata.findViewById(R.id.tv_nodata) as TextView
        ivNodata.setImageResource(R.mipmap.no_data)
        if (hintId != 0) {
            tvNodata.setText(hintId)
        }
        nodata.visibility = View.GONE
    }

    //点击空白处隐藏软键盘
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // TODO Auto-generated method stub
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v!!.windowToken, 0)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.height
            val right = left + v.width
            return if (event.x > left && event.x < right
                    && event.y > top && event.y < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                false
            } else {
                true
            }
        }
        return false
    }
}