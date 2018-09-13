package com.lzp.experience.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.lzp.base.component.BaseActivity;
import com.lzp.base.component.IBasePage;
import com.lzp.experience.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Li Xiaopeng
 * @date 18/9/13
 */
public class ColorMatrixStudyActivity extends BaseActivity implements IBasePage, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "ColorMatrixStudyActivit";
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.gray)
    Button gray;
    @BindView(R.id.old)
    Button old;
    @BindView(R.id.rotate)
    Button rotate;
    @BindView(R.id.height)
    Button height;
    @BindView(R.id.img)
    Button img;
    @BindView(R.id.red)
    SeekBar red;
    @BindView(R.id.green)
    SeekBar green;
    @BindView(R.id.blue)
    SeekBar blue;
    @BindView(R.id.r)
    SeekBar r;

    private float[] mColorMatrix = null;
    Bitmap bitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        ButterKnife.bind(this);
    }

    @Override
    public void readArguments(Bundle bundle) {

    }

    @Override
    public void writeArguments(Bundle bundle) {

    }

    @Override
    public void initView() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yinsuwan);
    }

    private Bitmap dealBitmap(float[] mColorMatrix) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mColorMatrix);

        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bmp;
    }

    @Override
    public void setListener() {
        img.setOnClickListener(this);
        gray.setOnClickListener(this);
        old.setOnClickListener(this);
        height.setOnClickListener(this);
        rotate.setOnClickListener(this);

        red.setOnSeekBarChangeListener(this);
        blue.setOnSeekBarChangeListener(this);
        green.setOnSeekBarChangeListener(this);
        r.setOnSeekBarChangeListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img:
                mColorMatrix = new float[]
                        {
                                1, 0, 0, 0, 0,
                                0, 1, 0, 0, 0,
                                0, 0, 1, 0, 0,
                                0, 0, 0, 1, 0

                        };
                break;
            case R.id.gray:
                mColorMatrix = new float[]
                        {
                                0.33f, 0.59f, 0.11f, 0, 0,
                                0.33f, 0.59f, 0.11f, 0, 0,
                                0.33f, 0.59f, 0.11f, 0, 0,
                                0, 0, 0, 1, 0

                        };
                break;
            case R.id.old:
                mColorMatrix = new float[]
                        {
                                0.393f, 0.769f, 0.189f, 0, 0,
                                0.349f, 0.686f, 0.168f, 0, 0,
                                0.272f, 0.534f, 0.131f, 0, 0,
                                0, 0, 0, 1, 0

                        };
                break;
            case R.id.height:
                mColorMatrix = new float[]
                        {
                                1.438f, 0.122f, 0.016f, 0, 0.03f,
                                0.062f, 1.378f, 0.016f, 0, 0.05f,
                                0.062f, 0.122f, 1.183f, 0, 0.02f,
                                0, 0, 0, 1, 0

                        };
                break;
            case R.id.rotate:
                mColorMatrix = new float[]
                        {
                                -1, 0, 0, 1, 1,
                                0, -1, 0, 1, 1,
                                0, 0, -1, 1, 1,
                                0, 0, 0, 1, 0

                        };
                break;

        }

        Bitmap bitmap1 = dealBitmap( mColorMatrix);
        iv.setImageBitmap(bitmap1);
    }

    private float cRed = 1;
    private float cGreen = 1;
    private float cBlue = 1;
    private float cR = 1;
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();
        switch (id){
            case R.id.red:
                cRed = progress/50;
                break;
            case R.id.green:
                cGreen = progress/50;
                break;
            case R.id.blue:
                cBlue = progress/50;
                break;
            case R.id.r:
                cR = progress/50;
                break;
        }

        mColorMatrix = new float[]
                {
                        cRed, 0, 0, 0, 0,
                        0, cGreen, 0, 0, 0,
                        0, 0, cBlue, 0, 0,
                        0, 0, 0, cR, 0

                };

        Bitmap bitmap1 = dealBitmap( mColorMatrix);
        iv.setImageBitmap(bitmap1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
