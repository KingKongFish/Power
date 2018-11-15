package org.xiaoyu.utils.excel.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.xiaoyu.utils.data.StringKit;
import org.xiaoyu.utils.excel.MergedRegion;

/**
 * 处理Excel，只处理excel中cell数据
 * 
 * @author Peilw
 * @version 1.0
 */
public class ExcelSimpleUtils {
  /**
   * @function 处理excel文件流，按sheet生成json格式数组数据信息
   *           格式：{"sheets":[{"rows":[[],[],[]...]}]...}
   * @param inputStream 文件流
   * @param fileName 文件类型
   * @return
   */
  public static JSONObject toJsonObject(InputStream inputStream, String fileName)
      throws Exception {
    Workbook wbs = null;
    if (fileName.contains("xlsx")) {
      wbs = new XSSFWorkbook(inputStream);
    } else if (fileName.contains("xls")) {
      wbs = new HSSFWorkbook(inputStream);
    } else {
      throw new RuntimeException("Excel file type wrong! ");
    }
    return toJsonObject(wbs, fileName);
  }

  public static JSONObject toJsonObject(String fileName) throws Exception {
    File file = new File(fileName);
    if (!file.exists()) {
      throw new RuntimeException();
    } else {
      InputStream inputStream = new FileInputStream(file);
      return toJsonObject(inputStream, fileName);
    }
  }
  
  /**
   * 把ExcelEasy对象转换为JSON对象
   * 
   * @param excel
   * @return
   */
  public static JSONObject toJsonObject(ExcelSimple excel) {
    return (JSONObject) JSON.toJSON(excel);
  }
  
  /**
   * @function 处理Workbook，按sheet生成json格式数组数据信息
   *           格式：{"sheets":[{"rows":[[],[],[]...]}]...}
   * @param wbs Excel WorkBook
   * @param fileName 文件类型
   * @return
   */
  public static JSONObject toJsonObject(Workbook wbs, String fileName) {
    try {
      ExcelSimple excel = new ExcelSimple();
      excel.setName(fileName);
      int sheetNums = wbs.getNumberOfSheets();
      List<SheetSimple> sheetList = new ArrayList<SheetSimple>();
      // sheet list start
      for (int i = 0; i < sheetNums; i++) {
        Sheet sheet = wbs.getSheetAt(i);
        SheetSimple sheetSimple = new SheetSimple();
        sheetSimple.setName(sheet.getSheetName());
        sheetSimple.setIndex(i);
        List<List<String>> rowList = new ArrayList<List<String>>();
        int maxCols = 0;
        sheetSimple.setMaxRows(sheet.getLastRowNum());
        // row list start
        for (int j = 0; j <= sheet.getLastRowNum(); j++) {
          List<String> cellList = new ArrayList<String>();
          Row row = sheet.getRow(j);
          if (null != row) {
            // cell list start
            for (int k = 0; k < row.getLastCellNum(); k++) {
              Cell cell = row.getCell(k);
              String tmpString;
              if (null != cell) {
                tmpString = getCellValue(cell);
                if (k > maxCols) {
                  maxCols = k;
                }
              } else {
                tmpString = "";
              }
              cellList.add(tmpString);
            }// end cell list
          }
          rowList.add(cellList);
        }// end row list
        int mergedRegions = sheet.getNumMergedRegions();
        List<MergedRegion> mergedRegionList = new ArrayList<MergedRegion>();
        for (int m = 1; m <= mergedRegions; m++) {
          CellRangeAddress cellRangeAddress = sheet.getMergedRegion(m - 1);
          MergedRegion mergedRegion = new MergedRegion();
          mergedRegion.setRowBegin(cellRangeAddress.getFirstRow());
          mergedRegion.setColBegin(cellRangeAddress.getFirstColumn());
          mergedRegion.setRowEnd(cellRangeAddress.getLastRow());
          mergedRegion.setColEnd(cellRangeAddress.getLastColumn());
          mergedRegionList.add(mergedRegion);
        }
        for (MergedRegion mergedRegion : mergedRegionList) {
          int rowBegin = mergedRegion.getRowBegin();
          int rowEnd = mergedRegion.getRowEnd();
          int colBegin = mergedRegion.getColBegin();
          int colEnd = mergedRegion.getColEnd();
          String value = rowList.get(rowBegin).get(colBegin);
          for (int m = rowBegin; m <= rowEnd; m++) {
            for (int n = colBegin; n <= colEnd; n++) {
              rowList.get(m).set(n, value);
            }
          }
        }
        sheetSimple.setMaxCols(maxCols);
        sheetSimple.setData(rowList);
        sheetList.add(sheetSimple);
        // sheetSimple.setMergedRegions(mergedRegionList);
      } // end sheet list
      excel.setSheets(sheetList);
      return (JSONObject) JSON.toJSON(excel);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * Excel 转换为JSON结构的数据，格式为：{"sheets":[{"rows":[[],[],[]...]}]...}
   * 
   * @param wbs POI Excel对象
   * @return JSONObject 对象
   */
  public static JSONObject toJsonObject(Workbook wbs) {
    try {
      int sheetNums = wbs.getNumberOfSheets();
      Map<String,Object> sheetObject = new HashMap<String,Object>();
      for (int i = 0; i < sheetNums; i++) {
        Sheet sheet = wbs.getSheetAt(i);
        List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
        List<String> columns = new ArrayList<String>();
        Row firstRow = sheet.getRow(0);
        if (firstRow != null) {
          for (int colNum = 0; colNum < firstRow.getLastCellNum(); colNum++) {
            columns.add(firstRow.getCell(colNum).getStringCellValue());
          }
          for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            if (null != row) {
              Map<String,Object> obj = new HashMap<String,Object>();
              int blankFieldNum = 0;
              for (int k = 0; k < row.getLastCellNum(); k++) {
                Cell cell = row.getCell(k);
                String value = getCellValue(cell);
                if (StringKit.isBlank(value)) {
                  blankFieldNum++;
                }
                obj.put(columns.get(k), value);
              }
              if (blankFieldNum != row.getLastCellNum()) {
                rowList.add(obj);
              }
            }
          }
          sheetObject.put(sheet.getSheetName(), rowList);
        }
      } 
      return (JSONObject)JSONObject.toJSON(sheetObject);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   *
   * @param wbs
   * @param ignore
   * @return
   */
  public static JSONObject toJsonObject(Workbook wbs,List<String> ignore) {
    try {
      int sheetNums = wbs.getNumberOfSheets();
      Map<String,Object> sheetObject = new HashMap<String,Object>();
      for (int i = 0; i < sheetNums; i++) {

        Sheet sheet = wbs.getSheetAt(i);
        String sheetName = StringKit.toString(sheet.getSheetName(), true);
        System.out.println("excel sheetName:"+sheetName);
        boolean isIgnore = false;
        System.out.println("excel ignore size:"+ignore.size());
        for(String str : ignore){
          System.out.println("excel str:"+str);
          if(str.toLowerCase().equals(sheetName.toLowerCase())){
            isIgnore = true;
            break;
          }
        }
        System.out.println("excel isIgnore:"+isIgnore);
        if(isIgnore){
          continue;
        }
        List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
        List<String> columns = new ArrayList<String>();
        Row firstRow = sheet.getRow(0);
        if (firstRow != null) {
          for (int colNum = 0; colNum < firstRow.getLastCellNum(); colNum++) {
            columns.add(firstRow.getCell(colNum).getStringCellValue());
          }
          for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            if (null != row) {
              Map<String,Object> obj = new HashMap<String,Object>();
              int blankFieldNum = 0;
              for (int k = 0; k < row.getLastCellNum(); k++) {
                Cell cell = row.getCell(k);
                String value = getCellValue(cell);
                if (StringKit.isBlank(value)) {
                  blankFieldNum++;
                }
                obj.put(columns.get(k), value);
              }
              if (blankFieldNum != row.getLastCellNum()) {
                rowList.add(obj);
              }
            }
          }
          sheetObject.put(sheetName, rowList);
        }
      }
      return (JSONObject)JSONObject.toJSON(sheetObject);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 文件输入流转换为JSONObject对象，根据输入的名称，对Excel 2003格式及Excel 2007格式进行不同的转换.
   * 
   * @param inputStream 文件输入流
   * @param fileName 文件名称
   * @return JSONObject
   * @throws Exception
   */
  public static JSONObject toMapObject(InputStream inputStream, String fileName)
      throws Exception {
    Workbook wbs = null;
    if (fileName.contains("xlsx")) {
      wbs = new XSSFWorkbook(inputStream);
    } else if (fileName.contains("xls")) {
      wbs = new HSSFWorkbook(inputStream);
    } else {
      throw new RuntimeException("Excel file type wrong! ");
    }
    return toJsonObject(wbs);
  }
  
  /**
   * 根据传入的Excel文件流，Excel文件名称及Sheet名称，获取Sheet数据并转为JSONObject.
   *    格式为：{"sheetName":[{...},{...}...]}
   * 
   * @param inputStream Excel文件输入流
   * @param fileName Excel文件名
   * @param sheetName Sheet名称
   * @return JSONObject JSON格式的Sheet数据
   * @throws Exception 异常
   */
  public static JSONObject toMapObject(InputStream inputStream, String fileName, String sheetName)
      throws Exception {
    Workbook wbs = null;
    if (fileName.contains("xlsx")) {
      wbs = new XSSFWorkbook(inputStream);
    } else if (fileName.contains("xls")) {
      wbs = new HSSFWorkbook(inputStream);
    } else {
      throw new RuntimeException("Excel file type wrong! ");
    }
    return getSheet(wbs, sheetName);
  }

  /**
   *
   * @param inputStream
   * @param fileName
   * @param ignore
   * @return
   * @throws Exception
   */
  public static JSONObject toMapObject(InputStream inputStream, String fileName, List<String> ignore)
      throws Exception {
    Workbook wbs = null;
    if (fileName.contains("xlsx")) {
      wbs = new XSSFWorkbook(inputStream);
    } else if (fileName.contains("xls")) {
      wbs = new HSSFWorkbook(inputStream);
    } else {
      throw new RuntimeException("Excel file type wrong! ");
    }
    return toJsonObject(wbs,ignore);
  }

  /**
   * 将Excel转换为JSONObject.
   * 
   * @param fileName 文件名
   * @return JSONObject 转换后的JSON格式的Excel数据
   * @throws Exception 异常
   */
  public static JSONObject toMapObject(String fileName) throws Exception {
    File file = new File(fileName);
    if (!file.exists()) {
      throw new RuntimeException();
    } else {
      InputStream inputStream = new FileInputStream(file);
      return toMapObject(inputStream, fileName);
    }
  }
  
  /**
   * 将Excel文件转换为Map<String,List<List<String>>>格式数据.
   * 
   * @param inputStream Excel文件
   * @param fileName Excel文件名
   * @return Map<String,List<List<String>>>
   * @throws Exception 异常
   */
  public static Map<String,List<List<String>>> toMapList(InputStream inputStream, String fileName)
      throws Exception {
    Workbook wbs = null;
    if (fileName.contains("xlsx")) {
      wbs = new XSSFWorkbook(inputStream);
    } else if (fileName.contains("xls")) {
      wbs = new HSSFWorkbook(inputStream);
    } else {
      throw new RuntimeException("Excel file type wrong! ");
    }
    return getSheetList(wbs);
  }
  
  /**
   * 
   * Excel转换为JSONP格式字符串.
   * 
   * @param callBackName JSONP调用名
   * @param inputStream Excel文件
   * @param fileName Excel文件名
   * @return
   * @throws Exception 异常
   */
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
  public static Workbook toExcel(JSON json) {
    try {
      ExcelSimple excel = JSON.toJavaObject(json, ExcelSimple.class);
      return toExcel(excel);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * @function 转换JSONObject 为Excel WorkBook
   * @param excel
   * @return Workbook
   */
  public static Workbook toExcel(ExcelSimple excel) {
    try {
      Workbook wbs;
      if (excel.getName().contains("xlsx")) {
        wbs = new XSSFWorkbook();
      } else {
        wbs = new HSSFWorkbook();
      }
      List<SheetSimple> sheetList = excel.getSheets();
      for (int i = 0; i < sheetList.size(); i++) {
        SheetSimple sheetEasy = sheetList.get(i);
        Sheet sheet = wbs.createSheet(sheetEasy.getName());
        List<List<String>> rowList = sheetEasy.getData();
        for (int j = 0; j < rowList.size(); j++) {
          Row row = sheet.createRow(j);
          List<String> cellList = rowList.get(j);
          for (int k = 0; k < cellList.size(); k++) {
            Cell cell = row.createCell(k);
            cell.setCellValue(cellList.get(k));
          }
        }
      }
      return wbs;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * 列表数据转换为Excel.
   * 
   * @param fileName 文件名
   * @param sheetName Sheet名称
   * @param dataList 数据列表
   * @return POI Excel对象
   */
  public static Workbook toExcel(String fileName, String sheetName, List<List<String>> dataList) {
    SheetSimple sheet = toGbrosSheet(sheetName, dataList);
    ExcelSimple excel = new ExcelSimple();
    excel.setName(fileName);
    List<SheetSimple> sheets = new ArrayList<SheetSimple>();
    sheets.add(sheet);
    excel.setSheets(sheets);
    return toExcel(excel);
  }

  /**
   * 根据文件名及自定义Excel SheetSimple转换为 ExcelSimple.
   * 
   * @param excelName Excel文件名
   * @param sheetList Sheet数据
   * @return ExcelSimple
   */
  public static ExcelSimple toGbrosExcel(String excelName, List<SheetSimple> sheetList) {
    ExcelSimple excel = new ExcelSimple();
    excel.setName(excelName);
    excel.setSheets(sheetList);
    return excel;
  }

  /**
   * 根据Sheet名称及Sheet列表数据转换为SheetSimple.
   * 
   * @param sheetName Sheet名称
   * @param list Sheet列表数据
   * @return SheetSimple
   */
  public static SheetSimple toGbrosSheet(String sheetName, List<List<String>> list) {
    SheetSimple sheet = new SheetSimple();
    sheet.setName(sheetName);
    sheet.setData(list);
    return sheet;
  }
  
  /**
   * Excel 日期转换为Java Date.
   * 
   * @param dateValue Excel Date值
   * @return Date
   */
  public static Date getExcelDate(Double dateValue) {
    return HSSFDateUtil.getJavaDate(dateValue, false);
  }
  
  /**
   * 获取单元格的值，并转换为字符串.
   * 
   * @param cell Excel 单元格
   * @return 字符串格式的单元格值
   */
  public static String getCellValue(Cell cell) {
    String tmpString = null;
    if (null != cell) {
      switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING: // 字符串
          tmpString = cell.getStringCellValue();
          break;
        case Cell.CELL_TYPE_NUMERIC: // 数字
          tmpString = StringKit.doubleToString(cell
              .getNumericCellValue());
          break;
        case Cell.CELL_TYPE_BOOLEAN: // Boolean
          tmpString = Boolean.toString(cell.getBooleanCellValue());
          break;
        case Cell.CELL_TYPE_FORMULA: // 公式
          try {
            tmpString = cell.getStringCellValue();
          } catch (Exception e) {
            tmpString = StringKit.doubleToString(cell.getNumericCellValue());
          }
          break;
        case Cell.CELL_TYPE_BLANK: // 空值
          tmpString = "";
          break;
        case Cell.CELL_TYPE_ERROR: // 故障
          tmpString = null;
          break;
        default:
          tmpString = null;
          break;
      }
    }
    return tmpString;
  }
  
  private static JSONObject getSheet(Workbook wbs, String sheetName) {
    try {
      Map<String,Object> sheetObject = new HashMap<String,Object>();
      Sheet sheet = wbs.getSheet(sheetName);
      List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
      List<String> columns = new ArrayList<String>();
      Row firstRow = sheet.getRow(0);
      if (firstRow != null) {
        for (int colNum = 0; colNum < firstRow.getLastCellNum(); colNum++) {
          columns.add(firstRow.getCell(colNum).getStringCellValue());
        }
        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
          Row row = sheet.getRow(j);
          if (null != row) {
            Map<String,Object> obj = new HashMap<String,Object>();
            int blankFieldNum = 0;
            for (int k = 0; k < row.getLastCellNum(); k++) {
              Cell cell = row.getCell(k);
              String value = getCellValue(cell);
              if (StringKit.isBlank(value)) {
                blankFieldNum++;
              }
              obj.put(columns.get(k), value);
            }
            if (blankFieldNum != row.getLastCellNum()) {
              rowList.add(obj);
            }
          }
        }
        sheetObject.put(sheet.getSheetName(), rowList);
      }
      return (JSONObject)JSONObject.toJSON(sheetObject);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  private static Map<String,List<List<String>>> getSheetList(Workbook wbs) {
    try {
      Map<String,List<List<String>>> sheetObject = new HashMap<String,List<List<String>>>();
      for (int i = 0; i < wbs.getNumberOfSheets(); i++) {
        Sheet sheet = wbs.getSheetAt(i);
        List<List<String>> rowList = new ArrayList<List<String>>();
        for (int j = 0; j <= sheet.getLastRowNum(); j++) {
          Row row = sheet.getRow(j);
          List<String> rowValue = new ArrayList<String>();
          if (null != row) {
            for (int k = 0; k < row.getLastCellNum(); k++) {
              Cell cell = row.getCell(k);
              String value = getCellValue(cell);
              rowValue.add(value);
            }
          }
          rowList.add(rowValue);
        }
        sheetObject.put(StringKit.toString(sheet.getSheetName(), true), rowList);
      }
      return sheetObject;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public static Long dateToJavaDate(String date) {
    return StringKit.isNotBlank(date) ? (Long.parseLong(date) - 25569) * 86400000L : null;
  }
}
