package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/10.
 * 实体仓库库存响应体
 */

public class EntityWarehouseStocksResponse {

    /**
     * returnValue : 1
     * map : [{"productId":138,"name":"蕾丝女士能量裤","sum":87},{"productId":206,"name":"樱花200件包","sum":5},{"productId":205,"name":"樱花100件包","sum":0},{"productId":208,"name":"樱花70件包","sum":5},{"productId":207,"name":"樱花内裤100件包","sum":3},{"productId":195,"name":"慕斯","sum":0},{"productId":182,"name":"撩裤","sum":0},{"productId":85,"name":"金薇月色玲珑大码文胸","sum":0},{"productId":192,"name":"魅惑","sum":0},{"productId":2,"name":"金薇高端款胸罩","sum":0},{"productId":152,"name":"金薇玲珑款T裤（台湾）","sum":0},{"productId":179,"name":"Seek","sum":0},{"productId":183,"name":"羽心","sum":0},{"productId":178,"name":"魅丝（内衣）","sum":0},{"productId":160,"name":"色U-网纱款内衣","sum":0},{"productId":197,"name":"男T-型动派","sum":0},{"productId":1,"name":"金薇呼吸款胸罩","sum":0},{"productId":67,"name":"金薇第二梦单品","sum":0},{"productId":165,"name":"梦露-安全裤","sum":0},{"productId":153,"name":"金薇男士KK裤（台湾）","sum":0}]
     */

    private int returnValue;
    private List<MapBean> map;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public List<MapBean> getMap() {
        return map;
    }

    public void setMap(List<MapBean> map) {
        this.map = map;
    }

    public static class MapBean {
        /**
         * productId : 138
         * name : 蕾丝女士能量裤
         * sum : 87
         */

        private int productId;
        private String name;
        private int sum;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }
    }
}
