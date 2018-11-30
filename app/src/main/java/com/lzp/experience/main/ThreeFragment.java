package com.lzp.experience.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.lxp.utils.LogUtils;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;
import com.lzp.experience.widgt.RoundRectImageView;
import com.lzp.experience.textpath.WaveTextPathView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Li Xiaopeng on 18/5/18.
 */

public class ThreeFragment extends MainBaseFragment implements IBasePage {

    private static final String TAG = "ThreeFragment";
    @BindView(R.id.riv)
    RoundRectImageView riv;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    Unbinder unbinder;
    @BindView(R.id.waveView)
    WaveTextPathView waveView;

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
        LogUtils.logE(TAG, "onMCreate: "+this.getClass().getSimpleName());
    }

    @Override
    public void readArguments(Bundle bundle) {

    }

    @Override
    public void writeArguments(Bundle bundle) {

    }

    @Override
    public void initView() {
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtils.logE(TAG, "onProgressChanged: " + progress);
                riv.setCurrentR(progress);
                waveView.setCurrentR(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void setListener() {

    }

    @Override
    public void getData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
