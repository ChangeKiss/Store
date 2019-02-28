package com.Store.www.ui.imageManager.common;

import java.io.File;
import java.io.Serializable;

/**
 * @author luo.
 * @date 2017/5/10.
 */
public interface Callback extends Serializable {

    void onSingleImageSelected(String path);

    void onImageSelected(String path);

    void onImageUnselected(String path);

    void onCameraShot(File imageFile);

    void onPreviewChanged(int select, int sum, boolean visible);
}
