package org.xiaoyu.utils.data;

/**
 * 变量工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class VariableKit {
  
  /**
   * 获取变量，当前变量不为空，以当前变量为准，当前变量为空以全局变量为准.
   * @param global 全局
   * @param local 当前
   * @return
   */
  public static String getValue(String global, String local) {
    String variable = local;
    if (StringKit.isBlank(variable)) {
      variable = global;
    }
    return variable;
  }
}
