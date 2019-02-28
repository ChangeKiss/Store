package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description:  提交下级提货单请求体
 */
public class SubmitSubordinateBillRequest {

    /**
     * userId : 252699
     * cartIds : [29859,29860]
     * code : 123456789
     * typeId : 3
     * password:"jkdfhjkdhgfjkhdjkghd"
     */

    private int userId;
    private String code;
    private int typeId;
    private String password;
    private List<Integer> cartIds;

    public SubmitSubordinateBillRequest(int userId, String code, int typeId, String password, List<Integer> cartIds) {
        this.userId = userId;
        this.code = code;
        this.typeId = typeId;
        this.password = password;
        this.cartIds = cartIds;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getCartIds() {
        return cartIds;
    }

    public void setCartIds(List<Integer> cartIds) {
        this.cartIds = cartIds;
    }
}
