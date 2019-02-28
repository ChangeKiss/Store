package com.Store.www.entity;

/**
 * Created by www on 2018/10/10.
 * 修改实体仓库信息请求体
 */

public class AlterEntityWarehouseRequest {

    /**
     * agentId : 822126
     * id : 仓库编号, 整型
     * province : 省, 字符串
     * city : 市, 字符串
     * area : 区, 字符串
     * address : 详细地址, 字符串
     * warehouseName : 仓库名称，字符串
     */

    private int agentId;
    private int id;
    private String province;
    private String city;
    private String area;
    private String address;
    private String warehouseName;

    public AlterEntityWarehouseRequest(int agentId, int id, String province, String city, String area, String address, String warehouseName) {
        this.agentId = agentId;
        this.id = id;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.warehouseName = warehouseName;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
