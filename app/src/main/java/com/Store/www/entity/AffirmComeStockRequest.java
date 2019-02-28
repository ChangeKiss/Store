package com.Store.www.entity;

/**
 * Created by www on 2018/10/16.
 * 确认出库请求体
 */

public class AffirmComeStockRequest {

    /**
     * warehouseId : 6
     * remark : 备注, 字符串
     */

    private int warehouseId;
    private String remark;
    private String agentCode;

    public AffirmComeStockRequest(int warehouseId, String remark, String agentCode) {
        this.warehouseId = warehouseId;
        this.remark = remark;
        this.agentCode = agentCode;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
}
