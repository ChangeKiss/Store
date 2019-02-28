package com.Store.www.entity;

/**
 * 添加新地址的请求体
 */

public class AddNewLocationRequest {

    /**
     * receiveName : 测试
     * phone : 13500000000
     * address : 测试一下
     * userId : 252699
     * province : 北京市
     * country : 中国
     * city : 北京市市辖区
     * street : 东城区
     * isUsed : 1
     */

    private String receiveName;
    private String phone;
    private String address;
    private String userId;
    private String province;
    private String country;
    private String city;
    private String street;
    private int isUsed;

    public AddNewLocationRequest(String receiveName, String phone, String address, String userId, String province, String country, String city, String street, int isUsed) {
        this.receiveName = receiveName;
        this.phone = phone;
        this.address = address;
        this.userId = userId;
        this.province = province;
        this.country = country;
        this.city = city;
        this.street = street;
        this.isUsed = isUsed;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }
}
