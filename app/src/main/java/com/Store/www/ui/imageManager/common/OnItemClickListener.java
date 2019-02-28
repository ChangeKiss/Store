package com.Store.www.ui.imageManager.common;



import com.Store.www.ui.imageManager.bean.Image;

/**
 * @author luo.
 * @date 2017/5/10.
 */
public interface OnItemClickListener {

    int onCheckedClick(int position, Image image);

    void onImageClick(int position, Image image);
}
