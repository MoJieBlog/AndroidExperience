package com.lxp.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class ToastUtils {

    public static Toast mToast;

    public static Toast getToast(Context context,String text) {
        if (mToast == null) {
            mToast = new Toast(context);
        }
        View view = View.inflate(context, R.layout.toast_layout,null);
        mToast.setView(view);
        mToast.setGravity(Gravity.BOTTOM, 0, UiUtils.dip2px(context,100));
        return mToast;
    }

    public static void setToast(Context context,int stringId) {
        setToast(context,context.getResources().getString(stringId));
    }

    public static Toast setToast(Context context,String text) {
        Toast toast = getToast(context,text);
        toast.setDuration( Toast.LENGTH_SHORT);
        View view = toast.getView();
        TextView tv = view.findViewById(R.id.tv_toast);
        tv.setText(text);
        toast.show();
        return toast;
    }
}
