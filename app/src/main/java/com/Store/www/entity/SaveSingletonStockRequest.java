package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/11.
 * 保存实体仓库单件库存请求体
 */

public class SaveSingletonStockRequest {

    /**
     * skuList : [{"sum":4,"proColSizeId":10594},{"sum":3,"proColSizeId":10595},{"sum":5,"proColSizeId":10596},{"sum":64,"proColSizeId":10597}]
     * warehouseId : 1
     */

    private int warehouseId;
    private List<SkuListBean> skuList;

    public SaveSingletonStockRequest(int warehouseId, List<SkuListBean> skuList) {
        this.warehouseId = warehouseId;
        this.skuList = skuList;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<SkuListBean> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuListBean> skuList) {
        this.skuList = skuList;
    }

    public static class SkuListBean {
        /**
         * sum : 4
         * proColSizeId : 10594
         */

        private int sum;
        private int proColSizeId;

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public int getProColSizeId() {
            return proColSizeId;
        }

        public void setProColSizeId(int proColSizeId) {
            this.proColSizeId = proColSizeId;
        }
    }
}
