package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 下级团队人员响应体
 */
public class SubordinateTeamPeopleResponse {

    /**
     * returnValue : 1
     * dataList : [{"num":0,"name":"马睿"},{"num":2,"name":"麦敏芳"},{"num":1,"name":"张蔡叶"},{"num":0,"name":"杜丽萍"},{"num":0,"name":"田瑞丽"},{"num":5,"name":"殷雪芬"},{"num":0,"name":"钱黄慧"},{"num":0,"name":"朱乔平"}]
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
         * num : 0
         * name : 马睿
         */

        private int num;
        private String name;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
