package org.xiaoyu.utils.data;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字符串处理工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class StringKit {

  /**
   * 对象转换为字符串，对于NULL转换为""空字符串.
   * @param obj 对象
   * @return 对象转换后的字符串.
   */
  public static String toString(Object obj) {
    if(obj instanceof Double) {
      BigDecimal bg=new BigDecimal(obj.toString());
      return bg.toPlainString();
    } else {
      return obj == null || obj.toString().trim().toUpperCase().equals("NULL") ? "" : obj.toString();
    }
  }

  /**
   * 对象转换为字符串，对于NULL转换为""空字符串,且对于字符串前后的空格是否去除由标志位决定.
   * @param obj 字符串对象
   * @param istrim 是否去除字符串前后空格
   * @return 对象转换后的字符串
   */
  public static String toString(Object obj, boolean istrim) {
    if (istrim) {
      return obj == null || obj.toString().trim().toUpperCase().equals("NULL") ? ""
          : obj.toString().trim();
    } else {
      return obj == null || obj.toString().trim().toUpperCase().equals("NULL") ? ""
          : obj.toString();
    }
  }

  /**
   * 判断字符串是否为空.
   * @param str 字符串
   * @return 是否为空标识
   */
  public static boolean isEmpty(String str) {
    return ((str == null) || (str.length() == 0));
  }

  /**
   * 判断字符串是否不为空.
   * @param str 字符串
   * @return 是否不为空标识
   */
  public static boolean isNotEmpty(String str) {
    if (str != null) {
      str = str.trim();
    }
    return (!(isEmpty(str)));
  }

  /**
   * 是否为空字符串.
   * 
   * @param str 字符串
   * @return 是否为空字符串标识
   */
  public static boolean isBlank(String str) {
    int strLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return true;
    }
    for (int i = 0; i < strLen; ++i) {
      if (!(Character.isWhitespace(str.charAt(i)))) {
        return false;
      }
    }
    return true;
  }

  /**
   * 是否不为空字符串.
   * 
   * @param str 字符串
   * @return 是否不为空字符串标识
   */
  public static boolean isNotBlank(String str) {
    return (!(isBlank(str)));
  }

  /**
   * 判断字符串1与字符串2是否相等.
   * 
   * @param str1 字符串1
   * @param str2 字符串2
   * @return 字符串1与字符串2是否相等标识
   */
  public static boolean equals(String str1, String str2) {
    return ((str1 == null) ? false : (str2 == null) ? true : str1.equals(str2));
  }

  /**
   * 0,1字符转布尔型.
   * 
   * @param value 字符串
   * @return boolean
   */
  public static Boolean toBoolean(String value) {
    if (isBlank(value)) {
      return null;
    } else {
      if ("1".equals(value) || "true".equals(value)) {
        return Boolean.TRUE;
      } else if ("0".equals(value) || "false".equals(value)) {
        return Boolean.FALSE;
      } else {
        throw new RuntimeException("Can not parse the parameter \"" + value
            + "\" to boolean value.");
      }
    }
  }

  /**
   * 对象转字符串.
   * 
   * @param obj 对象
   * @return String
   */
  public static String convertToString(Object obj) {
    if (obj == null) {
      return "";
    }
    String str = obj.toString().trim();
    if (str.equals("null") || str.equals("NULL")) {
      return "";
    }
    return str;
  }

  /**
   * 对象转双精度型.
   * 
   * @param obj 对象
   * @return double
   */
  public static double convertToDouble(Object obj) {
    if (obj == null) {
      return 0d;
    }
    try {
      double tmpD = Double.parseDouble(obj.toString().trim());
      if (tmpD == 0.0 || tmpD == -0.0 || (tmpD < 1E-9 && tmpD > -1E-9)) {
        return 0d;
      }
      return tmpD;
    } catch (NumberFormatException e) {
      return 0d;
    }
  }
  
  /**
   * 对象转浮点型.
   * 
   * @param obj 对象
   * @return float
   */
  public static float convertToFloat(Object obj) {
    if (obj == null) {
      return 0f;
    }
    try {
      float tmpD = Float.parseFloat(obj.toString().trim());
      if (tmpD == 0.0 || tmpD == -0.0 || (tmpD < 1E-9 && tmpD > -1E-9)) {
        return 0f;
      }
      return tmpD;
    } catch (NumberFormatException e) {
      return 0f;
    }
  }

  /**
   * 对象转双精度型.
   * 
   * @param obj 对象
   * @return
   */
  public static double convertToDouble2(Object obj) {
    if (obj == null) {
      return 0d;
    }
    try {
      double doubleVal = Double.parseDouble(obj.toString().trim());
      if (doubleVal == 0.0 || doubleVal == -0.0 || (doubleVal < 1E-9 && doubleVal > -1E-9)) {
        return 0d;
      }
      DecimalFormat df = new DecimalFormat("#.00");
      String temp = df.format(doubleVal);
      double result = Double.parseDouble(temp);
      return result;
    } catch (NumberFormatException e) {
      return 0d;
    }
  }

  /**
   * 格式化double.
   * 
   * @param obj
   * @return
   */
  public static String convertDoubleToString(Object obj) {
    if (obj == null) {
      return "0";
    }
    try {
      double doubleVal = Double.parseDouble(obj.toString().trim());
      if (doubleVal == 0.0 || doubleVal == -0.0 || (doubleVal < 1E-9 && doubleVal > -1E-9)) {
        return "0";
      }
      DecimalFormat df = new DecimalFormat("0.00");
      String result = df.format(doubleVal);
      return result;
    } catch (RuntimeException e) {
      return "0";
    }
  }

  /**
   * 双精度型转字符串.
   * 
   * @param value 双精度值
   * @return
   */
  public static String doubleToString(Double value) {
    if (value == null) {
      return "0";
    }
    try {
      if (value == 0.0 || value == -0.0 || (value < 1E-9 && value > -1E-9)) {
        return "0";
      }
      String tmpValue = String.valueOf(value);
      String subValue = tmpValue.substring(tmpValue.indexOf(".") + 1,
          tmpValue.length());
      if (StringKit.isNotBlank(subValue)) {
        Double tmpDouble = Double.parseDouble("0." + subValue);
        if (0.0 == tmpDouble) {
          return tmpValue.substring(0, tmpValue.indexOf("."));
        }
      }
      return tmpValue;
    } catch (RuntimeException e) {
      return "0";
    }
  }

  /**
   * 将金额转换为万元.
   * 
   * @param obj
   * @return
   */
  public static String convertMoneyToWan(Object obj) {
    if (obj == null) {
      return "0";
    }
    try {
      double dval = Double.parseDouble(obj.toString().trim()) / 10000;
      if (dval == 0.0 || dval == -0.0 || (dval < 1E-9 && dval > -1E-9)) {
        return "0";
      }
      DecimalFormat df = new DecimalFormat("0.00");
      String result = df.format(dval);
      return result;
    } catch (RuntimeException e) {
      return "0";
    }
  }

  /**
   * 对象转布尔类型.
   * @param obj 对象
   * @return boolean 布尔值
   */
  public static boolean convertToBoolean(Object obj) {
    if (obj == null) {
      return false;
    }
    try {
      return Boolean.parseBoolean(obj.toString());
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 对象转整型.
   * 
   * @param obj 对象
   * @return int 整型
   */
  public static int convertToInt(Object obj) {
    if (obj == null) {
      return 0;
    }
    try {
      int result = Integer.parseInt(obj.toString().trim());
      return result;
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * 对象转长整型.
   * @param obj 对象
   * @return long 长整型
   */
  public static long convertToLong(Object obj) {
    if (obj == null) {
      return 0L;
    }
    try {
      long result = Long.parseLong(obj.toString().trim());
      return result;
    } catch (NumberFormatException e) {
      return 0L;
    }
  }

  /**
   * 对象转二进制数组.
   * @param obj 对象
   * @return 二进制数组
   */
  public static byte[] convertToBytes(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      byte[] result = obj.toString().trim().getBytes();
      return result;
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * 将时间转换为日期.
   * 
   * @param obj 对象
   * @return String 字符串
   */
  public static String convertTimeToDate(Object obj) {
    if (obj == null) {
      return "";
    }
    String str = obj.toString().trim();
    if (str.equals("null") || str.equals("NULL")) {
      return "";
    }
    if (str.length() > 10) {
      return str.substring(0, 10);
    } else {
      return str;
    }
  }

  /**
   * 字符串转换为html.
   */
  public static String convertStrToHtml(Object obj) {
    String str = convertToString(obj);
    str = str.replaceAll(" ", "&nbsp;");
    str = str.replaceAll("\"", "&quot;");
    str = str.replaceAll("\'", "&#x27;");
    str = str.replaceAll("<", "&lt;");
    str = str.replaceAll(">", "&gt;");
    str = str.replaceAll("\t",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
    str = str.replaceAll("\r\n", "<br/>");
    str = str.replaceAll("\n", "<br/>");
    return str;
  }

  /**
   * 将字符串转换为sql的in格式.
   * 
   * @param obj 对象
   * @return
   */
  public static String convertStrToSqlIn(Object obj) {
    String str = convertToString(obj);
    if (str.equals("")) {
      return "''";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("'");
    sb.append(str.replaceAll(",", "','"));
    sb.append("'");
    return sb.toString();
  }

  /**
   * 将数组转成字符串.
   * 
   * @param obj 对象
   * @return 字符串
   */
  public static String convertArrayToStr(Object[] obj) {
    if (obj == null || obj.length == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (Object s : obj) {
      if (s == null || s.equals("")) {
        continue;
      }
      if (sb.length() > 0) {
        sb.append(",");
      }
      sb.append("'").append(s).append("'");
    }
    return sb.toString();
  }

  /**
   * 将字符串数组转换为sql的in格式.
   * 
   * @param ss 字符数组
   * @return ('str1','str2','str3'...)
   */
  public static String convertArrayToSqlIn(String[] ss) {
    if (ss == null || ss.length == 0) {
      return "''";
    }
    StringBuilder sb = new StringBuilder();
    for (String s : ss) {
      if (s == null || s.equals("")) {
        continue;
      }
      if (sb.length() > 0) {
        sb.append(",");
      }
      sb.append("'").append(s).append("'");
    }
    return sb.toString();
  }

  /**
   * 将List数组转换为sql的in格式.
   * 
   * @param list 数组
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static String convertListToSqlIn(List list) {
    if (list == null || list.isEmpty()) {
      return "''";
    }
    StringBuilder sb = new StringBuilder();
    for (Object o : list) {
      if (o == null || o.toString().trim().equals("")) {
        continue;
      }
      if (sb.length() > 0) {
        sb.append(",");
      }
      sb.append("'").append(o).append("'");
    }
    return sb.toString();
  }

  /**
   * 从json字符串查找值.
   * 
   * @param json
   * @param name
   * @return value
   */
  public static String queryJson(String json, String name) {
    json = json.replaceAll("[{}]", "");
    int index = json.indexOf(name);
    if (index == -1) {
      return "";
    }
    int eval = json.indexOf(",", index);
    if (eval == -1) {
      eval = json.length();
    }
    return json.substring(json.indexOf(":", index) + 1, eval);
  }

  /**
   * 字符串转换为驼峰写法 如：auto-auth，转换为autoAuth 如：auto_auth，转换为autoAuth.
   */
  public static String toCamelString(String str) {
    if (isNotBlank(str)) {
      String tmp = "";
      int lenth = str.length();
      for (int i = 0; i < lenth; i++) {
        char ch = str.charAt(i);
        if (ch == '-' || ch == '_') {
          if (i != lenth - 1 && i != 0) {
            char next = str.charAt(i + 1);
            tmp = tmp + Character.toUpperCase(next);
            i++;
          }
        } else {
          tmp = tmp + ch;
        }
      }
      return tmp;
    } else {
      return null;
    }
  }

  /**
   * 字符串转换为驼峰写法.
   * 
   * @param camelString 驼峰写法的字符串
   * @param upperCaseFlag 是否转换为大些，默认为小写 如：autoAuth，转换为auto_auth
   */
  public static String camelToTbFieldString(String camelString,
      boolean upperCaseFlag) {
    if (isNotBlank(camelString)) {
      String tmp = "";
      int lenth = camelString.length();
      for (int i = 0; i < lenth; i++) {
        char ch = camelString.charAt(i);
        if (Character.isUpperCase(ch)) {
          tmp = tmp + "_" + ch;
        } else {
          tmp = tmp + ch;
        }
      }
      if (upperCaseFlag) {
        return tmp.toUpperCase();
      } else {
        return tmp.toLowerCase();
      }
    } else {
      return null;
    }
  }

  /**
   * 处理文件名，让文件名中不包含 &,",',=.
   */
  public static String formatFileName(String fileName) {
    fileName = fileName.replace("&", "");
    fileName = fileName.replace("\"", "");
    fileName = fileName.replace("'", "");
    fileName = fileName.replace("=", "");
    fileName = fileName.replace(" ", "");
    fileName = fileName.replace("?", "");
    return fileName;
  }
  
  /**
   *  截取以regex分割的字符串
   *    1、若字符串以 regex 开头，则截取第二段字符串，如：/project,截取后为：project
   *    2、若不存在 regex，则返回所有字符串，如：project，返回：project
   *    2、其他情况，如：project/123456，则返回：project
   * @param src
   * @param regex
   * @return
   */
  public static String getSubString(String src, String regex) {
    if (src != null) {
      src = src.trim();
      if (!src.contains("/")) {
        return src;
      } else if (src.startsWith("/")) {
        src = src.substring(1, src.length());
      }
      if (src.indexOf("/") != -1) {
        return src.substring(0, src.indexOf("/"));
      } else {
        return src;
      }
    } else {
      return src; 
    }
  }
  
  /**
   *  截取以第一个regex分割后剩余的字符串
   *    1、若字符串以 regex 开头，则截取第二段字符串，如：/project,截取后为：null;
   *    2、若不存在 regex，则返回所有字符串，如：project，返回：null;
   *    3、其他情况，如：project/123456，则返回：123456
   *    4、其他情况，如：/project/123456，则返回：123456
   * @param src
   * @param regex
   * @return
   */
  public static String getLastString(String src, String regex) {
    if (src != null) {
      src = src.trim();
      if (!src.contains("/")) {
        return null;
      } else if (src.startsWith("/")) {
        src = src.substring(1, src.length());
      }
      if (src.indexOf("/") != -1) {
        return src.substring(src.indexOf("/") + 1, src.length());
      } else {
        return null;
      }
    } else {
      return src; 
    }
  }
  
  /**
   * 截取最后一个regex后的字符串.
   * 
   * @param src 源字符串
   * @param regex 截取规则
   * @return 截取后的字符串
   */
  public static String getLastSlitString(String src, String regex) {
    if (src != null) {
      src = src.trim();
      if (!src.contains(regex)) {
        return src;
      } else {
        return src.substring(src.lastIndexOf(regex) + 1, src.length());
      }
    } else {
      return src; 
    }
  }
  
  /**
   * name:名称,amt:金额 转换为 Map
   * @param params
   * @return
   */
  public static Map<String, String> toKeyValue(String params) {
    Map<String, String> headerMap = null;
    if (isNotBlank(params)) {
      headerMap = new HashMap<>();
      String headerParam = params;
      String[] headers = headerParam.split(",");
      if (headers != null && headers.length > 0) {
        for (int i = 0; i < headers.length; i++) {
          String headerString = headers[i];
          String[] header = headerString.split(":");
          if (header != null && header.length == 2) {
            headerMap.put(header[0], header[1]);
          }
        }
      }
    }
    return headerMap;
  }

  /**
   * 获取字符串首字母工具类，从这里开始
   * @param sourceStr
   * @return
   */
  private static int BEGIN = 45217;
  private static int END = 63486;
  // 按照声母表示，这个表是在GB2312中的出现的第一个汉字，也就是说“啊”是代表首字母a的第一个汉字。
  // i, u, v都不做声母, 自定规则跟随前面的字母
  private static char[] chartable = {'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈',
          '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌',
          '塌', '挖', '昔', '压', '匝',};
  // 二十六个字母区间对应二十七个端点
  // GB2312码汉字区间十进制表示
  private static int[] table = new int[27];
  // 对应首字母区间表
  private static char[] initialtable = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
          'h', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
          't', 't', 'w', 'x', 'y', 'z',};
  // 初始化
  static {
    for (int i = 0; i < 26; i++) {
      table[i] = gbValue(chartable[i]);// 得到GB2312码的首字母区间端点表，十进制。
    }
    table[26] = END;// 区间表结尾
  }
  /**
   * 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串 最重要的一个方法，思路如下：一个个字符读入、判断、输出
   */
  public static String getFirstLowerLetter(String sourceStr) {
    String result = "";
    String str = sourceStr.toLowerCase();
    int StrLength = str.length();
    int i;
    try {
      for (i = 0; i < StrLength; i++) {
        result += Char2Initial(str.charAt(i));
      }
    } catch (Exception e) {
      result = "";
    }
    result = result.toLowerCase();
    return result;
  }
  public static String getFirstUpperLetter(String sourceStr) {
    String result = "";
    String str = sourceStr.toLowerCase();
    int StrLength = str.length();
    int i;
    try {
      for (i = 0; i < StrLength; i++) {
        result += Char2Initial(str.charAt(i));
      }
    } catch (Exception e) {
      result = "";
    }
    result = result.toUpperCase();
    return result;
  }
  /**
   * 输入字符,得到他的声母,英文字母返回对应的大写字母,其他非简体汉字返回 '0'
   */
  private static char Char2Initial(char ch) {
    // 对英文字母的处理：小写字母转换为大写，大写的直接返回
    if (ch >= 'a' && ch <= 'z') {
      return ch;
    }
    if (ch >= 'A' && ch <= 'Z') {
      return ch;
    }
    // 对非英文字母的处理：转化为首字母，然后判断是否在码表范围内，
    // 若不是，则直接返回。
    // 若是，则在码表内的进行判断。
    int gb = gbValue(ch);// 汉字转换首字母
    if ((gb < BEGIN) || (gb > END))// 在码表区间之前，直接返回
    {
      return ch;
    }
    int i;
    for (i = 0; i < 26; i++) {// 判断匹配码表区间，匹配到就break,判断区间形如“[,)”
      if ((gb >= table[i]) && (gb < table[i + 1])) {
        break;
      }
    }
    if (gb == END) {//补上GB2312区间最右端
      i = 25;
    }
    return initialtable[i]; // 在码表区间中，返回首字母
  }
  /**
   * 取出汉字的编码 cn 汉字
   */
  private static int gbValue(char ch) {// 将一个汉字（GB2312）转换为十进制表示。
    String str = new String();
    str += ch;
    try {
      byte[] bytes = str.getBytes("GB2312");
      if (bytes.length < 2) {
        return 0;
      }
      return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 获取字符串中的参数 ${}
   * @param object 原生查询
   * @return
   */
  public static List<String> getVariableParams(String object) {
    List<String> params = new ArrayList<String>();
    String[] tmpParams = object.split("\\$\\{");
    int j = 0;
    if (!object.startsWith("\\$\\{")) {
      j = 1;
    }
    for (int i=j; i < tmpParams.length; i++) {
      if (tmpParams[i].contains("}")) {
        String param = tmpParams[i].substring(0,tmpParams[i].indexOf("}"));
        params.add(param);
      }
    }
    return params;
  }

  /**
   * 获取字符串中的参数 {{}}
   * @param object 原生查询
   * @return
   */
  public static List<String> getSysParams(String object) {
    List<String> params = new ArrayList<String>();
    String[] tmpParams = object.split("\\{\\{");
    int j = 0;
    if (!object.startsWith("\\{\\{")) {
      j = 1;
    }
    for (int i=j; i < tmpParams.length; i++) {
      if (tmpParams[i].contains("}}")) {
        String param = tmpParams[i].substring(0,tmpParams[i].indexOf("}}"));
        params.add(param);
      }
    }
    return params;
  }

  public static String processSysParams(String object, String param, Object value) {
    if (value != null) {
      String strValue = StringKit.toString(value);
      object = object.replace("{{" + param + "}}", strValue);
    }
    return object;
  }

  public static String processSysParams(String object, Map<String,Object> dataMap) {
    if (StringKit.isNotBlank(object)) {
      List<String> params = getSysParams(object);
      for (String param : params) {
        object = processSysParams(object, param, dataMap.get(param));
      }
    }
    return object;
  }


  /**
   * main.
   */
  public static void main(String[] args) {
    String str = "AUTO_AuTh";
    System.out.println(toCamelString(str));
    String str1 = "autoAuthThis";
    System.out.println(camelToTbFieldString(str1, false));
    System.out.println(camelToTbFieldString(str1, true));
    System.out.println(doubleToString(125.000001));
    System.out.println(getSubString("/project","/"));
    System.out.println(getSubString("project","/"));
    System.out.println(getSubString("project/","/"));
    System.out.println(getSubString("/project/","/"));
    System.out.println(getSubString("/project/123456","/"));
    
    System.out.println(getLastString("/project","/"));
    System.out.println(getLastString("project","/"));
    System.out.println(getLastString("project/","/"));
    System.out.println(getLastString("/project/","/"));
    System.out.println(getLastString("/project/123456/11/111","/"));
    System.out.println(getLastSlitString("/project/123456/11/111", "/"));
    System.out.println(getFirstUpperLetter("中石油安环院"));
    System.out.println(getFirstLowerLetter("中石油安环院"));
    System.out.println(JSONObject.toJSON(getVariableParams("{\"rule\":[{\"rule\":\"{{start_time}}L > ${time}L\",\"remind\":\"有效期开始时间必须大于当前时间\"},{\"rule\":\"{{start_time}}L < {{end_time}}L\",\"remind\":\"有效期结束时间必须大于有效期开始时间\"},{\"rule\":\"{{end_time}}L - {{start_time}}L <= 28800000L  \",\"remind\":\"有效期结束时间与有效期开始时间间隔不能超过8小时\"}],\"type\":\"expression\"}")));
    System.out.println(JSONObject.toJSON(getSysParams("{\"rule\":[{\"rule\":\"{{valid_period_start}}L < {{valid_period_end}}L\",\"remind\":\"有效期结束时间必须大于有效期开始时间\"},{\"rule\":\"{{valid_period_end}}L - {{valid_period_start}})L <= 28800000L  \",\"remind\":\"有效期结束时间与有效期开始时间间隔不能超过8小时\"}],\"type\":\"expression\"}")));
  }
}
