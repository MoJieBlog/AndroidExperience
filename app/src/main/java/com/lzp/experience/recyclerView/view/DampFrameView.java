package com.lzp.experience.recyclerView.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.lxp.utils.LogUtils;

/**
 * @author Li Xiaopeng
 * @date 18/10/16
 */
public abstract class DampFrameView extends FrameLayout{

    private static final String TAG = "DampFrameView";

    protected float damp = .4f;//阻尼系数，越小阻力越大
    private Context context;
    protected float touchY;

    private float offSet = 0;//偏移量

    protected View targetView;

    public DampFrameView(@NonNull Context context) {
        this(context,null);
    }

    public DampFrameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction()==MotionEvent.ACTION_DOWN){
            if (!canScrollUp()||!canScrollDown()){
                touchY = ev.getY();
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ensureTargetView();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                offSet = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                float dy = (nowY - touchY)*damp;
                if (!canScrollUp() && dy < 0) {
                    dy = Math.min(-1,(int) dy);
                    LogUtils.logE(TAG, "dispatchTouchEvent: 到底了");
                    offSet = dy;
                    targetView.setTranslationY((int) dy);
                } else if (!canScrollDown() && dy > 0) {
                    dy = Math.max(1,(int) dy);
                    LogUtils.logE(TAG, "dispatchTouchEvent: 到顶了");
                    offSet = dy;
                    targetView.setTranslationY((int) dy);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (offSet != 0) {
                    touchY = 0;
                    recoverView();
                }
                break;
        }
        if (offSet != 0) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    private void ensureTargetView() {
        targetView = getTargetView();
        if (targetView==null){
            throw new RuntimeException("targetView can not be null");
        }
    }

    /**
     * 还原动画
     */
    private void recoverView() {
        ValueAnimator animator = ValueAnimator.ofFloat(-offSet,0);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                targetView.setTranslationY(-animatedValue);
            }
        });
        animator.start();
    }

    /**
     * 是否能向上滚
     * @return
     */
    protected abstract boolean canScrollUp();

    /**
     * 是否能向下滚
     * @return
     */
    protected abstract boolean canScrollDown();

    /**
     * 需要弹性的View
     * @return
     */
    @NonNull
    protected abstract View getTargetView();
}
