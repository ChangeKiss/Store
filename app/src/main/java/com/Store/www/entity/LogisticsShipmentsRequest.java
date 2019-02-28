package com.Store.www.entity;

/**
 * 物流发货请求体
 */

public class LogisticsShipmentsRequest {

    /**
     * orderNo : DD201800301221425
     * userId : 822125
     * company : fggg
     * number : fgg
     */

    private String orderNo;
    private String userId;
    private String company;
    private String number;

    public LogisticsShipmentsRequest(String orderNo, String userId, String company, String number) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.company = company;
        this.number = number;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
