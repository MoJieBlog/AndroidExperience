package com.lzp.experience.desiginstudy;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lxp.utils.LogUtils;
import com.lzp.base.component.BaseActivity;
import com.lzp.experience.R;

/**
 * @author Li Xiaopeng
 * @date 2018/12/20
 */
public class PersonActivity extends BaseActivity{
    private static final String TAG = "PersonActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedStatusBar(false);
        setNeedActionBar(false);
        setContentView(R.layout.activity_person);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View decorView = getWindow().getDecorView();
            //重点：SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/

        int i=0;
        switch (i){
            case 0:
                if (true){
                    LogUtils.logE(TAG, "initView: 返回了");
                    LogUtils.logE(TAG, "initView: 66666666");
                    return;
                }
                LogUtils.logE(TAG, "initView: 7777777777");
                break;
        }

        LogUtils.logE(TAG, "initView: 返回失败");
    }
}
