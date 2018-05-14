package com.lxp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class TimeUtils {
    /**
     * String 转时间戳
     *
     * @param timeString
     * @param sdf_       格式，例如 "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static long timeToTimeLongWithSDF(String timeString, String sdf_) {
        long timeStamp_temp = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(sdf_);
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp_temp = l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp_temp;
    }

    /**
     * 时间戳转时间
     *
     * @param time_s
     * @param sdf_   格式，例如 "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String timeLongToTimeWithSDF(String time_s, String sdf_) {
        long time_temp = Long.parseLong(time_s);
        if (time_s.length() <= 10) {//如果返回的是秒，修改为毫秒单位
            time_temp = time_temp * 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(sdf_);
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time_temp))));
        return sd;
    }

    /**
     * 时间差计算
     *
     * @param beforeTime  比较前时间
     * @param currentTime 当前系统时间
     * @return
     */
    public static String dateCompare(long beforeTime, long currentTime) {
        String temp = "--:--:--";
        long between = (currentTime - beforeTime) / 1000;//除以1000是为了转换成秒
        int day = (int) (between / (24 * 3600));
        int hour = (int) (between % (24 * 3600) / 3600);
        int minute = (int) (between % 3600 / 60);
        int second = (int) (between % 60);

        int i = 24 * 3600;
        // XLApplication.Log(between + "====between");
        if (between < i) {
            if (hour < 10) {
                temp = "0" + hour + ":";
            } else if (hour == 0) {
                temp = "00:";
            } else {
                temp = hour + ":";
            }
            if (minute < 10) {
                temp = temp + "0" + minute + ":";
            } else if (minute == 0) {
                temp = temp + "00" + ":";
            } else {
                temp = temp + minute + ":";
            }
            if (second < 10) {
                temp = temp + "0" + second + "";
            } else if (second == 0) {
                temp = temp + "00";
            } else {
                temp = temp + second;
            }
        } else {
            temp = "24:00:00";
        }
        return temp;
    }


    /**
     * 返回文字描述的日期
     *
     * @param timeStr 单位
     * @return
     */
    public static String getTimeFormatText(String timeStr) {
        long minute = 60 * 1000;// 1分钟
        long hour = 60 * minute;// 1小时
        long day = 24 * hour;// 1天
        long month = 31 * day;// 月
        long year = 12 * month;// 年
        if (timeStr == null) {
            return null;
        }

        long t = Long.parseLong(timeStr);
        long diff = System.currentTimeMillis() - (t * 1000);

        long r = 0;
        if (diff > year) {//大于一年
            //r = (diff / year);
            return timeLongToTimeWithSDF(timeStr, "yyyy-MM-dd");
        }


        if (diff > month) {//大于一个月
            r = (diff / month);
            return timeLongToTimeWithSDF(timeStr, "MM-dd");
        }
        if (diff > day) {
            String temp = "";
            r = (diff / day);
            if (r < 2) {
                temp = "昨天 " + timeLongToTimeWithSDF(timeStr, "HH:mm");
            } else {
                temp = timeLongToTimeWithSDF(timeStr, "MM-dd HH:mm");
            }
            return temp;
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + " 小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + " 分钟前";
        }
        return "刚刚";
    }

    /**
     * 判断当前日期是星期几
     *
     * @param longTime 时间戳
     * @param week     单位
     * @return
     */

    public static String getWeek(String longTime, String week) {
        try {
            Calendar c = Calendar.getInstance();
            long time_temp = Long.parseLong(longTime);
            if (longTime.length() <= 10) {//如果返回的是秒，修改为毫秒单位
                time_temp = time_temp * 1000;
            }
            c.setTimeInMillis(time_temp);

            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                week = week + "日";
            } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                week = week + "一";
            } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                week = week + "二";
            } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                week = week + "三";
            } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                week = week + "四";
            } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                week = week + "五";
            } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                week = week + "六";
            }
        } catch (Exception e) {
            week = "err";
        }

        return week;

    }

}
