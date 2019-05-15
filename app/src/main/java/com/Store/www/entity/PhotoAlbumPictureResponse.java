package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 相册照片响应体
 */
public class PhotoAlbumPictureResponse {

    /**
     * returnValue : 1
     * data : [{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":1,"height":100},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":2,"height":200},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125528.jpg","id":3,"height":60},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":4,"height":200},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125546.jpg","id":5,"height":120},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":6,"height":200},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125528.jpg","id":7,"height":80},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":8,"height":90},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125546.jpg","id":9,"height":200},{"name":"测试","width":100,"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":10,"height":150}]
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
         * name : 测试
         * width : 100
         * photo : http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg
         * id : 1
         * height : 100
         */

        private String name;
        private int width;
        private String photo;
        private int id;
        private int height;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
