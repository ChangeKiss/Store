package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 每日招新人数响应体
 */
public class EveryDayNewPeopleResponse {

    /**
     * returnValue : 1
     * dataList : [{"num":7,"time":1538323200000},{"num":2,"time":1538409600000},{"num":3,"time":1538496000000},{"num":10,"time":1538582400000},{"num":7,"time":1538668800000},{"num":6,"time":1538755200000},{"num":4,"time":1538841600000},{"num":3,"time":1538928000000},{"num":3,"time":1539014400000},{"num":6,"time":1539100800000},{"num":6,"time":1539187200000},{"num":7,"time":1539273600000},{"num":3,"time":1539360000000},{"num":5,"time":1539446400000},{"num":5,"time":1539532800000},{"num":6,"time":1539619200000},{"num":6,"time":1539705600000},{"num":2,"time":1539792000000},{"num":3,"time":1539878400000},{"num":3,"time":1539964800000},{"num":5,"time":1540051200000},{"num":9,"time":1540137600000},{"num":5,"time":1540224000000},{"num":1,"time":1540310400000},{"num":1,"time":1540396800000},{"num":5,"time":1540483200000},{"num":3,"time":1540569600000},{"num":2,"time":1540656000000},{"num":2,"time":1540742400000},{"num":3,"time":1540828800000},{"num":0,"time":1540915200000}]
     * errMsg : 成功
     */

    private int returnValue;
    private String errMsg;
    private List<DataListBean> dataList;

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

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * num : 7
         * time : 1538323200000
         */

        private int num;
        private long time;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
