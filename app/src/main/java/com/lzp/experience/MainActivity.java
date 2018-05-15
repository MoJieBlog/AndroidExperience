package com.lzp.experience;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.lzp.base.component.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNeedActionBar(true);
        setNeedStatusBar(true);

        setStatusBarColor(Color.parseColor("#ffd700"));
        setActionbarBackgroundColor(Color.parseColor("#ffa500"));

        setActionBarTitle("测试标题");
        setActionBarTitleColor(Color.parseColor("#e60012"));
        setRightTextVisible(true);
        setRightTextString("测试");

        setLeftIconVisible(true);
        setBottomLineVisible(true);
        setBottomLineColor(Color.parseColor("#e60012"));

        setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title:
                break;
            case R.id.tv_right:
                break;
            case R.id.iv_left_icon:
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}