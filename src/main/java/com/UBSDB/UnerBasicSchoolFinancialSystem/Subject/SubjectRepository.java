/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author enock
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
  @Query("SELECT u FROM Subject u WHERE u.SubjectName = :SubjectName")
    public Subject getSubjectBySubjectName(@Param("SubjectName") String SubjectName);
   
}
