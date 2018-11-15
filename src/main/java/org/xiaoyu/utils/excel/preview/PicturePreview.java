package org.xiaoyu.utils.excel.preview;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPicture;

public class PicturePreview {

  private int rowStart; // 原始图片的起始行
  private int colStart; // 原始图片的起始列
  private int rowEnd;   // 原始图片尾行
  private int colEnd;   // 原始图片的尾列
  private int xstart;   // 编辑后图片起始点x
  private int ystart;   // 编辑后图片起始点y
  private int xend;     // 编辑后图片尾点x
  private int yend;     // 编辑后图片尾点y
  private String data;// 图片的base64数据
  private String type;// 图片格式

  public PicturePreview(XSSFPicture picture) {
    this.rowStart = picture.getPreferredSize().getRow1();
    this.colStart = picture.getPreferredSize().getCol1();
    this.rowEnd = picture.getPreferredSize().getRow2();
    this.colEnd = picture.getPreferredSize().getCol2();
    this.xstart = picture.getPreferredSize().getDx1();
    this.ystart = picture.getPreferredSize().getDy1();
    this.xend = picture.getPreferredSize().getDx2();
    this.yend = picture.getPreferredSize().getDy2();
    this.data = Base64.encodeBase64String(picture.getPictureData().getData());
    System.out.println(data);
    this.type = picture.getPictureData().getMimeType();
  }

  public PicturePreview(HSSFPicture picture) {
    this.rowStart = picture.getPreferredSize().getRow1();
    this.colStart = picture.getPreferredSize().getCol1();
    this.rowEnd = picture.getPreferredSize().getRow2();
    this.colEnd = picture.getPreferredSize().getCol2();
    this.xstart = picture.getPreferredSize().getDx1();
    this.ystart = picture.getPreferredSize().getDy1();
    this.xend = picture.getPreferredSize().getDx2();
    this.yend = picture.getPreferredSize().getDy2();
    this.data = Base64.encodeBase64String(picture.getPictureData().getData());
    this.type = picture.getPictureData().getMimeType();
  }

  public PicturePreview() {
  }

  public PicturePreview(int rowStart, int colStart, int rowEnd, int colEnd,
      String data) {
    this.rowStart = rowStart;
    this.colStart = colStart;
    this.rowEnd = rowEnd;
    this.colEnd = colEnd;
    this.data = data;
  }

  public int getRowStart() {
    return rowStart;
  }

  public void setRowStart(int rowStart) {
    this.rowStart = rowStart;
  }

  public int getColStart() {
    return colStart;
  }

  public void setColStart(int colStart) {
    this.colStart = colStart;
  }

  public int getRowEnd() {
    return rowEnd;
  }

  public void setRowEnd(int rowEnd) {
    this.rowEnd = rowEnd;
  }

  public int getColEnd() {
    return colEnd;
  }

  public void setColEnd(int colEnd) {
    this.colEnd = colEnd;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int getXstart() {
    return xstart;
  }

  public void setXstart(int xstart) {
    this.xstart = xstart;
  }

  public int getYstart() {
    return ystart;
  }

  public void setyStart(int ystart) {
    this.ystart = ystart;
  }

  public int getXend() {
    return xend;
  }

  public void setXend(int xend) {
    this.xend = xend;
  }

  public int getYend() {
    return yend;
  }

  public void setYend(int yend) {
    this.yend = yend;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
