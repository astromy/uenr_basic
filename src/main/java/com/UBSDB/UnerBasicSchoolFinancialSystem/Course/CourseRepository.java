package com.UBSDB.UnerBasicSchoolFinancialSystem.Course;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    // Optimized query using JOIN FETCH to avoid N+1 problem
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.subject LEFT JOIN FETCH c.termWeek WHERE c.courseName = :courseName")
    Optional<Course> findByCourseName(@Param("courseName") String courseName);

    // Paginated query with subject entity graph for performance
    @EntityGraph(attributePaths = {"subject", "termWeek"})
    @Query("SELECT c FROM Course c")
    Page<Course> findAllWithSubject(Pageable pageable);

    // Search courses by name with pagination
    @EntityGraph(attributePaths = {"subject", "termWeek"})
    @Query("SELECT c FROM Course c WHERE LOWER(c.courseName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Course> searchCourses(@Param("search") String search, Pageable pageable);

    // Find courses by subject with pagination
    @EntityGraph(attributePaths = {"subject", "termWeek"})
    @Query("SELECT c FROM Course c WHERE c.subject.id = :subjectId")
    Page<Course> findBySubjectId(@Param("subjectId") Long subjectId, Pageable pageable);
    @EntityGraph(attributePaths = {"subject", "termWeek"})
    @Query("SELECT c FROM Course c WHERE c.klass.className = :klassId")
    Page<Course> findByKlassId(@Param("klassId") String klassId, Pageable pageable);

    // Count total courses
    long count();

    // Check if course name exists
    boolean existsByCourseName(String courseName);

    // Find all courses ordered by name (for dropdowns/selects)
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.subject LEFT JOIN FETCH c.termWeek ORDER BY c.courseName ASC")
    List<Course> findAllOrderByCourseName();
}
