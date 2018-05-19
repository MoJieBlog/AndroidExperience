package com.lzp.experience.main;

import android.os.Bundle;

import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;

/**
 * Created by Li Xiaopeng on 18/5/18.
 */

public class OneFragment extends MainBaseFragment implements IBasePage{

    public static OneFragment getFragment() {
        OneFragment oneFragment = new OneFragment();
        return oneFragment;
    }

    @Override
    public void onMCreate(Bundle savedInstanceState) {
        super.onMCreate(savedInstanceState);
        setContentView(R.layout.fragment_one);
    }

    @Override
    public void readArguments(Bundle bundle) {

    }

    @Override
    public void writeArguments(Bundle bundle) {

    }

    @Override
    public void initView() {
        initActionBar();
    }

    private void initActionBar() {
        setStatusBarColorRes(R.color.theme);
        setActionBarBackgroundColorRes(R.color.theme);
        setActionBarTitleRes(R.string.app_name);
        setActionBarTitleColorRes(R.color.ffffff);
        setActionBarBottomLineVisible(false);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void getData() {

    }
}
