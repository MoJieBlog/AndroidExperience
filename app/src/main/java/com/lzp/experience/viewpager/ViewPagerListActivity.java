package com.lzp.experience.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzp.base.component.BaseActivity;
import com.lzp.experience.R;
import com.lzp.experience.viewpager.transformer.GalleryTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 18/10/15
 */
public class ViewPagerListActivity extends BaseActivity {

    @BindView(R.id.vp_1)
    ViewPager vp1;
    @BindView(R.id.rl_vp_1)
    RelativeLayout rlVp1;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPagerAdapter mViewPagerAdapter2;
    private int[] mImages = {R.mipmap.yinsuwan, R.mipmap.yinsuwan_1,
            R.mipmap.yinsuwan_2, R.mipmap.yinsuwan_3, R.mipmap.yinsuwan_4, R.mipmap.yinsuwan_5};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_list);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mViewPagerAdapter = new ViewPagerAdapter(this, mImages);
        vp1.setOffscreenPageLimit(3);
        /*vp1.setPageMargin(30);*/
        vp1.setAdapter(mViewPagerAdapter);
        vp1.setPageTransformer(false, new GalleryTransformer(this));

        rlVp1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return vp1.dispatchTouchEvent(event);
            }
        });
    }
}
