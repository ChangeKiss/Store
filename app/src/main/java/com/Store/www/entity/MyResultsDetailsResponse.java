package com.Store.www.entity;

import java.util.List;

/**
 * 我的业绩明细的响应体
 */

public class MyResultsDetailsResponse {

    /**
     * returnValue : 1
     * data : [{"month":"03","totalMoney":1090000,"agentCode":"K20180201822125","profit":1090000,"createTime":"2018年03月22日","agentName":"郑盛测试1","status":1,"orderNo":"DD20180322221629"},{"month":"03","totalMoney":890000,"agentCode":"K20180201822125","profit":890000,"createTime":"2018年03月22日","agentName":"郑盛测试1","status":2,"orderNo":"DD20180322221630"},{"month":"03","totalMoney":185300000,"agentCode":"K20180201822125","profit":185300000,"createTime":"2018年03月22日","agentName":"郑盛测试1","status":1,"orderNo":"DD20180322221631"},{"month":"03","totalMoney":1090000,"agentCode":"K20180201822125","profit":1090000,"createTime":"2018年03月22日","agentName":"郑盛测试1","status":1,"orderNo":"DD20180322221632"},{"month":"03","totalMoney":890000,"agentCode":"18857738393","profit":890000,"createTime":"2018年03月22日","agentName":"胡巍-测试用","status":1,"orderNo":"DD20180322221633"},{"month":"03","totalMoney":1090000,"agentCode":"K20180202822134","profit":1090000,"createTime":"2018年03月22日","agentName":"kivie","status":2,"orderNo":"DD20180322221634"}]
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
         * month : 03
         * totalMoney : 1090000
         * agentCode : K20180201822125
         * profit : 1090000
         * createTime : 2018年03月22日
         * agentName : 郑盛测试1
         * status : 1
         * orderNo : DD20180322221629
         */

        private String month;
        private int totalMoney;
        private String agentCode;
        private int profit;
        private String createTime;
        private String agentName;
        private int status;
        private String orderNo;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

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

        public int getProfit() {
            return profit;
        }

        public void setProfit(int profit) {
            this.profit = profit;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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
