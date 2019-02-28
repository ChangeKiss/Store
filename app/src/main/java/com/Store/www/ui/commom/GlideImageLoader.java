package com.Store.www.ui.commom;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by www on 2017/12/15.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load((String) path)
                /*.placeholder(R.mipmap.welcome_two)
                .error(R.mipmap.welcome_one_icon)*/
                .into(imageView);
    }
}
