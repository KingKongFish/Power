package org.xiaoyu.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.xiaoyu.utils.entity.EntityUtil;

/**
 * 列表数据处理为树形数据工具类.
 * 
 * @author peilongwu
 * @param <T>
 * @date 2016-09-10
 */
public class TreeProduct<T> {

  public JSONArray getJsonTree(List<T> list, String idName, String pidName) {
    return getJsonTree(list, idName, pidName, "");
  }

  public JSONArray getJsonTree(List<T> list, String idName, String pidName,
      Object pid) {
    List<T> childList = getChildList(pidName, pid, list);
    JSONArray nodeArray = new JSONArray();
    for (T node : childList) {
      JSONObject childNode = (JSONObject) JSON.toJSON(node);
      nodeArray.add(addChildNodes(idName, pidName,
          EntityUtil.getFieldValue(node, idName), list, childNode));
    }
    return nodeArray;
  }

  public JSONObject addChildNodes(String idName, String pidName, Object pid,
      List<T> list, JSONObject parent) {
    try {
      List<T> childList = getChildList(pidName, pid, list);
      if (childList.isEmpty()) {
        return parent;
      } else {
        JSONArray childNodeArray = new JSONArray();
        for (T node : childList) {
          JSONObject childNode = (JSONObject) JSON.toJSON(node);
          childNodeArray.add(addChildNodes(idName, pidName,
              EntityUtil.getFieldValue(node, idName), list, childNode));
        }
        parent.put("_childList", childNodeArray);
        return parent;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<T> getChildList(String pidName, Object pid, List<T> list) {
    List<T> childList = new ArrayList<T>();
    for (T node : list) {
      Object pidValue = EntityUtil.getFieldValue(node, pidName);
      if (pid.equals(pidValue != null ? pidValue : "")) {
        childList.add(node);
      }
    }
    return childList;
  }
}
