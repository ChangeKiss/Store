package com.Store.www.entity;

/**
 * 意见反馈请求体
 */

public class FeedBackRequest {

    /**
     * userId : 1
     * content : asdasd
     */

    private int userId;
    private String content;

    public FeedBackRequest(int userId, String content) {
        this.userId = userId;
        this.content = content;
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
