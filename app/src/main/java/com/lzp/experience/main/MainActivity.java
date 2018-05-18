package com.lzp.experience.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzp.base.component.BaseActivity;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements IBasePage, View.OnClickListener {

    @InjectView(R.id.main_container)
    FrameLayout mainContainer;
    @InjectView(R.id.iv_main_tab_one)
    ImageView ivMainTabOne;
    @InjectView(R.id.tv_main_tab_one)
    TextView tvMainTabOne;
    @InjectView(R.id.ll_main_tab_one)
    LinearLayout llMainTabOne;
    @InjectView(R.id.iv_main_tab_two)
    ImageView ivMainTabTwo;
    @InjectView(R.id.tv_main_tab_two)
    TextView tvMainTabTwo;
    @InjectView(R.id.ll_main_tab_two)
    LinearLayout llMainTabTwo;
    @InjectView(R.id.iv_main_tab_three)
    ImageView ivMainTabThree;
    @InjectView(R.id.tv_amin_tab_three)
    TextView tvAminTabThree;
    @InjectView(R.id.ll_main_tab_three)
    LinearLayout llMainTabThree;
    private int currentTabIndex = 0;
    private MainBaseFragment currentFragment;//当前选中的Fragment
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedActionBar(false);
        setNeedStatusBar(false);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setListener();
    }

    @Override
    public void readArguments(Bundle bundle) {
    }

    @Override
    public void writeArguments(Bundle bundle) {
    }

    @Override
    public void initView() {
        if (oneFragment == null) {
            oneFragment = OneFragment.getFragment();
        }
        if (twoFragment == null) {
            twoFragment = TwoFragment.getFragment();
        }
        if (threeFragment == null) {
            threeFragment = ThreeFragment.getFragment();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_container, oneFragment);
        fragmentTransaction.show(oneFragment);
    }

    @Override
    public void setListener() {
        llMainTabOne.setOnClickListener(this);
        llMainTabTwo.setOnClickListener(this);
        llMainTabThree.setOnClickListener(this);
    }

    @Override
    public void getData() {

    }


    private void switchTab() {
        switch (currentTabIndex) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!oneFragment.isAdded()) {
            fragmentTransaction.add(R.id.main_container, oneFragment);
        }
        if (!twoFragment.isAdded()) {
            fragmentTransaction.add(R.id.main_container, twoFragment);
        }
        if (!threeFragment.isAdded()) {
            fragmentTransaction.add(R.id.main_container, threeFragment);
        }
        fragmentTransaction.hide(currentFragment);
        switch (v.getId()) {
            case R.id.ll_main_tab_one:
                currentFragment = oneFragment;
                currentTabIndex = 0;
                switchTab();
                break;
            case R.id.ll_main_tab_two:
                currentFragment = twoFragment;
                currentTabIndex = 1;
                switchTab();
                break;
            case R.id.ll_main_tab_three:
                currentTabIndex = 2;
                currentFragment = threeFragment;
                switchTab();
                break;
        }
        fragmentTransaction.show(currentFragment);
        fragmentTransaction.commit();
    }

}