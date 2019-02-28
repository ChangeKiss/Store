package com.Store.www.entity;

/**
 * 新增评论请求体
 */

public class NewCommentRequest {

    /**
     * userId : 252699
     * circleId : 12
     * content : 评论内容,整型
     * replyUserId : 252699
     */

    private int userId;
    private int circleId;
    private String content;
    //private int replyUserId;  //回复用户ID 先不传

    public NewCommentRequest(int userId, int circleId, String content) {
        this.userId = userId;
        this.circleId = circleId;
        this.content = content;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
