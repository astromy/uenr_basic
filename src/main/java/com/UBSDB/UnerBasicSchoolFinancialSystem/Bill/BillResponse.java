/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class BillResponse {
    private String AcademicYear;
    private String TermName;
    private String Amount;
    private Long BillId;
    private String Description;

    public String getAcademicYear() {
        return this.AcademicYear;
    }

    public void setAcademicYear(String AcademicYear) {
        this.AcademicYear = AcademicYear;
    }

    public String getTermName() {
        return this.TermName;
    }

    public void setTermName(String TermName) {
        this.TermName = TermName;
    }

    public String getAmount() {
        return this.Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public Long getBillId() {
        return this.BillId;
    }

    public void setBillId(Long BillId) {
        this.BillId = BillId;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public BillResponse(String AcademicYear, String TermName, String Amount, Long BillId, String Description) {
        this.AcademicYear = AcademicYear;
        this.TermName = TermName;
        this.Amount = Amount;
        this.BillId = BillId;
        this.Description = Description;
    }

    public BillResponse() {
    }

    public BillResponse(StudentBill bill) {
        if (bill == null) {
            return;
        }
        if (bill.getTerm() != null) {
            this.TermName = bill.getTerm().getTermName();
            if (bill.getTerm().getAcademicyear() != null) {
                this.AcademicYear = bill.getTerm().getAcademicyear().getAcademicName();
            }
        }
        this.Amount = Objects.toString(bill.getFees().getAmount());
        this.Description = bill.getFees().getFeeType();
    }
}
