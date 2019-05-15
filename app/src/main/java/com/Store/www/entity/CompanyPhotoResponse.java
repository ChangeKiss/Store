package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 公司相册响应体
 */
public class CompanyPhotoResponse {

    /**
     * returnValue : 1
     * data : [{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":1},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":2},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":3},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":4},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":5},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":6},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":7},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":8},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":9},{"createTime":1552962538000,"name":"测试","photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg","id":10}]
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
         * createTime : 1552962538000
         * name : 测试
         * photo : http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180911125710.jpg
         * id : 1
         */

        private long createTime;
        private String name;
        private String photo;
        private int id;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}
