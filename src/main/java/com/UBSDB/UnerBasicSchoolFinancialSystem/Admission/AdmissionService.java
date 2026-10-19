/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Admission;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author lys
 */
@Service
public interface AdmissionService {
    ApplicationForm saveApplicationForm(ApplicationForm AF);

    List<ApplicationForm> GetApplicationForm();

    ApplicationForm EditApplicationForm(ApplicationForm AF);

    Optional<ApplicationForm> findApplicationForm(ApplicationForm AF);

    StaffAplicationForm saveStaffApplicationForm(StaffAplicationForm SAF);

    List<StaffAplicationForm> findAlltaffApplicationForm();

    void deleteAll();
}