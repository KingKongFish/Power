package org.xiaoyu.utils.io.http.request;

import org.xiaoyu.utils.data.StringKit;
import org.xiaoyu.utils.io.http.OsKit;
import org.xiaoyu.utils.web.BrowserKit;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpRequest 工具类.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class RequestKit {

  /**
   * isAjax:判断请求是否为Ajax请求方法.</p>
   * 
   * @param request Http请求
   * @return boolean
   */
  public static boolean isAjax(HttpServletRequest request) {
    String header = request.getHeader("X-Requested-With");
    boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;
    return isAjax;
  }
  
  /**
   * 获取Http请求方法. </p>
   * 
   * @param request Http请求
   * @return HttpMethod
   */
  public static HttpMethod getMethod(HttpServletRequest request) {
    return HttpMethod.getHttpMethod(request.getMethod());
  }

  /**
   * isGet:判断请求是否为Get请求方法.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isGet(HttpServletRequest request) {
    boolean isGet = HttpMethod.GET.equals(HttpMethod.getHttpMethod(request
        .getMethod()));
    return isGet;
  }

  /**
   * isPost:判断请求是否为Post请求方法.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isPost(HttpServletRequest request) {
    boolean isPost = HttpMethod.POST.equals(HttpMethod.getHttpMethod(request
        .getMethod()));
    return isPost;
  }

  /**
   * isPut:判断请求是否为Put请求方法.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isPut(HttpServletRequest request) {
    boolean isPut = HttpMethod.PUT.equals(HttpMethod.getHttpMethod(request
        .getMethod()));
    return isPut;
  }

  /**
   * isHead:判断请求是否为Head请求方法.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isHead(HttpServletRequest request) {
    boolean isHead = HttpMethod.HEAD.equals(HttpMethod.getHttpMethod(request
        .getMethod()));
    return isHead;
  }

  /**
   * isDelete:判断请求是否为Delete请求方法.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isDelete(HttpServletRequest request) {
    boolean isDelete = HttpMethod.DELETE.equals(HttpMethod
        .getHttpMethod(request.getMethod()));
    return isDelete;
  }

  /**
   * isTrace:判断请求是否为Trace请求方法.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isTrace(HttpServletRequest request) {
    boolean isTrace = HttpMethod.TRACE.equals(HttpMethod.getHttpMethod(request
        .getMethod()));
    return isTrace;
  }

  /**
   * isOptions:判断请求是否为Options请求方法.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isOptions(HttpServletRequest request) {
    boolean isOptions = HttpMethod.OPTIONS.equals(HttpMethod
        .getHttpMethod(request.getMethod()));
    return isOptions;
  }

  /**
   * isUploadRequest.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isUploadRequest(HttpServletRequest request) {
    String contentType = request.getContentType();
    if ("multipart/form-data".equals(contentType)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * isJson:判断请求是否为Json请求.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isJson(HttpServletRequest request) {
    String contentType = request.getContentType();
    if ("application/json".equals(contentType)) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * getJsonBody:获取request json body.</p>
   * 
   * @param request
   * @return boolean
   * @throws IOException 
   */
  public static String getJsonBody(HttpServletRequest request) throws IOException {
    String contentType = request.getContentType();
    if (contentType != null && contentType.contains("application/json")) {
      String requestBody = IOUtils.toString(request.getInputStream(),"utf-8");
      System.out.println("request body : " + requestBody);
      return requestBody;
    } else {
      return null;
    }
  }

  /**
   * isXml:判断请求是否为Xml请求.</p>
   * 
   * @param request
   * @return boolean
   */
  public static boolean isXml(HttpServletRequest request) {
    String contentType = request.getContentType();
    if ("application/xml".equals(contentType)) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * isJsonP:判断请求是否为JsonP请求.</p>
   * @param request
   * @return boolean
   */
  public static boolean isJsonP(HttpServletRequest request) {
    String jsonP = request.getParameter("_jsonp");
    if (StringKit.isNotBlank(jsonP)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * getIpAddr:获得Http请求的IP地址.</p>
   * 
   * @param request
   * @return boolean
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip != null && ip.length() > 15) {
      if (ip.indexOf(",") > 0) {
        ip = ip.substring(0, ip.indexOf(","));
      }
    }
    return ip;
  }

  /**
   * 获取浏览器的类型.</p>
   * 
   * @param request
   * @return
   */
  public static String getBrowser(HttpServletRequest request) {
    return BrowserKit.getBrowserType(request).toString();
  }

  /**
   * 获取浏览器的类型.
   * @param request
   * @return
   */
  public static String getBrowserVersion(HttpServletRequest request) {
    return BrowserKit.checkBrowse(request).toString();
  }

  /**
   * 判断当前请求是否为IE请求.</p>
   */
  public static boolean isIe(HttpServletRequest request) {
    return BrowserKit.isIe(request);
  }

  /**
   * 判断当前请求是否为Chrome请求.
   */
  public static boolean isChrome(HttpServletRequest request) {
    if ("Chrome".equals(BrowserKit.getBrowserType(request))) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断当前请求是否为Firefox请求.</p>
   */
  public static boolean isFirefox(HttpServletRequest request) {
    if ("Firefox".equals(BrowserKit.getBrowserType(request))) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * 判断当前请求是否为Safari请求.</p>
   */
  public static boolean isSafari(HttpServletRequest request) {
    if ("Safari".equals(BrowserKit.getBrowserType(request))) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * 判断当前请求是否为Opera请求.</p>
   */
  public static boolean isOpera(HttpServletRequest request) {
    if ("Opera".equals(BrowserKit.getBrowserType(request))) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * 判断当前请求是否为Camino请求.</p>
   */
  public static boolean isCamino(HttpServletRequest request) {
    if ("Camino".equals(BrowserKit.getBrowserType(request))) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * 获取客户端操作系统类型.</p>
   */
  public static String getOs(HttpServletRequest request) {
    return OsKit.getOsType(request);
  }
  
  /**
   * 根据Key获取Head信息
   */
  public static String getHead(String key, HttpServletRequest request) {
    String header = request.getHeader(key);
    return header;
  }

}
