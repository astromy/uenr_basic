/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author enock
 */
@Controller
public class AssessmentController {
    @Autowired
    private UserService US; @Autowired private AssessmentService AS;
    @Autowired private klassService KS; @Autowired private TermService TS;
    @Autowired private StudentService SS;
    
   @PostMapping("TeacherPortal/SaveStudentAssessment/")
   public String AddAssessment(){
   
       return "TeacherPortal";
   }
   
   @GetMapping("Admin/StudentKlassAssessment/{TermId}")
   public String StudentKlassesAssessment(Model model,Principal principal
           ,@PathVariable("TermId")Long TermId,Principal pp)
   {
     
   model.addAttribute("TermId",TermId);
   
   model.addAttribute("StudentAssessmentKlass",KS.AllKlass());
  users user=US.FindUserById(pp.getName());
  
   for(role r:user.roles){
           switch (r.getName()) {
               case "Admin":
                   return "index";
               case "HeadTeacher":
                   return "HeadTeacherPortal";
               default:
                   return "FinancePortal";
           }
    }
     return "index";
   }
   
   @GetMapping("Admin/AssessmetCofig")
   public String AssessmetCofig(Model model,Principal principal) {
   model.addAttribute("AseesessmtSearchform",true);
  
   Map<String,Term>data=new HashMap<>();
   TS.terms().forEach(c->{
   data.put(c.getTermName()+"_"+c.getAcademicyear(), c);
   });
   
   model.addAttribute("data",data);
 
   return "index";
   }
   
   
   @GetMapping("/GetClassAssessment/{ClassId}/{TermId}")
   public String GetClassAssessmentCurrentTerm(Model model,Principal principal,
           @PathVariable("ClassId")String ClassId, @PathVariable("TermId")Long TermId) throws Exception{
       Term t =TS.findTermById(TermId).get();
     model.addAttribute("TermId",TermId);
     
   users user=US.FindUserById(principal.getName());
  Runnable r=()->{try {
   if(AS.GetClassGeneralAssessment(
        KS.FindKlass(ClassId) ,
           t ,
           SS.GetStudents_In_A_Particular_Class(KS.FindKlass(ClassId)) )==null||t==null){
   model.addAttribute("msg", "Please Contact Admin to Set the Term and or no assessment for class");
   
   }else{
   model.addAttribute("classIDAssessment",ClassId);
   model.addAttribute("ClassAssessment",AS.GetClassGeneralAssessment(
      
           KS.FindKlass(ClassId) ,t
           ,SS.GetStudents_In_A_Particular_Class(KS.FindKlass(ClassId)) ));
  ClassaGeneralAssessment cgl=new ClassaGeneralAssessment();
   model.addAttribute("Subjects",
           cgl.getSubjectNames(
                   AS.GetKlassAssessment(KS.FindKlass(ClassId),t)));
    model.addAttribute("AllStudentAssessment",
            AS.GetKlassAssessment(KS.FindKlass(ClassId), t).stream().map(Assessment::getAsessmentResponse).collect(Collectors.toList()));
   model.addAttribute("TermId", TermId);
   }}catch (Exception ex){
     ex.printStackTrace();
   } }; r.run();
   for(role r1:user.roles){
    if(r1.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
   }
return "index";
   
   }
    @GetMapping("/TeacherPortal/GetClassAssessmentTeahcer/{ClassId}")
    public String GetClassAssessmentTeacher(Model model,Principal principal,
                                                @PathVariable("ClassId")String ClassId) throws Exception{
        Term t =TS.findTermById(TS.getCurrentTerm().getId()).get();
System.out.println("student results compilation");
        model.addAttribute("TermId",t.getId());

        users user=US.FindUserById(principal.getName());
        Runnable r=()->{try {
            if(AS.GetClassGeneralAssessment(
                    KS.FindKlass(ClassId) ,
                    t ,
                    SS.GetStudents_In_A_Particular_Class(KS.FindKlass(ClassId)) )==null||t==null){
                model.addAttribute("err", "Please Contact Admin to Set the Term and or no assessment for class");
                System.out.println("student results compilation there is no data available");
            }else{
                model.addAttribute("classIDAssessment",ClassId);
                model.addAttribute("Teacher_Assessment",AS.GetClassGeneralAssessment(

                        KS.FindKlass(ClassId) ,t
                        ,SS.GetStudents_In_A_Particular_Class(KS.FindKlass(ClassId)) ));
                ClassaGeneralAssessment cgl=new ClassaGeneralAssessment();
                model.addAttribute("Subjects",
                        cgl.getSubjectNames(
                                AS.GetKlassAssessment(KS.FindKlass(ClassId),t)));
                model.addAttribute("AllStudentAssessment",
                        AS.GetKlassAssessment(KS.FindKlass(ClassId), t));
                model.addAttribute("TermId", t.getId());
                System.out.println("Pulling data to UI");
            }}catch (Exception ex){
            ex.printStackTrace();
            model.addAttribute("err", "Please Contact Admin");

        } }; r.run();
        for(role r1:user.roles){
            if(r1.getName().equals("HeadTeacher")){
                return "HeadTeacherPortal";
            }
        }
        return "TeacherPortal";

    }
   @GetMapping("/ApproveAssessment/{klassId}")
   public String ApproveAssessment(Model model,@PathVariable("klassId")String klassId, Principal principal) throws Exception{
  users user=US.FindUserById(principal.getName());
 List< Assessment> ass=AS.GetKlassAssessment(KS.FindKlass(klassId), TS.getCurrentTerm());
       model.addAttribute("msg",AS.ApproveAssessment(ass,user));
return "HeadTeacherPortal";
   }
   @GetMapping("/RemoveAssessment")
   public String ApproveAssessment(Model m){
       
  Runnable r =()->{
       AS.FindAll().forEach(c->{
            AS.removeAssessment(c);
      if((c.getSubject().getSubjectName().equals("C. ARTS"))&&(c.getStudentClass().equals("Basic3B"))){
     m.addAttribute("msg", AS.removeAssessment(c));
     System.out.println("Removed"+ AS.removeAssessment(c));
   }
    if(c.getSubject().getSubjectName()
            .equals("Ghanian Language")&&(c.getStudentClass().equals("Basic3B"))){
    m.addAttribute("msg", AS.removeAssessment(c));
      System.out.println("Removed"+ AS.removeAssessment(c));   
   }
   });
  };r.run();
   return "index";
   }
   @GetMapping("/getllAssessmentForSchool")
   @ResponseBody public List<Assessment> assessment(){
   Map<String ,List<Assessment>>assessment=new HashMap<>();
   AS.FindAll().forEach(C->{
      System.out.println(C.toString());
   });
   return AS.FindAll();
   }
   
   
  
   @GetMapping("RemoveAssessment/{subjectName}/{studentID}/{termId}")
   public @ResponseBody String removeAssesment(@PathVariable("subjectName")String subjectName
           ,@PathVariable("termId")Long termId,
           
           @PathVariable("studentID")String studentId){
       System.out.println(subjectName+"<----SubjectName    termID---->"+termId+ " studentId---->"+studentId);
      Runnable r1=()->{
       try{
           System.out.println( SS.findStudent(studentId).getAssessment()
                   .stream().map(Assessment::getSubject).collect(Collectors.toList()));
       Optional<Assessment>assessment=
           SS.findStudent(studentId).getAssessment()
                   .stream().filter(c->c.getSubject()
                           .getSubjectName().equals(subjectName)).findFirst();
       if(assessment.isPresent()){{
           System.out.println(assessment);
       AS.removeAssessment(   assessment.get()); 
       }
       }else{
       System.out.println("not found please try again");
       }
      }catch(Exception exx){
      System.out.println(exx.toString());
      }
      };
      r1.run();
    return "check from console";
   }
   
  @GetMapping("RemoveAssessment/{termId}")
   public @ResponseBody String removeAssesment(
           @PathVariable("termId")Long termId){
       Runnable r1=()->{
       try{
         TS.findTermById(termId).get().getAssessment().forEach(c->{
         AS.removeAssessment(c);
         });
      }catch(Exception exx){
      System.out.println(exx.toString());
      }
      };
      r1.run();
    return "check from console";
   }
   @GetMapping("RemoveAssessmentForClass/{klass}/{termId}")
   public @ResponseBody String removeAssesment(@PathVariable("klass")String klass
           ,@PathVariable("termId")Long termId){
try{
      KS.FindKlass(klass).getAssessment().stream().filter(c->c.getTerm().getId()==termId).forEach(a->{
  System.out.println("Found assessment");     System.out.println(AS.removeAssessment(a));
      });
      
     return "check from console"+" /// success";    
     }catch(Exception exx){
      System.out.println(exx.toString());
      return "check from console "+exx;
     }  
  
   }
   
   @GetMapping("/RemoveAssessmentStudent/{id}")
   public String removeAssessment(@PathVariable("id") Long id, Model m){
   try{
       Assessment as = AS.findAssessmentById(id).get();
    System.out.println( AS.removeAssessment(as));
    
    return "redirect:/StudentAssessment/Assessment/"+as.getStudent().getStudentId();
    
   }catch (Exception ex){
   
   m.addAttribute("msg", ex.toString());
   return "index";
   }
   
   
   }
   public List<Term>AssessmentTermsForStudent(Student stud){
   if (SS.FindstudentById(stud).isPresent()){
   List<Term>terms =SS.FindstudentById(stud).get().getAssessment().stream().map(Assessment::getTerm).collect(Collectors.toList());
   return terms;
   } 
   return  new ArrayList<>();
   }
}

