package com.Store.www.entity;

/**
 * 购物商品详情的请求体
 */

public class ShoppingCartRequest {


    /**
     * userId : 252699
     * id : 129528
     * productId : 5
     */

    private String userId;
    private int id;
    private int productId;

    public ShoppingCartRequest(String userId, int id, int productId) {
        this.userId = userId;
        this.id = id;
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
