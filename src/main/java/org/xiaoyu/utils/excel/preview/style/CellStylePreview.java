package org.xiaoyu.utils.excel.preview.style;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import org.xiaoyu.utils.excel.preview.CellPreview;
import org.xiaoyu.utils.excel.preview.CellType;

/**
 * Excel单元格格式，其中需要把Font,Color分解 Color使用三原色代替：R、G、B Font用实体表示：font、size等
 * @author 裴龙武
 *
 */
public class CellStylePreview {

  private int hight;           // 高度
  private float hightOfPoint;  // 高度
  private int width;           // 宽度
  private short align;         // 水平居中方式
  private short verticalAlign; // 垂直居中方式
  private short top;           // 上边框
  private short bottom;        // 下边框
  private short left;          // 左边框
  private short right;         // 有边框
  private String topColor;     // 上边框颜色
  private String bottomColor;  // 下边框颜色
  private String leftColor;    // 左边框颜色
  private String rightColor;   // 右边框颜色
  private short fill;          // 填充方式
  private String foreColor;    // 前景颜色
  private String backColor;    // 后景颜色
  private short data;          // 数据格式化
  private String formatString; // 格式化字符串
  private boolean hidden;      // 隐藏
  private short indention;     // 缩进
  private FontPreview font;    // 字体
  private short rotation;      // 旋转
  private boolean locked;      // 锁定
  private boolean wrap;        // 包裹

  public CellStylePreview(HSSFCell cell) {
    HSSFCellStyle cellStyle = cell.getCellStyle();
    this.align = cellStyle.getAlignment();// 水平对齐方式
    this.verticalAlign = cellStyle.getVerticalAlignment();// 垂直对齐方式
    this.top = cellStyle.getBorderTop();// 上边框
    this.bottom = cellStyle.getBorderBottom();// 下边框
    this.left = cellStyle.getBorderLeft();// 左边框
    this.right = cellStyle.getBorderRight();// 右边框
    if (HSSFColor.getIndexHash().get(cellStyle.getTopBorderColor()) != null) {
      this.topColor = HSSFColor.getIndexHash()
          .get(cellStyle.getTopBorderColor()).getHexString();// 上边框颜色
    }
    if (HSSFColor.getIndexHash().get(cellStyle.getBottomBorderColor()) != null) {
      this.bottomColor = HSSFColor.getIndexHash()
          .get(cellStyle.getBottomBorderColor()).getHexString();// 下边框颜色
    }
    if (HSSFColor.getIndexHash().get(cellStyle.getRightBorderColor()) != null) {
      this.rightColor = HSSFColor.getIndexHash()
          .get(cellStyle.getRightBorderColor()).getHexString();// 左边框颜色
    }
    if (HSSFColor.getIndexHash().get(cellStyle.getLeftBorderColor()) != null) {
      this.leftColor = HSSFColor.getIndexHash()
          .get(cellStyle.getLeftBorderColor()).getHexString();// 右边框颜色
    }
    this.fill = cellStyle.getFillPattern();// 填充方式
    if (HSSFCellStyle.NO_FILL != cellStyle.getFillPattern()) {
      this.foreColor = cellStyle.getFillForegroundColorColor().getHexString();// 前景填充颜色
      this.backColor = cellStyle.getFillBackgroundColorColor().getHexString();// 背景填充颜色
    }
    this.data = cellStyle.getDataFormat();// 数据格式
    this.formatString = cellStyle.getDataFormatString();// 格式化后数据
    this.hidden = cellStyle.getHidden();// 隐藏
    this.indention = cellStyle.getIndention();// 缩进
    this.font = new FontPreview(cell);// 字体
    this.locked = cellStyle.getLocked();// 锁定
    this.rotation = cellStyle.getRotation(); // 旋转
    this.wrap = cellStyle.getWrapText(); // 包裹
    this.hight = cell.getRow().getHeight();
    this.hightOfPoint = cell.getRow().getHeightInPoints();
    this.width = cell.getRow().getSheet().getColumnWidth(cell.getColumnIndex());
  }

  public CellStylePreview(XSSFCell cell) {
    XSSFCellStyle cellStyle = cell.getCellStyle();
    this.align = cellStyle.getAlignment();// 水平对齐方式
    this.verticalAlign = cellStyle.getVerticalAlignment();// 垂直对齐方式
    this.top = cellStyle.getBorderTop();// 上边框
    this.bottom = cellStyle.getBorderBottom();// 下边框
    this.left = cellStyle.getBorderLeft();// 左边框
    this.right = cellStyle.getBorderRight();// 右边框
    if (cellStyle.getTopBorderXSSFColor() != null) {
      this.topColor = cellStyle.getTopBorderXSSFColor().getARGBHex();// 上边框颜色
    } else {
      this.topColor = "";
    }
    if (cellStyle.getBottomBorderXSSFColor() != null) {
      this.bottomColor = cellStyle.getBottomBorderXSSFColor().getARGBHex();// 下边框颜色
    } else {
      this.bottomColor = "";
    }
    if (cellStyle.getRightBorderXSSFColor() != null) {
      this.rightColor = cellStyle.getRightBorderXSSFColor().getARGBHex();// 左边框颜色
    } else {
      this.rightColor = "";
    }
    if (cellStyle.getLeftBorderXSSFColor() != null) {
      this.leftColor = cellStyle.getLeftBorderXSSFColor().getARGBHex();// 右边框颜色
    } else {
      this.leftColor = "";
    }
    this.fill = cellStyle.getFillPattern();// 填充方式
    if (HSSFCellStyle.NO_FILL != cellStyle.getFillPattern()) {
      this.foreColor = cellStyle.getFillForegroundXSSFColor().getARGBHex();// 前景填充颜色
      this.backColor = cellStyle.getFillBackgroundXSSFColor().getARGBHex();// 背景填充颜色
    }
    this.data = cellStyle.getDataFormat();// 数据格式
    this.formatString = cellStyle.getDataFormatString();// 格式化后数据
    this.hidden = cellStyle.getHidden();// 隐藏
    this.indention = cellStyle.getIndention();// 缩进
    this.font = new FontPreview(cell);// 字体
    this.locked = cellStyle.getLocked();// 锁定
    this.rotation = cellStyle.getRotation(); // 旋转
    this.wrap = cellStyle.getWrapText(); // 包裹
    this.hight = cell.getRow().getHeight();
    this.hightOfPoint = cell.getRow().getHeightInPoints();
    this.width = cell.getRow().getSheet().getColumnWidth(cell.getColumnIndex());
  }

  public CellStylePreview() {
  }

  public CellStylePreview(int hight, float hightOfPoint, int width,
      short align, short verticalAlign, short top, short bottom, short left,
      short right, String topColor, String bottomColor, String leftColor,
      String rightColor, short fill, String foreColor, String backColor,
      short data, String formatString, boolean hidden, short indention,
      FontPreview font, short rotation, boolean locked, boolean wrap) {
    this.hight = hight;
    this.hightOfPoint = hightOfPoint;
    this.width = width;
    this.align = align;
    this.verticalAlign = verticalAlign;
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
    this.topColor = topColor;
    this.bottomColor = bottomColor;
    this.leftColor = leftColor;
    this.rightColor = rightColor;
    this.fill = fill;
    this.foreColor = foreColor;
    this.backColor = backColor;
    this.data = data;
    this.formatString = formatString;
    this.hidden = hidden;
    this.indention = indention;
    this.font = font;
    this.rotation = rotation;
    this.locked = locked;
    this.wrap = wrap;
  }

  public static CellStyle createCellStyle(Workbook wbs, CellPreview cell) {
    CellStylePreview style = cell.getStyle();
    CellStyle cellStyle = wbs.createCellStyle();
    cellStyle.setAlignment(style.getAlign());
    cellStyle.setBorderBottom(style.getBottom());
    cellStyle.setBorderLeft(style.getLeft());
    cellStyle.setBorderRight(style.getRight());
    cellStyle.setBorderTop(style.getRight());
    cellStyle.setBottomBorderColor(style.getRight());
    cellStyle.setDataFormat(style.getRight());
    cellStyle.setFillBackgroundColor(style.getRight());
    cellStyle.setFillForegroundColor(style.getRight());
    cellStyle.setFillPattern(style.getRight());
    cellStyle.setHidden(style.getHidden());
    cellStyle.setIndention(style.getIndention());
    cellStyle.setLocked(style.getLocked());
    cellStyle.setRotation(style.getRotation());
    cellStyle.setVerticalAlignment(style.getVerticalAlign());
    cellStyle.setWrapText(style.getWrap());
    cellStyle.setFont(FontPreview.setFont(wbs, style.getFont()));
    if (CellType.Number == cell.getCellType()) {
      DataFormat format = wbs.createDataFormat();
      cellStyle.setDataFormat(format.getFormat("0.00"));
    }
    if (CellType.Integer == cell.getCellType()) {
      DataFormat format = wbs.createDataFormat();
      cellStyle.setDataFormat(format.getFormat("0"));
    }
    return cellStyle;
  }

  public String getFormatString() {
    return formatString;
  }

  public void setFormatString(String formatString) {
    this.formatString = formatString;
  }

  public short getRotation() {
    return rotation;
  }

  public void setRotation(short rotation) {
    this.rotation = rotation;
  }

  public boolean getLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public boolean getWrap() {
    return wrap;
  }

  public void setWrap(boolean wrap) {
    this.wrap = wrap;
  }

  public void setTopColor(String topColor) {
    this.topColor = topColor;
  }

  public void setBottomColor(String bottomColor) {
    this.bottomColor = bottomColor;
  }

  public void setLeftColor(String leftColor) {
    this.leftColor = leftColor;
  }

  public void setRightColor(String rightColor) {
    this.rightColor = rightColor;
  }

  public void setForeColor(String foreColor) {
    this.foreColor = foreColor;
  }

  public void setBackColor(String backColor) {
    this.backColor = backColor;
  }

  public short getAlign() {
    return align;
  }

  public void setAlign(short align) {
    this.align = align;
  }

  public short getVerticalAlign() {
    return verticalAlign;
  }

  public void setVerticalAlign(short verticalAlign) {
    this.verticalAlign = verticalAlign;
  }

  public short getTop() {
    return top;
  }

  public void setTop(short top) {
    this.top = top;
  }

  public short getBottom() {
    return bottom;
  }

  public void setBottom(short bottom) {
    this.bottom = bottom;
  }

  public short getLeft() {
    return left;
  }

  public void setLeft(short left) {
    this.left = left;
  }

  public short getRight() {
    return right;
  }

  public void setRight(short right) {
    this.right = right;
  }

  public boolean getHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public short getIndention() {
    return indention;
  }

  public void setIndention(short indention) {
    this.indention = indention;
  }

  public FontPreview getFont() {
    return font;
  }

  public void setFont(FontPreview font) {
    this.font = font;
  }

  public short getFill() {
    return fill;
  }

  public void setFill(short fill) {
    this.fill = fill;
  }

  public short getData() {
    return data;
  }

  public void setData(short data) {
    this.data = data;
  }

  public String getTopColor() {
    return topColor;
  }

  public String getBottomColor() {
    return bottomColor;
  }

  public String getLeftColor() {
    return leftColor;
  }

  public String getRightColor() {
    return rightColor;
  }

  public String getForeColor() {
    return foreColor;
  }

  public String getBackColor() {
    return backColor;
  }

  public float getHightOfPoint() {
    return hightOfPoint;
  }

  public void setHightOfPoint(float hightOfPoint) {
    this.hightOfPoint = hightOfPoint;
  }

  public int getHight() {
    return hight;
  }

  public void setHight(int hight) {
    this.hight = hight;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

}
