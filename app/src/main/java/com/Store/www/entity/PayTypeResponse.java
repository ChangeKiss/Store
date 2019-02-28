package com.Store.www.entity;

import java.util.List;

/**
 * 获取支付方式响应体
 */

public class PayTypeResponse {

    /**
     * returnValue : 1
     * data : [{"isOpen":1,"name":"京东","id":1,"chd":1},{"isOpen":1,"name":"支付宝","id":2,"chd":2},{"isOpen":1,"name":"建行","id":3,"chd":4},{"isOpen":1,"name":"截图","id":4,"chd":5}]
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
         * isOpen : 1
         * name : 京东
         * id : 1
         * chd : 1
         */

        private int isOpen;
        private String name;
        private int id;
        private int chd;

        public int getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(int isOpen) {
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getChd() {
            return chd;
        }

        public void setChd(int chd) {
            this.chd = chd;
        }
    }
}
