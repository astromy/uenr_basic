/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class StudentBillPaymentReport {
    private Student stud;
    private Payment Studentpay;
    private Bill Studentbills;
    private double remainingAmount;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Student getStud() {
        return stud;
    }

    public void setStud(Student stud) {
        this.stud = stud;
    }

    public Payment getStudentpay() {
        return Studentpay;
    }

    public void setStudentpay(Payment Studentpay) {
        this.Studentpay = Studentpay;
    }

    public Bill getStudentbills() {
        return Studentbills;
    }

    public void setStudentbills(Bill Studentbills) {
        this.Studentbills = Studentbills;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
    
}
