package com.lxp.utils;

import android.util.Log;

/**
 * Created by Li Xiaopeng on 17/8/24.
 */

public class LogUtils {
    /**
     * VERBOSE日志形式的标识符
     */
    public static final int VERBOSE = 5;
    /**
     * DEBUG日志形式的标识符
     */
    public static final int DEBUG = 4;
    /**
     * INFO日志形式的标识符
     */
    public static final int INFO = 3;
    /**
     * WARN日志形式的标识符
     */
    public static final int WARN = 2;
    /**
     * ERROR日志形式的标识符
     */
    public static final int ERROR = 1;

    public static final int LEVER = 0;

    public static void logE(String TAG,String msg){
        if (LEVER<ERROR){
            Log.e(TAG,msg);
        }
    }

    public static void logV(String TAG,String msg){
        if (LEVER<VERBOSE){
            Log.v(TAG,msg);
        }
    }

    public static void logD(String TAG,String msg){
        if (LEVER<DEBUG){
            Log.d(TAG,msg);
        }
    }

    public static void logI(String TAG,String msg){
        if (LEVER<INFO){
            Log.i(TAG,msg);
        }
    }
    public static void logW(String TAG,String msg){
        if (LEVER<WARN){
            Log.w(TAG,msg);
        }
    }

}
