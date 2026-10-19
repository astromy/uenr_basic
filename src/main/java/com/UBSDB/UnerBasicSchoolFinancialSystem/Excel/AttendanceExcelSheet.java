 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Excel;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class AttendanceExcelSheet {
      private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<Student> listStudent;
     private  String numberOfDays;

    public XSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(XSSFSheet sheet) {
        this.sheet = sheet;
    }

    public String getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
    public AttendanceExcelSheet(List<Student> listUsers) {
        this.listStudent = listUsers;
      
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine(String ClassName) {
        sheet = workbook.createSheet(ClassName);
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
             
        createCell(row, 1, "First Name", style);       
         createCell(row, 2, "Total Days", style);
         createCell(row, 3, "Days In School", style);
         createCell(row, 4, "Conduct", style);
         createCell(row, 5, "Interest", style);
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 //Write Boys sorted
      //  int columnCount = 0;
                 for (Student user : listStudent.stream().sorted((b,c)->b.getLastName().compareTo(c.getLastName())).collect(Collectors.toList())) {
           Row row = sheet.createRow(rowCount++);
          int columnCount = 0;
          
             createCell(row, columnCount++, user.getStudentId(), style);
           
             createCell(row, columnCount++, user.getFirstName()+" "+user.getSurname()+" " +user.getLastName(), style);
            createCell(row, columnCount++, numberOfDays, style);
            createCell(row, columnCount++, 0, style);
            createCell(row, columnCount++, "SATISFACTORY", style);
             createCell(row, columnCount++, "READING,ROBOTICS,MUSIC", style);
             
        } 
    }
     
    public void export(HttpServletResponse response,String ClassName) throws IOException {
        writeHeaderLine(ClassName);
        writeDataLines();
         
          try (ServletOutputStream outputStream = response.getOutputStream()) {
              workbook.write(outputStream);
              workbook.close();
          }
         
    }

    
  
    
}
