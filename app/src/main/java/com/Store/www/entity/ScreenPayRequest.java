package com.Store.www.entity;

import java.util.List;

/**
 * 上传截图支付请求体
 */

public class ScreenPayRequest {

    /**
     * orderNo : DD20171218216983
     * payInfo : ["http://jwbucket.oss-cn-shanghai.aliyuncs.com/252699_DD20171218216983_payInfoimage.jpg"]
     * userId : 252699
     * money : 2
     * payType : 1
     */

    private String orderNo;
    private String userId;
    private int money;
    private int payType;
    private List<String> payInfo;

    public ScreenPayRequest(String orderNo, String userId, int money, int payType, List<String> payInfo) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.money = money;
        this.payType = payType;
        this.payInfo = payInfo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public List<String> getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(List<String> payInfo) {
        this.payInfo = payInfo;
    }
}
