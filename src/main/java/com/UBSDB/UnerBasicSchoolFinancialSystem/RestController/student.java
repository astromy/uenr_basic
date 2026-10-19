/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.RestController;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @author lys
 */
@Component
@Data
public class student {
    private String Name;
    private String Level;
    private String Gender;
    private String DateOfBirth;
    private String Option;
    private String EnrollmentOption;
    private String ReferenceNumber;
    private String Program;
    private String Status;
    private String Telephone;
    private String StudentId;
    private String Email;
    private String FirstName;
    private String Othernames;
    private String IndexNumber;
    private String StudentType;

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getName() {
        return Name;
    }

   public student (){
   
   
   }    
   public student (com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student stud){
   this.Name=stud.getFullName();
   this.Level=stud.getStudentklass().getClassName();
   //this.DateOfBirth=stud.getDateOfBirth();
   this.Gender=stud.getSex();
   this.Option="";
   this.EnrollmentOption="";
   this.ReferenceNumber=stud.getStudentId();
   this.Program="Basic School";
   this.Status=stud.getStudentStatus();
   this.Telephone=stud.getParentNumber();
   this.StudentId=stud.getStudentId();
   this.Email=stud.getEmail();
   this.FirstName="";
   this.Othernames=stud.getLastName();
   this.IndexNumber=stud.getStudentId();
   this.StudentType="";
   
   }      
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public String getOption() {
        return Option;
    }

    public void setOption(String Option) {
        this.Option = Option;
    }

    public String getEnrollmentOption() {
        return EnrollmentOption;
    }

    public void setEnrollmentOption(String EnrollmentOption) {
        this.EnrollmentOption = EnrollmentOption;
    }

    public String getReferenceNumber() {
        return ReferenceNumber;
    }

    public void setReferenceNumber(String ReferenceNumber) {
        this.ReferenceNumber = ReferenceNumber;
    }

    public String getProgram() {
        return Program;
    }

    public void setProgram(String Program) {
        this.Program = Program;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String StudentId) {
        this.StudentId = StudentId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getOthernames() {
        return Othernames;
    }

    public void setOthernames(String Othernames) {
        this.Othernames = Othernames;
    }

    public String getIndexNumber() {
        return IndexNumber;
    }

    public void setIndexNumber(String IndexNumber) {
        this.IndexNumber = IndexNumber;
    }

    public String getStudentType() {
        return StudentType;
    }

    public void setStudentType(String StudentType) {
        this.StudentType = StudentType;
    }

    @Override
    public String toString() {
        return "student{" +
                "Name='" + Name + '\'' +
                ", Level='" + Level + '\'' +
                ", Gender='" + Gender + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", Option='" + Option + '\'' +
                ", EnrollmentOption='" + EnrollmentOption + '\'' +
                ", ReferenceNumber='" + ReferenceNumber + '\'' +
                ", Program='" + Program + '\'' +
                ", Status='" + Status + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", StudentId='" + StudentId + '\'' +
                ", Email='" + Email + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", Othernames='" + Othernames + '\'' +
                ", IndexNumber='" + IndexNumber + '\'' +
                ", StudentType='" + StudentType + '\'' +
                '}';
    }
}
