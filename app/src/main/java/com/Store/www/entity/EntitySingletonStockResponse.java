package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/11.
 * 实体商品单件库存响应体
 */

public class EntitySingletonStockResponse {

    /**
     * returnValue : 1
     * list : [{"repositoryCount":3,"color":"品红","size":"M","id":10594,"sku":"20180103-1R02M","outSum":0},{"repositoryCount":3,"color":"品红","size":"L","id":10595,"sku":"20180103-1R02L","outSum":0},{"repositoryCount":3,"color":"品红","size":"XL","id":10596,"sku":"20180103-1R02XL","outSum":0},{"repositoryCount":3,"color":"品红","size":"2XL","id":10597,"sku":"20180103-1R022XL","outSum":0}]
     */

    private int returnValue;
    private List<ListBean> list;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * repositoryCount : 3
         * color : 品红
         * size : M
         * id : 10594
         * sku : 20180103-1R02M
         * outSum : 0
         */

        private int repositoryCount;
        private String color;
        private String size;
        private int id;
        private String sku;
        private int outSum;

        public int getRepositoryCount() {
            return repositoryCount;
        }

        public void setRepositoryCount(int repositoryCount) {
            this.repositoryCount = repositoryCount;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public int getOutSum() {
            return outSum;
        }

        public void setOutSum(int outSum) {
            this.outSum = outSum;
        }
    }
}
