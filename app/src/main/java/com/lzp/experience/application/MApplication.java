package com.lzp.experience.application;

import android.app.Application;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.lzp.experience.MNotificationManager;

/**
 * Created by Li Xiaopeng on 18/3/21.
 */

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
        MNotificationManager.getInstance().init(this);
    }
}
