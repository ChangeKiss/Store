package com.Store.www.entity;

import java.util.List;

/**
 * 产品轮播响应体
 * @author: haifeng
 * @description:
 */
public class ProductBannerResponse {

    /**
     * returnValue : 1
     * data : [{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/8976d834-9a65-4234-aa5e-3c8a50f6cd48.jpg","infoId":2},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/f5ed3b35-9119-4ed1-9f6d-e47869fc7efb.jpg","infoId":1}]
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
         * url : http://jwbucket.oss-cn-shanghai.aliyuncs.com/8976d834-9a65-4234-aa5e-3c8a50f6cd48.jpg
         * infoId : 2
         */

        private String url;
        private int infoId;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getInfoId() {
            return infoId;
        }

        public void setInfoId(int infoId) {
            this.infoId = infoId;
        }
    }
}
