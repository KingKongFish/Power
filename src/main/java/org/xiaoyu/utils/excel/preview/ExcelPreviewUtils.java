package org.xiaoyu.utils.excel.preview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.xiaoyu.utils.excel.preview.style.CellStylePreview;

/**
 * 处理Excel，只处理excel中cell数据
 * 
 * @author peilongwu
 * @version 1.0
 * @date 2016-09-08
 */
public class ExcelPreviewUtils {
  /**
   * @function 处理excel文件流，按sheet生成json格式数组数据信息
   *           格式：{"sheets":[{"rows":[[],[],[]...]}]...}
   * @param in
   *          文件流
   * @param fileName
   *          文件类型
   * @return
   */
  public static JSONObject toJsonObject(InputStream inputStream, String fileName)
      throws Exception {
    Workbook wbs = null;
    String type = null;
    if (fileName.contains("xlsx")) {
      wbs = new XSSFWorkbook(inputStream);
      type = "xlsx";
    } else if (fileName.contains("xls")) {
      wbs = new HSSFWorkbook(inputStream);
      type = "xls";
    } else {
      throw new RuntimeException("Excel file type wrong! ");
    }
    return toJsonObject(wbs, fileName, type);
  }

  public static JSONObject toJsonObject(String fileName) throws Exception {
    File file = new File(fileName);
    if (!file.exists()) {
      throw new RuntimeException();
    } else {
      InputStream inputStream = new FileInputStream(file);
      JSONObject result = toJsonObject(inputStream, fileName);
      inputStream.close();
      return result;
    }
  }
  
  /**
   * 把ExcelEasy对象转换为JSON对象
   * @param excelEasy
   * @return
   */
  public static JSONObject toJsonObject(ExcelPreview gbrosExcel) {
    return (JSONObject) JSON.toJSON(gbrosExcel);
  }
  
  /**
   * @function 处理Workbook，按sheet生成json格式数组数据信息
   *           格式：{"sheets":[{"rows":[[],[],[]...]}]...}
   * @param Excel WorkBook
   * @param fileName
   *          文件类型
   */
  private static JSONObject toJsonObject(Workbook wbs, String fileName, String type) {
    try {
      ExcelPreview excel = new ExcelPreview();
      excel.setName(fileName);
      int sheetNums = wbs.getNumberOfSheets();
      List<SheetPreview> sheetList = new ArrayList<SheetPreview>();
      // sheet list start
      for (int i = 0; i < sheetNums; i++) {
        Sheet sheet = wbs.getSheetAt(i);
        SheetPreview sheetPreview = null;
        if ("xlsx".equals(type)) {
          sheetPreview = new SheetPreview((XSSFSheet) sheet);
        } else {
          sheetPreview = new SheetPreview((HSSFSheet) sheet);
        }
        sheetPreview.setIndex(i);
        sheetList.add(sheetPreview);
      }// end sheet list
      excel.setSheets(sheetList);
      return (JSONObject) JSON.toJSON(excel);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  public static String toJsonp(String callBackName, InputStream inputStream,
      String fileName) throws Exception {
    JSONObject json = toJsonObject(inputStream, fileName);
    String jsonp = callBackName + ":(" + json.toString() + ")";
    return jsonp;
  }

  /**
   * @function 转换JSONObject 为Excel WorkBook
   * @param json
   * @return Workbook
   */
  public static Workbook toExcel(JSONObject excelObject) {
    ExcelPreview excel = (ExcelPreview) JSON.toJavaObject(excelObject, ExcelPreview.class);
    return toExcel(excel);
  }
  
  /**
   * @function 转换ExcelPreview 为Excel WorkBook
   * @param json
   * @return Workbook
   */
  public static Workbook toExcel(ExcelPreview excel) {
    try {
      Workbook wbs;
      if (excel.getName().contains("xlsx")) {
        wbs = new XSSFWorkbook();
      } else {
        wbs = new HSSFWorkbook();
      }
      List<SheetPreview> sheetList = excel.getSheets();
      for (int i = 0; i < sheetList.size(); i++) {
        SheetPreview sheetEasy = sheetList.get(i);
        Sheet sheet = wbs.createSheet(sheetEasy.getName());
        List<List<CellPreview>> rowList = sheetEasy.getData();
        if (rowList != null && rowList.size() > 2 && rowList.get(1) != null
            && rowList.get(1).size() > 0) {
          CellStyle cellStyleTitle = CellStylePreview.createCellStyle(wbs,
              rowList.get(0).get(0));
          List<CellStyle> contentStyleList = new ArrayList<CellStyle>();
          for (int m = 0; m < rowList.get(0).size(); m++) {
            CellStyle cellStyle = CellStylePreview.createCellStyle(wbs, rowList
                .get(1).get(m));
            contentStyleList.add(cellStyle);
          }
          for (int j = 0; j < rowList.size(); j++) {
            Row row = sheet.createRow(j);
            List<CellPreview> cellList = rowList.get(j);
            for (int k = 0; k < cellList.size(); k++) {
              Cell cell = row.createCell(k);
              if (j == 0) {
                CellPreview.setTitleCell(cell, cellList.get(k), cellStyleTitle);
              } else {
                CellPreview.setCell(cell, cellList.get(k),
                    contentStyleList.get(k));
              }
              sheet.autoSizeColumn(k);
            }
          }
        }
      }
      return wbs;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static ExcelPreview toGbrosExcel(String excelName,
      List<SheetPreview> sheetList) {
    ExcelPreview excel = new ExcelPreview();
    excel.setName(excelName);
    excel.setSheets(sheetList);
    return excel;
  }

  public static SheetPreview toGbrosSheet(String sheetName,
      List<List<String>> list) {
    SheetPreview sheet = new SheetPreview();
    sheet.setName(sheetName);
    // sheet.setData(list);
    return sheet;
  }

  public static void main(String[] args) {
    String fileName = "E:\\临时文件\\培训班学员视图.xls";

    try {
      JSONObject excel = ExcelPreviewUtils.toJsonObject(fileName);
      File file = new File("E:\\临时文件\\培训班学员视图.txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      OutputStream outStream = new FileOutputStream(file);
      outStream.write(excel.toString().getBytes());
      outStream.flush();
      outStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
