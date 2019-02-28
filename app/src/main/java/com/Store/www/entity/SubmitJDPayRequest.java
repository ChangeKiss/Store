package com.Store.www.entity;

/**
 * Created by www on 2018/6/16.
 * 提交京东支付请求体
 */

public class SubmitJDPayRequest {

    /**
     * jdNo : 8757584
     * code : 78787
     */

    private String jdNo;
    private String code;

    public SubmitJDPayRequest(String jdNo, String code) {
        this.jdNo = jdNo;
        this.code = code;
    }

    public String getJdNo() {
        return jdNo;
    }

    public void setJdNo(String jdNo) {
        this.jdNo = jdNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
