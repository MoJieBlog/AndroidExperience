package com.lzp.experience;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 可拖拽，单击展开和收缩的View
 *
 * @author Li Xiaopeng
 * @date 2018/10/31
 */
public class TopSheetView extends LinearLayout {

    private static final String TAG = "TopSheetView";
    private int minHeight = 150;//最小高度
    private int maxHeight = 450;//最大高度

    private boolean isOpen = false;
    private boolean isAnimation = false;

    private MyImageView switchView;
    private RecyclerView contentView;

    public TopSheetView(Context context) {
        this(context, null);
    }

    public TopSheetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setListener();
    }

    private void init() {
        if (minHeight > maxHeight) {
            throw new RuntimeException("minHeight is bigger than maxHeight");
        }
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams imageParams =
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        switchView = new MyImageView(getContext());
        switchView.setLayoutParams(imageParams);
        switchView.setPadding(30, 0, 30, 30);
        switchView.setImageResource(R.mipmap.find_publish_compile_site_details_unfold);


        LinearLayout.LayoutParams contentParams =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, minHeight);
        contentView = new RecyclerView(getContext());
        contentView.setBackgroundResource(R.drawable.rect_ffffff_999999_r6);
        contentView.setLayoutParams(contentParams);

        addView(contentView);
        addView(switchView);


    }


    private float lastY;
    private float currentY;

    private float lastTop;
    private float currentTop;

    private void setListener() {
        /*switchView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastTop = isOpen ? maxHeight : minHeight;
                        lastY = motionEvent.getY();
                        currentTop = switchView.getTop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currentTop = switchView.getTop();
                        currentY = motionEvent.getY();
                        if (currentTop + 2 >= minHeight && currentTop - 2 <= maxHeight) {
                            moveBy((int) (currentY - lastY));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (currentTop-lastTop>5){
                            if (Math.abs(currentTop - minHeight) > Math.abs(maxHeight - currentTop)) {
                                doOpenAnimation(currentTop, maxHeight);
                            } else {
                                doCloseAnimation(currentTop, minHeight);
                            }
                        }
                        break;
                }
                LogUtils.logE(TAG, "onTouch: "+(Math.abs(currentTop - lastTop) > 5));
                return Math.abs(currentTop - lastTop) > 5;
            }
        });*/

        switchView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "点击了");
                if (isOpen) {
                    close();
                } else {
                    open();
                }
            }
        });
    }

    private void moveBy(int deltY) {
        int measuredHeight = contentView.getMeasuredHeight();
        LinearLayout.LayoutParams contentParams =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, measuredHeight + deltY);
        contentView.setLayoutParams(contentParams);
    }

    /**
     * 获取动画实例
     *
     * @param fromY
     * @param toY
     * @return
     */
    private ValueAnimator getAnimator(float fromY, float toY) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromY, toY);
        animator.setDuration(300);
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams contentParams =
                        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) value);
                contentView.setLayoutParams(contentParams);
            }
        });

        return animator;
    }

    /**
     * 打开
     */
    public void open() {
        doOpenAnimation(minHeight, maxHeight);
    }

    /**
     * 关闭
     */
    public void close() {
        doCloseAnimation(maxHeight, minHeight);
    }

    /**
     * 关闭的动画
     *
     * @param fromY
     * @param toY
     */
    public void doOpenAnimation(float fromY, float toY) {
        if (isAnimation) return;
        isAnimation = true;
        ValueAnimator animator = getAnimator(fromY, toY);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                openStatus();
            }
        });
        animator.start();
    }


    /**
     * 关闭的动画
     *
     * @param fromY
     * @param toY
     */
    public void doCloseAnimation(float fromY, float toY) {
        if (isAnimation) return;
        isAnimation = true;
        ValueAnimator animator = getAnimator(fromY, toY);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                closeStatus();
            }
        });
        animator.start();
    }

    /**
     * 打开的状态的UI
     */
    private void openStatus() {
        switchView.setImageResource(R.mipmap.find_publish_compile_site_details_packup);
        isOpen = true;
        isAnimation = false;
    }

    /**
     * 关闭状态的UI
     */
    private void closeStatus() {
        isOpen = false;
        isAnimation = false;
        switchView.setImageResource(R.mipmap.find_publish_compile_site_details_unfold);
    }

    /**
     * 设置最小的高度
     *
     * @param minHeight
     */
    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    /**
     * 设置最大的高度
     *
     * @param maxHeight
     */
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
