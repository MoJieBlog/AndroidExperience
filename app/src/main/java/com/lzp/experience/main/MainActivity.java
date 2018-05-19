package com.lzp.experience.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzp.base.component.BaseActivity;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements IBasePage, View.OnClickListener {

    public static final int CODE_TAB_ONE = 0;
    public static final int CODE_TAB_TWO = 1;
    public static final int CODE_TAB_THREE = 2;

    private static final int CODE_TAB_NON = -1;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    @BindView(R.id.iv_main_tab_one)
    ImageView ivMainTabOne;
    @BindView(R.id.tv_main_tab_one)
    TextView tvMainTabOne;
    @BindView(R.id.ll_main_tab_one)
    LinearLayout llMainTabOne;
    @BindView(R.id.iv_main_tab_two)
    ImageView ivMainTabTwo;
    @BindView(R.id.tv_main_tab_two)
    TextView tvMainTabTwo;
    @BindView(R.id.ll_main_tab_two)
    LinearLayout llMainTabTwo;
    @BindView(R.id.iv_main_tab_three)
    ImageView ivMainTabThree;
    @BindView(R.id.tv_amin_tab_three)
    TextView tvAminTabThree;
    @BindView(R.id.ll_main_tab_three)
    LinearLayout llMainTabThree;

    private int lastIndex = CODE_TAB_NON;
    private int currentTabIndex = CODE_TAB_TWO;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;

    private List<MainBaseFragment> pageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedActionBar(false);
        setNeedStatusBar(false);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

        pageList.clear();
        pageList.add(oneFragment);
        pageList.add(twoFragment);
        pageList.add(threeFragment);
        switchFragment();
        switchTab();
    }

    private void setSelectedTab() {
        switch (currentTabIndex) {
            case CODE_TAB_ONE:
                ivMainTabOne.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab1_selected));
                ivMainTabTwo.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab2_normal));
                ivMainTabThree.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab3_normal));

                tvMainTabOne.setTextColor(getResources().getColor(R.color.theme_tab_selected));
                tvMainTabTwo.setTextColor(getResources().getColor(R.color.theme_tab_unselected));
                tvAminTabThree.setTextColor(getResources().getColor(R.color.theme_tab_unselected));

                break;
            case CODE_TAB_TWO:
                ivMainTabTwo.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab2_selected));
                ivMainTabOne.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab1_normal));
                ivMainTabThree.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab3_normal));

                tvMainTabOne.setTextColor(getResources().getColor(R.color.theme_tab_unselected));
                tvMainTabTwo.setTextColor(getResources().getColor(R.color.theme_tab_selected));
                tvAminTabThree.setTextColor(getResources().getColor(R.color.theme_tab_unselected));
                break;
            case CODE_TAB_THREE:
                ivMainTabThree.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab3_selected));
                ivMainTabTwo.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab2_normal));
                ivMainTabOne.setImageDrawable(getResources().getDrawable(R.mipmap.main_tab1_normal));

                tvMainTabOne.setTextColor(getResources().getColor(R.color.theme_tab_unselected));
                tvMainTabTwo.setTextColor(getResources().getColor(R.color.theme_tab_unselected));
                tvAminTabThree.setTextColor(getResources().getColor(R.color.theme_tab_selected));
                break;
        }
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
            case CODE_TAB_ONE:
                doAnimation(llMainTabOne);
                break;
            case CODE_TAB_TWO:
                doAnimation(llMainTabTwo);
                break;
            case CODE_TAB_THREE:
                doAnimation(llMainTabThree);
                break;
        }
        setSelectedTab();
    }

    private boolean doAnimator = false;

    private void doAnimation(View view) {
        if (!doAnimator) {
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.5f, 0.8f, 1f);
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.2f, 0.8f, 1f);
            set.setDuration(200);
            set.setInterpolator(new AccelerateInterpolator(0.5f));
            set.play(animatorX).with(animatorY);
            set.start();
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    doAnimator = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    doAnimator = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    doAnimator = false;
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main_tab_one:
                currentTabIndex = CODE_TAB_ONE;
                break;
            case R.id.ll_main_tab_two:
                currentTabIndex = CODE_TAB_TWO;
                break;
            case R.id.ll_main_tab_three:
                currentTabIndex = CODE_TAB_THREE;
                break;
        }

        switchFragment();
        switchTab();
    }

    /**
     * 切换Fragment
     */
    private void switchFragment() {
        if (currentTabIndex != lastIndex && lastIndex != CODE_TAB_NON) {//选择了不同的tab，切换
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(pageList.get(lastIndex));
            lastIndex = currentTabIndex;
            MainBaseFragment mainBaseFragment = pageList.get(currentTabIndex);
            if (!mainBaseFragment.isAdded()) {
                transaction.add(R.id.main_container, mainBaseFragment).show(mainBaseFragment).commit();
            } else {
                transaction.show(mainBaseFragment).commit();
            }
        } else if (lastIndex == CODE_TAB_NON) {//首次进入，设置默认显示
            MainBaseFragment mainBaseFragment = pageList.get(currentTabIndex);
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, mainBaseFragment)
                    .show(mainBaseFragment)
                    .commit();
            lastIndex = currentTabIndex;
        } else {//相同tab点击，不做切换
        }
    }

}