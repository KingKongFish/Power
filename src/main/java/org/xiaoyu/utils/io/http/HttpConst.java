package org.xiaoyu.utils.io.http;

/**
 * HTTP status code.
 */
public class HttpConst {

  /**
   * 1xx Informational.
   */
  public static final int CONTINUE = 100;

  /**
   * 2xx Success.
   */
  
  /**
   * request ok.
   * 请求成功.
   */
  public static final int OK = 200;

  /**
   * created.
   * 请求已经被实现，而且有一个新的资源已经依据请求的需要而建立。
   */
  public static final int CREATED = 201;

  /**
   * accepted.
   * 服务器已接受请求，但尚未处理。
   */
  public static final int ACCEPTED = 202;

  /**
   * 3xx Redirection.
   */
  
  /**
   * not modified.
   * 如果客户端发送了一个带条件的 GET 请求且该请求已被允许，而文档的内容（自上次访问以来或者根据请求的条件）并没有改变，
   * 则服务器应当返回这个状态码。304响应禁止包含消息体，因此始终以消息头后的第一个空行结尾。
   */
  public static final int NOT_MODIFIED = 304;

  /**
   * 4xx Client Error.
   */
  
  /**
   * bad request.
   * 1、语义有误，当前请求无法被服务器理解。除非进行修改，否则客户端不应该重复提交这个请求。
   * 2、请求参数有误。
   */
  public static final int BAD_REQUEST = 400;
  
  /**
   * unauthorized.
   * 当前请求需要用户验证.
   */
  public static final int UNAUTHORIZED = 401;

  /**
   * forbidden.
   * 服务器已经理解请求，但是拒绝执行它
   */
  public static final int FORBIDDEN = 403;
  
  /**
   * not found.
   * 请求失败，请求所希望得到的资源未被在服务器上发现.
   */
  public static final int NOT_FOUND = 404;

  /**
   * bad method.
   * 请求行中指定的请求方法不能被用于请求相应的资源
   */
  public static final int BAD_METHOD = 405;
  
  /**
   * conflict.
   * 由于和被请求的资源的当前状态之间存在冲突，请求无法完成
   */
  public static final int CONFLICT = 409;

  /**
   * 5xx Server Error.
   */
  
  /**
   * server error.
   * 服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。
   * 一般来说，这个问题都会在服务器端的源代码出现错误时出现。
   */
  public static final int SERVER_ERROR = 500;

}
