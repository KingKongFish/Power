package org.xiaoyu.utils.datatype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 列表工具类.
 * 
 * @author peilongwu
 * @date 2016-09-08
 */
public class ListKit {
  
  /**
   * 获取列表Map中，某一key的所有值.
   * @param list 列表
   * @param key 列名 
   * @return 列表字符串
   */
  public static List<String> getColList(List<Map<String,Object>> list, String key) {
    List<String> objs = null;
    if (list != null && list.size() > 0) {
      objs = new ArrayList<String>();
      for (Map<String,Object> map : list) {
        if (map.get(key) != null && !"".equals(map.get(key))) {
          objs.add(map.get(key).toString());
        }
      }
    }
    return objs;
  }
  
  /**
   * 列表字符串转换为以逗号分隔的单字符串.
   * 
   * @param list 字符串列表
   * @return 字符串
   */
  public static String getColList(List<String> list) {
    StringBuffer listStr = new StringBuffer("");
    if (list != null && list.size() > 0) {
      listStr.append(list.get(0));
      for (int i = 1; i < list.size(); i++) {
        listStr.append("," + list.get(i));
      }
    }
    return listStr.toString();
  }
  
  /**
   * 字符串列表转换为SQL IN 格式，如：'str1','str2','str3'....
   * @param list 字符串列表
   * @return 'str1','str2','str3'.... 字符串
   */
  public static String getColsIn(List<String> list) {
    StringBuffer listStr = new StringBuffer("");
    if (list != null && list.size() > 0) {
      listStr.append("'" + list.get(0) + "'");
      for (int i = 1; i < list.size(); i++) {
        listStr.append(",'" + list.get(i) + "'");
      }
    }
    return listStr.toString();
  }
  
  public static void main(String[] args) {
    List<String> keys = new ArrayList<String>();
    keys.add("123");
    keys.add("456");
    System.out.println(getColsIn(keys));
  }
}
