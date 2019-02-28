package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/2/24.
 */

public class AllCartRequest {

    /**
     * userId : 252699
     * productIds : [1,2,3,4,5]
     */

    private String userId;
    private List<Integer> productIds;

    public AllCartRequest(String userId, List<Integer> productIds) {
        this.userId = userId;
        this.productIds = productIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }
}
