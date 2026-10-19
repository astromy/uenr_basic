/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Teacher;

import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.cacheAssessment.CacheAssessment;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


/**
 *
 * @author enock
 */
@Entity
public class Teacher {
   private String FirstName;
   private String ClassTeacher;

    public String getClassTeacher() {
        return ClassTeacher;
    }

    public void setClassTeacher(String ClassTeacher) {
        this.ClassTeacher = ClassTeacher;
    }
    
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
    private String StaffId;

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String StaffId) {
        this.StaffId = StaffId;
    }
    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
    private String LastName;
     private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private long  TeacherId;
   
   private String PhoneNumber ;

    public String getPhoneNumberId() {
        return PhoneNumber;
    }

    public void setPhoneNumberId(String PhoneNumberId) {
        this.PhoneNumber = PhoneNumberId;
    }
   
   private String TeacherStatus;

    public String getTeacherStatus() {
        return TeacherStatus;
    }

    public void setTeacherStatus(String TeacherStatus) {
        this.TeacherStatus = TeacherStatus;
    }
    
    public long getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(long TeacherId) {
        this.TeacherId = TeacherId;
    }
    
@OneToMany (mappedBy = "teacher")
private List<CacheAssessment>cachedAssessments;

    public List<CacheAssessment> getCachedAssessments() {
        return cachedAssessments;
    }

    public void setCachedAssessments(List<CacheAssessment> cachedAssessments) {
        this.cachedAssessments = cachedAssessments;
    }
   @OneToMany(mappedBy="teacher")
   private List<TeachrKlassSubject>tkss;

    public List<TeachrKlassSubject> getTkss() {
        return tkss;
    }

    public void setTkss(List<TeachrKlassSubject> tkss) {
        this.tkss = tkss;
    }

    public TeacherResponse getResponse() {
        return TeacherResponse.builder()
                .ClassTeacher(ClassTeacher)
                .FirstName(FirstName)
                .TeacherId(TeacherId)
                .LastName(LastName)
                .sex(sex)
                .TeacherStatus(TeacherStatus)
                .PhoneNumber(PhoneNumber)
                .StaffId(StaffId)
                .build();


    }
}

