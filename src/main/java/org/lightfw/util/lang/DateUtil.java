package org.lightfw.util.lang;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

/**
 * 通用的日期, 时间处理.
 * 注意：Timestamp 是Date的子类，能接受Date参数的也可以接受Timestamp作为参数
 *
 * @author wangxm
 */
public class DateUtil {
    private static final Logger log = LogUtil.getLogger(DateUtil.class);
    /**
     * 一天的毫秒数
     */
    public final static long ONE_DAY_MILLIS = 24 * 60 * 60 * 1000;
    /**
     * 本地化为简体中文
     */
    public final static Locale DEFAULT_CHINA_LOCALE = Locale.SIMPLIFIED_CHINESE;
    /**
     * 时区设置为北京时间
     */
    public final static TimeZone DEFAULT_CHINA_TIMEZONE = TimeZone.getTimeZone("GMT+8:00");

    public final static String[] DEFAULT_WEEK_ARRAY_DESC = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    /**
     * 根据时间格式获得当前时间
     *
     * @param dateFormat
     * @return
     */
    public static String getDate(DateFormat dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.toString());
        return simpleDateFormat.format(new Date());
    }

    /**
     * 根据时间格式获得时间
     *
     * @param dateFormat
     * @param date
     * @return
     */
    public static String getDate(Date date, DateFormat dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.toString());
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转时间
     *
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date toDate(String dateStr, DateFormat dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.toString());
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串转对象
     *
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date toDate(String dateStr, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.error("Fail to convert to date", e);
            return null;
        }
    }

    /**
     * 字符串转时间
     *
     * @param dateStr
     * @return
     */
    public static Date toDate(String dateStr) {
        Date date = toDate(dateStr, DateFormat.YYYY_MM_DDHH_MM_SS);
        if (date == null) {
            date = toDate(dateStr, DateFormat.YYYY_MM_DD_HH_MM);
            if (date == null) {
                date = toDate(dateStr, DateFormat.YYYY_MM_DD);
                if (date == null) {
                    date = toDate(dateStr, DateFormat.HH_MM_SS);
                    if (date == null) {
                        date = toDate(dateStr, DateFormat.HH_MM);
                    }
                }
            }
        }
        return date;
    }

    /**
     * @return Date    返回类型
     * @throws
     * @Title: getStartTime
     * @Description: 获取当天的开始时间
     */
    public static Date getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        if (date != null) {
            todayStart.setTime(date);
        }
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * @return Date    返回类型
     * @throws
     * @Title: getEndTime
     * @Description: 获取当天的开始结束时间
     */
    public static Date getEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        if (date != null) {
            todayEnd.setTime(date);
        }
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 取Java虚拟机系统时间, 返回当前时间戳
     *
     * @return Timestamp类型的时间
     */
    public static Timestamp getSysTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 返回当前日期
     *
     * @return 只返回String格式的日期，yyyy-MM-dd， 长10位
     */
    public static String getSysDate() {
        return new Timestamp(System.currentTimeMillis()).toString().substring(0, 10);
    }


    /**
     * 返回当前日期和时间
     *
     * @return 返回String格式的日期和时间, yyyy-MM-dd HH:mm:ss， 长19位
     */
    public static String getSysDateTime() {
        return new Timestamp(System.currentTimeMillis()).toString().substring(0, 19);
    }

    /**
     * 返回当前日期和时间+毫秒
     *
     * @return 返回String格式的日期和时间, yyyy-MM-dd HH:mm:ss.SSS， 长23位
     */
    public static String getSysDateTimeMillis() {
        String str = new Timestamp(System.currentTimeMillis()).toString();
        if (str.length() >= 23) {
            return str.substring(0, 23);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            for (int i = 0; i < 23 - str.length(); i++) {
                sb.append("0");
            }
            return sb.toString();
        }
    }

    /**
     * 字符串转时间戳
     *
     * @param strDate YYYY-MM-DD HH24:MI:SS格式的字符串
     * @return 时间戳
     */
    public static Timestamp getTimestamp(String strDate) {
        if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }
        return Timestamp.valueOf(formatOmnipotent(strDate));
    }


    /**
     * 万能日期转化器，将各种格式转化为yyyy-mm-dd hh:mm:ss[.f...]
     *
     * @param str
     * @return
     */
    public static String formatOmnipotent(String str) {
        if (str == null) {
            return str;
        }
        if (str.trim().length() == 0 || str.trim().matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$")) {
            return str.trim();
        }
        //处理2010/03/01
        String strDate = str.trim().replaceAll("/", "-");
        //处理2010-01-01T00:00:00
        strDate = str.trim().replaceAll("T", " ");
        //处理'2010-03-01
        strDate = strDate.replaceAll("'", "");
        if (strDate.indexOf("-") < 0) {
            if (strDate.length() >= 16) { //处理20100301235959
                strDate = strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8)
                        + " " + strDate.substring(8, 10) + ":" + strDate.substring(10, 12) + ":" + strDate.substring(12, 14);
            } else if (strDate.length() >= 14) { //处理201003012359
                strDate = strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8)
                        + " " + strDate.substring(8, 10) + ":" + strDate.substring(10, 12) + ":00";
            } else if (strDate.length() >= 8) { //处理20100301
                strDate = strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
            }
        }

        //处理03-01-2010
        if (strDate.length() - strDate.lastIndexOf("-") == 4) {
            strDate = strDate.substring(strDate.lastIndexOf("-") + 1) + "-" + strDate.substring(0, strDate.indexOf("-")) + "-" + strDate.substring(strDate.indexOf("-") + 1, strDate.lastIndexOf("-"));
        }

        if (strDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return strDate + " 00:00:00";
        } else if (strDate.matches("^\\d{4}-(\\d{1,2})-(\\d{1,2})$")) {
            Matcher ma = Pattern.compile("^(\\d{4}-)(\\d{1,2})(-)(\\d{1,2})$").matcher(strDate);
            StringBuilder sb = new StringBuilder();
            if (ma.find()) {
                sb.append(ma.group(1));
                sb.append(ma.group(2).length() == 1 ? "0" + ma.group(2) : ma.group(2));
                sb.append(ma.group(3));
                sb.append(ma.group(4).length() == 1 ? "0" + ma.group(4) : ma.group(4));
            }
            sb.append(" 00:00:00");
            return sb.toString();
        } else if (strDate.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d*)?$")) {
            Matcher ma = Pattern.compile("^(\\d{4}-)(\\d{1,2})(-)(\\d{1,2})( )(\\d{1,2})(:)(\\d{1,2})(:)(\\d{1,2})(\\.\\d*)?$").matcher(strDate);
            StringBuilder sb = new StringBuilder();
            if (ma.find()) {
                sb.append(ma.group(1));
                sb.append(ma.group(2).length() == 1 ? "0" + ma.group(2) : ma.group(2));
                sb.append(ma.group(3));
                sb.append(ma.group(4).length() == 1 ? "0" + ma.group(4) : ma.group(4));
                sb.append(ma.group(5));
                sb.append(ma.group(6).length() == 1 ? "0" + ma.group(6) : ma.group(6));
                sb.append(ma.group(7));
                sb.append(ma.group(8).length() == 1 ? "0" + ma.group(8) : ma.group(8));
                sb.append(ma.group(9));
                sb.append(ma.group(10).length() == 1 ? "0" + ma.group(10) : ma.group(10));
                sb.append(ma.group(11) != null && ma.group(11).length() > 1 ? ma.group(11) : "");
            }
            return sb.toString();
        } else {
            log.error("Format Date (" + str + ") failed");
            return str;
        }
    }

    /**
     * 获得本地化日历
     *
     * @param dateDesc YYYY-MM-DD HH24:MI:SS 格式的字符串
     * @return
     */
    public static Calendar getCalendar(String dateDesc) {
        Calendar c = Calendar.getInstance(DateUtil.DEFAULT_CHINA_TIMEZONE, DateUtil.DEFAULT_CHINA_LOCALE);
        c.setTime(DateUtil.getTimestamp(dateDesc));
        return c;
    }

    /**
     * 获得本地化的时间
     *
     * @param longDate 时间的长整数
     * @return
     */
    public static Calendar getCalendar(long longDate) {
        Calendar c = Calendar.getInstance(DateUtil.DEFAULT_CHINA_TIMEZONE, DateUtil.DEFAULT_CHINA_LOCALE);
        c.setTimeInMillis(longDate);
        return c;
    }

    /**
     * 获得格式化的日期和时间描述
     *
     * @param longDate 时间的长整数
     * @return YYYY-MM-DD HH24:MI:SS 格式的字符串
     */
    public static String getDateTime(long longDate) {
        return new Timestamp(longDate).toString().substring(0, 19);
    }

    /**
     * 由毫秒数得到小时数
     *
     * @param longDate
     * @return
     */
    public static double getHourNumberByLong(long longDate) {
        return longDate / 60 * 60 * 1000;
    }

    /**
     * 判断thisDate是否是今天
     *
     * @param thisDate
     * @return
     */
    public static boolean isToday(Date thisDate) {
        String today = getDateTime(System.currentTimeMillis());
        String thisDateCal = getDateTime(thisDate.getTime());
        if (today.substring(0, 10).endsWith(thisDateCal.substring(0, 10))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断long1-long2和long3-long4区域是否重叠
     *
     * @param long1
     * @param long2
     * @param long3
     * @param long4
     * @return
     */
    public static boolean isOverlap(long long1, long long2, long long3, long long4) {
        boolean returnValue = false;
        if (long3 >= long1 && long3 < long2) {
            returnValue = true;
        } else if (long4 <= long2 && long4 > long1) {
            returnValue = true;
        } else if (long3 < long1 && long4 > long2) {
            returnValue = true;
        }
        return returnValue;
    }

    /**
     * 比较compareDate是否比当前时间早minTime，默认三天内
     *
     * @param compareDate 要比较的时间
     * @return
     */
    public static boolean isNew(long compareDate) {
        return isNew(compareDate, 3 * ONE_DAY_MILLIS);
    }

    /**
     * 比较compareDate是否比当前时间早minTime
     *
     * @param compareDate 要比较的时间
     * @param minTime     最小差距
     * @return
     */
    public static boolean isNew(long compareDate, long minTime) {
        if (System.currentTimeMillis() - compareDate > minTime) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取前一天日期yyyyMMdd
     *
     * @return 返回的日期格式为yyyyMMdd
     */
    public static String getYestoday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }

    /**
     * 获得SQL日期戳
     *
     * @param yearMonthDay
     * @return
     */
    public static java.sql.Date getSqlDate(String yearMonthDay) {
        if (yearMonthDay == null || yearMonthDay.trim().length() == 0) {
            return null;
        }
        Timestamp ts = getTimestamp(yearMonthDay);
        if (ts == null) {
            return null;
        } else {
            return new java.sql.Date(ts.getTime());
        }
    }

}