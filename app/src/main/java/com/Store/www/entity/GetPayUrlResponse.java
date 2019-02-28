package com.Store.www.entity;

/**
 * 获取支付链接的响应体
 */

public class GetPayUrlResponse {

    /**
     * returnValue : 1
     * errMsg : 成功
     * url : alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/bax07948wdekldaywrsr2069
     */

    private int returnValue;
    private String errMsg;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
