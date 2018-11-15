package org.xiaoyu.utils.data;

import java.security.MessageDigest;

/**
 * Sha加密工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class ShaDegist {
  
  private static MessageDigest SHA_Degist = null;
  public static final String SHA_HEAD = "{SHA}";

  /**
   * constructor.
   * @throws Exception
   */
  public ShaDegist() throws Exception {
    if (SHA_Degist == null) {
      SHA_Degist = MessageDigest.getInstance("SHA");
    }
  }

  private static byte[] shaCrypt(String src) throws Exception {
    if (SHA_Degist == null) {
      SHA_Degist = MessageDigest.getInstance("SHA");
    }
    SHA_Degist.update(src.getBytes());
    return SHA_Degist.digest();
  }

  public static String getSha(String src) throws Exception {
    if ((src != null) && (src.indexOf("{SHA}") == 0)) {
      return src;
    }
    StringBuffer sb = new StringBuffer("{SHA}");
    sb.append(new String(
        Base64Kit.encode(shaCrypt(StringKit.isNotBlank(src) ? src : ""))));
    return sb.toString();
  }
  
  public static void main(String[] args) throws Exception {
    System.out.println(getSha("1121") + ";");
  }
}