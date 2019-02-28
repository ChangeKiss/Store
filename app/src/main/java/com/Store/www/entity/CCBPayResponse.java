package com.Store.www.entity;

/**
 * 建行支付响应体
 */

public class CCBPayResponse {

    /**
     * returnValue : 1
     * data : [图片]https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?MERCHANTID=105331000000774&POSID=000428735&BRANCHID=330000000&ORDERID=DD20180523233149&PAYMENT=890000&CURCODE=01&TXCODE=520100&REMARK1=&REMARK2=&TYPE=1&GATEWAY=&CLIENTIP=&REGINFO=&PROINFO=%u91D1%u8587%u8BA2%u5355&REFERER=&MAC=0976a4fe210b8efe340c02f0f26d8bc9
     */

    private int returnValue;
    private String errMsg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
