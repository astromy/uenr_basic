/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import java.io.Serializable;

import java.time.LocalDate;

import jakarta.persistence.*;


/**
 *
 * @author enock
 */
@Entity
public class SchoolFees implements Serializable {
 @Id
 private Long Id;
 
 @ManyToOne
  private Term term;

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

   
@ManyToOne
private klass klass;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

private int hashCode;

private LocalDate FeesDate;
private String FeeType;
private String Moth;
private boolean status;

    public SchoolFees(String Moth) {
        this.Moth = Moth;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMoth() {
        return Moth;
    }

    public void setMoth(String Moth) {
        this.Moth = Moth;
    }

   
    public String getFeeType() {
        return FeeType;
    }

    public void setFeeType(String FeeType) {
        this.FeeType = FeeType;
    }

    public klass getKlass() {
        return klass;
    }

    public void setKlass(klass klass) {
        this.klass = klass;
    }
 
 private double Amount;
 
 private String FeeSpecification;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public LocalDate getFeesDate() {
        return FeesDate;
    }

    public void setFeesDate(LocalDate FeesDate) {
        this.FeesDate = FeesDate;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public String getFeeSpecification() {
        return FeeSpecification;
    }

    public void setFeeSpecification(String FeeSpecification) {
        this.FeeSpecification = FeeSpecification;
    }

    public SchoolFees() {
    }

    public SchoolFees(Term term, klass klass, LocalDate FeesDate, String FeeType, String Moth, boolean status, double Amount, String FeeSpecification) {
        this.term = term;
        this.klass = klass;
        this.FeesDate = FeesDate;
        this.FeeType = FeeType;
        this.Moth = Moth;
        this.status = status;
        this.Amount = Amount;
        this.FeeSpecification = FeeSpecification;
    }
 
 
}
