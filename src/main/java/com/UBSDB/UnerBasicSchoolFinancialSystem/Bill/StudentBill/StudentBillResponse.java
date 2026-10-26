/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill;

import com.UBSDB.UnerBasicSchoolFinancialSystem.SchoolFees.SchoolFees;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class StudentBillResponse {
    private UUID studBillId;
    private String studentId;
    private String studentName;
    private Long billId;
    private String billDescription;
    private double billAmount;

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getBillId() {
        return this.billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getBillDescription() {
        return this.billDescription;
    }

    public void setBillDescription(String billDescription) {
        this.billDescription = billDescription;
    }

    public double getBillAmount() {
        return this.billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public UUID getStudBillId() {
        return this.studBillId;
    }

    public void setStudBillId(UUID studBillId) {
        this.studBillId = studBillId;
    }

    public StudentBillResponse() {
    }

    public StudentBillResponse(StudentBill sb) {
        this.studBillId = sb.getStudBillId();
        if (sb.getStudent() != null) {
            this.studentId = sb.getStudent().getStudentId();
            this.studentName = sb.getStudent().getFirstName() + " " + sb.getStudent().getLastName();
        }
        if (sb.getFees() != null) {
            this.billId = sb.getFees().getId();
            SchoolFees fees = sb.getFees();
//            if (fees != null && !fees.isEmpty()) {
//                SchoolFees fee = fees.get(0);
//                this.billDescription = fee.getFeeType();
//                this.billAmount = fee.getAmount();
//            }
        }
    }
}
