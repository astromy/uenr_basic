package com.UBSDB.UnerBasicSchoolFinancialSystem.Student;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klassReponse;
import lombok.Data;

@Data
public class StudentResultResponse {
    private String studentId;
    private String fullName;
    private klassReponse studentClass;
    private String termName;
    private String academicYear;
    private String subjectName;
    private double totalScore;
    private double examScores;
    private double totalTestScore;
    private double totalTestScoreAverage;
    private double examsAverage;
    private String grade;
    private String remarks;
    private String position;
    private String initials;
    
    public StudentResultResponse(Assessment assessment) {
        this.studentId = assessment.getStudent().getStudentId();
        this.fullName = assessment.getStudent().getFullName();
        this.studentClass = assessment.getStudent().getStudentklass().getKlassResponse();
        this.termName = assessment.getTerm().getTermName();
        this.academicYear = assessment.getTerm().getAcademicyear().getAcademicName();
        this.subjectName = assessment.getSubject().getSubjectName();
        this.totalScore = assessment.getTotalScore();
        this.examScores = assessment.getExamScores();
        this.totalTestScore = assessment.getTotalTestScore();
        this.totalTestScoreAverage = assessment.getTotalTestScoreAverage();
        this.examsAverage = assessment.getExamsAverage();
        this.grade = assessment.getGrade();
        this.remarks = assessment.getRemarks();
        this.position = assessment.getPosition();
        this.initials = assessment.getInitials();
    }
}
