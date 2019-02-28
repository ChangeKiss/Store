package com.Store.www.entity;

import java.util.List;

/**
 * 资讯新闻响应体
 */

public class NewsResponse extends BaseBean{

    /**
     * data : [{"textDatas":[{"title":"大码文胸上线通知","time":1503849600000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/88464625-aba4-4119-ad56-4d5794ad7326.jpg","autor":"kivie","infoid":25}],"typeTitle":"公司新闻","typenum":3}]
     * errMsg : 成功
     */

    private String errMsg;
    private List<DataBean> data;

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
         * textDatas : [{"title":"大码文胸上线通知","time":1503849600000,"pictueUrl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/88464625-aba4-4119-ad56-4d5794ad7326.jpg","autor":"kivie","infoid":25}]
         * typeTitle : 公司新闻
         * typenum : 3
         */

        private String typeTitle;
        private int typenum;
        private List<TextDatasBean> textDatas;

        public String getTypeTitle() {
            return typeTitle;
        }

        public void setTypeTitle(String typeTitle) {
            this.typeTitle = typeTitle;
        }

        public int getTypenum() {
            return typenum;
        }

        public void setTypenum(int typenum) {
            this.typenum = typenum;
        }

        public List<TextDatasBean> getTextDatas() {
            return textDatas;
        }

        public void setTextDatas(List<TextDatasBean> textDatas) {
            this.textDatas = textDatas;
        }

        public static class TextDatasBean {
            /**
             * title : 大码文胸上线通知
             * time : 1503849600000
             * pictueUrl : http://jwbucket.oss-cn-shanghai.aliyuncs.com/88464625-aba4-4119-ad56-4d5794ad7326.jpg
             * autor : kivie
             * infoid : 25
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
}
