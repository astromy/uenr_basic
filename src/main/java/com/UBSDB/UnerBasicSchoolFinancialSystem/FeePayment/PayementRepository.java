/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 *
 * @author enock
 */
@Repository
public interface PayementRepository extends JpaRepository<Payment,Long> {

    @Query("SELECT COALESCE(SUM(p.Amount), 0.0) FROM Payment p WHERE p.date = :date AND p.term.id = :termId")
    double sumPaymentsByDateAndTerm(@Param("date") java.time.LocalDate date, @Param("termId") Long termId);


}
