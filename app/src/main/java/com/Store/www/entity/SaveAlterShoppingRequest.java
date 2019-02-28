package com.Store.www.entity;

import java.util.List;

/**
 * 保存修改购物车请求体
 */

public class SaveAlterShoppingRequest {


    /**
     * id : 131866
     * productId : 96
     * userId : 252699
     * count : 343
     * SKUdata : [{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW-BC02002H01S","sizeName":"S","colorName":"黑色","fontcolor":0,"count":11,"productId":96,"id":856334,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW-BC02002H01M","sizeName":"M","colorName":"黑色","fontcolor":0,"count":6,"productId":96,"id":856335,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570B","sizeName":"70B","colorName":"中国红","fontcolor":1,"count":6,"productId":96,"id":856336,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570C","sizeName":"70C","colorName":"中国红","fontcolor":1,"count":6,"productId":96,"id":856337,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575A","sizeName":"75A","colorName":"中国红","fontcolor":1,"count":8,"productId":96,"id":856338,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575B","sizeName":"75B","colorName":"中国红","fontcolor":1,"count":7,"productId":96,"id":856339,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575C","sizeName":"75C","colorName":"中国红","fontcolor":1,"count":7,"productId":96,"id":856340,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570A","sizeName":"70A","colorName":"中国红","fontcolor":1,"count":9,"productId":96,"id":856341,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520P0670A","sizeName":"70A","colorName":"富贵粉","fontcolor":2,"count":12,"productId":96,"id":856342,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570B","sizeName":"70B","colorName":"中国红","fontcolor":3,"count":6,"productId":96,"id":856343,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570C","sizeName":"70C","colorName":"中国红","fontcolor":3,"count":6,"productId":96,"id":856344,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575A","sizeName":"75A","colorName":"中国红","fontcolor":3,"count":8,"productId":96,"id":856345,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575B","sizeName":"75B","colorName":"中国红","fontcolor":3,"count":7,"productId":96,"id":856346,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575C","sizeName":"75C","colorName":"中国红","fontcolor":3,"count":7,"productId":96,"id":856347,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570A","sizeName":"70A","colorName":"中国红","fontcolor":3,"count":9,"productId":96,"id":856348,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520P0670A","sizeName":"70A","colorName":"富贵粉","fontcolor":4,"count":12,"productId":96,"id":856349,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570B","sizeName":"70B","colorName":"中国红","fontcolor":5,"count":6,"productId":96,"id":856350,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570C","sizeName":"70C","colorName":"中国红","fontcolor":5,"count":6,"productId":96,"id":856351,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575A","sizeName":"75A","colorName":"中国红","fontcolor":5,"count":8,"productId":96,"id":856352,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575B","sizeName":"75B","colorName":"中国红","fontcolor":5,"count":7,"productId":96,"id":856353,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575C","sizeName":"75C","colorName":"中国红","fontcolor":5,"count":7,"productId":96,"id":856354,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570A","sizeName":"70A","colorName":"中国红","fontcolor":5,"count":9,"productId":96,"id":856355,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520P0670A","sizeName":"70A","colorName":"富贵粉","fontcolor":6,"count":12,"productId":96,"id":856356,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570B","sizeName":"70B","colorName":"中国红","fontcolor":7,"count":6,"productId":96,"id":856357,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570C","sizeName":"70C","colorName":"中国红","fontcolor":7,"count":6,"productId":96,"id":856358,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575A","sizeName":"75A","colorName":"中国红","fontcolor":7,"count":8,"productId":96,"id":856359,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575B","sizeName":"75B","colorName":"中国红","fontcolor":7,"count":7,"productId":96,"id":856360,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575C","sizeName":"75C","colorName":"中国红","fontcolor":7,"count":7,"productId":96,"id":856361,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570A","sizeName":"70A","colorName":"中国红","fontcolor":7,"count":9,"productId":96,"id":856362,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520P0670A","sizeName":"70A","colorName":"富贵粉","fontcolor":8,"count":11,"productId":96,"id":856363,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570B","sizeName":"70B","colorName":"中国红","fontcolor":9,"count":6,"productId":96,"id":856364,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570C","sizeName":"70C","colorName":"中国红","fontcolor":9,"count":6,"productId":96,"id":856365,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575A","sizeName":"75A","colorName":"中国红","fontcolor":9,"count":8,"productId":96,"id":856366,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575B","sizeName":"75B","colorName":"中国红","fontcolor":9,"count":7,"productId":96,"id":856367,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575C","sizeName":"75C","colorName":"中国红","fontcolor":9,"count":7,"productId":96,"id":856368,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570A","sizeName":"70A","colorName":"中国红","fontcolor":9,"count":9,"productId":96,"id":856369,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520P0670A","sizeName":"70A","colorName":"富贵粉","fontcolor":10,"count":11,"productId":96,"id":856370,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570B","sizeName":"70B","colorName":"中国红","fontcolor":11,"count":6,"productId":96,"id":856371,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570C","sizeName":"70C","colorName":"中国红","fontcolor":11,"count":6,"productId":96,"id":856372,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575A","sizeName":"75A","colorName":"中国红","fontcolor":11,"count":8,"productId":96,"id":856373,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575B","sizeName":"75B","colorName":"中国红","fontcolor":11,"count":7,"productId":96,"id":856374,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0575C","sizeName":"75C","colorName":"中国红","fontcolor":11,"count":7,"productId":96,"id":856375,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520R0570A","sizeName":"70A","colorName":"中国红","fontcolor":11,"count":9,"productId":96,"id":856376,"shoppingId":131866},{"producName":"金薇嗨裤*美体款（嗨美）","sku":"JW1520P0670A","sizeName":"70A","colorName":"富贵粉","fontcolor":12,"count":10,"productId":96,"id":856377,"shoppingId":131866}]
     * totalPrice : 3052700
     * unitPrice : 8900
     */

    private int id;
    private int productId;
    private String userId;
    private int count;
    private int totalPrice;
    private int unitPrice;
    private List<SKUdataBean> SKUdata;

    public SaveAlterShoppingRequest(int id, int productId, String userId, int count, int totalPrice, int unitPrice, List<SKUdataBean> SKUdata) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
         * producName : 金薇嗨裤*美体款（嗨美）
         * sku : JW-BC02002H01S
         * sizeName : S
         * colorName : 黑色
         * fontcolor : 0
         * count : 11
         * productId : 96
         * id : 856334
         * shoppingId : 131866
         */

        private String producName;
        private String sku;
        private String sizeName;
        private String colorName;
        private int fontcolor;
        private int count;
        private int productId;
        private int id;
        private int shoppingId;

        public String getProducName() {
            return producName;
        }

        public void setProducName(String producName) {
            this.producName = producName;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getSizeName() {
            return sizeName;
        }

        public void setSizeName(String sizeName) {
            this.sizeName = sizeName;
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

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShoppingId() {
            return shoppingId;
        }

        public void setShoppingId(int shoppingId) {
            this.shoppingId = shoppingId;
        }
    }
}
