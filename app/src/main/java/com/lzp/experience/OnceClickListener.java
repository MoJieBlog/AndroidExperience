package com.lzp.experience;

import android.view.View;

/**
 * 防止用户快速点击，多次出发点击事件
 * @author Li Xiaopeng
 * @date 18/10/16
 */
public abstract class OnceClickListener implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onOnceClick(v);
        }
    }

    public abstract void onOnceClick(View v);
}
