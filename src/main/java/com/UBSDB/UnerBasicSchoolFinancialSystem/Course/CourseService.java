package com.UBSDB.UnerBasicSchoolFinancialSystem.Course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    Course saveCourse(Course course);

    /**
     * Save a course and link it to the specified class.
     * If the class is a Basic class (e.g., Basic1A), the course will be
     * linked to all classes with the same number (Basic1A through Basic1F).
     *
     * @param course  the course to save
     * @param classId the className of the class to link (e.g., "Basic1A")
     * @return the saved course
     */
    Course saveCourseWithClass(Course course, String classId);

    Optional<Course> findCourseById(String courseId);

    List<Course> getAllCourses();

    Page<Course> getAllCoursesPaged(Pageable pageable);

    Page<Course> searchCourses(String search, Pageable pageable);

    Page<Course> getCoursesBySubject(Long subjectId, Pageable pageable);

    long getTotalCourseCount();

    boolean existsByCourseName(String courseName);

    void deleteCourse(String courseId);

    Page<Course> getAllCoursesByClassPaged(Pageable pageable, String classId);

    /**
     * Find all class names linked to a given course.
     */
    List<String> getLinkedClassNames(UUID courseId);

    /**
     * Assign a course to multiple classes.
     * For Basic classes (e.g., Basic1A), the course will be automatically
     * linked to all sections with the same number (Basic1A through Basic1F).
     *
     * @param courseId the UUID of the course
     * @param classIds list of class names to assign the course to
     */
    void assignCourseToClasses(UUID courseId, List<String> classIds);

}
