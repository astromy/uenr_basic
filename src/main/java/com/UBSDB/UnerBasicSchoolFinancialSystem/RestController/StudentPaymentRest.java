/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.RestController;

import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Finance.FinanceService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Finance.SchoolStatisticsModel;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.Receipt;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.ReceiptService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SMS.SmsService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lys
 */
@RestController
public class StudentPaymentRest {
    private @Autowired StudentService SS;private @Autowired PaymentService PS;@Autowired ReceiptService RS;@Autowired private TermService TS;
     private @Autowired FinanceService FS;
     private @Autowired SmsService sms;

    @GetMapping("/GetStudentDetails/{ReferenceId}")
    public ResponseEntity<student> getStudentDetails(@PathVariable("ReferenceId")String ReferenceId)throws Exception{
        System.out.println(ReferenceId);
        try{
             System.out.println(ReferenceId); 
        Optional   <Student> TempStudent =SS.findOptionalStudentByReference(ReferenceId);
      System.out.println(TempStudent ); 
        if (TempStudent.isPresent()){
            TempStudent =SS.FindstudentById(new Student (ReferenceId));
            System.out.println(TempStudent.get().getStudentId());
            student stud = new student(TempStudent.get());
            // stud.setFirstName(ReferenceId);
            System.out.println(stud.toString());
            return ResponseEntity.ok(stud);

        }
        else {
            System.out.println(TempStudent.get().getStudentId());
            return  ResponseEntity.ok(null) ;
        }
       }catch (Exception ex){
       System.out.println(ex.toString());
          return  ResponseEntity.ok(null);
       
       }
        
    }
    
    @GetMapping("/api/allStudents/")
    public List<student> getStudentPayment(){
            List<student>stud=new ArrayList<>();
        try{

    Runnable r1=()->{
    SS.AALLStudents().stream().filter(c->c.getStudentStatus().equalsIgnoreCase("in School")).forEach(c->{
    stud.add(new student(c));
    
    });
    };
    r1.run();
        }catch(Exception ex){
        
        }
    return stud;
    }
    
    @PostMapping("/StudentPayment")
    public ResponseEntity<PaymentResponse> StudentPaymentResponse(PaymentResponse pR) throws Exception {
        String Studid=pR.getStudentId();
        pR.setAmount(pR.getAmount());
        pR.setPaymentDateTime(LocalDate.now()+"");
        pR.setTransactionId(pR.hashCode()+"_");
        
        System.out.print(Studid+" ----------->student Id "+pR.getPaymentDateTime());
        pR.setBankName("MicroFinanace");
   Payment TempPayment =new Payment(pR.getBankName(), pR.getPaymentDescription(), 
          LocalDate.now().toString(), "Fees", TS.getCurrentTerm(),Double.parseDouble(pR.getAmount()), LocalDate.now(), 0, 
          SS.findStudent(pR.getStudentId()));
    TempPayment.setTransactionSpecialId(pR.getTransactionId());
 System.out.print(TempPayment.getBank()+" ----------->payment");
    System.out.println(pR.toString());
     Receipt r= new Receipt (); 
    try{
     //users user=US.FindUserByUserName(p);transactionSpecialId
       
   // Receipt receipt =new Receipt(TempPayment, LocalDate.now())kl;
 
  TempPayment=PS.SaveSchoolFeesPaymentFromBank(TempPayment);
 r=   RS.saveReceipt(new Receipt(TempPayment, SS.findStudent(TempPayment.getStudent().getStudentId()),
            LocalDate.now().toString()));
 try{
     System.out.println(r.getStud().getParentNumber());
 System.out.println( sms.sendAdmissionMessage(r.getStud()));
 }catch (Exception ex){
 ex.printStackTrace();
 }
    return ResponseEntity.ok(pR);
    }catch (Exception ex){
       ex.printStackTrace();
       
        System.out.print(ex);

    }
  
     
          return   ResponseEntity.ok(pR);
    }
    @GetMapping("/getPaymentResponse")
    public PaymentResponse getPaymentResponse(){
    
    return new PaymentResponse();
    }
    
      @GetMapping("/searchStudent/{name}")
    public ResponseEntity <List<StudentResponse>>getStudents(@PathVariable ("name")String seacrhInput) throws Exception{
    try{
         
        //List<StudentResponse>stud;
         List<Student>stud=SS.SearchStudent(seacrhInput);
   
    return ResponseEntity.ok(stud.stream().map(Student::getStudentResponse).collect(Collectors.toList()));
    
    
   }catch (Exception ex){
   ex.printStackTrace();
    return ResponseEntity.ok(new ArrayList<StudentResponse>());
    
   }
    
    }
    
    @GetMapping("dashboard")
    public @ResponseBody
    SchoolStatisticsModel ShowFinancePortalStatistics(Model model, Principal principal,HttpSession session) throws Exception {
        try{

        model.addAttribute("user", principal.getName());
    final   SchoolStatisticsModel sc = new SchoolStatisticsModel();
    Runnable r4 = ()->{
        System.out.println("start Time "+ LocalTime.now());
          SchoolStatisticsModel sc1=FS.getSchoolStaticsModel();
               sc.setJunior_Staff(sc1.getJunior_Staff());
               sc.setNonStaff(sc1.getNonStaff());sc.setSeniorMember(sc1.getSeniorMember());
               sc.setTotalAmountPaid(sc1.getTotalAmountPaid());sc.setTotalStudents(sc1.getTotalStudents());
               sc.setTotalStudentsCreche(sc1.getTotalStudentsCreche()); 
               sc.setTotalStudentsJuniorHigh(sc1.getTotalStudentsJuniorHigh());
               sc.setTotalStudentsKindagarten(sc1.getTotalStudentsKindagarten());
               sc.setTotalStudentsLowerPrimary(sc1.getTotalStudentsLowerPrimary());
               sc.setTotalStudentsNursery(sc1.getTotalStudentsNursery());sc.setTotalStudentsUpperPrimary(sc1.getTotalStudentsUpperPrimary());
                sc.setTotalAmountPaid(sc1.getTotalAmountPaid());
        System.out.println("Done Loading Data "+sc.toString());
         System.out.println("stop Time "+ LocalTime.now());
       


            } ;
            Thread t3 = new Thread(r4);
            t3.run();
            return  sc;
        }catch (Exception ex){
            ex.printStackTrace();
        return new  SchoolStatisticsModel();
        }
    }}
