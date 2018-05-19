package com.lzp.experience.application;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lzp.base.swipeback.ParallaxHelper;

/**
 * Created by Li Xiaopeng on 18/3/21.
 */

public class MxActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private int activityCount = 0;
    private Activity currentActivity;

    public MxActivityLifecycleCallbacks() {
        ParallaxHelper.getInstance();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ParallaxHelper.getInstance().onActivityCreated(activity, savedInstanceState);
    }

    @Override
    public void onActivityStarted(Activity activity) {

        ParallaxHelper.getInstance().onActivityStarted(activity);

        activityCount++;
       /* // 后台切换到前台
        if (MarkDotManager.getInstance().isSessionPast()) {
            MarkDotManager.getInstance().createNewSession();
        } else {
            MarkDotManager.getInstance().getSessionId();
        }
        MarkDotManager.getInstance().saveLastHandlerTime();*/
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ParallaxHelper.getInstance().onActivityResumed(activity);
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ParallaxHelper.getInstance().onActivityPaused(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ParallaxHelper.getInstance().onActivityStopped(activity);
        activityCount--;
        if (activityCount == 0) {
           // 前台切换到后台

        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ParallaxHelper.getInstance().onActivitySaveInstanceState(activity, outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ParallaxHelper.getInstance().onActivityDestroyed(activity);
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }


}
