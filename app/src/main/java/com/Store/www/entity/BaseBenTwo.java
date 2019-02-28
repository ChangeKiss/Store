package com.Store.www.entity;

/**
 * 消息响应体
 */

public class BaseBenTwo {
    private int returnValue;
    private String errMsg;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
