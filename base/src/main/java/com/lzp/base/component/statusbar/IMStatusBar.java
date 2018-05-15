package com.lzp.base.component.statusbar;

import com.lzp.base.component.IBase;

/**
 * Created by Li Xiaopeng on 18/3/22.
 */

public interface IMStatusBar extends IBase {

    void setStatusBarColor(int color);

    void setStatusBarColorRes(int colorRes);

    void setStatusBarAlpha(float alpha);
}
