package org.xiaoyu.utils.excel.preview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.xiaoyu.utils.excel.preview.style.CellStylePreview;
import org.xiaoyu.utils.excel.preview.style.FontPreview;

public class ExcelPreviewGenerate {

  /**
   * @param name
   * @param data
   *          格式：[{"name":"sheet1","data":[{"名称":"plw","年龄":"18"},{"名称":"mzr","年龄":"20"}]},
   *               {"name":"sheet2","data":[{"名称":"plw","年龄":"18"},{"名称":"mzr","年龄":"20"}]}]
   * @return
   */
  public static ExcelPreview generate(String name, JSONArray sheetsObjects) {
    ExcelPreview excel = new ExcelPreview();
    excel.setName(name);
    List<SheetPreview> sheets = new ArrayList<SheetPreview>();
    if (sheetsObjects != null && !sheetsObjects.isEmpty()) {
      SheetPreview sheetPreview = null;
      int index = 0;
      for (int i = 0; i < sheetsObjects.size(); i++) {
        sheetPreview = new SheetPreview();
        JSONObject sheet = sheetsObjects.getJSONObject(i);
        List<List<CellPreview>> sheetData = new ArrayList<List<CellPreview>>();
        sheetData.add(getTitle(sheet.getJSONArray("data")));
        sheetData.addAll(getContent(sheet.getJSONArray("data")));
        sheetPreview.setData(sheetData);
        sheetPreview.setIndex(index);
        sheetPreview.setMaxRows(sheetData.size());
        sheetPreview.setMaxCols(sheetData.get(0).size());
        sheetPreview.setName(sheet.getString("name"));
        index++;
        sheets.add(sheetPreview);
      }
    }
    excel.setSheets(sheets);
    return excel;
  }

  public static List<CellPreview> getTitle(JSONArray sheet) {
    List<CellPreview> titleList = new ArrayList<CellPreview>();
    if (sheet != null && sheet.size() > 0) {
      JSONObject row = sheet.getJSONObject(0);
      CellPreview cell = null;
      int col = 0;
      for (Map.Entry<String, Object> entry : row.entrySet()) {
        cell = new CellPreview();
        cell.setRow(0);
        cell.setCol(col);
        cell.setCellType(CellType.String);
        cell.setValue(entry.getKey());
        cell.setStyle(getDefaultTitleStyle());
        titleList.add(cell);
        col++;
      }
    }
    return titleList;
  }

  public static List<List<CellPreview>> getContent(JSONArray sheet) {
    List<List<CellPreview>> sheetData = new ArrayList<List<CellPreview>>();
    if (sheet != null && sheet.size() > 0) {
      List<CellPreview> rowList = null;
      for (int i = 0; i < sheet.size(); i++) {
        rowList = new ArrayList<CellPreview>();
        JSONObject row = sheet.getJSONObject(i);
        CellPreview cell = null;
        int col = 0;
        for (Map.Entry<String, Object> entry : row.entrySet()) {
          cell = new CellPreview();
          cell.setRow(i + 1);
          cell.setCol(col);
          cell.setCellType(CellType.String);
          cell.setValue(entry.getValue());
          cell.setStyle(getDefaultContentStyle());
          rowList.add(cell);
          col++;
        }
        sheetData.add(rowList);
      }
    }
    return sheetData;
  }

  public static CellStylePreview getDefaultContentStyle() {
    CellStylePreview style = new CellStylePreview();
    style.setAlign((short) 2);
    style.setBottom((short) 1);
    style.setData((short) 0);
    style.setFill((short) 0);
    style.setFormatString("General");
    style.setHidden(false);
    style.setHightOfPoint(20);
    style.setIndention((short) 0);
    style.setLeft((short) 1);
    style.setLocked(false);
    style.setRight((short) 1);
    style.setRotation((short) 0);
    style.setTop((short) 1);
    style.setWrap(false);
    FontPreview font = new FontPreview();
    font.setBoldweight("normal");
    font.setFontName("宋体");
    font.setHightOfPoint((short) 10);
    font.setItalic(false);
    font.setStrikeout(false);
    style.setFont(font);
    return style;
  }

  public static CellStylePreview getDefaultTitleStyle() {
    CellStylePreview style = new CellStylePreview();
    style.setAlign((short) 2);
    style.setBottom((short) 1);
    style.setData((short) 0);
    style.setFill((short) 0);
    style.setFormatString("General");
    style.setHidden(false);
    style.setHightOfPoint(20);
    style.setIndention((short) 0);
    style.setLeft((short) 1);
    style.setLocked(false);
    style.setRight((short) 1);
    style.setRotation((short) 0);
    style.setTop((short) 1);
    style.setWrap(false);
    FontPreview font = new FontPreview();
    font.setBoldweight("bold");
    font.setFontName("宋体");
    font.setHightOfPoint((short) 12);
    font.setItalic(false);
    font.setStrikeout(false);
    style.setFont(font);
    return style;
  }

}
