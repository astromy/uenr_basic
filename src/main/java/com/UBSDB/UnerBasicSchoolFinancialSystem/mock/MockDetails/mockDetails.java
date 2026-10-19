/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.mock.MockDetails;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


/**
 *
 * @author lys
 */
@Entity
public class mockDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "mockDetails")
    private List<Mock>mockDetails;
    
    private   String hashCode;
    
    private int subjects;
    
    private  int submitted;
    
    private String startDate;
    
    private String endDate;
   private String mockName;
   @ManyToMany
 private List<Subject>subjectsa;
   @ManyToOne
private Term term;

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public List<Subject> getSubjectsa() {
        return subjectsa;
    }

    public void setSubjectsa(List<Subject> subjectsa) {
        this.subjectsa = subjectsa;
    }
    
 public String getMockName() {
        return mockName;
    }

    public void setMockName(String mockName) {
        this.mockName = mockName;
    }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Mock> getMockDetails() {
        return mockDetails;
    }

    public void setMockDetails(List<Mock> mockDetails) {
        this.mockDetails = mockDetails;
    }

  
    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public int getSubjects() {
        return subjects;
    }

    public void setSubjects(int subjects) {
        this.subjects = subjects;
    }

    public int getSubmitted() {
        return submitted;
    }

    public void setSubmitted(int submitted) {
        this.submitted = submitted;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public mockDetails(Long id) {
        this.id = id;
    }

    public mockDetails() {
    }
   private String submittedNames;
   
private int  totalStudents;

private String StudentsResultsUploaded;

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getStudentsResultsUploaded() {
        return StudentsResultsUploaded;
    }

    public void setStudentsResultsUploaded(String StudentsResultsUploaded) {
        this.StudentsResultsUploaded = StudentsResultsUploaded;
    }

    public String getSubmittedNames() {
        return submittedNames;
    }

    public void setSubmittedNames(String submittedNames) {
        this.submittedNames = submittedNames;
    }
   
}
