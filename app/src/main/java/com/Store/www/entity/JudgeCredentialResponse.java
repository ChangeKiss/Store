package com.Store.www.entity;

/**
 * 判断用户是否有证书的响应体
 */

public class JudgeCredentialResponse {

    /**
     * returnValue : 1
     * errMsg : 成功
     * cert : http://jwbucket.oss-cn-shanghai.aliyuncs.com/20180312K20180201822125.jpg
     * isCert : 1
     */

    private int returnValue;
    private String errMsg;
    private String cert;
    private int isCert;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public int getIsCert() {
        return isCert;
    }

    public void setIsCert(int isCert) {
        this.isCert = isCert;
    }
}
