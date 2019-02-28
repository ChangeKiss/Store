package com.Store.www.entity;

/**
 * 新闻详情请求体
 */

public class NewsDetailsRequest {

    /**
     * infoid : 55
     */

    private String infoid;

    public NewsDetailsRequest(String infoid) {
        this.infoid = infoid;
    }

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }
}
