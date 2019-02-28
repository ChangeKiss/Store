package com.Store.www.entity;

/**
 * 点赞请求体
 */

public class PraiseRequest {

    /**
     * userId : 252699
     * circleId : 2
     */

    private int userId;
    private int circleId;

    public PraiseRequest(int userId, int circleId) {
        this.userId = userId;
        this.circleId = circleId;
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
}
