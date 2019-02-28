package com.Store.www.entity;

/**
 * 支付确认请求体
 */

public class PayAffirmRequest {

    /**
     * orderNo : DD20170705193326
     */

    private String orderNo;
    private int orderType;

    public PayAffirmRequest(String orderNo, int orderType) {
        this.orderNo = orderNo;
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
