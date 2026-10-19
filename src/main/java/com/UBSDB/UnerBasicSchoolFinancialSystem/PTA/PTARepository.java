/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.PTA;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author enock
 */
@org.springframework.stereotype.Controller
public interface PTARepository extends JpaRepository<PTA, Long>{
    
}
