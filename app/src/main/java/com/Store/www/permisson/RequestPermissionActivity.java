package com.Store.www.permisson;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.Store.www.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

//获取权限
public class RequestPermissionActivity extends BaseActivity{

    private static final int REQUEST_CODE = 1;
    private PermissionListener mListener;

    public void requestPermissions(String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for(String permission : permissions) {
            if(ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if(!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionList.toArray(new String[permissionList.size()]),
                    REQUEST_CODE);
        }else {
            mListener.onGranted();
        }
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length > 0) {
                    List<String> deniedPermission = new ArrayList<>();
                    for(int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        if(grantResult == PackageManager.PERMISSION_DENIED) {
                            deniedPermission.add(permissions[i]);
                        }
                    }
                    if(deniedPermission.isEmpty()) {
                        mListener.onGranted();
                    }else {
                        mListener.onDenied(deniedPermission);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int initLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }
}