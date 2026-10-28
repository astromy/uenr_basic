//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PaymentObjectBillingResponse {
    String StudentName;
    String termlyPayment;
    String outstanding;
    String Total;
    String currentBill;

    public String getStudentName() {
        return this.StudentName;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }

    public String getTermlyPayment() {
        return this.termlyPayment;
    }

    public void setTermlyPayment(String termlyPayment) {
        this.termlyPayment = termlyPayment;
    }

    public String getOutstanding() {
        return this.outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getTotal() {
        return this.Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }

    public String getCurrentBill() {
        return this.currentBill;
    }

    public void setCurrentBill(String currentBill) {
        this.currentBill = currentBill;
    }

    public PaymentObjectBillingResponse() {
    }

    public PaymentObjectBillingResponse(Student stud, Term t) {
       if (stud.getStudentBills()==null){
           return;

       }else {
        List<SchoolFees> bills = stud.getStudentBills().stream().map(StudentBill::getFees).toList();
        List<SchoolFees> Previousbills = stud.getStudentBills().stream().filter((c) -> {
            return !Objects.equals(c.getTerm().getId(), t.getId());
        }).map(StudentBill::getFees).toList();
        List<Payment> PreviousPayments = stud.getPayments().stream().filter((c) -> {
            return c.getTerm().getId() != t.getId();
        }).toList();
        this.StudentName = stud.getFullName();
        this.termlyPayment = ((Double)stud.getPayments().stream().filter((c) -> {
            return c.getTerm().getId() == t.getId();
        }).map(Payment::getAmount).reduce(0.0, Double::sum)).toString();
        this.outstanding = Previousbills.stream().map(SchoolFees::getAmount).reduce(0.0, Double::sum)- PreviousPayments.stream().map(Payment::getAmount).reduce(0.0, (a, b) -> {
            return a + b;
        }) + "";
        this.Total = stud.getStudentBills().stream().map(StudentBill::getFees).map(SchoolFees::getAmount).reduce(0.0, Double::sum) - stud.getPayments().stream().map(Payment::getAmount).reduce(0.0, (a, b) -> {
            return a + b;
        }) + "";
        this.currentBill = ((Double)bills.stream().filter((c) -> {
            return c.getTerm().getId() == t.getId();
        }).map(SchoolFees::getAmount).reduce(0.0, (a, b) -> {
            return a + b;
        })).toString();
       }
    }

    public String toString() {
        return "PaymentObjectBillingResponse{StudentName=" + this.StudentName + ", termlyPayment=" + this.termlyPayment + ", outstanding=" + this.outstanding + ", Total=" + this.Total + ", currentBill=" + this.currentBill + '}';
    }
}
