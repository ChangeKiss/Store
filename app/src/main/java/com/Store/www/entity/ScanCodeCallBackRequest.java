package com.Store.www.entity;

/**
 * Created by www on 2018/10/18.
 * 二维码扫描结束回调接口
 */

public class ScanCodeCallBackRequest {

    /**
     * agentCode : K201805822126
     * code : hdjsdh
     * address : "省市区"
     */

    private String agentCode;
    private String code;
    private String address;

    public ScanCodeCallBackRequest(String agentCode, String code, String address) {
        this.agentCode = agentCode;
        this.code = code;
        this.address = address;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
