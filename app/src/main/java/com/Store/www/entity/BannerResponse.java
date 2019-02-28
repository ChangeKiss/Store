package com.Store.www.entity;

import java.util.List;

/**
 * 轮播图的响应体
 */

public class BannerResponse extends BaseBenTwo{

    /**
     * data : [{"title":"金薇代理365保障计划","time":1513094400000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/9d344ce7-f589-4490-b21f-b3ecf6555f3d.jpg","autor":"k","infoid":55},{"title":"关于加盟费用取消通知","time":1513094400000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/0711de3c-0c73-4d96-b871-1d75fb3a1e3d.jpg","autor":"k","infoid":54},{"title":"支付宝通道使用教程","time":1510156800000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/b3ea07c0-6ef1-4f65-ab57-add4ce7e89b1.jpg","autor":"kivie","infoid":42},{"title":"分红查询地址","time":1508428800000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/963e65e0-aea4-4d54-869b-726bc0c6dc11.png","autor":"K","infoid":39}]
     * errMsg : 成功
     */

    private List<DataBean> data;

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
         * autor : k
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
