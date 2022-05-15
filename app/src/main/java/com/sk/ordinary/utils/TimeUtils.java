package com.sk.ordinary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 对事件进行处理的工具类
 */
public class TimeUtils {

    public static Calendar cal;
    public static SimpleDateFormat sdf;
    /**
     * 获取当前年
     * @return
     */
    public static int year() {
        cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     * @return
     */
    public static int month() {
        cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH)+1;
    }

    /**
     * 获取当前天
     * @return
     */
    public static int day() {
        cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间特定格式的时间
     * @return
     */
    public static String getSdfTime() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

    /**
     * 获取指定日期星期几
     */
    public static String getWeek(String time){
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        int week =  cal.get(Calendar.DAY_OF_WEEK) -1;
        return weekDays[week];
    }
}
