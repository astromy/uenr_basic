/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Finance;

import org.springframework.stereotype.Component;

@Component
public class SchoolStatisticsModel {
    private int TotalStudents;
    private int NonStaff;
    private int Junior_Staff;
    private int SeniorMember;
    private int TotalStudentsJuniorHigh;
    private int TotalStudentsLowerPrimary;
    private int TotalStudentsUpperPrimary;
    private int TotalStudentsCreche;
    private int TotalStudentsNursery;
    private int TotalStudentsKindagarten;
    private float TotalAmountPaid;
    private double TotalExpenses;
    private double NetProfit;


    public String toString() {
        return "SchoolStatisticsModel{TotalStudents=" + this.TotalStudents + ", NonStaff=" + this.NonStaff + ", Junior_Staff=" + this.Junior_Staff + ", SeniorMember=" + this.SeniorMember + ", TotalStudentsJuniorHigh=" + this.TotalStudentsJuniorHigh + ", TotalStudentsLowerPrimary=" + this.TotalStudentsLowerPrimary + ", TotalStudentsUpperPrimary=" + this.TotalStudentsUpperPrimary + ", TotalStudentsCreche=" + this.TotalStudentsCreche + ", TotalStudentsNursery=" + this.TotalStudentsNursery + ", TotalStudentsKindagarten=" + this.TotalStudentsKindagarten + ", TotalAmountPaid=" + this.TotalAmountPaid + ", TotalExpenses=" + this.TotalExpenses + ", NetProfit=" + this.NetProfit + '}';
    }

    public SchoolStatisticsModel() {
    }

    public SchoolStatisticsModel(int TotalStudents, int NonStaff, int Junior_Staff, int SeniorMember, int TotalStudentsKindagarten, int TotalStudentsJuniorHigh, int TotalStudentsLowerPrimary, int TotalStudentsUpperPrimary, int TotalStudentsCreche, int TotalStudentsNursery, float TotalAmountPaid, double TotalExpenses, double NetProfit) {
        this.TotalStudents = TotalStudents;
        this.NonStaff = NonStaff;
        this.Junior_Staff = Junior_Staff;
        this.SeniorMember = SeniorMember;
        this.TotalStudentsJuniorHigh = TotalStudentsJuniorHigh;
        this.TotalStudentsLowerPrimary = TotalStudentsLowerPrimary;
        this.TotalStudentsUpperPrimary = TotalStudentsUpperPrimary;
        this.TotalStudentsCreche = TotalStudentsCreche;
        this.TotalStudentsNursery = TotalStudentsNursery;
        this.TotalStudentsKindagarten = TotalStudentsKindagarten;
        this.TotalAmountPaid = TotalAmountPaid;
        this.TotalExpenses = TotalExpenses;
        this.NetProfit = NetProfit;
    }

    public int getTotalStudents() {
        return this.TotalStudents;
    }

    public int getTotalStudentsKindagarten() {
        return this.TotalStudentsKindagarten;
    }

    public void setTotalStudentsKindagarten(int TotalStudentsKindagarten) {
        this.TotalStudentsKindagarten = TotalStudentsKindagarten;
    }

    public void setTotalStudents(int TotalStudents) {
        this.TotalStudents = TotalStudents;
    }

    public int getNonStaff() {
        return this.NonStaff;
    }

    public void setNonStaff(int NonStaff) {
        this.NonStaff = NonStaff;
    }

    public int getJunior_Staff() {
        return this.Junior_Staff;
    }

    public void setJunior_Staff(int Junior_Staff) {
        this.Junior_Staff = Junior_Staff;
    }

    public int getSeniorMember() {
        return this.SeniorMember;
    }

    public void setSeniorMember(int SeniorMember) {
        this.SeniorMember = SeniorMember;
    }

    public int getTotalStudentsJuniorHigh() {
        return this.TotalStudentsJuniorHigh;
    }

    public void setTotalStudentsJuniorHigh(int TotalStudentsJuniorHigh) {
        this.TotalStudentsJuniorHigh = TotalStudentsJuniorHigh;
    }

    public int getTotalStudentsLowerPrimary() {
        return this.TotalStudentsLowerPrimary;
    }

    public void setTotalStudentsLowerPrimary(int TotalStudentsLowerPrimary) {
        this.TotalStudentsLowerPrimary = TotalStudentsLowerPrimary;
    }

    public int getTotalStudentsUpperPrimary() {
        return this.TotalStudentsUpperPrimary;
    }

    public void setTotalStudentsUpperPrimary(int TotalStudentsUpperPrimary) {
        this.TotalStudentsUpperPrimary = TotalStudentsUpperPrimary;
    }

    public int getTotalStudentsCreche() {
        return this.TotalStudentsCreche;
    }

    public void setTotalStudentsCreche(int TotalStudentsCreche) {
        this.TotalStudentsCreche = TotalStudentsCreche;
    }

    public int getTotalStudentsNursery() {
        return this.TotalStudentsNursery;
    }

    public void setTotalStudentsNursery(int TotalStudentsNursery) {
        this.TotalStudentsNursery = TotalStudentsNursery;
    }

    public float getTotalAmountPaid() {
        return this.TotalAmountPaid;
    }

    public void setTotalAmountPaid(float TotalAmountPaid) {
        this.TotalAmountPaid = TotalAmountPaid;
    }

    public double getTotalExpenses() {
        return this.TotalExpenses;
    }

    public void setTotalExpenses(double TotalExpenses) {
        this.TotalExpenses = TotalExpenses;
    }

    public double getNetProfit() {
        return this.NetProfit;
    }

    public void setNetProfit(double NetProfit) {
        this.NetProfit = NetProfit;
    }
}
