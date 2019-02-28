package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/8/14.
 * 查看物流响应体
 */

public class LookLogisticsResponse {

    /**
     * returnValue : 1
     * data : [{"no":"3366969258232","info":["本人签收-已签收","福建厦门杏东公司-钟诗纬(18059297137,)-派件中","已到达-福建厦门杏东公司","福建厦门转运中心-已发往-福建厦门杏东公司","福建厦门转运中心-已发往-福建厦门杏东公司","广东佛山航空部-已进行装车扫描","广东佛山航空部-已发往-福建厦门转运中心","广东佛山公司--已收件"]}]
     * errMsg : null
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
         * no : 3366969258232
         * info : ["本人签收-已签收","福建厦门杏东公司-钟诗纬(18059297137,)-派件中","已到达-福建厦门杏东公司","福建厦门转运中心-已发往-福建厦门杏东公司","福建厦门转运中心-已发往-福建厦门杏东公司","广东佛山航空部-已进行装车扫描","广东佛山航空部-已发往-福建厦门转运中心","广东佛山公司--已收件"]
         */

        private String no;
        private List<String> info;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public List<String> getInfo() {
            return info;
        }

        public void setInfo(List<String> info) {
            this.info = info;
        }
    }
}
