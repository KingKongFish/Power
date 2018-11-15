package org.xiaoyu.utils.data;

import java.net.InetAddress;
import java.util.UUID;

;

/**
 * The base class for identifier generators that use a UUID algorithm. This
 * class implements the algorithm, subclasses define the identifier format.
 *
 * @see UuidHexGenerator
 * @see UUIDStringGenerator
 * @author Gavin King
 */

public abstract class UuidGenerator {

  private static final int IP;
  
  static {
    int ipadd;
    try {
      ipadd = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
    } catch (Exception e) {
      ipadd = 0;
    }
    IP = ipadd;
  }
  
  private static short counter = (short) 0;
  private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

  public UuidGenerator() {
  }

  /**
   * Unique across JVMs on this machine (unless they load this class in the same
   * quater second - very unlikely)
   */
  protected int getJvm() {
    return JVM;
  }

  /**
   * Unique in a millisecond for this JVM instance (unless there are >
   * Short.MAX_VALUE instances created in a millisecond)
   */
  protected short getCount() {
    synchronized (UuidGenerator.class) {
      if (counter < 0) {
        counter = 0;
      }
      return counter++;
    }
  }

  /**
   * Unique in a local network.
   */
  protected int getIp() {
    return IP;
  }

  /**
   * Unique down to millisecond.
   */
  protected short getHiTime() {
    return (short) (System.currentTimeMillis() >>> 32);
  }

  protected int getLoTime() {
    return (int) System.currentTimeMillis();
  }

  /**
   * 
   * @return
   */
  public static String getUuid32() {
    String uuid = UUID.randomUUID().toString().replace("-", "");
    return uuid;
  }

}
