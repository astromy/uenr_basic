/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Student;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student,String>{
    @Query("SELECT u FROM Student u WHERE u.StudentId = :StudentReference")
  public Optional<Student> getStudentByStudentReference(@Param("StudentReference") String StudentReference);
    @Query("SELECT u FROM Student u WHERE u.StudentId LIKE %:search% OR u.FirstName LIKE %:search% OR u.LastName LIKE %:search%")
 public Page<Student>findByStudentIdOrFirstNameOrLastNameOrsurname(String search, Pageable pageable);

    @Query("SELECT a FROM Assessment a WHERE a.student.StudentId = :studentId AND a.term.id = :termId")
    public List<Assessment> findStudentResultsByTerm(@Param("studentId") String studentId, @Param("termId") Long termId);

    @Query("SELECT a FROM Assessment a WHERE a.student.StudentId = :studentId")
    public List<Assessment> findAllStudentResults(@Param("studentId") String studentId);

    @Query("SELECT a FROM Assessment a WHERE a.student.StudentId = :studentId AND a.term.id = :termId AND a.subject.id = :subjectId")
    public List<Assessment> findStudentResultsByTermAndSubject(
            @Param("studentId") String studentId, @Param("termId") Long termId, @Param("subjectId") Long subjectId);

    @Query("SELECT DISTINCT a.term FROM Assessment a WHERE a.student.StudentId = :studentId")
    public List<com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term> findStudentTerms(@Param("studentId") String studentId);

    @Query("SELECT DISTINCT a.subject FROM Assessment a WHERE a.student.StudentId = :studentId AND a.term.id = :termId")
    public List<com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject> findStudentSubjectsByTerm(
            @Param("studentId") String studentId, @Param("termId") Long termId);

    @Query("SELECT NEW com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentResponse(a) FROM Assessment a WHERE a.student.StudentId = :studentId")
    public List<AssessmentResponse> findAssessmentResponseByStudentId(@Param("studentId") String studentId);

    @Query("SELECT NEW com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.AssessmentResponse(a) FROM Assessment a WHERE a.student.StudentId = :studentId AND a.term.id = :termId")
    public List<AssessmentResponse> findAssessmentResponseByStudentIdAndTermId(
            @Param("studentId") String studentId, @Param("termId") Long termId);

    Page<Student> findByStudentStatus(String studentStatus, Pageable pageable);
   @Query("SELECT COUNT(s) FROM Student s WHERE s.StudentStatus = 'In School' AND s.ParentCategory = 'Junior-Staff'")
   
    Page<Student> findByStudentklassClassName(String className, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.StudentStatus = :status AND s.studentklass.className = :className")
    Page<Student> findByStudentStatusAndStudentklassClassName(
            @Param("status") String status, @Param("className") String className, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.StudentStatus = 'In School' AND s.ParentCategory = 'Junior-Staff'")
    long countJuniorStaffStudents();

    @Query("SELECT COUNT(s) FROM Student s WHERE s.StudentStatus = 'In School' AND s.ParentCategory = 'Non-Staff'")
    long countNonStaffStudents();

    @Query("SELECT COUNT(s) FROM Student s WHERE s.StudentStatus = 'In School' AND s.ParentCategory = 'Senior-Member'")
    long countSeniorMemberStudents();

    @Query("SELECT COUNT(s) FROM Student s WHERE s.StudentStatus = 'In School' AND s.studentklass.ClassLevel = :level")
    long countStudentsByClassLevel(@Param("level") String level);
}
