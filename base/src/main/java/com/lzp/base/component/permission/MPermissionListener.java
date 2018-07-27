package com.lzp.base.component.permission;

import java.util.List;

/**
 * 权限监听
 * Created by Li Xiaopeng on 18/7/27.
 */
public interface MPermissionListener {

    /**
     * 拥有权限MxPermissionUtils.java
     */
    void onGranted();

    /**
     * 没有权限
     *
     * @param deniedPermissions 拒绝的权限的列表
     */
    void onDenied(List<String> deniedPermissions);

    /**
     * 请求权限成功
     */
    void onGetPermissionSuccess();

    /**
     * 请求权限失败
     */
    void onGetPermissionFail(List<String> deniedPermissions);

}
