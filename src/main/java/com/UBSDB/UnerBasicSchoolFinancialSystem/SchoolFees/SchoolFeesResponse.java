package com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;

import java.time.LocalDate;

/**
 * DTO used to expose SchoolFees data through REST API endpoints.
 * Avoids exposing internal entity relationships and supports live
 * client-side filtering in the Finance Portal.
 */
public class SchoolFeesResponse {

    private Long id;
    private String termName;
    private String academicYear;
    private double amount;
    private String feeType;
    private String feeSpecification;
    private String feesDate;
    private String month;
    private String classLevel;
    private String className;
    private boolean status;

    public SchoolFeesResponse() {
    }

    public SchoolFeesResponse(SchoolFees fee) {
        this.id = fee.getId();
        this.amount = fee.getAmount();
        this.feeType = fee.getFeeType();
        this.feeSpecification = fee.getFeeSpecification();
        this.month = fee.getMoth();
        this.status = fee.isStatus();
        if (fee.getFeesDate() != null) {
            this.feesDate = fee.getFeesDate().toString();
        }
        if (fee.getTerm() != null) {
            this.termName = fee.getTerm().getTermName();
            if (fee.getTerm().getAcademicyear() != null) {
                this.academicYear = fee.getTerm().getAcademicyear().getAcademicName();
            }
        }
        if (fee.getKlass() != null) {
            this.classLevel = fee.getKlass().getClassLevel();
            this.className = fee.getKlass().getClassName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeSpecification() {
        return feeSpecification;
    }

    public void setFeeSpecification(String feeSpecification) {
        this.feeSpecification = feeSpecification;
    }

    public String getFeesDate() {
        return feesDate;
    }

    public void setFeesDate(String feesDate) {
        this.feesDate = feesDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}