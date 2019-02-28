package com.Store.www.entity;

/**
 * Created by www on 2018/8/27.
 */

public class AppUpDataResponse {

    /**
     * versionName : 2.6.1
     * versionCode : 24
     * content : 1.新增了新功能介绍界面。 2.修复了一些导致app产生崩溃的bug。
     * minSupport : 1
     * forceUpdate 0 正常更新  1 强制更新
     * url : http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180815090619.6.1修复BUG 发布版.apk
     */

    private String versionName;
    private int versionCode;
    private String content;
    private int minSupport;
    private int forceUpdate;
    private String url;
    private String apkName;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(int minSupport) {
        this.minSupport = minSupport;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }
}
