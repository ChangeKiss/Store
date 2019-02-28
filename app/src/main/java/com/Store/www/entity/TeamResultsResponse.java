package com.Store.www.entity;

import java.util.List;

/**
 * 业绩明细响应体
 */

public class TeamResultsResponse {


    /**
     * returnValue : 1
     * data : [{"details":[{"totalMoney":1090000,"agentCode":"K20180201822125","time":"2018-03","createTime":"2018年03月22日","profit":1090000,"agentName":"郑盛测试1","status":1,"orderNo":"DD20180322221629"},{"totalMoney":890000,"agentCode":"K20180201822125","time":"2018-03","createTime":"2018年03月22日","profit":890000,"agentName":"郑盛测试1","status":2,"orderNo":"DD20180322221630"},{"totalMoney":185300000,"agentCode":"K20180201822125","time":"2018-03","createTime":"2018年03月22日","profit":185300000,"agentName":"郑盛测试1","status":1,"orderNo":"DD20180322221631"}],"time":"2018-03"},{"details":[],"time":"2018-02"},{"details":[],"time":"2018-01"}]
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
         * details : [{"totalMoney":1090000,"agentCode":"K20180201822125","time":"2018-03","createTime":"2018年03月22日","profit":1090000,"agentName":"郑盛测试1","status":1,"orderNo":"DD20180322221629"},{"totalMoney":890000,"agentCode":"K20180201822125","time":"2018-03","createTime":"2018年03月22日","profit":890000,"agentName":"郑盛测试1","status":2,"orderNo":"DD20180322221630"},{"totalMoney":185300000,"agentCode":"K20180201822125","time":"2018-03","createTime":"2018年03月22日","profit":185300000,"agentName":"郑盛测试1","status":1,"orderNo":"DD20180322221631"}]
         * time : 2018-03
         */

        private String time;
        private List<DetailsBean> details;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean {
            /**
             * totalMoney : 1090000
             * agentCode : K20180201822125
             * time : 2018-03
             * createTime : 2018年03月22日
             * profit : 1090000
             * agentName : 郑盛测试1
             * status : 1
             * orderNo : DD20180322221629
             */

            private int totalMoney;
            private String agentCode;
            private String time;
            private String createTime;
            private int profit;
            private String agentName;
            private int status;
            private String orderNo;

            public int getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(int totalMoney) {
                this.totalMoney = totalMoney;
            }

            public String getAgentCode() {
                return agentCode;
            }

            public void setAgentCode(String agentCode) {
                this.agentCode = agentCode;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getProfit() {
                return profit;
            }

            public void setProfit(int profit) {
                this.profit = profit;
            }

            public String getAgentName() {
                return agentName;
            }

            public void setAgentName(String agentName) {
                this.agentName = agentName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }
        }
    }
}
