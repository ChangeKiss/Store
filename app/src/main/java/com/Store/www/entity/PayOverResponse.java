package com.Store.www.entity;

/**
 * 支付完成后的消息响应体
 */

public class PayOverResponse {


    /**
     * state : 2
     * message : 正在支付中
     */

    private int state;
    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
