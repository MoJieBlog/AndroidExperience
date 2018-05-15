package com.lzp.base.component.actionbar;

import android.graphics.drawable.Drawable;

import com.lzp.base.component.IBase;

/**
 * Created by Li Xiaopeng on 18/3/23.
 */

public interface IMActionbar extends IBase {

    void setActionbarBackgroundColor(int color);
    void setActionbarBackgroundColorRes(int colorRes);

    void setRightIconDrawable(Drawable drawable);
    void setRightIconRes(int drawableRes);
    void setRightIconVisible(boolean visible);

    void setRightTextString(String rightTextStr);
    void setRightTextRes(int rightTextRes);
    void setRightTextVisible(boolean visible);

    void setRightTextColor(int color);
    void setRightTextColorRes(int colorRes);

    void setLeftIconDrawable(Drawable drawable);
    void setLeftIconRes(int drawableRes);
    void setLeftIconVisible(boolean visible);

    void setLeftTextString(String leftTextStr);
    void setLeftTextRes(int leftTextRes);
    void setLeftTextVisible(boolean visible);

    void setLeftTextColor(int color);
    void setLeftTextColorRes(int colorRes);

    void setActionBarTitle(String titleStr);
    void setActionBarTitleRes(int titleRes);

    void setActionBarTitleColor(int color);
    void setActionBarTitleColorRes(int colorRes);

    void setBottomLineVisible(boolean visible);
    void setBottomLineColor(int color);
    void setBottomLineColorRes(int colorRes);
}
