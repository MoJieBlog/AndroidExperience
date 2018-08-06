package com.lzp.base.component.permission;

import android.support.v7.app.AppCompatActivity;

import com.lzp.base.component.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li Xiaopeng on 18/7/27.
 */
public class MPermissionActivity extends AppCompatActivity{

    /*权限List*/
    private List<String> permissionList = new ArrayList<>();
    /*权限回调相关*/
    MPermissionListener listener;

    /**
     * 检查是否拥有某权限
     */
    public void checkMPermission(final MPermissionListener listener) {
        this.listener = listener;
        MPermissionUtils.checkPermission(this, permissionList.toArray(new String[permissionList.size()]), this.listener);
    }

    /**
     * 获取权限
     */
    public void getMPermission(List<String> permissionList) {
        MPermissionUtils.getPermission(this, permissionList.toArray(new String[permissionList.size()]));
    }

    /**
     * 是否有权限
     *
     * @return
     */
    public boolean hasPerssion(String permission) {
        return MPermissionUtils.checkPermission(this, permission);
    }

    /**
     * 添加权限
     *
     * @param permission
     */
    public void addPermission(String permission) {
        boolean needAdd = true;
        for (String temp : permissionList) {
            if (temp.equals(permission)) {
                needAdd = false;
                break;
            }
        }
        if (needAdd) {
            permissionList.add(permission);
        }
    }

}
