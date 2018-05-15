package com.lzp.base.component.contentView;

import android.view.View;

import com.lzp.base.component.IBase;
import com.lzp.base.component.actionbar.MActionbar;
import com.lzp.base.component.statusbar.MStatusBar;

/**
 * Created by Li Xiaopeng on 18/3/23.
 */

public interface IMRootView extends IBase {

    void setNeedActionBar(boolean need);

    void setNeedStatusBar(boolean need);

    MActionbar getMActionBar();

    MStatusBar getMStatusBar();

    View getContentView();

    void setListener(View.OnClickListener listener);
}
