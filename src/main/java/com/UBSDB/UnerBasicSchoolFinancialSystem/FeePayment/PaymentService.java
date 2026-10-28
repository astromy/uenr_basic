/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.Receipt;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.ReceiptService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;


import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.UserService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author enock
 */
@Service
public class PaymentService {
    @Autowired private PayementRepository PR;
   @Autowired
  private UserService US;
    @Autowired
    private StudentService SS;   @Autowired
    private ReceiptService RS;
 private   @Autowired TermService TS;private @Autowired BillService BS;
 
   public double getTotalFeessPaymentForStudent(Payment payment){
   double totalSchoolFeesPayment=0;
    for (Payment p:AllPayments()){
   if(p.getStudent().getStudentId().equals(payment.getStudent().getStudentId())){
        if(p.getPaymentType().equalsIgnoreCase("SchoolFees")){
    totalSchoolFeesPayment=totalSchoolFeesPayment+p.getAmount();
     }
   
   }}
   return totalSchoolFeesPayment;
    }
   public double getTotalFeessPaymentForStudent(Student stud ){
   double totalSchoolFeesPayment= SS.FindstudentById(stud).get().getPayments().stream().map(Payment::getAmount).reduce(0.0,(a,b)-> a+b);
   return totalSchoolFeesPayment;
    }
   public Optional<Double> getStudentPaymentBasedOnTerm(Student stud){
   return SS.FindstudentById(stud).get().getPayments().stream().
           filter(b->b.getTerm()
                   .getTermName().
                   equals(TS.getCurrentTerm().getTermName())).
           collect(Collectors.toList()).stream().map(Payment::getAmount).reduce(( g, t)->g+t);
   
   }
   
   public List<Payment>StudentOtherFeePayment(String PaymentType,List <Student>students){
  //  Fees fee=new Fees();fee.setFeeType(PaymentType);
    List<Payment>TempPayments=new ArrayList<>();
    students.stream().map(stud -> {
        Payment p=new Payment();
     //   p.setAmount(OtherFeePaymentHelper.GetPaymentFeeType(fee).GetFees(TS.getCurrentTerm()).getAmount());
        p.setPaymentType(PaymentType);
        p.setTerm(TS.getCurrentTerm());
        p.setStudent(stud);
            return p;
        }).forEachOrdered(p -> {
            TempPayments.add(p);
        });
   return TempPayments;
   }
   
   public double getTotalPaymentForStudent(Student stud){
   double totalSchoolFeesPayment=0;
    for (Payment p:AllPayments()){
   if(p.getStudent().getStudentId().equals(stud.getStudentId())){
       // if(p.getPaymentType().equalsIgnoreCase("SchoolFees")){
    totalSchoolFeesPayment=totalSchoolFeesPayment+p.getAmount();
    // }
   
   }}
   return totalSchoolFeesPayment;
    }
   public double getTotalPaymentForStudentBasedOnTerm(Term term,Student stud){
   double totalSchoolFeesPayment=0;
    for (Payment p:AllPayments()){
   if(p.getStudent().getStudentId().equals(stud.getStudentId())&&(Objects.equals(p.getTerm().getId(), term.getId()))){
        if(p.getPaymentType().equalsIgnoreCase("SchoolFees")){
    totalSchoolFeesPayment=totalSchoolFeesPayment+p.getAmount();
     }
   
   }}
   return totalSchoolFeesPayment;
    }
   
  // public Double OutStandingbalance (String studId){
  //  return     0.0;
 
 /// }
    
  public  List<Payment>payments(String studId){
  List<Payment>payment=new ArrayList<>();
  for(Payment pay:AllPayments()){
  if(pay.getStudent().getStudentId().equals(studId)){
  payment.add(pay);
  }
  
  }
  return payment;
  } 
  
  public Payment ShowStudentNewPayment(String studentId,String paymentType){
     if(GetPaymentTerm()!=null){
     Student student=SS.findStudent(studentId);
    Payment payment = new Payment();
    payment.setStudent(student);
    payment.setTerm(GetPaymentTerm());
    payment.setPaymentType(paymentType);
    return payment;
     }
   return null;
  }
  
  public List<Payment> GetPaymentForAcademicYear(Academic Year){
  List<Payment>payment=new ArrayList<>();
      for(Payment pay:AllPayments()){
      if(pay.getTerm().getAcademicyear().getAcademicYear().getYear()==Year.getAcademicYear().getYear())
      payment.add(pay);
      }
  
  return payment;
  }
   
  public List<Payment> GetPaymentsBasedOnTerm(Long term,String ClassId){
  List <Payment>Data=new ArrayList<>();
      for(Term t:TS.terms()){
   for(Payment p:AllPayments()){
       if((t.getId()==(term))&&(p.getStudent().
           getStudentklass().getClassName().equals(ClassId))){
           
  Data.add(p);
   }
   
   }
 
   }
   return Data;
  }
   //am sorry didnt know is in the controller till i finished
    public Term GetPaymentTerm(){
    for(Term t:TS.terms()){
       if(LocalDate.now().isBefore(LocalDate.parse(t.getStartDate()))||LocalDate.now().
               isAfter(LocalDate.parse(t.getEndDate()))==true){
       
       } else {
         return t;  
        }
    
    }
    return null;
    }
  
   
   public double GetBillForParticularStudent(List<Payment> payment,String id){
       double TotalBills=0;
     for(StudentBill bil :SS.findStudent(id).getStudentBills()){
        
      TotalBills=TotalBills+bil.getFees().getAmount();
    }
     for(Payment p:payment){
     TotalBills=TotalBills-p.getAmount();
     }
     return  TotalBills;
   }
   
   public double OutStandingbalance(String id){
  double amountB=BS.totalBillAmount(SS.findStudent(id))-BS.totalBillAmountxTermly(TS.getCurrentTerm(),SS.findStudent(id));
  double tempPBT=0.0;
  if(getStudentPaymentBasedOnTerm( SS.findStudent(id)).isPresent()){
  tempPBT =getStudentPaymentBasedOnTerm( SS.findStudent(id)).get();
  } 
  double amount=getTotalPaymentForStudent(SS.findStudent(id))- tempPBT;
     
       return amountB-amount;
     }
      
         
    
           
   public  Payment SaveSchoolFeesPayment(Payment payment,Principal p){
     try{
     payment.setUser( US.FindUserByUserName(p));
        payment.setDate(LocalDate.now());Student stud=SS.findStudent(payment.getStudent().getStudentId());
       payment.setRemaining(GetBalanceBasedOnTerm
        (payment.getTerm(),stud,payment.getAmount(),payment.getDiscount()));
       payment = PR.save(payment);
     return payment ;
    }catch(Exception ex){  System.out.println("error contact admin "+ex.getLocalizedMessage());
  return null;
   }
   
   }
     public  Payment SaveSchoolFeesPaymentFromBank(Payment payment){
     try{
       
      //payment.setUser( US.FindUserByUserName(p));
      // payment.set
        payment.setDate(LocalDate.now());
       payment.setRemaining(GetBalanceBasedOnTerm
        (payment.getTerm(),new Student(payment.getStudent().getStudentId()),payment.getAmount(),payment.getDiscount()));
          
       payment= PR.save(payment);
     return payment ;
    }catch(Exception ex){  System.out.println("error contact admin "+ex.getLocalizedMessage());
  return null;
   }
    
    }
   public List<Payment>AllPayments(){
    
    return PR.findAll();
    }
   
   public double GetBalanceBasedOnTerm(Term term,Student stud,double amount,double discont){
   double balance=0;
   double payments =0.0;
   double bills = 0.0;
   stud=SS.FindstudentById(stud).get();
   
  bills= stud.getStudentBills().stream()
          .map(StudentBill::getFees)
           .map(SchoolFees::getAmount)
          .reduce(0.0,(a,b)->a+b);
  payments=stud.getPayments().stream().map(Payment::getAmount).reduce(0.0,(a,b)->a+b);
  float Previousdiscount=stud.getPayments().stream().map(Payment::getDiscount).reduce(0.f,(f,g)->f+g);
  balance =((bills-(discont+Previousdiscount))-(amount+payments));
  
  return  balance;
   
   }
    
   public List< StudentTermlyPayment>GetStudentTermlyPayments(klass klass,Term term){
   List<StudentTermlyPayment>LisOfSTP=new ArrayList<>();
   for (Student stud:SS.GetStudents_In_A_Particular_Class(klass)){
       StudentTermlyPayment STP=new StudentTermlyPayment();
   
   STP.setStud(stud);
   STP.setTerm(term);
   STP.setTotalAmountPaid(getTotalPaymentForStudentBasedOnTerm(term,stud));
   STP.setTotalBills(BS.totalBillAmountxTermly(term, stud));
   STP.setBalance(-(STP.getTotalAmountPaid())+(STP.getTotalBills()));
  STP.setTotalOutStandingBalance(-(getTotalPaymentForStudent(stud))+(BS.totalBillAmount(stud)));

    LisOfSTP.add(STP);
   }
   
   return LisOfSTP;
   
   } 
   public String SaveStudentPaymentFromFinancePaymentResponse (  List <Payment>payments)throws Exception{

     payments.forEach(c->{
             System.out.println(c.getTransactionSpecialId()+" payment Saving --------->"+ c.getAmount() +
             c.getDiscount()+c.getRemaining());
         c.setTerm(TS.getCurrentTerm());
    // c.setRemaining(GetBalanceBasedOnTerm
      //  (TS.getCurrentTerm(),SS.findStudent(c.getStudent().getStudentId())
         //       ,c.getAmount(),c.getDiscount()));
        c.setDescription("Fees");
     System.out.println(c.getTransactionSpecialId()+" "+ c.getAmount() +
             c.getDiscount()+" checking Remaining "+c.getRemaining());
     });
     payments.forEach(c->{
      System.out.println(c.getTransactionSpecialId()+" payment Saving --------->"+ c.getAmount() );
        
         c= PR.save(c);
       System.out.println(c.getId()+" saved "+c.getTransactionSpecialId());
       
         RS.saveReceipt(new Receipt(c, c.getStudent(),
                 c.getPaymentTime()));
       });
   

   return "Successfully Added  Data";
   }
   public String message(String message){
   return message;
   }

   
    public double sumPaymentsByDateAndTerm(java.time.LocalDate date, Long termId){  
        return PR.sumPaymentsByDateAndTerm(date, termId);
    }
}
