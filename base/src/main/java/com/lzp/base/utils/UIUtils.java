package com.lzp.base.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Li Xiaopeng on 18/3/22.
 */

public class UIUtils {

    /**
     * 获取statusbar的高度
     * @param ctx
     * @return
     */
    public static int getStatusbarHeight(@NonNull Context ctx) {
        int result = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * returns the height of the ActionBar if one is enabled - supports both the native ActionBar
     * and ActionBarSherlock - http://stackoverflow.com/a/15476793/1673548
     */
    public static int getActionBarHeight(@Nullable Context context) {
        if (context == null) {
            return 0;
        }

        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        // 使用android.support.v7.appcompat包做actionbar兼容的情况
        if (context.getTheme() != null && context.getTheme()
                .resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
        return actionBarHeight != 0 ? actionBarHeight : dpToPx(context, 48);
    }

    public static int dpToPx(@NonNull Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return (int) px;
    }

    public static int pxToDp(@NonNull Context context, float px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((px / displayMetrics.density) + 0.5);
    }
}
