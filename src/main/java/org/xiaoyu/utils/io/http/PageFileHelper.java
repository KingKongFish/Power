package org.xiaoyu.utils.io.http;

import java.io.File;
import java.io.FileInputStream;

/**
 * 页面文件帮助工具类.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class PageFileHelper {

  /**
   * 从文件获取页面内容.
   * 
   * @param filePath
   * @return String
   */
  public static String getPageContent(String filePath) {
    try {
      String pageContent = null;
      File page = new File(filePath);
      byte[] fbytes = new byte[(int) page.length()];
      if (!page.exists()) {
        pageContent = "页面不存在";
      }
      FileInputStream fileInStream = new FileInputStream(page);
      fileInStream.read(fbytes);
      pageContent = new String(fbytes, "UTF-8");
      fileInStream.close();
      return pageContent;
    } catch (Exception e) {
      e.printStackTrace();
      return "系统异常，请联系管理员";
    }
  }

}
