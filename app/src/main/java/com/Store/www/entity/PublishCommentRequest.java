package com.Store.www.entity;

/**
 * 发表评论请求体
 */

public class PublishCommentRequest {

    /**
     * userId : 252699
     * content : 内容，字符串
     * textId : 10
     */

    private int userId;
    private String content;
    private int textId;

    public PublishCommentRequest(int userId, String content, int textId) {
        this.userId = userId;
        this.content = content;
        this.textId = textId;
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

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }
}
