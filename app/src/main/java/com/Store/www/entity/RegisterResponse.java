package com.Store.www.entity;

/**
 * 用户注册请求体
 */

public class RegisterResponse {

    /**
     * user : 郑盛测试用
     * mobile : 13588032840
     * introducer : 18857738393
     * password : e10adc3949ba59abbe56e057f20f883e
     */

    private String user;
    private String mobile;
    private String introducer;
    private String password;

    public RegisterResponse(String user, String mobile, String introducer, String password) {
        this.user = user;
        this.mobile = mobile;
        this.introducer = introducer;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
