package com.Store.www.entity;

import java.util.List;

/**
 * 新闻资讯更多的响应体
 */

public class NewsMoreResponse {

    /**
     * returnValue : 1
     * data : [{"title":"金薇代理365保障计划","time":1513094400000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/9d344ce7-f589-4490-b21f-b3ecf6555f3d.jpg","autor":"kivie","infoid":55},{"title":"关于加盟费用取消通知","time":1513094400000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/0711de3c-0c73-4d96-b871-1d75fb3a1e3d.jpg","autor":"kivie","infoid":54},{"title":"分红查询地址","time":1508428800000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/963e65e0-aea4-4d54-869b-726bc0c6dc11.png","autor":"kivie","infoid":39},{"title":"大码文胸上线通知","time":1503849600000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/88464625-aba4-4119-ad56-4d5794ad7326.jpg","autor":"kivie","infoid":25},{"title":"青春成人礼 不做圈内人","time":1501689600000,"pictueUrl":null,"autor":"kivie","infoid":24},{"title":"运动内衣上新通告","time":1500912000000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/639ced8a-d180-4782-bd54-a2c17e3a31ed.jpg","autor":"kivie","infoid":20}]
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
         * title : 金薇代理365保障计划
         * time : 1513094400000
         * pictueUrl : http://jwbucket.oss-cn-shanghai.aliyuncs.com/9d344ce7-f589-4490-b21f-b3ecf6555f3d.jpg
         * autor : kivie
         * infoid : 55
         */

        private String title;
        private long time;
        private String pictueUrl;
        private String autor;
        private int infoid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getPictueUrl() {
            return pictueUrl;
        }

        public void setPictueUrl(String pictueUrl) {
            this.pictueUrl = pictueUrl;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public int getInfoid() {
            return infoid;
        }

        public void setInfoid(int infoid) {
            this.infoid = infoid;
        }
    }
}
