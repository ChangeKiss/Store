package com.Store.www.entity;

/**
 * Created by www on 2018/9/3.
 * 提交升级请求
 */

public class SubmitUpBegRequest {


    /**
     * agentId : 822126
     */

    private int agentId;

    public SubmitUpBegRequest(int agentId) {
        this.agentId = agentId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
}
