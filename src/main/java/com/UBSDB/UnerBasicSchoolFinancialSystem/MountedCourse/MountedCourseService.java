package com.UBSDB.UnerBasicSchoolFinancialSystem.MountedCourse;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MountedCourseService {
    MountedCourse saveMountedCourse(MountedCourse mountedCourse);

    List<MountedCourse> getAllMountedCourses();

    Page<MountedCourse> getAllMountedCoursesPaged(Pageable pageable);

    Optional<MountedCourse> getMountedCourseById(Long id);

    List<MountedCourse> getMountedCoursesByClassAndTerm(klass klass, Term term);

    List<MountedCourse> getActiveMountedCoursesByClassAndTerm(klass klass, Term term);

    List<MountedCourse> getActiveMountedCoursesByClassAndTermAndWeek(klass klass, Term term, com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek termWeek);

    List<MountedCourse> getMountedCoursesByClassAndTermPaged(klass klass, Term term, Pageable pageable);

    List<MountedCourse> getMountedCoursesBySubject(Subject subject, klass klass, Term term);

    List<MountedCourse> getMountedCoursesByCourse(Course course, klass klass, Term term);

    boolean existsByCourseAndKlassAndTerm(Course course, klass klass, Term term);

    void deleteMountedCourse(Long id);

    long getTotalMountedCourseCount();
}
