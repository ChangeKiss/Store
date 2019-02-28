package com.Store.www.entity;

import java.util.List;

/**
 * 提货单的响应体
 */

public class PickUpGoodsResponse extends BaseBean{

    /**
     * data : [{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812248328","receiver":"郑盛测试","typeName":"提货","count":40,"type":1,"expressTotal":1000000,"userId":822125,"productImage":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/玲珑款0.jpg","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":1,"status":0},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218314","receiver":"郑盛测试","typeName":"提货","count":20,"type":1,"expressTotal":1,"userId":822125,"productImage":"","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":0,"status":99},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218313","receiver":"郑盛测试","typeName":"提货","count":20,"type":1,"expressTotal":1,"userId":822125,"productImage":"","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":0,"status":0},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218312","receiver":"郑盛测试","typeName":"提货","count":20,"type":1,"expressTotal":1000000,"userId":822125,"productImage":"","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":1,"status":0},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218311","receiver":"郑盛测试","typeName":"提货","count":20,"type":1,"expressTotal":1000000,"userId":822125,"productImage":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/微信图片_20171230170749.jpg","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":1,"status":99},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218310","receiver":"郑盛测试","typeName":"提货","count":20,"type":1,"expressTotal":1000000,"userId":822125,"productImage":"","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":1,"status":0},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218309","receiver":"郑盛测试","typeName":"提货","count":20,"type":1,"expressTotal":1000000,"userId":822125,"productImage":"","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":1,"status":99},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218308","receiver":"郑盛测试","typeName":"提货","count":920,"type":1,"expressTotal":1000000,"userId":822125,"productImage":"","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":1,"status":99},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218307","receiver":"郑盛测试","typeName":"提货","count":50,"type":1,"expressTotal":1000000,"userId":822125,"productImage":"","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":1,"status":99},{"agentId":822125,"address":"台湾省 高雄市  零零落落","orderNumber":"TH201812218306","receiver":"郑盛测试","typeName":"提货","count":20,"type":1,"expressTotal":1000000,"userId":822125,"productImage":"","receiveName":"郑盛测试","mobilephone":"134567","payExpressStats":1,"status":99}]
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
         * agentId : 822125
         * address : 台湾省 高雄市  零零落落
         * orderNumber : TH201812248328
         * receiver : 郑盛测试
         * typeName : 提货
         * count : 40
         * type : 1
         * expressTotal : 1000000
         * userId : 822125
         * productImage : http://jwbucket.oss-cn-shanghai.aliyuncs.com/玲珑款0.jpg
         * receiveName : 郑盛测试
         * mobilephone : 134567
         * payExpressStats : 1
         * status : 0
         */

        private int agentId;
        private String address;
        private String orderNumber;
        private String receiver;
        private String typeName;
        private int count;
        private int type;
        private int expressTotal;
        private int userId;
        private String productImage;
        private String receiveName;
        private String mobilephone;
        private int payExpressStats;
        private int status;

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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
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

        public int getExpressTotal() {
            return expressTotal;
        }

        public void setExpressTotal(int expressTotal) {
            this.expressTotal = expressTotal;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public int getPayExpressStats() {
            return payExpressStats;
        }

        public void setPayExpressStats(int payExpressStats) {
            this.payExpressStats = payExpressStats;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
