package com.Store.www.entity;

/**
 * 轮播图响应体
 */

public class CarouselResponse extends BaseBean{


    /**
     * data : {"id":42,"title":"支付宝通道使用教程","autor":"kivie","context":"       <p>< img style=\"max-width:100%;\" alt=\"支付教程\" src=\"http://jwbucket.oss-cn-shanghai.aliyuncs.com/84c5ed72-af1f-4302-b49a-1df52e836001.png\"><b><\/b><i><\/i><u><\/u><sub><\/sub><sup><\/sup><strike><\/strike><br><\/p ><p><br><\/p >","creatTime":1510156800000,"type":"首页轮播","typenum":5,"pictueurl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/b3ea07c0-6ef1-4f65-ab57-add4ce7e89b1.jpg"}
     * errMsg : 成功
     */

    private DataBean data;
    private String errMsg;

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
         * id : 42
         * title : 支付宝通道使用教程
         * autor : kivie
         * context :  <p>< img style="max-width:100%;" alt="支付教程" src="http://jwbucket.oss-cn-shanghai.aliyuncs.com/84c5ed72-af1f-4302-b49a-1df52e836001.png"><b></b><i></i><u></u><sub></sub><sup></sup><strike></strike><br></p ><p><br></p >
         * creatTime : 1510156800000
         * type : 首页轮播
         * typenum : 5
         * pictueurl : http://jwbucket.oss-cn-shanghai.aliyuncs.com/b3ea07c0-6ef1-4f65-ab57-add4ce7e89b1.jpg
         */

        private int id;
        private String title;
        private String autor;
        private String context;
        private long creatTime;
        private String type;
        private int typenum;
        private String pictueurl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public long getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(long creatTime) {
            this.creatTime = creatTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getTypenum() {
            return typenum;
        }

        public void setTypenum(int typenum) {
            this.typenum = typenum;
        }

        public String getPictueurl() {
            return pictueurl;
        }

        public void setPictueurl(String pictueurl) {
            this.pictueurl = pictueurl;
        }
    }
}
