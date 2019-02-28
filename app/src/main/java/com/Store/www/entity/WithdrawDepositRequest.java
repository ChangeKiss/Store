package com.Store.www.entity;

/**
 * 提现请求体
 */

public class WithdrawDepositRequest {

    /**
     * agentId : 252699
     * money : 999
     * paymentPassword : 支付密码,字符串
     * cardId : 2
     * coupletNumber : 联行号
     */

    private int agentId;
    private int money;
    private String paymentPassword;
    private int cardId;
    private String coupletNumber;

    public WithdrawDepositRequest(int agentId, int money, String paymentPassword, int cardId, String coupletNumber) {
        this.agentId = agentId;
        this.money = money;
        this.paymentPassword = paymentPassword;
        this.cardId = cardId;
        this.coupletNumber = coupletNumber;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCoupletNumber() {
        return coupletNumber;
    }

    public void setCoupletNumber(String coupletNumber) {
        this.coupletNumber = coupletNumber;
    }
}
