package com.Store.www.entity;

/**
 * 删除订单请求体
 */

public class DeleteOrderRequest {

    /**
     * userId : 1
     * orderNumber : 11
     */

    private String userId;
    private String orderNumber;

    public DeleteOrderRequest(String userId, String orderNumber) {
        this.userId = userId;
        this.orderNumber = orderNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
