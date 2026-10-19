package com.UBSDB.UnerBasicSchoolFinancialSystem.Course;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.SubjectResponse;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.TermWeek.TermWeek;
import lombok.Data;

@Data
public class CourseResponse {
    private String courseId;
    private String courseName;
    private String description;
    private String notes;
    private SubjectResponse subject;
    private String termWeekName;

    public CourseResponse() {
    }

    public CourseResponse(String courseId, String courseName, String description, String notes, SubjectResponse subject, String termWeekName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.notes = notes;
        this.subject = subject;
        this.termWeekName = termWeekName;
    }

    public CourseResponse(Course course) {
        this.courseId = course.getCourseId().toString();
        this.courseName = course.getCourseName();
        this.description = course.getDescription();
        this.notes = course.getNotes();
        if (course.getSubject() != null) {
            this.subject = new SubjectResponse(course.getSubject().getId(), course.getSubject().getSubjectName());
        }
        if (course.getTermWeek() != null) {
            TermWeek tw = course.getTermWeek();
            if (tw.getTerm() != null) {
                this.termWeekName = tw.getTerm().getTermName() + " - " + tw.getWeekName();
            } else {
                this.termWeekName = tw.getWeekName();
            }
        }
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
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

    public SubjectResponse getSubject() {
        return subject;
    }

    public void setSubject(SubjectResponse subject) {
        this.subject = subject;
    }

    public String getTermWeekName() {
        return termWeekName;
    }

    public void setTermWeekName(String termWeekName) {
        this.termWeekName = termWeekName;
    }
}
