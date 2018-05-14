package com.lzp.base.contentView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lzp.base.actionbar.MActionbar;
import com.lzp.base.statusbar.MStatusBar;

/**
 * Created by Li Xiaopeng on 18/3/22.
 */

public abstract class MRootView implements IMRootView {

    public static int NO_CONTENT_LAYOUT = -1;

    private Context context;

    private LinearLayout rootView;
    private MStatusBar statusBar;
    private MActionbar actionbar;
    private View contentView;

    private boolean needStatusBar = true;
    private boolean needActionBar = true;

    public MRootView(Context context) {
        this.context = context;
        rootView = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(layoutParams);
        rootView.setOrientation(LinearLayout.VERTICAL);
    }

    public View initRootView(){
        initStatusBar();
        initActionbar();
        initContentView();
        return rootView;
    }

    protected void initContentView(){
        if (getContentLayoutRes()!=NO_CONTENT_LAYOUT){
            contentView = LayoutInflater.from(context).inflate(getContentLayoutRes(),null);
        }else if(getContentView()!=null){
            contentView = getContentView();
        }else{
            contentView = new FrameLayout(context);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(layoutParams);
        rootView.addView(contentView);
    }

    private void initActionbar() {
        if (needActionBar){
            actionbar = new MActionbar(context);
            rootView.addView(actionbar.getRootView());
        }
    }

    private void initStatusBar() {
        if (needStatusBar){
            statusBar = new MStatusBar(context);
            rootView.addView(statusBar.getRootView());
        }
    }

    public abstract int getContentLayoutRes();
    public abstract View getContentLayout();

    public View getRootView() {
        return rootView;
    }


    @Override
    public void onRelease() {
        contentView = null;
        rootView = null;
        context = null;
        statusBar = null;
        actionbar = null;
        if (statusBar!=null)statusBar.onRelease();
        if (actionbar!=null)actionbar.onRelease();
    }

    @Override
    public void setNeedActionBar(boolean need) {
        needActionBar = need;
    }

    @Override
    public void setNeedStatusBar(boolean need) {
        needStatusBar = need;
    }

    @Override
    public MActionbar getMActionBar() {
        return actionbar;
    }

    @Override
    public MStatusBar getMStatusBar() {
        return statusBar;
    }

    @Override
    public View getContentView() {
        return contentView;
    }
    @Override
    public void setListener(View.OnClickListener listener) {
        if (actionbar!=null)actionbar.setClickListener(listener);
    }
}
