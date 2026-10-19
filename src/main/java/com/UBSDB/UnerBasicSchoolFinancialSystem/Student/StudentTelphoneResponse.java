//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.UBSDB.UnerBasicSchoolFinancialSystem.Student;

import org.springframework.stereotype.Component;

@Component
public class StudentTelphoneResponse {
    private String StudentId;
    private String phonenumber;
    private String StudentName;
    private String GuardianName;

    public StudentTelphoneResponse() {
    }

    public String getStudentId() {
        return this.StudentId;
    }

    public void setStudentId(String StudentId) {
        this.StudentId = StudentId;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getStudentName() {
        return this.StudentName;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }

    public String getGuardianName() {
        return this.GuardianName;
    }

    public void setGuardianName(String GuardianName) {
        this.GuardianName = GuardianName;
    }
}
