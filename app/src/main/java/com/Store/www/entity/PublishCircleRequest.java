package com.Store.www.entity;

import java.util.List;

/**
 * 发布圈子请求体
 */

public class PublishCircleRequest {


    /**
     * userId : 252699
     * circleInfo : {"content":"内容，字符串","type":0,"images":["images"]}
     */

    private int userId;
    private CircleInfoBean circleInfo;

    public PublishCircleRequest(int userId, CircleInfoBean circleInfo) {
        this.userId = userId;
        this.circleInfo = circleInfo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CircleInfoBean getCircleInfo() {
        return circleInfo;
    }

    public void setCircleInfo(CircleInfoBean circleInfo) {
        this.circleInfo = circleInfo;
    }

    public static class CircleInfoBean {
        /**
         * content : 内容，字符串
         * type : 0
         * images : ["images"]
         */

        private String content;
        private int type;
        private List<String> images;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }


    }
}
