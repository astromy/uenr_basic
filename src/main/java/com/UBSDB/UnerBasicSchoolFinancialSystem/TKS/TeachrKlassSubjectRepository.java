/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.TKS;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 *
 * @author enock
 */

@Repository
public interface TeachrKlassSubjectRepository extends JpaRepository<TeachrKlassSubject, UUID> {
    
    
}
