/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */
@Component
public class Sheet {
  private XSSFSheet sheet;

    public XSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(XSSFSheet sheet) {
        this.sheet = sheet;
    }
  
  
}
