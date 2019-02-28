package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 资金申请时判断是否是台湾代理的响应体
 */
public class JudgeTaiWanAgentResponse {

    /**
     * returnValue : 1
     * errMsg : dhfgjk
     * open : 1
     */

    private int returnValue;
    private String errMsg;
    private int open;

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

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }
}
