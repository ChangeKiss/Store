package com.Store.www.entity;

/**
 * 设置付款信息的请求体
 */

public class SetMyPayContentRequest {

    /**
     * userId : 252699
     * prompt : asjhfkjsad
     */

    private int userId;
    private String prompt;

    public SetMyPayContentRequest(int userId, String prompt) {
        this.userId = userId;
        this.prompt = prompt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
