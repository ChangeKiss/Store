package com.Store.www.entity;

/**
 * 余额支付请求体
 */

public class BalancePayRequest {

    /**
     * agentId : 252699
     * orderNo : 订单编号, 字符串
     * paymentPassword : 密码, 字符串
     * status : 0
     * money : 99
     * isUserBalance : 1 是否使用余额
     */

    private int agentId;
    private String orderNo;
    private String paymentPassword;
    private int status;
    private int money;
    private int isUserBalance;

    public BalancePayRequest(int agentId, String orderNo, String paymentPassword, int status, int money, int isUserBalance) {
        this.agentId = agentId;
        this.orderNo = orderNo;
        this.paymentPassword = paymentPassword;
        this.status = status;
        this.money = money;
        this.isUserBalance = isUserBalance;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getIsUserBalance() {
        return isUserBalance;
    }

    public void setIsUserBalance(int isUserBalance) {
        this.isUserBalance = isUserBalance;
    }
}
