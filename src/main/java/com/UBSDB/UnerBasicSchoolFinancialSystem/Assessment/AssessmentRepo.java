/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author enock
 */
@Repository
public interface AssessmentRepo extends JpaRepository<Assessment,Long>{

    @Query("SELECT a FROM Assessment a WHERE a.term.id = :termId AND a.student.StudentId = :studentId")
    List<Assessment> findByTermAndStudent(@Param("termId") Long termId, @Param("studentId") String studentId);
    @Query("SELECT a FROM Assessment a WHERE a.term.id = :termId AND a.student.StudentId = :studentId And a.subject.id= :subjectId")
    List<Assessment> findByTermAndStudentAndSubject(@Param("termId") Long termId,
                                                    @Param("studentId") String studentId
                                                     , @Param("subjectId") Long subjectId);

}
