package org.xiaoyu.utils.data;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * IP 地址工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class IpKit {

  /**
   * 判断传入的字符串是否为IP地址.
   * @param addr IP地址字符串
   * @return 是否为IP地址
   */
  public static boolean isIp(String addr) {
    if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
      return false;
    }
    /**
     * 判断IP格式和范围
     */
    String rexp = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
    Pattern pat = Pattern.compile(rexp);
    Matcher mat = pat.matcher(addr);
    boolean ipAddress = mat.find();
    return ipAddress;
  }
}
