package com.Store.www.entity;

/**
 * 判断代理是否有证书的请求体
 */

public class JudgeCredentialRequest {
    private int agentId;

    public JudgeCredentialRequest(int agentId) {
        this.agentId = agentId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
}
