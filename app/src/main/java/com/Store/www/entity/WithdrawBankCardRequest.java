package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 提现到银行卡请求体
 */
public class WithdrawBankCardRequest {

    /**
     * coupletNumber : 111111111111
     * money : 10000
     * agentId : 822125
     * cardNumber : 1211
     * paymentPassword : 4607e782c4d86fd5364d7e4508bb10d9
     * phoneNumber : 13500000000
     * name : 测试
     * idNumber : 22222
     */

    private String coupletNumber;
    private int money;
    private String agentId;
    private String cardNumber;
    private String paymentPassword;
    private String phoneNumber;
    private String name;
    private String idNumber;

    public WithdrawBankCardRequest(String coupletNumber, int money, String agentId, String cardNumber, String paymentPassword, String phoneNumber, String name, String idNumber) {
        this.coupletNumber = coupletNumber;
        this.money = money;
        this.agentId = agentId;
        this.cardNumber = cardNumber;
        this.paymentPassword = paymentPassword;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.idNumber = idNumber;
    }

    public String getCoupletNumber() {
        return coupletNumber;
    }

    public void setCoupletNumber(String coupletNumber) {
        this.coupletNumber = coupletNumber;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
