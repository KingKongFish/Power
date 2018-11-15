package org.xiaoyu.utils.tree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.xiaoyu.utils.data.StringKit;

import java.util.*;

/**
 * 列表数据处理为树形数据工具类.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class TreeMapProduct {

  /**
   * 列表数据处理为树形JSONArray.
   *    格式：
   *    [
   *      {"{idName}":"{value}"...,"_childList"[...]},
   *      {"{idName}":"{value}"...,"_childList"[...]}
   *      ...
   *    ]
   * 
   * @param list 列表数据
   * @param idName 数据主键字段名称
   * @param pidName 父数据主键字段名称
   * @return JSONArray
   */
  public static JSONArray getJsonTree(List<Map<String, Object>> list,String idName,String pidName) {
    JSONArray result = getJsonTree(list, idName, pidName, "");
    return result;
  }
  
  public static JSONArray getJsonTree(List<Map<String, Object>> list,
      String idName, String pidName, Object pid) {
    List<Map<String, Object>> childList = getChildList(pidName, pid, list);
    JSONArray nodeArray = new JSONArray();
    int index = 0;
    for (Map<String, Object> node : childList) {
      JSONObject childNode = (JSONObject) JSON.toJSON(node);
      nodeArray.add(index,addChildNodes(idName, pidName, node.get(idName), list, childNode));
      index++;
    }
    return nodeArray;
  }
  
  public static JSONObject addChildNodes(String idName, String pidName,Object pid, 
      List<Map<String, Object>> list, JSONObject parent) {
    try {
      List<Map<String, Object>> childList = getChildList(pidName, pid, list);
      if (childList.isEmpty()) {
        return parent;
      } else {
        JSONArray childNodeArray = new JSONArray();
        int index = 0;
        for (Map<String, Object> node : childList) {
          JSONObject childNode = (JSONObject) JSON.toJSON(node);
          childNodeArray.add(index,addChildNodes(idName, pidName, node.get(idName), list,
                      childNode));
          index++;
        }
        parent.put("_childList", childNodeArray);
        return parent;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public static List<Map<String, Object>> getTree(List<Map<String, Object>> list,String idName,
      String pidName) {
    List<Map<String, Object>> result = getTree(list, idName, pidName, "");
    return result;
  }

  public static List<Map<String, Object>> getTree(List<Map<String, Object>> list,
      String idName, String pidName, Object pid) {
    Map<String, Object> parent = null;
    if (pid != null && StringKit.isNotBlank(pid.toString())) {
      for (Map<String, Object> object : list) {
        if (object.get(idName).equals(pid)) {
          parent = object;
          break;
        }
      }
    }
    List<Map<String, Object>> childList = getChildList(pidName, pid, list);
    List<Map<String, Object>> childArray = new ArrayList<Map<String,Object>>();
    for (Map<String, Object> node : childList) {
      childArray.add(addChilds(idName, pidName, node.get(idName), list, node));
    }
    if (parent != null) {
      parent.put("_childList", childArray);
      List<Map<String, Object>> parentList = new ArrayList<Map<String, Object>>();
      parentList.add(parent);
      return parentList;
    } else {
      return childArray;
    }
  }
  
  public static Map<String, Object> addChilds(String idName, String pidName,
      Object pid, List<Map<String, Object>> list, Map<String, Object> parent) {
    try {
      List<Map<String, Object>> childList = getChildList(pidName, pid, list);
      if (childList.isEmpty()) {
        return parent;
      } else {
        List<Map<String, Object>> childArray = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> node : childList) {
          childArray.add(addChilds(idName, pidName,node.get(idName),list,node));
        }
        parent.put("_childList", childArray);
        return parent;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public static List<Map<String, Object>> getChildList(String pidName,Object pid, 
      List<Map<String, Object>> list) {
    List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
    pid = pid != null ? pid : "";
    Iterator<Map<String, Object>> listIterator = list.iterator(); 
    while (listIterator.hasNext()) {
      Map<String, Object> node = listIterator.next();
      Object pidValue = node.get(pidName);
      if (pid.equals(pidValue != null ? pidValue : "")) {
        childList.add(node);
        listIterator.remove();
      }
    }
    return childList;
  }

  public static List<Map<String, Object>> getParentAndChilds(List<Map<String, Object>> allNodes, String idName, String pidName, Object pid) {
    List<Map<String, Object>> parentAndChilds = new ArrayList<>();
    Map<String, Object> parent = null;
    if (pid != null && StringKit.isNotBlank(pid.toString())) {
      for (Map<String, Object> object : allNodes) {
        if (object.get(idName).equals(pid)) {
          parent = object;
          break;
        }
      }
    }
    //获取顶级节点
    List<Map<String, Object>> childList = getChildList(pidName, pid, allNodes);
    if(childList != null && childList.size() > 0) {
      parentAndChilds.addAll(childList);
    }
    for (Map<String, Object> node : childList) {
      addChildList(idName, pidName, node.get(idName), allNodes, node, parentAndChilds);
    }
    if (parent != null) {
      parentAndChilds.add(parent);
    }
    return parentAndChilds;
  }

  public static void addChildList(String idName, String pidName, Object pid,
    List<Map<String, Object>> allNodes, Map<String, Object> parent, List<Map<String, Object>> parentAndChilds) {
    try {
      List<Map<String, Object>> childList = getChildList(pidName, pid, allNodes);
      if (childList != null && childList.isEmpty()) {
        return;
      } else {
        if(childList != null && childList.size() > 0) {
          parentAndChilds.addAll(childList);
        }
        for (Map<String, Object> node : childList) {
          addChildList(idName, pidName, node.get(idName), allNodes,node, parentAndChilds);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main (String[] args) {
    Map<String,Object> map1 = new HashMap<String,Object>();
    map1.put("id",1);
    map1.put("pid",null);
    Map<String,Object> map2 = new HashMap<String,Object>();
    map2.put("id",2);
    map2.put("pid",null);
    Map<String,Object> map3 = new HashMap<String,Object>();
    map3.put("id",3);
    map3.put("pid",1);
    Map<String,Object> map4 = new HashMap<String,Object>();
    map4.put("id",4);
    map4.put("pid",1);
    Map<String,Object> map5 = new HashMap<String,Object>();
    map5.put("id",5);
    map5.put("pid",2);
    Map<String,Object> map6 = new HashMap<String,Object>();
    map6.put("id",6);
    map6.put("pid",3);

    List<Map<String,Object>> allNodes = new ArrayList<>();
    allNodes.add(map1);
    allNodes.add(map2);
    allNodes.add(map3);
    allNodes.add(map4);
    allNodes.add(map5);
    allNodes.add(map6);

    List<Map<String,Object>> childs = TreeMapProduct.getParentAndChilds(allNodes, "id", "pid", 3);
    System.out.println(childs.size());
  }
}
