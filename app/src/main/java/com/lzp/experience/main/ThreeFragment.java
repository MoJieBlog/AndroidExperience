package com.lzp.experience.main;

import android.os.Bundle;
import android.util.Log;

import com.lxp.utils.LogUtils;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Li Xiaopeng on 18/5/18.
 */

public class ThreeFragment extends MainBaseFragment implements IBasePage {

    private static final String TAG = "ThreeFragment";
    public static ThreeFragment getFragment() {
        ThreeFragment threeFragment = new ThreeFragment();
        return threeFragment;
    }

    @Override
    public void onMCreate(Bundle savedInstanceState) {
        super.onMCreate(savedInstanceState);
        setNeedActionBar(false);
        setNeedStatusBar(false);
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
