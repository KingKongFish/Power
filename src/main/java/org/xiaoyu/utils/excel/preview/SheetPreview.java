package org.xiaoyu.utils.excel.preview;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class SheetPreview {
  /**
   * Excel sheet name
   */
  private String name;
  /**
   * Excel sheet index begin 0
   */
  private int index;
  /**
   * Excel sheet maxRows
   */
  private int maxRows;//
  /**
   * Excel sheet maxCols
   */
  private int maxCols;
  /**
   * Excel sheet data
   */
  private List<List<CellPreview>> data;
  /**
   * Excel sheet all merge cells
   */
  private List<MergedRegionPreview> merged;
  /**
   * Excel picture data info
   */
  private List<PicturePreview> pictures;

  public SheetPreview(XSSFSheet sheet) {
    this.name = sheet.getSheetName();
    this.maxRows = sheet.getPhysicalNumberOfRows();
    List<List<CellPreview>> rowList = new ArrayList<List<CellPreview>>();
    int maxCols = 0;
    for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) { // row list start
      List<CellPreview> cellList = new ArrayList<CellPreview>();
      XSSFRow row = sheet.getRow(j);
      if (null != row) { // cell list start
        for (int k = 0; k < row.getLastCellNum(); k++) {
          XSSFCell cell = row.getCell(k);
          CellPreview cellPreview = null;
          if (null != cell) {
            cellPreview = new CellPreview(cell);
          }
          cellList.add(cellPreview);
        }// end cell list
      }
      rowList.add(cellList);
    }// end row list
    this.maxCols = maxCols;
    this.data = rowList;
    int mergedRegions = sheet.getNumMergedRegions();
    List<MergedRegionPreview> mergedRegionList = new ArrayList<MergedRegionPreview>();
    for (int m = 1; m <= mergedRegions; m++) {
      CellRangeAddress cellRangeAddress = sheet.getMergedRegion(m - 1);
      MergedRegionPreview mergedRegion = new MergedRegionPreview();
      int rowStart = cellRangeAddress.getFirstRow();
      int colStart = cellRangeAddress.getFirstColumn();
      int rowEnd = cellRangeAddress.getLastRow();
      int colEnd = cellRangeAddress.getLastColumn();
      mergedRegion.setRowBegin(rowStart);
      mergedRegion.setColBegin(colStart);
      mergedRegion.setRowEnd(rowEnd);
      mergedRegion.setColEnd(colEnd);
      mergedRegionList.add(mergedRegion);
      this.data
          .get(rowStart)
          .get(colStart)
          .setMerged(new int[][] { { rowStart, colStart }, { rowEnd, colEnd } });
    }
    this.merged = mergedRegionList;
    // 处理图片
    List<XSSFShape> shapes = sheet.createDrawingPatriarch().getShapes();
    List<PicturePreview> pictures = new ArrayList<PicturePreview>();
    if (shapes != null && !shapes.isEmpty()) {
      for (XSSFShape shape : shapes) {
        if (shape instanceof XSSFPicture) {
          pictures.add(new PicturePreview((XSSFPicture) shape));
        }
      }
    }
    this.pictures = pictures;
  }

  public SheetPreview(HSSFSheet sheet) {
    this.name = sheet.getSheetName();
    this.maxRows = sheet.getPhysicalNumberOfRows();
    List<List<CellPreview>> rowList = new ArrayList<List<CellPreview>>();
    int maxCols = 0;
    for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) { // row list start
      List<CellPreview> cellList = new ArrayList<CellPreview>();
      HSSFRow row = sheet.getRow(j);
      if (null != row) { // cell list start
        for (int k = 0; k < row.getLastCellNum(); k++) {
          HSSFCell cell = row.getCell(k);
          CellPreview cellPreview = null;
          if (null != cell) {
            cellPreview = new CellPreview(cell);
          }
          cellList.add(cellPreview);
        }// end cell list
      }
      rowList.add(cellList);
    }// end row list
    this.maxCols = maxCols;
    this.data = rowList;
    int mergedRegions = sheet.getNumMergedRegions();
    List<MergedRegionPreview> mergedRegionList = new ArrayList<MergedRegionPreview>();
    for (int m = 1; m <= mergedRegions; m++) {
      CellRangeAddress cellRangeAddress = sheet.getMergedRegion(m - 1);
      MergedRegionPreview mergedRegion = new MergedRegionPreview();
      int rowStart = cellRangeAddress.getFirstRow();
      int colStart = cellRangeAddress.getFirstColumn();
      int rowEnd = cellRangeAddress.getLastRow();
      int colEnd = cellRangeAddress.getLastColumn();
      mergedRegion.setRowBegin(rowStart);
      mergedRegion.setColBegin(colStart);
      mergedRegion.setRowEnd(rowEnd);
      mergedRegion.setColEnd(colEnd);
      mergedRegionList.add(mergedRegion);
      this.data
          .get(rowStart)
          .get(colStart)
          .setMerged(new int[][] { { rowStart, colStart }, { rowEnd, colEnd } });
    }
    this.merged = mergedRegionList;
    // 处理图片
    List<HSSFShape> shapes = sheet.createDrawingPatriarch().getChildren();
    List<PicturePreview> pictures = new ArrayList<PicturePreview>();
    if (shapes != null && !shapes.isEmpty()) {
      for (HSSFShape shape : shapes) {
        if (shape instanceof HSSFPicture) {
          pictures.add(new PicturePreview((HSSFPicture) shape));
        }
      }
    }
    this.pictures = pictures;
  }

  public SheetPreview() {
  }

  public SheetPreview(String name, int index, int maxRows, int maxCols,
      List<List<CellPreview>> data) {
    this.name = name;
    this.index = index;
    this.maxRows = maxRows;
    this.maxCols = maxCols;
    this.data = data;
  }

  public SheetPreview(String name, int index, int maxRows, int maxCols,
      List<List<CellPreview>> data, List<MergedRegionPreview> merged) {
    this.name = name;
    this.index = index;
    this.maxRows = maxRows;
    this.maxCols = maxCols;
    this.data = data;
    this.merged = merged;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getMaxRows() {
    return maxRows;
  }

  public void setMaxRows(int maxRows) {
    this.maxRows = maxRows;
  }

  public int getMaxCols() {
    return maxCols;
  }

  public void setMaxCols(int maxCols) {
    this.maxCols = maxCols;
  }

  public List<List<CellPreview>> getData() {
    return data;
  }

  public void setData(List<List<CellPreview>> data) {
    this.data = data;
  }

  public List<MergedRegionPreview> getMerged() {
    return merged;
  }

  public void setMergedRegions(List<MergedRegionPreview> merged) {
    this.merged = merged;
  }

  public List<PicturePreview> getPictures() {
    return pictures;
  }

  public void setPictures(List<PicturePreview> pictures) {
    this.pictures = pictures;
  }

  public void setMerged(List<MergedRegionPreview> merged) {
    this.merged = merged;
  }

}
