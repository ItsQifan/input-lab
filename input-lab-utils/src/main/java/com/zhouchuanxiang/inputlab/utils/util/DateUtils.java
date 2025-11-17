package com.zhouchuanxiang.inputlab.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {
    }

    private static final long ONE_DAY_MILSEC = 24 * 60 * 60 * 1000L;
    private static final long FIRST_DAY_OFFSET_MILSEC = 8 * 60 * 60 * 1000L;
    private static final long ONE_DAY_SEC = 24 * 60 * 60L;

    /**
     * 日期格式 yyyy-MM
     */
    public static final String DATE_PATTERN_MONTH = "yyyy-MM";
    /**
     * 年月份
     */
    public static final String DATE_PATTERN_MONTH_SHORT = "yyyyMM";
    /**
     * 日期格式 yyyy-MM-dd
     */
    public static final String DATE_PATTERN_SHORT = "yyyy-MM-dd";
    /**
     * 日期格式  -- yyyyMMdd
     */
    public static final String DATE_COMPRESS_PATTERN = "yyyyMMdd";
    /**
     * 日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_PATTERN_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式 yyyyMMddHHmmss
     */
    public static final String DATE_PATTERN_LONG_COMPRESS = "yyyyMMddHHmmss";

    /**
     * 日期格式 yyMMddHHmm
     */
    public static final String MINUTE_LONG_COMPRESS = "yyMMddHHmm";

    public static final int DATE_START_INDEX_OF_DEFAULT_PATTERN = 8;

    private static final String INVALID_PARAM_MSG = "The date could not be null!";

    public static final String DATE_PATTERN_MOUTHDAYTIME = "MMddHHmmss";

    /**
     * 获取指定时间的下一天
     *
     * @param date 字符串格式日期(yyyy-MM-dd)
     * @return
     * @throws ParseException
     */
    public static String getNextDay(String date) throws ParseException {
        if (date == null || date.equals("")) {
            return null;
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_PATTERN_SHORT);
        Date datestamp = sDateFormat.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(datestamp);
        c.add(Calendar.DAY_OF_MONTH,1);
        date = sDateFormat.format(c.getTime());
        return date;
    }

    /**
     * 获取N年后的时间
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getAfterYearDay(Date date,int num) throws ParseException {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR,num);
        return c.getTime();
    }


    /**
     * 获取其他日期
     *
     * @param num
     * @return
     * @throws Exception
     */
    public static String getOtherDay(int num,String fomat) throws Exception {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(fomat);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,num);
        String date = sDateFormat.format(c.getTime());
        return date;
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrDate() {
        Date date_time = new Date();
        return FormatDate(date_time,DATE_COMPRESS_PATTERN);
    }

    public static String getCurrTime() {
        Date date_time = new Date();
        return FormatDate(date_time, "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrDat(){
        Date date_time = new Date();
        return FormatDate(date_time,DATE_PATTERN_SHORT);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrDate(String format) {
        Date date_time = new Date();
        return FormatDate(date_time,format);
    }

    /**
     * 对日期进行格式化
     *
     * @param date 日期
     * @param sf   日期格式
     * @return 字符串
     */
    public static String FormatDate(Date date,String sf) {
        if (date == null)
            return "";
        SimpleDateFormat dateformat = new SimpleDateFormat(sf);
        return dateformat.format(date);
    }

    /**
     * 根据时间参数判断时间是否在minTime-maxTime之间
     * 如：2014-08:20 10:09:20是否在06:10~~12:00范围内
     * 2014-08:20 10:09:20是否在23:10~~06:00范围内
     *
     * @param time    字符串时间 格式：yyyy-MM-dd HH:mm:ss
     * @param minTime 开始时间 格式:HH:mm
     * @param maxTime 结束时间 格式:HH:mm
     * @return true-在区间内
     */
    public static boolean isDuring(String time,String minTime,String maxTime) {
        boolean bool = false;
        String subTime = time.substring(11,19);//得到时间中时分秒
        String newMinTime = minTime + ":00";//开始时间增加秒
        String newMaxTime = maxTime + ":00";//结束时间增加秒

        if (newMaxTime.compareTo(newMinTime) >= 0) {//判断开始时间是否大于结束时间，是则表示跨天
            if (newMinTime.compareTo(subTime) <= 0 && newMaxTime.compareTo(subTime) >= 0) {
                bool = true;
            }
        } else {
            if ((newMinTime.compareTo(subTime) <= 0 && "23:59:59".compareTo(subTime) >= 0) ||
                    ("00:00:00".compareTo(subTime) <= 0 && newMaxTime.compareTo(subTime) >= 0)) {
                bool = true;
            }
        }
        return bool;
    }

    /**
     * 根据时间判断相差是否>=day天
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(Date)相差是否大于等于60天
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是注册时间、最后一次交易时间等
     * @return true-相差>=day天
     * @parm day:天数
     */
    public static boolean isOuterD(String maxTime,Date minTime,int day) {
        try {
            if (maxTime == null || minTime == null || "".equals(maxTime)) {
                return false;
            }
            int i = differDays(maxTime,minTime);
            if (i >= day) {
                return true;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return false;
    }

    /**
     * 根据时间判断相差是否<=day天
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(Date)相差是否小于等于60天
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是注册时间、最后一次交易时间等
     * @param day:天数
     * @return true-相差<=day天
     */
    public static boolean isInnerD(String maxTime,Date minTime,int day) {
        try {
            if (maxTime == null || minTime == null || "".equals(maxTime)) {
                return false;
            }
            int i = differDays(maxTime,minTime);
            if (i <= day) {
                return true;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return false;
    }

    /**
     * 根据时间判断相差是否>=day天
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(String)相差是否大于等于60天
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等等
     * @return true-相差>=day天
     * @parm day:天数
     */
    public static boolean isOuterD(String maxTime,String minTime,int day) {
        try {
            if (maxTime == null || minTime == null || "".equals(maxTime) || "".equals(minTime)) {
                return false;
            }
            int i = differDays(maxTime,minTime);
            if (i >= day) {
                return true;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return false;
    }

    /**
     * 根据时间判断相差是否<=day天
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(String)相差是否小于等于60天
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @param day:天数
     * @return true-相差<=day天
     */
    public static boolean isInnerD(String maxTime,String minTime,int day) {
        try {
            if (maxTime == null || minTime == null || "".equals(maxTime) || "".equals(minTime)) {
                return false;
            }
            int i = differDays(maxTime,minTime);
            if (i <= day) {
                return true;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return false;
    }

    /**
     * 根据时间判断相差是否<=day天
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(String)相差是否小于等于60天
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @param day:天数
     * @return true-相差<=day天
     */
    public static boolean isInnerDSameFormat(String maxTime,String minTime,int day) {
        try {
            if (maxTime == null || minTime == null || "".equals(maxTime) || "".equals(minTime)) {
                return false;
            }
            int i = differDaysSameFormat(maxTime,minTime);
            if (i <= day) {
                return true;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return false;
    }


    /**
     * 两时间相差天数
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(Date)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是注册时间、最后一次交易时间等
     * @return 相差的天数
     * @throws ParseException
     */
    private static int differDays(String maxTime,Date minTime) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN_LONG); //格式定义 24小时制
        Date tmpTxtm = df.parse(maxTime);//将交易时间转换为Date类型
        long tx = tmpTxtm.getTime();//交易时间转换为毫秒
        long rg = minTime.getTime();//最后一次成功登录转换毫秒
        return (int) ((tx - rg) / (1000 * 60 * 60 * 24));//得到两个时间相差的天数;
    }

    /**
     * 两时间相差天数
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(Date)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @return 相差的天数
     * @throws ParseException
     */
    private static int differDays(String maxTime,String minTime) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN_LONG); //格式定义 24小时制
        DateFormat df2 = new SimpleDateFormat(DATE_PATTERN_LONG_COMPRESS); //格式定义 24小时制
        Date tmpMax = df.parse(maxTime);//转换为Date类型
        Date tmpMin;
        if (minTime.indexOf('-') < 0) {
            tmpMin = df2.parse(minTime);
        } else {
            tmpMin = df.parse(minTime);
        }
        long lMax = tmpMax.getTime();//转换为毫秒
        long lMin = tmpMin.getTime();//转换毫秒
        return (int) ((lMax - lMin) / (1000 * 60 * 60 * 24));//得到两个时间相差的天数
    }

    /**
     * 两时间相差天数
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(Date)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @return 相差的天数
     * @throws ParseException
     */
    private static int differDaysSameFormat(String maxTime,String minTime) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN_SHORT); //格式定义 24小时制
        DateFormat df2 = new SimpleDateFormat(DATE_PATTERN_SHORT); //格式定义 24小时制
        Date tmpMax = df.parse(maxTime);//转换为Date类型
        Date tmpMin;
        if (minTime.indexOf('-') < 0) {
            tmpMin = df2.parse(minTime);
        } else {
            tmpMin = df.parse(minTime);
        }
        long lMax = tmpMax.getTime();//转换为毫秒
        long lMin = tmpMin.getTime();//转换毫秒
        return (int) ((lMax - lMin) / (1000 * 60 * 60 * 24));//得到两个时间相差的天数
    }

    /**
     * 两日期相差天数
     * 如：20140820 (String)与 20140508 (String)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @return 相差的天数
     * @throws ParseException
     */
    public static int dateInterval(String maxTime,String minTime) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_COMPRESS_PATTERN);
        Date tmpMax = df.parse(maxTime);//转换为Date类型
        Date tmpMin = df.parse(minTime);//转换为Date类型
        long lMax = tmpMax.getTime();//转换为毫秒
        long lMin = tmpMin.getTime();//转换毫秒
        return (int) ((lMax - lMin) / (1000 * 60 * 60 * 24));//得到两个时间相差的天数
    }

    /**
     * 两日期相差天数
     * 如：2014-08-20 (String)与 2014-05-08 (String)相差
     *
     * @param maxTime：大的时间
     * @param minTime：小的时间
     * @return 相差的天数
     * @throws ParseException
     */
    public static int dateSubtraction(String maxTime,String minTime) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN_SHORT);
        Date tmpMax = df.parse(maxTime);//转换为Date类型
        Date tmpMin = df.parse(minTime);//转换为Date类型
        long lMax = tmpMax.getTime();//转换为毫秒
        long lMin = tmpMin.getTime();//转换毫秒
        return (int) ((lMax - lMin) / (1000 * 60 * 60 * 24));//得到两个时间相差的天数
    }

    /**
     * 根据时间判断相差是否<=hour小时
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(Date)相差是否小于等于36小时
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是注册时间、最后一次交易时间等
     * @param
     * @return true-相差<=hour小时
     */
    public static boolean isInnerH(String maxTime,Date minTime,int hour) {
        try {
            if (maxTime == null || minTime == null || "".equals(maxTime)) {
                return false;
            }
            int i = differHours(maxTime,minTime);
            if (i <= hour) {
                return true;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return false;
    }

    /**
     * 根据时间判断相差是否<=day小时
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(String)相差是否小于等于60小时
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @param
     * @return true-相差<=day小时
     */
    public static boolean isInnerH(String maxTime,String minTime,int hour) {
        try {
            if (maxTime == null || minTime == null || "".equals(maxTime) || "".equals(minTime)) {
                return false;
            }
            int i = differHours(maxTime,minTime);
            if (i <= hour) {
                return true;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return false;
    }

    /**
     * 判断两个时间是否相等
     *
     * @param maxTime 字符串类型
     * @param minTime 日期类型
     * @return true 相等  false 不相等
     */
    public static boolean isEqual(Date maxTime,Date minTime) {
        boolean bool = false;
        try {
            long l = differMillis(maxTime,minTime);
            if (l == 0) {
                bool = true;
                return bool;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return bool;
    }

    /**
     * 两时间相差小时数
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(Date)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是注册时间、最后一次交易时间等
     * @return 相差的小时
     * @throws ParseException
     */
    private static int differHours(String maxTime,Date minTime) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN_LONG); //格式定义 24小时制
        Date tmpTxtm = df.parse(maxTime);//将交易时间转换为Date类型
        long tx = tmpTxtm.getTime();//交易时间转换为毫秒
        long rg = minTime.getTime();//最后一次成功登录转换毫秒
        return (int) ((tx - rg) / (1000 * 60 * 60));//得到两个时间相差的天数
    }

    /**
     * 两时间相差小时数
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(String)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @return 相差的小时
     * @throws ParseException
     */
    private static int differHours(String maxTime,String minTime) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN_LONG); //格式定义 24小时制
        DateFormat df2 = new SimpleDateFormat(DATE_PATTERN_LONG_COMPRESS); //格式定义 24小时制
        Date tmpMax = df.parse(maxTime);//转换为Date类型
        Date tmpMin;
        if (minTime.indexOf('-') < 0) {
            tmpMin = df2.parse(minTime);
        } else {
            tmpMin = df.parse(minTime);
        }
        long lMax = tmpMax.getTime();//转换为毫秒
        long lMin = tmpMin.getTime();//转换毫秒
        return (int) ((lMax - lMin) / (1000 * 60 * 60));//得到两个时间相差的天数
    }

    /**
     * 两时间相差分钟数
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(String)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @return 相差的分钟
     * @throws ParseException
     */
    public static boolean differMinute(String maxTime,String minTime,String minute) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN_LONG); //格式定义 24小时制
        DateFormat df2 = new SimpleDateFormat(DATE_PATTERN_LONG_COMPRESS); //格式定义 24小时制
        Date tmpMax = df.parse(maxTime);//转换为Date类型
        Date tmpMin;
        if (minTime.indexOf('-') < 0) {
            tmpMin = df2.parse(minTime);
        } else {
            tmpMin = df.parse(minTime);
        }
        long lMax = tmpMax.getTime();//转换为毫秒
        long lMin = tmpMin.getTime();//转换毫秒
        int i = (int) ((lMax - lMin) / (1000 * 60));
        if(i < Integer.parseInt(minute)){
            return true;
        }
        return false;
    }

    /**
     * 两时间相差的毫秒数
     * 如：2014-08-20 10:10:30(String)与2014-05-08 10:30:20(Date)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是最后一次绑卡时间等
     * @return 相差的毫秒数
     * @throws ParseException
     */
    public static long differMillis(Date maxTime,Date minTime) throws ParseException {
        if (null == maxTime || null == minTime) {
            return 0L;
        }
        long lMax = maxTime.getTime();//转换为毫秒
        long lMin = minTime.getTime();//转换毫秒
        return lMax - lMin;
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static String getCurrYear() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static String getCurrMonth() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.MONTH) + 1);
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(Date specifiedDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(specifiedDay);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE,day + 1);
        String dayAfter = new SimpleDateFormat(DATE_PATTERN_SHORT).format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得当月最后一天
     *
     * @param date
     * @return
     */
    public static String lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH,value);
        return DateUtils.FormatDate(cal.getTime(),"yyyy-MM-dd");
    }

    /**
     * 两时间相差天数
     * 如：2014-08-20 10:10:30(Date)与2014-05-08 10:30:20(Date)相差
     *
     * @param maxTime：大的时间，一般是交易时间
     * @param minTime：小的时间，一般是注册时间、最后一次交易时间等
     * @return 相差的天数
     * @throws ParseException
     */
    public static long differDates(Date maxTime,Date minTime) throws ParseException {
        if (null == maxTime || null == minTime) {
            return 0L;
        }
        long tx = maxTime.getTime();//交易时间转换为毫秒
        long rg = minTime.getTime();//最后一次成功登录转换毫秒
        return (tx - rg) / (1000 * 60 * 60 * 24);//得到两个时间相差的天数
    }


    public static String geFormatDate(Date date) {
        if (null == date) {
            return null;
        }
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
    }

    /**
     * 获取当前日期前一天
     *
     * @return
     */
    public static String getBeforeDate(int i) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_PATTERN_SHORT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,i);
        String date = sDateFormat.format(c.getTime());
        return date;
    }

    public static Date getCurrDateTime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime,Date startTime,Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static Date formatStr(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_COMPRESS_PATTERN);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /***
     *  将时间戳转换为字符串日期- yyyyMMdd
     * @param str
     * @return
     */
    public static String formatStr(Date str) {
        DateFormat format = new SimpleDateFormat(DATE_COMPRESS_PATTERN);
        String date  = format.format(str);
        return date;
    }

    public static String formatDateStr(Date date) {
        DateFormat format = new SimpleDateFormat(DATE_PATTERN_LONG);
        return format.format(date);
    }

    /**
     * 获取今天0点的信息
     * @return
     */
    public static Date getToDateDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
        cal.set(Calendar.MINUTE, 0);//控制分
        cal.set(Calendar.SECOND, 0);//控制秒
        System.out.println(cal.getTime());
        System.out.println(cal.getTimeInMillis());
        return cal.getTime();
    }

    /**
     * 获取某个时间点的时间
     * @param date
     * @param addHours
     * @return
     */
    public static Date addHours(Date date, int addHours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, addHours);
        return calendar.getTime();
    }

}
