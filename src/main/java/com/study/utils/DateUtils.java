
package com.study.utils;


import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换工具类
 * pierre
 */

public class DateUtils {
    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmss";
    public static final String LOSTACC_FORMAT = "yyyyMMdd";

    public static final String ADMIN_FORMAT = "yyyy-MM-dd";

    public static final String CHANNEL_FORMAT = "yyyy-MM-dd H:m:s";
    public static final String CHANNEL_FORMAT_ALI = "yyyy-MM-dd HH:mm:ss";

    public static DateFormat getDateFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat;
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @throws ParseException
     */
    public static Date stringToDate(String date, String format) throws ParseException {
        if (StringUtils.isNotBlank(date)) {
            return getDateFormat(format).parse(date);
        }
        return null;
    }


    public static Integer dateToInteger(Date srcDate, String format) throws Exception {

        SimpleDateFormat sf = new SimpleDateFormat(format);

        return Integer.valueOf(sf.format(srcDate));

    }

    public static Long dateToLong(Date srcDate, String format) throws Exception {

        SimpleDateFormat sf = new SimpleDateFormat(format);

        return Long.valueOf(sf.format(srcDate));

    }

    public static Long strToLong(String srcDate, String format) throws Exception {

        SimpleDateFormat sf = new SimpleDateFormat(format);
        return dateToLong(sf.parse(srcDate), DEFAULT_FORMAT);

    }


    public static String dateToString(Date srcDate, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(srcDate);
    }

    public static Date getPastDate(int unit, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(unit, value);

        return calendar.getTime();
    }

    /**
     * 得到指定日期当天的开始时间
     */
    public static Date getStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 得到指定日期当天原最后时间
     *
     * @param date
     */
    public static Date getEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
//        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 得到指定日期的昨天
     *
     * @param date
     */
    public static Date getYesterdayStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    public static String getSomeDateStr(Date date, int i) {
        SimpleDateFormat sf = new SimpleDateFormat(LOSTACC_FORMAT);
        return sf.format(getNowSomeDate(date, i));
    }


    /**
     * 得到特定日期的第N天
     * 当day>0时,为后N天，否则，为前N天
     *
     * @param date
     * @param day  数字
     */
    public static Date getNowSomeDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到特定日期的第N月
     * 当month>0时,为后N月，否则，为前N月
     *
     * @param date
     * @param month 数字
     */
    public static Date getNowSomeMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取特定月第一天
     *
     * @param date
     * @param month
     */
    public static Date getMothFirstDay(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前时间后多少分钟
     *
     * @param date
     * @param minutes
     */
    public static Date getNowSomeMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 获取当前时间后多少秒
     *
     * @param date
     * @param minutes
     */
    public static Date getNowSomeSecond(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, minutes);
        return calendar.getTime();
    }

    /**
     * 将字符串转化为日期型
     *
     * @param date
     * @throws ParseException
     */
    public static Date getInt2Date(String date, String format) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(date);
    }

    /**
     * 获取时间的差的秒数 yyyyMMddHHmmss
     *
     * @param endTime
     * @param startTime
     * @throws Exception
     */
    public static long getTimeDifference(String endTime, String startTime) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return (sf.parse(endTime).getTime() - sf.parse(startTime).getTime()) / 1000;
    }
}
