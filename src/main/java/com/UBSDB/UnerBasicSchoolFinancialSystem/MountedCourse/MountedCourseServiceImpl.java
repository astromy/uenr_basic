package com.UBSDB.UnerBasicSchoolFinancialSystem.MountedCourse;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MountedCourseServiceImpl implements MountedCourseService {

    @Autowired
    private MountedCourseRepository mountedCourseRepository;

    @Override
    public MountedCourse saveMountedCourse(MountedCourse mountedCourse) {
        if (mountedCourse.getStatus() == null || mountedCourse.getStatus().isEmpty()) {
            mountedCourse.setStatus("Active");
        }
        return mountedCourseRepository.save(mountedCourse);
    }

    @Override
    public List<MountedCourse> getAllMountedCourses() {
        return mountedCourseRepository.findAll();
    }

    @Override
    public Page<MountedCourse> getAllMountedCoursesPaged(Pageable pageable) {
        return mountedCourseRepository.findAll(pageable);
    }

    @Override
    public Optional<MountedCourse> getMountedCourseById(Long id) {
        return mountedCourseRepository.findById(id);
    }

    @Override
    public List<MountedCourse> getMountedCoursesByClassAndTerm(klass klass, Term term) {
        return mountedCourseRepository.findByKlassAndTerm(klass, term);
    }

    @Override
    public List<MountedCourse> getActiveMountedCoursesByClassAndTerm(klass klass, Term term) {
        return mountedCourseRepository.findActiveMountedCourses(klass, term);
    }

    @Override
    public List<MountedCourse> getActiveMountedCoursesByClassAndTermAndWeek(klass klass, Term term, com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek termWeek) {
        return mountedCourseRepository.findActiveMountedCoursesByClassAndTermAndWeek(klass, term, termWeek);
    }

    @Override
    public List<MountedCourse> getMountedCoursesByClassAndTermPaged(klass klass, Term term, Pageable pageable) {
        return mountedCourseRepository.findByKlassAndTerm(klass, term, pageable).getContent();
    }

    @Override
    public List<MountedCourse> getMountedCoursesBySubject(Subject subject, klass klass, Term term) {
        return mountedCourseRepository.findBySubjectAndKlassAndTerm(subject, klass, term);
    }

    @Override
    public List<MountedCourse> getMountedCoursesByCourse(Course course, klass klass, Term term) {
        return mountedCourseRepository.findByCourseAndKlassAndTerm(course, klass, term);
    }

    @Override
    public boolean existsByCourseAndKlassAndTerm(Course course, klass klass, Term term) {
        return mountedCourseRepository.existsByCourseAndKlassAndTerm(course, klass, term);
    }

    @Override
    public void deleteMountedCourse(Long id) {
        mountedCourseRepository.deleteById(id);
    }

    @Override
    public long getTotalMountedCourseCount() {
        return mountedCourseRepository.count();
    }
}
