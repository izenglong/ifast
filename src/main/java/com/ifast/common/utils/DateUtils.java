package com.ifast.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN_10 = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN_19 = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 时间格式(yyyyMMddHHmmss)
     */
    public final static String DATE_TIME_PATTERN_14 = "yyyyMMddHHmmss";
    
    /**
     * 时间格式(yyyyMMdd)
     */
    public final static String DATE_TIME_PATTERN_8 = "yyyyMMdd";

    public static String format(Date date) {
        return format(date, DATE_PATTERN_10);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}
