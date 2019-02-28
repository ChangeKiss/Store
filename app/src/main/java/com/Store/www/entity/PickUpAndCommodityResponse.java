package com.Store.www.entity;

import java.util.List;

/**
 * 获取提货篮以及商品库存的响应体
 */

public class PickUpAndCommodityResponse {

    /**
     * returnValue : 1
     * data : [{"repositoryCount":29,"productId":1,"surplusCount":29,"count":0,"repositoryId":451,"id":29587,"userId":252699,"productName":"金薇呼吸款胸罩","productNo":"JW-W1601"},{"repositoryCount":17,"productId":61,"surplusCount":17,"count":0,"repositoryId":451,"id":29588,"userId":252699,"productName":"金薇玲珑款胸罩","productNo":"JW-W1660"}]
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
         * repositoryCount : 29
         * productId : 1
         * surplusCount : 29
         * count : 0
         * repositoryId : 451
         * id : 29587
         * userId : 252699
         * productName : 金薇呼吸款胸罩
         * productNo : JW-W1601
         */

        private int repositoryCount;
        private int productId;
        private int surplusCount;
        private int count;
        private int repositoryId;
        private int id;
        private int userId;
        private String productName;
        private String productNo;
        private boolean isCheck;

        public int getRepositoryCount() {
            return repositoryCount;
        }

        public void setRepositoryCount(int repositoryCount) {
            this.repositoryCount = repositoryCount;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getSurplusCount() {
            return surplusCount;
        }

        public void setSurplusCount(int surplusCount) {
            this.surplusCount = surplusCount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getRepositoryId() {
            return repositoryId;
        }

        public void setRepositoryId(int repositoryId) {
            this.repositoryId = repositoryId;
        }

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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
}
