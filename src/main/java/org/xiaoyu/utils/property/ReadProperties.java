package org.xiaoyu.utils.property;

import java.io.InputStream;
import java.util.*;

/**
 * 读入配置文件
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public final class ReadProperties {

  private Properties properties;

  public ReadProperties(String fileName) {
    properties = new Properties();
    InputStream stream = ReadProperties.class.getResourceAsStream(fileName);
    try {
      properties.load(stream);
    } catch (Exception e) {
      e.printStackTrace();
    }
    properties.putAll(System.getProperties());
  }

  /**
   * @param filename :文件名称
   * @return PropertyResourceBundle 取得文件
   */
  private PropertyResourceBundle getFile(String filename) {

    return (PropertyResourceBundle) ResourceBundle.getBundle(filename);
  }

  /**
   * @param propertyName :对象名称
   * @param filename :文件名称
   * @return 对象值 取得配置文件的对象值
   */
  @SuppressWarnings("unused")
  private String getProperty(String propertyName, String filename) {

    return getFile(filename).getString(propertyName);

  }

  /**
   * @param propertyName 对象名称
   * @return 对象值 取得配置文件的对象值
   */
  public Object getProperty(String propertyName) {
    Object result = properties.get(propertyName);
    return result;
  }

  public static void main(String[] args) {
    ReadProperties rp = new ReadProperties("/mail.properties");

    System.out.println(rp.getProperty("fromPassword").toString());
  }
}
