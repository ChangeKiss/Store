package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 塑身衣代理等级分布
 */
public class ShapeWearEachLevelPeopleResponse {

    /**
     * returnValue : 1
     * dataList : [{"count":0,"levelName":"特约"},{"count":0,"levelName":"二级"},{"count":0,"levelName":"一级"},{"count":65,"levelName":"钻石"},{"count":60,"levelName":"合伙人"}]
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
         * count : 0
         * levelName : 特约
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
