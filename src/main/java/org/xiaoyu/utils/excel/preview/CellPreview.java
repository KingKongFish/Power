package org.xiaoyu.utils.excel.preview;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;

import org.xiaoyu.utils.data.StringKit;
import org.xiaoyu.utils.date.DateUtils;
import org.xiaoyu.utils.excel.preview.style.CellStylePreview;

public class CellPreview {

  private Object value;    // 单元格值
  private CellType cellType; // 单元格数据类型
  private int row;         // 行索引
  private int col;         // 列索引
  private CellStylePreview style;// style
  private String formula;  // 公式
  private String comment;  // 注释
  private int[][] merged;

  public CellPreview(Object value, CellType cellType, int row, int col,
      CellStylePreview style, String formula, String comment) {
    this.value = value;
    this.cellType = cellType;
    this.row = row;
    this.col = col;
    this.style = style;
    this.formula = formula;
    this.comment = comment;
  }

  public CellPreview(HSSFCell cell) {
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_BOOLEAN: // Boolean
        value = cell.getBooleanCellValue();
        this.cellType = CellType.Boolean;
        break;
      case Cell.CELL_TYPE_NUMERIC: // 数字
        value = cell.getNumericCellValue();
        this.cellType = CellType.Number;
        break;
      case Cell.CELL_TYPE_STRING: // 字符串
        value = cell.getStringCellValue();
        this.cellType = CellType.String;
        break;
      case Cell.CELL_TYPE_FORMULA: // 公式
        value = cell.getNumericCellValue();
        this.cellType = CellType.Number;
        this.formula = cell.getCellFormula();
        break;
      case Cell.CELL_TYPE_BLANK: // 空值
        value = "";
        break;
      case Cell.CELL_TYPE_ERROR: // 故障
        value = "Error";
        break;
      default:
        if (cell.getDateCellValue() != null) {
          value = cell.getDateCellValue().getTime();
          this.cellType = CellType.Time;
        } else if (cell.getRichStringCellValue() != null) {
          value = cell.getRichStringCellValue().getString();
          this.cellType = CellType.String;
        } else {
          value = "未知类型";
        }
        break;
    }
    this.col = cell.getColumnIndex();
    this.row = cell.getRowIndex();
    if (cell.getCellComment() != null) {
      if (cell.getCellComment().getString() != null) {
        this.comment = cell.getCellComment().getString().getString();
      }
    }
    this.style = new CellStylePreview(cell);
  }

  public static void setTitleCell(Cell cell, CellPreview cellPreview,
      CellStyle cellStyle) {
    if (cellPreview.getValue() != null) {
      cell.setCellValue(cellPreview.getValue().toString());
    } else {
      cell.setCellValue("");
    }
    cell.setCellStyle(cellStyle);
  }

  public static void setCell(Cell cell, CellPreview cellPreview,
      CellStyle cellStyle) {
    switch (cellPreview.getCellType()) {
      case Boolean : {
        cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
        if (cellPreview.getValue() != null) {
          cell.setCellValue(Boolean.getBoolean(cellPreview.getValue().toString()));
        } else {
          cell.setCellValue(false);
        }
        break;
      }
      case Integer : {
        if (cellPreview.getValue() != null) {
          cell.setCellValue(Double.parseDouble(cellPreview.getValue().toString()));
        } else {
          cell.setCellValue("");
        }
        break;
      }
      case Number : {
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        if (cellPreview.getValue() != null) {
          cell.setCellValue(Double.parseDouble(cellPreview.getValue().toString()));
        } else {
          cell.setCellValue("");
        }
        break;
      }
      case String : {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        if (cellPreview.getValue() != null) {
          cell.setCellValue(cellPreview.getValue().toString());
        } else {
          cell.setCellValue("");
        }
        break;
      }
      case Date : {
        if (cellPreview.getValue() != null 
            && !"".equals(cellPreview.getValue())) {
          String value = StringKit.toString(cellPreview.getValue());
          cell.setCellValue(DateUtils.getDate1(Long.parseLong(value)));;
        } else {
          cell.setCellValue("");
        }
        break;
      }
      case Time : {
        if (cellPreview.getValue() != null 
            && !"".equals(cellPreview.getValue())) {
          String value = StringKit.toString(cellPreview.getValue());
          cell.setCellValue(new Date(Long.parseLong(value)));;
        } else {
          cell.setCellValue("");
        }
        break;
      }
      default : {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        if (cellPreview.getValue() != null) {
          cell.setCellValue(cellPreview.getValue().toString());
        } else {
          cell.setCellValue("");
        }
        break;
      }
    }
    cell.setCellStyle(cellStyle);
  }

  public CellPreview(XSSFCell cell) {
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_BOOLEAN: // Boolean
        value = cell.getBooleanCellValue();
        this.cellType = CellType.Boolean;
        break;
      case Cell.CELL_TYPE_NUMERIC: // 数字
        value = cell.getNumericCellValue();
        this.cellType = CellType.Number;
        break;
      case Cell.CELL_TYPE_STRING: // 字符串
        value = cell.getStringCellValue();
        this.cellType = CellType.String;
        break;
      case Cell.CELL_TYPE_FORMULA: // 公式
        value = cell.getNumericCellValue();
        this.formula = cell.getCellFormula();
        this.cellType = CellType.Number;
        break;
      case Cell.CELL_TYPE_BLANK: // 空值
        value = "";
        this.cellType = CellType.String;
        break;
      case Cell.CELL_TYPE_ERROR: // 故障
        value = "Error";
        break;
      default:
        if (cell.getDateCellValue() != null) {
          value = cell.getDateCellValue().getTime();
          this.cellType = CellType.Time;
        } else if (cell.getRichStringCellValue() != null) {
          value = cell.getRichStringCellValue().getString();
          this.cellType = CellType.String;
        } else {
          value = "未知类型";
        }
        break;
    }
    this.col = cell.getColumnIndex();
    this.row = cell.getRowIndex();
    if (cell.getCellComment() != null) {
      if (cell.getCellComment().getString() != null) {
        this.comment = cell.getCellComment().getString().getString();
      }
    }
    this.style = new CellStylePreview(cell);
  }

  public CellPreview() {
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public CellStylePreview getStyle() {
    return style;
  }

  public void setStyle(CellStylePreview style) {
    this.style = style;
  }

  public String getFormula() {
    return formula;
  }

  public void setFormula(String formula) {
    this.formula = formula;
  }

  public CellType getCellType() {
    return cellType;
  }

  public void setCellType(CellType cellType) {
    this.cellType = cellType;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int[][] getMerged() {
    return merged;
  }

  public void setMerged(int[][] merged) {
    this.merged = merged;
  }
}
