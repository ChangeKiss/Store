package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 提现订单回退响应体
 */
public class WithdrawOrderBackRequest {

    /**
     * orderIdList : [247258]
     * agentId : 822125
     * paymentPassword : 4607e782c4d86fd5364d7e4508bb10d9
     * money : 10000
     */

    private String agentId;
    private String paymentPassword;
    private int money;
    private List<Integer> orderIdList;

    public WithdrawOrderBackRequest(String agentId, String paymentPassword, int money, List<Integer> orderIdList) {
        this.agentId = agentId;
        this.paymentPassword = paymentPassword;
        this.money = money;
        this.orderIdList = orderIdList;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Integer> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<Integer> orderIdList) {
        this.orderIdList = orderIdList;
    }
}
