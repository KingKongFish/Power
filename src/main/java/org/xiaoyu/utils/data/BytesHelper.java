package org.xiaoyu.utils.data;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;

/**
 * 二进制数据转换工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public final class BytesHelper {

  /**
   * 二进制转换为整型.
   * @param bytes
   * @return 整型
   */
  public static int toInt(byte[] bytes) {
    int result = 0;
    for (int i = 0; i < 4; i++) {
      result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
    }
    return result;
  }

  /**
   * 二进制转换为短整型.
   * @param bytes
   * @return 短整型
   */
  public static short toShort(byte[] bytes) {
    return (short) (((-(short) Byte.MIN_VALUE + (short) bytes[0]) << 8)
        - (short) Byte.MIN_VALUE + (short) bytes[1]);
  }

  /**
   * 整型转换为二进制.
   * @param bytes
   * @return 二进制数组
   */
  public static byte[] toBytes(int value) {
    byte[] result = new byte[4];
    for (int i = 3; i >= 0; i--) {
      result[i] = (byte) ((0xFFL & value) + Byte.MIN_VALUE);
      value >>>= 8;
    }
    return result;
  }

  /**
   * 短整型转换为二进制.
   * @param bytes
   * @return 二进制数组
   */
  public static byte[] toBytes(short value) {
    byte[] result = new byte[2];
    for (int i = 1; i >= 0; i--) {
      result[i] = (byte) ((0xFFL & value) + Byte.MIN_VALUE);
      value >>>= 8;
    }
    return result;
  }

  /**
   * 对象转换为二进制.
   * @param bytes
   * @return 二进制数组
   */
  public static byte[] toBytes(Object object) throws IOException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream outObj = new ObjectOutputStream(byteOut);
    outObj.writeObject(object);
    byte[] objbytes = byteOut.toByteArray();
    return objbytes;
  }

  /**
   * Blob转换为二进制.
   * @param bytes
   * @return 二进制数组
   */
  public static Object toObject(Blob blob) throws Exception {
    InputStream is = blob.getBinaryStream();
    BufferedInputStream input = new BufferedInputStream(is);

    // byte[] buff = blob.getBytes(0, blob.length());
    byte[] buff = new byte[Integer.parseInt(blob.length() + "")];
    while (-1 != (input.read(buff, 0, buff.length))){
    };

    ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buff));
    Object object = (Object) in.readObject();
    return object;
  }
}
