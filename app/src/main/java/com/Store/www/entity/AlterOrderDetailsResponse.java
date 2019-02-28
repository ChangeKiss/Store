package com.Store.www.entity;

import java.util.List;

/**
 * 修改商品信息的响应体
 */

public class AlterOrderDetailsResponse {


    /**
     * unitPrice : 8900
     * returnValue : 1
     * count : 51
     * errMsg : 成功
     * SKUdata : [{"id":856234,"productId":5,"count":6,"createTime":null,"updateTime":null,"sku":"JW1520R0570B","sizeName":"70B","colorName":"中国红","producName":"金薇烧花款胸罩","shoppingId":131866},{"id":856235,"productId":5,"count":6,"createTime":null,"updateTime":null,"sku":"JW1520R0570C","sizeName":"70C","colorName":"中国红","producName":"金薇烧花款胸罩","shoppingId":131866},{"id":856236,"productId":5,"count":6,"createTime":null,"updateTime":null,"sku":"JW1520R0575A","sizeName":"75A","colorName":"中国红","producName":"金薇烧花款胸罩","shoppingId":131866},{"id":856237,"productId":5,"count":7,"createTime":null,"updateTime":null,"sku":"JW1520R0575B","sizeName":"75B","colorName":"中国红","producName":"金薇烧花款胸罩","shoppingId":131866},{"id":856238,"productId":5,"count":7,"createTime":null,"updateTime":null,"sku":"JW1520R0575C","sizeName":"75C","colorName":"中国红","producName":"金薇烧花款胸罩","shoppingId":131866},{"id":856239,"productId":5,"count":9,"createTime":null,"updateTime":null,"sku":"JW1520R0570A","sizeName":"70A","colorName":"中国红","producName":"金薇烧花款胸罩","shoppingId":131866},{"id":856240,"productId":5,"count":10,"createTime":null,"updateTime":null,"sku":"JW1520P0670A","sizeName":"70A","colorName":"富贵粉","producName":"金薇烧花款胸罩","shoppingId":131866}]
     * id : 131866
     */

    private int unitPrice;
    private int returnValue;
    private int count;
    private String errMsg;
    private int id;
    private List<SKUdataBean> SKUdata;

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SKUdataBean> getSKUdata() {
        return SKUdata;
    }

    public void setSKUdata(List<SKUdataBean> SKUdata) {
        this.SKUdata = SKUdata;
    }

    public static class SKUdataBean {
        /**
         * id : 856234
         * productId : 5
         * count : 6
         * createTime : null
         * updateTime : null
         * sku : JW1520R0570B
         * sizeName : 70B
         * colorName : 中国红
         * producName : 金薇烧花款胸罩
         * shoppingId : 131866
         */

        private int id;
        private int productId;
        private int count;
        private Object createTime;
        private Object updateTime;
        private String sku;
        private String sizeName;
        private String colorName;
        private String producName;
        private int shoppingId;
        private int stock;

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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
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

        public String getProducName() {
            return producName;
        }

        public void setProducName(String producName) {
            this.producName = producName;
        }

        public int getShoppingId() {
            return shoppingId;
        }

        public void setShoppingId(int shoppingId) {
            this.shoppingId = shoppingId;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}
