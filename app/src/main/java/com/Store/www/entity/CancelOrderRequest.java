package com.Store.www.entity;

/**
 * 取消订单请求体
 */

public class CancelOrderRequest {

    /**
     * orderNumber : TH201712224072
     * userId : 252699
     */

    private String orderNumber;
    private String userId;

    public CancelOrderRequest(String orderNumber, String userId) {
        this.orderNumber = orderNumber;
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
