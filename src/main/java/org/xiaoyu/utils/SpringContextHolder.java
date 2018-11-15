package org.xiaoyu.utils;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class SpringContextHolder implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  /**
   * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
   */
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringContextHolder.applicationContext = applicationContext;
  }

  /**
   * 取得存储在静态变量中的ApplicationContext.
   */
  public static ApplicationContext getApplicationContext() {
    checkApplicationContext();
    return applicationContext;
  }

  /**
   * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) {
    checkApplicationContext();
    return (T) applicationContext.getBean(name);
  }

  /**
   * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(Class<T> clazz) {
    checkApplicationContext();
    return (T) applicationContext.getBeansOfType(clazz);
  }

  /**
   * 重载修改后的Spring容器中的Bean.
   */
  @SuppressWarnings("rawtypes")
  public static void changeBeans(String beanName, Class beanClass,
      Map<String, Object> attrDataMap) {
    DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
        .getAutowireCapableBeanFactory();
    beanFactory.removeBeanDefinition(beanName);
    registerBean(beanName, beanClass, attrDataMap);
  }

  /**
   * 在Spring容器中注册一个Bean.
   */
  @SuppressWarnings("rawtypes")
  public static void registerBean(String beanName, Class beanClass,
      Map<String, Object> attrDataMap) {
    try {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
      for (Map.Entry<String, Object> entry : attrDataMap.entrySet()) {
        builder.addPropertyValue(entry.getKey(), entry.getValue());
      }
      System.out.println("动态加入Bean到Spring容器");
      ConfigurableApplicationContext configurableApplicationContext = 
          (ConfigurableApplicationContext) applicationContext;
      DefaultListableBeanFactory defaultListableBeanFactory = 
          (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
      defaultListableBeanFactory.registerBeanDefinition(beanName, builder.getRawBeanDefinition());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 在Spring容器中注册一个Bean.
   */
  @SuppressWarnings("rawtypes")
  public static void registerBean(String beanName, Class beanClass,
      Map<String, Object> attrDataMap, String initMethod, String destroyMethod) {
    try {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
      for (Map.Entry<String, Object> entry : attrDataMap.entrySet()) {
        builder.addPropertyValue(entry.getKey(), entry.getValue());
      }
      builder.setInitMethodName(initMethod);
      builder.setDestroyMethodName(destroyMethod);
      builder.setLazyInit(false);
      System.out.println("动态加入Bean到Spring容器");
      ConfigurableApplicationContext configurableApplicationContext = 
          (ConfigurableApplicationContext) applicationContext;
      DefaultListableBeanFactory defaultListableBeanFactory = 
          (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
      defaultListableBeanFactory.registerBeanDefinition(beanName, builder.getRawBeanDefinition());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 在Spring容器中删除一个Bean
   */
  public static void removeBean(String beanName) throws Exception {
    try {
      ConfigurableApplicationContext configurableApplicationContext = 
          (ConfigurableApplicationContext) applicationContext;
      DefaultListableBeanFactory defaultListableBeanFactory = 
          (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
      defaultListableBeanFactory.removeBeanDefinition(beanName);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  private static void checkApplicationContext() {
    if (applicationContext == null) {
      throw new IllegalStateException(
          "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
    }
  }
}