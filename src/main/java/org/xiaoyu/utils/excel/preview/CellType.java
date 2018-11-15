package org.xiaoyu.utils.excel.preview;

public enum CellType {
  Boolean, Integer, Number, String, Date, Time, Error;
  
  public static CellType getCellType(String cellType) {
    if ("Boolean".equals(cellType)) {
      return Boolean;
    } else if ("Integer".equals(cellType)) {
      return Integer;
    } else if ("Number".equals(cellType)) {
      return Number;
    } else if ("String".equals(cellType)) {
      return String;
    } else if ("Date".equals(cellType)) {
      return Date;
    } else if ("Time".equals(cellType)) {
      return Time;
    } else {
      return Error;
    }
  }
  
  public static CellType toCellType(String type) {
    if ("Boolean".equals(type)) {
      return Boolean;
    } else if ("Integer".equals(type)) {
      return Integer;
    } else if ("Number".equals(type)) {
      return Number;
    } else if ("String".equals(type) || "Text".equals(type) || "Key".equals(type) 
        || "Object".equals(type) || "Array".equals(type)) {
      return String;
    } else if ("Time".equals(type) || "DateTime".equals(type)) {
      return Time;
    } else {
      return Error;
    }
  }
}
