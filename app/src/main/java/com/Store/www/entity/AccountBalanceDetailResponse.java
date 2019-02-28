package com.Store.www.entity;

/**
 * 账户余额明细响应体
 */

public class AccountBalanceDetailResponse {

    /**
     * returnValue : 1
     * data : {"orderNo":"0","money":10000,"createTime":"2018-04-26 09:52:42","cardNumber":"中国银行尾号 5868","status":0}
     * errMsg : null
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
         * orderNo : 0
         * money : 10000
         * createTime : 2018-04-26 09:52:42
         * cardNumber : 中国银行尾号 5868
         * status : 0
         */

        private String orderNo;
        private int money;
        private String createTime;
        private String cardNumber;
        private int status;
        private String detail;
        private String failReason;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getFailReason() {
            return failReason;
        }

        public void setFailReason(String failReason) {
            this.failReason = failReason;
        }
    }
}
