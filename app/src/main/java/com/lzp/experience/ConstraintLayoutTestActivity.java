package com.lzp.experience;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lzp.base.component.BaseActivity;

/**
 * @author Li Xiaopeng
 * @date 18/9/17
 */
public class ConstraintLayoutTestActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedActionBar(false);
        setNeedStatusBar(false);
        setContentView(R.layout.activity_constrainlayout_test);
    }
}
