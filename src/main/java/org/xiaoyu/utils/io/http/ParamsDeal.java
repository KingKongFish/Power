package org.xiaoyu.utils.io.http;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.xiaoyu.utils.data.StringKit;

/**
 * Http Reuqest Params 处理工具类.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class ParamsDeal {
  
  /**
   * 判断传入的参数是否不为空.
   * 
   * @param params HttpServletRequest Paramters
   * @param name 参数名称
   * @return boolean
   */
  public static boolean isNotNull(Map<String,String[]> params, String name) {
    if (params != null) {
      String[] values = params.get(name);
      if (values != null && values.length > 0) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * 通过传入的参数名称，获取HttpServletRequest参数数组.
   * 
   * @param params HttpServletRequest参数
   * @param name 参数名称
   * @return String[]
   */
  public static String[] getParams(Map<String,String[]> params, String name) {
    if (isNotNull(params, name)) {
      return params.get(name);
    } else {
      return null;
    }
  }
  
  /**
   * 通过参数名，从Http请求参数及Rawbody中获取参数值.
   * 
   * @param params
   * @param rawBody
   * @param name
   * @return String
   */
  public static String getStringParam(Map<String,String[]> params, String rawBody, String name) {
    try {
      String value = getStringParam(params, name);
      if (StringKit.isNotBlank(value)) {
        return value;
      } else {
        JSONObject rawbody = JSONObject.parseObject(rawBody);
        if (rawbody != null) {
          return rawbody.getString(name);
        } else {
          return null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * 通过传入的参数名，从Http参数中获取字符串类型参数值.
   * 
   * @param params
   * @param name
   * @return String
   */
  public static String getStringParam(Map<String,String[]> params, String name) {
    if (isNotNull(params, name)) {
      return params.get(name)[0];
    } else {
      return null;
    }
  }
  
  /**
   * 通过传入的参数名，从Http参数中获取整型类型参数值.
   * 
   * @param params
   * @param name
   * @return Integer
   */
  public static Integer getIntParam(Map<String,String[]> params, String name) {
    if (isNotNull(params, name)) {
      return Integer.parseInt(params.get(name)[0]);
    } else {
      return null;
    }
  }

  /**
   * 通过传入的参数名，从Http参数中获取长整型类型参数值.
   * 
   * @param params
   * @param name
   * @return Long
   */
  public static Long getLongParam(Map<String, String[]> params, String name) {
    if (isNotNull(params, name)) {
      return Long.parseLong(params.get(name)[0]);
    } else {
      return null;
    }
  }
  
  /**
   * 把Http请求参数Map<String, String[]>转换为Map<String,Object>.
   * 
   * @param params
   * @return Map<String,Object>
   */
  public static Map<String,Object> getParamMap(Map<String, String[]> params) {
    Map<String,Object> paramMap = new HashMap<String,Object>();
    if (params != null) {
      for (Map.Entry<String, String[]> entry : params.entrySet()) {
        if (entry.getValue() != null && entry.getValue().length > 0) {
          paramMap.put(entry.getKey(), entry.getValue()[0]);
        }
      }
    }
    return paramMap;
  }
  
  /**
   * 获取Http参数中_confirm的值.
   * 
   * @param object
   * @return String
   */
  public static String getConfirmFlag(Map<String, Object> object) {
    return StringKit.toString(object.get("_confirm"));
  }
  
  public static Map<String, String[]> convertParams(Map<String, Object> object) {
    Map<String, String[]> params = new HashMap<String, String[]>();
    if (object != null) {
      for (Map.Entry<String, Object> entry : object.entrySet()) {
        if (entry.getValue() instanceof String) {
          params.put(entry.getKey(), new String[]{(String)entry.getValue()});
        }
      }
    }
    return params;
  }
  
  /**
   * 从Http中获取的参数无法向其中再加入一些动态参数内容，需要把参数都放置到新的Map中.
   * 
   * @param object
   * @return Map<String, String[]>
   */
  public static Map<String, String[]> getUnlockParams(Map<String, String[]> object) {
    return new HashMap<>(object);
  }
}
