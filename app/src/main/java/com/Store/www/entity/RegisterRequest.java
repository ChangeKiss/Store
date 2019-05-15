package com.Store.www.entity;

/**
 * Created by www on 2017/12/25.
 */

public class RegisterRequest {

    /**
     * user : 郑盛测试用
     * mobile : 13588032840
     * introducer : 18857738393
     * password : e10adc3949ba59abbe56e057f20f883e
     */

    private String user;
    private String mobile;
    private String code;
    private String password;

    public RegisterRequest(String user, String mobile, String code, String password) {
        this.user = user;
        this.mobile = mobile;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
