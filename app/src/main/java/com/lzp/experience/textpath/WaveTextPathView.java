package com.lzp.experience.textpath;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.lzp.base.utils.UIUtils;

/**
 * @author Li Xiaopeng
 * @date 18/9/3
 */
public class WaveTextPathView extends View {
    private static final String TAG = "WaveTextPathView";

    private Context context;

    private TextPaint textPaint;
    private int textPaintColor = Color.RED;
    private Path textPath;//文字的path

    //文本宽度
    private float mTextWidth;//文字的宽度
    private float mTextHeight;//文字的高度

    private String content = "Android";
    private PathMeasure pathMeasure;//path的测量类，用于操作path

    private Paint backPaint;

    public WaveTextPathView(Context context) {
        this(context, null);
    }

    public WaveTextPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initPaint();
        initTextPath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPath(canvas);
    }


    private int mCurrentY = 0;

    public void setCurrentR(int mCurrentR){
        mCurrentY = (int) (mTextHeight*mCurrentR/100);
        postInvalidate();
    }


    private void drawPath(Canvas canvas) {
       // canvas.save();//防止错位
        int sc = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //居中
        canvas.translate(getWidth() / 2 - mTextWidth / 2, 0);

        canvas.translate(0, getHeight() / 2 - mTextHeight / 2);
        pathMeasure.setPath(textPath, false);

        canvas.drawPath(textPath, textPaint);

        Rect rect = new Rect(0,0,(int)mTextWidth,mCurrentY);
        canvas.drawRect(rect,backPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //处理包裹内容的情况
        int warpDefaultSize = UIUtils.dpToPx(context, 100);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = heightSize = warpDefaultSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = warpDefaultSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = warpDefaultSize;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    private int viewHeight,viewWide;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWide = w;
    }

    private void initTextPath() {
        if (TextUtils.isEmpty(content)) {
            throw new RuntimeException("content can not be empty");
        }
        if (textPaint == null) {
            throw new RuntimeException("paint not init");
        }
        textPath = new Path();
        pathMeasure = new PathMeasure();
        textPaint.getTextPath(content, 0, content.length(), 0, textPaint.getTextSize(), textPath);
        pathMeasure.setPath(textPath, false);
    }

    private void initPaint() {
        textPaint = new TextPaint();
        textPaint.setColor(textPaintColor);
        textPaint.setStrokeWidth(5);
        textPaint.setTextSize(150f);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔末端为圆形 BUTT：无线帽   ROUND：圆形线帽    SQUARE：方形线帽
        textPaint.setStrokeJoin(Paint.Join.ROUND);//设置画笔转弯处我圆形 MITER：锐角 ROUND：圆弧  BEVEL：斜线

        mTextWidth = Layout.getDesiredWidth(content, textPaint);
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        mTextHeight = metrics.bottom - metrics.top;


        backPaint = new Paint();
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(Color.BLUE);
        backPaint.setStrokeWidth(5);
        backPaint.setAntiAlias(true);
        backPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔末端为圆形 BUTT：无线帽   ROUND：圆形线帽    SQUARE：方形线帽
        backPaint.setStrokeJoin(Paint.Join.ROUND);//设置画笔转弯处我圆形 MITER：锐角 ROUND：圆弧  BEVEL：斜线
        backPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }
}
