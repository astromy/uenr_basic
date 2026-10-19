/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.PROMOTION;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Academic.Academic;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
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
public class promote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
private Long ID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public promote(String previousClass, String nextClass, Student student, Academic academicyear) {
        this.previousClass = previousClass;
        this.nextClass = nextClass;
        this.student = student;
        this.academicyear = academicyear;
    }

    public promote() {
    }
    
    
    private String previousClass;
  private String nextClass;

  @ManyToOne
  private Student student;

  @OneToOne
  private Academic academicyear;

    public Academic getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(Academic academicyear) {
        this.academicyear = academicyear;
    }
  
  
    public String getPreviousClass() {
        return previousClass;
    }

    public void setPreviousClass(String previousClass) {
        this.previousClass = previousClass;
    }

    public String getNextClass() {
        return nextClass;
    }

    public void setNextClass(String nextClass) {
        this.nextClass = nextClass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

  
}
