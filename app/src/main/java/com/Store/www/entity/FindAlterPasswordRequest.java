package com.Store.www.entity;

/**
 * 找回并修改密码请求体
 */

public class FindAlterPasswordRequest {

    /**
     * mobilephone : 手机号码
     * newPassword : 新密码，加密后
     * code : 验证码
     */

    private String mobilephone;
    private String newPassword;
    private String code;

    public FindAlterPasswordRequest(String mobilephone, String newPassword, String code) {
        this.mobilephone = mobilephone;
        this.newPassword = newPassword;
        this.code = code;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
