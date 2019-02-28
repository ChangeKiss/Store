package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/23.
 * 商品提示响应体
 */

public class CommodityHintResponse {

    /**
     * returnValue : 1
     * data : [{"productId":1,"remind":"1234456"}]
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
         * productId : 1
         * remind : 1234456
         */

        private int productId;
        private String remind;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getRemind() {
            return remind;
        }

        public void setRemind(String remind) {
            this.remind = remind;
        }
    }
}
