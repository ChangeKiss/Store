package com.Store.www.entity;

/**
 * @author: haifeng
 * @description:  提交结算购物车响应体
 */
public class CloseAccountResponse {

    /**
     * returnValue : 1
     * data : {"isKVE20D":1}
     * errMsg : 结算提交成功
     */

    private int returnValue;
    private DataBean data;
    private String errMsg;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static class DataBean {
        /**
         * isKVE20D : 1
         */

        private int isKVE20D;

        public int getIsKVE20D() {
            return isKVE20D;
        }

        public void setIsKVE20D(int isKVE20D) {
            this.isKVE20D = isKVE20D;
        }
    }
}
