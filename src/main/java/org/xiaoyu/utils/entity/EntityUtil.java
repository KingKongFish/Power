package org.xiaoyu.utils.entity;

import org.xiaoyu.utils.data.StringKit;

import java.lang.reflect.Field;

/**
 * 实体工具类.
 * 
 * @author peilongwu
 * @date 2016-09-08
 */
public class EntityUtil {
  
  /**
   * 从实体中获取某个属性的值.
   * 
   * @param entity 实体
   * @param fieldName 属性名称
   * @return 属性值
   */
  public static Object getFieldValue(Object entity, String fieldName) {
    Object returnValue = null;
    try {
      Field field = entity.getClass().getDeclaredField(fieldName);
      if (!field.isAccessible()) {
        field.setAccessible(true);
        returnValue = field.get(entity);
        field.setAccessible(false);
      } else {
        returnValue = field.get(entity);
      }
    } catch (Exception e) {
      System.err.println("Field is not exist.");
    }
    return returnValue;
  }

  /**
   * 判断实体中是否存在某属性.
   * 
   * @param entity 实体
   * @param fieldName 属性名称
   * @return 是否存在标识
   */
  public static boolean isHasField(Object entity, String fieldName) {
    try {
      Field field = entity.getClass().getDeclaredField(fieldName);
      if (field != null) {
        return true;
      }
    } catch (Exception e) {
      System.err.println("Entity is not has '" + fieldName + "' feild.");
    }
    return false;
  }

  /**
   * 属性名称转换为驼峰写法.
   * 
   * @param fieldName 属性名称
   * @return 驼峰写法的属性名称
   */
  public static String toCamelString(String fieldName) {
    return StringKit.toCamelString(fieldName);
  }
}
