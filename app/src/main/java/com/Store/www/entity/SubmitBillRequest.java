package com.Store.www.entity;

import java.util.List;

/**
 * 提交提货篮的请求体
 */

public class SubmitBillRequest {


    /**
     * cartIds : [29859,29860]
     * userId : 252699
     * typeId : 1
     * orderType : 1
     * city : 北京市市辖区
     * area : 东城区
     * address : 测试一下得到的却上班就是看看究竟是你
     * mobilephone : 13500000000
     * province : 北京市
     * receiveName : 测试
     */

    private String userId;
    private int typeId;
    private int orderType;
    private String city;
    private String area;
    private String address;
    private String mobilephone;
    private String province;
    private String receiveName;
    private List<Integer> cartIds;

    public SubmitBillRequest(String userId, int typeId, int orderType, String city, String area, String address, String mobilephone, String province, String receiveName, List<Integer> cartIds) {
        this.userId = userId;
        this.typeId = typeId;
        this.orderType = orderType;
        this.city = city;
        this.area = area;
        this.address = address;
        this.mobilephone = mobilephone;
        this.province = province;
        this.receiveName = receiveName;
        this.cartIds = cartIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public List<Integer> getCartIds() {
        return cartIds;
    }

    public void setCartIds(List<Integer> cartIds) {
        this.cartIds = cartIds;
    }
}
