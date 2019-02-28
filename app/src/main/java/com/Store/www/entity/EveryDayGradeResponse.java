package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 每日业绩响应体
 */
public class EveryDayGradeResponse {

    /**
     * returnValue : 1
     * dataList : [{"time":1541088000000,"audited":1249000,"unaudited":0},{"time":1541692800000,"audited":148000,"unaudited":0},{"time":1541865600000,"audited":124500,"unaudited":0},{"time":1542038400000,"audited":25000,"unaudited":0},{"time":1542297600000,"audited":5900,"unaudited":0},{"time":1542384000000,"audited":11800,"unaudited":0},{"time":1542470400000,"audited":254500,"unaudited":0},{"time":1542556800000,"audited":8784800,"unaudited":0},{"time":1542643200000,"audited":31600,"unaudited":0},{"time":1542729600000,"audited":560900,"unaudited":0},{"time":1542816000000,"audited":276900,"unaudited":0},{"time":1542902400000,"audited":952400,"unaudited":0},{"time":1542988800000,"audited":12059000,"unaudited":0},{"time":1543075200000,"audited":5275100,"unaudited":0},{"time":1543334400000,"audited":487000,"unaudited":0},{"time":1543507200000,"audited":1185000,"unaudited":0}]
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
         * time : 1541088000000
         * audited : 1249000
         * unaudited : 0
         */

        private long time;
        private int audited;
        private int unaudited;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getAudited() {
            return audited;
        }

        public void setAudited(int audited) {
            this.audited = audited;
        }

        public int getUnaudited() {
            return unaudited;
        }

        public void setUnaudited(int unaudited) {
            this.unaudited = unaudited;
        }
    }
}
