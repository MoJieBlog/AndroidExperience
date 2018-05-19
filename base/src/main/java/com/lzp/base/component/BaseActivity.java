package com.lzp.base.component;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lzp.base.R;
import com.lzp.base.component.actionbar.IMActionbar;
import com.lzp.base.component.actionbar.MActionbar;
import com.lzp.base.component.contentView.IMRootView;
import com.lzp.base.component.contentView.MRootView;
import com.lzp.base.component.statusbar.IMStatusBar;
import com.lzp.base.component.statusbar.MStatusBar;
import com.lzp.base.swipeback.ParallaxHelper;
import com.lzp.base.swipeback.ViewDragHelper;
import com.lzp.base.swipeback.widget.ParallaxBackLayout;
import com.lzp.base.utils.SystemBarTintManager;
import com.lzp.base.utils.SystemUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * Created by Li Xiaopeng on 18/3/22.
 */

public class BaseActivity extends AppCompatActivity implements IMStatusBar, IMActionbar, IMRootView {

    public static final String EXTRA_BUNDLE = "bundle";

    private MRootView mRootView;

    private SystemBarTintManager tintManager;

    private View contentView;
    private int contentLayoutId = MRootView.NO_CONTENT_LAYOUT;

    private IBasePage basePage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEdgeFlag(ViewDragHelper.EDGE_LEFT);
        mRootView = new MRootView(this) {
            @Override
            public int getContentLayoutRes() {
                return contentLayoutId;
            }

            @Override
            public View getContentLayout() {
                return contentView;
            }
        };
        try{
            basePage = (IBasePage) this;
            Intent intent = getIntent();
            if (savedInstanceState != null){
                basePage.readArguments(savedInstanceState);
            }else if (intent.hasExtra(EXTRA_BUNDLE)){
                Bundle bundle = intent.getBundleExtra(EXTRA_BUNDLE);
                basePage.readArguments(bundle);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setEdgeFlag(int edgeFlag) {
        ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(this,true);
        layout.setScrollThresHold(0.2f);
        layout.setEdgeFlag(edgeFlag);
        layout.setEnableGesture(true);
    }

    @Override
    public void setContentView(View view) {
        configeFullScreen();
        contentView = view;
        super.setContentView(mRootView.initRootView());
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        configeFullScreen();
        contentLayoutId = layoutResID;
        super.setContentView(mRootView.initRootView());
        ButterKnife.bind(this);
        if (basePage != null){
            basePage.initView();
            basePage.setListener();
            basePage.getData();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (basePage != null) basePage.writeArguments(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (basePage != null) basePage.readArguments(savedInstanceState);
    }

    private void configeFullScreen() {
        if (SystemUtils.isHUAWEI()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
                setAndroidStatusBarColor(R.color.transparent);
            }


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int ui = window.getDecorView().getSystemUiVisibility();
            ui |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(ui);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            setAndroidStatusBarColor(R.color.transparent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(true);
            setAndroidStatusBarColor(R.color.transparent);
        }
        setStatusIconColor(true);
    }

    private boolean isFlymeSetStatusBarDarkMode;// 魅族设置深色主题是否成功
    private boolean isMIUISetStatusBarDarkMode;     // miui设置深色主题是否成功

    /**
     * 设置状态栏icon颜色
     *
     * @param isLight icon 是否为白色
     */
    private void setStatusIconColor(boolean isLight) {

        if (SystemUtils.isFlyme()) {
            isFlymeSetStatusBarDarkMode = flymeSetStatusBarLightMode(getWindow(), true);
        } else if (SystemUtils.isMIUI()) {
            isMIUISetStatusBarDarkMode = miUISetStatusBarLightMode(getWindow(), true);
        }

        if (!isFlymeSetStatusBarDarkMode && !isMIUISetStatusBarDarkMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 由于魅族和小米系统的定制，在android 6.0的时候，状态栏图标颜色设置不成功
            // 所以首先调用第三方系统提供接口设置图标颜色
            // 若果魅族和小米的状态栏图标设置都没成功
            // 则判断android版本>=23(6.0)设置状态栏图标都为深色
            // 否则都为浅色

            View decor = getWindow().getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (isLight) {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }

    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean flymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean miUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 设置android原生 statusbar 颜色
     */
    public void setAndroidStatusBarColor(int color) {
        if (SystemUtils.isHUAWEI()) {
            if (tintManager != null) {
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(getResources().getColor(color));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(color));
            } else {
                if (tintManager != null) {
                    tintManager.setStatusBarTintEnabled(true);
                    tintManager.setStatusBarTintColor(getResources().getColor(color));
                }
            }
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        int bits = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        getTintManager(this);
    }

    public SystemBarTintManager getTintManager(Activity activity) {
        if (tintManager == null) {
            tintManager = new SystemBarTintManager(activity);
        }
        return tintManager;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onRelease();
    }

    @Override
    public void onRelease() {
        if (mRootView != null) mRootView.onRelease();
    }

    @Override
    public void setStatusBarColor(int color) {
        MStatusBar mStatusBar = mRootView.getMStatusBar();
        if (mStatusBar != null) mStatusBar.setStatusBarColor(color);
    }

    @Override
    public void setStatusBarColorRes(int colorRes) {
        setStatusBarColor(getResources().getColor(colorRes));
    }

    @Override
    public void setStatusBarAlpha(float alpha) {
        MStatusBar mStatusBar = mRootView.getMStatusBar();
        if (mStatusBar != null) mStatusBar.setStatusBarAlpha(alpha);
    }

    @Override
    public void setActionBarBackgroundColor(int color) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarBackgroundColor(color);
    }

    @Override
    public void setActionBarBackgroundColorRes(int colorRes) {
        setActionBarBackgroundColor(getResources().getColor(colorRes));
    }

    @Override
    public void setActionBarRightIconDrawable(Drawable drawable) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarRightIconDrawable(drawable);
    }

    @Override
    public void setActionBarRightIconRes(int drawableRes) {
        setActionBarRightIconDrawable(getResources().getDrawable(drawableRes));
    }

    @Override
    public void setActionBarRightIconVisible(boolean visible) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarRightIconVisible(visible);
    }

    @Override
    public void setActionBarRightTextString(String rightTextStr) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarRightTextString(rightTextStr);
    }

    @Override
    public void setActionBarRightTextRes(int rightTextRes) {
        setActionBarRightTextString(getResources().getString(rightTextRes));
    }

    @Override
    public void setActionBarRightTextVisible(boolean visible) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarRightTextVisible(visible);
    }

    @Override
    public void setActionBarRightTextColor(int color) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarRightTextColor(color);
    }

    @Override
    public void setActionBarRightTextColorRes(int colorRes) {
        setActionBarRightTextColor(getResources().getColor(colorRes));
    }

    @Override
    public void setActionBarLeftIconDrawable(Drawable drawable) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarLeftIconDrawable(drawable);
    }

    @Override
    public void setActionBarLeftIconRes(int drawableRes) {
        setActionBarLeftIconDrawable(getResources().getDrawable(drawableRes));
    }

    @Override
    public void setActionBarLeftIconVisible(boolean visible) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarLeftIconVisible(visible);
    }

    @Override
    public void setActionBarLeftTextString(String leftTextStr) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarLeftTextString(leftTextStr);
    }

    @Override
    public void setActionBarLeftTextRes(int leftTextRes) {
        setActionBarLeftTextString(getResources().getString(leftTextRes));
    }

    @Override
    public void setActionBarLeftTextVisible(boolean visible) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarLeftTextVisible(visible);
    }

    @Override
    public void setActionBarLeftTextColor(int color) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarLeftTextColor(color);
    }

    @Override
    public void setActionBarLeftTextColorRes(int colorRes) {
        setActionBarLeftTextColor(getResources().getColor(colorRes));
    }

    @Override
    public void setActionBarTitle(String titleStr) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarTitle(titleStr);
    }

    @Override
    public void setActionBarTitleRes(int titleRes) {
        setTitle(getResources().getString(titleRes));
    }

    @Override
    public void setActionBarTitleColor(int color) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarTitleColor(color);
    }

    @Override
    public void setActionBarTitleColorRes(int colorRes) {
        setActionBarTitleColor(getResources().getColor(colorRes));
    }

    @Override
    public void setActionBarBottomLineVisible(boolean visible) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarBottomLineVisible(visible);
    }

    @Override
    public void setActionBarBottomLineColor(int color) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar != null) mActionBar.setActionBarBottomLineColor(color);
    }

    @Override
    public void setActionBarBottomLineColorRes(int colorRes) {
        setActionBarBottomLineColor(getResources().getColor(colorRes));
    }

    @Override
    public void setNeedActionBar(boolean need) {
        mRootView.setNeedActionBar(need);
    }

    @Override
    public void setNeedStatusBar(boolean need) {
        mRootView.setNeedStatusBar(need);
    }

    @Override
    public MActionbar getMActionBar() {
        return mRootView.getMActionBar();
    }

    @Override
    public MStatusBar getMStatusBar() {
        return mRootView.getMStatusBar();
    }

    @Override
    public View getContentView() {
        return mRootView.getContentView();
    }

    @Override
    public void setListener(View.OnClickListener listener) {
        MActionbar mActionBar = mRootView.getMActionBar();
        if (mActionBar!=null)mActionBar.setClickListener(listener);
    }
}
