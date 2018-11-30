package com.lzp.experience.image.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lzp.experience.R;

/**
 * @author Li Xiaopeng
 * @date 2018/11/30
 */
public class BitmapShaderTest extends View {

    private Context context;
    private Resources resources;

    private Paint paint;
    BitmapShader shader;
    public BitmapShaderTest(Context context) {
        this(context, null);
    }

    public BitmapShaderTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        resources = context.getResources();

        init();
    }

    private void init() {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.daqiao);
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setShader(shader);
        paint.setTextSize(250);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("王者荣耀",0,500,paint);
    }
}
