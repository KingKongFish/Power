package org.xiaoyu.utils.excel.preview.style;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class FontPreview {

  private String fontName;    // 字体
  private String boldweight;  // 粗体
  private short hight;        // 高度
  private short hightOfPoint; // 高度
  private String color;       // 颜色
  private boolean italic;     // 斜体
  private boolean strikeout;  // 删除线
  private byte underline;     // 下划线

  public static Font setFont(Workbook wbs, FontPreview fontPreview) {
    Font font = wbs.createFont();
    font.setBoldweight("bold".equals(fontPreview.getBoldweight()) ? Font.BOLDWEIGHT_BOLD
        : Font.BOLDWEIGHT_NORMAL);
    font.setFontName(fontPreview.getFontName());
    font.setFontHeightInPoints(fontPreview.getHightOfPoint());
    font.setStrikeout(fontPreview.isStrikeout());
    font.setItalic(fontPreview.isItalic());
    font.setUnderline(fontPreview.isUnderline());
    return font;
  }

  public FontPreview(HSSFCell cell) {
    HSSFFont font = cell.getCellStyle().getFont(cell.getSheet().getWorkbook());
    this.fontName = font.getFontName();
    if (Font.BOLDWEIGHT_BOLD == font.getBoldweight()) {
      this.boldweight = "bold";
    } else {
      this.boldweight = "normal";
    }
    if (font.getHSSFColor(cell.getSheet().getWorkbook()) != null) {
      this.color = font.getHSSFColor(cell.getSheet().getWorkbook())
          .getHexString();// 颜色
    }
    this.hight = font.getFontHeight(); // 高度
    this.hightOfPoint = font.getFontHeightInPoints();// 高度
    this.italic = font.getItalic(); // 斜体
    this.strikeout = font.getStrikeout(); // 删除线
    this.underline = font.getUnderline(); // 下划线
  }

  public FontPreview(XSSFCell cell) {
    XSSFFont font = cell.getCellStyle().getFont();
    this.fontName = font.getFontName();
    if (Font.BOLDWEIGHT_BOLD == font.getBoldweight()) {
      this.boldweight = "bold";
    } else {
      this.boldweight = "normal";
    }
    if (font.getXSSFColor() != null) {
      this.color = font.getXSSFColor().getARGBHex(); // 颜色
    } else {
      this.color = "";
    }
    this.hight = font.getFontHeight(); // 高度
    this.hightOfPoint = font.getFontHeightInPoints();// 高度
    this.italic = font.getItalic(); // 斜体
    this.strikeout = font.getStrikeout(); // 删除线
    this.underline = font.getUnderline(); // 下划线
  }

  public FontPreview() {
  }

  public FontPreview(String fontName, String boldweight, short hight,
      short hightOfPoint, String color, boolean italic, boolean strikeout,
      byte underline) {
    this.fontName = fontName;
    this.boldweight = boldweight;
    this.hight = hight;
    this.hightOfPoint = hightOfPoint;
    this.color = color;
    this.italic = italic;
    this.strikeout = strikeout;
    this.underline = underline;
  }

  public String getFontName() {
    return fontName;
  }

  public void setFontName(String fontName) {
    this.fontName = fontName;
  }

  public String getBoldweight() {
    return boldweight;
  }

  public void setBoldweight(String boldweight) {
    this.boldweight = boldweight;
  }

  public short getHight() {
    return hight;
  }

  public void setHight(short hight) {
    this.hight = hight;
  }

  public short getHightOfPoint() {
    return hightOfPoint;
  }

  public void setHightOfPoint(short hightOfPoint) {
    this.hightOfPoint = hightOfPoint;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public boolean isItalic() {
    return italic;
  }

  public void setItalic(boolean italic) {
    this.italic = italic;
  }

  public boolean isStrikeout() {
    return strikeout;
  }

  public void setStrikeout(boolean strikeout) {
    this.strikeout = strikeout;
  }

  public byte isUnderline() {
    return underline;
  }

  public void setUnderline(byte underline) {
    this.underline = underline;
  }
}
