package org.xiaoyu.utils.io.http;

import javax.servlet.http.HttpServletRequest;

/**
 * 根据Request获取操作系统类型
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class OsKit {

  private static boolean getOsType(HttpServletRequest request, String osType) {
    if (request != null && request.getHeader("USER-AGENT") != null) {
      String userAgent = request.getHeader("USER-AGENT").toLowerCase();
      return userAgent.indexOf(osType.toLowerCase()) > 0 ? true : false;
    } else {
      return false;
    }
  }

  public static String getOsType(HttpServletRequest request) {
    String osType = null;
    if (getOsType(request, "Linux")) {
      osType = "Linux";
    }
    if (getOsType(request, "Windows")) {
      osType = "Windows";
    }
    if (getOsType(request, "Mac OS")) {
      osType = "Mac OS";
    }
    if (osType == null) {
      osType = "Other";
    }
    return osType;
  }
}
