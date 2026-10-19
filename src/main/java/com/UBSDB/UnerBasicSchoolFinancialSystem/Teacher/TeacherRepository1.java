/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author enock
 */
@Repository
public interface TeacherRepository1 extends JpaRepository< Teacher,Long> {
    @Query("SELECT u FROM Teacher u WHERE u.StaffId = :StaffId")
    public Teacher getTeacherByStaffId(@Param("StaffId") String StaffId);

    
}
