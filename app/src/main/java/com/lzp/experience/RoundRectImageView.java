package com.lzp.experience;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lzp.base.utils.UIUtils;

/**
 * @author Li Xiaopeng
 * @date 18/8/30
 */
public class RoundRectImageView extends View {

    private Context context;
    private Paint bitmapPaint;
    private Paint rectPaint;

    private int viewWidth;
    private int viewHeight;

    private int defaultSize;

    private Bitmap bitmap;
    private Rect srcBmp;
    private Rect desBmp;
    RectF roundRectF;

    public RoundRectImageView(Context context) {
        this(context, null);
    }

    public RoundRectImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

    }

    private void init() {
        defaultSize = UIUtils.dpToPx(context, 150);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yinsuwan);
        initPaint();
    }

    private void initPaint() {
        bitmapPaint = new Paint();
        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        bitmapPaint.setAntiAlias(true);

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRectF, mCurrentR, mCurrentR, rectPaint);
        canvas.drawBitmap(bitmap, srcBmp, desBmp, bitmapPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        viewWidth = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.getSize(widthMeasureSpec) : defaultSize;
        viewHeight = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.getSize(heightMeasureSpec) : defaultSize;
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;

        roundRectF = new RectF(0, 0, viewWidth, viewHeight);
        srcBmp = new Rect(0, 0, viewWidth, viewHeight);
        desBmp = new Rect(0, 0, viewWidth, viewHeight);

        doAnimation();
    }

    private float mCurrentR = 50f;
    private void doAnimation() {

        ValueAnimator animator = ValueAnimator.ofFloat(50f, viewWidth/2);

        animator.setRepeatCount(5);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mCurrentR = animatedValue;
                postInvalidate();
            }
        });

        animator.start();
    }
}
