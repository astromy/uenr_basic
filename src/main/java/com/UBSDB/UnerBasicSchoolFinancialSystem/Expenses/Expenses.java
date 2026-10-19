/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Expenses;

import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 *
 * @author enock
 */
@Entity
public class Expenses {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;
    private String expensesType;
    private double Amount;
    
    private LocalDate ExpenseDate;
    @OneToOne
    private users User;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(String expensesType) {
        this.expensesType = expensesType;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public LocalDate getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(LocalDate ExpenseDate) {
        this.ExpenseDate = ExpenseDate;
    }

    public users getUser() {
        return User;
    }

    public void setUser(users User) {
        this.User = User;
    }
}
