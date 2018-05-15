package com.lzp.experience.application;

import android.app.Application;

/**
 * Created by Li Xiaopeng on 18/3/21.
 */

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       registerActivityLifecycleCallbacks(new MxActivityLifecycleCallbacks());
    }
}
