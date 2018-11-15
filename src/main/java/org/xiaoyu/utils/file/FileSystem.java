package org.xiaoyu.utils.file;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.util.List;

/**
 * Mac 系统文件判断.
 * 
 * @author peilongwu
 * @date 2016-09-08
 */
public class FileSystem {
  
  @SuppressWarnings("unchecked")
  public static boolean isMac(File file) throws ZipException {
    boolean bl = false;
    ZipFile zipFile = new ZipFile(file);
    List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
    for (int i = 0; i < fileHeaderList.size(); i++) {
      FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
      if (fileHeader != null) {
        if (fileHeader.getFileName().contains("__MACOSX")
            || fileHeader.getFileName().contains(".DS_Store")) {
          bl = true;
          break;
        }
      } else {
        throw new RuntimeException("FileHeader is not null");
      }
    }
    return bl;
  }
}
