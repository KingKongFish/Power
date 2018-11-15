package org.xiaoyu.utils.excel.preview.style;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class ColorPreview {

  private short[] rgb;
  private String rgbString;

  public ColorPreview(HSSFColor color) {
    this.rgb = color.getTriplet();
    this.rgbString = color.getHexString();
  }

  public ColorPreview(short index) {
    HSSFColor color = HSSFColor.getIndexHash().get(index);
    this.rgb = color.getTriplet();
    this.rgbString = color.getHexString();
  }

  public ColorPreview(XSSFColor color) {
    this.rgbString = color.getARGBHex();
  }

  public ColorPreview() {
  }

  public ColorPreview(short[] rgb, String rgbString) {
    this.rgb = rgb;
    this.rgbString = rgbString;
  }

  public short[] getRgb() {
    return rgb;
  }

  public void setRgb(short[] rgb) {
    this.rgb = rgb;
  }

  public String getRgbString() {
    return rgbString;
  }

  public void setRgbString(String rgbString) {
    this.rgbString = rgbString;
  }

}
