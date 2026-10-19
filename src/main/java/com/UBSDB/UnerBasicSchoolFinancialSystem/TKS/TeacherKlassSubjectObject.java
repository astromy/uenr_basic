/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.TKS;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import org.springframework.stereotype.Component;

/**
 *
 * @author FAIS-001
 */
@Component
public class TeacherKlassSubjectObject {
    
    private Teacher teacher;
    
    private klass klas;
   
    private Subject subjects;

    public TeacherKlassSubjectObject(Teacher teacher, klass klas, Subject subjects) {
        this.teacher = teacher;
        this.klas = klas;
        this.subjects = subjects;
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

    public TeacherKlassSubjectObject() {
    }
    
}
