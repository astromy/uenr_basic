/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author HP
 */
@Controller
public class ReceiptController {
    private @Autowired StudentService SS;
 @GetMapping("/findReceipt/{id}")
 public String findReceipt (Model model , @PathVariable("id")Long id){
 
     
 return "receipt";
 } 
  @GetMapping("/findStudReceipt/{id}")
 public String findReceiptStud (Model model , @PathVariable("id")String id){
      Student stud=SS.findStudent(id);
    model.addAttribute("name",stud.getFullName());
    stud.getReceipts().forEach(c->{
   
    });
    model.addAttribute("receipts_", stud.getReceipts());
     
 return "Receipt";
 }
 
    
}
