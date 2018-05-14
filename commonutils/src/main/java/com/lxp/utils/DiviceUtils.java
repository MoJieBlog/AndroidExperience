package com.lxp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * 系统本身的一些工具
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class DiviceUtils {

    /**
     * 打电话
     *
     * @param activity
     * @param number
     */
    public static void tel(Activity activity, String number) {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        activity.startActivity(intent);
    }

    /**
     * 此方法需要处理权限，是否允许读取设备
     *
     * @param activity
     * @return
     */
    public static final String getDivice(final Context activity) {
        // ContextCompat.checkSelfPermission(OFBApplication.applicationContext, permissions[0]);
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        final String[] permissions = {Manifest.permission.READ_PHONE_STATE};
        return DEVICE_ID;
    }

    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    /**
     * 获取设备唯一标识号，不需要权限  获取UUID
     *
     * @param context
     * @return
     */
    public synchronized static String getDiviceId(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

}
