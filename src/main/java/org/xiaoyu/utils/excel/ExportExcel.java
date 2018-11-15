package org.xiaoyu.utils.excel;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Excel 导出 Servlet.
 * 
 * @author peilongwu
 * @date 2016-09-08
 */
public class ExportExcel extends HttpServlet {

  private static final long serialVersionUID = 1L;

  /**
   * do get.
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      OutputStream outputStream = null;
      String fileTempName = (String) request.getSession().getAttribute(
          "fileName");
      response.setContentType("application/vnd.ms-excel");
      String fileName = new String(fileTempName.getBytes("GBK"), "ISO8859_1");
      response.setHeader("Content-Disposition", "attachment; filename="
          + fileName + ".xls");
      outputStream = response.getOutputStream();
      Workbook workbook = (Workbook) request.getSession().getAttribute(
          "excel-report");
      workbook.write(outputStream);
      request.getSession().removeAttribute("fileName");
      request.getSession().removeAttribute("excel-report");
      outputStream.flush();
      outputStream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * do post.
   */
  public void doPost(HttpServletRequest req, HttpServletResponse resp) {
    try {
      this.doGet(req, resp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
