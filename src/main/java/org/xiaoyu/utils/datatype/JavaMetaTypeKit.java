package org.xiaoyu.utils.datatype;

import org.xiaoyu.utils.data.StringKit;

/**
 * Java基础类型转换工具类.
 * 
 * @author peilongwu
 * @date 2016-09-08
 */
public class JavaMetaTypeKit {

  /**
   * 由类型名及值转换为Java基础类型值.
   * 
   * @param typeName 类型名称
   * @param value 值
   * @return 类型对象
   */
  public static Object toItsType(String typeName, String value) {
    boolean blankFlag = StringKit.isBlank(value);
    Object convertValue = null;
    switch (JavaMetaType.getJavaMetaType(typeName)) {
      case String: {
        convertValue = value;
        break;
      }
      case Integer: {
        convertValue = (blankFlag == true ? null : Integer.parseInt(value));
        break;
      }
      case Long: {
        convertValue = (blankFlag == true ? null : Long.parseLong(value));
        break;
      }
      case Float: {
        convertValue = (blankFlag == true ? null : Float.parseFloat(value));
        break;
      }
      case Double: {
        convertValue = (blankFlag == true ? null : Double.parseDouble(value));
        break;
      }
      case Boolean: {
        convertValue = StringKit.toBoolean(value);
        break;
      }
      case Byte: {
        convertValue = (blankFlag == true ? null : Byte.parseByte(value));
        break;
      }
      case DbTime: {
        convertValue = value;
        break;
      }
      case Unknow: {
        convertValue = value;
        break;
      }
      default: {
        break;
      }
    }
    return convertValue;
  }

}
