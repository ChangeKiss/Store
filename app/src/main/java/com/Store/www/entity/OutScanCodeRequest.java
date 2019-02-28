package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 出库扫码请求体
 */
public class OutScanCodeRequest {

    /**
     * agentCode : 18857738393
     * code : 123456
     * warehouseId : 2
     */

    private String agentCode;
    private String code;
    private int warehouseId;

    public OutScanCodeRequest(String agentCode, String code, int warehouseId) {
        this.agentCode = agentCode;
        this.code = code;
        this.warehouseId = warehouseId;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
}
