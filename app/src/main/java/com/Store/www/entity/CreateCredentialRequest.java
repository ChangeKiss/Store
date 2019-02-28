package com.Store.www.entity;

/**
 * 生成证书请求体
 */

public class CreateCredentialRequest {

    /**
     * agentId : 252699
     * headPicture : 头像，字符串
     */

    private int agentId;
    private String headPicture;

    public CreateCredentialRequest(int agentId, String headPicture) {
        this.agentId = agentId;
        this.headPicture = headPicture;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }
}
