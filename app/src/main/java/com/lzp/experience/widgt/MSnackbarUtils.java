package com.lzp.experience.widgt;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * @author Li Xiaopeng
 * @date 2018/12/11
 */
public class MSnackbarUtils {

    public Snackbar getSnackbar(String text,int duration){
        Snackbar snackbar = Snackbar.make(null, text, duration);
        View view = snackbar.getView();
        return snackbar;
    }


}
