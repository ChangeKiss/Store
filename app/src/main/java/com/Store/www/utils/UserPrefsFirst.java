package com.Store.www.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用来存是否是第一次安装APP
 */

public class UserPrefsFirst {
    private final SharedPreferences preferences;


    private static UserPrefsFirst userPrefsFirst;

    public static void init(Context context) {
        userPrefsFirst = new UserPrefsFirst(context);
    }
    private String PREFS_NAME = "user_info";
    private String FIRST = "first";  //是否是第一次打开APP的版本号
    private String CODE_NAME = "codeName";  //APP的版本编号
    private String NETWORK = "newWork";  //网络类型

    private UserPrefsFirst(Context context) {
        preferences = context.getApplicationContext().
                getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }



    public static UserPrefsFirst getInstance() {
        return userPrefsFirst;
    }


    public void setFirst(int first){
        preferences.edit().putInt(FIRST,first).commit();
    }

    public int getFirst(){
        return preferences.getInt(FIRST,0);
    }

    public void setCodeNma(String codeName){
        preferences.edit().putString(CODE_NAME,codeName).commit();
    }

    public String getCodeName(){
        return  preferences.getString(CODE_NAME,null);
    }

    public void setNetWork(String netWork){
        preferences.edit().putString(NETWORK,netWork).commit();
    }

    public String getNetWork(){

        return preferences.getString(NETWORK,null);
    }
}
