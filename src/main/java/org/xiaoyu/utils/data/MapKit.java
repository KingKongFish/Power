package org.xiaoyu.utils.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map 工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class MapKit {
  
  /**
   * 列表Map转换为Map<key,Map<key,value>>格式.
   * @param dataList 列表数据
   * @param key 作为Map中key的属性名称
   * @return Map<String,Map<String,Object>>
   */
  public static Map<String,Map<String,Object>> buildMap(List<Map<String,Object>> dataList,
      String key) {
    Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
    if (dataList != null && dataList.size() > 0) {
      for (Map<String,Object> data : dataList) {
        map.put(data.get(key).toString(), data);
      }
    }
    return map;
  }
}
