package com.Store.www.entity;

import java.util.List;

/**
 * 代理人申请请求体
 */

public class AgentApplyRequest {

    /**
     * qq : 测试
     * updateInfo : []
     * userId : 252699
     * wechat : 测试
     * weibo : 测试
     * ID : 测试
     * EMAIL : 测试
     * experience : 测试
     */

    private String qq;
    private String userId;
    private String wechat;
    private String weibo;
    private String ID;
    private String EMAIL;
    private String experience;
    private List<String> updateInfo;

    public AgentApplyRequest(String qq, String userId, String wechat, String weibo, String ID, String EMAIL, String experience, List<String> updateInfo) {
        this.qq = qq;
        this.userId = userId;
        this.wechat = wechat;
        this.weibo = weibo;
        this.ID = ID;
        this.EMAIL = EMAIL;
        this.experience = experience;
        this.updateInfo = updateInfo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<String> getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(List<String> updateInfo) {
        this.updateInfo = updateInfo;
    }
}
