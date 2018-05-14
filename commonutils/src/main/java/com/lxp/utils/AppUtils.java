package com.lxp.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class AppUtils {

    /**
     * 启动另一个app
     *
     * @param activity
     * @param packageName 另一个app的包名
     * @param className   另一个app的界面名字
     * @param downLoadUrl 加入没有安装该app，打开浏览器下载这个app
     */
    public static void startAnotherApp(Activity activity, String packageName, String className, String downLoadUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            activity.startActivity(intent);
        } catch (Exception e) {
            Intent viewIntent = new
                    Intent("android.intent.action.VIEW", Uri.parse(downLoadUrl));
            activity.startActivity(viewIntent);
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        String version = "1.0.0";
        try {
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = null;
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            ToastUtils.setToast(context, context.getResources().getString(R.string.getVersionFail));
            version = "1.0.0";
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    public static void exitBy2Click(Activity activity) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastUtils.setToast(activity,"再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            //后台运行
            activity.moveTaskToBack(false);
            ToastUtils.mToast.cancel();
            // 退出应用,注意这里的退出应用是在后台运行。全部杀死程序会收不到消息，但是现在是可以的
            activity.finish();
            //System.exit(0);
        }
    }
}
