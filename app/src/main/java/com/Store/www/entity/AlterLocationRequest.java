package com.Store.www.entity;

/**
 * 修改地址请求体
 */

public class AlterLocationRequest {

    /**
     * receiveName : 测试用
     * phone : 18857738393
     * addressId : 570937
     * address : 浙江-温州-鹿城区-qqq
     * userId : 252699
     * province : 浙江省
     * country : 中国
     * city : 宁波市
     * street : 江东区
     * isUsed : 0
     */

    private String receiveName;
    private String phone;
    private int addressId;
    private String address;
    private String userId;
    private String province;
    private String country;
    private String city;
    private String street;
    private int isUsed;

    public AlterLocationRequest(String receiveName, String phone, int addressId, String address, String userId, String province, String country, String city, String street, int isUsed) {
        this.receiveName = receiveName;
        this.phone = phone;
        this.addressId = addressId;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
