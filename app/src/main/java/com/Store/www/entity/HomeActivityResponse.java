package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description:
 * 首页活动响应体
 */
public class HomeActivityResponse {

    /**
     * returnValue : 1
     * data : [{"createTime":1554099649000,"id":1,"title":"测试","photos":[{"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20190109093305.jpg","id":1}]}]
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
         * createTime : 1554099649000
         * id : 1
         * title : 测试
         * photos : [{"photo":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20190109093305.jpg","id":1}]
         */

        private long createTime;
        private int id;
        private String title;
        private List<PhotosBean> photos;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

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

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class PhotosBean {
            /**
             * photo : http://jwbucket.oss-cn-shanghai.aliyuncs.com/20190109093305.jpg
             * id : 1
             */

            private String photo;
            private int id;

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
}
