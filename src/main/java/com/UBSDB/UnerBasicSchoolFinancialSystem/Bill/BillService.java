/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit.BillEdit;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit.EditBillService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    private BillRepository BR;
    
    @Autowired private StudentService studentService;
    @Autowired
    private EditBillService EBS;
    @Autowired
    private TermService TS;

    public BillService() {
    }

    public Bill saveBill(Bill bill) {
        try {
            return (Bill)this.BR.save(bill);
        } catch (Exception var3) {
            return null;
        }
    }

    public Optional<Bill> FindstudentBill(Long id) {
        return this.BR.findById(id);
    }

    public List<Bill> bills() {
        return this.BR.findAll();
    }

    public List<StudentBill> Bills(Student stud) {
        return stud.getStudentBills();
    }

    public Optional<StudentBill> BillsBasedOnTerm(Term term, Student stud) {
        if (term == null || stud == null || stud.getStudentId() == null) {
            return Optional.empty();
        }
        return this.studentService.findOptionalStudent(stud.getStudentId())
                .flatMap(student -> student.getStudentBills().stream()
                        .filter(c -> c.getTerm() !=
                                null && c.getTerm().getId()
                                != null && c.getTerm().getId().equals(term.getId()))
                        .findFirst());
    }

    public double totalBillAmount(Student stud) {
        if (stud == null || stud.getStudentId() == null) {
            return 0.0;
        }
        return this.studentService.findOptionalStudent(stud.getStudentId())
                .map(Student::getStudentBills)
                .stream()
                .flatMap(List::stream)
                .map(StudentBill::getBill)
                .filter(Objects::nonNull)
                .map(Bill::getFee)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(SchoolFees::getAmount)
                .filter(Objects::nonNull)
                .reduce(0.0, (b, f) -> b + f);
    }

    public List<BillEdit> getBillEditOnAStudent(List<Bill> bills) {
        List<BillEdit> edits = new ArrayList<>();
        bills.forEach((c) -> {
            if (c.getBillEdits() != null) {
                c.getBillEdits().forEach((b) -> {
                    edits.add(b);
                });
            }
        });
        return edits;
    }

    public double totalBillAmountxTermly(Term term, Student stud) {
        if (term == null || stud == null) {
            return 0.0;
        }
        return stud.getStudentBills().stream()
                .filter(c -> c.getTerm() != null && c.getTerm().getId() != null && c.getTerm().getId().equals(term.getId()))
                .map(StudentBill::getBill)
                .filter(Objects::nonNull)
                .map(Bill::getFee)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(SchoolFees::getAmount)
                .filter(Objects::nonNull)
                .reduce(0.0, (k, f) -> k + f);
    }

    public void deleteBills() {
        this.BR.deleteAll();
    }

    public List<BillResponse> Responses(Student stud) {
        List<BillResponse> billResponses = new ArrayList<>();
        stud.getStudentBills().forEach((c) -> {
          //  billResponses.add(new StudentBill(c));
        });
        return billResponses;
    }

    public BillResponse getStudentBill(Long id) {
        return this.BR.findById(id)
                .map(b -> {
                    System.out.println(b.getTerm().getId() + " " + b.getFee() + " from get a student particular bill");
                    return new BillResponse(b);
                })
                .orElse(new BillResponse());
    }
    public Optional<Bill>findBillByTerm(Term term){
        return BR.findAll().stream().filter(c->c.getTerm().getId()== term.getId()).findFirst();
    }
}
