package org.xiaoyu.utils.data;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 编码转换工具类.
 * 
 * @author peilongwu
 * @date 2016-09-07
 */
public class ConvertCode {

  /**
   * 字符串转换为UTF-8编码.（不支持中文）
   * @param str 字符串
   * @return 字符串
   */
  public static String toUtf8String(String str) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < str.length(); i++) {
      char cr = str.charAt(i);
      if (cr >= 0 && cr <= 255) {
        sb.append(cr);
      } else {
        byte[] bytes;
        try {
          bytes = Character.toString(cr).getBytes("utf-8");
        } catch (Exception ex) {
          System.out.println(ex);
          bytes = new byte[0];
        }
        for (int j = 0; j < bytes.length; j++) {
          int index = bytes[j];
          if (index < 0) {
            index += 256;
          }
          sb.append("%" + Integer.toHexString(index).toUpperCase());
        }
      }
    }
    return sb.toString();
  }

	/**
	 * 字符串转为utf-8编码字符串（支持中文）
	 * @param str
	 * @return
	 */
  public static String utf82zh(String string) {
    String patternString = "&#([0-9]{4,5});";
    Pattern pp = Pattern.compile(patternString);
    Matcher mm = pp.matcher(string);
    StringBuffer stringBuffer = new StringBuffer();
    while (mm.find()) {
      mm.appendReplacement(stringBuffer,
          Character.toString((char) Integer.parseInt(mm.group(1), 10)));
    }
    mm.appendTail(stringBuffer);
    string = stringBuffer.toString();
    System.out.println(string);
    return string;
  }

  /**
   * Unicode字符串转换为Utf-8字符串.
   * @param theString 待转换字符串
   * @return 转换后的字符串
   */
  public static String unicodeToUtf8(String theString) {
    char athChar;
    int len = theString.length();
    StringBuffer outBuffer = new StringBuffer(len);
    for (int x = 0; x < len;) {
      athChar = theString.charAt(x++);
      if (athChar == '\\') {
        athChar = theString.charAt(x++);
        if (athChar == 'u') {
          // Read the xxxx
          int value = 0;
          for (int i = 0; i < 4; i++) {
            athChar = theString.charAt(x++);
            switch (athChar) {
              case '0':
              case '1':
              case '2':
              case '3':
              case '4':
              case '5':
              case '6':
              case '7':
              case '8':
              case '9':
                value = (value << 4) + athChar - '0';
                break;
              case 'a':
              case 'b':
              case 'c':
              case 'd':
              case 'e':
              case 'f':
                value = (value << 4) + 10 + athChar - 'a';
                break;
              case 'A':
              case 'B':
              case 'C':
              case 'D':
              case 'E':
              case 'F':
                value = (value << 4) + 10 + athChar - 'A';
                break;
              default:
                throw new IllegalArgumentException(
                    "Malformed   \\uxxxx   encoding.");
            }
          }
          outBuffer.append((char) value);
        } else {
          if (athChar == 't') {
            athChar = '\t';
          } else if (athChar == 'r') {
            athChar = '\r';
          } else if (athChar == 'n') {
            athChar = '\n';
          } else if (athChar == 'f') {
            athChar = '\f';
          } 
          outBuffer.append(athChar);
        }
      } else {
        outBuffer.append(athChar);
      }
    }
    return outBuffer.toString();
  }

  /**
   * UTF-8字符串转换为Unicode编码字符串.
   * @param inStr 待转换字符串
   * @return 转换后字符串
   */
  public static String utf8ToUnicode(String inStr) {
    String uncodeStr = "";
    char[] myBuffer = inStr.toCharArray();

    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < inStr.length(); i++) {
      UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
      System.out.println("ub:" + ub);
      if (ub == UnicodeBlock.BASIC_LATIN) {
        // 英文及数字等
        System.out.println("myBuffer[i]:" + myBuffer[i]);
        sb.append(myBuffer[i]);
      } else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
        // 全角半角字符
        int jl = (int) myBuffer[i] - 65248;
        sb.append((char) jl);
      } else {
        // 汉字
        short sl = (short) myBuffer[i];
        String hexS = Integer.toHexString(sl);
        String unicode = "\\u" + hexS;

        System.out.println("myBuffer:" + unicode.toLowerCase());
        sb.append(unicode.toLowerCase());
      }
    }
    uncodeStr = sb.toString();
    System.out.println("uncodeStr:" + uncodeStr);
    return uncodeStr;
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    System.out.println(utf8ToUnicode("%E6%9C%AB%E8%8A%82%E7%82%B9"));
  }
}
