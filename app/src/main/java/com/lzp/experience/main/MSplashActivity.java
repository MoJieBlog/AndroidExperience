package com.lzp.experience.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.lzp.base.component.BaseActivity;
import com.lzp.experience.R;
import com.lzp.experience.textpath.MTextPathView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Li Xiaopeng on 18/8/2.
 */
public class MSplashActivity extends BaseActivity {

    private static final String TAG = "MSplashActivity";
    @BindView(R.id.text_path_view)
    MTextPathView textPathView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedActionBar(false);
        setNeedStatusBar(false);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        textPathView.start();
        textPathView.setAnimListener(new MTextPathView.AnimListener() {
            @Override
            public void animStart() {
            }

            @Override
            public void animFinish() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MSplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },500);
            }
        });
    }
}
