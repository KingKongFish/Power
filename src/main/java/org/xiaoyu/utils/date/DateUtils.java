package org.xiaoyu.utils.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.xiaoyu.utils.data.StringKit;

/**
 * 日期和时间工具类.
 * 
 * @author peilongwu
 * @date 2016-09-08
 */
public class DateUtils {
  
  private static String currentTime; //当前时间
  private static String currentDate; //当前日期
  public static final int MILLISECONDS_PER_SECOND = 1000;

  /**
   * 转换Date类型的数据为yyyy/MM/dd格式的时间字符串.
   * @param date 日期
   * @return yyyy/MM/dd格式字符串
   */
  public static String convertDateToStr1(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    String strdate = "";
    try {
      strdate = formatter.format(date);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return strdate;
  }

  /**
   * 转换Date类型的数据为yyyy-MM-dd格式的时间字符串.
   *
   * @param date 日期
   * @return yyyy-MM-dd格式字符串
   */
  public static String convertDateToStr2(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String strdate = "";
    try {
      strdate = formatter.format(date);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return strdate;
  }

  /**
   * 转换Date类型的数据为yyyy-MM-dd HH:mm:ss格式的时间字符串.
   *
   * @param date 日期
   * @return yyyy-MM-dd HH:mm:ss格式字符串
   */
  public static String convertDateToStr3(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String strdate = "";
    try {
      strdate = formatter.format(date);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return strdate;
  }
  
  /**
   * 字符串转换为 MM/dd/yyyy 格式的字符串.
   * 
   * @param date 字符串类型日期，格式为：MM/dd/yyyy
   * @return 日期
   */
  public static Date convertStrToDate(String date) {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    try {
      return formatter.parse(date);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * yyyy/MM/dd | yyyy-MM-dd 格式的日期字符串转换为格式为MM/dd/yyyy的字符串.
   * 
   * @param strDate 日期字符串
   * @return MM/dd/yyyy的字符串
   */
  public static String getStr(String strDate) {
    String year = strDate.substring(0, 4);
    String month = strDate.substring(5, 7);
    String day = strDate.substring(8, 10);
    strDate = month + "/" + day + "/" + year;
    return strDate;
  }
  
  /**
   * 获取日期字符串，格式为：yyyyMMddHHmmss.
   * 
   * @param date 日期
   * @return yyyyMMddHHmmss格式的字符串
   */
  public static String getStr(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String strdate = "";
    try {
      strdate = formatter.format(date);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return strdate;
  }

  /**
   * 计算与当前日期相差几年（year）的日期.
   * 
   * @param date 日期
   * @param year 差额年
   * @return 日期
   */
  public static Date calcDate(Date date, int year) {
    Calendar rili = Calendar.getInstance();
    rili.setTime(date);
    rili.add(Calendar.YEAR, year);
    date = rili.getTime();
    return date;
  }

  /**
   * 计算与当前日期几天（day）的日期.
   * 
   * @param date 日期
   * @param day 差额天数
   * @return 日期
   */
  public static Date calcDateDay(Date date, int day) {
    Calendar rili = Calendar.getInstance();
    rili.setTime(date);
    rili.add(Calendar.DAY_OF_YEAR, day);
    date = rili.getTime();
    return date;
  }

  /**
   * 日期字符串转换为yyyy-MM-dd HH:mm:ss格式的字符串.
   * 
   * @param strdate 日期字符串
   * @return 日期
   */
  public static Date convertDate(String strdate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    ParsePosition pos = new ParsePosition(1);
    try {
      date = formatter.parse(strdate, pos);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 日期转换为 MM/dd/yyyy 格式的日期.
   * 
   * @param date 日期
   * @return 转换后的日期
   */
  public static Date getCurtDate(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    try {
      date = formatter.parse(formatter.format(date));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
  
  /**
   * 日期格式字符串转换为 yyyy-MM-dd HH:mm:ss 格式的字符串.
   * 
   * @param strdate 日期格式字符串
   * @return yyyy-MM-dd HH:mm:ss 格式的字符串
   */
  public static String convertDateToGMT8(String strdate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    try {
      date = formatter.parse(strdate);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
    long chinaMills = date.getTime() + timeZone.getRawOffset();
    return getTime(chinaMills);
  }
  
  /**
   * 日期格式 yyyy-MM-dd HH:mm:ss 字符串，转换为日期.
   * 
   * @param strdate 日期格式字符串
   * @return 日期
   */
  public static Date convertDateToStandard(String strdate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    try {
      date = formatter.parse(strdate);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 格式：yyyy/MM/dd 的字符串转换为日期.
   * 
   * @param strdate yyyy/MM/dd 字符串
   * @return Date 日期
   */
  public static Date convertDateToStandard2(String strdate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    Date date = null;
    try {
      date = formatter.parse(strdate);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 日期转换为格式：MM/dd/yyyy 的字符串.
   * 
   * @param date 日期
   * @return MM/dd/yyyy 的字符串
   * 
   */
  public static String getStrDate(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    String strdate = "";
    try {
      strdate = formatter.format(date);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return strdate;
  }
  
  /**
   * 获取格式：yyyy-MM-dd 的当前日期字符串.
   */
  public static String getCurrentDate1() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    currentDate = formatter.format(nowDate);
    return currentDate;
  }

  /**
   * 获取格式：MM/dd/yyyy 的当前日期字符串.
   */
  public static Date getCurrentDate2() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date date = null;
    try {
      date = formatter.parse(formatter.format(nowDate));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
  
  /**
   * 获取当前日期字符串，格式为：yyyyMMdd.
   */
  public static String getCurrentDate3() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    currentDate = formatter.format(nowDate);
    return currentDate;
  }
  
  /**
   * 获取当前日期字符串，格式为：yyMMdd.
   */
  public static String getCurrentDate4() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
    currentDate = formatter.format(nowDate);
    return currentDate;
  }

  /**
   * 获取当前日期字符串，格式为：yyyyMMddHHmmss.
   */
  public static String getCt() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    currentTime = formatter.format(nowDate);
    return currentTime;
  }

  /**
   * 获取当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
   */
  public static Date getCurrentTime() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    try {
      date = formatter.parse(formatter.format(nowDate));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
  
  /**
   * 获取当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
   */
  public static String getCurrentTime1() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = null;
    time = formatter.format(nowDate);
    return time;
  }
  
  
  /**
   * 时间比较：
   *   0，相等
   *   1，strDate1在strDate2之前
   *   2，strDate1在strDate2之后
   *   4，异常.
   *  
   * @param strDate1 yyyy-MM-dd格式的日期字符串
   * @param strDate2 yyyy-MM-dd格式的日期字符串
   * @return 比较后的结果
   */
  public static String compareDate(String strDate1, String strDate2) {
    Date date1 = null;
    Date date2 = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    try {
      date1 = formatter.parse(strDate1);
      date2 = formatter.parse(strDate2);
      if (date1.equals(strDate2)) {
        return "0";
      } else if (date1.before(date2)) {
        return "1";
      } else {
        return "2";
      }
    } catch (ParseException e) {
      e.printStackTrace();
      return "4";
    }

  }

  /**
   * 获取当前年份.
   *
   * @return 当前年份字符串
   */
  public static String getCurrentYear() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
    return formatter.format(nowDate);
  }
  
  /**
   * 获取传入时间的年份.
   * 
   * @param date 时间戳
   * @return 传入时间的年份字符串
   */
  public static String getCurrentYear(Long date) {
    Date nowDate = new Date(date);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
    return formatter.format(nowDate);
  }

  /**
   * 获取当前月份.
   */
  public static String getCurrentMonth() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MM");
    return formatter.format(nowDate);
  }
  
  /**
   * 获取当前传入日期的月份.
   * 
   * @param date 时间戳
   */
  public static String getCurrentMonth(Long date) {
    Date nowDate = new Date(date);
    SimpleDateFormat formatter = new SimpleDateFormat("MM");
    return formatter.format(nowDate);
  }

  /**
   *
   * @param date 时间戳
   * @param format 格式化
   * @return
   */
  public static String getCurrentMonth(Long date,String format) {
    Date nowDate = new Date(date);
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(nowDate);
  }
  /**
   * 获取当前日.
   */
  public static String getCurrentDay() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd");
    return formatter.format(nowDate);
  }
  
  /**
   * 获取当前传入日期的日.
   * 
   * @param date 时间戳
   */
  public static String getCurrentDay(Long date) {
    Date nowDate = new Date(date);
    SimpleDateFormat formatter = new SimpleDateFormat("dd");
    return formatter.format(nowDate);
  }
  
  /**
   * 获取传入日期字符串的年份，传入为格式：yyyy?MM?dd
   */
  public static String getYear(String date) {
    return date.substring(0, 4);
  }

  /**
   * 获取传入日期字符串的月份，格式：yyyy?MM?dd
   */
  public static String getMonth(String date) {
    return date.substring(5, 7);
  }
  
  /**
   * 获取传入日期字符串的年月，格式：yyyyMM
   */
  public static String getMonth() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
    currentDate = formatter.format(nowDate);
    return currentDate;
  }

  /**
   * 获取当前年月，格式：yyMM
   */
  public static String getMonth1() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyMM",Locale.CHINESE);
    currentDate = formatter.format(nowDate);
    return currentDate;
  }
  
  /**
   * 获取传入日期字符串的年月，格式：yyyy-MM.
   */
  public static String getMonth2() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    currentDate = formatter.format(nowDate);
    return currentDate;
  }
  
  /**
   * 获取当前月份的时间戳，最终转换为：yyyy-MM-01 00:00:00 时间戳.
   */
  public static Long getMonth3() {
    String month = DateUtils.getMonth2() + "-01 00:00:00";
    Date date = DateUtils.convertDateToStandard(month);
    return date.getTime();
  }
  
  /**
   * 获取传入年份第一个月，最终转换为：yyyy-01-01 00:00:00 时间戳.
   */
  public static Long getFirstMonth(String year) {
    String month = year + "-01-01 00:00:00";
    Date date = DateUtils.convertDateToStandard(month);
    return date.getTime();
  }
  
  
  /**
   * 获取当前月份的第一天，转换为：yyyy-MM-01 的字符串.
   */
  public static String getMonthFirst() {
    return DateUtils.getMonth2() + "-01";
  }
  
  /**
   * 获取下一月第一天：yyyy-MM-01.
   */
  public static String getMonthLast() {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.add(Calendar.MONTH, 1);
    cale.set(Calendar.DAY_OF_MONTH, 0);
    return getDate2(cale.getTime().getTime());
  }
  
  /**
   * 获取距离当前时间第N天：yyyy-MM-01.
   */
  public static String getDifferDays(int days) {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.setTime(new Date()); 
    cale.set(Calendar.DATE, cale.get(Calendar.DATE) + days);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(cale.getTime());
  }
  
  /**
   * 获取距离当前时间第N天：yyyy-MM-01.
   */
  public static String getDifferDays(Date date, int days) {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.setTime(date); 
    cale.set(Calendar.DATE, cale.get(Calendar.DATE) + days);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(cale.getTime());
  }
  
  /**
   * 获取距离当前时间第几个小时.
   */
  public static String getDifferHours(Date date, int hours) {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.setTime(date); 
    cale.set(Calendar.DATE, cale.get(Calendar.HOUR) + hours);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(cale.getTime());
  }
  
  /**
   * 获取距离当前时间第几分钟.
   */
  public static String getDifferMinutes(Date date, int minutes) {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.setTime(date); 
    cale.set(Calendar.DATE, cale.get(Calendar.MINUTE) + minutes);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(cale.getTime());
  }
  
  /**
   * 获取距离当前时间第几个小时.
   */
  public static String getDifferSeconds(Date date, int seconds) {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.setTime(date); 
    cale.set(Calendar.DATE, cale.get(Calendar.SECOND) + seconds);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(cale.getTime());
  }
  
  /**
   * 获取距离当前时间第N天：yyyy-MM-01.
   */
  public static String getDifferMonths(Date date, int month) {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.setTime(date); 
    cale.set(Calendar.DATE, cale.get(Calendar.MONTH) + month);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(cale.getTime());
  }
  
  /**
   * 获取距离当前月份的月份.
   */
  public static Long getDifferMonthsTime(Long time, int month) {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.setTime(new Date(time)); 
    cale.set(Calendar.MONTH, cale.get(Calendar.MONTH) + month);
    return cale.getTime().getTime();
  }

  /**
   * 从格式：yyyy?MM?dd 的获取dd字符串.
   */
  public static String getDay(String date) {
    return date.substring(8, 10);
  }
  
  /**
   * 从时间字符串中获取日期字符串，格式：yyyy?MM?dd 的字符串.
   */
  public static String getOnlyDate(String time) {
    return time.substring(0, 10);
  }

  /**
   * 获取当前时间，并转换为数据库Timestamp类型.
   */
  public static Timestamp getcurTime() {
    Date date = new Date();
    return (new Timestamp(date.getTime()));
  }

  /**
   * 字符串转换为指定格式的日期.
   * @param mask 日期格式
   * @param strDate 日期字符串
   * @return 日期
   */
  public static Date strToDate(String mask, String strDate) {
    SimpleDateFormat format = new SimpleDateFormat(mask);
    Date date = null;
    try {
      date = format.parse(strDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 字符串转换为日期型： 
   *  1、若只为年份，如：2014，自动补为：2014-01-01 
   *  2、若为年月，如：2014-01，自动补为：2014-01-01
   * @param strDate
   * @return
   */
  public static Date strToDate(String strDate) {
    strDate = strDate.trim();
    if (strDate.length() == 4) {
      strDate = strDate + "-01-01";
      return strToDate("yyyy-MM-dd", strDate);
    } else if (strDate.length() == 7) {
      strDate = strDate + "-01";
      return strToDate("yyyy-MM-dd", strDate);
    } else if (strDate.length() == 10) {
      return strToDate("yyyy-MM-dd", strDate);
    } else {
      throw new RuntimeException("传入的日期格式有误");
    }
  }
  
  /**
   * 字符串转换为yyyy-MM-dd字符串： 
   *  1、若只为年份，如：2014，自动补为：2014-01-01 
   *  2、若为年月，如：2014-01，自动补为：2014-01-01
   * @param strDate
   * @return
   */
  public static String strToDateString(String strDate) {
    strDate = strDate.trim();
    if (strDate.length() == 4) {
      strDate = strDate + "-01-01";
      return strDate;
    } else if (strDate.length() == 7) {
      strDate = strDate + "-01";
      return strDate;
    } else if (strDate.length() == 10) {
      return strDate;
    } else {
      throw new RuntimeException("传入的日期格式有误");
    }
  }

  /**
   * 转换字符串为：yyyy-MM-dd HH:mm:ss 为Date
   * @param strTime
   * @return
   */
  public static Date strToTime(String strTime) {
    strTime = strTime.trim();
    if (strTime.length() == 4) {
      strTime = strTime + "-01-01 00:00:00";
      return strToDate("yyyy-MM-dd HH:mm:ss", strTime);
    } else if (strTime.length() == 7) {
      strTime = strTime + "-01 00:00:00";
      return strToDate("yyyy-MM-dd HH:mm:ss", strTime);
    } else if (strTime.length() == 10) {
      strTime = strTime + " 00:00:00";
      return strToDate("yyyy-MM-dd HH:mm:ss", strTime);
    } else if (strTime.length() == 13) {
      strTime = strTime + ":00:00";
      return strToDate("yyyy-MM-dd HH:mm:ss", strTime);
    } else if (strTime.length() == 16) {
      strTime = strTime + ":00";
      return strToDate("yyyy-MM-dd HH:mm:ss", strTime);
    } else if (strTime.length() == 19) {
      return strToDate("yyyy-MM-dd HH:mm:ss", strTime);
    } else {
      throw new RuntimeException("传入的日期格式有误");
    }
  }
  
  /**
   * 日期字符串格式化为：yyyy-MM-dd HH:mm:ss 的字符串.
   * 
   * @param strTime 日期格式的字符串.
   * @return
   */
  public static String strToTimeString(String strTime) {
    strTime = strTime.trim();
    if (strTime.length() == 4) {
      strTime = strTime + "-01-01 00:00:00";
    } else if (strTime.length() == 7) {
      strTime = strTime + "-01 00:00:00";
    } else if (strTime.length() == 10) {
      strTime = strTime + " 00:00:00";
    } else if (strTime.length() == 13) {
      strTime = strTime + ":00:00";
    } else if (strTime.length() == 16) {
      strTime = strTime + ":00";
    } else {
      throw new RuntimeException("传入的日期格式有误");
    }
    return strTime;
  }
  
  /**
   * 
   * 暂未启用.
   */
  public static String strToLastTimeString(String strTime) {
    strTime = strTime.trim();
    Date date = strToTime(strTime);
    if (strTime.length() == 4) {
      date = calcDate(date, 1);
      String dateString = convertDateToStr2(date);
      strTime = getYear(dateString) + "-01-01 00:00:00";
    } else if (strTime.length() == 7) {
      String dateString = getDifferMonths(date, 1);
      strTime = dateString + " 00:00:00";
    } else if (strTime.length() == 10) {
      String dateString = getDifferDays(date, 1);
      strTime = dateString + " 00:00:00";
    } else if (strTime.length() == 13) {
      strTime = getDifferHours(date, 1);
    } else if (strTime.length() == 16) {
      strTime = getDifferMinutes(date, 1);
    } else {
      throw new RuntimeException("传入的日期格式有误");
    }
    return strTime;
  }

  /**
   * 计算开始时间与结束时间相差的毫秒数.
   * 
   * @param mask 日期字符串格式
   * @param strBeginDate 开始日期
   * @param strEndDate 结束日期
   * @return 相差的毫秒数
   */
  public static long sub(String mask, String strBeginDate, String strEndDate) {
    long dateRange = 0;
    Date beginDate = strToDate(mask, strBeginDate);
    Date endDate = strToDate(mask, strEndDate);
    dateRange = endDate.getTime() - beginDate.getTime();
    return dateRange;
  }

  /**
   * 计算开始时间与结束时间相差的毫秒数.
   *  
   * @param strBeginDate 开始时间
   * @param strEndDate 结束时间
   * @return 相差的毫秒数
   */
  public static long sub(String strBeginDate, String strEndDate) {
    long dateRange = 0;
    Date beginDate = strToDate(strBeginDate);
    Date endDate = strToDate(strEndDate);
    dateRange = endDate.getTime() - beginDate.getTime();
    return dateRange;
  }

  /**
   * 计算开始时间与结束时间相差的秒数.
   *  
   * @param strBeginDate 开始时间
   * @param strEndDate 结束时间
   * @return 相差的秒数
   */
  public static int subToSecond(String strBeginDate, String strEndDate) {
    String secNum = "";
    long dateRange = sub("yyyy-MM-dd HH:mm:ss", strBeginDate, strEndDate);
    secNum = "" + (dateRange / MILLISECONDS_PER_SECOND);
    return Integer.parseInt(secNum);
  }

  /**
   * 计算开始时间与结束时间相差的秒数.
   *  
   * @param strBeginDate 开始时间
   * @param strEndDate 结束时间
   * @return 相差的秒数
   */
  public static int subToSecondyMd(String strBeginDate, String strEndDate) {
    String secNum = "";
    long dateRange = sub("yyyyMMddHHmmss", strBeginDate, strEndDate);
    secNum = "" + (dateRange / MILLISECONDS_PER_SECOND);
    return Math.abs(Integer.parseInt(secNum));
  }

  /**
   * 检查字符串是否为yyyyMMddHHmmss格式.
   * 
   * @param strdate 时间字符串
   */
  public static boolean checkCurrentTime(String strdate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    formatter.setLenient(false);
    try {
      formatter.parse(strdate);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  /**
   * 获取当前时间.
   */
  public static String currentTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new Date());
  }

  /**
   * 获取格式为：MMddHHmmss 日期字符串.
   */
  public static String getCtf() {
    Date nowDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    currentTime = formatter.format(nowDate);
    return currentTime;
  }

  /**
   * 日期字符串 + 小时 + 分钟，转换为时间.
   * 
   * @param date 当前日期
   * @param hour 当前小时
   * @param min 当前分钟
   */
  public static Date getDateTimeByString(String date, String hour, String min) {
    String tmpTime = date + " " + hour + ":" + min + ":00";// 拼凑时间格式
    return convertDateToStandard(tmpTime);// 经过转换后将时间返回
  }

  /**
   * 获取当前时间的毫秒数.
   * 
   * @return Long 毫秒数.
   */
  public static Long getTime() {
    Date nowDate = new Date();
    return nowDate.getTime();
  }
  
  /**
   * 毫秒数转换为时间字符串，格式：yyyy-MM-dd HH:mm:ss.
   * 
   * @param time 毫秒数
   * @return yyyy-MM-dd HH:mm:ss 格式字符串
   */
  public static String getTime(Long time) {
    return getDate(time, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 获取当前日期的毫秒数，计算格式为：yyyy-MM-dd 00:00:00 毫秒数.
   */
  public static Long getDate() {
    try {
      String nowDate = getCurrentDate1();
      nowDate = nowDate + " 00:00:00";
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date currentTime;
      currentTime = formatter.parse(nowDate);
      return currentTime.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 获取当前日期的毫秒数，计算格式为：yyyy-MM-dd 00:00:00 毫秒数.
   */
  public static Long getYesterday() {
    try {
      String nowDate = getDifferDays(getCurrentTime(), -1);
      nowDate = nowDate + " 00:00:00";
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date currentTime;
      currentTime = formatter.parse(nowDate);
      return currentTime.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * 根据传入的格式格式化时间.
   * 
   * @param time 毫秒数
   * @param mask 时间格式
   */
  public static String getDate(Long time, String mask) {
    Date date = new Date(time);
    SimpleDateFormat formatter = new SimpleDateFormat(mask);
    currentDate = formatter.format(date);
    return currentDate;
  }

  /**
   * 把传入的毫秒数格式化为为：yyyyMMdd 字符串.
   */
  public static String getDate(Long time) {
    return getDate(time, "yyyyMMdd");
  }
  
  /**
   * 把传入的毫秒数转换为日期.
   */
  public static Date getDate1(Long time) {
    return new Date(time);
  }

  /**
   * 把传入的毫秒数转换为格式为：yyyy-MM-dd 的字符串
   */
  public static String getDate2(Long time) {
    return getDate(time, "yyyy-MM-dd");
  }

  /**
   * 判断当前日期是否在所传入的时间段
   * 
   * @param startTime yyyyMMdd
   * @param endTime yyyyMMdd
   * @return 布尔值
   * @throws Exception 异常
   */
  public static boolean isInRightDate(String startTime, String endTime)
      throws Exception {
    String nowDate = getCurrentDate3();
    if (!StringKit.isBlank(startTime) && !StringKit.isBlank(endTime)) {
      if (Integer.parseInt(nowDate) >= Integer.parseInt(startTime)
          && Integer.parseInt(nowDate) <= Integer.parseInt(endTime)) {
        return true;
      } else {
        return false;
      }
    } else if (!StringKit.isBlank(startTime)) {
      if (Integer.parseInt(nowDate) >= Integer.parseInt(startTime)) {
        return true;
      } else {
        return false;
      }
    } else if (!StringKit.isBlank(endTime)) {
      if (Integer.parseInt(nowDate) <= Integer.parseInt(endTime)) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }
  
  /**
   * 获取当前月的日期范围，即最后一天.
   */
  public static int getMonthRange() {
    Calendar cale = null;
    cale = Calendar.getInstance();
    cale.add(Calendar.MONTH, 1);
    cale.set(Calendar.DAY_OF_MONTH, 0);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return Integer.parseInt(getDay(format.format(cale.getTime())));
  }
  
  /**
   * 获取当前月的日期范围.
   */
  public static String getMonthRangeString() {
    String monthFirst = getMonthFirst() + "," + getMonthLast();
    return monthFirst;
  }

  public static String getNumDate(Long date) {
    String year = getCurrentYear(date);
    String month = getCurrentMonth(date);
    String day = getCurrentDay(date);
    return month + "/" + day + "/" + year;
  }

  /**
   * 获取中文格式的日期字符串，格式为：yyyy年mm月dd日.
   */
  public static String getCnDate() {
    String year = getCurrentYear();
    String month = getCurrentMonth();
    String day = getCurrentDay();
    return year + "年" + month + "月" + day + "日";
  }
  
  /**
   * 把传入的日期毫秒数转换为中文格式的日期字符串，格式为：yyyy年mm月dd日.
   * 
   * @param date 日期毫秒数
   * @return yyyy年mm月dd日字符串
   */
  public static String getCnDate(Long date) {
    String year = getCurrentYear(date);
    String month = getCurrentMonth(date);
    String day = getCurrentDay(date);
    return year + "年" + month + "月" + day + "日";
  }

  /**
   * 毫秒数转换为英文格式的日期字符串
   * @param date 毫秒数
   * @param style 格式化
   * @return
   */
  public static String getEnDate(Long date, String style) {
    Date nowDate = new Date(date);
    Locale locale = new Locale("en", "US");
    SimpleDateFormat format = new SimpleDateFormat(style, locale);
    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return format.format(nowDate).replace("&", "of");
  }

  /**
   * 毫秒数转换为英文格式的日期字符串，格式为：d & MMM. (EEE.), yyyy
   * 
   * @param date 毫秒数
   * @return 日期字符串
   */
  public static String getEnDate(Long date) {
    Date nowDate = new Date(date);
    Locale locale = new Locale("en", "US");
    SimpleDateFormat format = new SimpleDateFormat("d & MMM. (EEE.), yyyy", locale);
    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return format.format(nowDate).replace("&", "of");
  }
  
  /**
   * 获取英文格式的日期字符串，格式为：d & MMM. (EEE.), yyyy
   *
   * @return 日期字符串
   */
  public static String getEnDate() {
    Date date = new Date();
    Locale locale = new Locale("en", "US");
    SimpleDateFormat format = new SimpleDateFormat("d & MMM. (EEE.), yyyy", locale);
    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return format.format(date).replace("&", "of");
  }
  
  /**
   * 毫秒数转换为英文格式的日期字符串，格式为：MMM d&, yyyy
   * 
   * @param date 毫秒数
   * @return 日期字符串
   */
  public static String getEnDate2(Long date) {
    Date nowDate = new Date(date);
    Locale locale = new Locale("en", "US");
    SimpleDateFormat format = new SimpleDateFormat("MMM d&, yyyy", locale);
    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return format.format(nowDate).replace("&", "th");
  }
  
  /**
   * 获取英文格式的日期字符串，格式为：MMM d&, yyyy
   *
   * @return 日期字符串
   */
  public static String getEnDate2() {
    Date date = new Date();
    Locale locale = new Locale("en", "US");
    SimpleDateFormat format = new SimpleDateFormat("MMM d&, yyyy", locale);
    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return format.format(date).replace("&", "th");
  }
  
  /**
   * 日期转换为英文格式的日期字符串，格式为：EEE, dd MMM yyyy HH:mm:ss z
   * 
   * @param date 日期
   * @return 日期字符串
   */
  public static String getGmtDateString(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    String dateString = dateFormat.format(date);
    return dateString;
  }
  
  /**
   * 日期转换为英文格式的日期，格式为：EEE, dd MMM yyyy HH:mm:ss z
   * 
   * @param date 日期
   * @return 日期
   */
  public static Date getGmtDate(Date date) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    String dateString = dateFormat.format(date);
    return dateFormat.parse(dateString);
  }
  
  /**
   * 日期字符串转换为英文格式的日期，格式为：EEE, dd MMM yyyy HH:mm:ss z
   * 
   * @param dateString 日期字符串
   * @return 日期
   */
  public static Date  getGmtDate(String dateString) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.parse(dateString);
  }

  /**
   * 时间戳转换为日期字符串.
   * 
   * @param timestamp 时间戳
   * @return 日期字符串
   */
  public static String toDate(Object timestamp) {
    if (timestamp != null) {
      String timeString = StringKit.toString(timestamp);
      Long time = StringKit.isNotBlank(timeString) ? Long.parseLong(timeString) : null;
      if (time != null) {
        return getDate2(time);
      }
    }
    return "";
  }
  
  /**
   * 时间戳转换为时间字符串.
   * 
   * @param timestamp 时间戳
   * @return 时间字符串
   */
  public static String toTime(Object timestamp) {
    if (timestamp != null) {
      String timeString = StringKit.toString(timestamp);
      Long time = StringKit.isNotBlank(timeString) ? Long.parseLong(timeString) : null;
      if (time != null) {
        return getTime(time);
      }
    }
    return "";
  }
  
  /**
   * 星期一、星期二·····星期日
   * 
   * @param weekOfDay from 1 - 7
   * @return 中文周描述
   */
  public static String getCnWeek(int weekOfDay) {
    String[] weekDay = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    return weekDay[weekOfDay - 1];
  }
  
  /**
   * 周一、周二·····周日
   * 
   * @param weekOfDay from 1 - 7
   * @return 中文周描述
   */
  public static String getCnWeek2(int weekOfDay) {
    String[] weekDay = {"周一","周二","周三","周四","周五","周六","周日"};
    return weekDay[weekOfDay - 1];
  }
  
  /**
   * Monday、Tuesday·····Sunday
   * 
   * @param weekOfDay from 1 - 7
   * @return 英文周描述
   */
  public static String getEnWeek(int weekOfDay) {
    String[] weekDay = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    return weekDay[weekOfDay - 1];
  }
  
  /**
   * Mon.、Tues.·····Sun.
   * 
   * @param weekOfDay from 1 - 7
   * @return 英文周描述
   */
  public static String getEnWeek2(int weekOfDay) {
    String[] weekDay = {"Mon.","Tues.","Wed.","Thur.","Fri.","Sat.","Sun."};
    return weekDay[weekOfDay - 1];
  }

  /**
   * 获取当前日期周数
   */
  public int getDateInWeekNum() {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setTime(date);
    return calendar.get(Calendar.WEEK_OF_YEAR);
  }

  /**
   * 获取当前日期月数
   * @return
   */
  public int getDateInMonthNum() {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.MONTH) + 1;
  }

  public static void main(String[] args) {
    Date date = strToDate("1974-02-28");
    Date date1 = strToTime("2014");
    System.out.println(date.getTime());
    System.out.println(date1.getTime());
    System.out.println(getTime(1439654400000L));
    System.out.println(getMonth3());
    System.out.println(getTime(1439568000000L));
    System.out.println(getMonthRange());
    System.out.println(getTime());
    System.out.println(getMonthRangeString());
    System.out.println(getDifferDays(20));
    String currentDay = DateUtils.getCurrentDate1();
    System.out.println(currentDay + "," + DateUtils.getDifferDays(7));
    Date currentTime = new Date();
    
    SimpleDateFormat sdf =
            new SimpleDateFormat("d & MMM. (EEE.), yyyy", new Locale("en", "US"));
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    System.out.println("GMT time: " + sdf.format(currentTime).replace("&", "of"));
    
    System.out.println((41814 - 25569) * 86400);
    System.out.println(getDifferDays(new Date(1403568000000L),1 * 7));
    
    System.out.println(getTime(1403568000000L));
    System.out.println(getTime(1403568000000L + 3600 * 24 * 14));
    
    System.out.println(getCnDate(1441900800000L));
    System.out.println(getEnDate(1441900800000L));
    System.out.println(getEnDate2(1441900800000L));
    System.out.println("-------" + strToLastTimeString("2015-12-15 12"));
    
    double val = Math.ceil((double)1000 / 60);
    System.out.println("val1:" + (double)1000 / 60);
    System.out.println("val2:" + val);
    System.out.println(getTime(getFirstMonth("2016")));
    System.out.println(getTime(getDifferMonthsTime(getFirstMonth("2016"), 2)));
    System.out.println(getTime());
    System.out.println(getTime(1465968219324L));
    System.out.println(getCurrentDate4());
    System.out.println(getDate(getYesterday()));
    System.out.println(getMonth1());

    Date date2 = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setTime(date2);
    System.out.println("周几：" + calendar.get(Calendar.WEEK_OF_YEAR));

    Date date3 = strToDate("2018-01-20");
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setFirstDayOfWeek(Calendar.MONDAY);
    calendar2.setTime(date3);
    System.out.println("每周第几天：" + calendar2.get(Calendar.DAY_OF_WEEK));
    System.out.println("每周的第一天值：" + calendar2.getFirstDayOfWeek());
    int dayWeek = calendar2.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
    if(1 == dayWeek) {
      calendar2.add(Calendar.DAY_OF_MONTH, -1);
    }
    int day = calendar2.get(Calendar.DAY_OF_WEEK);
    calendar2.add(Calendar.DATE, calendar2.getFirstDayOfWeek() - day + 6);
    System.out.println(convertDateToStr2(calendar2.getTime()) + " 12:00:00");
    System.out.println(strToTime(convertDateToStr2(calendar2.getTime()) + " 12:00:00"));
    if (strToTime("2018-01-21 12:00:00").getTime() >
        strToTime(convertDateToStr2(calendar2.getTime()) + " 12:00:00").getTime()) {
      System.out.println("大于");
    }
    Date date4 = strToDate("2018-09-28");
    Calendar calendar3 = Calendar.getInstance();
    calendar3.setTime(date4);
    calendar3.set(Calendar.DAY_OF_MONTH,1);
    calendar3.add(Calendar.DATE,20);
    System.out.println("当月21日日期：" + DateUtils.convertDateToStr2(calendar3.getTime()) + " 00:00:00");
  }
}
