package org.xiaoyu.utils.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 内存检测.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class MemoryMonitor {

  private static final Logger logger = LoggerFactory
      .getLogger(MemoryMonitor.class);

  public static void outputMemoryInfo() {
    float max = Runtime.getRuntime().maxMemory() / (1024 * 1024);
    float used = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
        .freeMemory()) / (1024 * 1024);
    float free = Runtime.getRuntime().freeMemory() / (1024 * 1024);
    float total = Runtime.getRuntime().totalMemory() / (1024 * 1024);
    logger.debug("Max can use: " + max + "MB"); // 最大可用内存，对应-Xmx
    logger.debug("Free and Used）: " + used + "MB"); // 当前JVM占用的内存总数，其值相当于当前JVM已使用的内存及freeMemory()的总和
    logger.debug("Free: " + free + "MB"); // 当前JVM空闲内存
    // 当前JVM占用的内存总数，其值相当于当前JVM已使用的内存及freeMemory()的总和
    logger.debug("Free and Used）: " + total + "MB"); 
    
  }
}
