/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author lys
 */
@Entity
@Builder
@AllArgsConstructor

public class CacheAssessment {
 @Id   
@GeneratedValue(strategy = GenerationType.AUTO)    
 private Long id;
@OneToOne
private klass StudentClass;

   
    public klass getStudentClass() {
        return StudentClass;
    }

    public void setStudentClass(klass StudentClass) {
        this.StudentClass = StudentClass;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getTestScores() {
        return TotalScore;
    }



    public double getExamScores() {
        return ExamScores;
    }

    public void setExamScores(double ExamScores) {
        this.ExamScores = ExamScores;
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

    public double getTotalTestScore() {
        return TotalTestScore;
    }

    public void setTotalTestScore(double TotalTestScore) {
        this.TotalTestScore = TotalTestScore;
    }
    private float total_exams_exams_score;

    public float getTotal_exams_exams_score() {
        return total_exams_exams_score;
    }

    public void setTotal_exams_exams_score(float total_exams_exams_score) {
        this.total_exams_exams_score = total_exams_exams_score;
    }
    
@OneToOne
private  Student student;
private String HeadMasterApproval;  

    public String getHeadMasterApproval() {
        return HeadMasterApproval;
    }

    public void setHeadMasterApproval(String HeadMasterApproval) {
        this.HeadMasterApproval = HeadMasterApproval;
    }
private double TotalScore;

    public double getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(double TotalScore) {
        this.TotalScore = TotalScore;
    }
  
private double ExamScores;

@OneToOne
private Subject subject;
private String Grade;
private String Remarks;

    public CacheAssessment(double TotalScore, double ExamScores, Subject subject, double TotalTestScore, double TotalTestScoreAverage, double ExamsAverage) {
        this.TotalScore = TotalScore;
        this.ExamScores = ExamScores;
        this.subject = subject;
        this.TotalTestScore = TotalTestScore;
        this.TotalTestScoreAverage = TotalTestScoreAverage;
        this.ExamsAverage = ExamsAverage;
    }

    public CacheAssessment() {
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }
    public double getTotalTestScoreAverage() {
        return TotalTestScoreAverage;
    }

    public void setTotalTestScoreAverage(double TotalTestScoreAverage) {
        this.TotalTestScoreAverage = TotalTestScoreAverage;
    }
private double TotalTestScore;
private double TotalTestScoreAverage;
private double ExamsAverage;
private String position;
private String initials;
private String headmasterInitial;

    public String getHeadmasterInitial() {
        return headmasterInitial;
    }

    public void setHeadmasterInitial(String headmasterInitial) {
        this.headmasterInitial = headmasterInitial;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
    public double getExamsAverage() {
        return ExamsAverage;
    }

    public void setExamsAverage(double ExamsAverage) {
        this.ExamsAverage = ExamsAverage;
    }
private float highestScore;

    public float getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(float highestScore) {
        this.highestScore = highestScore;
    }
  
  
    @OneToOne
   private Term term;
    
  @ManyToOne
private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    private String SpecialId;

    public String getSpecialId() {
        return SpecialId;
    }

    public void setSpecialId(String SpecialId) {
        this.SpecialId = SpecialId;
    }
    
}
