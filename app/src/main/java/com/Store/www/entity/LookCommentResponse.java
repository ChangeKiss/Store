package com.Store.www.entity;

import java.util.List;

/**
 * 查看评论响应体
 */

public class LookCommentResponse {


    /**
     * returnValue : 1
     * data : [{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520855884329","replyUserId":null,"nickName":"胡巍-测试用","time":"2018-03-19 16:07:02","id":5,"replyNickName":null,"userId":252699,"content":"很棒棒"}]
     * isLastPage : 1
     * num : 0
     * errMsg : 成功
     */

    private int returnValue;
    private int isLastPage;
    private int num;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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
         * headPicture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520855884329
         * replyUserId : null
         * nickName : 胡巍-测试用
         * time : 2018-03-19 16:07:02
         * id : 5
         * replyNickName : null
         * userId : 252699
         * content : 很棒棒
         */

        private String headPicture;
        private Object replyUserId;
        private String nickName;
        private String time;
        private int id;
        private Object replyNickName;
        private int userId;
        private String content;

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public Object getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(Object replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getReplyNickName() {
            return replyNickName;
        }

        public void setReplyNickName(Object replyNickName) {
            this.replyNickName = replyNickName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
