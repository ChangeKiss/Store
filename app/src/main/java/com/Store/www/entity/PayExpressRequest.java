package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 支付运费请求体
 */
public class PayExpressRequest {

    /**
     * no : TH201812248328
     * payType : 0
     */

    private String no;
    private int payType;

    public PayExpressRequest(String no, int payType) {
        this.no = no;
        this.payType = payType;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
