package com.Store.www.entity;

/**
 * Created by www on 2018/6/16.
 * 创建京东支付响应体
 */

public class JDPayResponse {

    /**
     * returnValue : 1
     * jdNo : 596829669798481920
     */

    private int returnValue;
    private String jdNo;
    private String errMsg;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getJdNo() {
        return jdNo;
    }

    public void setJdNo(String jdNo) {
        this.jdNo = jdNo;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
