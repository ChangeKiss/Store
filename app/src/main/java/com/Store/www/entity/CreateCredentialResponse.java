package com.Store.www.entity;

/**
 * 生成证书响应体
 */

public class CreateCredentialResponse {

    /**
     * returnValue : 1
     * data : http://jwbucket.oss-cn-shanghai.aliyuncs.com/1520822133992K20180201822125.jpg
     * errMsg : null
     */

    private int returnValue;
    private String data;
    private String errMsg;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
