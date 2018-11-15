package org.xiaoyu.utils.data;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * getMD5默认编码为UTF-8,加密字符串小写 getSHA1默认编码为UTF-8,加密字符串小写
 * getStrcpy可根据需要传入加密算法类型,是否大小写,编码格式
 * 
 * @author yangyu
 * 
 */
public class EncryptKit {

	/**
	 * md5加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
  public static String getMD5(String str) throws Exception {
    return md5(str, "UTF-8", true);
  }

  /**
   * 字符串加密
   * @param str
   * @return
   */
  public static String getSha(String str) {
    return getStrcpy(str, "SHA-1", true, "UTF-8");
  }

  public static String getStrcpy(String str, String algorithm,
      boolean isLowerCase, String encoding) {
    MessageDigest msgDigest = null;
    String code = "";
    try {
      algorithm = algorithm.toUpperCase();
      msgDigest = MessageDigest.getInstance(algorithm);
      msgDigest.update(str.getBytes(encoding));// 汉字加密需加"UTF-8"编码
      byte[] bytes = msgDigest.digest();
      BigInteger bigInt = new BigInteger(1, bytes);
      if (isLowerCase) {
        code = bigInt.toString(16).toLowerCase();
      } else {
        code = bigInt.toString(16).toUpperCase();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return code;
  }

  public static String getStrcpy(String str, String algorithm,
      boolean isLowerCase, String encoding, int length) {
    MessageDigest msgDigest = null;
    String code = "";
    try {
      algorithm = algorithm.toUpperCase();
      msgDigest = MessageDigest.getInstance(algorithm);
      msgDigest.update(str.getBytes(encoding));// 汉字加密需加"UTF-8"编码
      byte[] bytes = msgDigest.digest();
      BigInteger bigInt = new BigInteger(1, bytes);
      if (isLowerCase) {
        code = bigInt.toString(16).toLowerCase();
      } else {
        code = bigInt.toString(16).toUpperCase();
      }
      if (code.length() >= length) {
        code = code.substring(0, length);
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return code;
  }
  
  public static String md5(String encodeStr, String encoding, boolean isLowerCase)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 
        'B', 'C', 'D', 'E', 'F'};
    try {
      // byte[] btInput = s.getBytes();
      // 获得MD5摘要算法的 MessageDigest 对象
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      // 使用指定的字节更新摘要
      mdInst.update(encodeStr.getBytes(encoding));// 汉字加密需加"UTF-8"编码
      // mdInst.update(btInput);
      // 获得密文
      byte[] md = mdInst.digest();
      // 把密文转换成十六进制的字符串形式
      int mdLength = md.length;
      char[] str = new char[mdLength * 2];
      int index = 0;
      for (int i = 0; i < mdLength; i++) {
        byte byte0 = md[i];
        str[index++] = hexDigits[byte0 >>> 4 & 0xf];
        str[index++] = hexDigits[byte0 & 0xf];
      }
      return isLowerCase == true ? new String(str).toLowerCase() : new String(
          str).toUpperCase();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw e;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static void main(String[] args) {
    try {
      System.out.println(getMD5("25722621450660612098f944d0cd262511e5a8c52586336b7f3d"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
