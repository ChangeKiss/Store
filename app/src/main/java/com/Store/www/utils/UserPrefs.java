package com.Store.www.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.Store.www.entity.LoginResponse;

/**
 *用户仓库，保存用户信息,使用前必须先对其进行初始化 init()*****
 */

public class UserPrefs {
    private final SharedPreferences preferences;


    private static UserPrefs userPrefs;

    public static void init(Context context) {
        userPrefs = new UserPrefs(context);
    }
    private String PREFS_NAME = "user_info";
    private String USER_ID = "userId";//用户信息注册登录用id
    private String ID_INT = "id_int";//用户信息注册登录用id
    private String NICKNAME = "nickname";//用户注册时的昵称
    private String LEVEL = "level";  // 用户代理级别
    private String YEAR = "year";    // 年份
    private String MONTH = "month";  //月份
    private String AGENTCODE = "agentCode"; // 代理人ID
    private String ACCOUNT = "account"; //新代理商编号
    private String CODE = "code";  //代理商编号
    private String PHONE = "phone"; //电话号码
    private String WXI = "wxi";  //代理人微信
    private String EMAIL = "email"; //代理人邮箱
    private String USER_NAME = "user_name"; //代理人名字
    private String ICON = "headPicture"; //头像
    private String ADDRESS = "address";  //地址
    private String WIDTH = "width"; // 当前屏幕的宽
    private String HEIGHT = "height"; //当前屏幕的高
    private String ISALIPAY = "isAlipay"; //是否能使用支付宝
    private String LOGIN_TOKEN = "loginToken"; //登录的token
    private String CODE_NAME = "codeName";  //APP的版本编号
    private String ADVERTISING_PICTURE;  //请求下来的广告图片
    private String CORSETLEVELNAME;   //塑身衣等级


    private UserPrefs(Context context) {
        preferences = context.getApplicationContext().
                getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }



    public static UserPrefs getInstance() {
        return userPrefs;
    }

    public void setUserId(String userId) {
        preferences.edit().putString(USER_ID, userId).commit();
    }

    public String getUserId() {
        return preferences.getString(USER_ID, null);
    }

    public int getId(){
        return preferences.getInt(ID_INT,0);
    }

    public void setId(int id_int){
        preferences.edit().putInt(ID_INT,id_int).commit();
    }

    public String getNickName(){
        return preferences.getString(NICKNAME,null);
    }

    public void setNickName(String nickName){
        preferences.edit().putString(NICKNAME,nickName).commit();
    }

    public String getLevel(){
        return preferences.getString(LEVEL,null);
    }

    public void setLevel(String level){
        preferences.edit().putString(LEVEL,level).commit();
    }

    public String getAgentCode(){
        return preferences.getString(AGENTCODE,null);
    }

    public void setAgentCode(String agentCode){
        preferences.edit().putString(AGENTCODE,agentCode).commit();
    }

    public String getPhone(){
        return preferences.getString(PHONE,null);
    }

    public void setPhone(String phone){
        preferences.edit().putString(PHONE,phone).commit();
    }

    public String getMonth(){
        return preferences.getString(MONTH,null);
    }

    public void setMonth(String month){
        preferences.edit().putString(MONTH,month).commit();
    }

    public int getYear(){
        return preferences.getInt(YEAR,0);
    }

    public void setYear(int year){
        preferences.edit().putInt(YEAR,year).commit();
    }

    public void setWxi(String wxi){
        preferences.edit().putString(WXI,wxi).commit();
    }

    public String getWxi(){
        return preferences.getString(WXI,null);
    }

    public void setEmail(String email){
        preferences.edit().putString(EMAIL,email).commit();
    }

    public String getEmail(){
        return preferences.getString(EMAIL,null);
    }

    public void setUserName(String userName){
        preferences.edit().putString(USER_NAME,userName).commit();
    }

    public String getUserName(){
        return preferences.getString(USER_NAME,null);
    }

    public void setIcon(String iconUrl) {
        preferences.edit().putString(ICON, iconUrl).commit();
    }

    public String getIcon() {
        return preferences.getString(ICON, null);
    }

    public void setAddress(String address){
        preferences.edit().putString(ADDRESS,address).commit();
    }

    public String getAddress(){
        return preferences.getString(ADDRESS,null);
    }

    public void setCode(String code){
        preferences.edit().putString(CODE,code).commit();
    }

    public String getCode(){
        return preferences.getString(CODE,null);
    }

    public void setAccount(String account){
        preferences.edit().putString(ACCOUNT,account).commit();
    }

    public String getAccount(){
        return preferences.getString(ACCOUNT,null);
    }

    public void setWidth(int width){
        preferences.edit().putInt(WIDTH,width).commit();
    }
    public int getWidth(){
        return preferences.getInt(WIDTH,0);
    }

    public void setHeight(int height){
        preferences.edit().putInt(HEIGHT,height).commit();
    }
    public int getHeight(){
        return preferences.getInt(HEIGHT,0);
    }

    public void setIsAlipay(int isAlipay){
        preferences.edit().putInt(ISALIPAY,isAlipay).commit();
    }

    public int getIsAlipay(){
        return preferences.getInt(ISALIPAY,0);
    }


    public void setLoginToken(String loginToken){
        preferences.edit().putString(LOGIN_TOKEN,loginToken).commit();
    }

    public String getLoginToken(){
        return  preferences.getString(LOGIN_TOKEN,null);
    }

    public void setAdvertisingPicture(String advertisingPicture){
        preferences.edit().putString(ADVERTISING_PICTURE,advertisingPicture).commit();
    }

    public String getAdvertisingPicture(){
        return preferences.getString(ADVERTISING_PICTURE,null);
    }

    public void setCorsetLevelName(String CorsetLevelName){
        preferences.edit().putString(CORSETLEVELNAME,CorsetLevelName).commit();
    }

    public String getCorsetLevelName(){
        return preferences.getString(CORSETLEVELNAME,null);
    }

    //退出时清空用户信息
    public void clearUser() {
        preferences.edit().clear().commit();
    }

    //登录时获取用户信息
    public void setLoginInfo(LoginResponse data){
        setUserId(String.valueOf(data.getData().getAgentId()));
        setId(data.getData().getAgentId());
        setNickName(data.getData().getName());
        setLevel(data.getData().getLevel());
        setAgentCode(data.getData().getAgentNum());
        setPhone(data.getData().getPhone());
        setWxi((String) data.getData().getWechatNum());
        setEmail((String) data.getData().getEmail());
        setUserName(data.getData().getName());
        setAddress(data.getData().getAddress());
        setCode(data.getData().getCode());
        setAccount(data.getData().getAccount());
        setIcon(data.getData().getHeadPicture());
        setIsAlipay(data.getData().getIsAlipay());
    }


}
