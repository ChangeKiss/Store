package com.Store.www.entity;

/**
 * 删除评论请求体
 */

public class DeleteCommentRequest {

    /**
     * userId : 252699
     * commentId : 10
     */

    private int userId;
    private int commentId;

    public DeleteCommentRequest(int userId, int commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
