package com.Store.www.entity;

/**
 * 新闻资讯更多的请求体
 */

public class NewsMoreRequest {

    /**
     * typenum : 3
     * page : 1
     */

    private int typenum;
    private int page;
    private int index;

    public NewsMoreRequest(int typenum, int page, int index) {
        this.typenum = typenum;
        this.page = page;
        this.index = index;
    }

    public int getTypenum() {
        return typenum;
    }

    public void setTypenum(int typenum) {
        this.typenum = typenum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
