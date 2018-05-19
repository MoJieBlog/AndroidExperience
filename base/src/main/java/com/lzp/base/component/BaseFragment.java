package com.lzp.base.component;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzp.base.component.actionbar.IMActionbar;
import com.lzp.base.component.actionbar.MActionbar;
import com.lzp.base.component.contentView.IMRootView;
import com.lzp.base.component.contentView.MRootView;
import com.lzp.base.component.statusbar.IMStatusBar;
import com.lzp.base.component.statusbar.MStatusBar;

import butterknife.ButterKnife;

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
        ButterKnife.bind(this,rootView.getRootView());
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


    public void onMCreate(Bundle savedInstanceState) {

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
    public void setActionBarBackgroundColor(int color) {
        rootView.getMActionBar().setActionBarBackgroundColor(color);
    }

    @Override
    public void setActionBarBackgroundColorRes(int colorRes) {
        rootView.getMActionBar().setActionBarBackgroundColorRes(colorRes);
    }

    @Override
    public void setActionBarRightIconDrawable(Drawable drawable) {
        rootView.getMActionBar().setActionBarRightIconDrawable(drawable);
    }

    @Override
    public void setActionBarRightIconRes(int drawableRes) {
        rootView.getMActionBar().setActionBarRightIconRes(drawableRes);
    }

    @Override
    public void setActionBarRightIconVisible(boolean visible) {
        rootView.getMActionBar().setActionBarRightIconVisible(visible);
    }

    @Override
    public void setActionBarRightTextString(String rightTextStr) {
        rootView.getMActionBar().setActionBarRightTextString(rightTextStr);
    }

    @Override
    public void setActionBarRightTextRes(int rightTextRes) {
        rootView.getMActionBar().setActionBarRightTextRes(rightTextRes);
    }

    @Override
    public void setActionBarRightTextVisible(boolean visible) {
        rootView.getMActionBar().setActionBarRightTextVisible(visible);
    }

    @Override
    public void setActionBarRightTextColor(int color) {
        rootView.getMActionBar().setActionBarRightTextColor(color);
    }

    @Override
    public void setActionBarRightTextColorRes(int colorRes) {
        rootView.getMActionBar().setActionBarRightTextColorRes(colorRes);
    }

    @Override
    public void setActionBarLeftIconDrawable(Drawable drawable) {
        rootView.getMActionBar().setActionBarLeftIconDrawable(drawable);
    }

    @Override
    public void setActionBarLeftIconRes(int drawableRes) {
        rootView.getMActionBar().setActionBarLeftIconRes(drawableRes);
    }

    @Override
    public void setActionBarLeftIconVisible(boolean visible) {
        rootView.getMActionBar().setActionBarLeftIconVisible(visible);
    }

    @Override
    public void setActionBarLeftTextString(String leftTextStr) {
        rootView.getMActionBar().setActionBarLeftTextString(leftTextStr);
    }

    @Override
    public void setActionBarLeftTextRes(int leftTextRes) {
        rootView.getMActionBar().setActionBarLeftTextRes(leftTextRes);
    }

    @Override
    public void setActionBarLeftTextVisible(boolean visible) {
        rootView.getMActionBar().setActionBarLeftTextVisible(visible);
    }

    @Override
    public void setActionBarLeftTextColor(int color) {
        rootView.getMActionBar().setActionBarLeftTextColor(color);
    }

    @Override
    public void setActionBarLeftTextColorRes(int colorRes) {
        rootView.getMActionBar().setActionBarLeftTextColorRes(colorRes);
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
    public void setActionBarBottomLineVisible(boolean visible) {
        rootView.getMActionBar().setActionBarBottomLineVisible(visible);
    }

    @Override
    public void setActionBarBottomLineColor(int color) {
        rootView.getMActionBar().setActionBarBottomLineColor(color);
    }

    @Override
    public void setActionBarBottomLineColorRes(int colorRes) {
        rootView.getMActionBar().setActionBarBottomLineColorRes(colorRes);
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
