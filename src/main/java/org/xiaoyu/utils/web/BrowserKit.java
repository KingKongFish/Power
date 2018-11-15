package org.xiaoyu.utils.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 浏览器工具类.
 * @author peilongwu.
 * @date 2016-09-10
 */
public class BrowserKit {

  public enum BrowserType {
    IE10, IE9, IE8, IE7, IE6, Firefox, Safari, Chrome, Opera, Camino, Gecko, Other
  }

  /**
   * isIE:判断是否是IE.
   * 
   * @param request
   * @return boolean
   */
  public static boolean isIe(HttpServletRequest request) {
    try {
      if (request != null && request.getHeader("USER-AGENT") != null) {
        return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 ? true
            : false;
      } else {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 获取IE版本.
   * 
   * @param request
   * @return
   */
  public static Double getIEversion(HttpServletRequest request) {
    Double version = 0.0;
    if (getBrowserType(request, "msie 10.0")) {
      version = 10.0;
    }
    if (getBrowserType(request, "msie 9.0")) {
      version = 9.0;
    }
    if (getBrowserType(request, "msie 8.0")) {
      version = 8.0;
    }
    if (getBrowserType(request, "msie 7.0")) {
      version = 7.0;
    }
    if (getBrowserType(request, "msie 6.0")) {
      version = 6.0;
    }
    return version;
  }

  /**
   * Get browser type.
   * 
   * @param request
   * @return
   */
  public static BrowserType getBrowserType(HttpServletRequest request) {
    BrowserType browserType = null;
    if (getBrowserType(request, "msie 10.0")) {
      browserType = BrowserType.IE9;
    }
    if (getBrowserType(request, "msie 9.0")) {
      browserType = BrowserType.IE9;
    }
    if (getBrowserType(request, "msie 8.0")) {
      browserType = BrowserType.IE8;
    }
    if (getBrowserType(request, "msie 7.0")) {
      browserType = BrowserType.IE7;
    }
    if (getBrowserType(request, "msie 6.0")) {
      browserType = BrowserType.IE6;
    }
    if (getBrowserType(request, "Firefox")) {
      browserType = BrowserType.Firefox;
    }
    if (getBrowserType(request, "Safari")) {
      browserType = BrowserType.Safari;
    }
    if (getBrowserType(request, "Chrome")) {
      browserType = BrowserType.Chrome;
    }
    if (getBrowserType(request, "Opera")) {
      browserType = BrowserType.Opera;
    }
    if (getBrowserType(request, "Camino")) {
      browserType = BrowserType.Camino;
    }
    if (browserType == null) {
      browserType = BrowserType.Other;
    }
    return browserType;
  }

  private static boolean getBrowserType(HttpServletRequest request,
      String brosertype) {
    if (request != null && request.getHeader("USER-AGENT") != null) {
      String userAgent = "";
      if (request.getHeader("USER-AGENT") != null) {
        userAgent = request.getHeader("USER-AGENT").toLowerCase();
      }
      return userAgent.indexOf(brosertype.toLowerCase()) > 0 ? true : false;
    } else {
      return false;
    }
  }

  private static final String IE10 = "MSIE 10.0";
  private static final String IE9 = "MSIE 9.0";
  private static final String IE8 = "MSIE 8.0";
  private static final String IE7 = "MSIE 7.0";
  private static final String IE6 = "MSIE 6.0";
  private static final String MAXTHON = "Maxthon";
  private static final String QQ = "QQBrowser";
  private static final String GREEN = "GreenBrowser";
  private static final String SE360 = "360SE";
  private static final String FIREFOX = "Firefox";
  private static final String OPERA = "Opera";
  private static final String CHROME = "Chrome";
  private static final String SAFARI = "Safari";
  private static final String OTHER = "其它";

  public static String checkBrowse(HttpServletRequest request) {
    if (request != null && request.getHeader("USER-AGENT") != null) {
      String userAgent = request.getHeader("USER-AGENT");
      String subString = null;
      if (regex(OPERA, userAgent)) {
        return OPERA;
      }
      if (regex(CHROME, userAgent)) {
        subString = userAgent.substring(userAgent.indexOf(CHROME));
        return subString.substring(0, subString.indexOf(" "));
      }
      if (regex(FIREFOX, userAgent)) {
        return FIREFOX;
      }
      if (regex(SAFARI, userAgent)) {
        return SAFARI;
      }
      if (regex(SE360, userAgent)) {
        return SE360;
      }
      if (regex(GREEN, userAgent)) {
        return GREEN;
      }
      if (regex(QQ, userAgent)) {
        return QQ;
      }
      if (regex(MAXTHON, userAgent)) {
        return MAXTHON;
      }
      if (regex(IE10, userAgent)) {
        return IE10;
      }
      if (regex(IE9, userAgent)) {
        return IE9;
      }
      if (regex(IE8, userAgent)) {
        return IE8;
      }
      if (regex(IE7, userAgent)) {
        return IE7;
      }
      if (regex(IE6, userAgent)) {
        return IE6;
      }
    }
    return OTHER;
  }

  public static boolean regex(String regex, String str) {
    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(str);
    return matcher.find();
  }

  public static void main(String[] args) {
    String userAgent = "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 "
        + "(KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36";
    String subString = userAgent.substring(userAgent.indexOf(CHROME));
    System.out.println(subString.substring(0, subString.indexOf(" ")));
  }
}
