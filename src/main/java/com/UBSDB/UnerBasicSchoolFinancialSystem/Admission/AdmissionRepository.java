/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Admission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lys
 */
@Repository
public interface AdmissionRepository extends JpaRepository<ApplicationForm, String> {
}
