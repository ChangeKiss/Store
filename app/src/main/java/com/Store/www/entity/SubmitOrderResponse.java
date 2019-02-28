package com.Store.www.entity;

/**
 * 提交订单的响应体
 */

public class SubmitOrderResponse {

    /**
     * returnValue : 1
     * data : {"orderType":1,"agentId":252699,"orderNumber":"DD20180103216714","address":"浙江省 宁波市 浙江-温州-鹿城区","receiver":"JW","nature":1,"billType":null,"natureName":"云订单","count":1,"typeName":"云订单","agentName":"胡巍-测试用","type":1,"userId":252699,"recieveName":"测试用","total":1,"sendProgress":null,"mobilephone":"18857738393","billStatus":null,"repositoryType":1,"status":3}
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
         * orderType : 1
         * agentId : 252699
         * orderNumber : DD20180103216714
         * address : 浙江省 宁波市 浙江-温州-鹿城区
         * receiver : JW
         * nature : 1
         * billType : null
         * natureName : 云订单
         * count : 1
         * typeName : 云订单
         * agentName : 胡巍-测试用
         * type : 1
         * userId : 252699
         * recieveName : 测试用
         * total : 1
         * sendProgress : null
         * mobilephone : 18857738393
         * billStatus : null
         * repositoryType : 1
         * status : 3
         */

        private int orderType;
        private int agentId;
        private String orderNumber;
        private String address;
        private String receiver;
        private int nature;
        private Object billType;
        private String natureName;
        private int count;
        private String typeName;
        private String agentName;
        private int type;
        private int userId;
        private String recieveName;
        private int total;
        private Object sendProgress;
        private String mobilephone;
        private Object billStatus;
        private int repositoryType;
        private int status;
        private long createTime;

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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
