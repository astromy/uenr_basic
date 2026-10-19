
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment.CacheAssessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import lombok.*;


import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 *
 * @author enock
 */
@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class Assessment implements Serializable {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private Long id;
    @ManyToOne
    private klass StudentClass;
    private int TotalStudent;
    private float total_exams_exams_score;
    @ManyToOne
    private Student student;
    private String HeadMasterApproval;
    private double TotalScore;
    private double ExamScores;
    @OneToOne
    private Subject subject;
    private String Grade;
    private String Remarks;
    private double TotalTestScore;
    private double TotalTestScoreAverage;
    private double ExamsAverage;
    private String position;
    private String initials;
    private String headmasterInitial;
    private float highestScore;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Term term;
    private String details;



    public AssessmentResponse getAsessmentResponse() {
        return AssessmentResponse.builder()
                .examsAverage(getExamsAverage())
                .grade(getGrade())
                .className(getStudentClass().getClassName())
                .headmasterInitial(getHeadmasterInitial())
                .examScores(getExamScores())
                .highestScore(getHighestScore())
                .initials(getInitials())
                .position(getPosition())
                .remarks(getRemarks())

                .totalScore(getTotalScore())
                .studentName(getStudent().getFirstName()+"_"+getStudent().getLastName()+" ")
                .position(getPosition())
                .TeacherName(getTeacher().getFirstName() != null ? getTeacher().getFirstName() : "")
                .termName(getTerm().getTermName())
                .totalScore(getTotal_exams_exams_score())
                .examsAverage(getExamsAverage())
                .subjectName(getSubject().getSubjectName())
                .totalTestScore(getTotalTestScore())
                .totalTestScoreAverage(getTotalTestScoreAverage())
                .academicYear(term.getTermAcademicYear())

                .initials(initials)

                .build();
    }


    public Assessment getAsessment(CacheAssessment assessment) {
       return Assessment.builder()
               .ExamsAverage(assessment.getExamsAverage())
               .Grade(assessment.getGrade())
               .StudentClass(assessment.getStudentClass())
               .headmasterInitial(assessment.getHeadmasterInitial())
               .ExamScores(assessment.getExamScores())
               .highestScore(assessment.getHighestScore())
               .initials(assessment.getInitials())
               .position(assessment.getPosition())
               .Remarks(assessment.getRemarks())
               .TotalScore(assessment.getTotalScore())
               .student(assessment.getStudent())
               .position(assessment.getPosition())
               .teacher(assessment.getTeacher())
               .term(assessment.getTerm())
               .total_exams_exams_score(assessment.getTotal_exams_exams_score())
               .TotalTestScoreAverage(assessment.getTotalTestScoreAverage())
               .subject(assessment.getSubject())
               .TotalTestScore(assessment.getTotalTestScore())
               .total_exams_exams_score(assessment.getTotal_exams_exams_score())

               .initials(initials)
               .teacher(assessment.getTeacher())
               .build();
    }


   }
