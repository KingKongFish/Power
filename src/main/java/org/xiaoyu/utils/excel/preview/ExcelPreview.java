package org.xiaoyu.utils.excel.preview;

import java.util.List;

/**
 * 简单Excel对象，只包括名称和sheet数据
 * 
 * @author Peilw
 * 
 */

public class ExcelPreview {

  private String name;
  private List<SheetPreview> sheets;

  public ExcelPreview() {
  }

  public ExcelPreview(String name, List<SheetPreview> sheets) {
    this.name = name;
    this.sheets = sheets;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<SheetPreview> getSheets() {
    return sheets;
  }

  public void setSheets(List<SheetPreview> sheets) {
    this.sheets = sheets;
  }
}
