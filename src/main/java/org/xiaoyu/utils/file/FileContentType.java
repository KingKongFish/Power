package org.xiaoyu.utils.file;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件类型工具类.
 * 
 * @author peilongwu
 * @date 2016-09-08
 */
public class FileContentType {

  /**
   * 根据文件名称获取文件内容类型.
   * 
   * @param fileName 文件名称
   * @param type 文件类型
   * @return 文件内容类型
   */
  public static String getContentType(String fileName, String type) {
    String pos = fileName.substring(fileName.lastIndexOf(".") + 1,
        fileName.length());
    pos = pos.toLowerCase();
    String fileType = "";
    if (pos.equals("jpg") || pos.equals("jpeg") || pos.equals("png") || pos.equals("gif")) {
      fileType = "image/" + pos + ";charset=UTF-8";
    } else if (pos.equals("js")) {
      fileType = "application/javascript;charset=UTF-8";
    } else if (pos.equals("css")) {
      fileType = "text/css;charset=UTF-8";
    } else if (pos.equals("mid") || pos.equals("midi")) {
      fileType = "audio/mid";
    } else if (pos.equals("txt")) {
      fileType = "text/plain;charset=UTF-8";
    } else if (pos.equals("smil")) {
      fileType = "application/smil;charset=US-ASCII;";
    } else if (pos.equals("img")) {
      fileType = new StringBuffer().append("image/").append(pos).toString();
    } else if (pos.equals("audio")) {
      fileType = new StringBuffer().append("audio/").append(pos).toString();
    } else if (pos.equals("html")) {
      fileType = "text/html;charset=UTF-8;";
    } else if (pos.equals("doc") ||  pos.equals("docx")) {
      fileType = "application/vnd.oasis.opendocument.text";
    } else if (pos.equals("pdf")) {
      fileType = "application/pdf";
    } else if (pos.equals("pdf")) {
      fileType = "application/pdf";
    } else if (pos.equals("xls") || pos.equals("xlsx")) {
      fileType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    } else if (pos.equals("ppt") || pos.equals("pptx")) {
      fileType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    } else if (pos.equals("mp4")) {
      fileType = "video/mp4";
    } else if (pos.equals("avi")) {
      fileType = "video/x-msvideo";
    } else if (pos.equals("flv")) {
      fileType = "video/x-flv";
    } else {
      fileType = "application/octet-stream";
    }
    return fileType;
  }

  /**
   * 根据文件名称获取文件内容类型.
   * 
   * @param fileName 文件名称
   * @return 文件内容类型
   */
  public static String getContentType(String fileName) {
    return getContentType(fileName, null);
  }

  /**
   * 获取文件是否可预览状态.
   *   available：可预览
   *   unavailable：不可预览
   *   converting：转换后预览
   *   
   * @param fileName 文件名
   * @param type 文件类型
   * @return 字符串标识的状态
   */
  public static String getPreviewState(String fileName, String type) {
    String pos = fileName.substring(fileName.lastIndexOf(".") + 1,
        fileName.length());
    pos = pos.toLowerCase();
    String fileType = "";
    if (pos.equals("jpg") || pos.equals("jpeg") || pos.equals("png") || pos.equals("gif")) {
      fileType = "available";
    } else if (pos.equals("mid") || pos.equals("midi")) {
      fileType = "unavailable";
    } else if (pos.equals("txt")) {
      fileType = "available";
    } else if (pos.equals("smil")) {
      fileType = "unavailable";
    } else if (type.equals("img")) {
      fileType = new StringBuffer().append("image/").append(pos).toString();
    } else if (type.equals("audio")) {
      fileType = new StringBuffer().append("audio/").append(pos).toString();
    } else if (pos.equals("html")) {
      fileType = "available";
    } else if (pos.equals("doc") || pos.equals("docx")) {
      fileType = "converting";
    } else if (pos.equals("pdf")) {
      fileType = "converting";
    } else if (pos.equals("xls") || pos.equals("xlsx")) {
      fileType = "converting";
    } else if (pos.equals("ppt") || pos.equals("pptx")) {
      fileType = "converting";
    } else {
      fileType = "unavailable";
    }
    return fileType;
  }

  /**
   *  判断是否是图片.
   *  
   * @param pos 文件后缀名
   * @return 是否为图片标识
   */
  public static Boolean isImage(String pos) {

    if (pos.equals("jpg") || pos.equals("jpeg") || pos.equals("png")
        || pos.equals("gif") || pos.equals("JPG") || pos.equals("JPEG")
        || pos.equals("PNG") || pos.equals("GIF")) {
      return true;
    }
    return false;
  }

  /**
   *  判断是否是视频.
   *  
   * @param pos 文件后缀名
   * @return 是否为视频标识
   */
  public static Boolean isVideo(String pos) {
    pos = pos.toLowerCase();
    if (pos.equals("mp4") || pos.equals("avi")) {
      return true;
    }
    return false;
  }

  /**
   * 根据文件名获取文件后缀.
   * 
   * @param fileName 文件名
   * @return 文件名后缀
   */
  public static String getFileType(String fileName) {
    String pos = "";
    if (StringUtils.isNotBlank(fileName)) {
      if (fileName.contains(".")
          && fileName.length() > fileName.lastIndexOf(".")) {
        pos = fileName.substring(fileName.lastIndexOf(".") + 1,
            fileName.length());
        pos = pos.toLowerCase();
      }
    }
    return pos;

  }

  /**
   * 判断传入的文件是否为传入的类型.
   * 
   * @param fileName 文件名称
   * @param type 类型
   * @return boolean 文件类型比对后结果
   */
  public static boolean isFileType(String fileName, String type) {
    String fileType = getFileType(fileName);
    if (StringUtils.isNotBlank(type) && type.equals(fileType)) {
      return true;
    }
    return false;
  }
  
  /**
   * 根据文件名称获取文件类型.
   * 
   * @param fileName 文件名称
   * @return 文件类型
   */
  public static String getFileNodeType(String fileName) {
    String pos = fileName.substring(fileName.lastIndexOf(".") + 1,
        fileName.length());
    pos = pos.toLowerCase();
    String fileType = "";
    if (pos.equals("png") || pos.equals("jpg") || pos.equals("jpeg")
        || pos.equals("gif") || pos.equals("bmp") || pos.equals("webp")
        || pos.equals("psd") || pos.equals("ai")) {
      fileType = "image";
    } else if (pos.equals("js") || pos.equals("txt") || pos.equals("php")
        || pos.equals("css") || pos.equals("sql")) {
      fileType = "text";
    } else if (pos.equals("mid") || pos.equals("midi") || pos.equals("mp3")
        || pos.equals("mp4") || pos.equals("mov") || pos.equals("avi")) {
      fileType = "media";
    } else if (pos.equals("doc") || pos.equals("docx") || pos.equals("xls")
        || pos.equals("xlsx") || pos.equals("ppt") || pos.equals("pptx")
        || pos.equals("pdf")) {
      fileType = "office";
    } else if (pos.equals("zip") || pos.equals("rar") || pos.equals("tar")
        || pos.equals("war") || pos.equals("apk") || pos.equals("gz")
        || pos.equals("7z")) {
      fileType = "archive";
    } else {
      fileType = "other";
    }
    return fileType;
  }
  
  public static void main(String[] args) {
    System.out.println(isFileType("test.xls", "xlsx"));
  }
}
