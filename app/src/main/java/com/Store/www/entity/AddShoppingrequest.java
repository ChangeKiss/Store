package com.Store.www.entity;

import java.util.List;

/**
 * 加入购物车请求体
 */

public class AddShoppingrequest {


    /**
     * id : 0
     * productId : 5
     * userId : 252699
     * count : 7
     * SKUdata : [{"Name":"中国红70A","sku":"JW1520R0570A","colorName":"中国红","fontcolor":0,"count":0},{"Name":"中国红70B","sku":"JW1520R0570B","colorName":"中国红","fontcolor":0,"count":0},{"Name":"中国红70C","sku":"JW1520R0570C","colorName":"中国红","fontcolor":0,"count":1},{"Name":"中国红75A","sku":"JW1520R0575A","colorName":"中国红","fontcolor":0,"count":1},{"Name":"中国红75B","sku":"JW1520R0575B","colorName":"中国红","fontcolor":0,"count":1},{"Name":"中国红75C","sku":"JW1520R0575C","colorName":"中国红","fontcolor":0,"count":1},{"Name":"中国红80A","sku":"JW1520R0580A","colorName":"中国红","fontcolor":0,"count":2},{"Name":"中国红80B","sku":"JW1520R0580B","colorName":"中国红","fontcolor":0,"count":1},{"Name":"中国红80C","sku":"JW1520R0580C","colorName":"中国红","fontcolor":0,"count":0},{"Name":"中国红85A","sku":"JW1520R0585A","colorName":"中国红","fontcolor":0,"count":0},{"Name":"中国红85B","sku":"JW1520R0585B","colorName":"中国红","fontcolor":0,"count":0},{"Name":"中国红85C","sku":"JW1520R0585C","colorName":"中国红","fontcolor":0,"count":0},{"Name":"富贵粉70A","sku":"JW1520P0670A","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉70B","sku":"JW1520P0670B","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉70C","sku":"JW1520P0670C","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉75A","sku":"JW1520P0675A","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉75B","sku":"JW1520P0675B","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉75C","sku":"JW1520P0675C","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉80A","sku":"JW1520P0680A","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉80B","sku":"JW1520P0680B","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉80C","sku":"JW1520P0680C","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉85A","sku":"JW1520P0685A","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉85B","sku":"JW1520P0685B","colorName":"富贵粉","fontcolor":1,"count":0},{"Name":"富贵粉85C","sku":"JW1520P0685C","colorName":"富贵粉","fontcolor":1,"count":0}]
     * totalPrice : 62300
     * unitPrice : 8900
     */

    private int id;
    private int productId;
    private int userId;
    private int count;
    private int totalPrice;
    private int unitPrice;
    private List<SKUdataBean> SKUdata;

    public AddShoppingrequest(int id, int productId, int userId, int count, int totalPrice, int unitPrice, List<SKUdataBean> SKUdata) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.count = count;
        this.totalPrice = totalPrice;
        this.unitPrice = unitPrice;
        this.SKUdata = SKUdata;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<SKUdataBean> getSKUdata() {
        return SKUdata;
    }

    public void setSKUdata(List<SKUdataBean> SKUdata) {
        this.SKUdata = SKUdata;
    }

    public static class SKUdataBean {
        /**
         * Name : 中国红70A
         * sku : JW1520R0570A
         * colorName : 中国红
         * fontcolor : 0
         * count : 0
         */

        private String Name;
        private String sku;
        private String colorName;
        private int fontcolor;
        private int count;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getColorName() {
            return colorName;
        }

        public void setColorName(String colorName) {
            this.colorName = colorName;
        }

        public int getFontcolor() {
            return fontcolor;
        }

        public void setFontcolor(int fontcolor) {
            this.fontcolor = fontcolor;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
