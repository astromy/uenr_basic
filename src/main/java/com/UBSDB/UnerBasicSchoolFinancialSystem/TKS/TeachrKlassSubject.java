/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.TKS;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Subject.Subject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher.Teacher;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


/**
 *
 * @author enock
 */
@Entity
//@Table(name="TeacherKlassSubjects")
@Setter
@Getter
@AllArgsConstructor
@Table(name="Klass_Teacher_Subject")
public class TeachrKlassSubject  {
    @Id
  // @GeneratedValue(strategy = GenerationType.UUID)
    private UUID teacherKlassSubjectId;

    public TeachrKlassSubject() {

    }

    public TeachrKlassSubject(Teacher teacher, klass klas, Subject subjects) {
        this.teacher = teacher;
        this.klas = klas;
        this.subjects = subjects;
    }

    @ManyToOne
    @JoinColumn(name = "teacher_teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private  Subject subjects;

    @ManyToOne
    @JoinColumn(name = "klas_class_name")
    private  klass klas;

}
