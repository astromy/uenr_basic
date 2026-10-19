/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class StudentTermlyPayment {
  private Student stud;

    public Student getStud() {
        return stud;
    }

    public void setStud(Student stud) {
        this.stud = stud;
    }

    public double getTotalAmountPaid() {
        return TotalAmountPaid;
    }

    public void setTotalAmountPaid(double TotalAmountPaid) {
        this.TotalAmountPaid = TotalAmountPaid;
    }

    public double getTotalBills() {
        return TotalBills;
    }

    public void setTotalBills(double TotalBills) {
        this.TotalBills = TotalBills;
    }

    public double getTotalOutStandingBalance() {
        return TotalOutStandingBalance;
    }

    public void setTotalOutStandingBalance(double TotalOutStandingBalance) {
        this.TotalOutStandingBalance = TotalOutStandingBalance;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }
  private double TotalAmountPaid;
  private double TotalBills;
  private double TotalOutStandingBalance;
  private Term   term;
 private double Balance;

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }
    
}
