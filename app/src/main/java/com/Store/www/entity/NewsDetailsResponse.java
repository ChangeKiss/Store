package com.Store.www.entity;

/**
 * 新闻详情响应体
 */

public class NewsDetailsResponse {

    /**
     * returnValue : 1
     * data : {"id":55,"title":"金薇代理365保障计划","autor":"kivie","context":"                                   <p><img style=\"max-width:100%;\" alt=\"1\" src=\"http://jwbucket.oss-cn-shanghai.aliyuncs.com/d1c2b455-b8d4-44bd-a6b4-0d709aac9bda.png\"><img style=\"max-width:100%;\" alt=\"2\" src=\"http://jwbucket.oss-cn-shanghai.aliyuncs.com/fda7c8ba-3a64-4f5a-a4cc-51cefaa669b9.png\"><img style=\"max-width:100%;\" alt=\"3\" src=\"http://jwbucket.oss-cn-shanghai.aliyuncs.com/de1013fb-b30f-4cee-9513-d130b1a958a6.png\"><\/p>\r\n                                 <p><b><\/b><i><\/i><u><\/u><sub><\/sub><sup><\/sup><strike><\/strike><b><\/b><i><\/i><u><\/u><sub><\/sub><sup><\/sup><strike><\/strike><b><\/b><i><\/i><u><\/u><sub><\/sub><sup><\/sup><strike><\/strike><br><\/p><p><br><\/p>","creatTime":1513094400000,"type":"公司新闻","typenum":3,"pictueurl":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/9d344ce7-f589-4490-b21f-b3ecf6555f3d.jpg"}
     * errMsg : 成功
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
         * id : 55
         * title : 金薇代理365保障计划
         * autor : kivie
         * context :                                    <p><img style="max-width:100%;" alt="1" src="http://jwbucket.oss-cn-shanghai.aliyuncs.com/d1c2b455-b8d4-44bd-a6b4-0d709aac9bda.png"><img style="max-width:100%;" alt="2" src="http://jwbucket.oss-cn-shanghai.aliyuncs.com/fda7c8ba-3a64-4f5a-a4cc-51cefaa669b9.png"><img style="max-width:100%;" alt="3" src="http://jwbucket.oss-cn-shanghai.aliyuncs.com/de1013fb-b30f-4cee-9513-d130b1a958a6.png"></p>
         <p><b></b><i></i><u></u><sub></sub><sup></sup><strike></strike><b></b><i></i><u></u><sub></sub><sup></sup><strike></strike><b></b><i></i><u></u><sub></sub><sup></sup><strike></strike><br></p><p><br></p>
         * creatTime : 1513094400000
         * type : 公司新闻
         * typenum : 3
         * pictueurl : http://jwbucket.oss-cn-shanghai.aliyuncs.com/9d344ce7-f589-4490-b21f-b3ecf6555f3d.jpg
         */

        private int id;
        private String title;
        private String autor;
        private String context;
        private long creatTime;
        private String type;
        private int typenum;
        private String pictueurl;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
