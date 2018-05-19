package com.lzp.base.component.actionbar;

import android.graphics.drawable.Drawable;

import com.lzp.base.component.IBase;

/**
 * Created by Li Xiaopeng on 18/3/23.
 */

public interface IMActionbar extends IBase {

    void setActionBarBackgroundColor(int color);
    void setActionBarBackgroundColorRes(int colorRes);

    void setActionBarRightIconDrawable(Drawable drawable);
    void setActionBarRightIconRes(int drawableRes);
    void setActionBarRightIconVisible(boolean visible);

    void setActionBarRightTextString(String rightTextStr);
    void setActionBarRightTextRes(int rightTextRes);
    void setActionBarRightTextVisible(boolean visible);

    void setActionBarRightTextColor(int color);
    void setActionBarRightTextColorRes(int colorRes);

    void setActionBarLeftIconDrawable(Drawable drawable);
    void setActionBarLeftIconRes(int drawableRes);
    void setActionBarLeftIconVisible(boolean visible);

    void setActionBarLeftTextString(String leftTextStr);
    void setActionBarLeftTextRes(int leftTextRes);
    void setActionBarLeftTextVisible(boolean visible);

    void setActionBarLeftTextColor(int color);
    void setActionBarLeftTextColorRes(int colorRes);

    void setActionBarTitle(String titleStr);
    void setActionBarTitleRes(int titleRes);

    void setActionBarTitleColor(int color);
    void setActionBarTitleColorRes(int colorRes);

    void setActionBarBottomLineVisible(boolean visible);
    void setActionBarBottomLineColor(int color);
    void setActionBarBottomLineColorRes(int colorRes);
}
