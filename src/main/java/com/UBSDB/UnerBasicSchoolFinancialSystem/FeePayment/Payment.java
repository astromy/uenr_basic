/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


/**
 *
 * @author enock
 */
@Entity
public class Payment {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private float discount;
private String bank;
private String description;
private String paymentTime;
private String transactionSpecialId;
private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTransactionSpecialId() {
        return transactionSpecialId;
    }

    public void setTransactionSpecialId(String transactionSpecialId) {
        this.transactionSpecialId = transactionSpecialId;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

private String paymentType;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

@ManyToOne
private Term term;
private String TransactionDate;

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }
private double Amount;
private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
private double Remaining; 
 
   @ManyToOne
    private users user;

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }
@ManyToOne
private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   public double getAmount() {
        return Amount;
    }

    public Payment(String bank, String description, String paymentTime, String paymentType, Term term, double Amount, LocalDate date, double Remaining, Student student) {
        this.bank = bank;
        this.description = description;
        this.paymentTime = paymentTime;
        this.paymentType = paymentType;
        this.term = term;
        this.Amount = Amount;
        this.date = date;
        this.Remaining = Remaining;
        this.student = student;
    }

    public Payment() {
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getRemaining() {
        return Remaining;
    }

    public void setRemaining(double Remaining) {
        this.Remaining = Remaining;
    }
    public Payment getConvertPayment(PaymentResponse PR){
    Payment p = new Payment(PR.getBankName(), PR.getPaymentDescription(), 
            PR.getPaymentDateTime(), PR.getPaymentDateTime(), null, 0, 
            LocalDate.parse(PR.getPaymentDateTime()), 0, 
            new Student(PR.getStudentId()));
    return p;
    } 
}
