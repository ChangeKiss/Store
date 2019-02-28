package com.Store.www.entity;

import java.util.List;

/**
 * 新闻评论请求体
 */

public class NewsCommentResponse {

    /**
     * returnValue : 1
     * isLastPage : 1
     * errMsg : 错误信息, 字符串
     * data : [{"content":"评论内容,字符串","time":"发布时间,长整形","name":"用户名称, 整型","isSelf":1,"headPicture":"用户头像, 整型"}]
     */

    private int returnValue;
    private int isLastPage;
    private String errMsg;
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
         * content : 评论内容,字符串
         * "commentId":【评论ID,整形】,
         * time : 发布时间,长整形
         * name : 用户名称, 整型
         * isSelf : 1
         * headPicture : 用户头像, 整型
         */

        private String content;
        private int commentId;
        private long time;
        private String name;
        private int isSelf;
        private String headPicture;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIsSelf() {
            return isSelf;
        }

        public void setIsSelf(int isSelf) {
            this.isSelf = isSelf;
        }

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }
    }
}
