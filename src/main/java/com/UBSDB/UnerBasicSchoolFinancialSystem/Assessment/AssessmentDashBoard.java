/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;

/**
 *
 * @author lys
 */

public class AssessmentDashBoard {
    private int id;
    private Term term;
    private String StudentClass;
    private int TotalSubject;
    private int SubmittedSubjects;

    public AssessmentDashBoard() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Term getTerm() {
        return this.term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public String getStudentClass() {
        return this.StudentClass;
    }

    public void setStudentClass(String StudentClass) {
        this.StudentClass = StudentClass;
    }

    public int getTotalSubject() {
        return this.TotalSubject;
    }

    public void setTotalSubject(int TotalSubject) {
        this.TotalSubject = TotalSubject;
    }

    public int getSubmittedSubjects() {
        return this.SubmittedSubjects;
    }

    public void setSubmittedSubjects(int SubmittedSubjects) {
        this.SubmittedSubjects = SubmittedSubjects;
    }
}
