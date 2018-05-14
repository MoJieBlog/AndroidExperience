package com.lxp.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Li Xiaopeng on 2017/1/18
 * 邮箱：448063828@qq.com
 */
public class EditTextUtils {

    /**
     * 切换键盘的可见性
     * @param activity
     */
    public static void toggle(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive()) {
            //如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * 切换密码的可见性
     *
     * @param mPasswordEditText
     * @param isHidden
     */
    public static void switchPWD(EditText mPasswordEditText, boolean isHidden) {
        if (isHidden) {
            //设置EditText文本为可见的
            mPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //设置EditText文本为隐藏的
            mPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mPasswordEditText.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = mPasswordEditText.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    /**
     * 光标移到最后
     *
     * @param editText
     */
    public static void toEnd(EditText editText) {
        String trim = editText.getText().toString().trim();
        editText.setSelection(trim.length());
    }

    /**
     * 关闭输入键盘
     *
     * @param activity
     */
    public static void close(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 另一个关闭键盘
     * @param activity
     */
    public static void hide(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {//如果显示
            // 隐藏软键盘
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        }
    }
}
