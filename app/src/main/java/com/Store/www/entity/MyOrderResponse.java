package com.Store.www.entity;

import java.util.List;

/**
 * 我的订单响应体
 */

public class MyOrderResponse extends BaseBean{

    /**
     * data : [{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20171215216828","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":1,"type":1,"userId":252699,"total":1,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":null,"status":4},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20171028209534","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":20,"type":1,"userId":252699,"total":218000,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20171028209534_payInfo0B635D30-7330-414C-8151-47CC76AEF6F9.gif","status":1},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20171028209533","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":30,"type":1,"userId":252699,"total":864000,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20171028209533_payInfo4A540CB7-C904-4F12-AE99-79842B4926C5.gif","status":1},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20170905200675","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":54,"type":1,"userId":252699,"total":480600,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20170905200675_payInfojava.util.Random@552af915.jpg","status":2},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20170623192897","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":83,"type":1,"userId":252699,"total":1070700,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20170623192897_payInfojava.util.Random@65ea6330.jpg","status":2},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20170621192801","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":50,"type":1,"userId":252699,"total":445000,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20170621192801_payInfojava.util.Random@c074a5.jpg","status":2},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20170527187360","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":30,"type":1,"userId":252699,"total":237000,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20170527187360_payInfojava.util.Random@242eb73a.jpg","status":2},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20170527187357","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":30,"type":1,"userId":252699,"total":237000,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20170527187357_payInfojava.util.Random@55b0dac2.jpg","status":2},{"orderType":1,"agentId":252699,"address":"北京市 北京市市辖区 外地号急急急嗯ill","orderNumber":"DD20170527187355","receiver":"JW","nature":1,"billType":null,"typeName":"云订单","natureName":"云订单","count":60,"type":1,"userId":252699,"total":534000,"receiveName":"胡二","sendProgress":null,"mobilephone":"18857722222","billStatus":null,"repositoryType":1,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20170527187355_payInfojava.util.Random@1f1856d4.jpg","status":2}]
     * errMsg : 成功
     */

    private String errMsg;
    private List<DataBean> data;

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
         * agentId : 252699
         * address : 北京市 北京市市辖区 外地号急急急嗯ill
         * orderNumber : DD20171215216828
         * receiver : JW
         * nature : 1
         * billType : null
         * typeName : 云订单
         * natureName : 云订单
         * count : 1
         * type : 1
         * userId : 252699
         * total : 1
         * receiveName : 胡二
         * sendProgress : null
         * mobilephone : 18857722222
         * billStatus : null
         * repositoryType : 1
         * payInfo : null
         * status : 4
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
        private String currency;
        private long expiryTime;
        private String productImage;
        private long createTime;
        private int isUseBalance;
        private int useBalance;

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

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        public void setExpiryTime(long expiryTime) {
            this.expiryTime = expiryTime;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getIsUseBalance() {
            return isUseBalance;
        }

        public void setIsUseBalance(int isUseBalance) {
            this.isUseBalance = isUseBalance;
        }

        public int getUseBalance() {
            return useBalance;
        }

        public void setUseBalance(int useBalance) {
            this.useBalance = useBalance;
        }
    }
}
