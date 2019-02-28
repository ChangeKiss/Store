package com.Store.www.entity;

import java.util.List;

/**
 * 业绩明细响应体
 */

public class ResultsDetailResponse extends BaseBean{

    /**
     * keepOn : 0
     * month : 12
     * data : [{"title":1,"name":"201712"},{"code":"18857738393","gPrice":0.01,"title":0,"productName":"测试商品不要下单","gCount":1,"name":"胡巍-测试用"},{"title":1,"name":"201711"},{"title":1,"name":"201710"},{"code":"18857738393","gPrice":0.05,"title":0,"productName":"测试商品不要下单","gCount":5,"name":"胡巍-测试用"},{"code":"JW1607260168","gPrice":1770,"title":0,"productName":"双11购物节（KK先生裤）","gCount":30,"name":"胡巍-测试用"},{"code":"JW1607260168","gPrice":2180,"title":0,"productName":"双11购物节赠品（kk先生裤）","gCount":20,"name":"胡巍-测试用"},{"code":"JW1607260168","gPrice":8640,"title":0,"productName":"双11购物节赠品（玲珑款）","gCount":30,"name":"胡巍-测试用"},{"title":1,"name":"201709"},{"code":"18857738393","gPrice":9256,"title":0,"productName":"金薇呼吸款胸罩","gCount":104,"name":"胡巍-测试用"},{"code":"18857738393","gPrice":58380,"title":0,"productName":"第二梦15件包","gCount":28,"name":"胡巍-测试用"},{"title":1,"name":"201708"},{"code":"JW20170512800002","gPrice":3069,"title":0,"productName":"金薇简.居衣","gCount":31,"name":"顾莉莉测试"},{"title":1,"name":"201707"},{"title":1,"name":"201706"},{"title":1,"name":"201705"},{"title":1,"name":"201704"},{"title":1,"name":"201703"},{"title":1,"name":"201702"},{"title":1,"name":"201701"},{"title":1,"name":"201612"},{"title":1,"name":"201611"},{"title":1,"name":"201610"},{"title":1,"name":"201609"},{"title":1,"name":"201608"},{"title":1,"name":"201607"},{"title":1,"name":"201606"},{"title":1,"name":"201605"},{"title":1,"name":"201604"},{"title":1,"name":"201603"},{"title":1,"name":"201602"},{"title":1,"name":"201601"},{"title":1,"name":"201512"}]
     * year : 2015
     * errMsg : 成功
     * page : 1
     */

    private int keepOn;
    private String month;
    private String year;
    private String errMsg;
    private int page;
    private List<DataBean> data;

    public int getKeepOn() {
        return keepOn;
    }

    public void setKeepOn(int keepOn) {
        this.keepOn = keepOn;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 1
         * name : 201712
         * code : 18857738393
         * gPrice : 0.01
         * productName : 测试商品不要下单
         * gCount : 1
         */

        private int title;
        private String name;
        private String code;
        private double gPrice;
        private String productName;
        private int gCount;

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getGPrice() {
            return gPrice;
        }

        public void setGPrice(double gPrice) {
            this.gPrice = gPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getGCount() {
            return gCount;
        }

        public void setGCount(int gCount) {
            this.gCount = gCount;
        }
    }
}
