package com.Store.www.entity;

/**
 * 删除地址请求体
 */

public class DeleteAddressRequest {

    /**
     * addressId : 574484
     * userId : 252699
     */

    private int addressId;
    private String userId;

    public DeleteAddressRequest(int addressId, String userId) {
        this.addressId = addressId;
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
