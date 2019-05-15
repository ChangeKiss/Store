package com.Store.www.base.CustomScrollView;

/**
 * @author: haifeng
 * @description:
 */

public interface RefreshListener {

    void startRefresh(); //刷新

    void loadMore();  //加载

    void hintChange(String hint);  //提示文字

    void setWidthX(int x);  //设置x

}

