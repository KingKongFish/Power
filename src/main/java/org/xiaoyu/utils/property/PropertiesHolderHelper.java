package org.xiaoyu.utils.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xiaoyu.utils.data.StringKit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 配置文件读取工具类，用于引擎全部配置文件使用：
 *    把新增的配置文件都加入：props.properties，该类会依次加载所有配置.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class PropertiesHolderHelper {
  
  static Logger logger = LoggerFactory.getLogger(PropertiesHolderHelper.class);
  private static PropertiesHolder propertiesHolder = new PropertiesHolder();

  private static final String[] DEFAULT_LOCATIONS = new String[] 
      { "classpath:properties/props.properties" };

  private static String[] getPropertiesFiles() {
    propertiesHolder.initProperties(DEFAULT_LOCATIONS);
    Properties props = propertiesHolder.getProperties();
    ArrayList<String> propsFiles = new ArrayList<String>();
    for (Entry<Object, Object> entry : props.entrySet()) {
      propsFiles.add((String) entry.getValue());
    }
    //propsFiles.add("classpath:properties/ead-platform.properties");
    String[] files = new String[propsFiles.size()];
    return propsFiles.toArray(files);
  }

  static {
    String[] propsFiles = getPropertiesFiles();
    propertiesHolder.initProperties(propsFiles);
    Properties props = propertiesHolder.getProperties();
    ArrayList<String> keys = new ArrayList<String>();
    for (Object e : props.keySet()) {
      keys.add((String) e);
    }
    Collections.sort(keys);
  }

  public static Properties getProperties() {
    return propertiesHolder.getProperties();
  }

  public static Properties getProperties(String prefix) {
    return propertiesHolder.getProperties(prefix);
  }
  
  public static String getProperty(String key) {
    return StringKit.toString(propertiesHolder.getProperties().get(key));
  }

  public static void main(String[] args) {
  }
}
