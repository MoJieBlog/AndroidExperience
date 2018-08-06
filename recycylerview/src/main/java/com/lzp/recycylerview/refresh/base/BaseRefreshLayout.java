package com.lzp.recycylerview.refresh.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Li Xiaopeng on 18/8/6.
 */
public abstract class BaseRefreshLayout extends LoadingLayout implements IRefreshView{

    public BaseRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public BaseRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
