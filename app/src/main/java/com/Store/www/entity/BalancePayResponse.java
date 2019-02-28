package com.Store.www.entity;

/**
 * Created by www on 2018/7/23.
 * 余额支付响应体
 */

public class BalancePayResponse {

    /**
     * total : 445000
     * returnValue : 1
     * errMsg : 成功
     */

    private int total;
    private int returnValue;
    private String errMsg;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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
