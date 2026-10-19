/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.security.users;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BillEdit {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private int id;
    private float amount;
    private float previousAmount;
    @ManyToOne
    private Bill bill;
    @ManyToOne
    private users user;
    private LocalDate date;
    private String stud;

    public float getPreviousAmount() {
        return this.previousAmount;
    }

    public void setPreviousAmount(float previousAmount) {
        this.previousAmount = previousAmount;
    }

    public String getStud() {
        return this.stud;
    }

    public void setStud(String stud) {
        this.stud = stud;
    }

    public BillEdit(int id, float amount, Bill bill, users user, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.bill = bill;
        this.user = user;
        this.date = date;
    }

    public BillEdit(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Bill getBill() {
        return this.bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public users getUser() {
        return this.user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BillEdit() {
    }
}
