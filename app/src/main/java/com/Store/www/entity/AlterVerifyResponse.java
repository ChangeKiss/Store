package com.Store.www.entity;

/**
 * Created by www on 2018/6/4.
 * 修改验证码响应体
 */

public class AlterVerifyResponse {

    /**
     * returnValue : 1
     * data : 验证码
     */

    private int returnValue;
    private String data;
    private String errMsg;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
