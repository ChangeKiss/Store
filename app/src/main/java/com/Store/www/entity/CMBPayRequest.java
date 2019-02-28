package com.Store.www.entity;

/**
 * Created by www on 2018/11/19.
 * 招行支付请求体
 */

public class CMBPayRequest {

    /**
     * orderNo : 刷刷刷
     * userId : 1111
     */

    private String orderNo;
    private String userId;
    private int appType;

    public CMBPayRequest(String orderNo, String userId, int appType) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.appType = appType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }
}
