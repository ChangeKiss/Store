package com.Store.www.entity;

import java.util.List;

/**
 * 购物车提交结算请求体
 */

public class CloseAccountRequest {

    /**
     * userId : 252699
     * products : [{"productId":2,"count":55}]
     */

    private String userId;
    private int isAndroidNewVersion;
    private List<ProductsBean> products;

    public CloseAccountRequest(String userId, int isAndroidNewVersion, List<ProductsBean> products) {
        this.userId = userId;
        this.isAndroidNewVersion = isAndroidNewVersion;
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsAndroidNewVersion() {
        return isAndroidNewVersion;
    }

    public void setIsAndroidNewVersion(int isAndroidNewVersion) {
        this.isAndroidNewVersion = isAndroidNewVersion;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        /**
         * productId : 2
         * count : 55
         */

        private int productId;
        private int count;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
