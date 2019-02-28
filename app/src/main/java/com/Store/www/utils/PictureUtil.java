package com.Store.www.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 下载图片工具类
 */

public class PictureUtil {
    /**
     * 将drawable里面的图片保存在存储卡
     * @param activity 上下文
     * @param id  drawable里面的图片的id（R.drawable.xxx）
     * @param imagename 本地保存图片的名字
     * @return 返回图片路径
     */
    public static String imagePath(Activity activity, int id, String imagename) {
        String filename = Environment.getExternalStorageDirectory() + File.separator + imagename;
        String imagePath = filename + ".png";
        File file = new File(imagePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            Bitmap pic = BitmapFactory.decodeResource(activity.getResources(), id);
            FileOutputStream fos = new FileOutputStream(file);
            pic.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch(Throwable t) {
            t.printStackTrace();
            imagePath = "";
        }
        return imagePath;

    }
}
