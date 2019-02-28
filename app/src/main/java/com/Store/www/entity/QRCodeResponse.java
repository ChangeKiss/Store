package com.Store.www.entity;

/**
 * 获取二维码的响应体
 */

public class QRCodeResponse extends BaseBenTwo{

    /**
     * code : http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699.png
     * shareUrl : http://101.37.21.21:8017/jw-message/html/web/myEwm.html?userId=252699
     */

    private String code;
    private String shareUrl;
    private String context;
    private String title;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
