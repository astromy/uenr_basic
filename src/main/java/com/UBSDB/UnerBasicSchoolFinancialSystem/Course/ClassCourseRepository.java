package com.UBSDB.UnerBasicSchoolFinancialSystem.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassCourseRepository extends JpaRepository<ClassCourse, Long> {

    /**
     * Find all class-course links for a given course ID.
     */
    List<ClassCourse> findByCourseId(UUID courseId);

    /**
     * Find all class-course links for a given class name.
     */
    List<ClassCourse> findByClassName(String className);

    /**
     * Delete all class-course links for a given course ID.
     */
    void deleteByCourseId(UUID courseId);

    /**
     * Check if a link already exists for a given class and course.
     */
    boolean existsByClassNameAndCourseId(String className, UUID courseId);

    /**
     * Find all courses linked to classes whose names start with the given prefix.
     * Useful for finding all courses linked to Basic1 (Basic1A, Basic1B, etc.)
     */
    @Query("SELECT cc FROM ClassCourse cc WHERE cc.className LIKE CONCAT(:prefix, '%')")
    List<ClassCourse> findByClassNameStartingWith(@Param("prefix") String prefix);
}
