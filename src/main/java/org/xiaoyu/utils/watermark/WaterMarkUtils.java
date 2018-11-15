package org.xiaoyu.utils.watermark;

import org.xiaoyu.utils.SpringContextHolder;
import org.xiaoyu.utils.data.StringKit;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by 十一
 * 2018/6/4 12:36
 */
public class WaterMarkUtils {

  public static byte[] waterMark(byte[] fileBody, HttpServletRequest request) {
    try {
//      ByteArrayInputStream bais = new ByteArrayInputStream(fileBody);
//      Image img = null;
//      img = ImageIO.read(bais);
//      int imgWidth = img.getWidth(null);
//      int imgHeight = img.getHeight(null);
      //获取格式化水印参数
      String markType = request.getParameter("_mark");
      String waterMark = request.getParameter("_watermark");
      int x = StringKit.convertToInt(request.getParameter("_x"));//横向相对于角的位置
      int y = StringKit.convertToInt(request.getParameter("_y"));//纵向相对于角的位置
      String position = request.getParameter("_p");//位置:ul左上，ll左下，ur右上，lr右下

      //处理文件二进制，增加水印
      if ("image".equals(markType)) {
//        sysFileService = SpringContextHolder.getBean("sysFileService");
//        SysFile markFile = sysFileService.getSysFileAndBody(waterMark);
//        byte[] markFileBody = markFile.getFileBody();
        byte [] markFileBody = null;
        int width = StringKit.convertToInt(request.getParameter("_w"));
        int height = StringKit.convertToInt(request.getParameter("_h"));
        fileBody = markImage(fileBody, markFileBody, width, height, x, y);
      } else if ("word".equals(markType)) {
        String fontStr = request.getParameter("_font");
        String colorStr = request.getParameter("_color");
        Font font = font(fontStr);
        Color color = color(colorStr);
        fileBody = markWord(fileBody, waterMark, font, color, x, y, position);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileBody;
  }

  public static byte[] markWord(byte[] fileBody, String text, Font font, Color color, int x, int y, String position) {
    try {
      Image img = null;
      if (fileBody != null) {
        ByteArrayInputStream bais = new ByteArrayInputStream(fileBody);
        img = ImageIO.read(bais);
      }
      int imgWidth = img.getWidth(null);
      int imgHeight = img.getHeight(null);
      // 加水印
      BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
      mark(bufImg, img, text, font, color, x, y, position);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bufImg, "jpg", baos);
      byte[] newFileBody = baos.toByteArray();
      return newFileBody;
    } catch (Exception e) {
      return fileBody;
    }
  }

  public static byte[] markImage(byte[] fileBody, byte[] fileMarkBody, int width, int height, int x, int y) {
    Image img = null;
    Image mark = null;
    try {
      if (fileBody != null) {
        ByteArrayInputStream fb = new ByteArrayInputStream(fileBody);
        img = ImageIO.read(fb);
      }
      if (fileMarkBody != null) {
        ByteArrayInputStream fmb = new ByteArrayInputStream(fileMarkBody);
        mark = ImageIO.read(fmb);
      }
      int imgWidth = img.getWidth(null);
      int imgHeight = img.getHeight(null);
      BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
      mark(bufImg, img, mark, width, height, x, y);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bufImg, "jpg", baos);
      byte[] newFileBody = baos.toByteArray();
      return newFileBody;
    } catch (IOException e) {
      return fileBody;
    }
  }

  // 加文字水印
  public static void mark(BufferedImage bufImg, Image img, String text, Font font, Color color, int x, int y, String position) {
    Graphics2D g = bufImg.createGraphics();
    g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
    g.setColor(color);
    g.setFont(font);
    String[] strArray = text.split(";");
    if ("".equals(position) || "ul".equals(position)) {
      for (String str : strArray) {
        g.drawString(str, x, y);
        y += font.getSize();
      }
    } else if ("ll".equals(position)) {//位置:ul左上，ll左下，ur右上，lr右下
      y = bufImg.getHeight() - y - (int)(strArray.length * font.getSize() * 0.58);
      for (String str : strArray) {
        g.drawString(str, x, y);
        y += font.getSize();
      }
    } else if ("ur".equals(position)) {
      for (String str : strArray) {
        int lx = bufImg.getWidth() - x - getWatermarkLength(str, g);
        g.drawString(str, lx, y);
        y += font.getSize();
      }
    } else if ("lr".equals(position)) {
      y = bufImg.getHeight() - y - (int)(strArray.length * font.getSize() * 0.58);
      for (String str : strArray) {
        int lx = bufImg.getWidth() - x - getWatermarkLength(str, g);
        g.drawString(str, lx, y);
        y += font.getSize();
      }
    }
    g.dispose();
  }

  // 加图片水印
  public static void mark(BufferedImage bufImg, Image img, Image markImg, int width, int height, int x, int y) {
    Graphics2D g = bufImg.createGraphics();
    g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
    g.drawImage(markImg, x, y, width, height, null);
    g.dispose();
  }

  public static Font font(String fontStr) {
    String[] strArray = fontStr.split(":");
    Font font = new Font(strArray[0], Integer.valueOf(strArray[1]), Integer.valueOf(strArray[2]));
    return font;
  }

  public static Color color(String colorStr) {
    String[] strArray = colorStr.split(":");
    Color color = new Color(Integer.valueOf(strArray[0]), Integer.valueOf(strArray[1]), Integer.valueOf(strArray[2]));
    return color;
  }

  public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
    return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
  }
}
