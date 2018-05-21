package com.lzp.recycylerview.refresh;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Li Xiaopeng on 18/5/21.
 */

public class MSwipeRefreshLayout extends ViewGroup implements NestedScrollingParent,
        NestedScrollingChild {
    public MSwipeRefreshLayout(Context context) {
        super(context);
    }

    public MSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
