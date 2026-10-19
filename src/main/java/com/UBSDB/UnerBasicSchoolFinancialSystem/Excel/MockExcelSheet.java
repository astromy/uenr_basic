/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Excel;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.mockDetails;
import java.io.IOException;
import java.util.Comparator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */

@Service
public class MockExcelSheet {
 
   // private XSSFSheet sheet;
   // private final List<Student> listStudent;
    private  List<TeachrKlassSubject>TKS;
   private mockDetails Md;

    public mockDetails getMd() {
        return Md;
    }

    public void setMd(mockDetails Md) {
        this.Md = Md;
    }
   
    public List<TeachrKlassSubject> getTKS() {
        return TKS.stream().filter(c->c.getKlas().getClassName().equalsIgnoreCase("JHS3A"))
                .collect(Collectors.toList());
    }

    public void setTKS(List<TeachrKlassSubject> TKS) {
        this.TKS = TKS;
    }

   

   
    public StudentService getSS() {
        return SS;
    }

    public void setSS(StudentService SS) {
        this.SS = SS;
    }
    /*public  XSSFSheet checkExist(String ClassName){
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
        if(workbook.getSheet(ClassName).getSheetName().equals(ClassName)){
            System.out.println("exist");
             
            return sheet;
        }else{
           sheet = workbook.createSheet(ClassName);
        return sheet;
     
       }
       }
    
      
    
    }*/
     private @Autowired StudentService SS;
    private void writeHeaderLine(XSSFSheet     sheet,XSSFWorkbook workbook) {
        
   //sheet  =workbook.createSheet(ClassName);
       //checkExist(ClassName);
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
             
        createCell(row, 1, "Sur Name", style);       
         
         createCell(row, 2, "Exam Score(100)", style);
         createCell(row, 0, "Student Id", style);
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
       
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
     XSSFWorkbook workbook=new XSSFWorkbook();
         getTKS().forEach(c->{     
         String text =c.getKlas().getClassName()+"_"+c.getSubjects().getSubjectName()+"_"
                 +c.getTeacher().getStaffId()+"_"+t.getTermName();
           Sheet  Sheet1 = new Sheet();
      
         XSSFSheet     sheet1 = workbook.createSheet(text);
        Sheet1.setSheet(sheet1);
         writeHeaderLine(Sheet1.getSheet(),workbook);
        writeDataLines(c,Sheet1.getSheet(),workbook);
        }
        );
      
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
      
  
       
        
        outputStream.close();
      
        workbook.close();
       
    }
private void writeDataLines(TeachrKlassSubject TKS,XSSFSheet     sheet,XSSFWorkbook workbook) {
        int rowCount = 1;
     List<Student>Students=SS.GetStudents_In_A_Particular_Class(TKS.getKlas());
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
       //    int columnCount = 0;  
if(Students.isEmpty()){
Students.add(new Student("0","","",""));

}       
       for (Student user : Students.stream().sorted(Comparator.comparing(Student::getSurname)).collect(Collectors.toList())) {
      user.setFullName(user.getFirstName()+" "+user.getLastName()+" "+user.getSurname());
            Row row = sheet.createRow(rowCount++);
          int columnCount = 0;
             createCell(row, columnCount++, user.getStudentId(), style);
           
             createCell(row, columnCount++, user.getFullName(), style);
             createCell(row, columnCount++, 0, style);
          
             
        }    
    
}
   
}
