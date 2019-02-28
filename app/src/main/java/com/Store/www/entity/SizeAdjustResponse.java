package com.Store.www.entity;

import java.util.List;

/**
 * 增加尺码单的响应体
 */

public class SizeAdjustResponse {

    /**
     * returnValue : 1
     * data : [{"createTime":1515118033000,"SKUdata":[{"id":11820,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":2,"sku":"JW-W1601H0170A","sizeName":"70A","colorName":"黑色","changeListId":3254,"changeListNo":"TZ201801053254"},{"id":11821,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":-1,"sku":"JW-W1601H0175A","sizeName":"75A","colorName":"黑色","changeListId":3254,"changeListNo":"TZ201801053254"},{"id":11822,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":-1,"sku":"JW-W1601H0175B","sizeName":"75B","colorName":"黑色","changeListId":3254,"changeListNo":"TZ201801053254"}],"id":3254,"repositoryName":"胡巍-测试用的云","changeListNo":"TZ201801053254","productNo":"JW-W1601","productName":"金薇呼吸款胸罩","status":null},{"createTime":1514182502000,"SKUdata":[{"id":11818,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":1,"sku":"JW-W1601Y0170B","sizeName":"70B","colorName":"肤色","changeListId":3253,"changeListNo":"TZ201712253253"},{"id":11819,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":-1,"sku":"JW-W1601X0175A","sizeName":"75A","colorName":"小豹纹","changeListId":3253,"changeListNo":"TZ201712253253"}],"id":3253,"repositoryName":"胡巍-测试用的云","changeListNo":"TZ201712253253","productNo":"JW-W1601","productName":"金薇呼吸款胸罩","status":null},{"createTime":1514182328000,"SKUdata":[{"id":11816,"userId":252699,"repositoryId":451,"productId":61,"productNo":"JW-W1660","count":1,"sku":"JW-W1660G0470B","sizeName":"70B","colorName":"冰绿","changeListId":3252,"changeListNo":"TZ201712253252"},{"id":11817,"userId":252699,"repositoryId":451,"productId":61,"productNo":"JW-W1660","count":-1,"sku":"JW-W1660G0470C","sizeName":"70C","colorName":"冰绿","changeListId":3252,"changeListNo":"TZ201712253252"}],"id":3252,"repositoryName":"胡巍-测试用的云","changeListNo":"TZ201712253252","productNo":"JW-W1660","productName":"金薇玲珑款胸罩","status":null},{"createTime":1504580935000,"SKUdata":[{"id":5049,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":-2,"sku":"JW-W1601H0170A","sizeName":"70A","colorName":"黑色","changeListId":2181,"changeListNo":"TZ201709052181"},{"id":5050,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":2,"sku":"JW-W1601H0180A","sizeName":"80A","colorName":"黑色","changeListId":2181,"changeListNo":"TZ201709052181"}],"id":2181,"repositoryName":"胡巍-测试用的云","changeListNo":"TZ201709052181","productNo":"JW-W1601","productName":"金薇呼吸款胸罩","status":null}]
     * errMsg : 成功
     */

    private int returnValue;
    private String errMsg;
    private List<DataBean> data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

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

    public static class DataBean {
        /**
         * createTime : 1515118033000
         * SKUdata : [{"id":11820,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":2,"sku":"JW-W1601H0170A","sizeName":"70A","colorName":"黑色","changeListId":3254,"changeListNo":"TZ201801053254"},{"id":11821,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":-1,"sku":"JW-W1601H0175A","sizeName":"75A","colorName":"黑色","changeListId":3254,"changeListNo":"TZ201801053254"},{"id":11822,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":-1,"sku":"JW-W1601H0175B","sizeName":"75B","colorName":"黑色","changeListId":3254,"changeListNo":"TZ201801053254"}]
         * id : 3254
         * repositoryName : 胡巍-测试用的云
         * changeListNo : TZ201801053254
         * productNo : JW-W1601
         * productName : 金薇呼吸款胸罩
         * status : null
         */

        private long createTime;
        private int id;
        private String repositoryName;
        private String changeListNo;
        private String productNo;
        private String productName;
        private Object status;
        private List<SKUdataBean> SKUdata;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRepositoryName() {
            return repositoryName;
        }

        public void setRepositoryName(String repositoryName) {
            this.repositoryName = repositoryName;
        }

        public String getChangeListNo() {
            return changeListNo;
        }

        public void setChangeListNo(String changeListNo) {
            this.changeListNo = changeListNo;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public List<SKUdataBean> getSKUdata() {
            return SKUdata;
        }

        public void setSKUdata(List<SKUdataBean> SKUdata) {
            this.SKUdata = SKUdata;
        }

        public static class SKUdataBean {
            /**
             * id : 11820
             * userId : 252699
             * repositoryId : 451
             * productId : 1
             * productNo : JW-W1601
             * count : 2
             * sku : JW-W1601H0170A
             * sizeName : 70A
             * colorName : 黑色
             * changeListId : 3254
             * changeListNo : TZ201801053254
             */

            private int id;
            private int userId;
            private int repositoryId;
            private int productId;
            private String productNo;
            private int count;
            private String sku;
            private String sizeName;
            private String colorName;
            private int changeListId;
            private String changeListNo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getRepositoryId() {
                return repositoryId;
            }

            public void setRepositoryId(int repositoryId) {
                this.repositoryId = repositoryId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductNo() {
                return productNo;
            }

            public void setProductNo(String productNo) {
                this.productNo = productNo;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
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

            public int getChangeListId() {
                return changeListId;
            }

            public void setChangeListId(int changeListId) {
                this.changeListId = changeListId;
            }

            public String getChangeListNo() {
                return changeListNo;
            }

            public void setChangeListNo(String changeListNo) {
                this.changeListNo = changeListNo;
            }
        }
    }
}
