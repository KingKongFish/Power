package org.xiaoyu.utils.property;

import java.io.InputStream;
import java.util.*;

/**
 * 读取配置文件工具类.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public final class ReadProperty {
  
  private static final Properties properties;

  static {
    properties = new Properties();
    InputStream stream = ReadProperties.class
        .getResourceAsStream("/dataCollection.properties");
    try {
      properties.load(stream);
    } catch (Exception e) {
      e.printStackTrace();
    }
    properties.putAll(System.getProperties());
  }

  /**
   * @param propertyName
   */
  public static String getPropertyByStr(String propertyName) {
    return String.valueOf(ReadProperty.properties.get(propertyName));
  }

  /**
   * @param propertyName
   */
  public static int getPropertyByInt(String propertyName) {
    return Integer.parseInt(ReadProperty.getPropertyByStr(propertyName));
  }
}
