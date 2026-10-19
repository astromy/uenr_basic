/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.mock;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.*;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import org.springframework.stereotype.Component;

/**
 *
 * @author enock
 */
@Component
public class MockHelper {
    private String SubjectName;
    private Student stud;
    private double Score;
//@Enock1234L
    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String SubjectName) {
        this.SubjectName = SubjectName;
    }

    public Student getStud() {
        return stud;
    }

    public void setStud(Student stud) {
        this.stud = stud;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double Score) {
        this.Score = Score;
    }
    
}
