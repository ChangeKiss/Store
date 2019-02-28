package com.Store.www.entity;

/**
 * 是否有密码的请求体
 */

public class WhetherPasswordRequest {

    /**
     * agentId : 822125
     */

    private int agentId;

    public WhetherPasswordRequest(int agentId) {
        this.agentId = agentId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
}
