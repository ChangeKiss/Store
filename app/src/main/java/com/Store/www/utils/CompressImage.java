package com.Store.www.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 压缩图片的方法
 *
 * @author Administrator
 *
 */
public class CompressImage {
	/**
	 * 压缩图片
	 *
	 * @param path
	 */
	public static void compressImage(String path, File iconFile) {
		Bitmap image = BitmapFactory.decodeFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos); // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
			baos.reset(); // 重置baos即清空baos
			image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10; // 每次都减少10
		}
		//LogUtils.e("path.length / 1024="+baos.toByteArray().length / 1024);   //此处打印log可能导致OOM
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		// iconFile = new File(BufferCacheFile.ICON_PATH, "icon.jpg");
		// if (!iconFile.exists()) {
		// iconFile.getParentFile().mkdirs();
		// }
		saveFile(bitmap, iconFile);
	}

	/**
	 * 按比例压缩图片
	 *
	 * @param filePath
	 *            需要压缩图片的路径
	 * @param cachePath
	 *            存放压缩后的图片路径
	 * @param toWidth
	 *            压缩的宽度
	 * @param toHeight
	 *            压缩的高度
	 * @return
	 */

	public static boolean getCacheImage(String filePath, File cachePath,
										int toWidth, int toHeight) {
		OutputStream out = null;
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = false; // 设置为true，只读尺寸信息，不加载像素信息到内存
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, option); // 此时bitmap为空
		option.inJustDecodeBounds = false;
		int bWidth = option.outWidth;
		int bHeight = option.outHeight;
		int be = 1; // be = 1代表不缩放
		if (bWidth / toWidth > bHeight / toHeight && bWidth > toWidth) {
			be = bWidth / toHeight;
		} else if (bHeight / toHeight > bWidth / toWidth && bHeight > toHeight) {
			be = bHeight / toHeight;
		} else if (bHeight / toHeight == bWidth / toWidth
				&& (bHeight > toHeight || bWidth > toWidth)) {
			be = bHeight / toHeight;
		}
		option.inSampleSize = be; // 设置缩放比例
		bitmap = BitmapFactory.decodeFile(filePath, option);
		try {
			out = new FileOutputStream(cachePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bitmap.compress(CompressFormat.JPEG, 50, out)) {
			compressImage(cachePath.getPath(), cachePath);
			return true;
		}
		return false;
	}

	/**
	 * 保存压缩图片
	 *
	 * @param bm
	 * @param file
	 */
	public static void saveFile(Bitmap bm, File file) {
		if (file == null) {
			return;
		}
		File dirFile = file;
		// 检测图片是否存在
		if (dirFile.exists()) {
			dirFile.delete(); // 删除原图片
		}
		try {
			BufferedOutputStream bos;
			bos = new BufferedOutputStream(new FileOutputStream(dirFile));
			// 100表示不进行压缩，70表示压缩率为30%
			bm.compress(CompressFormat.JPEG, 50, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fileDelete(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				// 删除子文件
				if (files[i].isFile()) {
					File deleteFile = new File(files[i].getAbsolutePath());
					// 路径为文件且不为空则进行删除
					if (deleteFile.isFile() && deleteFile.exists()) {
						deleteFile.delete();
					}
				}
			}
		}
	}
}
