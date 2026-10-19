package com.UBSDB.UnerBasicSchoolFinancialSystem.Course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Junction/composite table linking classes to courses.
 * When a course is assigned to a Basic class (e.g., Basic1A),
 * it is automatically linked to all classes with the same number
 * (Basic1A, Basic1B, Basic1C, Basic1D, Basic1E, Basic1F).
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "class_course", uniqueConstraints = {
        @UniqueConstraint(name = "uk_class_course", columnNames = {"class_name", "course_id"})
})
public class ClassCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name", nullable = false, length = 50)
    private String className;

    @Column(name = "course_id", nullable = false)
    private UUID courseId;
}
