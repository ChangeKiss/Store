package com.Store.www.entity;

import java.util.List;

/**
 * 提交订单的请求体
 */

public class SubmitOrderRequest {

    /**
     * cartIds : [129699]
     * userId : 252699
     * totalPrice : 2
     * typeId : 1
     * natureId : 1
     * orderType : 1
     * city : 北京市市辖区
     * area : 西城区
     * address : 天安门广场
     * mobilephone : 18857738393
     * province : 北京市
     * receiveName : 胡2
     * receiver : JW
     * repositoryType : 1
     * isPutCloud：0 是否存入云库 0正常发货 1存入云库
     */

    private String userId;
    private int totalPrice;
    private int typeId;
    private int natureId;
    private int orderType;
    private String city;
    private String area;
    private String address;
    private String mobilephone;
    private String province;
    private String receiveName;
    private String receiver;
    private String repositoryType;
    private String remark;
    private int isPutCloud;
    private List<Integer> cartIds;

    public SubmitOrderRequest(String userId, int totalPrice, int typeId, int natureId, int orderType, String city, String area, String address, String mobilephone, String province, String receiveName, String receiver, String repositoryType, String remark, int isPutCloud, List<Integer> cartIds) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.typeId = typeId;
        this.natureId = natureId;
        this.orderType = orderType;
        this.city = city;
        this.area = area;
        this.address = address;
        this.mobilephone = mobilephone;
        this.province = province;
        this.receiveName = receiveName;
        this.receiver = receiver;
        this.repositoryType = repositoryType;
        this.remark = remark;
        this.isPutCloud = isPutCloud;
        this.cartIds = cartIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getNatureId() {
        return natureId;
    }

    public void setNatureId(int natureId) {
        this.natureId = natureId;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(String repositoryType) {
        this.repositoryType = repositoryType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsPutCloud() {
        return isPutCloud;
    }

    public void setIsPutCloud(int isPutCloud) {
        this.isPutCloud = isPutCloud;
    }

    public List<Integer> getCartIds() {
        return cartIds;
    }

    public void setCartIds(List<Integer> cartIds) {
        this.cartIds = cartIds;
    }

}
