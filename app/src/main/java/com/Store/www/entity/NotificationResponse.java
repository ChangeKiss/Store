package com.Store.www.entity;

/**
 * 极光推送通知返回的数据，用来判断跳转到界面 以及接收后台参数
 */

public class NotificationResponse {
    /**
     * "ad_url":"H5地址",
     * "ad_img":"图片地址"
     */
    private String from;
    private String url;
    private String ad_img;
    private String ad_url;
    private int code;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAd_img() {
        return ad_img;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }
}
