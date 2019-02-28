package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/11/26.
 * 上级云库存数量请求体
 */

public class SuperiorsCloudKcRequest {



    /**
     * userId : 822151
     * products : [{"count":1,"productId":209}]
     */


    private String userId;
    private List<ProductsBean> products;

    public SuperiorsCloudKcRequest(String userId, List<ProductsBean> products) {
        this.userId = userId;
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        /**
         * count : 1
         * productId : 209
         */

        private int count;
        private int productId;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }
    }
}
