package com.Store.www.entity;

/**
 * 设置支付密码请求体
 */

public class SettingPasswordRequest {

    /**
     * agentId : 252699
     * paymentPassword : 支付密码,字符串
     */

    private int agentId;
    private String paymentPassword;

    public SettingPasswordRequest(int agentId, String paymentPassword) {
        this.agentId = agentId;
        this.paymentPassword = paymentPassword;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getPaymentPassword() {
        return paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }
}
