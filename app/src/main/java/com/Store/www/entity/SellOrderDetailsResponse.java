package com.Store.www.entity;

import java.util.List;

/**
 * 销售订单详情响应体
 */

public class SellOrderDetailsResponse {

    /**
     * orderType : 1
     * agent : {"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_1517554057332_headImage.jpg","agentId":822125,"code":"K20180201822125","mobilephone":"13588032842","name":"郑盛测试1"}
     * agentId : 822125
     * orderNumber : DD20180316221580
     * returnValue : 1
     * typeName : 云订单
     * errMsg : 成功
     * type : 1
     * total : 1
     * receiveName : 111
     * sendProgress : null
     * mobilephone : 13500000000
     * payInfoList : [{"createTime":1521170723000,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":26560,"orderNo":"DD20180316221580","orderId":221580,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_DD20180316221580_1521170722825_payInfoimage_2.jpg","payTime":1521170723000,"no":"PAY-DD20180316221580-26560","money":1,"status":0,"payType":1},{"createTime":1521170723000,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":26561,"orderNo":"DD20180316221580","orderId":221580,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_DD20180316221580_1521170722779_payInfoimage_0.jpg","payTime":1521170723000,"no":"PAY-DD20180316221580-26561","money":1,"status":0,"payType":1},{"createTime":1521170723000,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":26562,"orderNo":"DD20180316221580","orderId":221580,"payInfo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_DD20180316221580_1521170722809_payInfoimage_1.jpg","payTime":1521170723000,"no":"PAY-DD20180316221580-26562","money":1,"status":0,"payType":1}]
     * billStatus : null
     * orderProduct : [{"createTime":1521170651000,"updateTime":1521170651000,"creater":null,"updater":null,"isDelete":0,"id":456947,"orderId":221580,"productId":5,"typeName":"文胸系列","natureName":null,"name":"金薇烧花款胸罩","price":"8900","inprice":null,"priceNumber":null,"count":"50","sku":"JW1520R0570C","colorCode":"R05","sizeCode":"70C","color":"中国红","size":"70C","alreadyShipped":null,"notShipped":null,"status":null,"totalPrice":445000,"no":"JW1520","num":null,"isFee":null,"feeAmount":null,"feeCount":null}]
     * address : 北京市 北京市市辖区  东城区111
     * receiver : JW
     * nature : 1
     * billType : null
     * natureName : 云订单
     * count : 50
     * userId : 822125
     * createTime : 1521170651000
     * repositoryType : 1
     * payInfo : http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_DD20180316221580_1521170722809_payInfoimage_1.jpg
     * status : 4
     */

    private int orderType;
    private AgentBean agent;
    private int agentId;
    private String orderNumber;
    private int returnValue;
    private String typeName;
    private String errMsg;
    private int type;
    private int total;
    private String receiveName;
    private Object sendProgress;
    private String mobilephone;
    private Object billStatus;
    private String address;
    private String receiver;
    private int nature;
    private Object billType;
    private String natureName;
    private int useBalance;
    private int count;
    private int userId;
    private long createTime;
    private int repositoryType;
    private String payInfo;
    private int status;
    private List<PayInfoListBean> payInfoList;
    private List<OrderProductBean> orderProduct;

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public AgentBean getAgent() {
        return agent;
    }

    public void setAgent(AgentBean agent) {
        this.agent = agent;
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

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public int getUseBalance() {
        return useBalance;
    }

    public void setUseBalance(int useBalance) {
        this.useBalance = useBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public List<PayInfoListBean> getPayInfoList() {
        return payInfoList;
    }

    public void setPayInfoList(List<PayInfoListBean> payInfoList) {
        this.payInfoList = payInfoList;
    }

    public List<OrderProductBean> getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(List<OrderProductBean> orderProduct) {
        this.orderProduct = orderProduct;
    }

    public static class AgentBean {
        /**
         * headPicture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_1517554057332_headImage.jpg
         * agentId : 822125
         * code : K20180201822125
         * mobilephone : 13588032842
         * name : 郑盛测试1
         */

        private String headPicture;
        private int agentId;
        private String code;
        private String mobilephone;
        private String name;

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PayInfoListBean {
        /**
         * createTime : 1521170723000
         * updateTime : null
         * creater : null
         * updater : null
         * isDelete : null
         * id : 26560
         * orderNo : DD20180316221580
         * orderId : 221580
         * payInfo : http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_DD20180316221580_1521170722825_payInfoimage_2.jpg
         * payTime : 1521170723000
         * no : PAY-DD20180316221580-26560
         * money : 1
         * status : 0
         * payType : 1
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

    public static class OrderProductBean {
        /**
         * createTime : 1521170651000
         * updateTime : 1521170651000
         * creater : null
         * updater : null
         * isDelete : 0
         * id : 456947
         * orderId : 221580
         * productId : 5
         * typeName : 文胸系列
         * natureName : null
         * name : 金薇烧花款胸罩
         * price : 8900
         * inprice : null
         * priceNumber : null
         * count : 50
         * sku : JW1520R0570C
         * colorCode : R05
         * sizeCode : 70C
         * color : 中国红
         * size : 70C
         * alreadyShipped : null
         * notShipped : null
         * status : null
         * totalPrice : 445000
         * no : JW1520
         * num : null
         * isFee : null
         * feeAmount : null
         * feeCount : null
         */

        private long createTime;
        private long updateTime;
        private Object creater;
        private Object updater;
        private int isDelete;
        private int id;
        private int orderId;
        private int productId;
        private String typeName;
        private Object natureName;
        private String name;
        private String price;
        private Object inprice;
        private Object priceNumber;
        private String count;
        private String sku;
        private String colorCode;
        private String sizeCode;
        private String color;
        private String size;
        private Object alreadyShipped;
        private Object notShipped;
        private Object status;
        private int totalPrice;
        private String no;
        private Object num;
        private Object isFee;
        private Object feeAmount;
        private Object feeCount;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
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

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Object getNatureName() {
            return natureName;
        }

        public void setNatureName(Object natureName) {
            this.natureName = natureName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Object getInprice() {
            return inprice;
        }

        public void setInprice(Object inprice) {
            this.inprice = inprice;
        }

        public Object getPriceNumber() {
            return priceNumber;
        }

        public void setPriceNumber(Object priceNumber) {
            this.priceNumber = priceNumber;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }

        public String getSizeCode() {
            return sizeCode;
        }

        public void setSizeCode(String sizeCode) {
            this.sizeCode = sizeCode;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Object getAlreadyShipped() {
            return alreadyShipped;
        }

        public void setAlreadyShipped(Object alreadyShipped) {
            this.alreadyShipped = alreadyShipped;
        }

        public Object getNotShipped() {
            return notShipped;
        }

        public void setNotShipped(Object notShipped) {
            this.notShipped = notShipped;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public Object getNum() {
            return num;
        }

        public void setNum(Object num) {
            this.num = num;
        }

        public Object getIsFee() {
            return isFee;
        }

        public void setIsFee(Object isFee) {
            this.isFee = isFee;
        }

        public Object getFeeAmount() {
            return feeAmount;
        }

        public void setFeeAmount(Object feeAmount) {
            this.feeAmount = feeAmount;
        }

        public Object getFeeCount() {
            return feeCount;
        }

        public void setFeeCount(Object feeCount) {
            this.feeCount = feeCount;
        }
    }
}
