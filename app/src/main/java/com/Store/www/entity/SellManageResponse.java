package com.Store.www.entity;

import java.util.List;

/**
 * 销售管理响应体
 */

public class SellManageResponse {

    /**
     * returnValue : 1
     * data : [{"orderType":1,"agentId":820350,"address":"北京市 北京市市辖区 天安门","orderNumber":"DD20180113218491","receiver":"18857738393","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":30,"type":1,"userId":820350,"total":654000,"receiveName":"郑盛测试","sendProgress":null,"mobilephone":"13500000000","billStatus":null,"repositoryType":0,"payInfo":null,"status":100},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 天安门广场","orderNumber":"DD20171225217460","receiver":"18857738393","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":3,"type":1,"userId":252699,"total":3,"receiveName":"胡2","sendProgress":null,"mobilephone":"18857738393","billStatus":null,"repositoryType":0,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20171225217460_payInfoda_1512732129420.jpg","status":4},{"orderType":2,"agentId":804626,"address":"内蒙古自治区 包头市 街道","orderNumber":"DD20170705193329","receiver":"18857738393","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":1,"type":1,"userId":804626,"total":19900,"receiveName":"测试","sendProgress":null,"mobilephone":"18767676767","billStatus":null,"repositoryType":0,"payInfo":null,"status":100},{"orderType":2,"agentId":804626,"address":"内蒙古自治区 包头市 街道","orderNumber":"DD20170705193327","receiver":"18857738393","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":3,"type":1,"userId":804626,"total":80700,"receiveName":"测试","sendProgress":null,"mobilephone":"18767676767","billStatus":null,"repositoryType":0,"payInfo":null,"status":100},{"orderType":2,"agentId":804626,"address":"内蒙古自治区 包头市 街道","orderNumber":"DD20170705193326","receiver":"18857738393","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":3,"type":1,"userId":804626,"total":80700,"receiveName":"测试","sendProgress":null,"mobilephone":"18767676767","billStatus":null,"repositoryType":0,"payInfo":null,"status":100}]
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
         * orderType : 1
         * agentId : 820350
         * address : 北京市 北京市市辖区 天安门
         * orderNumber : DD20180113218491
         * receiver : 18857738393
         * nature : 1
         * billType : null
         * typeName : 云订单
         * natureName : 云订单
         * count : 30
         * type : 1
         * userId : 820350
         * total : 654000
         * receiveName : 郑盛测试
         * sendProgress : null
         * mobilephone : 13500000000
         * billStatus : null
         * repositoryType : 0
         * payInfo : null
         * status : 100
         * isIShow : 1 塑身衣订单 0 不是塑身衣订单
         */

        private int orderType;
        private int agentId;
        private String address;
        private String orderNumber;
        private String receiver;
        private int nature;
        private Object billType;
        private String typeName;
        private String natureName;
        private int count;
        private int type;
        private int userId;
        private int total;
        private String receiveName;
        private Object sendProgress;
        private String mobilephone;
        private Object billStatus;
        private int repositoryType;
        private Object payInfo;
        private int status;
        private long expiryTime;
        private String currency;
        private String productImage;
        private int isIShow;

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public int getNature() {
            return nature;
        }

        public void setNature(int nature) {
            this.nature = nature;
        }

        public Object getBillType() {
            return billType;
        }

        public void setBillType(Object billType) {
            this.billType = billType;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getNatureName() {
            return natureName;
        }

        public void setNatureName(String natureName) {
            this.natureName = natureName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public Object getSendProgress() {
            return sendProgress;
        }

        public void setSendProgress(Object sendProgress) {
            this.sendProgress = sendProgress;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public Object getBillStatus() {
            return billStatus;
        }

        public void setBillStatus(Object billStatus) {
            this.billStatus = billStatus;
        }

        public int getRepositoryType() {
            return repositoryType;
        }

        public void setRepositoryType(int repositoryType) {
            this.repositoryType = repositoryType;
        }

        public Object getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(Object payInfo) {
            this.payInfo = payInfo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        public void setExpiryTime(long expiryTime) {
            this.expiryTime = expiryTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public int getIsIShow() {
            return isIShow;
        }

        public void setIsIShow(int isIShow) {
            this.isIShow = isIShow;
        }
    }
}
