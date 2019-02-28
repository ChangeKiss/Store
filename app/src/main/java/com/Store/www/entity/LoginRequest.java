package com.Store.www.entity;

/**
 * 登录请求体
 */

public class LoginRequest {

    /**
     * mobilephone : 13588032842
     * password : e10adc3949ba59abbe56e057f20f883e
     */

    private String mobilephone;
    private String password;

    public LoginRequest(String mobilephone, String password) {
        this.mobilephone = mobilephone;
        this.password = password;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
