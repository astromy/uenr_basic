/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;
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
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    public int getId() {
        return Id;
    }
    private String attendancepk;

    public String getAttendancepk() {
        return attendancepk;
    }

    public void setAttendancepk(String attendancepk) {
        this.attendancepk = attendancepk;
    }
    
    private String Conduct;
    private String Interest;

    public String getConduct() {
        return Conduct;
    }

    public void setConduct(String Conduct) {
        this.Conduct = Conduct;
    }

    public String getInterest() {
        return Interest;
    }

    public void setInterest(String Interest) {
        this.Interest = Interest;
    }
    public void setId(int Id) {
        this.Id = Id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public String getTotalNumberOfDays() {
        return TotalNumberOfDays;
    }

    public void setTotalNumberOfDays(String TotalNumberOfDays) {
        this.TotalNumberOfDays = TotalNumberOfDays;
    }

    public String getStudentAttendedDays() {
        return StudentAttendedDays;
    }

    public void setStudentAttendedDays(String StudentAttendedDays) {
        this.StudentAttendedDays = StudentAttendedDays;
    }
    @ManyToOne
    private Student student;
    @OneToOne
    private Term term;
    private String TotalNumberOfDays;
    private String StudentAttendedDays;
    
}
