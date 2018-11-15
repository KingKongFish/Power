package org.xiaoyu.utils.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件读取工具类.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class PropertiesHolder {
  static Logger logger = LoggerFactory.getLogger(PropertiesHolder.class);

  private Properties propsHolder = new Properties();
  private Object lock = new Object();
  private String[] locations;

  private void loadProperties(InputStream is) {
    Properties props = new Properties();
    try {
      props.load(is);
      propsHolder.putAll(props);
    } catch (IOException e) {
      logger.error("load Input Stream error", e);
    }
  }

  private void loadFromClasspath(String resourceName) {
    InputStream is = PropertiesHolder.class.getClassLoader()
        .getResourceAsStream(resourceName);
    if (is == null) {
      throw new RuntimeException("Not found " + resourceName + " in classpath");
    }
    loadProperties(is);
  }

  private void loadFromFileSystem(String resourceName)
      throws FileNotFoundException {
    InputStream is = new FileInputStream(resourceName);
    loadProperties(is);
  }

  public Properties getProperties() {
    if (propsHolder.size() == 0) {
      synchronized (lock) {
        if (propsHolder.size() == 0) {
          initProperties(locations);
        }
      }
    }
    return propsHolder;
  }

  public Properties getProperties(String namespace) {
    Properties properties = getProperties();
    Properties props = new Properties();
    String prefix = namespace + ".";
    for (Object key : properties.keySet()) {
      String keyStr = (String) key;
      String value = properties.getProperty(keyStr);
      if (keyStr.indexOf(prefix) == 0) {
        String subKey = keyStr.substring(prefix.length());
        if (subKey != null && subKey.length() != 0) {
          props.setProperty(subKey, value);
        }
      }
    }
    return props;
  }

  public void initProperties(String[] resourceNames) {
    for (String resourceName : resourceNames) {
      try {
        resourceName = replaceWithEnvironment(resourceName);
        if (resourceName.indexOf("classpath:") == 0) {
          loadFromClasspath(resourceName.substring("classpath:".length()));
        } else if (resourceName.indexOf("file:") == 0) {
          loadFromFileSystem(resourceName.substring("file:".length()));
        }
        logger.info("Load resource properties success:" + resourceName);
      } catch (Exception e) {
        logger.error("Load resource properties error:" + e.getMessage());
      }
    }
  }

  private String replaceWithEnvironment(String str) {
    for (String env : System.getenv().keySet()) {
      String value = System.getenv(env);
      str = replaceTokenValue(str, env, value);
    }
    return str;
  }

  private String getStringReplaceToken(String name) {
    return "${" + name + "}";
  }

  private String replaceTokenValue(String str, String variable, String value) {
    String token = getStringReplaceToken(variable);
    return str.replace(token, value);
  }

  public void setLocations(String[] locations) {
    this.locations = locations;
  }

  public String[] getLocations() {
    return locations;
  }
}
