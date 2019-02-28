package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/24.
 * 扫描记录明细响应体
 */

public class ScanRecordDetailResponse {

    /**
     * returnValue : 1
     * data : [{"date":1540345699000,"imageUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/打底裤APP主图.jpg","productName":"金薇Me裤","barCode":"1010651011800"}]
     */

    private int returnValue;
    private List<DataBean> data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * date : 1540345699000
         * imageUrl : http://jwbucket.oss-cn-shanghai.aliyuncs.com/打底裤APP主图.jpg
         * productName : 金薇Me裤
         * barCode : 1010651011800
         */

        private long date;
        private String imageUrl;
        private String productName;
        private String barCode;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }
    }
}
