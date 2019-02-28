package com.Store.www.entity;


/**
 * Created by www on 2018/6/16.
 * 京东支付请求体
 */

public class JDPayRequest {
    /**
     * mobilephone : 13588032842
     * cardId : 16   银行卡ID
     * type : 0   银行卡类型
     * idNumber : 330211199501010053  身份证号码
     * code : dd   信用卡安全码
     * orderNumber : 订单号
     * mobilephone : 手机号
     * idNumber : 身份证
     * exp : 信用卡有效期
     */

    private int cardId;
    private String code;
    private String orderNumber;
    private String exp;

    public JDPayRequest( int cardId, String code, String orderNumber, String exp) {
        this.cardId = cardId;
        this.code = code;
        this.orderNumber = orderNumber;
        this.exp = exp;
    }



    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
