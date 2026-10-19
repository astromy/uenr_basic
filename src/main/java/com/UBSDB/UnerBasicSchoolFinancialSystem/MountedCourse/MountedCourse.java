package com.UBSDB.UnerBasicSchoolFinancialSystem.MountedCourse;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import jakarta.persistence.Column;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "mounted_courses", indexes = {
        @jakarta.persistence.Index(name = "idx_mounted_course_class", columnList = "class_name"),
        @jakarta.persistence.Index(name = "idx_mounted_course_term", columnList = "term_id"),
        @jakarta.persistence.Index(name = "idx_mounted_course_subject", columnList = "subject_id"),
        @jakarta.persistence.Index(name = "idx_mounted_course_course", columnList = "course_id")
})
public class MountedCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "class_name", nullable = false)
    private klass klass;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;
    @ManyToOne
    @JoinColumn(name = "term_week_id", nullable = false)

    private TermWeek termWeek;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    private String notes;

    @Column(length = 20)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public klass getKlass() {
        return klass;
    }

    public void setKlass(klass klass) {
        this.klass = klass;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MountedCourseResponse getMountedCourseResponse() {
        return new MountedCourseResponse(this);
    }
}
