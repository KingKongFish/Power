package org.xiaoyu.utils.tree;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 列表数据处理为树形数据工具类.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class TreeMapUtils {

  /**
   * 列表数据处理为递归树形List.
   *    格式：
   *     [
   *      {key:value...,_childList:[{}...]}
   *      {key:value...,_childList:[{}...]}
   *      {key:value...,_childList:[{}...]}
   *      ...
   *     ]
   * @param list 列表数据
   * @param idName 数据主键字段名称
   * @param pidName 父数据主键字段名称
   * @return List<Map<String, Object>>
   */
  public static List<Map<String, Object>> getChilds(
      List<Map<String, Object>> list, String idName, String pidName, Object pid) {
    List<Map<String, Object>> childList = getChildList(pidName, pid, list);
    List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
    for (Map<String, Object> node : childList) {
      returnList.add(node);
      addChildList(idName, pidName, node.get(idName), list, returnList);
    }
    return returnList;
  }

  private static void addChildList(String idName, String pidName, Object pid,
      List<Map<String, Object>> list, List<Map<String, Object>> returnList) {
    try {
      List<Map<String, Object>> childList = getChildList(pidName, pid, list);
      if (childList.isEmpty()) {
        return;
      } else {
        for (Map<String, Object> node : childList) {
          returnList.add(node);
          addChildList(idName, pidName, node.get(idName), list, returnList);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static List<Object> getChildIds(List<Map<String, Object>> list,
      String idName, String pidName, Object pid) {
    List<Map<String, Object>> returnList = getChilds(list, idName, pidName, pid);
    List<Object> ids = new ArrayList<Object>();
    for (Map<String, Object> map : returnList) {
      ids.add(map.get(idName));
    }
    return ids;
  }
  
  /**
   * 获得树形机构父节点下，父节点及字节点的Id列表 结构如下： id:xxx name:xxx _childList: { id:xxxx
   * name:xxxx _childList:{ ... } } 步骤： 1、递归获取当前父节点位置 2、递归获取子节点的Id
   */
  public static List<Object> getChildIds(JSONArray array, String idName,
      Object id) {
    List<Object> ids = new ArrayList<Object>();
    if (id != null && !"".equals(id)) {
      if (array != null && array.size() > 0) {
        JSONObject parent = null;
        for (int i = 0; i < array.size(); i++) {
          parent = getParentObject(array.getJSONObject(i), idName, id);
          if (parent != null && !parent.isEmpty()) {
            break;
          }
        }
        if (parent != null && !parent.isEmpty()) {
          addChildIds(ids, parent, idName);
        }
      }
      ids.add(id);
    }
    return ids;
  }
  
  /**
   * 获得树形机构父节点下，父节点及字节点的Id列表 结构如下： id:xxx name:xxx _childList: { id:xxxx
   * name:xxxx _childList:{ ... } } 步骤： 1、递归获取当前父节点位置 2、递归获取子节点的Id
   */
  public static List<Object> getChildIds(List<Map<String, Object>> array, String idName,
      Object id) {
    List<Object> ids = new ArrayList<Object>();
    if (id != null && !"".equals(id)) {
      if (array != null && array.size() > 0) {
        Map<String, Object> parent = null;
        for (int i = 0; i < array.size(); i++) {
          parent = getParentObject(array.get(i), idName, id);
          if (parent != null && !parent.isEmpty()) {
            break;
          }
        }
        if (parent != null && !parent.isEmpty()) {
          addChildIds(ids, parent, idName);
        }
      }
      ids.add(id);
    }
    return ids;
  }

  public static List<Map<String, Object>> getChildList(String pidName,
      Object pid, List<Map<String, Object>> list) {
    List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
    for (Map<String, Object> node : list) {
      Object pidValue = node.get(pidName);
      if (pid.equals(pidValue != null ? pidValue : "")) {
        childList.add(node);
      }
    }
    return childList;
  }

  /**
   * 寻找父节点
   */
  private static JSONObject getParentObject(JSONObject node, String idName,
      Object id) {
    if (node != null && !node.isEmpty()) {
      Object idValue = node.get(idName);
      if (id.equals(idValue)) {
        return node;
      } else if (node.get("_childList") != null) {
        JSONArray childList = node.getJSONArray("_childList");
        for (int j = 0; j < childList.size(); j++) {
          JSONObject tmpNode = getParentObject(childList.getJSONObject(j),
              idName, id);
          if (tmpNode != null) {
            return tmpNode;
          }
        }
      }
    }
    return null;
  }
  
  /**
   * 寻找父节点
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static Map<String, Object> getParentObject(Map<String, Object> node, String idName,
      Object id) {
    if (node != null && !node.isEmpty()) {
      Object idValue = node.get(idName);
      if (id.equals(idValue)) {
        return node;
      } else if (node.get("_childList") != null) {
        List<Map<String,Object>> childList = (List)node.get("_childList");
        for (int j = 0; j < childList.size(); j++) {
          Map<String, Object> tmpNode = getParentObject(childList.get(j), idName, id);
          if (tmpNode != null) {
            return tmpNode;
          }
        }
      }
    }
    return null;
  }

  /**
   * 加入子节点Id
   */
  private static void addChildIds(List<Object> ids, JSONObject parent,
      String idName) {
    if (parent.get("_childList") != null) {
      JSONArray childList = parent.getJSONArray("_childList");
      if (childList != null && childList.size() > 0) {
        for (int i = 0; i < childList.size(); i++) {
          JSONObject childNode = childList.getJSONObject(i);
          if (childNode != null && !childNode.isEmpty()) {
            addChildIds(ids, childNode, idName);
          }
        }
      }
    }
    ids.add(parent.get(idName));
  }
  
  /**
   * 加入子节点Id
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private static void addChildIds(List<Object> ids, Map<String,Object> parent,
      String idName) {
    if (parent.get("_childList") != null) {
      List<Map<String,Object>> childList = (List)parent.get("_childList");
      if (childList != null && childList.size() > 0) {
        for (int i = 0; i < childList.size(); i++) {
          Map<String,Object> childNode = childList.get(i);
          if (childNode != null && !childNode.isEmpty()) {
            addChildIds(ids, childNode, idName);
          }
        }
      }
    }
    ids.add(parent.get(idName));
  }

  public static void main(String[] args) {
    String tmpList = "[]";
    JSONArray array = JSONArray.parseArray(tmpList);
    List<Object> ids = TreeMapUtils.getChildIds(array, "category_id",
        "0146a3df6499f9458a5d468f91cb024d");
    for (Object obj : ids) {
      System.out.println(obj);
    }
  }
}
