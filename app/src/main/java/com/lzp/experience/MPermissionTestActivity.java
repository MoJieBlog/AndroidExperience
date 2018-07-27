package com.lzp.experience;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.lxp.utils.ToastUtils;
import com.lzp.base.component.permission.MPermissionActivity;
import com.lzp.base.component.permission.MPermissionListener;
import com.lzp.base.component.permission.MPermissionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Li Xiaopeng on 18/7/27.
 */
public class MPermissionTestActivity extends MPermissionActivity {

    @BindView(R.id.get_permission)
    Button getPermission;
    @BindView(R.id.check_permission)
    Button checkPermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        ButterKnife.bind(this);

        getPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMPermission();
            }
        });


        checkPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = hasPerssion(MPermissionManager.getWriteExternalStorage());
                if (b){
                    ToastUtils.setToast(MPermissionTestActivity.this, "拥有权限");
                }else{
                    ToastUtils.setToast(MPermissionTestActivity.this, "没有权限");
                }
            }
        });
    }

    private void getMPermission() {
        addPermission(MPermissionManager.getWriteExternalStorage());
        addPermission(MPermissionManager.getReadExternalStorage());

        checkMxPermission(new MPermissionListener() {
            @Override
            public void onGranted() {
                ToastUtils.setToast(MPermissionTestActivity.this, "拥有权限");
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                ToastUtils.setToast(MPermissionTestActivity.this, "没有权限");
            }

            @Override
            public void onGetPermissionSuccess() {
                ToastUtils.setToast(MPermissionTestActivity.this, "成功获取权限");
            }

            @Override
            public void onGetPermissionFail(List<String> deniedPermissions) {
                ToastUtils.setToast(MPermissionTestActivity.this, "获取权限失败");
            }
        });
    }
}
