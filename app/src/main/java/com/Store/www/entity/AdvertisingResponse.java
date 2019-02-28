package com.Store.www.entity;

/**
 * Created by www on 2018/8/8.
 * 广告响应体
 */

public class AdvertisingResponse {

    /**
     * returnValue : 1
     * data : {"picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/62547071-199a-44bb-befe-7ff20921643e.png","url":"http://www.baidu.com","status":1}
     */

    private int returnValue;
    private DataBean data;

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

    public static class DataBean {
        /**
         * picture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/62547071-199a-44bb-befe-7ff20921643e.png
         * url : http://www.baidu.com
         * status : 1
         */

        private String picture;
        private String url;
        private int status;

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
