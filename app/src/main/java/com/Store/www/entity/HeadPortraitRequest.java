package com.Store.www.entity;

/**
 * 保存头像的请求体
 */

public class HeadPortraitRequest {

    /**
     * agentId : 252699
     * headPicture : 11111
     */

    private int agentId;
    private String headPicture;

    public HeadPortraitRequest(int agentId, String headPicture) {
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
