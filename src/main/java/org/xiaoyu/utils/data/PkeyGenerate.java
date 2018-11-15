package org.xiaoyu.utils.data;

/**
 * 主键生成器.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class PkeyGenerate {

  /**
   * 批量生成一批主键.
   * @param num 生成主键的数目.
   * @return 主键数组.
   */
  public static String[] generatePKeys(int num) {
    String[] pkeys = new String[num];
    for (int i = 0; i < num; i++) {
      pkeys[i] = getPKey();
    }
    return pkeys;
  }

  /**
   * 返回指定长度的父节点主键列表
   * 
   * @param pid 父节点主键
   * @param length 长度
   * @return 字符数组
   */
  public static String[] parentKeys(String pid, int length) {
    String[] pkeys = new String[length];
    for (int i = 0; i < length; i++) {
      pkeys[i] = pid;
    }
    return pkeys;
  }

  /**
   * 主键生成方法.
   * @return 生成的主键字符串
   */
  public static String getPKey() {
    UuidHexGenerator uuid = new UuidHexGenerator();
    return uuid.generate().toString();
  }

  /**
   * 主键生成方法.
   * @return 生成的主键字符串
   */
  public static String getPKey(int length) {
    UuidHexGenerator uuid = new UuidHexGenerator();
    if (length > 32) {
      length = 32;
    }
    return uuid.generate(length).toString();
  }


  /**
   * 随机生成六位加盐字符串.
   * @return
   */
  public static String getSalt() {
    return EncryptKit.getStrcpy(getPKey(), "MD5", false, "UTF-8", 6).toLowerCase();
  }

  public static void main(String[] args) throws Exception {
    System.out.println(getPKey());
    System.out.println(getPKey(4));
    System.out.println(11111);
    System.out.println(EncryptKit.getStrcpy(getPKey(), "MD5", false, "UTF-8", 32));
    System.out.println(getSalt());
    String[] pkeys = generatePKeys(50);
    for (int i = 0; i < pkeys.length; i++) {
      System.out.println(pkeys[i]);
    }
  }
}
