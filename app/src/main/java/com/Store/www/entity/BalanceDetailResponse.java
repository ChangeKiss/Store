package com.Store.www.entity;

import java.util.List;

/**
 * 余额明细响应体
 */

public class BalanceDetailResponse {

    /**
     * returnValue : 1
     * data : {"month":4,"totalMoney":5270000,"details":[{"money":5270000,"month":4,"year":2018,"createTime":"2018-04-20 15:31:20","detail":"业绩分红收入","type":1}]}
     */

    private int returnValue;
    private String errMsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * month : 4
         * totalMoney : 5270000
         * details : [{"money":5270000,"month":4,"year":2018,"createTime":"2018-04-20 15:31:20","detail":"业绩分红收入","type":1}]
         */

        private int month;
        private int totalMoney;
        private List<DetailsBean> details;

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(int totalMoney) {
            this.totalMoney = totalMoney;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean {
            /**
             * money : 5270000
             * month : 4
             * year : 2018
             * createTime : 2018-04-20 15:31:20
             * detail : 业绩分红收入
             * type : 1
             */

            private int money;
            private int month;
            private int year;
            private String createTime;
            private String detail;
            private int type;
            private int id;

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
