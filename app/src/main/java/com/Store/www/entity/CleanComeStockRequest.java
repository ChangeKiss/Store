package com.Store.www.entity;

/**
 * Created by www on 2018/10/16.
 * 清空出库记录请求体
 */

public class CleanComeStockRequest {

    /**
     * warehouseId : 6
     */

    private int warehouseId;

    public CleanComeStockRequest(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
}
