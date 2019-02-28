package com.Store.www.entity;

/**
 * Created by www on 2018/8/14.
 * 查看物流请求体
 */

public class LookLogisticsRequest {

    /**
     * orderNo : DD20180629237267
     */

    private String orderNo;

    public LookLogisticsRequest(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
