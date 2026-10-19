package com.UBSDB.UnerBasicSchoolFinancialSystem.Course;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courses", indexes = {
        @jakarta.persistence.Index(name = "idx_course_name", columnList = "courseName"),
        @jakarta.persistence.Index(name = "idx_course_subject", columnList = "subject_id")
})
public class Course {
    @Id
    private UUID courseId;

    @Column(nullable = false, length = 100)
    private String courseName;

    @Column(length = 500)
    private String description;

    @Column(length = 2000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = true)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = true)
    private Term term;

    @ManyToOne
    @JoinColumn(name = "term_week_id", nullable = true)
    private TermWeek termWeek;
    @ManyToOne
    private klass klass;

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public TermWeek getTermWeek() {
        return termWeek;
    }

    public void setTermWeek(TermWeek termWeek) {
        this.termWeek = termWeek;
    }

    public klass getKlass() {
        return klass;
    }

    public void setKlass(klass klass) {
        this.klass = klass;
    }

    public CourseResponse getCourseResponse() {
        return new CourseResponse(this);
    }
}
