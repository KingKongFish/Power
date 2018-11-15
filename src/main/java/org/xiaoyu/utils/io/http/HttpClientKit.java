package org.xiaoyu.utils.io.http;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.xiaoyu.utils.data.EncryptKit;
import org.xiaoyu.utils.data.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientKit {

  private static Logger logger = LoggerFactory.getLogger(HttpClientKit.class);

  /**
   * HTTP GET
   *
   * @param queryString
   * @return
   * @throws HttpException
   * @throws IOException
   */
  public static String get(String queryString, String url) throws HttpException, IOException {
    HttpClient httpclient = new HttpClient();
    GetMethod method = new UTF8GetMethod(url);
    String responseStr = "";
    try {
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setQueryString(queryString);
      int statusCode = httpclient.executeMethod(method);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          System.out.println("OK:" + HttpStatus.SC_OK);
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  public static String get(String url, NameValuePair[] param) throws HttpException, IOException {
    ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
    Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
    HttpClient httpclient = new HttpClient();
    GetMethod method = new UTF8GetMethod(url);
    String responseStr = "";
    try {
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setQueryString(param);
      int statusCode = httpclient.executeMethod(method);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          System.out.println("OK:" + HttpStatus.SC_OK);
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  public static String get(String url, Header header) throws HttpException, IOException {
    ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
    Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
    HttpClient httpclient = new HttpClient();
    GetMethod method = new UTF8GetMethod(url);
    String responseStr = "";
    try {
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setRequestHeader(header);
      int statusCode = httpclient.executeMethod(method);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          System.out.println("OK:" + HttpStatus.SC_OK);
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  /**
   * POST 上传二进制数据
   *
   * @param url
   * @param parts
   * @return
   * @throws HttpException
   * @throws IOException
   */
  public static String post(String url, Part[] parts) throws HttpException, IOException {

    ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
    Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
    HttpClient httpclient = new HttpClient();
    PostMethod method = new UTF8PostMethod(url);
    String responseStr = "";
    try {
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setRequestHeader("Connection", "Keep-Alive");
      method.setRequestHeader("Cache-Control", "no-cache");
      MultipartRequestEntity entity = new MultipartRequestEntity(parts, method.getParams());
      method.setRequestEntity(entity);
      int statusCode = httpclient.executeMethod(method);
      switch (statusCode) {
        case HttpStatus.SC_OK: {
          System.out.println("OK:" + HttpStatus.SC_OK);
          responseStr = method.getResponseBodyAsString();
          break;
        }
        default: {
          System.out.println("failed:" + statusCode);
        }
        break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  @SuppressWarnings("deprecation")
  public static String post(String url, String json) throws HttpException, IOException {
    return post(url, json, null);
  }

  @SuppressWarnings("deprecation")
  public static String post(String url, String json, Map<String, String> headMap) throws HttpException, IOException {
    String responseStr = "";
    HttpClient httpclient = new HttpClient();
    PostMethod method = new UTF8PostMethod(url);
    try {
      if (headMap != null && !headMap.isEmpty()) {
        for (Map.Entry<String, String> entry : headMap.entrySet()) {
          method.setRequestHeader(entry.getKey(), entry.getValue());
        }
      }
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setRequestHeader("Content-Type", "application/json");
      method.setRequestBody(json);
      int statusCode = httpclient.executeMethod(method);
      logger.info("HttpClient response status code is : " + statusCode);
      if (statusCode < HttpStatus.SC_BAD_REQUEST) {
        responseStr = method.getResponseBodyAsString();
        logger.info("Http client response string : " + responseStr);
      } else {
        responseStr = method.getResponseBodyAsString();
        logger.info("Http client response error, status code : " + statusCode + ", response :" + responseStr);
        throw new RuntimeException("HttpClient response status code is " + statusCode + " and response string is " + responseStr);
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  @SuppressWarnings("deprecation")
  public static Map<String, Object> post(String url) throws HttpException, IOException {
    Map<String, Object> response = new HashedMap();
    HttpClient httpclient = new HttpClient();
    PostMethod method = new UTF8PostMethod(url);
    try {
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setRequestHeader("Content-Type", "application/json");
      int statusCode = httpclient.executeMethod(method);
      String responseStr = method.getResponseBodyAsString();
      response.put("statusCode", statusCode);
      response.put("responseStr", responseStr);
      logger.info("Http client response error, status code : " + statusCode + ", response :" + responseStr);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return response;
  }

  public static String post(String url, NameValuePair[] parametersBody) throws HttpException, IOException {
    String responseStr = "";
    HttpClient httpclient = new HttpClient();
    PostMethod method = new UTF8PostMethod(url);
    try {
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setRequestBody(parametersBody);
      int statusCode = httpclient.executeMethod(method);
      System.out.println("statusCode:" + statusCode);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          responseStr = method.getResponseBodyAsString();
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  public static String post(String url, NameValuePair[] parametersBody, String charset) throws HttpException, IOException {
    byte[] responseByte;
    String responseStr = "";
    HttpClient httpclient = new HttpClient();
    PostMethod method = new UTF8PostMethod(url);
    try {
      method.setRequestHeader("Accept-Charset", charset);
      method.setRequestBody(parametersBody);
      int statusCode = httpclient.executeMethod(method);
      System.out.println("statusCode:" + statusCode);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          responseByte = method.getResponseBody();
          responseStr = new String(responseByte, charset);
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          responseStr = method.getResponseBodyAsString();
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  public static String postQueryString(String url, NameValuePair[] params) throws HttpException, IOException {
    String responseStr = "";
    HttpClient httpclient = new HttpClient();
    PostMethod method = new UTF8PostMethod(url);
    try {
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setRequestHeader("Content-Type", "application/json");
      method.setQueryString(params);
      int statusCode = httpclient.executeMethod(method);
      System.out.println("statusCode:" + statusCode);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  public static String postEntity(String url, String data,
                                  Map<String, Object> headerParam, Map<String, Object> requestParam) throws HttpException, IOException, Exception {
    return postEntity(url, data, headerParam, requestParam, "", "");
  }

  public static String postEntity(String url, String data,
                                  Map<String, Object> headerParam, Map<String, Object> requestParam,
                                  String acceptType, String charSet)
    throws HttpException, IOException, Exception {
    String responseStr = "";
    HttpClient httpclient = new HttpClient();
    PostMethod method = new UTF8PostMethod(url);
    try {
      //header add
      for (Map.Entry<String, Object> entry : headerParam.entrySet()) {
        String key = entry.getKey();
        String value = StringKit.toString(entry.getValue());
        System.out.println("requestParam key:" + entry.getKey());
        System.out.println("requestParam value:" + entry.getValue());
        method.setRequestHeader(key, value);
      }
      //request paramter add
      NameValuePair[] param = new NameValuePair[requestParam.size()];
      int i = 0;
      for (Map.Entry<String, Object> entry : requestParam.entrySet()) {
        String key = entry.getKey();
        String value = StringKit.toString(entry.getValue());
        System.out.println("requestParam key:" + entry.getKey());
        System.out.println("requestParam value:" + entry.getValue());
        param[i] = new NameValuePair(key, value);
        i++;
      }
      method.setQueryString(param);
      acceptType = StringKit.isBlank(acceptType) ? "application/json" : acceptType;
      charSet = StringKit.isBlank(charSet) ? "UTF-8" : charSet;
      RequestEntity entity = new StringRequestEntity(data, acceptType, charSet);
      method.setRequestEntity(entity);
      int statusCode = httpclient.executeMethod(method);
      System.out.println("statusCode:" + statusCode);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  public static String postEntity(String url, File file,
                                  Map<String, Object> headerParam, Map<String, Object> requestParam)
    throws HttpException, IOException, Exception {
    String responseStr = "";
    HttpClient httpclient = new HttpClient();
    PostMethod method = new UTF8PostMethod(url);
    try {
      //header add
      for (Map.Entry<String, Object> entry : headerParam.entrySet()) {
        String key = entry.getKey();
        String value = StringKit.toString(entry.getValue());
        System.out.println("requestParam key:" + entry.getKey());
        System.out.println("requestParam value:" + entry.getValue());
        method.setRequestHeader(key, value);
      }
      //request paramter add
      NameValuePair[] param = new NameValuePair[requestParam.size()];
      int i = 0;
      for (Map.Entry<String, Object> entry : requestParam.entrySet()) {
        String key = entry.getKey();
        String value = StringKit.toString(entry.getValue());
        System.out.println("requestParam key:" + entry.getKey());
        System.out.println("requestParam value:" + entry.getValue());
        param[i] = new NameValuePair(key, value);
        i++;
      }
      method.setQueryString(param);
//      Part[] parts = {new FilePart("file", file)};
      Part[] parts = {new FilePart("file", file.getName(), file,
        new MimetypesFileTypeMap().getContentType(file), "UTF-8")};
      MultipartRequestEntity entity = new MultipartRequestEntity(parts, method.getParams());
      method.setRequestEntity(entity);
      int statusCode = httpclient.executeMethod(method);
      System.out.println("statusCode:" + statusCode);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  @SuppressWarnings("deprecation")
  public static String put(String url, String json) throws HttpException, IOException {
    String responseStr = "";
    HttpClient httpclient = new HttpClient();
    UTF8PutMethod method = new UTF8PutMethod(url);
    try {
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setRequestHeader("Content-Type", "application/json");
      method.setRequestBody(json);
      int statusCode = httpclient.executeMethod(method);
      System.out.println("statusCode:" + statusCode);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  public static String delete(String url, String json) throws HttpException, IOException {

    String responseStr = "";
    HttpClient httpclient = new HttpClient();
    UTF8DeteleMethod method = new UTF8DeteleMethod(url);
    try {
      method.setRequestHeader("Accept", "application/json");
      method.setRequestHeader("Accept-Charset", "utf-8");
      method.setRequestHeader("Content-Type", "application/json");
      method.setQueryString("");
      System.out.println("url:" + url);
      int statusCode = httpclient.executeMethod(method);
      System.out.println("statusCode:" + statusCode);
      switch (statusCode) {
        case HttpStatus.SC_OK:
          responseStr = method.getResponseBodyAsString();
          System.out.println("responseStr:" + responseStr);
          break;
        default:
          System.out.println("failed:" + statusCode);
          break;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      throw e;
    } finally {
      method.releaseConnection();
    }
    return responseStr;
  }

  public static class UTF8DeteleMethod extends DeleteMethod {

    public UTF8DeteleMethod(String url) {
      super(url);
    }

    @Override
    public String getRequestCharSet() {
      return "UTF-8";
    }
  }

  public static class UTF8PutMethod extends PutMethod {

    public UTF8PutMethod(String url) {
      super(url);
    }

    @Override
    public String getRequestCharSet() {
      return "UTF-8";
    }
  }

  public static class UTF8PostMethod extends PostMethod {

    public UTF8PostMethod(String url) {
      super(url);
    }

    @Override
    public String getRequestCharSet() {
      return "UTF-8";
    }
  }

  public static class UTF8GetMethod extends GetMethod {

    public UTF8GetMethod(String url) {
      super(url);
    }

    @Override
    public String getRequestCharSet() {
      return "UTF-8";
    }
  }

  public static void main(String[] args) throws Exception {

    String data = "{node_name: \"12\"}";
    Map<String, Object> headerParam = new HashMap<String, Object>();
    headerParam.put("Accept", "application/json");
    headerParam.put("Accept-Charset", "utf-8");
    headerParam.put("userId", "cuixin");
    Long timestamp = System.currentTimeMillis();
    headerParam.put("token", EncryptKit.getMD5("cuixin" + timestamp + "01554cf384968a8a8b1f554cf38403ea"));
    headerParam.put("time", String.valueOf(timestamp));
    Map<String, Object> requestParam = new HashMap<String, Object>();
    requestParam.put("_node", "01591b6b722140289f3a591b6ad00002");
//	  HttpClientKit.postEntity("http://192.168.31.186:8080/ems/api/resources-cloud",data,headerParam,requestParam);
//    headerParam.put("Content-Type", "application/json");
    HttpClientKit.postEntity("http://192.168.31.186:8080/ems/api/resources-cloud/upload", new File("/Users/yangyu/tmp/unzip/girl.jpg"), headerParam, requestParam);
  }
}
