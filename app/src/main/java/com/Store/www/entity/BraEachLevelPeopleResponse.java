package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 各等级人员分布响应体
 */
public class BraEachLevelPeopleResponse {

    /**
     * returnValue : 1
     * dataList : [{"count":360,"levelName":" 一级代理"},{"count":3785,"levelName":"代理商"},{"count":49343,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":3918,"levelName":"钻石总代"}]
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
         * count : 360
         * levelName :  一级代理
         */

        private int count;
        private String levelName;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }
    }
}
