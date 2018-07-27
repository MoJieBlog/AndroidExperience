package com.lzp.base.component.permission;

import android.Manifest;

/**
 * 常见权限
 * Created by Li Xiaopeng on 18/7/27.
 */
public class MPermissionManager {

    /**
     * 读取手机信息
     */
    public static String getPhoneState(){
        return Manifest.permission.READ_PHONE_STATE;
    }
    /**
     * ACCESS_FINE_LOCATION
     */
    public static String getFineLocation(){
        return Manifest.permission.ACCESS_FINE_LOCATION;
    }

    public static String getAccessCoarseLocation(){
        return Manifest.permission.ACCESS_COARSE_LOCATION;
    }

    public static String getWriteExternalStorage(){
        return Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

    public static String getReadExternalStorage(){
        return Manifest.permission.READ_EXTERNAL_STORAGE;
    }
    public static String getAccessWifiState(){
        return Manifest.permission.ACCESS_WIFI_STATE;
    }
    public static String getWriteSetting(){
        return Manifest.permission.WRITE_SETTINGS;
    }
    public static String getCamera(){
        return Manifest.permission.CAMERA;
    }
    public static String getAccounts(){
        return Manifest.permission.GET_ACCOUNTS;
    }
    public static String getReadContacts(){
        return Manifest.permission.READ_CONTACTS;
    }
}
