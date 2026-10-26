/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PTA.PTAService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author enock
 */
@Slf4j
@org.springframework.stereotype.Service
public class SchoolFeesService {

    @Autowired
    private StudentService SS;

    @Autowired
    private SchoolFeesRepository SR;
    @Autowired
    private TermService TS;

    @Autowired
    private BillService BS;
    private @Autowired
    klassService KS;
    @Autowired
    private PTAService PTAS;
    private @Autowired StudentBillService studentBillService;
    public SchoolFees updateFees(SchoolFees fee){
    try{
    return SR.save(fee);
    }catch (Exception ex){
        ex.printStackTrace();
    return null;
    }
    }
     public String EditFees(SchoolFees fee){
    try{
      SR.getSchoolFeesByhashCode(fee.getHashCode()).forEach(y->{
      y =SR.findById(y.getId()).get();
      
          y.setAmount(fee.getAmount());
         
      System.out.println(SR.save(y));
   
      });
    }catch (Exception ex){
        System.out.print(ex.toString());
    return ex.toString();
    }
    return "Updated Successfully";
    }
    public String SaveSchoolFees(SchoolFees fee, List<String> ClassLevel) {
       fee.setFeesDate(LocalDate.now());
       List<SchoolFees>allFees=new ArrayList<>();
      fee.setStatus(true);
       for(klass k:SortedListOfKlasses(ClassLevel)) {
          //  System.out.println(SortedListOfKlasses(ClassLevel).toString());
          SchoolFees sc = new SchoolFees
                  (fee.getTerm(), k, LocalDate.now(), fee.getFeeType(), LocalDate.now().getMonth() + "", true, fee.getAmount(), fee.getFeeSpecification());
          // sc.setAmount(fee.getAmount());

          sc.setKlass(k);
           long hcode =sc.hashCode();

           sc.setHashCode((int) hcode);
         var TempBill= BS.findBillByTerm(fee.getTerm());
          Bill bill = new Bill();
         if (TempBill.isPresent()){
         bill = TempBill.get();
         log.info("Bill exist for  term "+fee.getTerm().getTermName());
         }else {

             bill.setTerm(fee.getTerm());
             bill.setId((long) bill.hashCode() + LocalDate.now().toEpochDay());

             bill = BS.saveBill(bill);
             log.info("Creating Bill  for  term "+fee.getTerm().getTermName()+" with id "+ bill.getId());

         }
          sc.setBill(bill);
          sc.setId((long) sc.hashCode());
          SR.save(sc);
          List<StudentBill> studentBills = new ArrayList<>();
//          for (Student stud : k.getStudents()) {
//              if (stud.getStudentStatus().equals("in School") &&
//                      !fee.getFeeType().equalsIgnoreCase("New_Admission")
//                      && fee.getFeeSpecification().equals(stud.getParentCategory())) {
//                  StudentBill studentBil = StudentBill.
//                          builder()
//                          .bill(bill)
//                          .student(stud)
//                          .build();
//                  studentBills.add(studentBil);
//
//              }
//
//          }
         // studentBills.forEach(sb -> studentBillService.saveStudentBill(sb));
          System.out.println(sc.getId() + " <----fee id type:" + fee.getFeeType() + " " + TS.getCurrentTerm().getTermName());




    }
        return "sucessss fully addded all data";
    }

    public String SaveSchoolFees(SchoolFees fee, Student student) {
        fee.setFeesDate(LocalDate.now());
        int hcode = fee.hashCode();
        fee.setStatus(true);
        klass k = fee.getKlass() != null ? fee.getKlass() : student.getStudentklass();
        Bill bill = new Bill();
        bill.setTerm(fee.getTerm());
        bill = BS.saveBill(bill);
        SchoolFees sc = new SchoolFees(fee.getTerm(), k,
                LocalDate.now(), fee.getFeeType(), LocalDate.now().getMonth() + "",
                true, fee.getAmount(), fee.getFeeSpecification());
        sc.setHashCode(hcode);
        sc.setKlass(k);
        sc.setBill(bill);
        fee = SR.save(sc);
        if (student.getStudentStatus() != null && student.getStudentStatus().equals("in School")
                && !fee.getFeeType().equalsIgnoreCase("New_Admission")
                && fee.getFeeSpecification() != null
                && fee.getFeeSpecification().equals(student.getParentCategory())) {
            StudentBill studentBil = StudentBill.builder()
                    .fees(fee)
                    .student(student)
                    .build();
            studentBillService.saveStudentBill(studentBil);
        }
        return "successfully added outstanding fee";
    }


    public String RemoveFees(SchoolFees sf){
    
    try{
     SR.deleteById(sf.getId());
    }catch (Exception ex){
    return ex.toString();
    }
    return "sucess";
    }
    
public List<klass>SortedListOfKlasses(List<String>level){
   List<klass>tempKlass=new ArrayList<>();
    level.forEach(t->{
   KS.AllKlass().stream().filter(c->c.getClassLevel().equals(t)).forEach(g->{
    tempKlass.add(g);
   });
 
    });
    
  return  tempKlass;  
}
    public String CheckIfStaffOrNonStaffFeesHaveBeenAssigned(SchoolFees fee) {
    var klass=fee.getKlass();
    klass= KS.FindKlass(fee.getKlass().getClassName());
        try {
            if (klass.getSchoolFees().isEmpty()) {
                return "true";
            } else {
                //  boolean check =BS.bills().stream().
                //         filter(c->c.getFee().getFeesDate().getYear()==fee.getFeesDate().getYear()).filter(b->b.getTerm().getTermName().equals(fee.getTerm().getTermName()))
                //              .filter(h->h.getFee().getFeeSpecification().equals(fee.getFeeSpecification()));
                for (SchoolFees bill :klass.getSchoolFees()) {
                    if ((bill.getFeesDate().getYear()
                            == fee.getFeesDate().getYear())&&
                            (bill.getFeesDate().getMonth().name().equals(fee.getMoth()))
                           && (bill.getFeeType().equals(fee.getFeeType()))
                            && (bill.getTerm().getTermName()
                                    .equals(fee.getTerm().getTermName()))
                            && (bill.getFeeSpecification()
                                    .equals(fee.getFeeSpecification()))
                            && (bill.getKlass().getClassLevel().equals(fee.getKlass().getClassLevel()))) {
                        //check if either staff or non staff fee is assigned
                        return "please fees for" + fee.getFeeSpecification() + " have been assigned ";

                    }
                }
            }
        } catch (Exception ex) {
            return "true";
        }
        return "true";
    }

    public void AssignBillToStudentBasedOnFees(SchoolFees fees) {

 Runnable r =()->{
    SS.AALLStudents()
            .stream()
            .filter(stud->stud.getParentCategory().equals(fees
                    .getFeeSpecification()) && (stud.getStudentStatus()
                            .equals("in School")) 
                && stud.getStudentklass().getClassLevel()
                        .equals(fees.getKlass()
                                .getClassLevel())).forEach(stud->{

            StudentBill studBill =StudentBill
                    .builder().student(stud)
                    .fees(fees)
                    .build();
            studentBillService.saveStudentBill(studBill);



            });

        };
 Thread t= new Thread(r);
 t.start();

    }

    public Set<Term> Feeterms() {
        Set<Term> terms = new HashSet<>();
       var term =TS.getCurrentTerm();
      if (term.getTermName().equals("ThirdTerm")){
          log.info("Third term ......"+term.getTermName()+"...."+term.getAcademicyear().getAcademicName());
      if (TS.findNextTermBasedOnTerm(term).isPresent()){
      terms.add(TS.findNextTermBasedOnTerm(term).get());
      }
      }else{
       terms= term.getAcademicyear().getTerm();
      }
        return terms;

    }
    public List<SchoolFees>getAllSchoolFees(){
    
    return SR.findAll();
    }
    public SchoolFees findSchoolFees(SchoolFees fees){
    return SR.findById(fees.getId()).orElse(null);
    }
     public List<SchoolFees> findSchoolFees_hashCode(int  f){
    return SR.getSchoolFeesByhashCode(f)
            .stream().filter(c->{
        return c.getHashCode()>0;
    }).collect(Collectors.toList());
    }
}
