package org.xiaoyu.utils.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

/**
 * 文件处理工具类.
 * 
 * @author peilongwu
 * @date 2016-09-08
 */
public class FileKit {

  /**
   * 以字节为单位读取文件
   */
  public static void readFileByBytes(String fileName, int byteLen) {
    InputStream in = null;
    try {
      byte[] tempbytes = new byte[byteLen]; // 一次读定长字节
      int byteread = 0;
      in = new FileInputStream(fileName);
      while ((byteread = in.read(tempbytes)) != -1) {
        System.out.write(tempbytes, 0, byteread);
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  /**
   * 以字符为单位读取文件，常用于读文本，数字等类型的文件
   */
  public static void readFileByChars(String fileName) {
    File file = new File(fileName);
    Reader reader = null;
    try {
      System.out.println("以字符为单位读取文件内容，一次读一个字节：");
      // 一次读一个字符
      reader = new InputStreamReader(new FileInputStream(file));
      int tempchar;
      while ((tempchar = reader.read()) != -1) {
        // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
        // 但如果这两个字符分开显示时，会换两次行。
        // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
        if (((char) tempchar) != '\r') {
          System.out.print((char) tempchar);
        }
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      System.out.println("以字符为单位读取文件内容，一次读多个字节：");
      // 一次读多个字符
      char[] tempchars = new char[30];
      int charread = 0;
      reader = new InputStreamReader(new FileInputStream(fileName));
      // 读入多个字符到字符数组中，charread为一次读取字符数
      while ((charread = reader.read(tempchars)) != -1) {
        // 同样屏蔽掉\r不显示
        if ((charread == tempchars.length)
            && (tempchars[tempchars.length - 1] != '\r')) {
          System.out.print(tempchars);
        } else {
          for (int i = 0; i < charread; i++) {
            if (tempchars[i] == '\r') {
              continue;
            } else {
              System.out.print(tempchars[i]);
            }
          }
        }
      }

    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  /**
   * 以行为单位读取文件，常用于读面向行的格式化文件
   */
  public static void readFileByLines(String fileName) {
    File file = new File(fileName);
    BufferedReader reader = null;
    try {
      System.out.println("以行为单位读取文件内容，一次读一整行：");
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      int line = 1;
      // 一次读入一行，直到读入null为文件结束
      while ((tempString = reader.readLine()) != null) {
        // 显示行号
        System.out.println("line " + line + ": " + tempString);
        line++;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  /**
   * 以行为单位读取文件，常用于读面向行的格式化文件
   */
  public static String readFileByLines(File file) {
    BufferedReader reader = null;
    StringBuffer readerContent = new StringBuffer();
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      int line = 1;
      // 一次读入一行，直到读入null为文件结束
      while ((tempString = reader.readLine()) != null) {
        readerContent.append(tempString);
        line++;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
    return readerContent.toString();
  }

  /**
   * 以行为单位读取文件，常用于读面向行的格式化文件
   */
  public static String readFileContent(String fileName) {
    File file = new File(fileName);
    BufferedReader reader = null;
    String fileAllContent = "";
    try {
      System.out.println("以行为单位读取文件内容，一次读一整行：");
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      int line = 1;
      // 一次读入一行，直到读入null为文件结束
      while ((tempString = reader.readLine()) != null) {
        // 显示行号
        System.out.println("line " + line + ": " + tempString);
        line++;
        fileAllContent = fileAllContent + tempString;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
    return fileAllContent;
  }

  /**
   * 随机读取文件内容
   */
  public static void readFileByRandomAccess(String fileName) {
    RandomAccessFile randomFile = null;
    try {
      System.out.println("随机读取一段文件内容：");
      // 打开一个随机访问文件流，按只读方式
      randomFile = new RandomAccessFile(fileName, "r");
      // 文件长度，字节数
      long fileLength = randomFile.length();
      // 读文件的起始位置
      int beginIndex = (fileLength > 4) ? 4 : 0;
      // 将读文件的开始位置移到beginIndex位置。
      randomFile.seek(beginIndex);
      byte[] bytes = new byte[10];
      int byteread = 0;
      // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
      // 将一次读取的字节数赋给byteread
      while ((byteread = randomFile.read(bytes)) != -1) {
        System.out.write(bytes, 0, byteread);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (randomFile != null) {
        try {
          randomFile.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  /**
   * 文件删除
   * 
   * @param file
   */
  /**
   * 文件删除 bl is true:delete directory & file bl is false:detele file only;
   * 
   * @param file
   */
  public static void delete(File file, boolean bl) {

    if (file != null && file.exists()) {

      if (file.isFile()) {

        file.delete();
      } else if (file.isDirectory()) {

        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
          delete(files[i], bl);
        }
      }
      if (bl == true) {

        file.delete();
      } else {
        System.out.println("directory no delete");
      }

    }
  }
  
  /**
   * 获得指定文件的byte数组
   */
  public static byte[] getBytes(File file) {
    byte[] buffer = null;
    try {
      FileInputStream fis = new FileInputStream(file);
      ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
      byte[] bytes = new byte[1000];
      int length;
      while ((length = fis.read(bytes)) != -1) {
        bos.write(bytes, 0, length);
      }
      fis.close();
      bos.close();
      buffer = bos.toByteArray();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return buffer;
  }
  
  public static void writeFile(byte[] fileBody, String fileName, String filePath) throws Exception {
    //获取item中的上传文件的输入流
    InputStream in = new ByteArrayInputStream(fileBody);
    //创建一个文件输出流
    FileOutputStream out = new FileOutputStream(filePath + "\\" + fileName);
    //创建一个缓冲区
    byte[] buffer = new byte[1024];
    //判断输入流中的数据是否已经读完的标识
    int len = 0;
    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
    while ((len = in.read(buffer)) > 0) {
      //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
      out.write(buffer, 0, len);
    }
    //关闭输入流
    in.close();
    //关闭输出流
    out.close();
  }
  
  /**
   * 根据byte数组，生成文件
   */
  public static File bytesToFile(byte[] bytes, String fileName) throws Exception {
    File file = new File(fileName);  
    OutputStream output = new FileOutputStream(file);  
    BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);  
    bufferedOutput.write(bytes);  
    bufferedOutput.close();
    return file;
  }
  
  /**
   * 通过HttpClient从URL获取文件，并保存到制定路径
   * 
   * @param url 文件地址
   * @param dir 文件存储路径
   * @param fileName 文件名
   * @return File 文件
   * @throws Exception 获取文件异常
   */
  public static File getFileByUrl(String url, String dir, String fileName) throws Exception {
    HttpClient client = HttpClients.createDefault();  
    HttpGet httpget = new HttpGet(url);  
    HttpResponse response = client.execute(httpget);  

    HttpEntity entity = response.getEntity();  
    InputStream is = entity.getContent();
    String outputFile = dir + "/" + fileName;
    File file = new File(outputFile);  
    file.getParentFile().mkdirs();  
    FileOutputStream fileout = new FileOutputStream(file);  
    /** 
     * 根据实际运行效果 设置缓冲区大小 
     */  
    byte[] buffer = new byte[10 * 1024];  
    int ch = 0;  
    while ((ch = is.read(buffer)) != -1) {  
      fileout.write(buffer,0,ch);  
    }  
    is.close();  
    fileout.flush();  
    fileout.close();
    return file;
  }
}
