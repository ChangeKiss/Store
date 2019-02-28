package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 提交提货单响应体
 */
public class SubmitPickUpGoodsResponse {

    /**
     * returnValue : 1
     * data : {"agentId":822125,"orderNumber":"TH201812248328","address":"零零落落","receiver":"郑盛测试","payExpressTotal":1000000,"count":40,"typeName":"提货","isPayExpress":1,"agentName":"郑盛测试","type":1,"userId":822125,"recieveName":"郑盛测试","mobilephone":"134567","status":0}
     * errMsg : 成功
     */

    private int returnValue;
    private DataBean data;
    private String errMsg;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

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
         * agentId : 822125
         * orderNumber : TH201812248328
         * address : 零零落落
         * receiver : 郑盛测试
         * payExpressTotal : 1000000
         * count : 40
         * typeName : 提货
         * isPayExpress : 1
         * agentName : 郑盛测试
         * type : 1
         * userId : 822125
         * recieveName : 郑盛测试
         * mobilephone : 134567
         * status : 0
         */

        private int agentId;
        private String orderNumber;
        private String address;
        private String receiver;
        private int payExpressTotal;
        private int count;
        private String typeName;
        private int isPayExpress;
        private String agentName;
        private int type;
        private int userId;
        private String recieveName;
        private String mobilephone;
        private int status;

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public int getPayExpressTotal() {
            return payExpressTotal;
        }

        public void setPayExpressTotal(int payExpressTotal) {
            this.payExpressTotal = payExpressTotal;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getIsPayExpress() {
            return isPayExpress;
        }

        public void setIsPayExpress(int isPayExpress) {
            this.isPayExpress = isPayExpress;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getRecieveName() {
            return recieveName;
        }

        public void setRecieveName(String recieveName) {
            this.recieveName = recieveName;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
