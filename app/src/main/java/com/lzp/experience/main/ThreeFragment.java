package com.lzp.experience.main;

import android.os.Bundle;

import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;

/**
 * Created by Li Xiaopeng on 18/5/18.
 */

public class ThreeFragment extends MainBaseFragment implements IBasePage{

    public static ThreeFragment getFragment() {
        ThreeFragment threeFragment = new ThreeFragment();
        return threeFragment;
    }

    @Override
    public void onMCreate(Bundle savedInstanceState) {
        super.onMCreate(savedInstanceState);
        setContentView(R.layout.fragment_three);
    }

    @Override
    public void readArguments(Bundle bundle) {

    }

    @Override
    public void writeArguments(Bundle bundle) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void getData() {

    }
}
