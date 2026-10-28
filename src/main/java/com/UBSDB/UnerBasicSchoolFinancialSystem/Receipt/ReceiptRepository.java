/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HP
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt,Long>{
  @Query("SELECT u FROM Receipt u WHERE u.payment = :payment")
 public Optional<Receipt> getReceiptBypayment(@Param("payment") Long payment);      
}
