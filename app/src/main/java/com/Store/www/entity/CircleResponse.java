package com.Store.www.entity;

import java.util.List;

/**
 * 圈子响应体
 */

public class CircleResponse {

    /**
     * returnValue : 1
     * data : [{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"images":[{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1520497771032_circleImage_0.jpg"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1520497771062_circleImage_1.jpg"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1520497771079_circleImage_2.jpg"}],"isLike":0,"likeCount":0,"circleId":13,"time":1520497774000,"type":1,"content":"111","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"isLike":0,"likeCount":0,"circleId":12,"time":1520497550000,"type":0,"content":"","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"images":[{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497539228"}],"isLike":0,"likeCount":0,"circleId":11,"time":1520497543000,"type":1,"content":"测试","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"images":[{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497326768"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497329376"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497331600"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497334237"}],"isLike":0,"likeCount":0,"circleId":10,"time":1520497335000,"type":1,"content":"一起嗨","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"images":[{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497126520"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497129207"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497131462"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520497134926"}],"isLike":0,"likeCount":0,"circleId":9,"time":1520497136000,"type":1,"content":"嗨嗨嗨嗨","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"isLike":0,"likeCount":0,"circleId":8,"time":1520496917000,"type":0,"content":"嗨起来","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"isLike":0,"likeCount":0,"circleId":7,"time":1520496073000,"type":0,"content":"","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"images":[{"url":"http://drfwimg.oss-cn-beijing.aliyuncs.com/e2c097f9-c621-4c64-9443-84633f3e4c991511493154153"}],"isLike":0,"likeCount":0,"circleId":6,"time":1520495603000,"type":1,"content":"","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"isLike":0,"likeCount":0,"circleId":5,"time":1520495002000,"type":0,"content":"帅哈哈姐姐啊","commentCount":0},{"userInfo":{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699},"isOwn":0,"images":[{"url":"http://drfwimg.oss-cn-beijing.aliyuncs.com/e2c097f9-c621-4c64-9443-84633f3e4c991511493154153"}],"isLike":0,"likeCount":0,"circleId":4,"time":1520412504000,"type":1,"content":"test","commentCount":0}]
     * isLastPage : 0
     */

    private int returnValue;
    private int isLastPage;
    private List<DataBean> data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public int getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(int isLastPage) {
        this.isLastPage = isLastPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userInfo : {"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg","nickName":"胡巍-测试用","userId":252699}
         * isOwn : 0
         * images : [{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1520497771032_circleImage_0.jpg"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1520497771062_circleImage_1.jpg"},{"url":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1520497771079_circleImage_2.jpg"}]
         * isLike : 0
         * likeCount : 0
         * circleId : 13
         * time : 1520497774000
         * type : 1
         * content : 111
         * commentCount : 0
         */

        private UserInfoBean userInfo;
        private int isOwn;
        private int isLike;
        private int likeCount;
        private int circleId;
        private long time;
        private int type;
        private String content;
        private int commentCount;
        private List<ImagesBean> images;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public int getIsOwn() {
            return isOwn;
        }

        public void setIsOwn(int isOwn) {
            this.isOwn = isOwn;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCircleId() {
            return circleId;
        }

        public void setCircleId(int circleId) {
            this.circleId = circleId;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class UserInfoBean {
            /**
             * headPicture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1516775117102_headImage.jpg
             * nickName : 胡巍-测试用
             * userId : 252699
             */

            private String headPicture;
            private String nickName;
            private int userId;

            public String getHeadPicture() {
                return headPicture;
            }

            public void setHeadPicture(String headPicture) {
                this.headPicture = headPicture;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }

        public static class ImagesBean {
            /**
             * url : http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_1520497771032_circleImage_0.jpg
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
