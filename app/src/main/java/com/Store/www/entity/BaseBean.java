package com.Store.www.entity;

/**
 * 一个基础的消息响应体
 */

public class BaseBean {
    private int returnValue;
    private String error;



    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
