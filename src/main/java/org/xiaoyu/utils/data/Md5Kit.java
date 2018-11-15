package org.xiaoyu.utils.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class Md5Kit {

  /**
   * 对文件进行Md5加密.
   * @param file 文件
   * @return Md5加密码
   */
  public static String getMd5ByFile(File file) {
    String value = null;
    FileInputStream in = null;
    try {
      in = new FileInputStream(file);
      MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(byteBuffer);
      BigInteger bi = new BigInteger(1, md5.digest());
      value = bi.toString(16);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != in) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return value;
  }

  /**
   * 对文件进行Md5加密.（带缓冲区）
   * @param file 文件
   * @return Md5加密码
   */
  public static String getFileMd5(File file) {
    if (!file.isFile()) {
      return null;
    }
    MessageDigest digest = null;
    FileInputStream in = null;
    byte[] buffer = new byte[1024];
    int len;
    try {
      digest = MessageDigest.getInstance("MD5");
      in = new FileInputStream(file);
      while ((len = in.read(buffer, 0, 1024)) != -1) {
        digest.update(buffer, 0, len);
      }
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    BigInteger bigInt = new BigInteger(1, digest.digest());
    return bigInt.toString(16);
  }

  /**
   * 对输入流进行Md5加密.
   * @param in 输入流
   * @return Md5加密码
   */
  public static String getFileMd5(InputStream in) {
    MessageDigest digest = null;
    byte[] buffer = new byte[1024];
    int len;
    try {
      digest = MessageDigest.getInstance("MD5");
      while ((len = in.read(buffer, 0, 1024)) != -1) {
        digest.update(buffer, 0, len);
      }
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    BigInteger bigInt = new BigInteger(1, digest.digest());
    return bigInt.toString(16);
  }

  /**
   * 对二进制数据进行Md5加密.
   * @param bytes 二进制数组
   * @return Md5加密码
   */
  public static String getFileMd5(byte[] bytes) {
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("MD5");
      digest.update(bytes);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    BigInteger bigInt = new BigInteger(1, digest.digest());
    return bigInt.toString(16);
  }

  /**
   * 对一段String生成MD5加密信息.
   * 
   * @param message 要加密的String
   * @return 生成的MD5信息
   */
  public static String getMd5(String message) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      System.err.println("MD5摘要长度：" + md.getDigestLength());
      byte[] bytes = md.digest(message.getBytes("utf-8"));
      return byteToHexStringSingle(bytes);
    } catch (NoSuchAlgorithmException exp) {
      exp.printStackTrace();
    } catch (UnsupportedEncodingException exp) {
      exp.printStackTrace();
    }
    return null;
  }

  /**
   * 对一段String生成MD5加密信息.
   *
   * @param message 要加密的String
   * @return 生成的MD5 Byte[]
   */
  public static byte[] getMd5Bytes(String message) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      return md.digest(message.getBytes("utf-8"));
    } catch (NoSuchAlgorithmException exp) {
      exp.printStackTrace();
    } catch (UnsupportedEncodingException exp) {
      exp.printStackTrace();
    }
    return null;
  }

  /**
   * 独立把byte[]数组转换成十六进制字符串表示形式.
   * 
   * @param byteArray 二进制数组.
   * @return
   * @author Bill
   * @create 2010-2-24 下午03:26:53
   */
  public static String byteToHexStringSingle(byte[] byteArray) {
    StringBuffer md5StrBuff = new StringBuffer();

    for (int i = 0; i < byteArray.length; i++) {
      if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
        md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
      } else {
        md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
      }
    }

    return md5StrBuff.toString();
  }

}
