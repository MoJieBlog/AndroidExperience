package com.lxp.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class PermissionUtils {
    //andrpoid 6.0 需要写运行时权限
    public static void checkPermison(Activity activity, String[] permissions, PermissionListener listener) {

        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), 1);
            listener.onDenied(permissionList);
        } else {
            listener.onGranted();
        }
    }

    public interface PermissionListener {
        /**
         * 成功获取权限
         */
        void onGranted();

        /**
         * 为获取权限
         *
         * @param deniedPermission
         */
        void onDenied(List<String> deniedPermission);

    }
}
