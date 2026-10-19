/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.BillEdit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lys
 */
@Repository
public interface EditBillRepository extends JpaRepository<BillEdit, Integer> {
    
}
