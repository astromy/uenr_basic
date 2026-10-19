/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Admission;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */
@Service
public class AdmssionServiceImpl implements AdmissionService {
    @Autowired
    private AdmissionRepository AR;
    @Autowired
    private StaffApplicationFormRepository SARF;

    public AdmssionServiceImpl() {
    }

    public ApplicationForm saveApplicationForm(ApplicationForm AF) {
        try {
            return (ApplicationForm)this.AR.save(AF);
        } catch (Exception var3) {
            Exception ex = var3;
            System.out.println(ex.toString());
            return null;
        }
    }

    public List<ApplicationForm> GetApplicationForm() {
        return this.AR.findAll();
    }

    public ApplicationForm EditApplicationForm(ApplicationForm AF) {
        try {
            return (ApplicationForm)this.AR.save(AF);
        } catch (Exception var3) {
            Exception ex = var3;
            System.out.println(ex.toString());
            return null;
        }
    }

    public Optional<ApplicationForm> findApplicationForm(ApplicationForm AF) {
        return this.AR.findById(AF.getID());
    }

    public StaffAplicationForm saveStaffApplicationForm(StaffAplicationForm SAF) {
        try {
            return (StaffAplicationForm)this.SARF.save(SAF);
        } catch (Exception var3) {
            Exception ex = var3;
            System.out.println(ex.toString());
            return null;
        }
    }

    public List<StaffAplicationForm> findAlltaffApplicationForm() {
        return this.SARF.findAll();
    }

    public void deleteAll() {
        this.SARF.findAll();
    }
}
