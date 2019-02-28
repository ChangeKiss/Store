package com.Store.www.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 获取购物车响应体
 */

public class ShoppingCartResponse extends BaseBean implements Serializable{


    /**
     * data : [{"id":129327,"productId":62,"productName":"金薇玲珑款平角裤","productCode":"JW-W1660A","productImg":"LP.jpg","count":14,"totalPrice":49000,"price":3500,"userId":252699},{"id":129334,"productId":5,"productName":"金薇烧花款胸罩","productCode":"JW1520","productImg":"烧花款00.jpg","count":1,"totalPrice":8900,"price":8900,"userId":252699},{"id":129340,"productId":7,"productName":"金薇樱花恋胸罩","productCode":"JW1580","productImg":"樱花恋01.jpg","count":2,"totalPrice":21800,"price":10900,"userId":252699},{"id":129342,"productId":62,"productName":"金薇玲珑款平角裤","productCode":"JW-W1660A","productImg":"LP.jpg","count":16,"totalPrice":56000,"price":3500,"userId":252699},{"id":129354,"productId":5,"productName":"金薇烧花款胸罩","productCode":"JW1520","productImg":"烧花款00.jpg","count":100,"totalPrice":890000,"price":8900,"userId":252699},{"id":129355,"productId":5,"productName":"金薇烧花款胸罩","productCode":"JW1520","productImg":"烧花款00.jpg","count":50,"totalPrice":445000,"price":8900,"userId":252699},{"id":129357,"productId":62,"productName":"金薇玲珑款平角裤","productCode":"JW-W1660A","productImg":"LP.jpg","count":16,"totalPrice":56000,"price":3500,"userId":252699},{"id":129367,"productId":5,"productName":"金薇烧花款胸罩","productCode":"JW1520","productImg":"烧花款00.jpg","count":2,"totalPrice":17800,"price":8900,"userId":252699},{"id":129368,"productId":5,"productName":"金薇烧花款胸罩","productCode":"JW1520","productImg":"烧花款00.jpg","count":4,"totalPrice":35600,"price":8900,"userId":252699},{"id":129369,"productId":5,"productName":"金薇烧花款胸罩","productCode":"JW1520","productImg":"烧花款00.jpg","count":9,"totalPrice":80100,"price":8900,"userId":252699},{"id":129370,"productId":5,"productName":"金薇烧花款胸罩","productCode":"JW1520","productImg":"烧花款00.jpg","count":5,"totalPrice":44500,"price":8900,"userId":252699},{"id":129371,"productId":61,"productName":"金薇玲珑款胸罩","productCode":"JW-W1660","productImg":"玲珑款0.jpg","count":9,"totalPrice":116100,"price":12900,"userId":252699}]
     * errMsg : 成功
     */

    private String errMsg;
    private List<DataBean> data;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 129327
         * productId : 62
         * productName : 金薇玲珑款平角裤
         * productCode : JW-W1660A
         * productImg : LP.jpg
         * count : 14
         * totalPrice : 49000
         * price : 3500
         * userId : 252699
         */

        private int id;
        private int productId;
        private String productName;
        private String productCode;
        private String productImg;
        private int count;
        private int totalPrice;
        private int price;
        private int userId;
        private boolean isCheck;
        private long expiryTime;
        private String currency;

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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        public void setExpiryTime(long expiryTime) {
            this.expiryTime = expiryTime;
        }
    }
}
