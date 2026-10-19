package com.UBSDB.UnerBasicSchoolFinancialSystem.MountedCourse;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MountedCourseRepository extends JpaRepository<MountedCourse, Long> {

    List<MountedCourse> findByKlassAndTerm(klass klass, Term term);

    Page<MountedCourse> findByKlassAndTerm(klass klass, Term term, Pageable pageable);

    List<MountedCourse> findByKlassAndTermAndStatus(klass klass, Term term, String status);

    List<MountedCourse> findBySubjectAndKlassAndTerm(Subject subject, klass klass, Term term);

    List<MountedCourse> findByCourseAndKlassAndTerm(Course course, klass klass, Term term);

    Optional<MountedCourse> findByCourseAndKlassAndTermAndStatus(Course course, klass klass, Term term, String status);

    @Query("SELECT mc FROM MountedCourse mc LEFT JOIN FETCH mc.course LEFT JOIN FETCH mc.subject LEFT JOIN FETCH mc.klass LEFT JOIN FETCH mc.term WHERE mc.klass = :klass AND mc.term = :term")
    List<MountedCourse> findAllWithDetails(@Param("klass") klass klass, @Param("term") Term term);

    @Query("SELECT mc FROM MountedCourse mc LEFT JOIN FETCH mc.course LEFT JOIN FETCH mc.subject LEFT JOIN FETCH mc.klass LEFT JOIN FETCH mc.term WHERE mc.klass.className = :className AND mc.term.id = :termId")
    List<MountedCourse> findByClassNameAndTermId(@Param("className") String className, @Param("termId") Long termId);

    @Query("SELECT mc FROM MountedCourse mc LEFT JOIN FETCH mc.course LEFT JOIN FETCH mc.subject LEFT JOIN FETCH mc.klass LEFT JOIN FETCH mc.term WHERE mc.klass.className = :className AND mc.term.id = :termId AND mc.status = :status")
    List<MountedCourse> findByClassNameAndTermIdAndStatus(@Param("className") String className, @Param("termId") Long termId, @Param("status") String status);

    @Query("SELECT mc FROM MountedCourse mc LEFT JOIN FETCH mc.course LEFT JOIN FETCH mc.subject LEFT JOIN FETCH mc.klass LEFT JOIN FETCH mc.term WHERE mc.klass = :klass AND mc.term = :term AND mc.status = 'Active'")
    List<MountedCourse> findActiveMountedCourses(@Param("klass") klass klass, @Param("term") Term term);

    @Query("SELECT mc FROM MountedCourse mc LEFT JOIN FETCH mc.course LEFT JOIN FETCH mc.subject LEFT JOIN FETCH mc.klass LEFT JOIN FETCH mc.term WHERE mc.klass.className = :className AND mc.term.id = :termId AND mc.status = 'Active'")
    List<MountedCourse> findActiveMountedCoursesByClassAndTerm(@Param("className") String className, @Param("termId") Long termId);

    boolean existsByCourseAndKlassAndTerm(Course course, klass klass, Term term);
}
