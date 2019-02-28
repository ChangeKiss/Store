package com.Store.www.entity;

import java.util.List;

/**
 * 新建提货篮的响应体
 */

public class NewBillResponse {

    /**
     * returnValue : 1
     * data : [{"id":29776,"userId":252699,"repositoryId":451,"productId":1,"productNo":"JW-W1601","count":2,"productName":"金薇呼吸款胸罩"}]
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
         * id : 29776
         * userId : 252699
         * repositoryId : 451
         * productId : 1
         * productNo : JW-W1601
         * count : 2
         * productName : 金薇呼吸款胸罩
         */

        private int id;
        private int userId;
        private int repositoryId;
        private int productId;
        private String productNo;
        private int count;
        private String productName;

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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }
}
