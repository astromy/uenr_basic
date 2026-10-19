/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Admission;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


/**
 *
 * @author lys
 */

@Entity
public class StaffAplicationForm {
    @OneToOne
    private ApplicationForm Details;
    private String staffId;
    @Id
    private int Id;

    public StaffAplicationForm() {
    }

    public ApplicationForm getDetails() {
        return this.Details;
    }

    public void setDetails(ApplicationForm Details) {
        this.Details = Details;
    }

    public String getStaffId() {
        return this.staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
}
