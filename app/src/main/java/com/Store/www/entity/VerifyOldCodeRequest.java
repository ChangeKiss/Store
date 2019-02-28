package com.Store.www.entity;

/**
 * Created by www on 2018/6/4.
 * 验证旧的手机验证码请求体
 */

public class VerifyOldCodeRequest {

    /**
     * mobilephone : 手机号码
     * code : 验证码
     */

    private String mobilephone;
    private String code;

    public VerifyOldCodeRequest(String mobilephone, String code) {
        this.mobilephone = mobilephone;
        this.code = code;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
