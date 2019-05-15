package com.Store.www.entity;

/**
 * 验证码登录响应体
 * @author: haifeng
 * @description:
 */
public class CodeLoginRequest {

    /**
     * mobilephone : 18967823374
     * code : 333926
     */

    public CodeLoginRequest(String mobilephone, String code) {
        this.mobilephone = mobilephone;
        this.code = code;
    }


    private String mobilephone;
    private String code;

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
