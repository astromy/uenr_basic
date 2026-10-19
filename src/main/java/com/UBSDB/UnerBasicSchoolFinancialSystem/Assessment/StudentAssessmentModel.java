/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;

/**
 *
 * @author lys
 */

import org.springframework.stereotype.Component;

@Component
public class StudentAssessmentModel {
    private String TermName;
    private Long TermId;
    private String StudentId;
    private String StudentClass;
    private String academicYear;

    public String getTermName() {
        return this.TermName;
    }

    public StudentAssessmentModel() {
    }

    public StudentAssessmentModel(String TermName, Long TermId, String StudentId, String StudentClass, String academicYear) {
        this.TermName = TermName;
        this.TermId = TermId;
        this.StudentId = StudentId;
        this.StudentClass = StudentClass;
        this.academicYear = academicYear;
    }

    public String getAcademicYear() {
        return this.academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public void setTermName(String fullName) {
        this.TermName = fullName;
    }

    public Long getTermId() {
        return this.TermId;
    }

    public void setTermId(Long TermId) {
        this.TermId = TermId;
    }

    public String getStudentId() {
        return this.StudentId;
    }

    public void setStudentId(String StudentId) {
        this.StudentId = StudentId;
    }

    public String getStudentClass() {
        return this.StudentClass;
    }

    public void setStudentClass(String StudentClass) {
        this.StudentClass = StudentClass;
    }
}
