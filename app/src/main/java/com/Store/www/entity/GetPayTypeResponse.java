package com.Store.www.entity;

/**
 * 获取支付类型
 */

public class GetPayTypeResponse {

    /**
     * returnValue : 1
     * data : 0
     * errMsg : sd
     */

    private int returnValue;
    private int data;
    private String errMsg;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
