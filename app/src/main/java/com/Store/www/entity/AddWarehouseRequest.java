package com.Store.www.entity;

/**
 * Created by www on 2018/10/10.
 * 添加实体仓库请求体
 */

public class AddWarehouseRequest {

    /**
     * province : 省, 字符串
     * city : 市, 字符串
     * area : 区, 字符串
     * address : 详细地址, 字符串
     * warehouseName : 仓库名称，字符串
     * agentId : 用户id
     */

    private String agentId;
    private String province;
    private String city;
    private String area;
    private String address;
    private String warehouseName;

    public AddWarehouseRequest(String agentId, String province, String city, String area, String address, String warehouseName) {
        this.agentId = agentId;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.warehouseName = warehouseName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
