/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.Receipt;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.ReceiptService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.role;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author enock
 */
@Controller
@RequestMapping("/FinancePortal/")
public class PaymentController {
    @Autowired
    private StudentService SS;
    @Autowired
    private PaymentService PS;
    @Autowired
  private TermService TS;
  @Autowired
  private BillService BS;
   @Autowired
  private klassService KS;
  private @Autowired UserService  US;
 private @Autowired ReceiptService RS;
  @GetMapping("/GetOtherFeeForm/{PaymentType}/{klassName}") 
  public String GetOtherFeeForm(Model model,@PathVariable("PaymentType")String PaymentType
  ,@PathVariable("klassName")String klassName
  ){
  
  model.addAttribute("OtherPaymentForm",PS.StudentOtherFeePayment(PaymentType, SS.GetStudents_In_A_Particular_Class(KS.FindKlass(klassName))));
  return "OtherFeePayment";
  }
  
  //Other fee like claases, ermmm baako yi koraa desen canteen ne adeade
  @PostMapping("/SaveOtherFee")
   public String OtherFeePaymentForm(Model model,List<Payment>fees,Principal pp){
   for(Payment fee:fees){
   Payment p=new Payment(); p.setAmount(fee.getAmount());p.setPaymentType(fee.getPaymentType());p.setTerm(TS.getCurrentTerm());
   model.addAttribute("msg",PS.SaveSchoolFeesPayment(p, pp));
   }users user=US.FindUserById(pp.getName());
    for(role r:user.roles){
    if(r.getName().equals("Teacher")){
    return "TeacherPortal";
    }else if(r.getName().equals("Admin")){
    return "index";
    }else{
    return "FinancePortal";
    }
    }
    return null;
   }
   
   
   
  @GetMapping("/FeePaymentForm/{id}")
  public String PaymentFeePaymentForm(Model model,@PathVariable("id")String id,Principal p){
      String paymentType ="NewStudent";
        if(PS.ShowStudentNewPayment(id,paymentType)==null){
        model.addAttribute("msg","please contact admin to set the current term");
     
        }else{
     model.addAttribute("payment",
             PS.ShowStudentNewPayment(id,paymentType));
        }
        
        model.addAttribute("id",id);
     
     model.addAttribute("user",p.getName());
     model.addAttribute("StudentDeatils",SS.findStudent(id).getFirstName()+"  "+SS.findStudent(id).getSurname());
     model.addAttribute("class",SS.findStudent(id).getStudentklass().getClassName());
     model.addAttribute("picturepath",SS.findStudent(id).getProductPicturePath());
    // model.addAttribute("balance",NSS.GetFees(TS.getCurrentTerm()));
     return "FinancePortal";

   }
   
  @GetMapping("/PaymentForm/{id}")
   public String ShowPaymentForm(Model model,@PathVariable("id")String id,
          Principal principal){
       String paymentType ="SchoolFees";
       Student stud =SS.findStudent(id);
       if(PS.ShowStudentNewPayment(id,paymentType)==null){
       model.addAttribute("msg","please contact admin to set the current term");
    
       }else{
    model.addAttribute("payment",PS.ShowStudentNewPayment(id,paymentType));
       }
       model.addAttribute("id",id);
    
    model.addAttribute("user",principal.getName());
    model.addAttribute("StudentDeatils",
            stud.getFirstName()+"  "+stud.getSurname());
    model.addAttribute("class",
            stud.getStudentklass().getClassName());
    model.addAttribute("picturepath",
            stud.getProductPicturePath());
    var balance =0.0;
    if (stud.getStudentBills()==null){

      }else{
       balance=(stud.getStudentBills()
               .stream().map(StudentBill::getFees)
               .map(SchoolFees::getAmount)
               .reduce(0.0,Double::sum))-
                ( stud.getPayments().stream()
                        .map(Payment::getAmount)
                        .reduce(0.0, Double::sum));
      }
    model.addAttribute("balance",
            balance);
   // model.addAttribute("OutStabalance",PS.OutStandingbalance(id));.stream()
   
    return "FinancePortal";
     } 
   
  @PostMapping("/SavePayment")
  public String savePayment(Model model,Payment payment,Principal p,@ModelAttribute("student")String student){
    users user=US.FindUserByUserName(p);
      payment.setUser(user);payment.setStudent(new Student(student));
      payment=PS.SaveSchoolFeesPayment(payment,p);
  //Receipt receipt =new Receipt(payment, LocalDate.now());
  Receipt   receipt = RS.saveReceipt(new Receipt(payment, payment.getStudent(),LocalDate.now().toString()));
  
  //  model.addAttribute("msg",);
  
  
  
 // model.addAttribute("StudentList",SS.GetStudents_In_A_Particular_Class(SS.findStudent(payment.getStudent().getStudentId()).getStudentklass()));
  
  return "redir"
          + "ect:GetStudentReceipt/"+payment.getStudent().getStudentId()
          +"/"+payment.getAmount()+"/"+receipt.getId();
  }
  
  @GetMapping("/GetStudentPayments/{id}")
  public String GetStudentPayments(Model model ,@PathVariable("id")String id){
      Student stud =SS.findStudent(id);
              //stud.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount)
              //                    .reduce(0.0,Double::sum);
     model.addAttribute("studentPayments",PS.payments(id));
     model.addAttribute("StudentBills",stud.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount)
     );
     model.addAttribute("stud",stud);
    model.addAttribute("class",stud.getStudentklass().getClassName());
   model.addAttribute("balance",BS.totalBillAmount(stud));
   model.addAttribute("paymentt",PS.getTotalPaymentForStudent(stud));
   
     return "FinancePortal";
   }
   
  @GetMapping("/StudentPaymentList/{ClassId}")
  public String StudentPaymentListBasedOnClass(Model model,@PathVariable ("ClassId")String ClassId)
  {
  model.addAttribute("KlassPayments",
          PS.GetStudentTermlyPayments(KS.FindKlass(ClassId),TS.getCurrentTerm()));
  model.addAttribute("Terms", TS.terms());
   model.addAttribute("class",ClassId);
   model.addAttribute("TotalStudents",SS.GetStudents_In_A_Particular_Class(KS.FindKlass(ClassId)).size() );
  
   return "FinancePortal";
  }
  
 @GetMapping("/Payments/")
 public String DailyPayments( @RequestParam("sdate") String sdate,
         @RequestParam("Edate")String Edate, Model model){
 model.addAttribute("payments", PS.AllPayments()
         .stream()
         .filter(c->c.getDate()
                 .isBefore(LocalDate.parse(Edate))
                 ||c.getDate().isAfter(LocalDate.parse(sdate))));

 return "FinancePortal";
  }
 
 @GetMapping("/Defaulters/{classId}")
    public String DailyPayments( @PathVariable("classId")String classId,Model model,HttpServletRequest request){
   List<Student>students = new ArrayList<>();
   
   KS.FindKlass(classId).getStudents().stream().forEach(c->{
  double outcome =
          ( c.getStudentBills().stream().map(StudentBill::getFees)
                  .map(SchoolFees::getAmount).reduce(0.0,Double::sum))-
          (c.getPayments().stream().map(Payment::getAmount)
          .reduce(0.0, (b,o)->b+o));
  if(outcome<0){
  students.add(c);
  }
   });
   List<Payment>payments =new ArrayList<>();
   students.stream().forEach(c->{
       Payment p = new Payment();
  p.setStudent(c);
  p.setAmount(SS.FindstudentById(c)
          .get().getPayments().stream().map(Payment::getAmount).reduce(0.0,(v,g)->v+g));
  
  p.setRemaining(c.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount).reduce(0.0,Double::sum )-
                  p.getAmount());
            p.setPaymentType("fees");
   payments.add(p);
   }); 
       model.addAttribute("defaulters", payments
       );
  request.getSession().setAttribute("defaulterss", payments);
     
  return "FinancePortal";
   }
   
 @GetMapping("/Defaulters/")
    public String DailyPayments( Model model,HttpServletRequest request){
   List<Student>students = new ArrayList<>();
   
   SS.AALLStudents().stream().forEach(c->{
  double outcome =c.getPayments().stream().map(Payment::getAmount).reduce(0.0, (b,o)->b+o);

  if(outcome<0){
  students.add(c);
  }
   });
   List<Payment>payments =new ArrayList<>();
   students.stream().forEach(c->{
       Payment p = new Payment();
       Student stud =SS.FindstudentById(c).get();
  p.setStudent(c);p.setAmount(stud.getPayments().stream().map(Payment::getAmount).reduce(0.0,(v,g)->v+g));

       p.setRemaining(c.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount).reduce(0.0,Double::sum )-
               p.getAmount());   p.setPaymentType("fees");
   payments.add(p);
   }); 
       model.addAttribute("defaulters", payments );
   model.addAttribute("Amout_not_Paid", 
           payments.stream().map(Payment::getRemaining).reduce(0.0,(b,o)->b+o) );
   request.getSession().setAttribute("defaulterss", payments);
  request.getSession().setAttribute("Amout_not_Paid", 
           payments.stream().map(Payment::getRemaining).reduce(0.0,(b,o)->b+o) );
  
   
  return "index";
  }
  @GetMapping("/PriDefaulters/")
    public String pritDefaulters( Model model,HttpServletRequest request){
     try{
        List<Payment>payments =(List<Payment>) request.getSession().getAttribute("defaulterss");
    double amtT = payments.stream().mapToDouble(Payment::getRemaining).sum();
      model.addAttribute("defaulters", payments );
    model.addAttribute("Amout_not_Paid", amtT);
     }catch(Exception ex){
      model.addAttribute("msg", " SORRY...... "+ex.getMessage());
      return "index";
     }
    return "Receipt";
   }
 }
     
