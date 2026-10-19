/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.PROMOTION;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */
@Service
public class promoteServiceImpl implements promoteService {
private @Autowired promoteRepository pr;
private @Autowired StudentService SS;
private @Autowired TermService TS;
private @Autowired klassService kS;

@Override
    public promote savePromotion(promote promotion) {
      try{
      if(checkIfPromoted(promotion.getStudent())==true){
      System.out.println("promoted already");
      return promotion;
      }else{
        promotion=  pr.save(promotion);
         Student s=promotion.getStudent();
         s.setStudentklass(kS.FindKlass(promotion.getNextClass()));
     System.out.println(    SS.UpdateStudentRecords(s)+" ... "+promotion.getNextClass());
         return promotion;
      }
      }catch (Exception ex)
      {
      System.out.println(ex);
      return null;
      }        
        
        
    }

    @Override
    public List<promote> Studentpromotions(Student student) {
    
    return student.getPromotions();
    }

    @Override
    public String savePromtion_for_A_Class(klass klass) {
    try{
     klass.getStudents().forEach(s->{
     
     if(checkIfPromoted(s)==true){
     
     }else{
     
     promote p = new promote(s.getStudentklass().getClassName(), 
             SS.promoteStudent_withoutsaving(s), s, 
             TS.getCurrentTerm().getAcademicyear());
     
     SS.promoteStudent(s);
     }
     
     });
     return "Promoted";
    }catch (Exception ex){
    
    return "error";
    }
           
    }

    @Override
    public Optional<promote> findPromotion(promote promotion) {
    
        return pr.findById(promotion.getID());
        
    }
    
    
    private boolean checkIfPromoted(Student s){
    
  if(  s.getPromotions().stream().filter(c->c
             .getAcademicyear().getAcademicName()
             .equals(TS.getCurrentTerm()
                     .getAcademicyear()
                     .getAcademicName()))
          .findFirst().isPresent()){
     
  return true;  
    }else{
  return false;
  
  }
}
}