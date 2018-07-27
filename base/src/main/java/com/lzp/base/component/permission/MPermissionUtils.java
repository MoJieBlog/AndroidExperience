package com.lzp.base.component.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具
 * Created by Li Xiaopeng on 18/7/27.
 */
public class MPermissionUtils {

    public static final int CODE_PERMISSION_REQUEST = 1111;

    /**
     * 检查权限
     *
     * @param activity
     * @param permissions 权限集合
     * @param listener
     */
    public static void checkPermission(Activity activity, String[] permissions, MPermissionListener listener) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        if (deniedPermissions.isEmpty()) {
            listener.onGranted();
        } else {
            listener.onDenied(deniedPermissions);
        }
    }

    /**
     * 检查是否拥有单一权限
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context context,String permission){
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    /**
     * 获取权限
     *
     * @param activity
     * @param permissions
     */
    public static void getPermission(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, CODE_PERMISSION_REQUEST);
    }


    /**
     * 获取权限后这里处理结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @return
     */
    public static void onRequestPermissionsResult(Activity activity,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, MPermissionListener listener) {
        if (requestCode == CODE_PERMISSION_REQUEST) {
            List<String> deniedPermissions = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission);
                }
            }
            if (deniedPermissions.isEmpty()) {
                if (listener!=null)listener.onGetPermissionSuccess();
            } else {
                if (listener!=null)listener.onGetPermissionFail(deniedPermissions);
            }
        }
    }


}
