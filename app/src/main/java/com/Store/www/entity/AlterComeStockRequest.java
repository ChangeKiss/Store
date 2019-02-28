package com.Store.www.entity;

/**
 * Created by www on 2018/10/16.
 * 修改出库商品请求体
 */

public class AlterComeStockRequest {

    /**
     * id : 2
     * sum : 22
     */

    private int id;
    private int sum;

    public AlterComeStockRequest(int id, int sum) {
        this.id = id;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
