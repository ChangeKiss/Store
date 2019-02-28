package com.Store.www.entity;

/**
 * Created by www on 2018/9/1.
 * 拒绝升级请求体
 */

public class RejectUpRequest {

    /**
     * id : 2
     * reason : 拒绝理由, 字符串
     */

    private int id;
    private String reason;

    public RejectUpRequest(int id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
