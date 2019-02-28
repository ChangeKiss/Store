package com.Store.www.entity;

import java.util.List;

/**
 * 总业绩响应体
 */

public class SumResultsResponse {


    /**
     * returnValue : 1
     * data : [{"allMoney":999,"unauditedMoney":999,"auditedMoney":999,"allCount":999,"unauditedCount":999,"auditedCount":999,"month":9}]
     * errMsg : 错误信息, 字符串
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
         * allMoney : 999
         * unauditedMoney : 999
         * auditedMoney : 999
         * allCount : 999
         * unauditedCount : 999
         * auditedCount : 999
         * year:2018
         * month : 9
         * "time":"2018年3月份",
         */

        private int allMoney;
        private int unauditedMoney;
        private int auditedMoney;
        private int allCount;
        private int unauditedCount;
        private int auditedCount;
        private int year;
        private String month;
        private String time;

        public int getAllMoney() {
            return allMoney;
        }

        public void setAllMoney(int allMoney) {
            this.allMoney = allMoney;
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

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }

        public int getUnauditedCount() {
            return unauditedCount;
        }

        public void setUnauditedCount(int unauditedCount) {
            this.unauditedCount = unauditedCount;
        }

        public int getAuditedCount() {
            return auditedCount;
        }

        public void setAuditedCount(int auditedCount) {
            this.auditedCount = auditedCount;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
