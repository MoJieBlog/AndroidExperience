package com.lzp.experience.desiginstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzp.base.component.BaseActivity;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;
import com.lzp.experience.main.ThreeFragment;
import com.lzp.experience.main.TwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 2018/12/18
 */
public class TBIndexActivity extends BaseActivity implements IBasePage {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_left_icon)
    ImageButton ivLeftIcon;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right_icon)
    ImageButton ivRightIcon;
    @BindView(R.id.bottom_line)
    View bottomLine;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    private String[] tabs = {"tab1","tab2"};
    private Fragment[] fragments;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedActionBar(false);
        setNeedStatusBar(false);
        setContentView(R.layout.activity_tb_index);
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
        bottomLine.setVisibility(View.GONE);
        tvTitle.setText("测试");
        rlTitle.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        fragments = new Fragment[2];
        fragments[0] = new TwoFragment();
        fragments[1] = new ThreeFragment();
        FragmentStatePagerAdapter pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return fragments[position];
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        viewpager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public void setListener() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                //300 ===》 取值你想多高时开始完全显示title
                rlTitle.setAlpha(Math.abs(i * 1.0f) / 300);
            }
        });
    }

    @Override
    public void getData() {

    }
}
