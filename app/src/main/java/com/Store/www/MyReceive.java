package com.Store.www;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.Store.www.entity.NotificationResponse;
import com.Store.www.ui.activity.MainActivity;
import com.Store.www.ui.activity.MyOrderActivity;
import com.Store.www.ui.activity.PickUpGoodsActivity;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.qiyukf.unicorn.api.Unicorn;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送的 自定义接收器
 */

public class MyReceive extends BroadcastReceiver{

    private NotificationManager notificationManager;
    private String extras;  //判断点击通知之后的跳转页面，
    public static String from="";
    public static String url="";
    public static String ad_Url = "";
    public static String ad_img = "";
    public static String customMessage = "";
    public static int messageCode = 0;  //这个消息代码的值 用来判断是否收到过消息
    public static int messageCodeId = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null ==notificationManager){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())){
            LogUtils.d("用户注册成功");
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())){
            LogUtils.d("接收到用户自定义的消息");
            receivesNotification(context,bundle);
            MainActivity.hasNotification =1;
        }else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){
            LogUtils.d("接收到推送下来的消息");
            //接收推送
            receivesNotification(context,bundle);
        }else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())){
            LogUtils.d("用户点开了通知");
            MainActivity.hasNotification =1;
            //用户点击通知跳转到应用首页
            Intent intentNotification = new Intent(context,MainActivity.class);
            intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentNotification);
        }
    }

    private void receivesNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        customMessage = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        LogUtils.d("接收到的自定义消息"+customMessage);
        extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        NotificationResponse response = new Gson().fromJson(extras,NotificationResponse.class);
        from = response.getFrom();
        url = response.getUrl();
        ad_img = response.getAd_img();
        ad_Url = response.getAd_url();
        messageCodeId = response.getCode();
        if (messageCodeId==1001){  //1001是被登出
            //被登出跳转到应用首页
            UserPrefs.getInstance().clearUser(); //退出时清空本地仓库 并跳转到登录界面
            Unicorn.logout();//清空客服端资料
            Intent intentNotification = new Intent(context,MainActivity.class);
            intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentNotification);
        }else if (messageCodeId==1005){  //1005是建行/招行支付成功
            LogUtils.d("建行支付成功");
            Intent intent = new Intent();
            intent.setAction("ccb");
            context.sendBroadcast(intent);
            LogUtils.d("广播发送成功");
            Intent intentNotification = new Intent(context,MyOrderActivity.class);
            intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentNotification);
            LogUtils.d("界面跳转成功");
        }else if (messageCodeId == 1007){  // 1007是新闻资讯以及紧急通知某些用户的通知
            messageCode ++;  //收到1007类型的消息这个值就+1
            Intent intent = new Intent();
            intent.setAction(MainActivity.SHOW_ACTION);
            context.sendBroadcast(intent);
        }else if (messageCodeId==1008){  //保证金支付成功
            Intent intentMarginPayOK =new Intent(context,MainActivity.class);
            intentMarginPayOK.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentMarginPayOK);
        }else if (messageCodeId == 1009){  //支付运费成功
            Intent intent = new Intent(context,PickUpGoodsActivity.class);
            intent.setAction("payExpressOver");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.sendBroadcast(intent);
            context.startActivity(intent);
        }
        LogUtils.d("code=="+messageCodeId);
    }




}
