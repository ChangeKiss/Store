package com.Store.www.entity;

/**
 * 添加银行卡的请求体
 */

public class AddBankCardRequest {


    /**
     * name : ������
     * cardNumber : 6228480318248484376
     * i : /WNSuf+Av0Kk2Fbe8Kyg+447sX3QbU6lqAoVhGx8pECleb/aDXU=
     * agentId : 822125
     * h : /WFSuv+Bv0Gk11bd8N6g5o5BsRLQbk6qqA8V4mwYpEelcb/T
     * createTime : 20180625084441
     */

    private String name;
    private String cardNumber;
    private String i;
    private String agentId;
    private String h;
    private String createTime;


    public AddBankCardRequest(String name, String cardNumber, String i, String agentId, String h, String createTime) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.i = i;
        this.agentId = agentId;
        this.h = h;
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
