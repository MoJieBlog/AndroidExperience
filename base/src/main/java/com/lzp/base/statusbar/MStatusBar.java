package com.lzp.base.statusbar;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lzp.base.R;
import com.lzp.base.utils.UIUtils;

/**
 * Created by Li Xiaopeng on 18/3/22.
 */

public class MStatusBar implements IMStatusBar {

    private Context context;
    private Resources resources;
    private View rootView;

    public MStatusBar(Context context) {
        this.context = context;
        resources = context.getResources();

        rootView = new FrameLayout(context);
        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.getStatusbarHeight(context));
        rootView.setLayoutParams(params);
        rootView.setBackgroundColor(resources.getColor(R.color.color_statusbar));
    }

    @Override
    public void setStatusBarColor(int color) {
        if (rootView != null) rootView.setBackgroundColor(color);
    }

    @Override
    public void setStatusBarColorRes(int colorRes) {
        setStatusBarColor(resources.getColor(colorRes));
    }

    @Override
    public void setStatusBarAlpha(float alpha) {
        if (alpha >= 0 && alpha <= 1 && rootView != null) {
            rootView.getBackground().setAlpha((int) (alpha * 255));
        }
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void onRelease() {
        context = null;
        resources = null;
        rootView = null;
    }
}
