package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 支付保证金请求体
 */
public class PayBoundRequest {

    /**
     * agentId : 822125
     * appType : 1
     */

    private int agentId;
    private int appType;

    public PayBoundRequest(int agentId, int appType) {
        this.agentId = agentId;
        this.appType = appType;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }
}
