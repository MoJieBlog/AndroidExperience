package com.lzp.base.component;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzp.base.component.actionbar.IMActionbar;
import com.lzp.base.component.actionbar.MActionbar;
import com.lzp.base.component.contentView.IMRootView;
import com.lzp.base.component.contentView.MRootView;
import com.lzp.base.component.statusbar.IMStatusBar;
import com.lzp.base.component.statusbar.MStatusBar;

/**
 * Created by Li Xiaopeng on 18/5/14.
 */

public class BaseFragment extends Fragment implements IMRootView, IMStatusBar, IMActionbar {

    protected Context mContext;
    protected Resources mResources;

    private MRootView rootView;
    private int contentLayoutId = MRootView.NO_CONTENT_LAYOUT;
    private View contentLayout = null;

    private IBasePage mBasePage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mResources = context.getResources();

        rootView = new MRootView(context) {
            @Override
            public int getContentLayoutRes() {
                return contentLayoutId;
            }

            @Override
            public View getContentLayout() {
                return contentLayout;
            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onMCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        try {
            mBasePage = (IBasePage) this;
            if (savedInstanceState != null){
                mBasePage.readArguments(savedInstanceState);
            }else{
                Bundle bundle = getArguments();
                if (bundle != null){
                    mBasePage.readArguments(bundle);
                }
            }

            mBasePage.initView();
            mBasePage.setListener();
            mBasePage.getData();

        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView.getRootView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBasePage != null) mBasePage.writeArguments(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mBasePage != null && savedInstanceState != null) mBasePage.readArguments(savedInstanceState);
    }


    private void onMCreate(Bundle savedInstanceState) {

    }

    public void setContentView(View view) {
        contentLayout = view;
        rootView.initRootView();
    }

    public void setContentView(int layoutId) {
        contentLayoutId = layoutId;
        rootView.initRootView();
    }

    @Override
    public void onRelease() {
        rootView.onRelease();
    }

    @Override
    public void setStatusBarColor(int color) {
        rootView.getMStatusBar().setStatusBarColor(color);
    }

    @Override
    public void setStatusBarColorRes(int colorRes) {
        setStatusBarColor(mResources.getColor(colorRes));
    }

    @Override
    public void setStatusBarAlpha(float alpha) {
        rootView.getMStatusBar().setStatusBarAlpha(alpha);
    }

    @Override
    public void setActionbarBackgroundColor(int color) {
        rootView.getMActionBar().setActionbarBackgroundColor(color);
    }

    @Override
    public void setActionbarBackgroundColorRes(int colorRes) {
        rootView.getMActionBar().setActionbarBackgroundColorRes(colorRes);
    }

    @Override
    public void setRightIconDrawable(Drawable drawable) {
        rootView.getMActionBar().setRightIconDrawable(drawable);
    }

    @Override
    public void setRightIconRes(int drawableRes) {
        rootView.getMActionBar().setRightIconRes(drawableRes);
    }

    @Override
    public void setRightIconVisible(boolean visible) {
        rootView.getMActionBar().setRightIconVisible(visible);
    }

    @Override
    public void setRightTextString(String rightTextStr) {
        rootView.getMActionBar().setRightTextString(rightTextStr);
    }

    @Override
    public void setRightTextRes(int rightTextRes) {
        rootView.getMActionBar().setRightTextRes(rightTextRes);
    }

    @Override
    public void setRightTextVisible(boolean visible) {
        rootView.getMActionBar().setRightTextVisible(visible);
    }

    @Override
    public void setRightTextColor(int color) {
        rootView.getMActionBar().setRightTextColor(color);
    }

    @Override
    public void setRightTextColorRes(int colorRes) {
        rootView.getMActionBar().setRightTextColorRes(colorRes);
    }

    @Override
    public void setLeftIconDrawable(Drawable drawable) {
        rootView.getMActionBar().setLeftIconDrawable(drawable);
    }

    @Override
    public void setLeftIconRes(int drawableRes) {
        rootView.getMActionBar().setLeftIconRes(drawableRes);
    }

    @Override
    public void setLeftIconVisible(boolean visible) {
        rootView.getMActionBar().setLeftIconVisible(visible);
    }

    @Override
    public void setLeftTextString(String leftTextStr) {
        rootView.getMActionBar().setLeftTextString(leftTextStr);
    }

    @Override
    public void setLeftTextRes(int leftTextRes) {
        rootView.getMActionBar().setLeftTextRes(leftTextRes);
    }

    @Override
    public void setLeftTextVisible(boolean visible) {
        rootView.getMActionBar().setLeftTextVisible(visible);
    }

    @Override
    public void setLeftTextColor(int color) {
        rootView.getMActionBar().setLeftTextColor(color);
    }

    @Override
    public void setLeftTextColorRes(int colorRes) {
        rootView.getMActionBar().setLeftTextColorRes(colorRes);
    }

    @Override
    public void setActionBarTitle(String titleStr) {
        rootView.getMActionBar().setActionBarTitle(titleStr);
    }

    @Override
    public void setActionBarTitleRes(int titleRes) {
        rootView.getMActionBar().setActionBarTitleRes(titleRes);
    }

    @Override
    public void setActionBarTitleColor(int color) {
        rootView.getMActionBar().setActionBarTitleColor(color);
    }

    @Override
    public void setActionBarTitleColorRes(int colorRes) {
        rootView.getMActionBar().setActionBarTitleColorRes(colorRes);
    }

    @Override
    public void setBottomLineVisible(boolean visible) {
        rootView.getMActionBar().setBottomLineVisible(visible);
    }

    @Override
    public void setBottomLineColor(int color) {
        rootView.getMActionBar().setBottomLineColor(color);
    }

    @Override
    public void setBottomLineColorRes(int colorRes) {
        rootView.getMActionBar().setBottomLineColorRes(colorRes);
    }

    @Override
    public void setNeedActionBar(boolean need) {
        rootView.setNeedActionBar(need);
    }

    @Override
    public void setNeedStatusBar(boolean need) {
        rootView.setNeedStatusBar(need);
    }

    @Override
    public MActionbar getMActionBar() {
        return rootView.getMActionBar();
    }

    @Override
    public MStatusBar getMStatusBar() {
        return rootView.getMStatusBar();
    }

    @Override
    public View getContentView() {
        return rootView.getContentView();
    }

    @Override
    public void setListener(View.OnClickListener listener) {

    }
}
