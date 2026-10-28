/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Excel;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance.Attendance;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance.AttendanceService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.CourseService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.Receipt;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.ReceiptService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFeesService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.TeacherService1;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment.CacheAssessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment.CacheAssessmentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.MockDetailsService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.mockDetails;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author enock
 */
@Controller
@Slf4j
public class ExcelController {
    @Autowired
    ExcelServices SBFES;
    @Autowired
   private TermService TS;
    @Autowired private TeacherService1 tS;
     @Autowired
   private AssessmentService AS;
     @Autowired
        private CacheAssessmentService CAS;
     @Autowired
    private StudentService SS;@Autowired AttendanceService AtS;
     @Autowired private klassService KS;private @Autowired TeacherService1 ts;
           @Autowired private AttendanceExcelSheet AES;
           @Autowired private DownloadStudentAssessmentData ASAD;
     private @Autowired MockDetailsService MDS;    private @Autowired MockService MS;
       private @Autowired SchoolFeesService SFS;  
       private @Autowired PaymentService PS;
          private @Autowired ReceiptService RS;
   @Autowired
   private CourseService courseService;
   @Autowired
   private CourseExcelSheet courseExcelSheet;
   
   public ExcelController(Map<String, List<CacheAssessment>> assess) {
        this.assess = assess;
    }
  
           
           private String mgs;
   private   Map<String,List< CacheAssessment>> assess;

   
   
    public String getMgs() {
        return mgs;
    }

    public void setMgs(String mgs) {
        this.mgs = mgs;
    }
   
    @GetMapping("/SelectExcelFileToUpload")
    public String SelectExcelFileToUpload(Model model){
model.addAttribute("SelectExcelFileToUpload",true);
return "index";
 }
    
    @GetMapping("/ShowExcelForm")
    public String ShowExcelForm(Model model){
    model.addAttribute("excel",true);
    return "index";
    }
    
    @PostMapping("/GetFile")
    public String uploadExcelFile(Model model ,@RequestParam("file")MultipartFile file,HttpServletRequest request) throws IOException{
    model.addAttribute("StudentClasses",SBFES.ClassNames(file));
    request.getSession().setAttribute("file", file);
    // System.out.println(SBFES.ClassNames(file).toString());
    return "index";
    }
    
   @GetMapping("TeacherPortal/uploadExcel/")
   public String  TeacherUpload(Model model){
      
        model.addAttribute("AcademicYearExcel","please contact admin to add term");
       model.addAttribute("TermExcel",TS.getCurrentTerm());
     model.addAttribute("SubjectId","");
    model.addAttribute("TeacherId","");
  return "TeacherPortal";
} 
  
    @GetMapping("/TeacherPortal/uploadMockExcel/{MockId}")
   public String  TeacherUploadMock(Model model,@PathVariable("MockId")Long MockId,Principal p){
         Long teacherId=tS.findbyStaffId(p.getName()).getTeacherId();
       // model.addAttribute("AcademicYearExcel","please contact admin to add term");
       model.addAttribute("MockExcelTerm",TS.getCurrentTerm());
       model.addAttribute("Mock",MDS.findMockDetails(MockId).get() );
     model.addAttribute("SubjectId","");
    model.addAttribute("TeacherId",teacherId);
  return "TeacherPortal";
} 
   
   
   
   
   
   @PostMapping("TeacherPortal/GetAssessmentExcelFile")
   public String GetAssessmentExcelFile(Model model,@RequestParam("file")MultipartFile file,
           HttpServletRequest request,Principal p) throws IOException{
       Long teacherId=tS.findbyStaffId(p.getName()).getTeacherId();
       //System.out.println(teacherId);
     System.out.println("Assessment excel file ...>"+file.getOriginalFilename()+".....>name"+file.getInputStream().toString());
     if(file.getOriginalFilename().equals(p.getName()+"-"+TS.getCurrentTerm().getTermName()+".xlsx")){
     try{
         Map <String,List<CacheAssessment>>ass
             =SBFES.GetStudentAssessmentsFromExcelSheet
        (ts.findTeacher(teacherId),file, TS.getCurrentTerm());
        CAS.deleteAllCachedAssessment(ts.findTeacher(teacherId));  
     ass.forEach((a,b)->{
      CAS.saveAllCacheAssessment(b);
   b.forEach(c->{
        String message ="";
   if(c.getTotalTestScore()<=0||c.getExamScores()<=0||c.getTotalTestScore()>60){
    
  
      message=message+ "PLEASE SOME SCORE(s) WERE NOT ENTERED CORRECTLY FOR STUDENT:  "
                   + " "+c.getStudent().getStudentId()+" Name: "+c.getStudent().getFullName()+" "+c.getStudent().getFirstName()+" "+
                   c.getStudent().getLastName()+" Test Score "+
                   c.getTotalTestScore()+" exams score: "+c.getExamScores()+"__________________\n\n Please check the excel file and fix it please";
   }
   model.addAttribute("msg", message);
   });
   
   });  
     
     model.addAttribute("Assessment",ass);
    model.addAttribute("AssessmentButton",true);
    setMgs("");
    model.addAttribute("TeacherId",teacherId);
      
    
    
      //request.getSession().setAttribute("AssessmentExcelData"+teacherId, );
   
     }catch(Exception ec){
     
      model.addAttribute("msg",ec.getCause()+"_______->"+ec.getMessage());
     }} else{
      model.addAttribute("msg","wrong file uploaded correct file name is: "+p.getName()+"-"+TS.getCurrentTerm().getTermName()+".xlsx");
     }
     
       return "TeacherPortal";
}
   
   
   @PostMapping("TeacherPortal/GetMockExcelFile")
   public String GetMockExcelFile(Model model,@RequestParam("file")MultipartFile file,
           HttpServletRequest request,Principal p,@RequestParam("MockId")Long MockId) throws IOException{
       Long teacherId=tS.findbyStaffId(p.getName()).getTeacherId();
    String Name =(p.getName()+MDS.findMockDetails(MockId).get().getMockName()+"-"+TS.getCurrentTerm().getTermName()+".xlsx");
     System.out.println("Assessment excel file ...>"+file.getOriginalFilename()+".....>name"+file.getInputStream().toString()+ " Actual: "+Name);
     if(file.getOriginalFilename().equals(Name)){
     //PSC42superb mock-FirstTerm.xlsx
         try{  List  <String> message =new ArrayList<>();
         Map <String,List<CacheAssessment>>ass
             =SBFES.GetStudentMockFromExcelSheet
        (ts.findTeacher(teacherId),file, TS.getCurrentTerm());
        CAS.deleteAllCachedAssessment(ts.findTeacher(teacherId));  
     ass.forEach((a,b)->{
      
       CAS.saveAllCacheAssessment(b);
   b.forEach(c->{
    
   if(c.getTotalTestScore()<=0||c.getExamScores()<=0){
    message.add( "PLEASE SOME SCORE(s) WERE NOT ENTERED CORRECTLY FOR STUDENT: "
                   + " "+c.getStudent().getStudentId()+" Name: "+c.getStudent().getFullName()+" "+c.getStudent().getFirstName()+" "+
                   c.getStudent().getLastName()+" Test Score "+
                   c.getTotalTestScore()+" exams score: "+c.getExamScores()+"\n \n");
   }
   model.addAttribute("msg", message);
   });
   
   });  
    model.addAttribute("mockdetails",MockId); 
     model.addAttribute("Assessment",ass);
     model.addAttribute("MockButton",true);
    setMgs("");
    model.addAttribute("TeacherId",teacherId);
     
    
    
      //request.getSession().setAttribute("AssessmentExcelData"+teacherId, );
   
     }catch(Exception ec){
     
      model.addAttribute("msg",ec.getCause()+"_______->"+ec.getMessage());
     }} else{
      model.addAttribute("msg","wrong file uploaded correct file name is: "+Name);
     }
     
       return "TeacherPortal";
}
   
   
   
   @GetMapping("/TeacherPortal/UploadAttendanceFile")
   public String UploadAttendanceExcelForm(Model model) throws IOException{
          model.addAttribute("AttendanceUploadForm",true);
       return "TeacherPortal";
}
   
   
    @PostMapping("/TeacherPortal/GetAttendanceFile")
   public String GetAttendanceExcelFile(Model model,
           @RequestParam("file")MultipartFile file,HttpServletRequest request) throws IOException{
     List<Attendance>TermlyAttendance=SBFES.GetStudentAttendanceFromExcel(file, TS.getCurrentTerm());
    System.out.println(TS.getCurrentTerm()+"......"+TermlyAttendance.toString());
     
    model.addAttribute("Attendances",TermlyAttendance);
     request.getSession().setAttribute("Attendance",TermlyAttendance);
     return "TeacherPortal";
}
   @GetMapping("TeacherPortal/SaveAttendance")
   public String saveAttendanceIntoDb(Model model,HttpSession sess){
    List< Attendance> Att=(List< Attendance>)sess.getAttribute("Attendance");
 
   model.addAttribute("msg",AtS.saveStudentAssessment(Att));
   return "TeacherPortal";
   }
  
    @GetMapping("/TeacherPortal/SaveAssessment")
    public String saveAssessment(Model model,Principal p){
     try{
         Runnable r1=()->{
        Teacher t =tS.findbyStaffId(p.getName());
        t=tS.findTeacher(t.getTeacherId());
        Long teacherId=t.getTeacherId();
        String username = p.getName();

     //   Map<String,List< Assessment>> assess=(Map<String,List< Assessment>>)session.getAttribute("AssessmentExcelData"+teacherId);

     t.getCachedAssessments().stream().forEach(c->{
      var assessment =new Assessment();
      log.info(c.getStudentClass().getClassName());
         log.info(c.getTerm().getTermName());
      assessment= assessment.getAsessment(c);
       AS.saveAssessment(assessment, username);
      });
     // CAS.deleteAllCachedAssessment(t);
     model.addAttribute("msg",
             "Successfully saved assessment for"
                     + " "+t.getStaffId());
         };
         r1.run();
     }catch (Exception ex){
         System.out.println("Error ----> "+ex.toString());
       model.addAttribute("msg",
             "Contact Admin Unsuccessful Operation ");

     }


        return "TeacherPortal";
    }
   @GetMapping("/TeacherPortal/SaveMock/{id}")
   public String SaveMock(Model model,HttpSession session,Principal p,@PathVariable("id")Long id){
       
    try{
        Runnable r1=()->{
       Teacher t =tS.findbyStaffId(p.getName());
       t=tS.findTeacher(t.getTeacherId());
       Long teacherId=t.getTeacherId();
         
    //   Map<String,List< Assessment>> assess=(Map<String,List< Assessment>>)session.getAttribute("AssessmentExcelData"+teacherId);
     
    t.getCachedAssessments().stream().forEach(c->{
     
   MS.saveAssessment(new Mock(c,id));
   
    System.out.println();
    
     });
     CAS.deleteAllCachedAssessment(t);
    model.addAttribute("msg",
            "Successfully saved Mock for"
                    + " "+t.getStaffId());
        };
        r1.run();
    }catch (Exception ex){
        System.out.println("Error ----> "+ex.toString());
      model.addAttribute("msg",
            "Contact Admin Unsuccessful Operation ");
    
    }
     
 
       
   return "TeacherPortal";
   }
    
    
    @GetMapping("ShowStudentsDataExcelForm")
    public String ShowStudentsDataExcelForm(Model model){
     model.addAttribute("StudentExcelData", true);
        return "index";
    }
    @PostMapping("/GetStudentsDataExcelFile")
    public String upLoadStudentList(Model model ,@RequestParam("file")MultipartFile file,HttpResponse response) throws IOException{
    
    String msg=SBFES.saveStudentsFromExcelFile(file);
    model.addAttribute("msg",msg );
    model.addAttribute("StudentExcelData", true);
   
    return "index";
    }
    
    @PostMapping("/UploadStudentPayment")
    public String upLoadStudentPaymentList(Model model,Principal p ,@RequestParam("file")MultipartFile file
            ) throws IOException, Exception{
      List<String>rejected =new ArrayList<>();
      List <Payment>paymentss=new ArrayList<>();
        List<Payment> payments=SBFES.GetStudentsPaymentsFromExcelToDB(file);
        payments.forEach(c->{
            System.out.println("Checking the Students IDs"+c.getStudent().getStudentId());
        if(SS.findOptionalStudentByReference(c.getStudent().getStudentId()).isPresent()){
        c.setStudent(SS.findOptionalStudentByReference(c.getStudent().getStudentId()).get());
        paymentss.add(c);
        }else if(SS.FindstudentById(c.getStudent()).isPresent()){
         c.setStudent(SS.FindstudentById(c.getStudent()).get());
        paymentss.add(c);
        }else{
            rejected.add("Not uploaded into system "+c.getId());
       // payments.removeIf(d->d.getStudent().getStudentId()==c.getStudent().getStudentId());
        }
        System.out.println("Data very Good "+c.getAmount()+c.getPaymentTime()+c.getStudent().getStudentId());
        });
       paymentss.stream().forEach(c->{
           c.setTerm(TS.getCurrentTerm());
          c.setDate(LocalDate.now());
         
           c= PS.SaveSchoolFeesPayment(c,p); 
           Receipt r = new Receipt(c, c.getStudent(), c.getPaymentTime());
           RS.saveReceipt(r);
       });

  model.addAttribute("msg", 
          " Please these data was not uploaded check these data and reupload "+rejected);
           //payments.forEach(c->System.out.println(c.getPaymentTime()+" and "+c.getTransactionSpecialId()+c.getAmount())) ;
    model.addAttribute("Finance", true);
   
    return "FinancePortal";
    }
    
    @GetMapping("StudetBillingsExcel/{ClassName}")
    public String StudentBillingsExcel(Model model,HttpServletRequest request, @PathVariable("ClassName")String ClassName,HttpSession session) throws IOException{
   try{
        MultipartFile file=(MultipartFile) session.getAttribute("file");
    model.addAttribute("StudentClasses",file);
           List<ExceModell> StudentBillsExcel =       SBFES.getStudetBills(file);
        model.addAttribute("StudentBills",StudentBillsExcel);
        request.getSession().setAttribute("StudentBillsExcel", StudentBillsExcel);
        
   }catch(IOException ex){
    model.addAttribute("msg","Please error in some fields of file please check file or"
    +"session timed out please upload file again"+ex.toString()
    );
   
   }
    return "index";
    }
  @GetMapping("/saveExcelBill")
  public @ResponseBody List<Student> SaveStudentBillsExcel(HttpSession session){
     try{
       List<ExceModell> StudentBillsExcel =
               ( List<ExceModell> ) session.getAttribute("StudentBillsExcel");
         //System.out.println(StudentBillsExcel+ "file name uploading ");
     // List<ExceModell> StudentBillsExcel= SBFES.getStudetBills(file);
       List<Student>students=new ArrayList<>();
       
      StudentBillsExcel.forEach(c->{
     if(SS.findOptionalStudent(c.getStudentId()).isPresent()||SS.findOptionalStudentByReference(c.getStudentId()).isPresent()){
   SchoolFees sc =new SchoolFees();
     sc.setAmount(c.getOutstandingBalace());
     sc.setTerm(TS.findTermById(12212).get());
     sc.setFeeType("School_Fees");
    Student stud = null;
     if (SS.findOptionalStudent(c.getStudentId()).isPresent()){
   stud=SS.findOptionalStudent(c.getStudentId()).get();
    }else{
      stud=SS.findOptionalStudentByReference(c.getStudentId()).get();
     }
        sc.setKlass(stud.getStudentklass());
   
  stud.setParentCategory(c.getParentCategory());
    
     System.out.println(  SS.saveStudentCategory(stud).getParentCategory());
    SFS.SaveSchoolFees(sc, new Student(c.getStudentId())); 
  students.add(stud  ) ;
    }
      });
    
     
     return students;
     }catch (Exception ex){
         ex.printStackTrace();
  return new ArrayList<>();
  }
  
  
  }
    @PostMapping("/DownloadStudentAttendanceExcelSheet/")
    public void  DownloadStudentAttendanceExcelSheet(@RequestParam("ClassId")String ClassId
            ,@RequestParam("NumberOfDays")Integer NumberOfDays,HttpServletResponse response,Model model) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + ClassId + "_ Attendance_Sheet_" + "_" + TS.getCurrentTerm().getTermName() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Student> listUsers = SS.GetStudents_In_A_Particular_Class(KS.FindKlass(ClassId));
        AES = new AttendanceExcelSheet(listUsers);
        AES.setNumberOfDays(NumberOfDays.toString());
        model.addAttribute("msg", "Downloading............");
        AES.export(response, ClassId);
    }
          
    @GetMapping("/DownloadCourseExcelTemplate")
    public void downloadCourseExcelTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
       String headerKey = "Content-Disposition";
      String  headerValue = "attachment; filename=Course_Template.xlsx";
        response.setHeader(headerKey, headerValue);

        courseExcelSheet.export(response);
    }

    @PostMapping("/UploadCourseExcel")
    public String uploadCourseExcel(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            String result = SBFES.saveCoursesFromExcel(file);
            model.addAttribute("msg", result);
        } catch (Exception ex) {
            model.addAttribute("msg", "Error uploading course Excel: " + ex.getMessage());
            ex.printStackTrace();
        }
        model.addAttribute("CourseExcelUpload", true);
        return "index";
    }
     
     
     
     

    
    @GetMapping("/DownloadStudentAseessmentExcelSheet")
    public void  DownloadStudentAssessmentExcelSheet(
           HttpServletResponse response) throws IOException{
        try{
    response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=_ Assessment_Sheet_"+"_"+TS.getCurrentTerm().getTermName() + ".xlsx";
        response.setHeader(headerKey, headerValue);
     
        ASAD.setTKS(AS.FindAll());
      ASAD.export(response, headerValue, TS.getCurrentTerm());
        
    }catch(Exception ex){
        
        System.out.println(ex.toString());
    }}
   
    
  
    
    
    
    
    }
