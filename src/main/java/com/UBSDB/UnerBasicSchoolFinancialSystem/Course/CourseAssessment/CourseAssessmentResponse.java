package com.UBSDB.UnerBasicSchoolFinancialSystem.Course.CourseAssessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseAssessmentResponse {
    private Long id;
    private String studentId;
    private String studentName;
    private String className;
    private String subjectName;
    private String termName;
    private String academicYear;
    private double totalScore;
    private double examScores;
    private double totalTestScore;
    private double totalTestScoreAverage;
    private double examsAverage;
    private String grade;
    private String remarks;
    private String position;
    private String initials;
    private String headmasterInitial;
    private float highestScore;
    private String TeacherName;

    public CourseAssessmentResponse(Assessment a) {
        this.id = a.getId();
        this.studentId = a.getStudent() != null ? a.getStudent().getStudentId() : null;
        this.studentName = a.getStudent() != null
                ? a.getStudent().getFirstName() + "_" + a.getStudent().getLastName() + " "
                : null;
        this.className = a.getStudentClass() != null ? a.getStudentClass().getClassName() : null;
        this.subjectName = a.getSubject() != null ? a.getSubject().getSubjectName() : null;
        this.termName = a.getTerm() != null ? a.getTerm().getTermName() : null;
        this.academicYear = a.getTerm() != null ? a.getTerm().getTermAcademicYear() : null;
        this.totalScore = a.getTotal_exams_exams_score();
        this.examScores = a.getExamScores();
        this.totalTestScore = a.getTotalTestScore();
        this.totalTestScoreAverage = a.getTotalTestScoreAverage();
        this.examsAverage = a.getExamsAverage();
        this.grade = a.getGrade();
        this.remarks = a.getRemarks();
        this.position = a.getPosition();
        this.initials = a.getInitials();
        this.headmasterInitial = a.getHeadmasterInitial();
        this.highestScore = a.getHighestScore();
        this.TeacherName = a.getTeacher() != null ? a.getTeacher().getFirstName() : null;
    }

}
