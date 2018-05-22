package com.lzp.recycylerview.refresh.base;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Li Xiaopeng on 18/5/22.
 */

public abstract class LoadingLayout extends FrameLayout implements IBaseListener {

    protected Context mContext;
    protected Resources mResources;

    public LoadingLayout(@NonNull Context context) {
        this(context,null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mResources  = context.getResources();
        createViewPlaceHolder();
        init();
    }

    public abstract void init();
    public abstract int getViewWidth();
    public abstract int getViewHeight();

    private void createViewPlaceHolder() {
        View viewPlaceHolder = new View(mContext);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(viewPlaceHolder,lp);
    }
}
