package com.lzp.experience.widgt;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.lzp.base.utils.UIUtils;
import com.lzp.experience.R;

/**
 * @author Li Xiaopeng
 * @date 2019/2/27
 */
public class RoundImageView  extends AppCompatImageView {

    private Context context;

    private float leftTopR = 0;
    private float rightTopR = 0;
    private float rightBottomR = 0;
    private float leftBottomR = 0;
    private float[] rids = new float[8];

    private float width;
    private float height;

    private Path path = new Path();

    public RoundImageView(Context context) {
        this(context,null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        leftTopR = UIUtils.dpToPx(context,typedArray.getFloat(R.styleable.RoundImageView_left_top,leftTopR));
        rightTopR = UIUtils.dpToPx(context,typedArray.getFloat(R.styleable.RoundImageView_right_top,rightTopR));
        leftBottomR = UIUtils.dpToPx(context,typedArray.getFloat(R.styleable.RoundImageView_left_bottom,leftBottomR));
        rightBottomR = UIUtils.dpToPx(context,typedArray.getFloat(R.styleable.RoundImageView_right_bottom,rightBottomR));
        typedArray.recycle();
        setRids();
    }

    private void setRids(){
        rids[0] = leftTopR;
        rids[1] = leftTopR;
        rids[2] = rightTopR;
        rids[3] = rightTopR;
        rids[4] = rightBottomR;
        rids[5] = rightBottomR;
        rids[6] = leftBottomR;
        rids[7] = leftBottomR;
    }


    public void setLeftTopR(float leftTopR) {
        int r = UIUtils.dpToPx(context, leftTopR);
        this.leftTopR = r;
        setRids();
        invalidate();
    }

    public void setRightTopR(float rightTopR) {
        int r = UIUtils.dpToPx(context, rightTopR);
        this.rightTopR = r;
        setRids();
        invalidate();
    }

    public void setRightBottomR(float rightBottomR) {
        int r = UIUtils.dpToPx(context, rightBottomR);
        this.rightBottomR = r;
        setRids();
        invalidate();
    }

    public void setLeftBottomR(float leftBottomR) {
        int r = UIUtils.dpToPx(context, leftBottomR);
        this.leftBottomR = r;
        setRids();
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        //Array of 8 values, 4 pairs of [X,Y] radii
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0,0,width,height),rids,Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }
}