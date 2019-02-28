package com.Store.www.entity;

/**
 * 支付宝返回的响应体
 */

public class AliPayResponse {


    /**
     * mesage : 成功获取地址
     * orderNo : 545000262088011776
     * state : 1
     * url : alipayqr://platformapi/startapp?saId=10000007&qrcode=https%3A%2F%2Fqr.alipay.com%2Fbax08229lq6styixcwsd80b8%3F_s%3Dweb-other&_t=1516773163633
     */

    private String mesage;
    private String orderNo;
    private int state;
    private String url;

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
