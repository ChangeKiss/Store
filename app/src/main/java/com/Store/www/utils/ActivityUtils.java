package com.Store.www.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 封装了一些工具：弹吐司，跳转页面，获取屏幕宽高，隐藏显示软键盘，从Uri获取真实路径。。。
 */
public class ActivityUtils {
    // 弱引用
    private WeakReference<Activity> activityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;

    private Toast toast;
    public final static String SETTING = "EveryoneEnjoysSetting";
    private final static String Splitter = "%Splitter%";

    public ActivityUtils(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    public ActivityUtils(Fragment fragment) {
        fragmentWeakReference = new WeakReference<>(fragment);
    }

    private
    @Nullable
    Activity getActivity() {

        if (activityWeakReference != null) return activityWeakReference.get();
        if (fragmentWeakReference != null) {
            Fragment fragment = fragmentWeakReference.get();
            return fragment == null ? null : fragment.getActivity();
        }
        return null;
    }


    // 封装的弹吐司的方法
    public void showToast(CharSequence msg) {
        Activity activity = getActivity();
        if (activity != null) {
            if (toast == null) toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void showToast(int resId) {
        Activity activity = getActivity();
        if (activity != null) {
            String msg = activity.getString(resId);
            showToast(msg);
        }
    }

    /**
     * 范围内取有效值
     *
     * @param min 最小值
     * @param val 原始值
     * @param max 最大值
     * @return 符合最大最小范围的有效值
     */
    public static float bound(float min, float val, float max) {
        return Math.min(Math.max(val, min), max);
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density);
    }

    /**
     * dp转为px
     *
     * @param dp dp值
     * @return px值
     */
    public static int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    //自定义弹窗时间
    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }



    //网络请求连接失败的吐司
    public void showToastConnectError() {
        Activity activity = getActivity();
        if (activity != null) {
            String msg = "服务器或网络异常";
            showToast(msg);
        }
    }

    /**通过图片url生成Bitmap对象
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        Activity activity = getActivity();
        if (activity == null) return;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);//这里传0也没啥问题
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftKeyboard() {
        Activity activity = getActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }




    /**
     * 软键盘是否打开
     * 有问题
     *
     * @return
     */
    public boolean isShowSoftKeyboard() {
        Activity activity = getActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //获取状态信息
        return imm.isActive();//true 打开
    }

    /**
     * 截取指定字符串之间的内容
     * @param str  源字符串
     * @param strStart  起始字符串
     * @param strEnd    结束字符串
     * @return
     */
    public static String  getInsideString(String  str, String strStart, String strEnd ) {
        if ( str.indexOf(strStart) < 0 ){
            return "";
        }
        if ( str.indexOf(strEnd) < 0 ){
            return "";
        }
        return str.substring(str.indexOf(strStart) + strStart.length(), str.indexOf(strEnd));
    }


    /**
     * 验证是否有安装招行app
     * @param
     * @return
     */
    public static boolean isCMBAppInstalled(Context context)
    {
        PackageInfo packageInfo;

        try
        {
            packageInfo = context.getPackageManager().getPackageInfo("cmb.pb", 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            packageInfo = null;
        }

        if(packageInfo ==null)
        {
            //System.out.println("没有安装");
            return false;
        }
        else
        {
            //System.out.println("已经安装");
            return true;
        }
    }


    //把分为单位的int转换为一位String

    public static String changeMoney(int money) {
        return String.format("%.0f", money / 100.00);
    }


    //把分为单位的int转换为保留两位小数的String

    public static String changeMoneys(int money) {
        return String.format("%.2f", money / 100.00);
    }

    //把米为单位的int转换为保留一位小数的String千米
    public static String changeDistance(int diatance) {
        return String.format("%.1f", diatance / 1000.0);
    }

    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *转换时间戳至年月日时分秒
     * @param time
     * @return
     */
    public static String times(long time) {
//        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat mat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times=mat.format(calendar.getTime());
        return times;

    }

    //转换时间戳至年月日时分
    public static String time(long time) {
//        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat mat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String times =mat.format(calendar.getTime());
        return times;
    }

    //转换时间戳至年月日
    public static String YMDTime(long time) {
//        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat mat=new SimpleDateFormat("yyyy-MM-dd");
        String times =mat.format(calendar.getTime());
        return times;
    }

    //转换时间戳为时分
    public static String getHoursAndMinutes(long time){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat mat=new SimpleDateFormat("HH:mm");
        String times =mat.format(calendar.getTime());
        return times;
    }

    //转换Data类型的时间为年
    public static int getYear(Date date) {//可根据需要自行截取数据显示

        LogUtils.d("getTime()"+"choice date millis: " + date.getTime());

        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(format.format(date.getTime()));
        return year;

    }
    //转换Data类型的时间为月
    public static int getMonth(Date date) {//可根据需要自行截取数据显示

        LogUtils.d("getTime()"+"choice date millis: " + date.getTime());

        SimpleDateFormat format = new SimpleDateFormat("MM");
        int month = Integer.parseInt(format.format(date.getTime()));
        return month;

    }

    /**
     * 转换时间戳为 月日
     */
    public static String MDTime(long time){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat mat=new SimpleDateFormat("MM/dd");
        String times =mat.format(calendar.getTime());
        return times;
    }



    //给密码加盐
    /**生成32位MD5码
     */
    public static String Md5Password(String password){
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b:result){
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";

        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *  @param context
     *  @return true 表示开启
     */
    public static final boolean isOPenGps(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
       /* // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);*/  //暂时不用 注释于2019-1-9
        if (gps ) {
            return true;
        }
        return false;
    }


    //去除字符串中的空格
    public static String  stringTrim(String str){
        StringTokenizer st = new StringTokenizer(str," ");
        StringBuffer buffer = new StringBuffer();
        int i = 1;
        while (st.hasMoreTokens()){
            i ++;
            buffer.append(st.nextToken());
        }
        return  buffer.toString();
    }


    // Fragment时的页面跳转
    public void startActivity(Class<? extends Activity> clazz) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    // 跳转页面
    public void startActivity(Class<? extends Activity> clazz, String idName, int id) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        intent.putExtra(idName, id);
        activity.startActivity(intent);
    }

    // 跳转页面
    public void startActivity(Class<? extends Activity> clazz, String isTrue, boolean b) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        intent.putExtra(isTrue, b);
        activity.startActivity(intent);
    }

    // 跳转页面
    public void startActivity(Class<? extends Activity> clazz, String idName, String id) {
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        intent.putExtra(idName, id);
        activity.startActivity(intent);
    }



    private static final int DECIMAL_DIGITS=2;  //小数的位数
    //限制EditText文本框只能输入两位小数
    public static void setPoint(final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")){
                    if (s.length()-1-s.toString().indexOf(".")>DECIMAL_DIGITS){
                        s=s.toString().subSequence(0,s.toString().indexOf(".")+DECIMAL_DIGITS+1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")){
                    s="0"+s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")&&s.toString().trim().length()>1){
                    if (!s.toString().substring(1,2).equals(".")){
                        editText.setText(s.subSequence(0,1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    /**
     * 打电话
     *
     * @param activity
     * @param phone
     */
    public static void callPhone(Activity activity, String phone) {
        Intent intent3 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        if (intent3.resolveActivity(activity.getPackageManager()) != null) {
            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent3);
        } else {
            Toast.makeText(activity, "没有插电话卡，不能拨打电话！", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 判断用户是否开启相机权限 返回true 表示可以使用  返回false表示不可以使用
     */
    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    /**
     * 获取当前设备的网络连接类型
     * @param context
     * @return
     */
    public static String getNetWorkStatus(Context context) {
        String netWorkType = " ";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    netWorkType = "wifi";
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            netWorkType = "2G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            netWorkType = "3G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            netWorkType = "4G";
                            break;
                        default:
                            netWorkType = "unknown";
                    }
                    break;
                default:
                    netWorkType = "unknown";
            }
        }
        return netWorkType;
    }


    //获取设备网络连接类型
    public static int getPhoneNetWork(Context context){
        //结果返回值
        int netType = 0;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 2;
            } else {
                netType = 2;
            }
        }
        return netType;
    }

    /**
     * 获取当前APP的版本编号
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 获取APP版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVersionName(Context context) {
        String verName = " ";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


    /**
     * 针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
     * @param str
     * @return 返回全部为全角字符的字符串
     */
    public static String toDBC(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }

        }
        return new String(c);
    }




}
