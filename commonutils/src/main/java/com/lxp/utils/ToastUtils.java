package com.lxp.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class ToastUtils {

    public static Toast mToast;
    private static Handler mHandler = new Handler();

    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void setToast(Context context, String text) {
        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, 1500);
        mToast.show();
    }
}
