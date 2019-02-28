package com.Store.www.entity;

import java.util.List;

/**
 * 全选删除购物车请求体
 */

public class AllDeleteCartRequest {

    /**
     * userId : 14542
     * cartIds : [{"cartId":5334},{"cartId":5335}]
     */

    private String userId;
    private List<CartIdsBean> cartIds;

    public AllDeleteCartRequest(String userId, List<CartIdsBean> cartIds) {
        this.userId = userId;
        this.cartIds = cartIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartIdsBean> getCartIds() {
        return cartIds;
    }

    public void setCartIds(List<CartIdsBean> cartIds) {
        this.cartIds = cartIds;
    }

    public static class CartIdsBean {
        /**
         * cartId : 5334
         */

        private int cartId;

        public int getCartId() {
            return cartId;
        }

        public void setCartId(int cartId) {
            this.cartId = cartId;
        }
    }
}
