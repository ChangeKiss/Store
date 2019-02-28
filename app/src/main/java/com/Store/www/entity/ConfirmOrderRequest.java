package com.Store.www.entity;

/**
 * 确认订单的请求体
 */

public class ConfirmOrderRequest {

    /**
     * userId : 252699
     * orderNo : xxxx
     */

    private int userId;
    private String orderNo;

    public ConfirmOrderRequest(int userId, String orderNo) {
        this.userId = userId;
        this.orderNo = orderNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
