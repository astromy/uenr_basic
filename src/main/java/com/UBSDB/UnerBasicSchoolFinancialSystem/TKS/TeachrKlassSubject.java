/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.TKS;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
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
public class TeachrKlassSubject implements Serializable {

    public TeachrKlassSubject() {
    }

    public TeachrKlassSubject(Teacher teacher, klass klas, Subject subjects) {
        this.teacher = teacher;
        this.klas = klas;
        this.subjects = subjects;
    }
    @ManyToOne
    private Teacher teacher;
    @OneToOne
    private klass klas;
    @OneToOne
    private Subject subjects;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public klass getKlas() {
        return klas;
    }

    public void setKlas(klass klas) {
        this.klas = klas;
    }

    public Subject getSubjects() {
        return subjects;
    }

    public void setSubjects(Subject subjects) {
        this.subjects = subjects;
    }
}
