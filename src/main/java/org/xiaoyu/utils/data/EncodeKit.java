package org.xiaoyu.utils.data;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 各种格式的编码加码工具类.
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * 
 * @author calvin
 */
public class EncodeKit {

  private static final String DEFAULT_URL_ENCODING = "UTF-8";

  /**
   * Hex编码.
   */
  public static String hexEncode(byte[] input) {
    return Hex.encodeHexString(input);
  }

  /**
   * Hex解码.
   */
  public static byte[] hexDecode(String input) {
    try {
      return Hex.decodeHex(input.toCharArray());
    } catch (DecoderException e) {
      throw new IllegalStateException("Hex Decoder exception", e);
    }
  }

  /**
   * Base64编码.
   */
  public static String base64Encode(byte[] input) {
    return Base64.encodeBase64String(input);
  }

  /**
   * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
   */
  public static String base64UrlSafeEncode(byte[] input) {
    return Base64.encodeBase64URLSafeString(input);
  }

  /**
   * Base64解码.
   */
  public static byte[] base64Decode(String input) {
    return Base64.decodeBase64(input);
  }

  /**
   * URL 编码, Encode默认为UTF-8.
   */
  public static String urlEncode(String input) {
    return urlEncode(input, DEFAULT_URL_ENCODING);
  }

  /**
   * URL 编码.
   */
  public static String urlEncode(String input, String encoding) {
    try {
      return URLEncoder.encode(input, encoding);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Unsupported Encoding Exception", e);
    }
  }

  /**
   * URL 解码, Encode默认为UTF-8.
   */
  public static String urlDecode(String input) {
    return urlDecode(input, DEFAULT_URL_ENCODING);
  }

  /**
   * URL 解码.
   */
  public static String urlDecode(String input, String encoding) {
    try {
      return URLDecoder.decode(input, encoding);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Unsupported Encoding Exception", e);
    }
  }

  /**
   * Html 转码.
   */
  public static String htmlEscape(String html) {
    return StringEscapeUtils.escapeHtml4(html);
  }

  /**
   * Html 解码.
   */
  public static String htmlUnescape(String htmlEscaped) {
    return StringEscapeUtils.unescapeHtml4(htmlEscaped);
  }

  /**
   * Xml 转码.
   */
  public static String xmlEscape(String xml) {
    return StringEscapeUtils.escapeXml(xml);
  }

  /**
   * Xml 解码.
   */
  public static String xtmlUnescape(String xmlEscaped) {
    return StringEscapeUtils.unescapeXml(xmlEscaped);
  }
  
  public static String toUtf8String(String str) throws Exception {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      if (ch >= 0 && ch <= 255) {
        sb.append(ch);
      } else {
        byte[] bt;
        try {
          bt = Character.toString(ch).getBytes("utf-8");
        } catch (Exception ex) {
          bt = new byte[0];
          throw new Exception("toUtf8String is erro:" + ex);
        }
        for (int j = 0; j < bt.length; j++) {
          int length = bt[j];
          if (length < 0) {
            length += 256;
          }
          sb.append("%" + Integer.toHexString(length).toUpperCase());
        }
      }
    }
    return sb.toString();
  }

}
