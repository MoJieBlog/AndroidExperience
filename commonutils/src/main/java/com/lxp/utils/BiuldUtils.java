package com.lxp.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by Li Xiaopeng on 17/10/23.
 */

public class BiuldUtils {
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
}
