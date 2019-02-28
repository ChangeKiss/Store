package com.Store.www.entity;

/**
 * Created by www on 2017/12/25.
 */

public class LoginResponse extends BaseBean{

    /**
     * data : {"agentNum":"JW20171220818206","headPicture":null,"agentId":818206,"address":"","wechatNum":null,"receiver":"JW","phone":"13588032842","level":"VIP客户","name":"郑盛测试用","repositoryType":1,"email":null,"token":"e10adc3949ba59abbe56e057f20f883e"}
     * errMsg : 成功
     */

    private DataBean data;
    private String errMsg;

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
         * agentNum : JW20171220818206
         * headPicture : null
         * agentId : 818206
         * address :
         * wechatNum : null
         * receiver : JW
         * phone : 13588032842
         * level : VIP客户
         * name : 郑盛测试用
         * repositoryType : 1
         * email : null
         * token : e10adc3949ba59abbe56e057f20f883e
         * code   代理商编号
         * account  新代理商编号
         * isAlipay 是否能使用支付宝
         */

        private String agentNum;
        private String headPicture;
        private int agentId;
        private String address;
        private Object wechatNum;
        private String receiver;
        private String phone;
        private String level;
        private String name;
        private int repositoryType;
        private Object email;
        private String token;
        private String code;
        private String account;
        private int isAlipay;
        private String loginToken;

        public String getAgentNum() {
            return agentNum;
        }

        public void setAgentNum(String agentNum) {
            this.agentNum = agentNum;
        }

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getWechatNum() {
            return wechatNum;
        }

        public void setWechatNum(Object wechatNum) {
            this.wechatNum = wechatNum;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRepositoryType() {
            return repositoryType;
        }

        public void setRepositoryType(int repositoryType) {
            this.repositoryType = repositoryType;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getIsAlipay() {
            return isAlipay;
        }

        public void setIsAlipay(int isAlipay) {
            this.isAlipay = isAlipay;
        }

        public String getLoginToken() {
            return loginToken;
        }

        public void setLoginToken(String loginToken) {
            this.loginToken = loginToken;
        }
    }
}
