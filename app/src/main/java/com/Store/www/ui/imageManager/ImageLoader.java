package com.Store.www.ui.imageManager;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * @author luo.
 * @date 2017/5/10.
 */
public interface ImageLoader extends Serializable {
    void displayImage(Context context, String path, ImageView imageView);
}