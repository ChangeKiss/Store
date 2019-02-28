package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 塑身衣证书响应体
 */
public class ShapeWearCredentialResponse {

    /**
     * returnValue : 1
     * errMsg : 成功
     * cert : http://jwbucket.oss-cn-shanghai.aliyuncs.com/1545202905595K20180201822125ISHOW.jpg
     * isIShowCert : 1
     */

    private int returnValue;
    private String errMsg;
    private String cert;
    private int isIShowCert;

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

    public int getIsIShowCert() {
        return isIShowCert;
    }

    public void setIsIShowCert(int isIShowCert) {
        this.isIShowCert = isIShowCert;
    }
}
