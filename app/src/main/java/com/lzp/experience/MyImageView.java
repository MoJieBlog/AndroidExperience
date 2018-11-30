package com.lzp.experience;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * @author Li Xiaopeng
 * @date 2018/10/31
 */
@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView{

    private int downY;
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }
}
