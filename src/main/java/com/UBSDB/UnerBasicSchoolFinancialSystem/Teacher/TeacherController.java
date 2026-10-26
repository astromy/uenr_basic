/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PROMOTION.promote;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PROMOTION.promoteService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SMS.SmsService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectSeeder;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubjectService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.MockDetailsService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author enock
 */

@Slf4j
@Controller
public class TeacherController {
  @Autowired
    private TeacherService1 TS;
    
    @Autowired
    private TermService Ts;
     private AssessmentService AS;
    @Autowired
    private SubjectService Ss;
     @Autowired
    private klassService KS;
          @Autowired
private StudentService StudS;
        @Autowired
        private SubjectSeeder SS;
             @Autowired
        private UserService US;
              @Autowired
        private TeachrKlassSubjectService TKSs;
              private   @Autowired PaymentService ps;
               private   @Autowired promoteService prs;
               private @Autowired MockDetailsService MDS;
               private @Autowired SmsService sms;
               @GetMapping("Teachergeneral")
    public String generalInfo( Model m){
        try{
        m.addAttribute("generalTeach", true);
            
        }catch (Exception ex){
     m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
        }
    return "index";
    }
    @RequestMapping("/TeacherForm")
    public String ShowTeacherForm(Principal principal,Model model){
    model.addAttribute("teacher",new Teacher());
    model.addAttribute("TeacherForm",true);
    users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
        return "index";
    }
    @RequestMapping("/ShowUpdateTeacherForm/{id}")
    public String ShowUpdateTeacherForm(Principal principal,@PathVariable("id") Long id,Model model){
     model.addAttribute("Updateteacher",TS.findTeacher(id));
      
    model.addAttribute("UpdateTeacherForm",true);
   
        return "index";
    }
    
    @RequestMapping ("/Demote")
    public String DemoteStudents () throws Exception{
    try{
    
       // prs.revertStudentPromotionList();
      return "doing .....";
    }catch (Exception ex){
    
    return "Error"+ex;
    }

   }
    
    @RequestMapping("/Admin/home")
    public String populate(Model model ) throws Exception {
       System.out.print("welcome page .......... Admin");
      
        model.addAttribute("dashboard" , true);
   
         if(Ts.getCurrentTerm()==null){
            model.addAttribute("msg","Please Contact Admin To Set Current Term");
     
       return "index";
      }    
    if(Ss.Allsubjects().isEmpty()){
        if(KS.AllKlass().isEmpty()){
    return "redirect:/klass";
    }
   //model.addAttribute("msg", SS.SeedAllData());
    }
      
      
   return "index";
    }
    
    @PostMapping("/saveTeacher")
    public String saveTeacher(Principal principal,Model model,Teacher teacher) throws Exception{
        try {
       // teacher.setStaffId("KCPT"+TS.listOfTeachers().size()+1);
        teacher.setTeacherStatus("True");
 
        model.addAttribute("msg",TS.addTeacher(teacher));
        //sms.sendSMS_To_Teacher(teacher);
        users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }}
    return "index";
    
    }catch (Exception ex){
        ex.printStackTrace();
        model.addAttribute("msg","contact admin ");
        
  return "index";
}
}
       
    @PostMapping("/FindTeacher/{id}")
    public String FindTeacher(Principal principal,@PathVariable("id") Long id,Model model,Teacher teacher){
        teacher.setTeacherStatus("True");
        model.addAttribute("msg",TS.addTeacher(teacher));
        users user=US.FindUserById(principal.getName());
        model.addAttribute("teacher",TS.findTeacher(id));
        model.addAttribute("TeacherForm",true);
   
    return "index";
    }
    @PostMapping("/UpdateTeacher")
    public String UpdateTeacher(Principal p,Teacher t,Model m){
    m.addAttribute("msg",TS.UpdateTeacher(t));
    return "index";
    }
    @GetMapping("TeacherDatils/{TeacherId}")
    public String getTeacherDetails(Model model,@PathVariable("TeacherId")Long TeacherId,Principal principal)
    {
       model.addAttribute("TeacherId",TeacherId);
    model.addAttribute("Teacher",TS.findTeacher(TeacherId));
    model.addAttribute("TeacherKlass",TS.Teacher_Assigned_Klasses(TeacherId).stream().map(klass::getClassName).toList());
   users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
    return "index";
    }
    
    @GetMapping("TeacherSubjects/{TeacherId}/{ClassId}")
    public String getTeacherSubjets(Model model,@PathVariable("TeacherId")Long TeacherId,@PathVariable("ClassId")String ClassId,Principal principal)
    {
    model.addAttribute("Teacher",TS.findTeacher(TeacherId));
   model.addAttribute("TeacherKlass",TS.Teacher_Assigned_Klasses(TeacherId).stream().map(klass::getClassName).toList());
     model.addAttribute("TeacherId",TeacherId);
   
    model.addAttribute("subjects",TS.getSubjectsThoughtByATeacherInAClass(TeacherId, ClassId));
    
     users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
    
    return "index";
    }
    @GetMapping("Teacher/RemoveSubject/{TeacherId}/{SubjectId}")
    
    public String removeSubject(Model model,Principal principal,@PathVariable("TeacherId")Long TeacherId,@PathVariable("SubjectId")Long SubjectId){
    model.addAttribute("msg",TS.removeSubjectFromTeacherSubjects(TeacherId, SubjectId));
    
    users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
    return "index";
    }
    
    @GetMapping("/AssignClassToTeacher/{TeacherId}")
    public String AssignClassToTeacher(Principal principal,Model model,@PathVariable("TeacherId")Long TeacherId){
    model.addAttribute("Assignklasses",KS.AllKlass());
    model.addAttribute("Name",TS.getTeacherFullName(TeacherId));
    model.addAttribute("TeacherId",TeacherId);
    users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
    return "index";
    }
    
    @PostMapping("/AssignClassAndSubjectToTeacher/{TeacherId}/{ClassId}")
    public String AssignSubjectAndKlassToTeacher(Principal principal,@PathVariable("TeacherId") Long TeacherId,
      @RequestParam("subjectsAssign")List <Long>subjectsAssign,@PathVariable("ClassId")String ClassId,Model model) throws Exception
  
    {  
        try {
        subjectsAssign.forEach(c->{

     var output=   TS.AssignTeacherToClass(TeacherId, ClassId, c);
        log.info("Assigning subjects=> "+output+" : "+ ClassId+": c ");
        });
          //  List <Integer>Sujectsdata=(List<Integer>)(subjectsAssign); 
    
   // System.out.print(subjectsAssign.toString() + "checking subjects");
  
    //model.addAttribute("msg",TS.AssignTeacherToClass(TeacherId, ClassId, SubjectId));
      model.addAttribute("msg","Successfully assigned ");
       
           // model.addAttribute("SubjectsAssigned",TS.SubjectThoughtByTeachersInAParticularClass(ClassId));
        } catch (Exception ex) {
            model.addAttribute("err"," Error contact developer ");
      
            ex.printStackTrace();
            
            Logger.getLogger(TeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
    
    return "index";
        
    }
    @GetMapping("/subjectsToBeAssigned/{ClassId}/{TeacherId}")
    public String Show_Subjects_Available_To_BeAssigned_To_Techears_Based_On_Class
        (Principal principal,Model model,@PathVariable("ClassId") String ClassId,@PathVariable("TeacherId")Long TeacherId) throws Exception{
            
            
    model.addAttribute("ClassName",ClassId);
    model.addAttribute("Subjects",TS.Subjecs_Not_Assigned_To_A_Teacher_In_A_Particular_Class(KS.FindKlass(ClassId)));
  
          model.addAttribute("Name",TS.getTeacherFullName(TeacherId));
    model.addAttribute("TeacherId",TeacherId);
    
    users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
    return "index";
    }
        @GetMapping ("/deleteAllTKS")
        public String deleteAllTKS(){
        try {
        
       // TKSs.deleteAllTKS();
        return "done";
        }catch (Exception ex){
       return  ex.toString();
        }
        }
        @GetMapping("/ClassTeachers/{ClassId}")
        public String Class_Teachers(Principal principal,Model model,@PathVariable("ClassId")String ClassId) throws Exception{
         try{   
        model.addAttribute("TKS",TS.SubjectThoughtByTeachersInAParticularClass(ClassId));
        if(TKSs.getClassMaster(ClassId)==null){
        model.addAttribute("msg","Please assign a class Teacher To This Class");
       model.addAttribute("AddTeacherBtn",true);
        }else{
        model.addAttribute("msg","Class Teacher: "+TKSs.getClassMaster(ClassId).getTeacher().getLastName()+" "+TKSs.getClassMaster(ClassId).getTeacher().getFirstName()
       +" Class Name:"+ClassId 
        );
        model.addAttribute("ClassId",ClassId);model.addAttribute("TeacherId",TKSs.getClassMaster(ClassId).getTeacher());
        
        }
        
        users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }}catch (Exception ex){
         model.addAttribute("err","contact admin or developer");
   
        }
        
        return "index";
        }
        
         @GetMapping("/ClassTeachers")
        public String Classes(Principal principal,Model model) throws Exception{
         users user=US.FindUserById(principal.getName());
            try{
        model.addAttribute("ClassTeacher",KS.AllKlass());
       
          }catch (Exception ex){
         model.addAttribute("err","contact admin or developer");
   
        }
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
        
        return "index";
        }
        
    @GetMapping("/FindAllTeachers")
    public String Allteachers(Principal principal,Model model) throws Exception{
        try{
            var output =TS.listOfTeachers().stream().map(Teacher::getResponse).toList();
    model.addAttribute("teachersList",output);
	      System.out.println(output.toString());
    users user=US.FindUserById(principal.getName());
    for(role r:user.roles){
    if(r.getName().equals("HeadTeacher")){
    return "HeadTeacherPortal";
    }
    }
      }catch (Exception ex){
         model.addAttribute("err","contact admin or developer");
   
        }
    return "index";
    }
    
    @GetMapping("/TeacherPortal/Detail/")
    public String TeacherPortalDetail(Model model,Principal principal) throws Exception{
        try{
        Long TeacherId=TS.findbyStaffId(principal.getName()).getTeacherId();
    model.addAttribute("TeacherId",TeacherId);
    model.addAttribute("Teacher",TS.findTeacher(TeacherId));
         }catch (Exception ex){
         model.addAttribute("err","contact admin or developer");
   
        }
    return "TeacherPortal";
    
    }
    @GetMapping("/TeacherPortal/")
    public String TeacherPortal(Model model,Principal principal) throws Exception{
        try{
        Teacher t =TS.findbyStaffId(principal.getName());
        t=TS.findTeacher(t.getTeacherId());
      Long TeacherId=t.getTeacherId();
      model.addAttribute("TeacherId",TeacherId);
    model.addAttribute("Teacher",t);
    System.out.println(t.getTkss().toString());
    if(t.getTkss().stream().filter(c->c.getKlas().getClassName()
            .equalsIgnoreCase("JHS3A")).findFirst().isPresent()
            &&!MDS.findAllMocksByDate(LocalDate.now()).isEmpty()){
    
 
     model.addAttribute("LinkMock", true);
   
    }
    //Giving Teachers Access to save student daily classess fee
    if(t.getTeacherStatus().equals("NotClassTeacher")){
    
    }else{
        model.addAttribute("FeePayment",true); 
        model.addAttribute ("assignedClass",t.getClassTeacher());
        model.addAttribute("Feeklass",TS.findbyStaffId(principal.getName()).getClassTeacher());
    }
      }catch (Exception ex){
         model.addAttribute("err","contact admin or developer");
   
        }
    return "TeacherPortal";
    
    }
    ///TeacherPortal/GetAssessmentExcelFil
     @GetMapping("/TeacherPortal/mockgeneralTeacherPortal")
    public String mockgeneralTeacherPortal( Model m){
    try{
    
      m.addAttribute("LinkMocks",
              MDS.findAllMocksByDate(LocalDate.now()));
    
    }catch (Exception ex){
     m.addAttribute("err", "PLEASE CONTACT ADMIN");
      System.out.println(ex.toString());
        }
    return "TeacherPortal";
    }
     @GetMapping("/TeacherPortal/Classes/")
    public String TeacherPortalClasses(Model model,Principal principal){
        try{
        Teacher Teacher=TS.findbyStaffId(principal.getName());
       Long TeacherId=Teacher.getTeacherId();
     model.addAttribute("TeacherId",TeacherId);
     model.addAttribute("TeacherKlass",TS.Teacher_Assigned_Klasses(TeacherId));
    if(Teacher.getClassTeacher().equals("NotClassTeacher")){
    
    }else{
     model.addAttribute("Attendance",true);
     model.addAttribute("ClassId",Teacher.getClassTeacher());
             }
      }catch (Exception ex){
         model.addAttribute("err","contact admin or developer");
   
        }
     return "TeacherPortal";
    
    }
    
    
    @GetMapping("/TeacherPortal/Students/{ClassId}")
    public String TeacherPortalStudents(Model model, @PathVariable("ClassId")String ClassId,Principal principal) throws Exception{
        try{
      Long TeacherId=TS.findbyStaffId(principal.getName()).getTeacherId();
      model.addAttribute("TeacherId",TeacherId);
    model.addAttribute("StudentList",StudS.GetStudents_In_A_Particular_Class(KS.FindKlass(ClassId)));
    model.addAttribute("ClassId",ClassId);
        }catch (Exception ex){
         model.addAttribute("err","contact admin or developer");
   
        }
    return "TeacherPortal";
    }
    
    @GetMapping("/TeacherPortal/Subject/{ClassId}")
    public String TeacherPortalSubjects(Model model,
    @PathVariable("ClassId")String ClassId,Principal principal){
       Long TeacherId=TS.findbyStaffId(principal.getName()).getTeacherId();
      model.addAttribute("TeacherId",TeacherId);
     if(msg(model)!=null){
     model.addAttribute("TeacherSubjects",TS.getSubjectsThoughtByATeacherInAClass(TeacherId, ClassId));
    model.addAttribute("ClassId",ClassId);
   
     }else{
     msg( model);
     }
     return "TeacherPortal";
    }
    
   public String msg(Model model){
   if(Ts.getCurrentTerm()==null){
   model.addAttribute("msg","please contact admin to set term");
   return null;
   }
   return "done";
   } 
}
