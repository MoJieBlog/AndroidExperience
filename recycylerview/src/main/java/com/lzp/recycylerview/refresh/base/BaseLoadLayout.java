package com.lzp.recycylerview.refresh.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Li Xiaopeng on 18/8/6.
 */
public abstract class BaseLoadLayout extends LoadingLayout implements ILoadingView{

    public BaseLoadLayout(@NonNull Context context) {
        super(context);
    }

    public BaseLoadLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
