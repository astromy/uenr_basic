/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFeesService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author lys
 */
@Service
public class EditBillServiceImpl implements EditBillService{
@Autowired private EditBillRepository EBR;@Autowired private SchoolFeesService SFS;
@Autowired private BillService BS;@Autowired private StudentService SS;
@Autowired private StudentBillService studentBillervice;
    @Override
    public @ResponseBody BillEdit saveBill(BillEdit BE) {
        Runnable r3=()->{
        System.out.println(BE.getStud()+" student ID");
        
        Student stud = SS.findStudent(BE.getStud());
        Bill SB=BS.FindstudentBill(BE.getBill().getId()).get();
        System.out.println(SB.getId()+" sbill ID");
        var fee=SB.getFee().stream().filter(c->c.getBill().getId()
                ==BE.getBill().getId());

      var sc =SFS.findSchoolFees(fee.findFirst().get());
        System.out.println(sc.getAmount()+"--- fees amount "+sc.getId()+" fessID ID "+ BE.getAmount());
     
      BE.setPreviousAmount((float) sc.getAmount());
     // sc.setAmount(BE.getAmount());
  
      SchoolFees  TempFees=new SchoolFees(sc.getTerm(), sc.getKlass(), sc.getFeesDate(), sc.getFeeType(),
              sc.getMoth(), true, BE.getAmount(), 
              sc.getFeeSpecification());

    TempFees=  SFS.updateFees(TempFees);
        
//    bill =BS.saveBill(bill);
//            StudentBill studentBill = StudentBill
//                    .builder().bill(bill)
//                            .student(stud). build();
//
//            studentBillervice.saveStudentBill(studentBill);
        };r3.run();
        //System.out.println(BE.getBill().getId());
   return EBR.save(BE);
    }

    @Override
    public BillEdit updateStudentBill(BillEdit BE) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BillEdit FindBillEdit(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Float TotalEditAmount(Bill sB) {
  return sB.getBillEdits().stream().map(BillEdit::getAmount).reduce(0.f,(a,b)->a+b);
    }
    
}
