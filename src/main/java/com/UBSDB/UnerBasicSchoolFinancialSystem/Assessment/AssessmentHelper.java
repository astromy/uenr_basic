/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class AssessmentHelper {
    private String SubjectName;
    private Student stud;
    private double Score;

    public AssessmentHelper() {
    }

    public String getSubjectName() {
        return this.SubjectName;
    }

    public void setSubjectName(String SubjectName) {
        this.SubjectName = SubjectName;
    }

    public Student getStud() {
        return this.stud;
    }

    public void setStud(Student stud) {
        this.stud = stud;
    }

    public double getScore() {
        return this.Score;
    }

    public void setScore(double Score) {
        this.Score = Score;
    }
}
