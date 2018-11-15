package org.xiaoyu.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;

/**
 * 上传文件到制定的服务端.
 * 
 * @author peilongwu
 * @date 2016-09-10
 */
public class UploadFileToServer {
  
  private void createPath(String path) {
    File dir = new File(path);
    if (!dir.exists()) {
      dir.mkdirs();
    }
  }

  /**
   * 删除文件.
   * 
   * @param path
   */
  public void deleteFile(String path) {
    File file = new File(path);
    if (file.exists()) {
      file.delete();
    }
  }

  /**
   * 根据传入的文件长度计算文件大小，xxMxxKxxB .
   * 
   * @param len
   * @return 文件大小
   */
  public String getFileLength(long len) {
    String result = "";
    if (len >= 1024 * 1024) {
      BigDecimal fileSize = new BigDecimal(len / (1024 * 1024.0)).setScale(2,
          BigDecimal.ROUND_HALF_UP);
      result = fileSize.toString() + "M";
    } else if (len >= 1024) {
      BigDecimal fileSize = new BigDecimal(len / 1024.0).setScale(2,
          BigDecimal.ROUND_HALF_UP);
      result = fileSize.toString() + "K";
    } else {
      result = Long.toString(len) + "B";
    }
    return result;
  }

  /**
   * 上传文件到指定的路径,返回是否成功上传标识.
   * 
   * @param uploadFile 待上传文件
   * @param savePath 保存的路径
   * @return 是否上传成功标识
   */
  public boolean uploadToDisk(File uploadFile, String savePath) {
    String saveDir = savePath.substring(0, savePath.lastIndexOf("/"));
    createPath(saveDir);
    try {
      BufferedInputStream in = null;
      BufferedOutputStream out = null;
      try {
        out = new BufferedOutputStream(new FileOutputStream(savePath));
        in = new BufferedInputStream(new FileInputStream(uploadFile));
        int readFlag = 0;
        while ((readFlag = in.read()) != -1) {
          out.write(readFlag);
        }
      } finally {
        in.close();
        out.close();
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 上传文件，返回文件的字节数组.
   * 
   * @param uploadFile 待上传文件
   * @return 字节数组
   */
  public static byte[] uploadToDb(File uploadFile) {
    try {
      byte[] data = null;
      FileInputStream in = new FileInputStream(uploadFile);
      FileChannel fc = in.getChannel();
      data = new byte[(int) fc.size()];
      in.read(data);
      uploadFile.delete();
      in.close();
      return data;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
