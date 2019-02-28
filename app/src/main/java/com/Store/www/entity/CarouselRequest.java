package com.Store.www.entity;

/**
 * 轮播图的请求体
 */

public class CarouselRequest {

    /**
     * infoid : 42
     */

    private int infoid;

    public CarouselRequest(int infoid) {
        this.infoid = infoid;
    }

    public int getInfoid() {
        return infoid;
    }

    public void setInfoid(int infoid) {
        this.infoid = infoid;
    }
}
