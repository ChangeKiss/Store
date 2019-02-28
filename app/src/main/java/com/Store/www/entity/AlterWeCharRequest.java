package com.Store.www.entity;

/**
 * 修改微信请求体
 */

public class AlterWeCharRequest {

    /**
     * agentId : 252699
     * weChat : 微信，字符串
     */

    private int agentId;
    private String weChat;

    public AlterWeCharRequest(int agentId, String weChat) {
        this.agentId = agentId;
        this.weChat = weChat;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }
}
