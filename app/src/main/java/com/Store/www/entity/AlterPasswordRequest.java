package com.Store.www.entity;

/**
 * 修改密码请求体
 */

public class AlterPasswordRequest {

    /**
     * userId : 818206
     * password : e10adc3949ba59abbe56e057f20f883e
     * oldpassword : 670b14728ad9902aecba32e22fa4f6bd
     */

    private String userId;
    private String password;
    private String oldpassword;

    public AlterPasswordRequest(String userId, String password, String oldpassword) {
        this.userId = userId;
        this.password = password;
        this.oldpassword = oldpassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }
}
