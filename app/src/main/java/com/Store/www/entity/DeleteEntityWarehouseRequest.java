package com.Store.www.entity;

/**
 * Created by www on 2018/10/10.
 * 删除实体仓库请求体
 */

public class DeleteEntityWarehouseRequest {

    /**
     * id : 1
     */

    private int id;
    private int agentId;

    public DeleteEntityWarehouseRequest(int id, int agentId) {
        this.id = id;
        this.agentId = agentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
}
