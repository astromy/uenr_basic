/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.mock;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.*;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class MockService {
   @Autowired
   private MockRepo AR;
   
    @Autowired
   private klassService kS;
    @Autowired
   private StudentService SS;
   @Autowired private TeachrKlassSubjectService TKSs;
   @Autowired private TermService TS;
   public String saveAssessment(Mock assessment){
       try{
           if(!findAssessmentByDeatials(assessment).isPresent()){
           assessment.setStudentClass(assessment.getStudent().getStudentklass());
           assessment.setHeadMasterApproval("false");
            System.out.println
        (assessment.getStudent().getFirstName()+assessment.getSubject()
                .getSubjectName()+"found==>saved");
        assessment.setTotalScore(assessment.getExamScores());
        System.out.println(  AR.save(assessment).getMockDetails()+" details");
       
       return "Successfully saved";
           }
           
           else{
             
         System.out.println("foud"); 
       Mock tempAssessment= findAssessmentByDeatials( assessment).get();
           System.out.println
        (tempAssessment.getStudent().getFirstName()+tempAssessment.getSubject().getSubjectName()+"found==> updated");
        //   Assessment tempAssessment= findAssessmentByDeatials(assessment).get();
            tempAssessment.setExamScores(assessment.getExamScores()); 
            tempAssessment.setGrade(assessment.getGrade());
            tempAssessment.setTotalScore(assessment.getExamScores());
            tempAssessment.setTotalTestScoreAverage(assessment.getTotalTestScoreAverage());
            tempAssessment.setTotalTestScore(assessment.getTotalTestScore());
            tempAssessment.setPosition(assessment.getPosition());
             tempAssessment.setHighestScore(assessment.getHighestScore());
               tempAssessment.setMockDetails(assessment.getMockDetails());
           System.out.println(AR.save(tempAssessment).getStudent().getIdSpecia());
                 
             return "Updated Successfully";
           
            }
       }catch(Exception ex){
       ex.printStackTrace();
       return " Error "+ ex.toString()+ex.getMessage();
       }
   }
   public List<Mock>FindAll(){
   
   return AR.findAll();
   
   }
  /* public String CheckIfAssessmentForASubjectIsEnterdForAterm(Assessment Assessment){
 
      Optional<Assessment> ass= TS.findTermById(Assessment.getTerm().getId()).get().getAssessment()
        .stream().
        filter(newAss->(newAss.getSubject().getId()
           ==(Assessment.getSubject().getId()))&&newAss.getStudent().getStudentId().equals(Assessment.getStudent().getStudentId())).findFirst();
   if(ass.isPresent()){
   return "Assessment has been enterd already";
   
   }else{
   
   return "false";
   }
   }
  */
  public Map<Term,List<Mock>> getAssessmentBasedOnTerm(Term term,Student student){
  List<Mock>assess=new ArrayList<>();
  Map<Term,List<Mock>>TempTermAssessment=new HashMap<>();
  term.getMocks().stream().filter(TempAsessment -> TempAsessment.getStudent().getStudentId().equals(student.getStudentId())).forEachOrdered(TempAsessment -> {
      assess.add(TempAsessment);
       });
      TempTermAssessment.put(term, assess);
  return TempTermAssessment;
  }
  
  public Mock getStudentClassForATermAssessment(Term term,Student student){
  Mock newAss=new Mock();
   for(Mock TempAsessment:FindAll()){
  if((Objects.equals(TempAsessment.getTerm().getId(), term.getId())&&(TempAsessment.getStudent().getStudentId().equals(student.getStudentId())))){
  newAss=TempAsessment;
  }
  }return  newAss;
}
  public  Map<Student,List<Mock>>GetClassGeneralAssessment(klass klass,Term term,List<Student>stud){
            Map<Student,List<Mock>>GA=new HashMap<>();
             try{  
            stud.forEach(Newstud -> {
                List <Mock> Assessment=new ArrayList();
    Assessment=  SS.FindstudentById(Newstud).get().getMocks();
              
GA.put(Newstud, Assessment.stream().filter(c->c.getTerm().getId()==term.getId()).collect(Collectors.toList()) );
       }); }catch(Exception ex){
       }
return GA;
}
  //new data Structure for holdeing Student Name , Subject name,total Score which can be well sorted
  public List<MockHelper>SortedMarksWithData(List <Mock>assessment){
  List<MockHelper>AH=new ArrayList<>();
   assessment.stream().forEach(a->{
 MockHelper TempAH=new MockHelper();
  TempAH.setScore(a.getTotalScore());
  TempAH.setStud(a.getStudent());TempAH.setSubjectName(a.getSubject().getSubjectName());
  AH.add(TempAH);
   });
   return AH.stream().sorted(Comparator.comparing(MockHelper::getSubjectName)).collect(Collectors.toList());
  
  }
  public List<Mock>GetKlassAssessment(klass klas,Term term){
  
  
  return kS.FindKlass(klas.getClassName()).getMocks().stream().filter(c->c.getTerm().getId()==term.getId()).collect(Collectors.toList());
  } 
  
public String AddRankEnding(int number){
String TempConvertNumber =(number+"");
int totalNumberOfElements=TempConvertNumber.length()-1;
if(TempConvertNumber.length()>1){
if(TempConvertNumber.charAt(totalNumberOfElements-1)=='1'){

return TempConvertNumber+"th";
}
}
if (TempConvertNumber.charAt(totalNumberOfElements)=='1'){
return TempConvertNumber+"st";
}
else if(TempConvertNumber.charAt(0)=='1'&&TempConvertNumber.charAt(totalNumberOfElements)=='1'){
return TempConvertNumber+"th";
}
else if (TempConvertNumber.charAt(totalNumberOfElements)=='2'){
return TempConvertNumber+"nd";
}
else if (TempConvertNumber.charAt(totalNumberOfElements)=='3'){
return TempConvertNumber+"rd";
}else{
return TempConvertNumber+"th";
}
}
public String ApproveAssessment(List<Mock> ass,users user){
try{
    ass.stream().forEach(a->{
        a.setHeadMasterApproval("TRUE");
        a.setHeadmasterInitial(user.getFirstname().toUpperCase().charAt(0)+user.getLastname().toUpperCase().charAt(0)+"");
    AR.save(a);    
            
            });
   
return "Approved Successfully";
}catch (Exception ex){


return "Problem Approving Assessment Contact Admin"+ex.getLocalizedMessage();
}

}
public String checkHeadMasterInitials(Student stud,Term term){
String initials="false";
 List <Assessment>ass= TS.findTermById(term.getId()).get().getAssessment().stream().
          filter(c->c.getStudent().getStudentId()
                  .equals(stud.getStudentId())).collect(Collectors.toList());
        
for(Assessment as:ass){
if(as.getHeadMasterApproval().equals("false")){
return initials;
}else{
initials=as.getInitials();
        return initials;
}
}
return initials;
}
public String getInitialSfromTeacherAndHead(Student stud,Term term){
String message="";
    if(checkHeadMasterInitials(stud,term ).equals("false")){
 message="provisional";

} else {  

    Teacher t=TKSs.getClassMaster(stud.getStudentklass().getClassName()).getTeacher();
message ="HeadTeacher Signature:"+ checkHeadMasterInitials((stud), term)+
        "Teachers"+t.getFirstName().toUpperCase().charAt(0)+t.getLastName().toUpperCase().charAt(0)+" "+
         "Date: "+LocalDate.now();
}
return  message;

}
public Optional<Mock>  findAssessmentByDeatials(Mock assessment){
    
Optional<Mock> ass= TS.findTermById(assessment.getTerm()
        .getId()).get().getMocks()
        .stream().
        filter(newAss->(newAss.getSubject().getId()
           ==(assessment.getSubject().getId()))&&newAss.getStudent()
                   .getStudentId().equals(assessment.getStudent()
                           .getStudentId())).findFirst();

return ass;
}

public String removeAssessment(Mock a){
try{
    AR.delete(a);
return "Success";
}catch(Exception ex){

return ex.toString();
}

}
}
