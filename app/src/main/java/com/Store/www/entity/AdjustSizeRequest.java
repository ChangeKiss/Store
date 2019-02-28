package com.Store.www.entity;

import java.util.List;

/**
 * 调整尺码的请求体
 */

public class AdjustSizeRequest {

    /**
     * repositoryId : 451
     * userId : 252699
     * productId : 1
     * count : 0
     * SKUdata : [{"Name":"黑色","size":"70A","sku":"JW-W1601H0170A","repositoryCount":"0","count":0},{"Name":"黑色","size":"70B","sku":"JW-W1601H0170B","repositoryCount":"1","count":-1},{"Name":"黑色","size":"75A","sku":"JW-W1601H0175A","repositoryCount":"1","count":-1},{"Name":"黑色","size":"75B","sku":"JW-W1601H0175B","repositoryCount":"1","count":9},{"Name":"黑色","size":"75C","sku":"JW-W1601H0175C","repositoryCount":"2","count":0},{"Name":"黑色","size":"80A","sku":"JW-W1601H0180A","repositoryCount":"3","count":-3},{"Name":"黑色","size":"80B","sku":"JW-W1601H0180B","repositoryCount":"2","count":-2},{"Name":"黑色","size":"80C","sku":"JW-W1601H0180C","repositoryCount":"1","count":-1},{"Name":"黑色","size":"85A","sku":"JW-W1601H0185A","repositoryCount":"1","count":-1},{"Name":"黑色","size":"85B","sku":"JW-W1601H0185B","repositoryCount":"0","count":0},{"Name":"肤色","size":"70A","sku":"JW-W1601Y0170A","repositoryCount":"1","count":0},{"Name":"肤色","size":"70B","sku":"JW-W1601Y0170B","repositoryCount":"1","count":0},{"Name":"肤色","size":"75A","sku":"JW-W1601Y0175A","repositoryCount":"1","count":0},{"Name":"肤色","size":"75B","sku":"JW-W1601Y0175B","repositoryCount":"1","count":0},{"Name":"肤色","size":"75C","sku":"JW-W1601Y0175C","repositoryCount":"1","count":0},{"Name":"肤色","size":"80A","sku":"JW-W1601Y0180A","repositoryCount":"1","count":0},{"Name":"肤色","size":"80B","sku":"JW-W1601Y0180B","repositoryCount":"0","count":0},{"Name":"肤色","size":"80C","sku":"JW-W1601Y0180C","repositoryCount":"1","count":0},{"Name":"肤色","size":"85A","sku":"JW-W1601Y0185A","repositoryCount":"0","count":0},{"Name":"肤色","size":"85B","sku":"JW-W1601Y0185B","repositoryCount":"0","count":0},{"Name":"小豹纹","size":"70A","sku":"JW-W1601X0170A","repositoryCount":"0","count":0},{"Name":"小豹纹","size":"70B","sku":"JW-W1601X0170B","repositoryCount":"1","count":0},{"Name":"小豹纹","size":"75A","sku":"JW-W1601X0175A","repositoryCount":"2","count":0},{"Name":"小豹纹","size":"75B","sku":"JW-W1601X0175B","repositoryCount":"1","count":0},{"Name":"小豹纹","size":"75C","sku":"JW-W1601X0175C","repositoryCount":"0","count":0},{"Name":"小豹纹","size":"80A","sku":"JW-W1601X0180A","repositoryCount":"0","count":0},{"Name":"小豹纹","size":"80B","sku":"JW-W1601X0180B","repositoryCount":"0","count":0},{"Name":"小豹纹","size":"80C","sku":"JW-W1601X0180C","repositoryCount":"2","count":0},{"Name":"小豹纹","size":"85A","sku":"JW-W1601X0185A","repositoryCount":"1","count":0},{"Name":"小豹纹","size":"85B","sku":"JW-W1601X0185B","repositoryCount":"0","count":0}]
     */

    private int repositoryId;
    private String userId;
    private int productId;
    private int count;
    private List<SKUdataBean> SKUdata;

    public AdjustSizeRequest(int repositoryId, String userId, int productId, int count, List<SKUdataBean> SKUdata) {
        this.repositoryId = repositoryId;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
        this.SKUdata = SKUdata;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<SKUdataBean> getSKUdata() {
        return SKUdata;
    }

    public void setSKUdata(List<SKUdataBean> SKUdata) {
        this.SKUdata = SKUdata;
    }

    public static class SKUdataBean {
        /**
         * Name : 黑色
         * size : 70A
         * sku : JW-W1601H0170A
         * repositoryCount : 0
         * count : 0
         */

        private String Name;
        private String size;
        private String sku;
        private String repositoryCount;
        private int count;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getRepositoryCount() {
            return repositoryCount;
        }

        public void setRepositoryCount(String repositoryCount) {
            this.repositoryCount = repositoryCount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
