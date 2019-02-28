package com.Store.www.entity;

/**
 * @author: haifeng
 * @description:  我的团队响应体
 */
public class MyTeamDetailResponse {

    /**
     * returnValue : 1
     * data : {"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1541060970349","unauditedCorsetMoney":548475000,"subordinateCount":57410,"newNumber":100,"agentName":"王汉林","unauditedMoney":538200,"auditedMoney":1355700,"auditedCorsetMoney":426000000}
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
         * headPicture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/1541060970349
         * unauditedCorsetMoney : 548475000
         * subordinateCount : 57410
         * newNumber : 100
         * agentName : 王汉林
         * unauditedMoney : 538200
         * auditedMoney : 1355700
         * auditedCorsetMoney : 426000000
         */

        private String headPicture;
        private int unauditedCorsetMoney;
        private int subordinateCount;
        private int newNumber;
        private String agentName;
        private int unauditedMoney;
        private int auditedMoney;
        private int auditedCorsetMoney;

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public int getUnauditedCorsetMoney() {
            return unauditedCorsetMoney;
        }

        public void setUnauditedCorsetMoney(int unauditedCorsetMoney) {
            this.unauditedCorsetMoney = unauditedCorsetMoney;
        }

        public int getSubordinateCount() {
            return subordinateCount;
        }

        public void setSubordinateCount(int subordinateCount) {
            this.subordinateCount = subordinateCount;
        }

        public int getNewNumber() {
            return newNumber;
        }

        public void setNewNumber(int newNumber) {
            this.newNumber = newNumber;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public int getUnauditedMoney() {
            return unauditedMoney;
        }

        public void setUnauditedMoney(int unauditedMoney) {
            this.unauditedMoney = unauditedMoney;
        }

        public int getAuditedMoney() {
            return auditedMoney;
        }

        public void setAuditedMoney(int auditedMoney) {
            this.auditedMoney = auditedMoney;
        }

        public int getAuditedCorsetMoney() {
            return auditedCorsetMoney;
        }

        public void setAuditedCorsetMoney(int auditedCorsetMoney) {
            this.auditedCorsetMoney = auditedCorsetMoney;
        }
    }
}
