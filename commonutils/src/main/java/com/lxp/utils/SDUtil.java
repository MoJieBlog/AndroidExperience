package com.lxp.utils;


import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * SD卡工具
 */
public class SDUtil {

    // 检测SD卡是否挂载了
    public static boolean isMounted() {
        // 得到当前主要的存储设备的状态
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        }
        return false;
    }

    // 得到存储卡的根目录,绝对路径
    public static String getSDCardPath() {

        if (isMounted()) {
            // 得到主要的存储设备的文件路径
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        return null;
    }

    // 计算SD卡的存储空间
    public static long getSDSize() {

        if (isMounted()) {

            // StatFs用来计算文件系统空间大小的一个类.
            // 这个类是对Unix中的statvfs()这个方法的包装类.
            StatFs stat = new StatFs(getSDCardPath());
            // 得到块的数量
            // int blockCount = stat.getBlockCount();
            // 得到每块的大小
            // int blockSize = stat.getBlockSize();
            // return blockCount*blockSize/1024/1024;
            long blockCountLong = stat.getBlockCountLong();
            long blockSizeLong = stat.getBlockSizeLong();
            return blockCountLong * blockSizeLong / 1024 / 1024;
        }

        return 0;
    }

    // 计算SD的可用空间
    public static long getAvailableSize() {
        if (isMounted()) {

            StatFs stat = new StatFs(getSDCardPath());
            // 可用的块数
            long availableBlocksLong = stat.getAvailableBlocksLong();
            // 每块的大小
            long blockSizeLong = stat.getBlockSizeLong();
            return availableBlocksLong * blockSizeLong / 1024 / 1024;
        }
        return 0;
    }

    // 得到某个类型的文件的绝对路径
    public static String getPublicPath(String type) {
        if (isMounted()) {
            return Environment.getExternalStoragePublicDirectory(type)
                    .getAbsolutePath();
        }

        return null;
    }

    // 得到一个私有类型文件的路径
    public static String getPrivateDataPath(Context context, String type) {
        if (isMounted()) {

            return context.getExternalFilesDir(type).getAbsolutePath();
        }

        return null;
    }

    // 存储数据到SD卡中
    public static boolean saveDataIntoSDCardPrivate(byte[] data, Context context, String type,
                                                    String fileName) {

        if (isMounted()) {
            // 构建出文件存储的路径
            File file = new File(getPrivateDataPath(context, type));

            if (!file.exists()) {
                file.mkdirs();
            }

            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(
                        file, fileName)));

                bos.write(data, 0, data.length);
                bos.flush();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return false;
    }

    // 存储数据到SD卡中
    public static boolean saveDataIntoSDCardPublic(byte[] data, String type,
                                                   String fileName) {

        if (isMounted()) {
            // 构建出文件存储的路径
            File file = new File(getPublicPath(type));

            if (!file.exists()) {
                file.mkdirs();
            }

            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(
                        file, fileName)));

                bos.write(data, 0, data.length);
                bos.flush();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return false;
    }

    // 存储数据到SD卡中
    public static boolean saveDataIntoSDCard(byte[] data, String sspath, String dir,
                                             String fileName) {

        if (isMounted()) {
            // 构建出文件存储的路径
            String path = sspath + File.separator + dir;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(
                        file, fileName)));

                bos.write(data, 0, data.length);
                bos.flush();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return false;
    }

    // 从SD卡中读取内容
    public static byte[] getDataFromSDCard(String dir, String fileName) {

        if (isMounted()) {
            String path = getSDCardPath() + File.separator + dir;
            File file = new File(path, fileName);
            // 只有文件存在的时候才取这个文件
            if (file.exists()) {
                BufferedInputStream bis = null;
                ByteArrayOutputStream baos = null;

                try {
                    baos = new ByteArrayOutputStream();
                    bis = new BufferedInputStream(new FileInputStream(file));
                    int len = 0;
                    byte[] buffer = new byte[1024 * 8];
                    while ((len = bis.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                        baos.flush();
                    }

                    return baos.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                            baos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return null;
    }

}
