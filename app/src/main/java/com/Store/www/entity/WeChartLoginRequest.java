package com.Store.www.entity;

/**
 * 微信登录响应体
 * @author: haifeng
 * @description:
 */
public class WeChartLoginRequest {

    /**
     * headimgurl : htttps:hfjsf
     * nickname : hhh
     * unionid : hjsdkhfkjshf
     */

    private String headimgurl;
    private String nickname;
    private String unionid;

    public WeChartLoginRequest(String headimgurl, String nickname, String unionid) {
        this.headimgurl = headimgurl;
        this.nickname = nickname;
        this.unionid = unionid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
