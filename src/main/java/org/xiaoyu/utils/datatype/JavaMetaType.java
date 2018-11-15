package org.xiaoyu.utils.datatype;

/**
 * Java基础类型.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public enum JavaMetaType {

  String, Integer, Long, Float, Double, Boolean, Byte, DbTime, Unknow;

  public static JavaMetaType getJavaMetaType(String typeName) {
    if ("String".equals(typeName)) {
      return JavaMetaType.String;
    }
    if ("Integer".equals(typeName)) {
      return JavaMetaType.Integer;
    }
    if ("Long".equals(typeName)) {
      return JavaMetaType.Long;
    }
    if ("Float".equals(typeName)) {
      return JavaMetaType.Float;
    }
    if ("Double".equals(typeName)) {
      return JavaMetaType.Double;
    }
    if ("Boolean".equals(typeName)) {
      return JavaMetaType.Boolean;
    }
    if ("Byte".equals(typeName)) {
      return JavaMetaType.Byte;
    }
    if ("DbTime".equals(typeName)) {
      return JavaMetaType.DbTime;
    }
    return JavaMetaType.Unknow;
  }

}
