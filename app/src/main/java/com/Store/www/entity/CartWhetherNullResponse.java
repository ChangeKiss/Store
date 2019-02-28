package com.Store.www.entity;

/**
 * 购物车是否是空的响应体
 */

public class CartWhetherNullResponse {

    /**
     * returnValue : 1
     * data : {"cartCount":0,"isCartHaveCommodity":false}
     * errMsg : null
     */

    private int returnValue;
    private DataBean data;
    private Object errMsg;



    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public static class DataBean {
        /**
         * cartCount : 0
         * isCartHaveCommodity : false
         */

        private int cartCount;
        private boolean isCartHaveCommodity;

        public int getCartCount() {
            return cartCount;
        }

        public void setCartCount(int cartCount) {
            this.cartCount = cartCount;
        }

        public boolean isIsCartHaveCommodity() {
            return isCartHaveCommodity;
        }

        public void setIsCartHaveCommodity(boolean isCartHaveCommodity) {
            this.isCartHaveCommodity = isCartHaveCommodity;
        }
    }
}
