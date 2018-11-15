package org.xiaoyu.utils.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * 二进制转换为输入流工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class ByteToInputStream {

  /**
   * 二进制数据转换为输入流.
   * @param buf 二进制数组
   * @return 输入流
   */
  public static final InputStream byte2Input(byte[] buf) {
    return new ByteArrayInputStream(buf);
  }

  /**
   * 输入流转换为二进制数组.
   * @param inStream 输入流
   * @return 二进制数组
   * @throws IOException 输入输出异常.
   */
  public static final byte[] input2byte(InputStream inStream)
      throws IOException {
    ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
    byte[] buff = new byte[100];
    int rc = 0;
    while ((rc = inStream.read(buff, 0, 100)) > 0) {
      swapStream.write(buff, 0, rc);
    }
    byte[] in2b = swapStream.toByteArray();
    return in2b;
  }

}
