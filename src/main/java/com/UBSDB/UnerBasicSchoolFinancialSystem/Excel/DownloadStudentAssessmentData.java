/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Excel;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */
@Component
public class DownloadStudentAssessmentData {
     XSSFWorkbook workbook=new XSSFWorkbook();
    private XSSFSheet sheet;
   // private final List<Student> listStudent;
    private  List<Assessment>TKS;

    public List<Assessment> getTKS() {
        return TKS;
    }

    public void setTKS(List<Assessment> TKS) {
        this.TKS = TKS;
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(XSSFSheet sheet) {
        this.sheet = sheet;
    }

   
    public StudentService getSS() {
        return SS;
    }

    public void setSS(StudentService SS) {
        this.SS = SS;
    }
    public  XSSFSheet checkExist(String ClassName){
    if(workbook.getNumberOfSheets()==0){
             sheet = workbook.createSheet(ClassName);
             return sheet;
       } else if(workbook.getSheet(ClassName)!=null){
           
   
          if(workbook.getSheet(ClassName).getSheetName().equals(ClassName)){
            System.out.println("exist");
             return sheet;
          }else{
        sheet = workbook.createSheet(ClassName);
        return sheet;
          }
       }else{
        sheet = workbook.createSheet(ClassName);
        return sheet;
       }
    
    
      
    
    }
     private @Autowired StudentService SS;
    private void writeHeaderLine(String ClassName) {
       
       checkExist(ClassName);
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
             
        createCell(row, 0, "Student Id", style);       
        createCell(row, 1, "TotalTest Score(60%)", style);    
        createCell(row, 2, "TotalTest Score Average 50%", style);
        createCell(row, 3, "Total ExamScore(100)", style);
         createCell(row, 4, "Total Avearage(100)", style);
        createCell(row, 5, "Total Score(100)", style);
        createCell(row, 6, "Subject", style);
        createCell(row, 7, "Grade", style);
         
     
        
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
     

     public void export(HttpServletResponse response,String ClassName,Term t) throws IOException {
   
     
       
         writeHeaderLine("_"
                 +"_"+t.getTermName());
         
        writeDataLines();
       
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
      
  
       
      
        outputStream.close();
       
    }
private void writeDataLines() {
  
    int rowCount = 1;
   //  List<Student>Students=SS.GetStudents_In_A_Particular_Class(TKS.getKlas());
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
    for (Assessment Tass : TKS) {
      
            Row row = sheet.createRow(rowCount++);
          int columnCount = 0;
             createCell(row, columnCount++, Tass.getStudent().getStudentId(), style);
           
             createCell(row, columnCount++, Tass.getTotalTestScore()+"", style);
            createCell(row, columnCount++, Tass.getTotalTestScoreAverage()+"", style);
            createCell(row, columnCount++, Tass.getExamScores()+"", style);
            createCell(row, columnCount++, Tass.getExamsAverage()+"", style);
            createCell(row, columnCount++, Tass.getTotalScore()+"", style);
           createCell(row, columnCount++, Tass.getSubject().getSubjectName()+"", style);
           createCell(row, columnCount++, Tass.getGrade()+"", style);
        //   System.out.println("compiling data"+Tass.getStudent().getStudentId());
             
        }    
    

}
}
