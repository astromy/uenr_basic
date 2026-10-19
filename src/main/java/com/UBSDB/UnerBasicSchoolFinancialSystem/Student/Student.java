
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.UBSDB.UnerBasicSchoolFinancialSystem.Student;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Assessment.Assessment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Attendance.Attendance;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Bill.StudentBill.StudentBill;
import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.Payment;
import com.UBSDB.UnerBasicSchoolFinancialSystem.PROMOTION.promote;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Receipt.Receipt;
import com.UBSDB.UnerBasicSchoolFinancialSystem.klass.klass;
import com.UBSDB.UnerBasicSchoolFinancialSystem.mock.Mock;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


/**
 *
 * @author enock
 */
@Entity
public class Student implements Serializable {
    @Id
    private String StudentId;
    
    
    private Long idSpecia;
    
    
    @OneToMany(mappedBy = "student")
    private List<promote>promotions;

    public List<promote> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<promote> promotions) {
        this.promotions = promotions;
    }
    
    private String FirstName;
    private String LastName;
    private String surname;
    private String dateOfBirth;
    private String physicalDisability;
    private String NHISCardumber;
    private String GuadiaName;
    //Staff or non-Staff
    private String staffId;
    private String ParentCategory;
    private String parentNumber;
    private String residentialAddress;
    private String Occupation;
    private String Sex;
    private String fullName;
    private String GhanaCardNumber;
    @OneToMany(mappedBy = "student")
    private List<Attendance>attendances;

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
    
    public String getGhanaCardNumber() {
        return GhanaCardNumber;
    }

    public void setGhanaCardNumber(String GhanaCardNumber) {
        this.GhanaCardNumber = GhanaCardNumber;
    }
 private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getFullName() {
            return getSurname() +" " + getFirstName()+" "+getLastName();
    }

    public void setFullName(String fullName) {
        
        this.fullName = fullName;
    }
    
    //Checking those who have stooped school

    public String getSex() {
        return Sex;
    }

    public Student() {
    }

    public Long getIdSpecia() {
        return idSpecia;
    }

    public void setIdSpecia(Long idSpecia) {
        this.idSpecia = 00l;
    }

    public Student(String StudentId, String FirstName, String LastName, String surname) {
        this.StudentId = StudentId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.surname = surname;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public Student(String FirstName, String LastName, String surname, String dateOfBirth, String physicalDisability, String NHISCardumber, String GuadiaName, String staffId, String ParentCategory, String parentNumber, String residentialAddress, String Occupation, String Sex, String fullName, String StudentStatus, boolean newComer, String studentPicture, String productPicturePath, String ProductPicturePathPDF) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.physicalDisability = physicalDisability;
        this.NHISCardumber = NHISCardumber;
        this.GuadiaName = GuadiaName;
        this.staffId = staffId;
        this.ParentCategory = ParentCategory;
        this.parentNumber = parentNumber;
        this.residentialAddress = residentialAddress;
        this.Occupation = Occupation;
        this.Sex = Sex;
        this.fullName = fullName;
        this.StudentStatus = StudentStatus;
        this.newComer = newComer;
        this.studentPicture = studentPicture;
        this.productPicturePath = productPicturePath;
        this.ProductPicturePathPDF = ProductPicturePathPDF;
    }
    
    private String StudentStatus;
    private boolean newComer;

    public boolean isNewComer() {
        return newComer;
    }

    public void setNewComer(boolean newComer) {
        this.newComer = newComer;
    }
    public String getStudentStatus() {
        return StudentStatus;
    }

    public void setStudentStatus(String StudentStatus) {
        this.StudentStatus = StudentStatus;
    }
    @ManyToOne
    private klass studentklass;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhysicalDisability() {
        return physicalDisability;
    }

    public void setPhysicalDisability(String physicalDisability) {
        this.physicalDisability = physicalDisability;
    }

    public String getNHISCardumber() {
        return NHISCardumber;
    }

    public void setNHISCardumber(String NHISCardumber) {
        this.NHISCardumber = NHISCardumber;
    }

    public String getGuadiaName() {
        return GuadiaName;
    }
    
    @Column(name="sutdent_reference_number")
   private String StudentReference;

    public String getStudentReference() {
        return StudentReference;
    }

    public void setStudentReference(String StudentReference) {
        this.StudentReference = StudentReference;
    }
   
    public void setGuadiaName(String GuadiaName) {
        this.GuadiaName = GuadiaName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getParentCategory() {
        return ParentCategory;
    }

    public void setParentCategory(String ParentCategory) {
        this.ParentCategory = ParentCategory;
    }

    public String getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(String parentNumber) {
        this.parentNumber = parentNumber;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String Occupation) {
        this.Occupation = Occupation;
    }
    
   @OneToMany (mappedBy = "student")
    private  List<StudentBill> studentBills;

    public List<StudentBill> getStudentBills() {
        return studentBills;
    }

    public void setStudentBills(List<StudentBill> studentBills) {
        this.studentBills = studentBills;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String StudentId) {
        this.StudentId = StudentId;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public klass getStudentklass() {
        return studentklass;
    }

    public void setStudentklass(klass studentklass) {
        this.studentklass = studentklass;
    }
    private  String studentPicture;

    public String getStudentPicture() {
        return studentPicture;
    }

    public void setStudentPicture(String studentPicture) {
        this.studentPicture = studentPicture;
    }
    private  String productPicturePath;
   private String ProductPicturePathPDF;

    public void setProductPicturePathPDF(String ProductPicturePathPDF) {
        this.ProductPicturePathPDF = ProductPicturePathPDF;
    }
    public void setProductPicturePath(String productPicturePath) {
        this.productPicturePath = productPicturePath;
    }

    public String getProductPicturePath() {
     if(studentPicture==null||StudentId==null)return null;
      return productPicturePath;

    }
     public String getProductPicturePathPDF() {
     if(studentPicture==null||StudentId==null)return null;
      return ProductPicturePathPDF;

    }
     @OneToMany(mappedBy="student")
     
     private List<Assessment>assessment;
 @OneToMany(mappedBy="student")
     
     private List<Mock>mocks;

    public List<Mock> getMocks() {
        return mocks;
    }

    public void setMocks(List<Mock> mocks) {
        this.mocks = mocks;
    }
 
 
    public List<Assessment> getAssessment() {
        return assessment;
    }

    public void setAssessment(List<Assessment> assessment) {
        this.assessment = assessment;
    }
         @OneToMany(mappedBy="student")
     private List<Payment>payments;

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
@OneToMany(mappedBy="stud")
private List<Receipt>receipts;

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public Student(String StudentId) {
        this.StudentId = StudentId;
    }

  public StudentResponse getStudentResponse(){
      if (this.idSpecia==null){
      this.idSpecia=0l; 
      }
      var stud=new StudentResponse(this);
      stud.setStudentklass(this.studentklass.getKlassResponse());
  return stud;
  
  }

}
