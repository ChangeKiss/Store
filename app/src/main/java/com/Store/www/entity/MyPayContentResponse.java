package com.Store.www.entity;

/**
 * 获取自己的收款信息的响应体
 */

public class MyPayContentResponse {

    /**
     * returnValue : 1
     * data : 香烟瓜子啤酒有需要的吗？
     * errMsg : null
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
