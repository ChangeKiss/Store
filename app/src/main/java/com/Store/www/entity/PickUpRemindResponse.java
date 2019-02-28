package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 提货提现响应体
 */
public class PickUpRemindResponse {

    /**
     * returnValue : 1
     * errMsg : 成功
     * remind : 2131231312312312
     */

    private int returnValue;
    private String errMsg;
    private String remind;

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

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }
}
