package org.xiaoyu.utils.io.http.request;

import org.xiaoyu.utils.io.http.HttpConst;
import org.xiaoyu.utils.property.PropertiesHolderHelper;

/**
 * Http协议中定义的常用请求方法.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public enum HttpMethod {

  GET, // 从服务器获取一份文档，没有主体（Body）
  HEAD, // 只从服务器获取文档的头部，没有主体（Body）
  POST, // 向服务器发送需要处理的数据，有主体
  PUT, // 将请求的主体部分存储在服务器上
  TRACE, // 对可能经过代理服务器传送到服务器上去的报文进行追踪
  OPTIONS, // 决定可以在服务器上执行那些方法
  DELETE; // 从服务器上删除一份文档

  public static HttpMethod getHttpMethod(String method) {

    // 忽略Http请求中的大小写，发送过来的方法名统一转为大写
    String ignoreCaseMethod = method.toUpperCase();

    switch (HttpMethod.valueOf(ignoreCaseMethod)) {
      case GET: {
        return GET;
      }
      case HEAD: {
        return HEAD;
      }
      case POST: {
        return POST;
      }
      case PUT: {
        return PUT;
      }
      case TRACE: {
        return TRACE;
      }
      case OPTIONS: {
        return OPTIONS;
      }
      case DELETE: {
        return DELETE;
      }
      default: {
        throw new RuntimeException(PropertiesHolderHelper
            .getProperties("errcode").getProperty(String.valueOf(HttpConst.BAD_METHOD)));
      }
    }
  }

}
