package com.lzp.base.contentView;

import android.view.View;

import com.lzp.base.IBase;
import com.lzp.base.actionbar.MActionbar;
import com.lzp.base.statusbar.MStatusBar;

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
