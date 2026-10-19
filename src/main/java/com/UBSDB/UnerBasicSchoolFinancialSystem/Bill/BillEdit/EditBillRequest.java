/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.Bill;
import org.springframework.stereotype.Component;

@Component
public class EditBillRequest {
    private Long billId;
    private String amount;
    private String stud;

    public String getStud() {
        return this.stud;
    }

    public void setStud(String stud) {
        this.stud = stud;
    }

    public EditBillRequest(Long billId, String amount) {
        this.billId = billId;
        this.amount = amount;
    }

    public BillEdit getBillEdit() {
        BillEdit ed = new BillEdit();
        ed.setAmount(Float.parseFloat(this.amount));
        ed.setBill(new Bill(this.billId));
        ed.setStud(this.stud);
        return ed;
    }

    public Long getBillId() {
        return this.billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public EditBillRequest() {
    }
}
