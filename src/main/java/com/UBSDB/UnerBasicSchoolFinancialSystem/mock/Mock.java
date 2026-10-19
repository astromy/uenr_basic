/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.mock;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment.CacheAssessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails.mockDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 *
 * @author lys
 */
@Entity
public class Mock {
    @Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
@ManyToOne
private klass StudentClass;

    @Override
    public String toString() {
        return "Assessment{" + "id=" + id + ", StudentClass=" + StudentClass + ", total_exams_exams_score=" + total_exams_exams_score + ", student=" + student + ", HeadMasterApproval=" + HeadMasterApproval + ", TotalScore=" + TotalScore + ", ExamScores=" + ExamScores + ", subject=" + subject + ", Grade=" + Grade + ", Remarks=" + Remarks + ", TotalTestScore=" + TotalTestScore + ", TotalTestScoreAverage=" + TotalTestScoreAverage + ", ExamsAverage=" + ExamsAverage + ", position=" + position + ", initials=" + initials + ", headmasterInitial=" + headmasterInitial + ", term=" + term + '}';
    }

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
    
@ManyToOne
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

    public Mock (double TotalScore, double ExamScores, Subject subject, double TotalTestScore, double TotalTestScoreAverage, double ExamsAverage) {
        this.TotalScore = TotalScore;
        this.ExamScores = ExamScores;
        this.subject = subject;
        this.TotalTestScore = TotalTestScore;
        this.TotalTestScoreAverage = TotalTestScoreAverage;
        this.ExamsAverage = ExamsAverage;
    }

    public Mock () {
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
private int hashCode;
private String name;

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
  @ManyToOne
  private Teacher teacher; 
  
    @ManyToOne
   private Term term;
public Mock (CacheAssessment CA,Long id) {
        this.StudentClass = CA.getStudentClass();
        this.total_exams_exams_score = CA.getTotal_exams_exams_score();
        this.student = CA.getStudent();
        this.HeadMasterApproval = CA.getHeadMasterApproval();
        this.TotalScore = CA.getTotalScore();
        this.ExamScores = CA.getExamScores();
        this.subject = CA.getSubject();
        this.Grade = CA.getGrade();
        this.Remarks = CA.getRemarks();
        this.TotalTestScore = CA.getTotalTestScore();
        this.TotalTestScoreAverage = CA.getTotalTestScoreAverage();
        this.ExamsAverage = CA.getExamsAverage();
        this.position = CA.getPosition();
        this.initials = CA.getGrade();
        this.headmasterInitial = CA.getHeadmasterInitial();
        this.highestScore = CA.getHighestScore();
        this.term = CA.getTerm();
        this.teacher = CA.getTeacher();
        this.mockDetails=new mockDetails(id);
    }
public Assessment ConvertAssessment(Mock CA){
    Assessment a =new Assessment();
 a.setStudent(CA.getStudent()); 
 
       a.setTotal_exams_exams_score(CA.getTotal_exams_exams_score()) ;
        a.setStudentClass(  CA.getStudentClass());
        a.setHeadMasterApproval(CA.getHeadMasterApproval()); ;
         a.setTotalScore(CA.getTotalScore());
        a.setExamScores(CA.getExamScores());
        a.setSubject(CA.getSubject());
       a.setGrade(  CA.getGrade());
        a.setRemarks(CA.getRemarks());
        //this.TotalTestScore = CA.getTotalTestScore();
        //this.TotalTestScoreAverage = CA.getTotalTestScoreAverage();
        //this.ExamsAverage = CA.getExamsAverage();
        a.setPosition(CA.getPosition());
        a.setGrade(CA.getGrade());
        a.setHeadmasterInitial(CA.getHeadmasterInitial());
        a.setHighestScore(CA.getHighestScore());
        a.setTerm(CA.getTerm()) ;
        a.setTeacher(CA.getTeacher());

return a;
}
@ManyToOne
private mockDetails mockDetails;

    public mockDetails getMockDetails() {
        return mockDetails;
    }

    public void setMockDetails(mockDetails mockDetails) {
        this.mockDetails = mockDetails;
    }

}
