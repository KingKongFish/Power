package org.xiaoyu.utils.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;

/**
 * FreeMarker 模板引擎工具类.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class FreeMarkerUtils {
  
  /**
   * 根据模板名称及地址获取内容.
   * 
   * @param templateName 模板名称
   * @param templatePath 模板路径
   * @param data 模板参数
   * @return String 模板内容
   */
  public static String getContentByFile(String templateName, String templatePath,
      Object data) throws Exception {
    String emailContent = null;
    Template template = getTemplate(templateName, templatePath);
    // 输出文档路径及名称
    StringWriter stringWriter = new StringWriter();
    template.process(data, stringWriter);
    emailContent = stringWriter.toString();
    return emailContent;
  }

  /**
   * 根据模板名称及地址获取模板.
   * 
   * @param templateName 模板名称
   * @param templatePath 模板路径
   * @return Template 模板内容
   */
  public static Template getTemplate(String templateName, String templatePath) {
    try {
      Configuration configuration = new Configuration();
      configuration.setDefaultEncoding("UTF-8");
      configuration.setClassForTemplateLoading(FreeMarkerUtils.class, templatePath);
      Template template = null;
      template = configuration.getTemplate(templateName);
      return template;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * 根据模板模板内容生成内容.
   * 
   * @param templateName 模板名称
   * @param templateContent 模板内容
   * @param data 模板参数
   * @return 模板内容
   * 
   */
  public static String getContentByString(String templateName, String templateContent, Object data) 
      throws Exception {
    StringTemplateLoader stringLoader = new StringTemplateLoader();  
    stringLoader.putTemplate(templateName, templateContent);  
    Configuration configuration = new Configuration();
    configuration.setTemplateLoader(stringLoader);   
    configuration.setDefaultEncoding("UTF-8");   
    Template template = configuration.getTemplate(templateName, "UTF-8");
    // 输出文档路径及名称
    StringWriter stringWriter = new StringWriter();
    template.process(data, stringWriter);
    String emailContent = stringWriter.toString();
    return emailContent;
  }
}
