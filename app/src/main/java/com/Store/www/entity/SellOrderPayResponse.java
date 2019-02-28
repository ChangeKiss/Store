package com.Store.www.entity;

import java.util.List;

/**
 * 销售订单支付确认响应体
 */

public class SellOrderPayResponse {

    /**
     * orderType : 1
     * agentId : 252699
     * address : 北京市 北京市市辖区 天安门广场
     * orderNumber : DD20171225217460
     * returnValue : 1
     * receiver : 18857738393
     * nature : 1
     * billType : null
     * typeName : 云订单
     * natureName : 云订单
     * count : 3
     * errMsg : 成功
     * type : 1
     * userId : 252699
     * total : 3
     * receiveName : 胡2
     * sendProgress : null
     * mobilephone : 18857738393
     * billStatus : null
     * repositoryType : 0
     * payInfoList : [{"createTime":1514176813000,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":23667,"orderNo":"DD20171225217460","orderId":217460,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20171225217460_payInfoda_1512732129420.jpg","payTime":1514176813000,"no":"PAY-DD20171225217460-23667","money":3,"status":0,"payType":0}]
     * payInfo : http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20171225217460_payInfoda_1512732129420.jpg
     * status : 4
     */

    private int orderType;
    private int agentId;
    private String address;
    private String orderNumber;
    private int returnValue;
    private String receiver;
    private int nature;
    private Object billType;
    private String typeName;
    private String natureName;
    private int count;
    private String errMsg;
    private int type;
    private int userId;
    private int total;
    private String receiveName;
    private Object sendProgress;
    private String mobilephone;
    private Object billStatus;
    private int repositoryType;
    private String payInfo;
    private int status;
    private long createTime;
    private List<PayInfoListBean> payInfoList;

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

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
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

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
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

    public List<PayInfoListBean> getPayInfoList() {
        return payInfoList;
    }

    public void setPayInfoList(List<PayInfoListBean> payInfoList) {
        this.payInfoList = payInfoList;
    }

    public static class PayInfoListBean {
        /**
         * createTime : 1514176813000
         * updateTime : null
         * creater : null
         * updater : null
         * isDelete : null
         * id : 23667
         * orderNo : DD20171225217460
         * orderId : 217460
         * payInfo : http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20171225217460_payInfoda_1512732129420.jpg
         * payTime : 1514176813000
         * no : PAY-DD20171225217460-23667
         * money : 3
         * status : 0
         * payType : 0
         */

        private long createTime;
        private Object updateTime;
        private Object creater;
        private Object updater;
        private Object isDelete;
        private int id;
        private String orderNo;
        private int orderId;
        private String payInfo;
        private long payTime;
        private String no;
        private int money;
        private int status;
        private int payType;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getCreater() {
            return creater;
        }

        public void setCreater(Object creater) {
            this.creater = creater;
        }

        public Object getUpdater() {
            return updater;
        }

        public void setUpdater(Object updater) {
            this.updater = updater;
        }

        public Object getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(Object isDelete) {
            this.isDelete = isDelete;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(String payInfo) {
            this.payInfo = payInfo;
        }

        public long getPayTime() {
            return payTime;
        }

        public void setPayTime(long payTime) {
            this.payTime = payTime;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }
    }
}
