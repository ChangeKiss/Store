package com.Store.www.entity;

/**
 * 登出的请求体
 */

public class LoginOutRequest {

    /**
     * agentId : 822125
     */

    private String agentId;

    public LoginOutRequest(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
