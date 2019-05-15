package com.Store.www.net;

import android.text.TextUtils;

import com.Store.www.MyApplication;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.UserPrefs;
import com.Store.www.utils.UserPrefsFirst;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by www on 2017/12/14.
 */
    //对Retrofit进行初始化
public class RetrofitClient {
    public static Api mApi;
    public static Api mApiTwo;
    public static Api mApiThree;
    public static Api mApiLongConnection;
    public static Api mApiWeChart;

    //正式的服务器地址  ****
    public static final String BASE_URL = "http://47.96.152.157:8088/webapi_v2/webapi/";

    //正式服的订单模块 和购物车模块  *****
    public static final String Base_URL_THREE = "http://47.96.152.157:8088/webapi_v2/webapi/";

    //正式服务器地址  暂时作废
    //public static final String BASE_URL = "http://sys.kivie.com/webapi_v3/webapi/";

    //支付宝支付的服务器地址
    //public static final String BASE_URL_TWO = "http://sys.kivie.com/webapi_v2/webapi/";
    public static final String BASE_URL_TWO = "http://121.43.59.111:9005/";

    //请求微信access_token服务器地址
    public static final String WE_CHART_URL ="https://api.weixin.qq.com/";

   /* //韩迪测试服务器地址
    public static final String BASE_URL = "http://7.7.2.82:8083/";

    //陈韩迪测试服订单的服务器地址  订单模块 和 购物车模块
    //public static final String Base_URL_THREE = "http://7.7.2.52:8081/";
    public static final String Base_URL_THREE = "http://7.7.2.82:8082/";*/



    //朱峰服务器地址
    //public static final String BASE_URL = "http://6.6.6.170:8081/";

    //孙博文服务器地址
    //public static final String BASE_URL = "http://7.7.3.81:8082/";

    public static Api create(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();  //实例化日志拦截器
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  //设置日志拦截器级别
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS);
        okHttpClient.addNetworkInterceptor(interceptor);   //添加日志管理器
        if (TextUtils.isEmpty(UserPrefsFirst.getInstance().getNetWork()))UserPrefsFirst.getInstance().setNetWork(ActivityUtils.getNetWorkStatus(MyApplication.getmContext()));
        if (TextUtils.isEmpty(UserPrefsFirst.getInstance().getCodeName()))UserPrefsFirst.getInstance().setCodeNma(ActivityUtils.getVersionName(MyApplication.getmContext()));
        if (TextUtils.isEmpty(UserPrefs.getInstance().getLoginToken()))UserPrefs.getInstance().setLoginToken("0");
        okHttpClient.addInterceptor(new Interceptor() {     //添加请求头
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("n",UserPrefsFirst.getInstance().getNetWork())
                        .addHeader("d",android.os.Build.BRAND+","+android.os.Build.MODEL+","+android.os.Build.VERSION.RELEASE)  //设备厂商+设备型号+操作系统
                        .addHeader("v", UserPrefsFirst.getInstance().getCodeName())
                        .addHeader("t", "android")
                        .addHeader("token",UserPrefs.getInstance().getLoginToken())
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);

            }
        });
        OkHttpClient client = okHttpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                //给Retrofit设置OkHttpClient
                .client(client)
                .baseUrl(BASE_URL)
                //给Retrofit添加Gson的转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建Retrofit的API接口：构建的过程
        mApi = retrofit.create(Api.class);
        return mApi;
    }



    //支付宝用
    public static Api creates(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addNetworkInterceptor(interceptor);
        if (TextUtils.isEmpty(UserPrefsFirst.getInstance().getNetWork()))UserPrefsFirst.getInstance().setNetWork(ActivityUtils.getNetWorkStatus(MyApplication.getmContext()));
        if (TextUtils.isEmpty(UserPrefsFirst.getInstance().getCodeName()))UserPrefsFirst.getInstance().setCodeNma(ActivityUtils.getVersionName(MyApplication.getmContext()));
        if (TextUtils.isEmpty(UserPrefs.getInstance().getLoginToken()))UserPrefs.getInstance().setLoginToken("0");
        okHttpClient.addInterceptor(new Interceptor() {   //给每个接口统一添加请求头
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("n",UserPrefsFirst.getInstance().getNetWork())
                        .addHeader("d",android.os.Build.BRAND+","+android.os.Build.MODEL+","+android.os.Build.VERSION.RELEASE)  //设备厂商+设备型号+操作系统
                        .addHeader("v", UserPrefsFirst.getInstance().getCodeName())
                        .addHeader("t", "android")
                        .addHeader("token",UserPrefs.getInstance().getLoginToken())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);

            }
        });
        OkHttpClient client = okHttpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                //给Retrofit设置OkHttpClient
                .client(client)
                .baseUrl(BASE_URL_TWO)
                //给Retrofit添加Gson的转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建Retrofit的API接口：构建的过程
        mApiTwo = retrofit.create(Api.class);
        return mApiTwo;
    }

    //测试用
    public static Api createThree(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.addNetworkInterceptor(interceptor);
        if (TextUtils.isEmpty(UserPrefsFirst.getInstance().getNetWork()))UserPrefsFirst.getInstance().setNetWork(ActivityUtils.getNetWorkStatus(MyApplication.getmContext()));
        if (TextUtils.isEmpty(UserPrefsFirst.getInstance().getCodeName()))UserPrefsFirst.getInstance().setCodeNma(ActivityUtils.getVersionName(MyApplication.getmContext()));
        if (TextUtils.isEmpty(UserPrefs.getInstance().getLoginToken()))UserPrefs.getInstance().setLoginToken("0");
        okHttpClient.addInterceptor(new Interceptor() {     //给每个接口统一添加请求头
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("n",UserPrefsFirst.getInstance().getNetWork())
                        .addHeader("d",android.os.Build.BRAND+","+android.os.Build.MODEL+","+android.os.Build.VERSION.RELEASE)  //设备厂商+设备型号+操作系统
                        .addHeader("v", UserPrefsFirst.getInstance().getCodeName())
                        .addHeader("t", "android")
                        .addHeader("token",UserPrefs.getInstance().getLoginToken())
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);

            }
        });
        OkHttpClient client = okHttpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                //给Retrofit设置OkHttpClient
                .client(client)
                .baseUrl(Base_URL_THREE)
                //给Retrofit添加Gson的转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建Retrofit的API接口：构建的过程
        mApiThree = retrofit.create(Api.class);
        return mApiThree;
    }

    /**
     * 单独给报表使用的长时间网络请求
     * @return
     */
    public static Api createApiLongConnection(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(1,TimeUnit.SECONDS).readTimeout(10,TimeUnit.SECONDS);  //设置请求读取时间5秒 超过5秒即认为连接超时
        okHttpClient.addNetworkInterceptor(interceptor);
        if (TextUtils.isEmpty(UserPrefsFirst.getInstance().getNetWork()))UserPrefsFirst.getInstance().setNetWork(ActivityUtils.getNetWorkStatus(MyApplication.getmContext()));
        if (TextUtils.isEmpty(UserPrefsFirst.getInstance().getCodeName()))UserPrefsFirst.getInstance().setCodeNma(ActivityUtils.getVersionName(MyApplication.getmContext()));
        if (TextUtils.isEmpty(UserPrefs.getInstance().getLoginToken()))UserPrefs.getInstance().setLoginToken("0");
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder().addHeader("n",UserPrefsFirst.getInstance().getNetWork())
                        .addHeader("d",android.os.Build.BRAND+","+android.os.Build.MODEL+","+android.os.Build.VERSION.RELEASE)  //设备厂商+设备型号+操作系统
                        .addHeader("v", UserPrefsFirst.getInstance().getCodeName())
                        .addHeader("t", "android")
                        .addHeader("token",UserPrefs.getInstance().getLoginToken())
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = okHttpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                //给Retrofit添加Gson的转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiLongConnection = retrofit.create(Api.class);
        return mApiLongConnection;
    }

    /**
     * 微信登录网络请求
     * @return
     */
    public static Api createWeChartConnection(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(WE_CHART_URL)
                //给Retrofit添加Gson的转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiWeChart = retrofit.create(Api.class);
        return mApiWeChart;
    }


    public static synchronized Api getInstances(){  //正式的API
        if (mApi == null) {
            mApi = create();
        }
        return mApi;
    }

    public static synchronized Api getInstancesTwo(){  //支付宝支付时调这个方法
        if (mApiTwo == null) {
            mApiTwo = creates();
        }
        return mApiTwo;
    }

    public static synchronized Api getInstancesTest(){  //测试时的API
        if (mApiThree == null) {
            mApiThree = createThree();
        }
        return mApiThree;
    }

    /**
     * 报表数据请求时长时间网络请求
     * @return
     */
    public static synchronized Api getApiLongConnection(){
        if (mApiLongConnection == null){
            mApiLongConnection = createApiLongConnection();
        }
        return mApiLongConnection;
    }

    /**
     * 微信登录
     * @return
     */
    public static synchronized Api getApiWeChart(){
        if (mApiWeChart==null){
            mApiWeChart = createWeChartConnection();
        }
        return mApiWeChart;
    }


}
