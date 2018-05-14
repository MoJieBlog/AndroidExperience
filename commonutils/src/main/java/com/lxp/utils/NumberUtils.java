package com.lxp.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class NumberUtils {
    /**
     * 生成指定长度的随即串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 最多保留两位小数
     * @param inputNumber  3.10
     * @return 3.1
     */
    public static String get2number(double inputNumber){
        DecimalFormat format = new DecimalFormat("#.##");
        String format1 = format.format(inputNumber);
        return format1;
    }

    /**
     * 保留两位小数，肯定两位小数，没有用0代替
     * @param inputNumber  3.10
     * @return 3.10
     */
    public static String get2number1(double inputNumber){
        DecimalFormat format = new DecimalFormat("#.00");
        String format1 = format.format(inputNumber);
        return format1;
    }
}
