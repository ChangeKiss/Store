package com.Store.www.entity;

/**
 * Created by www on 2018/9/1.
 * 同意升级请求体
 */

public class ConsentUpRequest {

    /**
     * id : 2
     */

    private int id;

    public ConsentUpRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
