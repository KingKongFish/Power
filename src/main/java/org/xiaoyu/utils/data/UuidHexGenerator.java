package org.xiaoyu.utils.data;

import java.io.Serializable;

/**
 * Uuid生成器.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class UuidHexGenerator extends UuidGenerator {

  private String sep = "";

  public String formatCode(int intval) {
    String formatted = Integer.toHexString(intval);
    StringBuffer buf = new StringBuffer("00000000");
    buf.replace(8 - formatted.length(), 8, formatted);
    return buf.toString();
  }

  protected String format(int intval) {
    String formatted = Integer.toHexString(intval);
    StringBuffer buf = new StringBuffer("00000000");
    buf.replace(8 - formatted.length(), 8, formatted);
    return buf.toString();
  }

  protected String format(short shortval) {
    String formatted = Integer.toHexString(shortval);
    StringBuffer buf = new StringBuffer("0000");
    buf.replace(4 - formatted.length(), 4, formatted);
    return buf.toString();
  }

  public Serializable generate() {
    return new StringBuffer(36).append(format(getHiTime())).append(sep)
        .append(format(getLoTime())).append(sep).append(format(getIp()))
        .append(sep).append(format(getJvm())).append(sep)
        .append(format(getCount())).toString();
  }
  
  public Serializable generate(Integer length) {
    String buffer = new StringBuffer(36).append(format(getHiTime())).append(sep)
        .append(format(getLoTime())).append(sep).append(format(getIp()))
        .append(sep).append(format(getJvm())).append(sep)
        .append(format(getCount())).toString();
    String encode = Md5Kit.getFileMd5(buffer.getBytes());
    return encode.substring(0, length);
  }

  public static void main(String[] args) {
    UuidHexGenerator uuid = new UuidHexGenerator();
    for (int i = 0; i < 100; i++) {
      System.out.println(uuid.generate());
    }
  }

}
