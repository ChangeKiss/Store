package com.Store.www.entity;

/**
 * 上传头像请求体
 */

public class IconEditRequest {

    /**
     * agentId : 252699
     * headPicture : 666666
     */

    private int agentId;
    private String headPicture;

    public IconEditRequest(int agentId, String headPicture) {
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
