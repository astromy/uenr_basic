/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Position;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */
@Component
public class AssessmentPosition {
    
    private Student student;
    private double TotalScore;
    private String position;
   private int totalSubjects;
    public Student getStudent() {
        return student;
    }

    public int getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(int totalSubjects) {
        this.totalSubjects = totalSubjects;
    }

    public double getTotalScore() {
        return TotalScore;
    }

    public String getPosition() {
        return position;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setTotalScore(double TotalScore) {
        this.TotalScore = TotalScore;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
}
