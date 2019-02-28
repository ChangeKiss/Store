package com.Store.www.permisson;

import java.util.List;

/**
 * 返回的权限被拒绝或被接受时的操作的接口
 */

public interface PermissionListener {
        void onGranted();
        void onDenied(List<String> deniedPermissions);
}
