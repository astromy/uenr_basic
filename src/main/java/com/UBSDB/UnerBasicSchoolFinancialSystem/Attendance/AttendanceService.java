/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class AttendanceService {
  @Autowired
private AttendanceRepo AR; 
   @Autowired
private TermService TS;
  public String saveAttendance(Attendance attendance){
  attendance.setTerm(TS.getCurrentTerm());
      try{
          attendance.setAttendancepk(attendance.hashCode()+"");
          
  AR.save(attendance);
  return "saved successfully";
  
  }catch(Exception ex){
  return "Error "+ex;
  }
  }
  public Attendance getStudentAttendaceTermly(Student stud,Term term){
      System.out.println(term.getId()+" /// "+stud.getStudentId());
 Attendance TempAtt=new Attendance();
      for(Attendance att:stud.getAttendances()){
  if( att.getTerm().getId()==term.getId())   {
      TempAtt =att;
          }
    }
 return TempAtt ;
  }
   public String saveStudentAssessment(List<Attendance>Attendance){
   try{
       AR.saveAll(Attendance);
       return "nice!!! attendance successfully saved";
   }catch(Exception ex){
   return "error please contact admin for assistance \n"+ex.getMessage();
   }
   }       
}
