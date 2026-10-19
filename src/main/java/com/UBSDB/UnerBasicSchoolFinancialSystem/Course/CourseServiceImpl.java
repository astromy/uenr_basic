package com.UBSDB.UnerBasicSchoolFinancialSystem.Course;

import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ClassCourseRepository classCourseRepository;

    @Autowired
    private klassRepository klassRepository;

    @Override
    public Course saveCourse(Course course) {
        if (course.getCourseId() == null) {
            course.setCourseId(UUID.randomUUID());
        }
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public Course saveCourseWithClass(Course course, String classId) {
        // Save the course first
        if (course.getCourseId() == null) {
            course.setCourseId(UUID.randomUUID());
        }
        Course savedCourse = courseRepository.save(course);

        // If a class is specified, create the class-course link(s)
        if (classId != null && !classId.trim().isEmpty()) {
            linkCourseToClass(savedCourse.getCourseId(), classId.trim());
        }

        return savedCourse;
    }

    /**
     * Links a course to a class. If the class is a Basic class (e.g., Basic1A),
     * the course is linked to all classes with the same number (Basic1A through Basic1F).
     */
    private void linkCourseToClass(UUID courseId, String classId) {
        List<String> classNamesToLink = new ArrayList<>();

        // Check if this is a Basic class (starts with "Basic" followed by a number)
        if (classId.matches("Basic\\d+[A-F]")) {
            // Extract the Basic number prefix (e.g., "Basic1" from "Basic1A")
            String basicPrefix = classId.replaceAll("^(Basic\\d+)[A-F]$", "$1");

            // Find all classes with this Basic number (Basic1A, Basic1B, etc.)
            List<klass> allBasicClasses = klassRepository.findByClassNameStartingWith(basicPrefix);

            classNamesToLink = allBasicClasses.stream()
                    .map(klass::getClassName)
                    .filter(name -> name.matches(basicPrefix + "[A-F]"))
                    .collect(Collectors.toList());
        } else {
            // Non-Basic class: just link to the single class
            classNamesToLink.add(classId);
        }

        // Create ClassCourse entries for each class
        for (String className : classNamesToLink) {
            if (!classCourseRepository.existsByClassNameAndCourseId(className, courseId)) {
                ClassCourse classCourse = ClassCourse.builder()
                        .className(className)
                        .courseId(courseId)
                        .build();
                classCourseRepository.save(classCourse);
            }
        }
    }

    @Override
    public List<String> getLinkedClassNames(UUID courseId) {
        return classCourseRepository.findByCourseId(courseId).stream()
                .map(ClassCourse::getClassName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void assignCourseToClasses(UUID courseId, List<String> classIds) {
        if (classIds == null || classIds.isEmpty()) {
            return;
        }

        for (String classId : classIds) {
            if (classId != null && !classId.trim().isEmpty()) {
                linkCourseToClass(courseId, classId.trim());
            }
        }
    }

    @Override
    public Optional<Course> findCourseById(String courseId) {
        UUID uuid = UUID.fromString(courseId);
        return courseRepository.findById(uuid);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAllOrderByCourseName();
    }

    @Override
    public Page<Course> getAllCoursesPaged(Pageable pageable) {
        return courseRepository.findAllWithSubject(pageable);
    }

    @Override
    public Page<Course> searchCourses(String search, Pageable pageable) {
        return courseRepository.searchCourses(search, pageable);
    }

    @Override
    public Page<Course> getCoursesBySubject(Long subjectId, Pageable pageable) {
        return courseRepository.findBySubjectId(subjectId, pageable);
    }

    @Override
    public long getTotalCourseCount() {
        return courseRepository.count();
    }

    @Override
    public boolean existsByCourseName(String courseName) {
        return courseRepository.existsByCourseName(courseName);
    }

    @Override
    public void deleteCourse(String courseId) {
        UUID uuid = UUID.fromString(courseId);
        courseRepository.deleteById(uuid);
    }

    @Override
    public Page<Course> getAllCoursesByClassPaged(Pageable pageable, String classId)
             {
        try{

           return   courseRepository.findByKlassId(classId,pageable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
