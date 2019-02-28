package com.Store.www.entity;

/**
 * 删除商品请求体
 */

public class DeleteCartRequest {

    /**
     * cartId : 129688
     * userId : 252699
     */

    private int cartId;
    private String userId;

    public DeleteCartRequest(int cartId, String userId) {
        this.cartId = cartId;
        this.userId = userId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
