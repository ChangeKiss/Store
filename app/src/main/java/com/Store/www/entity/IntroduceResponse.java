package com.Store.www.entity;

import java.util.List;

/**
 * 商品详情的响应体
 */

public class IntroduceResponse extends BaseBean{


    /**
     * data : {"no":"JW1520","sponge":"","wholeSale":8900,"supplierId":2,"patent":"","year":"2015","typeName":"女式内衣","gNo":"","remark":",JW1520烧花款内衣","purchasePrice":8900,"girdle":"","content":"烧花款内衣","retailSale":21800,"lowPrice":89,"price":8900,"colorGroup":null,"create":"admin","brand":"KIVIE","afterThan":"","supplierName":"东莞高芬制衣有限公司\t","images":[{"id":103,"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/烧花款00.jpg"},{"id":105,"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/烧花款.jpg"}],"productId":5,"salePrice":21800,"torso":"","packagePrice":8900,"costPrice":null,"sideThan":"","buckle":"","regTime":1512461400000,"skuData":[{"colorName":"中国红","sizeName":"70A","sku":"JW1520R0570A"},{"colorName":"中国红","sizeName":"70B","sku":"JW1520R0570B"},{"colorName":"中国红","sizeName":"70C","sku":"JW1520R0570C"},{"colorName":"中国红","sizeName":"75A","sku":"JW1520R0575A"},{"colorName":"中国红","sizeName":"75B","sku":"JW1520R0575B"},{"colorName":"中国红","sizeName":"75C","sku":"JW1520R0575C"},{"colorName":"中国红","sizeName":"80A","sku":"JW1520R0580A"},{"colorName":"中国红","sizeName":"80B","sku":"JW1520R0580B"},{"colorName":"中国红","sizeName":"80C","sku":"JW1520R0580C"},{"colorName":"中国红","sizeName":"85A","sku":"JW1520R0585A"},{"colorName":"中国红","sizeName":"85B","sku":"JW1520R0585B"},{"colorName":"中国红","sizeName":"85C","sku":"JW1520R0585C"},{"colorName":"富贵粉","sizeName":"70A","sku":"JW1520P0670A"},{"colorName":"富贵粉","sizeName":"70B","sku":"JW1520P0670B"},{"colorName":"富贵粉","sizeName":"70C","sku":"JW1520P0670C"},{"colorName":"富贵粉","sizeName":"75A","sku":"JW1520P0675A"},{"colorName":"富贵粉","sizeName":"75B","sku":"JW1520P0675B"},{"colorName":"富贵粉","sizeName":"75C","sku":"JW1520P0675C"},{"colorName":"富贵粉","sizeName":"80A","sku":"JW1520P0680A"},{"colorName":"富贵粉","sizeName":"80B","sku":"JW1520P0680B"},{"colorName":"富贵粉","sizeName":"80C","sku":"JW1520P0680C"},{"colorName":"富贵粉","sizeName":"85A","sku":"JW1520P0685A"},{"colorName":"富贵粉","sizeName":"85B","sku":"JW1520P0685B"},{"colorName":"富贵粉","sizeName":"85C","sku":"JW1520P0685C"}],"reger":null,"createTime":1512461400000,"sizeGroup":null,"fabric":"","lining":"","name":"金薇烧花款胸罩","style":null,"marketTime":null}
     * errMsg : 成功
     */

    private DataBean data;
    private String errMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static class DataBean {
        /**
         * no : JW1520
         * sponge :
         * wholeSale : 8900
         * supplierId : 2
         * patent :
         * year : 2015
         * typeName : 女式内衣
         * gNo :
         * remark : ,JW1520烧花款内衣
         * purchasePrice : 8900
         * girdle :
         * content : 烧花款内衣
         * retailSale : 21800
         * lowPrice : 89
         * price : 8900
         * colorGroup : null
         * create : admin
         * brand : KIVIE
         * afterThan :
         * supplierName : 东莞高芬制衣有限公司
         * images : [{"id":103,"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/烧花款00.jpg"},{"id":105,"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/烧花款.jpg"}]
         * productId : 5
         * salePrice : 21800
         * torso :
         * packagePrice : 8900
         * costPrice : null
         * sideThan :
         * buckle :
         * regTime : 1512461400000
         * skuData : [{"colorName":"中国红","sizeName":"70A","sku":"JW1520R0570A"},{"colorName":"中国红","sizeName":"70B","sku":"JW1520R0570B"},{"colorName":"中国红","sizeName":"70C","sku":"JW1520R0570C"},{"colorName":"中国红","sizeName":"75A","sku":"JW1520R0575A"},{"colorName":"中国红","sizeName":"75B","sku":"JW1520R0575B"},{"colorName":"中国红","sizeName":"75C","sku":"JW1520R0575C"},{"colorName":"中国红","sizeName":"80A","sku":"JW1520R0580A"},{"colorName":"中国红","sizeName":"80B","sku":"JW1520R0580B"},{"colorName":"中国红","sizeName":"80C","sku":"JW1520R0580C"},{"colorName":"中国红","sizeName":"85A","sku":"JW1520R0585A"},{"colorName":"中国红","sizeName":"85B","sku":"JW1520R0585B"},{"colorName":"中国红","sizeName":"85C","sku":"JW1520R0585C"},{"colorName":"富贵粉","sizeName":"70A","sku":"JW1520P0670A"},{"colorName":"富贵粉","sizeName":"70B","sku":"JW1520P0670B"},{"colorName":"富贵粉","sizeName":"70C","sku":"JW1520P0670C"},{"colorName":"富贵粉","sizeName":"75A","sku":"JW1520P0675A"},{"colorName":"富贵粉","sizeName":"75B","sku":"JW1520P0675B"},{"colorName":"富贵粉","sizeName":"75C","sku":"JW1520P0675C"},{"colorName":"富贵粉","sizeName":"80A","sku":"JW1520P0680A"},{"colorName":"富贵粉","sizeName":"80B","sku":"JW1520P0680B"},{"colorName":"富贵粉","sizeName":"80C","sku":"JW1520P0680C"},{"colorName":"富贵粉","sizeName":"85A","sku":"JW1520P0685A"},{"colorName":"富贵粉","sizeName":"85B","sku":"JW1520P0685B"},{"colorName":"富贵粉","sizeName":"85C","sku":"JW1520P0685C"}]
         * reger : null
         * createTime : 1512461400000
         * sizeGroup : null
         * fabric :
         * lining :
         * name : 金薇烧花款胸罩
         * style : null
         * marketTime : null
         */

        private String no;
        private String sponge;
        private int wholeSale;
        private int supplierId;
        private String patent;
        private String year;
        private String typeName;
        private String gNo;
        private String remark;
        private int purchasePrice;
        private String girdle;
        private String content;
        private int retailSale;
        private int lowPrice;
        private int price;
        private Object colorGroup;
        private String create;
        private String brand;
        private String afterThan;
        private String supplierName;
        private int productId;
        private int salePrice;
        private String torso;
        private int packagePrice;
        private Object costPrice;
        private String sideThan;
        private String buckle;
        private long regTime;
        private Object reger;
        private long createTime;
        private Object sizeGroup;
        private String fabric;
        private String lining;
        private String name;
        private Object style;
        private Object marketTime;
        private String currency;
        private List<ImagesBean> images;
        private List<SkuDataBean> skuData;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getSponge() {
            return sponge;
        }

        public void setSponge(String sponge) {
            this.sponge = sponge;
        }

        public int getWholeSale() {
            return wholeSale;
        }

        public void setWholeSale(int wholeSale) {
            this.wholeSale = wholeSale;
        }

        public int getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(int supplierId) {
            this.supplierId = supplierId;
        }

        public String getPatent() {
            return patent;
        }

        public void setPatent(String patent) {
            this.patent = patent;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getGNo() {
            return gNo;
        }

        public void setGNo(String gNo) {
            this.gNo = gNo;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(int purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public String getGirdle() {
            return girdle;
        }

        public void setGirdle(String girdle) {
            this.girdle = girdle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getRetailSale() {
            return retailSale;
        }

        public void setRetailSale(int retailSale) {
            this.retailSale = retailSale;
        }

        public int getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(int lowPrice) {
            this.lowPrice = lowPrice;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public Object getColorGroup() {
            return colorGroup;
        }

        public void setColorGroup(Object colorGroup) {
            this.colorGroup = colorGroup;
        }

        public String getCreate() {
            return create;
        }

        public void setCreate(String create) {
            this.create = create;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getAfterThan() {
            return afterThan;
        }

        public void setAfterThan(String afterThan) {
            this.afterThan = afterThan;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(int salePrice) {
            this.salePrice = salePrice;
        }

        public String getTorso() {
            return torso;
        }

        public void setTorso(String torso) {
            this.torso = torso;
        }

        public int getPackagePrice() {
            return packagePrice;
        }

        public void setPackagePrice(int packagePrice) {
            this.packagePrice = packagePrice;
        }

        public Object getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(Object costPrice) {
            this.costPrice = costPrice;
        }

        public String getSideThan() {
            return sideThan;
        }

        public void setSideThan(String sideThan) {
            this.sideThan = sideThan;
        }

        public String getBuckle() {
            return buckle;
        }

        public void setBuckle(String buckle) {
            this.buckle = buckle;
        }

        public long getRegTime() {
            return regTime;
        }

        public void setRegTime(long regTime) {
            this.regTime = regTime;
        }

        public Object getReger() {
            return reger;
        }

        public void setReger(Object reger) {
            this.reger = reger;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getSizeGroup() {
            return sizeGroup;
        }

        public void setSizeGroup(Object sizeGroup) {
            this.sizeGroup = sizeGroup;
        }

        public String getFabric() {
            return fabric;
        }

        public void setFabric(String fabric) {
            this.fabric = fabric;
        }

        public String getLining() {
            return lining;
        }

        public void setLining(String lining) {
            this.lining = lining;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Object getStyle() {
            return style;
        }

        public void setStyle(Object style) {
            this.style = style;
        }

        public Object getMarketTime() {
            return marketTime;
        }

        public void setMarketTime(Object marketTime) {
            this.marketTime = marketTime;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public List<SkuDataBean> getSkuData() {
            return skuData;
        }

        public void setSkuData(List<SkuDataBean> skuData) {
            this.skuData = skuData;
        }

        public static class ImagesBean {
            /**
             * id : 103
             * url : http://jwbucket.oss-cn-shanghai.aliyuncs.com/烧花款00.jpg
             */

            private int id;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class SkuDataBean {
            /**
             * colorName : 中国红
             * sizeName : 70A
             * sku : JW1520R0570A
             */

            private String colorName;
            private String sizeName;
            private String sku;
            private int stock;

            public String getColorName() {
                return colorName;
            }

            public void setColorName(String colorName) {
                this.colorName = colorName;
            }

            public String getSizeName() {
                return sizeName;
            }

            public void setSizeName(String sizeName) {
                this.sizeName = sizeName;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }
        }
    }
}
